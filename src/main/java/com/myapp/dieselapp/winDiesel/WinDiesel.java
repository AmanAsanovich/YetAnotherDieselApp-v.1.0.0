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

import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Enumeration;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import javax.swing.JFrame;
import static javax.swing.SwingUtilities.getWindowAncestor;
import javax.swing.UIManager;
import javax.swing.DefaultListModel;

import com.myapp.dieselapp.diesel.Upper;
import java.io.File;


/**
 * 
 * @author 2018 Aman Mambetov "asanbay"@mail.ru
 */
public class WinDiesel extends javax.swing.JFrame {

    private Upper myUpper;
    private IniData iniDataSet, iniDataSetBak;
    public Document dIniDoc;
    private Map <String, String> mUserReqs;     // Login-Password pairs
    DefaultListModel lmActiveTopicsList;
    DefaultListModel lmAvailableTopicsList;
    final private boolean bDebug;
    
//    private String[][] aUlinks;             // array[topic id][title of the topic]      {"288909487", "Hard disks"}
    Map<String, String> mLinks;


    // Creates new form winDiesel
    public WinDiesel() {
        initComponents();
        lmActiveTopicsList = (DefaultListModel) activeTopicsList.getModel();
        lmAvailableTopicsList = (DefaultListModel) availableTopicsList.getModel();
        mLinks = new HashMap<>();       // Hash for links to user topics: title-topic id

        myUpper = new Upper();
        // Read ini-file
        try {
            this.dIniDoc = myUpper.ReadIni("Dapp.xml");
        } catch (IOException ex) {
            log.log(Level.SEVERE, "Problem with the Dapp.xml file", ex);
        }
        // Create ini-data set
        iniDataSet = new IniData(dIniDoc);
        iniDataSetBak = new IniData(dIniDoc);     // For backup this xml data
        initComboBox();
        
        // Get accounts info to a hash
        mUserReqs = new HashMap<>();
        Elements eAccs = this.dIniDoc.select("Account");
        eAccs.forEach((_item) -> {
            mUserReqs.put(_item.attr("Login"), _item.attr("Password"));
        });

        String sStr = dIniDoc.select("System DebugFlag").text();
        bDebug = sStr.equals("1");

    }   // End of Constructor
    
    private void initComboBox () {
        
        Elements eAccs = this.dIniDoc.select("Account");
        loginComboBox.removeAllItems();
        eAccs.forEach((_item) -> {
            loginComboBox.addItem(_item.attr("Login"));
        });
    }
    
    private void initList() {
        // Fill List with topic titles
        String sQry = "Account[Login=" + loginComboBox.getSelectedItem() + "] IdOfTopic";
        Elements eAccs = dIniDoc.select(sQry);
        
        eAccs.forEach((_item) -> {
            lmActiveTopicsList.addElement(_item.attr("Title"));
        });

    }

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        activeTopicsList = new javax.swing.JList<>();
        activeTopicsList.setModel(new DefaultListModel());
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        addLoginBttn = new javax.swing.JButton();
        deleteLoginBttn = new javax.swing.JButton();
        loginComboBox = new javax.swing.JComboBox<>();
        doUpBttn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cancelBttn = new javax.swing.JButton();
        showListTopicBttn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        availableTopicsList = new javax.swing.JList<>();
        availableTopicsList.setModel(new DefaultListModel());
        deleteTopcBttn = new javax.swing.JButton();
        okButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Работа со списком тем");
        setBounds(new java.awt.Rectangle(300, 100, 0, 0));
        setPreferredSize(new java.awt.Dimension(650, 450));

        activeTopicsList.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        activeTopicsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                activeTopicsListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(activeTopicsList);

        jLabel1.setText("Выберите имя пользователя");

        jLabel2.setText("Список активных тем");

