/*
 * Copyright 2018 Aman Mambetov "asanbay"@mail.ru
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.myapp.dieselapp.diesel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.FileUtils;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

/**
 *
 * @author 2018 Aman Mambetov "asanbay"@mail.ru
 */
public class Upper {

    private Connection.Response resp;
    private Document dIniDoc;
    final private String sTopicUrl;
    final private String sUserAgent;
    public Map<String, String> mCookies;
    public String sUp;
    private boolean bDebug;


    public Upper () {
        sTopicUrl = "http://diesel.elcat.kg/index.php?showtopic=";
        sUserAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36 OPR/41.0.2353.69";
        mCookies = new HashMap<>();
        bDebug = false;
        
        dIniDoc = null;
        try {
            dIniDoc = this.ReadIni("Dapp.xml");
        } catch (IOException ex) {
            log.log(Level.SEVERE, null, ex);
        }
        if (dIniDoc == null) {
            log.info("Problem with the Dapp.xml file");
        }
        
        String sStr = dIniDoc.select("System DebugFlag").text();
        if (sStr.equals("1")) {
            bDebug = true;
            log.info("Debug mode on");
        }

        sUp = dIniDoc.select("System UpString").text();
    }
    
    public void doIt () throws IOException {
    /*
    * Метод считывает из файла "Dapp.xml" акаунты форума diesel.elcat.kg и соответсвующий им список номеров топиков для апания,
    * Для каждого аккаунта перебирается номера тем, которые затем апаются 
    *
    */
        Elements eSetOfIDs;

        Elements eAccounts = dIniDoc.select("Account");      // each element consist one account data
        for (Element _acc : eAccounts) {

            eSetOfIDs = _acc.select("IdOfTopic");     // get list of id
            if (eSetOfIDs.isEmpty()) {
                log.info("Empty data in the ini XML. Exit");
                return;
            }
        
            if (login(_acc.attr("Login"), _acc.attr("Password"))) {
                for (Element _oneID : eSetOfIDs) {                          // For each ID
                    this.newUp(_oneID.text());
                    this.myDelay(7);
                }
            }

        }

        
    }   // End of doIt

    
    public Boolean login(String sUser, String sPassw) throws IOException {
    /*
    * Метод входа в учетную запись форума diesel.elcat.kg
    */
        if (sUser.isEmpty() || sPassw.isEmpty()) {
            log.info("Username or password is empty. Exit");
            return false;
        }
        if (bDebug) { log.info(sUser); }
/*        
        try {
            getSert();
        } catch (NoSuchAlgorithmException | KeyManagementException ex) {
            log.log(Level.SEVERE, null, ex);
        }
*/

        String sURL = "http://diesel.elcat.kg/index.php?app=core&module=global&section=login";
        Document dDoc = Jsoup.connect(sURL).get();
        this.myDelay(2);
        
        // Поиск значения auth_key и f
        final String sAuthKey = dDoc.select("FORM[id=login] input[name=auth_key]").val();   // name="auth_key" value="88...15a0"

        if (sAuthKey == null) {
            log.log(Level.INFO, "value of login FORM input name='auth_key' not found.");
            return false;
        }
        
        resp = Jsoup.connect("http://diesel.elcat.kg/index.php?app=core&module=global&section=login&do=process")
            .postDataCharset("Windows-1251")
            .userAgent(sUserAgent)
            .data("auth_key", sAuthKey)
            .data("ips_username", sUser)
            .data("ips_password", sPassw)
            .data("rememberMe", "1")
            .data("anonymous", "0")
            .data("submit", "Отправить")
            .method(Connection.Method.POST)
            .execute();

        if (bDebug) { log.info(resp.statusMessage()); }

        // To check - am I logen in?
        dDoc = resp.parse();
        String sExitLink = dDoc.select("a[id=user_link]").text();
        String sPatt = sUser + ".*";
        if (!sExitLink.matches(sPatt)) {
            log.info("Not logged in. Either bad username or password. Perhaps wrong codepage");
            if (bDebug) { FileWrite(dDoc.toString(), "login-out.htm"); }
            return false;
        }
        if (bDebug) { log.info("Login confirmed"); }
        mCookies = resp.cookies();
    return true;
    }   // End of login method
    
    
    private boolean newUp (String sTID) throws IOException {
/*      
*   Метод записывает сообщение содержащееся в объекте-строке sUp ("up") в указанную
*   тему на diesel-форуме.
*   sTID содержит id темы, куда нужно записать сообщение
*/
        sTID = sTID.replaceAll("^\\s*(\\d{8,11})\\s*$", "$1");  // remove spaces if any
        Document dDoc = this.findNdeleteUp(sTID);    // Delete "up"
        
        // Поиск значения auth_key и f
        final Elements eInput = dDoc.select("form#ips_fastReplyForm input");  // Выбрать все теги input в форме с id=ips_fastReplyForm
        final String sAuthKey = eInput.select("input[name=auth_key]").val();
        final String sF = eInput.select("input[name=f]").val();

        if (sAuthKey == null) {
            log.log(Level.INFO, "value of FORM input name='auth_key' not found.");
            return false;
        }
        
        resp = Jsoup.connect("http://diesel.elcat.kg/index.php?")
            .postDataCharset("Windows-1251")
            .userAgent(sUserAgent)
            .data("app", "forums")
            .data("module", "post")
            .data("section", "post")
            .data("do", "reply_post_do")
            .data("f", sF)
            .data("t", sTID)
            .data("st", "")
            .data("auth_key", sAuthKey)
            .data("fast_reply_used", "1")
            .data("enableemo", "yes")
            .data("enablesig", "yes")
            .data("enabletrack", "1")
            .data("Post", sUp)
            .data("submit", "Отправить")
            .cookies(mCookies)
            .method(Connection.Method.POST)
            .execute();

	if (resp.statusCode()!=200) {
            log.log(Level.INFO, "Response status{0}", resp.statusCode());
            dDoc = resp.parse();
            if (bDebug) { FileWrite(dDoc.toString(), "newUp-out.htm"); }
            return false;
        }
        
    return true;    
    }   // End of newUp method
    
