/*
 * Copyright 2018 Мен.
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

import org.jsoup.nodes.Document;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Мен
 */
public class UpperTest {
    
    public UpperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of doIt method, of class Upper.
     */
    @Test
    public void testDoIt() throws Exception {
        System.out.println("doIt");
        Upper instance = new Upper();
        instance.doIt();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of login method, of class Upper.
     */
    @Test
    public void testLogin() throws Exception {
        System.out.println("login");
        String sUser = "";
        String sPassw = "";
        Upper instance = new Upper();
        Boolean expResult = null;
        Boolean result = instance.login(sUser, sPassw);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of FileWrite method, of class Upper.
     */
    @Test
    public void testFileWrite() {
        System.out.println("FileWrite");
        String sBuf = "";
        String sFName = "";
        Upper instance = new Upper();
        instance.FileWrite(sBuf, sFName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ReadIni method, of class Upper.
     */
    @Test
    public void testReadIni() throws Exception {
        System.out.println("ReadIni");
        String sFName = "";
        Upper instance = new Upper();
        Document expResult = null;
        Document result = instance.ReadIni(sFName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of myDelay method, of class Upper.
     */
    @Test
    public void testMyDelay() {
        System.out.println("myDelay");
        long interval = 0L;
        Upper instance = new Upper();
        instance.myDelay(interval);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
