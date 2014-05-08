package org.agmip.utility.cultivarscanner;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void testApp() {
        App.main("C:\\DSSAT45\\DSSATPRO.v45", "MZ", "CER");
    }
    
    @Test
    public void testApp2() {
        App.main("C:\\DSSAT45\\DSSATPRO.v45", "All");
    }
}
