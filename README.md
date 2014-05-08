cultivarScanner
===============
App.java
  Provide a simple command line UI for user. There are two partern for provding arguments for the UI as follow.
  Partern 1: 
    Provide 1) DSSATPRO file path 2) 2-bit crop id 3) 3-bit model id.
    For example xxx.jar C:/DSSAT45/DSSATPRO.v45 MZ CER
  Partern 2:
    Provide 1) DSSATPRO file path 2) Flag "ALL" for scan all the cultivars (no case sensitivity)
    For example xxx.jar C:/DSSAT45/DSSATPRO.v45 All
    
Scanner.java
  Provide the functionality of scanning the cultivar files in the genotype folder. Could be used as a library to extend the functionality.
  Four scan methods are provided to scan the folder.
    scan(CR, model) 
      Scan the given crop under given model, and output the report in the default path.
    scan(CR, model, outPath)
      Scan the given crop under given model, and output the report in the given path.
    scanAll()
      Scan all the *.cul files in the genotype folder, and output the report in the default path.
    scanAll(outPath)
      Scan all the *.cul files in the genotype folder, and output the report in the given path.
  
