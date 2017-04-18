import java.util.*;
import java.io.*;

/**
* updateOffDiagonalElementsForEMUntieVariancesIndividualAnalysis.java process the data for GMTK 
*
*/

public class updateOffDiagonalElementsForEMUntieVariancesIndividualAnalysis {
	  private String inputDirectoryName ;
	  private String outputDirectoryName ;
	  private String inputSiteInfoFileName ;
	  private String inputDataFileName ;
	  private String outputFileName ;
	  private String tempParameterFileName ;
	  private String informationFileName ;
	  
	  private int biopsyIndex ;
	  private int AVariableIndex ;
	  private int LVariableIndex ;
	  private int DVariableIndex ;
	  
	  private int numberOfSites ;
    private String [] chr ;
    private int [] bp ;
    
    private int gNum = 12 ;
    private int zNum = 2 ;
    private double [] g_var_est = new double [gNum] ;
    private double [] z_var_est = new double [zNum] ;
        
    public updateOffDiagonalElementsForEMUntieVariancesIndividualAnalysis (int biopsyIndexInput) throws java.io.IOException {
    	  tempParameterFileName = "./em_covar.txt" ;
    	  
    	  biopsyIndex = biopsyIndexInput ;
    	  AVariableIndex = biopsyIndex + 2 ;
        LVariableIndex = biopsyIndex + 5 ;
        DVariableIndex = 8 ;
    	  
    	  String [] biopsyTags = new String [3] ;
    	  biopsyTags [0] = "01-1-B2" ; 
    	  biopsyTags [1] = "01-2-B2" ; 
    	  biopsyTags [2] = "01-4-B3" ; 
    	  inputDataFileName = "/net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/data/itomic/subject1/bams/combinedData_01-1-B2_01-2-B2_01-4-B3.txt" ;
    	  outputFileName = "trainWithLogSiteDistance_"+biopsyTags[biopsyIndex]+".txt" ;
    }
        
    public void getCurrentEstimatedParameters () throws java.io.IOException {
        RandomAccessFile accessFile = new RandomAccessFile (tempParameterFileName, "r") ; 
        for (int i = 0 ; i < gNum ; i ++ ) {
            Double g_var_Dou = new Double (accessFile.readLine()) ;
            g_var_est [i] = g_var_Dou.doubleValue () ;
        }
        for (int i = 0 ; i < zNum ; i ++ ) {
            Double z_var_Dou = new Double (accessFile.readLine()) ;
            z_var_est [i] = z_var_Dou.doubleValue () ;
        }
        accessFile.close () ;
    }
    
    public void readInPositionInfo () throws java.io.IOException {
        RandomAccessFile accessFile = new RandomAccessFile (inputDataFileName, "r") ; 
        int count = 0 ;
        while (true) {
            String currentLine = accessFile.readLine() ;
            if (currentLine==null) break ;
            count ++ ;
        }
        numberOfSites = count ;
        chr = new String [numberOfSites] ;
        bp = new int [numberOfSites] ;
        System.out.println (numberOfSites + " sites") ;
        accessFile.close () ;
        accessFile = new RandomAccessFile (inputDataFileName, "r") ; 
        for (int i = 0 ; i < numberOfSites ; i ++ ) {
            String currentLine = accessFile.readLine() ;
            String [] tokens = currentLine.split("	", -1) ; 
            chr [i] = tokens [0] ;
            Integer currentPositionInt = new Integer (tokens [1]) ;
            bp [i] = currentPositionInt.intValue () ; 
        }
        accessFile.close () ;
    }
    