        addLoginBttn.setText("Добавить");
        addLoginBttn.setPreferredSize(new java.awt.Dimension(90, 23));
        addLoginBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addLoginBttnActionPerformed(evt);
            }
        });

        deleteLoginBttn.setText("Удалить");
        deleteLoginBttn.setPreferredSize(new java.awt.Dimension(90, 23));
        deleteLoginBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteLoginBttnActionPerformed(evt);
            }
        });

        loginComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                loginComboBoxItemStateChanged(evt);
            }
        });

        doUpBttn.setText("Апать выбранную тему");
        doUpBttn.setEnabled(false);
        doUpBttn.setPreferredSize(new java.awt.Dimension(155, 23));
        doUpBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doUpBttnActionPerformed(evt);
            }
        });

        jLabel3.setText("Доступные темы для добавления (показаны темы только за сегодняшнее число)");

        cancelBttn.setText("Отмена");
        cancelBttn.setMaximumSize(new java.awt.Dimension(90, 23));
        cancelBttn.setMinimumSize(new java.awt.Dimension(90, 23));
        cancelBttn.setPreferredSize(new java.awt.Dimension(90, 23));
        cancelBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBttnActionPerformed(evt);
            }
        });

        showListTopicBttn.setText("Показать список тем");
        showListTopicBttn.setPreferredSize(new java.awt.Dimension(155, 23));
        showListTopicBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showListTopicBttnActionPerformed(evt);
            }
        });

        availableTopicsList.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        availableTopicsList.setEnabled(false);
        jScrollPane2.setViewportView(availableTopicsList);

        deleteTopcBttn.setText("Удалить из списка");
        deleteTopcBttn.setEnabled(false);
        deleteTopcBttn.setPreferredSize(new java.awt.Dimension(155, 23));
        deleteTopcBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteTopcBttnActionPerformed(evt);
            }
        });

        okButton.setText("Сохранить");
        okButton.setMaximumSize(new java.awt.Dimension(90, 23));
        okButton.setMinimumSize(new java.awt.Dimension(90, 23));
        okButton.setPreferredSize(new java.awt.Dimension(90, 23));
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGap(65, 65, 65)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(loginComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(addLoginBttn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(18, 18, 18)
                                    .addComponent(deleteLoginBttn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(showListTopicBttn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(deleteTopcBttn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(81, 81, 81))))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(doUpBttn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(cancelBttn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addLoginBttn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteLoginBttn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(showListTopicBttn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteTopcBttn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(doUpBttn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelBttn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
                      

    
    private void deleteLoginBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteLoginBttnActionPerformed
        // 
        JFrame mainFrame = (JFrame) getWindowAncestor(this);
        if (confrmDeleteDlg == null) {
            confrmDeleteDlg = new ConfirmDelete(mainFrame, true);
            confrmDeleteDlg.setLocationRelativeTo(mainFrame);
        }
        confrmDeleteDlg.setVisible(true);
        Boolean bResp = confrmDeleteDlg.getDialogResponse();
        if (bResp) {
            iniDataSet.deleteAccount(loginComboBox.getSelectedItem().toString());
            if (bDebug)
                System.out.format("Deleted login - %s\n", loginComboBox.getSelectedItem().toString());
            loginComboBox.removeItemAt(loginComboBox.getSelectedIndex());
        }

    }//GEN-LAST:event_deleteLoginBttnActionPerformed

    private void cancelBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBttnActionPerformed
        // Кнопка "Завершить"
        System.exit(0);
    }//GEN-LAST:event_cancelBttnActionPerformed

    private void addLoginBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addLoginBttnActionPerformed
        // Кнопка "Добавить логин"
        String[] sDResp;
        JFrame mainFrame = (JFrame) getWindowAncestor(this);
        if (addLgnDlg == null) {
            addLgnDlg = new AddLoginJDialog(mainFrame, true);
            addLgnDlg.setLocationRelativeTo(mainFrame);
        }
        addLgnDlg.setVisible(true);
        sDResp = addLgnDlg.getDialogResponse();     // Returns String[2]
        if ( ! sDResp[0].isEmpty() ) {
            if (bDebug) {
                System.out.format("Added login - %s\n", sDResp[0]);
                System.out.format("Added pwd - %s\n", sDResp[1]);
            }
            loginComboBox.addItem(sDResp[0]);
            iniDataSet.addAccount(sDResp[0], sDResp[1]);
        }
    }//GEN-LAST:event_addLoginBttnActionPerformed

    private void loginComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_loginComboBoxItemStateChanged
        // 
        lmActiveTopicsList.clear();
        initList();
    }//GEN-LAST:event_loginComboBoxItemStateChanged
    
    private void showListTopicBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showListTopicBttnActionPerformed
        // Кнопка "Показать список тем"
        String sUserN = loginComboBox.getSelectedItem().toString();
        String sPass = mUserReqs.get(sUserN);       // Берём соответствующий пароль из Map
        
        if (bDebug) log.log(Level.INFO, "UserN {0}", sUserN);
        
        // Заполняем список "Доступные темы для добавления" (второй список на экране)
        try {
            mLinks = getTopics(sUserN, sPass);       // Получаем от форума темы юзера в хеш <topic id, title>
        } catch (IOException ex) {
            log.log(Level.SEVERE, null, ex);
        }
        
        // Заполняем jList2 
        availableTopicsList.setEnabled(true);
        lmAvailableTopicsList.removeAllElements();
        boolean bExist;
        Enumeration eActTopList;
        for (Entry _entr : mLinks.entrySet()) {
            bExist = false;
            eActTopList = lmActiveTopicsList.elements();
            while (eActTopList.hasMoreElements()) {
                if (eActTopList.nextElement().equals(_entr.getValue())) {
                    bExist = true;       // This title exist in the ActiveTopicsList
                    break;
                }
            }
            if (!bExist)    // If the title exist in the ActiveTopicsList then skip it
                lmAvailableTopicsList.addElement(_entr.getValue());
        }
        showListTopicBttn.setEnabled(false);
        doUpBttn.setEnabled(true);      // "Апать выбранную тему" button
    }//GEN-LAST:event_showListTopicBttnActionPerformed
    
    private void doUpBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doUpBttnActionPerformed
        // "Апать выбранную тему" button (Добавить с список апающихся тем)
        if (mLinks.isEmpty()) {
            log.info("HashMap is empty");
            return;
        }
        int idx = availableTopicsList.getSelectedIndex();
        Object oSelectedTitle = lmAvailableTopicsList.get(idx);     // Title of selected topic
        
        if (idx >=0) {
            // Переписываем выбранный топик из нижнего списка в активный
            lmActiveTopicsList.insertElementAt(oSelectedTitle, 0);
            activeTopicsList.setSelectedIndex(0);
            lmAvailableTopicsList.removeElementAt(idx);
            // Добавляем выбранный топик в ini-файл
            String sKey = getHashKey(oSelectedTitle);       // Get id of selected topic 
            iniDataSet.addTopic(loginComboBox.getSelectedItem().toString(), sKey, oSelectedTitle.toString());
        }
        showListTopicBttn.setEnabled(true);
        if (lmAvailableTopicsList.isEmpty()) {
            doUpBttn.setEnabled(false);
        }
    }//GEN-LAST:event_doUpBttnActionPerformed

    private void deleteTopcBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteTopcBttnActionPerformed
        // Кнопка "Удалить из списка"
        // Метод удаляет id выбранной темы из списка апающихся тем 
        int idx = activeTopicsList.getSelectedIndex();
        Object oSelectedTitle = lmActiveTopicsList.get(idx);
        
        iniDataSet.deleteTopicByTitle(oSelectedTitle.toString());       // Удалить выбранную тему из xml-файла
        
        if (idx >=0) {
            lmActiveTopicsList.removeElementAt(idx);
        }
        deleteTopcBttn.setEnabled(false);
    }//GEN-LAST:event_deleteTopcBttnActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        // Кнопка Сохранить
        myUpper.FileWrite(iniDataSetBak.getData(), "Dapp.xml.bak");
        myUpper.FileWrite(iniDataSet.getData(), "Dapp.xml");
    }//GEN-LAST:event_okButtonActionPerformed

    private void activeTopicsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_activeTopicsListValueChanged
        // TODO add your handling code here:
        deleteTopcBttn.setEnabled(true);
    }//GEN-LAST:event_activeTopicsListValueChanged

    private String getHashKey (Object oGivenValue) {
        // Get hash key by value
        String sKey = "";
        if (oGivenValue != null) {
            for(Entry<String, String> ent : mLinks.entrySet()) {
                if (oGivenValue.equals(ent.getValue())) {
                    sKey = ent.getKey();
                }
            }
        }
        if (sKey.isEmpty()) {
            log.info("Error: key not found!");
        }
        return sKey;
    }
    
    public Map<String,String> getTopics (String sUser, String sPass) throws IOException {
        /*
        * Метод получает темы пользователя за вчера и сегодня в список
        * возвращает Map<ссылка, Заголовок ссылки>
        */
        Map<String, String> mUserLinks = new HashMap<>();
/*        
        if (!myUpper.login(sUser, sPass)) {
            return mUserLinks;
        }
        // Open index page to find user's home page
        Document dDoc = Jsoup.connect("http://diesel.elcat.kg/").cookies(myUpper.mCookies).get();
        Element eUserHomePageLink = dDoc.select("a #user_link").first();
        if (eUserHomePageLink == null) {
            log.info("The link to user home page not found. Exit.");
            return mUserLinks;
        }
        String sUserHPlink = eUserHomePageLink.attr("href");    // Ссылка на домашнюю страницу юзера "http://diesel.elcat.kg/index.php?showuser=68793"
        sUserHPlink +="&tab=topics";
        myUpper.myDelay(5);
        
        dDoc = Jsoup.connect(sUserHPlink).cookies(myUpper.mCookies).get();
*/

        File fInput = new File("themes.htm");
        if ( !fInput.exists()) {
            log.log(Level.SEVERE, "File not found");
            mUserLinks.put("000000", "Error. File not found!");
        }
        Document dDoc = Jsoup.parse(fInput, "UTF-8");


        Elements eUserTopicList = dDoc.select("div .post_block");   // List of topics
        if (eUserTopicList == null) {
            log.info("The list of topics is empty. Exit.");
            return mUserLinks;
        }
        String sDateTimeStamp;
        Element eDesc;
        Element eLink;      // href link to topic: <a href="http://diesel.elcat.kg/index.php?showtopic=291425253&amp;view=findpost&amp;p=316534145" title="Просмотр темы">Буду благодарен за клетку для морских свинок. </a>
        String sTopicId;
        for (Element _eOneEl : eUserTopicList) {
            sDateTimeStamp = _eOneEl.select("p .posted_info").text();
//                if (sDateTimeStamp.matches("^\\t[^\\d].+")) {     // We dont need old posts: accept only |^Сегодня, ^Вчера...|
                    eLink = _eOneEl.select("a").first();    // To get date of the post, we dont need old posts, today only
                    sTopicId = eLink.attr("href");
                    sTopicId = sTopicId.replaceFirst(".+showtopic=(\\d+)&?.*", "$1");
                    System.out.format("%s\n", eLink.text());
                    System.out.format("sTopicId %s\n", sTopicId);
                    mUserLinks.put(sTopicId, eLink.text());       // mUserLinks map consist pairs <topic id, title>
//                }
        }

        if (mUserLinks.isEmpty()) {
            myUpper.FileWrite(dDoc.toString(), "getT-out.htm");
            log.log(Level.INFO, "User links hash is empty");
            mUserLinks.put("000000", "Warning. User links hash is empty");
        }
    return mUserLinks;
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> activeTopicsList;
    private javax.swing.JButton addLoginBttn;
    private javax.swing.JList<String> availableTopicsList;
    private javax.swing.JButton cancelBttn;
    private javax.swing.JButton deleteLoginBttn;
    private javax.swing.JButton deleteTopcBttn;
    private javax.swing.JButton doUpBttn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> loginComboBox;
    private javax.swing.JButton okButton;
    private javax.swing.JButton showListTopicBttn;
    // End of variables declaration//GEN-END:variables

    
    public static final Logger log = Logger.getLogger(WinDiesel.class.getName());
    
    private AddLoginJDialog addLgnDlg;
    private ConfirmDelete confrmDeleteDlg;
}

