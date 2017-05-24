import java.util.*;
import java.io.*;

/**
* updateOffDiagonalElementsForEMUntieVariances.java process the data for GMTK 
*
* author: Jie Liu
* contact: liu6@uw.edu
*
*/

public class updateOffDiagonalElementsForEMUntieVariances {
	  private String inputDataFileName ;
	  private String outputFileName ;
	  private String tempParameterFileName ;
	  
    private int biopsyNum ;
    private int cloneNum ;
    private int gNum = 12 ; // default to be 12, corresponding to 12 genotypes
    private int zNum ;
    private int [] prevalenceLevels ;
    private double [] g_var_est ;
    private double [] z_var_est ;
    
    private int numberOfSites ;
    private String [] chr ;
    private int [] bp ;
            
    public updateOffDiagonalElementsForEMUntieVariances (String tempf, String dataf, String outf, int biopsyNumInput, int cloneNumInput, int genotypeNumInput, int zNumInput, int [] prevLevels) throws java.io.IOException {
    	  tempParameterFileName = tempf ;
    	  inputDataFileName = dataf ;
    	  outputFileName = outf ;
    	  biopsyNum = biopsyNumInput ;
    	  cloneNum = cloneNumInput ;
    	  gNum = genotypeNumInput ;
    	  zNum = zNumInput ;
    	  prevalenceLevels = new int [cloneNum] ;
    	  g_var_est = new double [gNum] ;
        z_var_est = new double [zNum] ;
    	  for (int i = 0 ; i < cloneNum ; i ++) {
    	      prevalenceLevels [i] = prevLevels [i] ;
    	  }
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
        	      output.print (" " + Math.log(((gNum-1)*1.0)/(gNum*peakDensityG [j]*1.0))) ;
        	  }
        	  for (int j = 0 ; j < zNum ; j ++ ) {
        	      peakDensityZ [j] = getPeakNormDensity (z_var_est[j]) ;
        	      output.print (" " + Math.log(((zNum-1)*1.0)/(zNum*peakDensityZ [j]*1.0))) ;
        	  }
        	  
            for (int j = 0 ; j < gNum ; j ++ ) {
                double rho_g = (getNormalPDF(Math.log(distance), 0.0, g_var_est [j])/peakDensityG [j])*((gNum-1)*1.0/gNum*1.0) + 1.0/(gNum*1.0) ;
                double OffDiagonalG = (1.0-rho_g)/(gNum*1.0-1.0) ;
        	      output.print (" "+OffDiagonalG) ;
        	  }
        	  for (int j = 0 ; j < zNum ; j ++ ) {
                double rho_z = (getNormalPDF(Math.log(distance), 0.0, z_var_est [j])/peakDensityZ [j])*((zNum-1)*1.0/zNum*1.0) + 1.0/(zNum*1.0) ;
                double OffDiagonalZ = (1.0-rho_z)/(zNum*1.0-1.0) ;
        	      output.print (" "+OffDiagonalZ) ;
        	  }
        	  for (int j = 0 ; j < biopsyNum*2+1 ; j++) {
        	      output.print (" "+ tokens [j+2]) ;
        	  }
        	  if (currentChr.equals(previousChr)) {
        	      output.print (" 1") ;
        	  } else {
        	      output.print (" 0") ;
        	  }
        	  for (int k = 0 ; k < cloneNum ; k ++) {
    	          output.print (" " + prevalenceLevels [k]) ;
    	      }
    	      output.println () ;
        	  previousChr = currentChr ;
        	  previousPos = currentPos ;
        }
        output.close () ;
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
        if (args.length != 8) {
            System.err.println("Usage:  java updateOffDiagonalElementsForEMUntieVariances tempParaFileName dataFileName tempDataFileName biopsyNum subcloneNum genotypeNum zNum prevalences"); 
            System.exit(-1); 
        }
        Integer biopsyNumInt = new Integer (args [3]) ;
        Integer cloneNumInt = new Integer (args [4]) ;
        Integer genotypeNumInt = new Integer (args [5]) ;
        Integer zNumInt = new Integer (args [6]) ;
        String [] tokens = args [7].split("~", -1) ;
        int [] ps = new int [cloneNumInt.intValue()] ;
        for (int k = 0 ; k < cloneNumInt.intValue() ; k ++) {
            Integer pInt = new Integer (tokens[k]) ;
            ps [k] = pInt.intValue () ;
        }
        updateOffDiagonalElementsForEMUntieVariances my = new updateOffDiagonalElementsForEMUntieVariances (args[0], args[1], args[2], biopsyNumInt.intValue(), cloneNumInt.intValue(), genotypeNumInt.intValue(), zNumInt.intValue(), ps) ;
        my.getCurrentEstimatedParameters () ;
        my.readInPositionInfo () ;
        my.printDataFiles () ;
    }
    
}
