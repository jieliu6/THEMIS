import java.util.*;
import java.io.*;

/**
* generatingGausianMeansAL5CopiesNoCNAs.java generate Gaussian means for different P, G
*
* author: Jie Liu
* contact: liu6@uw.edu*
*/

public class generatingGausianMeansAL5CopiesNoCNAs {
    
    private int numberOfGenotypes ;    
    private int numberOfPrevalenceLevels ;    
    private int totalNum ;
    private int numberOfBiopsies = 3 ;
    private double precision = 0.05 ;
    private double [] c_para ;
    
    private String gaussianMeanFileNameA_D0 = "meansA_D0_noCNAs.txt" ;
    private String mcFileNameA_D0 = "mcA_D0_noCNAs.txt" ;
    private String mixtureFileNameA_D0 = "mixtureA_D0_noCNAs.txt" ;
    private String collectionFileNameA_D0 = "collectionA_D0_noCNAs.txt" ;
    
    private String gaussianMeanFileNameA_D1 = "meansA_D1_noCNAs.txt" ;
    private String mcFileNameA_D1 = "mcA_D1_noCNAs.txt" ;
    private String mixtureFileNameA_D1 = "mixtureA_D1_noCNAs.txt" ;
    private String collectionFileNameA_D1 = "collectionA_D1_noCNAs.txt" ;
    
    private String gaussianMeanFileNameL = "meansL_noCNAs.txt" ;
    private String mcFileNameL = "mcL_noCNAs.txt" ;
    private String mixtureFileNameL = "mixtureL_noCNAs.txt" ;
    private String collectionFileNameL = "collectionL_noCNAs.txt" ;
        
    public generatingGausianMeansAL5CopiesNoCNAs (int num1, int num2, double pre, int num3, double [] cvalues) {
    	  numberOfGenotypes = num1 ;
    	  numberOfPrevalenceLevels = num2 ;
    	  numberOfBiopsies = num3 ;
    	  // totalNum = numberOfGenotypes*numberOfPrevalenceLevels ;
    	  totalNum = 1 ;
    	  precision = pre ;
    	  c_para = new double [numberOfBiopsies] ;
    	  for (int i = 0 ; i < numberOfBiopsies ; i ++ ) {
    	      c_para [i] = cvalues [i] ;
    	  }
    }
    
    public void generatingGaussianMeanFileA_D0 () throws java.io.IOException {
        PrintStream output = new PrintStream(new FileOutputStream(gaussianMeanFileNameA_D0)) ;
        output.println (totalNum+"") ;
        output.println ("0 meanA_D0_0_noCNAs 1 0.5") ;
    	  output.close () ;
    }
    
    public void generatingMCFileA_D0 () throws java.io.IOException {
    	  for (int k = 0 ; k < numberOfBiopsies ; k ++ ) {
    	    	String biopsyLabel = k+1 + "" ;
    	    	mcFileNameA_D0 = "mcA"+biopsyLabel+"_D0_noCNAs.txt" ;
    	      PrintStream output = new PrintStream(new FileOutputStream(mcFileNameA_D0)) ;
    	      output.println (totalNum+"") ;
            for (int i = 0 ; i < totalNum ; i ++ ) {
                output.println (i + " 1 0 gcA"+biopsyLabel+"_D0_" + i + "_noCNAs meanA_D0_" +i + "_noCNAs covarA"+biopsyLabel+"_D0") ;
            }
            output.close () ;
        }
    }
    
    public void generatingMixtureFileA_D0 () throws java.io.IOException {
    	  for (int k = 0 ; k < numberOfBiopsies ; k ++ ) {
    	    	String biopsyLabel = k+1 + "" ;
    	    	mixtureFileNameA_D0 = "mixtureA"+biopsyLabel+"_D0_noCNAs.txt" ;
    	      PrintStream output = new PrintStream(new FileOutputStream(mixtureFileNameA_D0)) ;
    	      output.println (totalNum+"") ;
            for (int i = 0 ; i < totalNum ; i ++ ) {
                output.println (i + " 1 mixtureA"+biopsyLabel+"_D0_"+i+"_noCNAs 1 unityDPMF gcA"+biopsyLabel+"_D0_" + i + "_noCNAs") ;
            }
            output.close () ;
        }
    }
    