    public void printDataFiles () throws java.io.IOException {
    	  RandomAccessFile accessFile = new RandomAccessFile (inputDataFileName , "r") ;
    	  PrintStream output = new PrintStream(new FileOutputStream(outputFileName)) ;
    	  String previousChr = "0" ; ;
        int previousPos = 1999999999 ;
        int startSearchIndex = 0 ;
    	  for (int i = 0 ; i < numberOfSites ; i ++ ) {
    	  	  String currentLine = accessFile.readLine() ;
    	  	  String [] tokens = currentLine.split("	", -1) ; 
    	  	  String currentChr = chr [i] ;
            int currentPos = bp [i] ;
            double distance = 1e100 ;
            if (currentChr.equals(previousChr)) {
            	  distance = (currentPos - previousPos)*1.0 ;
        	  } 
        	  output.print (Math.log(distance)) ;
        	  double [] peakDensityG = new double [gNum] ; 
        	  double [] peakDensityZ = new double [zNum] ; 
        	  for (int j = 0 ; j < gNum ; j ++ ) {
        	      peakDensityG [j] = getPeakNormDensity (g_var_est[j]) ;
        	      // peakDensityG [j] = getPeakNormDensity (1.0) ;
        	      output.print (" " + Math.log(((gNum-1)*1.0)/(gNum*peakDensityG [j]*1.0))) ;
        	  }
        	  for (int j = 0 ; j < zNum ; j ++ ) {
        	      peakDensityZ [j] = getPeakNormDensity (z_var_est[j]) ;
        	      // peakDensityZ [j] = getPeakNormDensity (1.0) ;
        	      output.print (" " + Math.log(((zNum-1)*1.0)/(zNum*peakDensityZ [j]*1.0))) ;
        	  }
        	  
            for (int j = 0 ; j < gNum ; j ++ ) {
                // double x = Math.log(distance)/(Math.sqrt(g_var_est[j])) ;
                double rho_g = (getNormalPDF(Math.log(distance), 0.0, g_var_est [j])/peakDensityG [j])*((gNum-1)*1.0/gNum*1.0) + 1.0/(gNum*1.0) ;
                double OffDiagonalG = (1.0-rho_g)/(gNum*1.0-1.0) ;
        	      output.print (" "+OffDiagonalG) ;
        	  }
        	  for (int j = 0 ; j < zNum ; j ++ ) {
                // double x = Math.log(distance)/(Math.sqrt(z_var_est[j])) ;
                double rho_z = (getNormalPDF(Math.log(distance), 0.0, z_var_est [j])/peakDensityZ [j])*((zNum-1)*1.0/zNum*1.0) + 1.0/(zNum*1.0) ;
                double OffDiagonalZ = (1.0-rho_z)/(zNum*1.0-1.0) ;
        	      output.print (" "+OffDiagonalZ) ;
        	  }
        	  
        	  output.print (" "+ tokens [AVariableIndex] + " " + tokens [LVariableIndex] + " " + tokens [DVariableIndex]) ;
        	  if (currentChr.equals(previousChr)) {
        	      output.println (" 1") ;
        	  } else {
        	      output.println (" 0") ;
        	  }
        	  previousChr = currentChr ;
        	  previousPos = currentPos ;
        }
        output.close () ;
    }
    
    public double getStdNormalPDF (double x) {
        double pdf = 0.0 ;
        pdf = (1.0/(Math.sqrt(2*Math.PI)))*Math.pow(Math.E, (-(x)*(x))/(2.0)) ;
        return pdf ;
    }
    
    public double getNormalPDF (double x, double mu, double sigma2) {
        double pdf = 0.0 ;
        pdf = (1.0/(Math.sqrt(2*Math.PI*sigma2)))*Math.pow(Math.E, (-(x-mu)*(x-mu))/(2.0*sigma2)) ;
        return pdf ;
    }
    
    public double getPeakNormDensity (double sigma2) {
        double pdf = 0.0 ;
        pdf = (1.0/(Math.sqrt(2*Math.PI*sigma2))) ;
        return pdf ;
    }
    
    public static void main(String args[]) throws java.io.IOException {    	  
        if (args.length != 1) {
            System.err.println("Usage:  java updateOffDiagonalElementsForEMUntieVariancesIndividualAnalysis biopsyIndex"); 
            System.exit(-1); 
        }
        Integer biopsyIndexInt = new Integer (args[0]) ;
        updateOffDiagonalElementsForEMUntieVariancesIndividualAnalysis my = new updateOffDiagonalElementsForEMUntieVariancesIndividualAnalysis (biopsyIndexInt.intValue()) ;
        my.getCurrentEstimatedParameters () ;
        my.readInPositionInfo () ;
        my.printDataFiles () ;
    }
    
}
