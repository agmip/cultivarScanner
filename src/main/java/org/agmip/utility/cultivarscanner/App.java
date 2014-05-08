package org.agmip.utility.cultivarscanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Meng Zhang
 */
public class App {

    public static void main(String... args) {

        Scanner s;
        boolean scanAll = false;
        if (args.length < 2 || args.length > 3) {
            System.out.println("The number of arguments is incorrect.");
            return;
        } else if (args.length == 2) {
            if ("ALL".equalsIgnoreCase(args[1])) {
                s = new Scanner(args[0]);
                scanAll = true;
            } else {
                System.out.println("Invalid crop id: [" + args[1] + "], or use ALL for all crops");
                return;
            }
        } else {
            s = new Scanner(args[0]);
        }
        System.out.println("Scan following DSSATPRO file : [" + args[0] + "]");

        try {
            File out;
            if (scanAll) {
                System.out.println("Scan all cultivar...");
                out = s.scanAll();
            } else {
                System.out.println("Scan " + args[1] + args[2] + "...");
                out = s.scan(args[1], args[2]);
            }
            if (out != null) {
                System.out.println("Generate report file : [" + out.getName() + "]");
            } else {
                System.out.println("Failed to generate report file!");
            }
            

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