    public void generatingCollectionFileA_D0 () throws java.io.IOException {
    	  for (int k = 0 ; k < numberOfBiopsies ; k ++ ) {
    	    	String biopsyLabel = k+1 + "" ;
    	    	collectionFileNameA_D0 = "collectionA"+biopsyLabel+"_D0_noCNAs.txt" ;
    	      PrintStream output = new PrintStream(new FileOutputStream(collectionFileNameA_D0)) ;
    	      output.println ("1             ") ;
    	      output.println ("0             ") ;
    	      output.println ("pA"+biopsyLabel+"_given_G_P_D0_noCNAs") ;
    	      output.println (totalNum+"") ;
            for (int i = 0 ; i < totalNum ; i ++ ) {
                output.println ("mixtureA"+biopsyLabel+"_D0_" + i + "_noCNAs") ;
            }
            output.close () ;
        }
    }
    
    public void generatingGaussianMeanFileA_D1 () throws java.io.IOException {
        PrintStream output = new PrintStream(new FileOutputStream(gaussianMeanFileNameA_D1)) ;
        output.println (totalNum+"") ;
        output.println ("0 meanA_D1_0_noCNAs 1 0.0") ;
    	  output.close () ;
    }
    
    public void generatingMCFileA_D1 () throws java.io.IOException {
    	  for (int k = 0 ; k < numberOfBiopsies ; k ++ ) {
    	    	String biopsyLabel = k+1 + "" ;
    	    	mcFileNameA_D1 = "mcA"+biopsyLabel+"_D1_noCNAs.txt" ;
    	      PrintStream output = new PrintStream(new FileOutputStream(mcFileNameA_D1)) ;
    	      output.println (totalNum+"") ;
            for (int i = 0 ; i < totalNum ; i ++ ) {
                output.println (i + " 1 0 gcA"+biopsyLabel+"_D1_" + i + "_noCNAs meanA_D1_" +i + "_noCNAs covarA"+biopsyLabel+"_D1") ;
            }
            output.close () ;
        }
    }
    
    public void generatingMixtureFileA_D1 () throws java.io.IOException {
    	  for (int k = 0 ; k < numberOfBiopsies ; k ++ ) {
    	    	String biopsyLabel = k+1 + "" ;
    	    	mixtureFileNameA_D1 = "mixtureA"+biopsyLabel+"_D1_noCNAs.txt" ;
    	      PrintStream output = new PrintStream(new FileOutputStream(mixtureFileNameA_D1)) ;
    	      output.println (totalNum+"") ;
            for (int i = 0 ; i < totalNum ; i ++ ) {
                output.println (i + " 1 mixtureA"+biopsyLabel+"_D1_"+i+"_noCNAs 1 unityDPMF gcA"+biopsyLabel+"_D1_" + i + "_noCNAs") ;
            }
            output.close () ;
        }
    }
    
    public void generatingCollectionFileA_D1 () throws java.io.IOException {
    	  for (int k = 0 ; k < numberOfBiopsies ; k ++ ) {
    	    	String biopsyLabel = k+1 + "" ;
    	    	collectionFileNameA_D1 = "collectionA"+biopsyLabel+"_D1_noCNAs.txt" ;
    	      PrintStream output = new PrintStream(new FileOutputStream(collectionFileNameA_D1)) ;
    	      output.println ("1             ") ;
    	      output.println ("0             ") ;
    	      output.println ("pA"+biopsyLabel+"_given_G_P_D1_noCNAs") ;
    	      output.println (totalNum+"") ;
            for (int i = 0 ; i < totalNum ; i ++ ) {
                output.println ("mixtureA"+biopsyLabel+"_D1_" + i + "_noCNAs") ;
            }
            output.close () ;
        }
    }
    
    public void generatingGaussianMeanFileL () throws java.io.IOException {
    	  for (int k = 0 ; k < numberOfBiopsies ; k ++ ) {
    	    	String biopsyLabel = k+1 + "" ;
    	    	gaussianMeanFileNameL = "meansL"+biopsyLabel+"_noCNAs.txt" ;
            PrintStream output = new PrintStream(new FileOutputStream(gaussianMeanFileNameL)) ;
            output.println (totalNum+"") ;
            double mean = c_para [k] ;
    	      output.println ("0 meanL"+biopsyLabel+"_0_noCNAs 1 " + mean) ;
    	      output.close () ;
    	  }
    }
    
