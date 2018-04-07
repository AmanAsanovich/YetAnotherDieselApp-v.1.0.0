package com.myapp.dieselapp.winDiesel;

import org.jsoup.nodes.Document;

/*
 * Class to manipulate with application settings based on XML
 * Aman Mambetov
 * aman.mambet@gmail.com
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
// Debug        
//        System.out.format("Pattern = %s\n", sRe);
//        System.out.format("%s\n", sDocument);
// Debug        
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