    private Document findNdeleteUp(String sTID) throws IOException {
/*      
*   Метод удаляет все сообщения содержащиеся в объекте-строке sUp ("up") из указанной
*   темы на diesel-форуме.
*   sTID содержит id темы, в которой нужно удалить сообщения
*/
        sTID = sTID.replaceAll("^\\s*(\\d{8,11})\\s*$", "$1");  // remove spaces if any
        Document dDoc = Jsoup.connect(sTopicUrl + sTID).cookies(mCookies).get();

        // Поиск постов со строкой 'up'
        String sUpPattern1 = "div .post:matches(^.{0,5}up$)";     // Up to 7 symbols terminated with "up"
        String sUpPattern2 = "div .post:matches(^.{0,5}ап$)";     // Up to 7 symbols terminated with "ап"
        Elements eAllPosts = dDoc.select("div .post_wrap");     // Собираем все посты 
        for (Element _item : eAllPosts) {
            Element eUpPost1 = _item.select(sUpPattern1).first();    // Ищем пост со строкой 'up'
            Element eUpPost2 = _item.select(sUpPattern2).first();    // Ищем пост со строкой 'ап'
            if (eUpPost1 != null || eUpPost2 != null) {
                String sDelLink = _item.select("li.post_del a").first().attr("href");  // Если есть такой, выбираем ссылку удаления
                if (sDelLink != null) {
                    try {
                        dDoc = Jsoup.connect(sDelLink).cookies(mCookies).get();    // Delete it
                    } catch (IOException ex) {
                        log.log(Level.SEVERE, null, ex);
                    }
                    this.myDelay(1);
                }
            }
        }
        
       return dDoc;
    }

    
    public void FileWrite (String sBuf, String sFName) {
        try {
            FileUtils.writeStringToFile(new File(sFName), sBuf, "UTF-8");
        } catch (IOException ex) {
            log.log(Level.SEVERE, null, ex);
        }
    }

    
    public final Document ReadIni (String sFName) throws IOException {
    /*
    * Метод чтения настроек из файла инициализации
    * 
    */
        InputStream is = null;
        try {
            is = new FileInputStream(sFName) {
                @Override
                public int read() throws IOException {
                return 0;
                }
            };
        }
        catch (FileNotFoundException ex) {
            log.log(Level.SEVERE, null, ex);
        }

        final Document dDoc = Jsoup.parse(is, "UTF-8", "", Parser.xmlParser());
        if (dDoc.getElementsByTag("DappIni").isEmpty()) {
            log.info("Please check if Dapp.ini file encoded in UTF-8 or UTF-16 and contains data.");
        }
        return dDoc;
    }
    
    public void myDelay (long interval) {
        try {
            TimeUnit.SECONDS.sleep(interval);
        } catch (InterruptedException ex) {
            log.log(Level.SEVERE, null, ex);
        }
    }
    
    private void getSert () throws NoSuchAlgorithmException, KeyManagementException {
    /* 
     *  fix for
     *    Exception in thread "main" javax.net.ssl.SSLHandshakeException:
     *       sun.security.validator.ValidatorException:
     *           PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException:
     *               unable to find valid certification path to requested target
     *  Written and compiled by Réal Gagnon rgagnon.com
     */
    // Этот фикс использовался в старой версии Дизель-форума во избежание ошибки 500
    TrustManager[] trustAllCerts = new TrustManager[] {
       new X509TrustManager() {
          @Override
          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
          }
          @Override
          public void checkClientTrusted(X509Certificate[] certs, String authType) {  }
          @Override
          public void checkServerTrusted(X509Certificate[] certs, String authType) {  }
       }
    };

    SSLContext sc = SSLContext.getInstance("SSL");
    sc.init(null, trustAllCerts, new java.security.SecureRandom());
    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

    // Create all-trusting host name verifier
    HostnameVerifier allHostsValid = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
          return true;
        }
    };
    // Install the all-trusting host verifier
    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        
    }
/*
 * end of the fix
 */

public static final Logger log = Logger.getLogger(Upper.class.getName());
   
}   // End of Upper class
