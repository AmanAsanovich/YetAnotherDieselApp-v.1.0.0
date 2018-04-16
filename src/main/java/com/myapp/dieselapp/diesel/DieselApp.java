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

import java.io.*;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.swing.UIManager;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.myapp.dieselapp.winDiesel.WinDiesel;

/**
 *
 * @author 2018 Aman Mambetov "asanbay"@mail.ru
 */
public class DieselApp {

    protected static Upper myUpper;

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // Parsing the command line
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption( "gui", false, "Run Topic Manager window" );
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "DieselApp [-OPTION]", options );
        
        String logFile = System.getProperty("java.util.logging.config.file");   // java -Djava.util.logging.config.file=<external-logging.properties> -jar MyProjectJar.jar
        if(logFile == null){
                    LogManager.getLogManager().readConfiguration(
                            DieselApp.class.getClassLoader().getResourceAsStream("logging.properties"));
        }                
        
        try {
            // parse the command line arguments
            CommandLine line = parser.parse( options, args );
            if( line.hasOption( "gui" ) ) {
                DieselApp.runGui();
            }
            else {
                myUpper = new Upper();
                myUpper.doIt();
            }
        }
        catch( ParseException exp ) {
            Logger.getLogger(DieselApp.class.getName()).log(Level.SEVERE, null, exp);
        }

    }   // End of Main
    
    private static void runGui() {
        /* Set the Windows look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        // If Windows is not available, stay with the default look and feel.
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            Logger.getLogger(DieselApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WinDiesel().setVisible(true);
            }
        });
    }
}