    public void generatingMCFileL () throws java.io.IOException {
    	  for (int k = 0 ; k < numberOfBiopsies ; k ++ ) {
    	    	String biopsyLabel = k+1 + "" ;
    	    	mcFileNameL = "mcL"+biopsyLabel+"_noCNAs.txt" ;
    	      PrintStream output = new PrintStream(new FileOutputStream(mcFileNameL)) ;
    	      output.println (totalNum+"") ;
            for (int i = 0 ; i < totalNum ; i ++ ) {
                output.println (i + " 1 0 gcL"+biopsyLabel+"_" + i + "_noCNAs meanL"+biopsyLabel+"_" +i + "_noCNAs covarL"+biopsyLabel+"") ;
            }
            output.close () ;
        }
    }
    
    public void generatingMixtureFileL () throws java.io.IOException {
    	  for (int k = 0 ; k < numberOfBiopsies ; k ++ ) {
    	    	String biopsyLabel = k+1 + "" ;
    	    	mixtureFileNameL = "mixtureL"+biopsyLabel+"_noCNAs.txt" ;
    	      PrintStream output = new PrintStream(new FileOutputStream(mixtureFileNameL)) ;
    	      output.println (totalNum+"") ;
            for (int i = 0 ; i < totalNum ; i ++ ) {
                output.println (i + " 1 mixtureL"+biopsyLabel+"_"+i+"_noCNAs 1 unityDPMF gcL"+biopsyLabel+"_" + i + "_noCNAs") ;
            }
            output.close () ;
        }
    }
    
    public void generatingCollectionFileL () throws java.io.IOException {
    	  for (int k = 0 ; k < numberOfBiopsies ; k ++ ) {
    	    	String biopsyLabel = k+1 + "" ;
    	    	collectionFileNameL = "collectionL"+biopsyLabel+"_noCNAs.txt" ;
    	      PrintStream output = new PrintStream(new FileOutputStream(collectionFileNameL)) ;
    	      output.println ("1             ") ;
    	      output.println ("0             ") ;
    	      output.println ("pL"+biopsyLabel+"_given_G_P_noCNAs") ;
    	      output.println (totalNum+"") ;
            for (int i = 0 ; i < totalNum ; i ++ ) {
                output.println ("mixtureL"+biopsyLabel+"_"  + i + "_noCNAs") ;
            }
            output.close () ;
        }
    }
        
    public static void main(String args[]) throws java.io.IOException {    	  
        if (args.length != 5 ) {
            System.err.println("Usage:  java generatingGausianMeansAL5CopiesNoCNAs numberOfGenotypes numberOfPrevalenceLevels precision numberOfBiopsies c_parameter_1-...-c_parameter_m"); 
            System.exit(-1); 
        }
        Integer numberOfGenotypesInt = new Integer (args[0]) ;
        Integer numberOfPrevalenceLevelsInt = new Integer (args[1]) ;
        Double  precisionDou = new Double (args[2]) ;
        Integer numberOfBiopsiesInt = new Integer (args[3]) ;
        int nob = numberOfBiopsiesInt.intValue () ;
        String [] tokens = args[4].split("~", -1) ;
        double [] cs = new double [nob] ;
        for (int i = 0 ; i < nob ; i ++ ) {
            Double cDou = new Double (tokens[i]) ;
            cs [i] = cDou.doubleValue () ;
        }
        generatingGausianMeansAL5CopiesNoCNAs my = new generatingGausianMeansAL5CopiesNoCNAs (numberOfGenotypesInt.intValue (), numberOfPrevalenceLevelsInt.intValue (), precisionDou.doubleValue (), nob, cs) ;
        my.generatingGaussianMeanFileA_D0 () ;
        my.generatingMCFileA_D0 () ;
        my.generatingMixtureFileA_D0 () ;
        my.generatingCollectionFileA_D0 () ;
        
        my.generatingGaussianMeanFileA_D1 () ;
        my.generatingMCFileA_D1 () ;
        my.generatingMixtureFileA_D1 () ;
        my.generatingCollectionFileA_D1 () ;
        
        my.generatingGaussianMeanFileL () ;
        my.generatingMCFileL () ;
        my.generatingMixtureFileL () ;
        my.generatingCollectionFileL () ;
        
    }
    
}
