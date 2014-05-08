package org.agmip.utility.cultivarscanner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Meng Zhang
 */
public class Scanner {

    private String dssatproPath = null;
    private int version = 46;
    
    public Scanner(String dssatproPath) {
        this.dssatproPath = dssatproPath;
        this.version = Integer.parseInt(dssatproPath.replaceAll(".+\\.v", ""));
    }

    public File scan(String crid, String model) throws FileNotFoundException, IOException, Exception {
        return scan(crid, model, "");
    }

    public File scan(String crid, String model, String outputPath) throws FileNotFoundException, IOException, Exception {
        File output = revisePath(outputPath);
        File culPath = getCulPath();
        File culFile = getCulFile(culPath, crid, model);
        BufferedWriter bw = new BufferedWriter(new FileWriter(output));
        bw.append(readCulInfo(culFile));
        bw.flush();
        bw.close();

        return output;
    }
    
    public File scanAll() throws FileNotFoundException, IOException, Exception {
        return scanAll("");
    }
    
    public File scanAll(String outputPath) throws FileNotFoundException, IOException, Exception {
        File output = revisePath(outputPath);
        File culPath = getCulPath();
        ArrayList<File> culFiles = getCulFiles(culPath);
        BufferedWriter bw = new BufferedWriter(new FileWriter(output));
        for (File culFile : culFiles) {
            bw.append(readCulInfo(culFile));
//            System.out.println(readCulInfo(culFile));
            
        }
        bw.flush();
        bw.close();
        return output;
    }
    
    private File revisePath(String path) {
        Calendar cal = Calendar.getInstance();
        String defFileName = "report_" + cal.getTimeInMillis() + ".txt";
        if (path == null) {
            path = "";
        }
        File output = new File(path);
        if (!path.matches("(.\\\\)*.+\\..+")) {
            if ("".equals(path)) {
                output = new File(defFileName);
            } else if (output.getPath().equals(File.separator)) {
                output = new File(File.separator + defFileName);
            } else {
                output = new File(output.getPath() + File.separator + defFileName);
            }
        }
        return output;
    }
    
    private File getCulPath() throws FileNotFoundException, IOException, Exception {
        String culPath = null;
        BufferedReader br = new BufferedReader(new FileReader(new File(dssatproPath)));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.toUpperCase().startsWith("CRD")) {
                String[] p = line.split(" ");
                if (p.length != 3) {
                    throw new Exception("Unrecognizable line for genotype path in DSSATPRO : [" + line + "]");
                } else {
                    culPath = p[1] + p[2];
                }
                break;
            }
        }
        if (culPath != null) {
            return new File(culPath);
        } else {
            throw new Exception("Could not find genotype path in DSSATPRO");
        }
    }
    
    private File getCulFile(File culPath, String crid, String model) throws Exception {
        String culFileName = String.format("%s%s%03d.CUL", crid, model, version);
        File culFile = new File(culPath.getPath() + File.separator + culFileName);
        if (!culFile.exists()) {
            throw new Exception(culFileName + " could not be found in the genotype directory");
        } else {
            return culFile;
        }
    }
    
    private ArrayList<File> getCulFiles(File culPath) throws Exception {
        ArrayList<File> culFiles = new ArrayList();
        for (File file : culPath.listFiles()) {
            if (file.getName().toUpperCase().endsWith(".CUL")) {
                culFiles.add(file);
            }
        }
        return culFiles;
    }
    
    private String readCulInfo(File culFile) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(culFile));
        StringBuilder sb = new StringBuilder();
        String crid = culFile.getName().substring(0, 2);
        String model = culFile.getName().substring(2, 5);
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.startsWith("!") && !line.startsWith("@") && !line.startsWith("*") && line.trim().length() >= 24) {
                String cul_id = line.substring(0, 7).trim();
                String cul_name = line.substring(7, 25).trim();
//                // debug
//                if (line.substring(0, 24).trim().length() == 25) {
//                    System.out.println(culFile.getName() + "\t" + line);
//                }
                sb.append(String.format("%s\t%s\t%03d\t%s\t%s\r\n", crid, model, version, cul_id, cul_name));
            }
        }
        br.close();
        return sb.toString();
    }
}
