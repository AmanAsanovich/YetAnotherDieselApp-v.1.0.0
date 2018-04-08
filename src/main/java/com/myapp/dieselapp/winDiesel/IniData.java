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
package com.myapp.dieselapp.winDiesel;

import org.jsoup.nodes.Document;

/*
 * Class to manipulate with application settings based on XML
 * Aman Mambetov
 * "asanbay"@mail.ru
 */
public class IniData {
    private String sDocument;
    public IniData(Document dDoc) {
        sDocument = dDoc.toString();
    }

    public void addTopic(String sAccName, String sTopicId, String sTitle) {
        String sPattern = "(?s).+" + sTopicId + ".+";
        if (sDocument.matches(sPattern))
            return;         // Return if topic exists

        String sRe = "(?s)(";
        sRe += sAccName;
        sRe += "[^>]+?>\\s*\n)";    // IMPORTANT: +? lasy
        
        String sNewTopic = "  <IdOfTopic Title=\"";
        sNewTopic += sTitle;
        sNewTopic += "\">\n";
        sNewTopic += "    " + sTopicId;
        sNewTopic += " \n  </IdOfTopic> \n";
        
        sDocument = sDocument.replaceFirst(sRe, "$1"+sNewTopic);
    }

    public void deleteTopicByTitle(String sTitle) {
        // IMPORTANT: +? lasy
        String sRe = "(?s)\\s*<IdOfTopic Title=\"";
        sRe += sTitle;
        sRe += "\">.+?</IdOfTopic>";
        sDocument = sDocument.replaceFirst(sRe, "");
    }
    
    public void deleteTopic(String sTopicId) {
        // IMPORTANT: +? lasy
        String sRe = "(?s)\\s*<IdOfTopic[^>]+?>\\s*\n\\s*";
        sRe += sTopicId;
        sRe += "\\s*\n\\s*</IdOfTopic>";
        sDocument = sDocument.replaceFirst(sRe, "");
    }
    
    public void addAccount(String sAccName, String sPasswd) {
        //
        String sPattern = "(?s).+" + sAccName + ".+";
        if (sDocument.matches(sPattern))
            return;         // Return if account exists
        
        String sRe = "appIni> \n <Account Login=\"";
        sRe += sAccName;
        sRe += "\" Password=\"";
        sRe += sPasswd;
        sRe += "\"> \n </Account> \n";
        sDocument = sDocument.replaceFirst("appIni>\\s*\n", sRe);
    }

    public void deleteAccount(String sAccName) {
        // At least one account has to remain in the ini file?
        String sRe = "(?s)\\s*<Account Login=\"";
        sRe += sAccName;
        sRe += ".+?</Account>";    // IMPORTANT: +? lasy
        sDocument = sDocument.replaceFirst(sRe, "");
    }
    
    public String getData() {
        return sDocument;
    }

}
