import java.util.*;
import java.io.*;

/**
* generatingGausianMeansAL5Copies.java generate Gaussian means for different P, G
* 
* author: Jie Liu
* contact: liu6@uw.edu
*/

public class generatingGausianMeansAL5Copies {
    
    private int numberOfGenotypes ;    
    private int numberOfPrevalenceLevels ;    
    private int totalNum ;
    private int numberOfBiopsies = 3 ;
    private double precision = 0.05 ;
    private double [] c_para ;
    
    private String gaussianMeanFileNameA_D0 = "meansA_D0.txt" ;
    private String mcFileNameA_D0 ;
    private String mixtureFileNameA_D0 ;
    private String collectionFileNameA_D0 ;
    
    private String gaussianMeanFileNameA_D1 = "meansA_D1.txt" ;
    private String mcFileNameA_D1 ;
    private String mixtureFileNameA_D1 ;
    private String collectionFileNameA_D1 ;
    
    private String gaussianMeanFileNameL ;
    private String mcFileNameL ;
    private String mixtureFileNameL ;
    private String collectionFileNameL ;
    
    private String modelDirectory ;
        
    public generatingGausianMeansAL5Copies (String dir, int num1, int num2, double pre, int num3, double [] cvalues) {
    	  modelDirectory = dir ;
    	  numberOfGenotypes = num1 ;
    	  numberOfPrevalenceLevels = num2 ;
    	  numberOfBiopsies = num3 ;
    	  totalNum = numberOfGenotypes*numberOfPrevalenceLevels ;
    	  precision = pre ;
    	  c_para = new double [numberOfBiopsies] ;
    	  for (int i = 0 ; i < numberOfBiopsies ; i ++ ) {
    	      c_para [i] = cvalues [i] ;
    	  }
    }
    
    public void generatingGaussianMeanFileA_D0 () throws java.io.IOException {
        PrintStream output = new PrintStream(new FileOutputStream(modelDirectory + "/" + gaussianMeanFileNameA_D0)) ;
        output.println (totalNum+"") ;
        int counter = 0 ;
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {  // for genotype --
    	      double prevalence = precision*(i+1) ;
    	      double mean = 0.5 ;
    	      output.println (counter + " meanA_D0_" + counter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {  // for genotype -B
    	      double prevalence = precision*(i+1) ;
    	      double mean = (1-prevalence)/(2-prevalence) ;
    	      output.println (counter + " meanA_D0_" + counter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {  // for genotype AB
    	      double mean = 0.5 ;
    	      output.println (counter + " meanA_D0_" + counter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {  // for genotype BB
    	      double prevalence = precision*(i+1) ;
    	      double mean = (1-prevalence)/2 ;
    	      output.println (counter + " meanA_D0_" + counter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {  // for genotype ABB
    	      double prevalence = precision*(i+1) ;
    	      double mean = 1/(2+prevalence) ;
    	      output.println (counter + " meanA_D0_" + counter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {  // for genotype BBB
    	      double prevalence = precision*(i+1) ;
    	      double mean = (1-prevalence)/(2+prevalence) ;
    	      output.println (counter + " meanA_D0_" + counter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {  // for genotype ABBB
    	      double prevalence = precision*(i+1) ;
    	      double mean = 1.0/(2+2*prevalence) ;
    	      output.println (counter + " meanA_D0_" + counter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {  // for genotype AABB
    	      double prevalence = precision*(i+1) ;
    	      double mean = 0.5 ;
    	      output.println (counter + " meanA_D0_" + counter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {  // for genotype BBBB
    	      double prevalence = precision*(i+1) ;
    	      double mean = (1-prevalence)/(2+2*prevalence) ;
    	      output.println (counter + " meanA_D0_" + counter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {  // for genotype ABBBB
    	      double prevalence = precision*(i+1) ;
    	      double mean = 1.0/(2+3*prevalence) ;
    	      output.println (counter + " meanA_D0_" + counter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {  // for genotype AABBB
    	      double prevalence = precision*(i+1) ;
    	      double mean = (1+prevalence)/(2+3*prevalence) ;
    	      output.println (counter + " meanA_D0_" + counter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {  // for genotype BBBBB
    	      double prevalence = precision*(i+1) ;
    	      double mean = (1-prevalence)/(2+3*prevalence) ;
    	      output.println (counter + " meanA_D0_" + counter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  output.close () ;
    }
    
    public void generatingMCFileA_D0 () throws java.io.IOException {
    	  for (int k = 0 ; k < numberOfBiopsies ; k ++ ) {
    	    	String biopsyLabel = k+1 + "" ;
    	    	mcFileNameA_D0 = "mcA"+biopsyLabel+"_D0.txt" ;
    	      PrintStream output = new PrintStream(new FileOutputStream(modelDirectory + "/" + mcFileNameA_D0)) ;
    	      output.println (totalNum+"") ;
            for (int i = 0 ; i < totalNum ; i ++ ) {
                output.println (i + " 1 0 gcA"+biopsyLabel+"_D0_" + i + " meanA_D0_" +i + " covarA"+biopsyLabel+"_D0") ;
            }
            output.close () ;
        }
    }
    
    public void generatingMixtureFileA_D0 () throws java.io.IOException {
    	  for (int k = 0 ; k < numberOfBiopsies ; k ++ ) {
    	    	String biopsyLabel = k+1 + "" ;
    	    	mixtureFileNameA_D0 = "mixtureA"+biopsyLabel+"_D0.txt" ;
    	      PrintStream output = new PrintStream(new FileOutputStream(modelDirectory + "/" + mixtureFileNameA_D0)) ;
    	      output.println (totalNum+"") ;
            for (int i = 0 ; i < totalNum ; i ++ ) {
                output.println (i + " 1 mixtureA"+biopsyLabel+"_D0_"+i+" 1 unityDPMF gcA"+biopsyLabel+"_D0_" + i) ;
            }
            output.close () ;
        }
    }
    
    public void generatingCollectionFileA_D0 () throws java.io.IOException {
    	  for (int k = 0 ; k < numberOfBiopsies ; k ++ ) {
    	    	String biopsyLabel = k+1 + "" ;
    	    	collectionFileNameA_D0 = "collectionA"+biopsyLabel+"_D0.txt" ;
    	      PrintStream output = new PrintStream(new FileOutputStream(modelDirectory + "/" + collectionFileNameA_D0)) ;
    	      output.println ("1             ") ;
    	      output.println ("0             ") ;
    	      output.println ("pA"+biopsyLabel+"_given_G_P_D0") ;
    	      output.println (totalNum+"") ;
            for (int i = 0 ; i < totalNum ; i ++ ) {
                output.println ("mixtureA"+biopsyLabel+"_D0_" + i) ;
            }
            output.close () ;
        }
    }
    
    public void generatingGaussianMeanFileA_D1 () throws java.io.IOException {
        PrintStream output = new PrintStream(new FileOutputStream(modelDirectory + "/" + gaussianMeanFileNameA_D1)) ;
        int overall = totalNum + numberOfPrevalenceLevels*4 ; // need to change
    	  output.println (overall+"") ; 
        int counter = 0 ;
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {
    	      double prevalence = precision*(i+1) ;
    	      double mean = Math.log(0.0) ;
    	      output.println (counter + " meanA_D1_" + counter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {
    	      double prevalence = precision*(i+1) ;
    	      double mean = prevalence/(2-prevalence) ;
    	      output.println (counter + " meanA_D1_" + counter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {
    	      double prevalence = precision*(i+1) ;
    	      double mean = 0.5*prevalence ;
    	      output.println (counter + " meanA_D1_" + counter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {
    	      double prevalence = precision*(i+1) ;
    	      double mean = prevalence ;
    	      output.println (counter + " meanA_D1_" + counter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {
    	      double prevalence = precision*(i+1) ;
    	      double mean = prevalence/(2+prevalence) ;
    	      output.println (counter + " meanA_D1_1st_" + counter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {
    	      double prevalence = precision*(i+1) ;
    	      double mean = (2*prevalence)/(2+prevalence) ;
    	      int correctCounter = counter - numberOfPrevalenceLevels ;
    	      output.println (counter + " meanA_D1_2nd_" + correctCounter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {
    	      double prevalence = precision*(i+1) ;
    	      double mean = (3*prevalence)/(2+prevalence) ;
    	      int correctCounter = counter - numberOfPrevalenceLevels ;
    	      output.println (counter + " meanA_D1_" + correctCounter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) { // for genotype ABBB, first component
    	      double prevalence = precision*(i+1) ;
    	      double mean = prevalence/(2+2*prevalence) ;
    	      int correctCounter = counter - numberOfPrevalenceLevels ;
    	      output.println (counter + " meanA_D1_1st_" + correctCounter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) { // for genotype ABBB, second component
    	      double prevalence = precision*(i+1) ;
    	      double mean = (3*prevalence)/(2+2*prevalence) ;
    	      int correctCounter = counter - numberOfPrevalenceLevels*2 ;
    	      output.println (counter + " meanA_D1_2nd_" + correctCounter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {  // for genotype AABB
    	      double prevalence = precision*(i+1) ;
    	      double mean = (2*prevalence)/(2+2*prevalence) ;
    	      int correctCounter = counter - numberOfPrevalenceLevels*2 ;
    	      output.println (counter + " meanA_D1_" + correctCounter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {  // for genotype BBBB
    	      double prevalence = precision*(i+1) ;
    	      double mean = (4*prevalence)/(2+2*prevalence) ;
    	      int correctCounter = counter - numberOfPrevalenceLevels*2 ;
    	      output.println (counter + " meanA_D1_" + correctCounter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) { // for genotype ABBBB, first component
    	      double prevalence = precision*(i+1) ;
    	      double mean = prevalence/(2+3*prevalence) ;
    	      int correctCounter = counter - numberOfPrevalenceLevels*2 ;
    	      output.println (counter + " meanA_D1_1st_" + correctCounter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) { // for genotype ABBBB, second component
    	      double prevalence = precision*(i+1) ;
    	      double mean = (4*prevalence)/(2+3*prevalence) ;
    	      int correctCounter = counter - numberOfPrevalenceLevels*3 ;
    	      output.println (counter + " meanA_D1_2nd_" + correctCounter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) { // for genotype AABBB, first component
    	      double prevalence = precision*(i+1) ;
    	      double mean = (2*prevalence)/(2+3*prevalence) ;
    	      int correctCounter = counter - numberOfPrevalenceLevels*3 ;
    	      output.println (counter + " meanA_D1_1st_" + correctCounter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) { // for genotype AABBB, second component
    	      double prevalence = precision*(i+1) ;
    	      double mean = (3*prevalence)/(2+3*prevalence) ;
    	      int correctCounter = counter - numberOfPrevalenceLevels*4 ;
    	      output.println (counter + " meanA_D1_2nd_" + correctCounter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {  // for genotype BBBBB
    	      double prevalence = precision*(i+1) ;
    	      double mean = (5*prevalence)/(2+3*prevalence) ;
    	      int correctCounter = counter - numberOfPrevalenceLevels*4 ;
    	      output.println (counter + " meanA_D1_" + correctCounter + " 1 " + mean) ;
    	      counter ++ ;
    	  }
    	  output.close () ;
    }
    
    public void generatingMCFileA_D1 () throws java.io.IOException {
    	  for (int k = 0 ; k < numberOfBiopsies ; k ++ ) {
    	    	String biopsyLabel = k+1 + "" ;
    	    	mcFileNameA_D1 = "mcA"+biopsyLabel+"_D1.txt" ;
    	      PrintStream output = new PrintStream(new FileOutputStream(modelDirectory + "/" + mcFileNameA_D1)) ;
    	      int overall = totalNum + numberOfPrevalenceLevels*4 ; // need to change
    	      output.println (overall+"") ; 
    	      int counter = 0 ;
            for (int i = 0 ; i < totalNum-numberOfPrevalenceLevels*8 ; i ++ ) {
                output.println (counter + " 1 0 gcA"+biopsyLabel+"_D1_" + i + " meanA_D1_" +i + " covarA"+biopsyLabel+"_D1") ;
                counter ++ ;
            }
            for (int i = totalNum-numberOfPrevalenceLevels*8 ; i < totalNum-numberOfPrevalenceLevels*7 ; i ++ ) {
                output.println (counter + " 1 0 gcA"+biopsyLabel+"_D1_1st_" + i + " meanA_D1_1st_" +i + " covarA"+biopsyLabel+"_D1") ;
                counter ++ ;
            }
            for (int i = totalNum-numberOfPrevalenceLevels*8 ; i < totalNum-numberOfPrevalenceLevels*7 ; i ++ ) {
                output.println (counter + " 1 0 gcA"+biopsyLabel+"_D1_2nd_" + i + " meanA_D1_2nd_" +i + " covarA"+biopsyLabel+"_D1") ;
                counter ++ ;
            }
            for (int i = totalNum-numberOfPrevalenceLevels*7 ; i < totalNum-numberOfPrevalenceLevels*6 ; i ++ ) {
                output.println (counter + " 1 0 gcA"+biopsyLabel+"_D1_" + i + " meanA_D1_" +i + " covarA"+biopsyLabel+"_D1") ;
                counter ++ ;
            }
            for (int i = totalNum-numberOfPrevalenceLevels*6 ; i < totalNum-numberOfPrevalenceLevels*5 ; i ++ ) {
                output.println (counter + " 1 0 gcA"+biopsyLabel+"_D1_1st_" + i + " meanA_D1_1st_" +i + " covarA"+biopsyLabel+"_D1") ;
                counter ++ ;
            }
            for (int i = totalNum-numberOfPrevalenceLevels*6 ; i < totalNum-numberOfPrevalenceLevels*5 ; i ++ ) {
                output.println (counter + " 1 0 gcA"+biopsyLabel+"_D1_2nd_" + i + " meanA_D1_2nd_" +i + " covarA"+biopsyLabel+"_D1") ;
                counter ++ ;
            }
            for (int i = totalNum-numberOfPrevalenceLevels*5 ; i < totalNum-numberOfPrevalenceLevels*3 ; i ++ ) {
                output.println (counter + " 1 0 gcA"+biopsyLabel+"_D1_" + i + " meanA_D1_" +i + " covarA"+biopsyLabel+"_D1") ;
                counter ++ ;
            }
            for (int i = totalNum-numberOfPrevalenceLevels*3 ; i < totalNum-numberOfPrevalenceLevels*2 ; i ++ ) {
                output.println (counter + " 1 0 gcA"+biopsyLabel+"_D1_1st_" + i + " meanA_D1_1st_" +i + " covarA"+biopsyLabel+"_D1") ;
                counter ++ ;
            }
            for (int i = totalNum-numberOfPrevalenceLevels*3 ; i < totalNum-numberOfPrevalenceLevels*2 ; i ++ ) {
                output.println (counter + " 1 0 gcA"+biopsyLabel+"_D1_2nd_" + i + " meanA_D1_2nd_" +i + " covarA"+biopsyLabel+"_D1") ;
                counter ++ ;
            }
            for (int i = totalNum-numberOfPrevalenceLevels*2 ; i < totalNum-numberOfPrevalenceLevels*1 ; i ++ ) {
                output.println (counter + " 1 0 gcA"+biopsyLabel+"_D1_1st_" + i + " meanA_D1_1st_" +i + " covarA"+biopsyLabel+"_D1") ;
                counter ++ ;
            }
            for (int i = totalNum-numberOfPrevalenceLevels*2 ; i < totalNum-numberOfPrevalenceLevels*1 ; i ++ ) {
                output.println (counter + " 1 0 gcA"+biopsyLabel+"_D1_2nd_" + i + " meanA_D1_2nd_" +i + " covarA"+biopsyLabel+"_D1") ;
                counter ++ ;
            }
            for (int i = totalNum-numberOfPrevalenceLevels*1 ; i < totalNum-numberOfPrevalenceLevels*0 ; i ++ ) {
                output.println (counter + " 1 0 gcA"+biopsyLabel+"_D1_" + i + " meanA_D1_" +i + " covarA"+biopsyLabel+"_D1") ;
                counter ++ ;
            }
            output.close () ;
        }
    }
    
    public void generatingMixtureFileA_D1 () throws java.io.IOException {
    	  for (int k = 0 ; k < numberOfBiopsies ; k ++ ) {
    	    	String biopsyLabel = k+1 + "" ;
    	    	mixtureFileNameA_D1 = "mixtureA"+biopsyLabel+"_D1.txt" ;
    	      PrintStream output = new PrintStream(new FileOutputStream(modelDirectory + "/" + mixtureFileNameA_D1)) ;
    	      output.println (totalNum+"") ;
            for (int i = 0 ; i < totalNum-numberOfPrevalenceLevels*8 ; i ++ ) {
                output.println (i + " 1 mixtureA"+biopsyLabel+"_D1_"+i+" 1 unityDPMF gcA"+biopsyLabel+"_D1_" + i) ;
            }
            for (int i = totalNum-numberOfPrevalenceLevels*8 ; i < totalNum-numberOfPrevalenceLevels*7 ; i ++ ) {
                output.println (i + " 1 mixtureA"+biopsyLabel+"_D1_"+i+" 2 uniform_2d_DPMF gcA"+biopsyLabel+"_D1_1st_" + i +" gcA"+biopsyLabel+"_D1_2nd_" + i) ;
            }
            for (int i = totalNum-numberOfPrevalenceLevels*7 ; i < totalNum-numberOfPrevalenceLevels*6 ; i ++ ) {
                output.println (i + " 1 mixtureA"+biopsyLabel+"_D1_"+i+" 1 unityDPMF gcA"+biopsyLabel+"_D1_" + i) ;
            }
            for (int i = totalNum-numberOfPrevalenceLevels*6 ; i < totalNum-numberOfPrevalenceLevels*5 ; i ++ ) {
                output.println (i + " 1 mixtureA"+biopsyLabel+"_D1_"+i+" 2 uniform_2d_DPMF gcA"+biopsyLabel+"_D1_1st_" + i +" gcA"+biopsyLabel+"_D1_2nd_" + i) ;
            }
            for (int i = totalNum-numberOfPrevalenceLevels*5 ; i < totalNum-numberOfPrevalenceLevels*3 ; i ++ ) {
                output.println (i + " 1 mixtureA"+biopsyLabel+"_D1_"+i+" 1 unityDPMF gcA"+biopsyLabel+"_D1_" + i) ;
            }
            for (int i = totalNum-numberOfPrevalenceLevels*3 ; i < totalNum-numberOfPrevalenceLevels*1 ; i ++ ) {
                output.println (i + " 1 mixtureA"+biopsyLabel+"_D1_"+i+" 2 uniform_2d_DPMF gcA"+biopsyLabel+"_D1_1st_" + i +" gcA"+biopsyLabel+"_D1_2nd_" + i) ;
            }
            for (int i = totalNum-numberOfPrevalenceLevels*1 ; i < totalNum-numberOfPrevalenceLevels*0 ; i ++ ) {
                output.println (i + " 1 mixtureA"+biopsyLabel+"_D1_"+i+" 1 unityDPMF gcA"+biopsyLabel+"_D1_" + i) ;
            }
            output.close () ;
        }
    }
    
    public void generatingCollectionFileA_D1 () throws java.io.IOException {
    	  for (int k = 0 ; k < numberOfBiopsies ; k ++ ) {
    	    	String biopsyLabel = k+1 + "" ;
    	    	collectionFileNameA_D1 = "collectionA"+biopsyLabel+"_D1.txt" ;
    	      PrintStream output = new PrintStream(new FileOutputStream(modelDirectory + "/" + collectionFileNameA_D1)) ;
    	      output.println ("1             ") ;
    	      output.println ("0             ") ;
    	      output.println ("pA"+biopsyLabel+"_given_G_P_D1") ;
    	      output.println (totalNum+"") ;
            for (int i = 0 ; i < totalNum ; i ++ ) {
                output.println ("mixtureA"+biopsyLabel+"_D1_" + i) ;
            }
            output.close () ;
        }  
    }
    
    public void generatingGaussianMeanFileL () throws java.io.IOException {
    	  for (int k = 0 ; k < numberOfBiopsies ; k ++ ) {
    	    	String biopsyLabel = k+1 + "" ;
    	    	gaussianMeanFileNameL = "meansL"+biopsyLabel+".txt" ;
            PrintStream output = new PrintStream(new FileOutputStream(modelDirectory + "/" + gaussianMeanFileNameL)) ;
            output.println (totalNum+"") ;
            int counter = 0 ;
    	      for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {
    	          double prevalence = precision*(i+1) ;
    	          double mean = Math.log(1 - prevalence)/Math.log(2) + c_para [k] ; 
    	          output.println (counter + " meanL"+biopsyLabel+"_" + counter + " 1 " + mean) ;
    	          counter ++ ;
    	      }
    	      for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {
    	          double prevalence = precision*(i+1) ;
    	          double mean = Math.log(1 - 0.5*prevalence)/Math.log(2) + c_para [k] ; 
    	          output.println (counter + " meanL"+biopsyLabel+"_" + counter + " 1 " + mean) ;
    	          counter ++ ;
    	      }
    	      for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {
    	          double mean = c_para [k] ;
    	          output.println (counter + " meanL"+biopsyLabel+"_" + counter + " 1 " + mean) ;
    	          counter ++ ;
    	      }
    	      for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {
    	          double mean = c_para [k] ;
    	          output.println (counter + " meanL"+biopsyLabel+"_" + counter + " 1 " + mean) ;
    	          counter ++ ;
    	      }
    	      for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {
    	          double prevalence = precision*(i+1) ;
    	          double mean = Math.log(1 + 0.5*prevalence)/Math.log(2) + c_para [k] ; 
    	          output.println (counter + " meanL"+biopsyLabel+"_" + counter + " 1 " + mean) ;
    	          counter ++ ;
    	      }
    	      for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {
    	          double prevalence = precision*(i+1) ;
    	          double mean = Math.log(1 + 0.5*prevalence)/Math.log(2) + c_para [k] ; 
    	          output.println (counter + " meanL"+biopsyLabel+"_" + counter + " 1 " + mean) ;
    	          counter ++ ;
    	      }
    	      for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {
    	          double prevalence = precision*(i+1) ;
    	          double mean = Math.log(1 + 1.0*prevalence)/Math.log(2) + c_para [k] ; 
    	          output.println (counter + " meanL"+biopsyLabel+"_" + counter + " 1 " + mean) ;
    	          counter ++ ;
    	      }
    	      for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {
    	          double prevalence = precision*(i+1) ;
    	          double mean = Math.log(1 + 1.0*prevalence)/Math.log(2) + c_para [k] ; 
    	          output.println (counter + " meanL"+biopsyLabel+"_" + counter + " 1 " + mean) ;
    	          counter ++ ;
    	      }
    	      for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {
    	          double prevalence = precision*(i+1) ;
    	          double mean = Math.log(1 + 1.0*prevalence)/Math.log(2) + c_para [k] ; 
    	          output.println (counter + " meanL"+biopsyLabel+"_" + counter + " 1 " + mean) ;
    	          counter ++ ;
    	      }
    	      for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {
    	          double prevalence = precision*(i+1) ;
    	          double mean = Math.log(1 + 1.5*prevalence)/Math.log(2) + c_para [k] ; 
    	          output.println (counter + " meanL"+biopsyLabel+"_" + counter + " 1 " + mean) ;
    	          counter ++ ;
    	      }
    	      for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {
    	          double prevalence = precision*(i+1) ;
    	          double mean = Math.log(1 + 1.5*prevalence)/Math.log(2) + c_para [k] ; 
    	          output.println (counter + " meanL"+biopsyLabel+"_" + counter + " 1 " + mean) ;
    	          counter ++ ;
    	      }
    	      for (int i = 0 ; i < numberOfPrevalenceLevels ; i ++ ) {
    	          double prevalence = precision*(i+1) ;
    	          double mean = Math.log(1 + 1.5*prevalence)/Math.log(2) + c_para [k] ; 
    	          output.println (counter + " meanL"+biopsyLabel+"_" + counter + " 1 " + mean) ;
    	          counter ++ ;
    	      }
    	      output.close () ;
    	  }
    }
    
    public void generatingMCFileL () throws java.io.IOException {
    	  for (int k = 0 ; k < numberOfBiopsies ; k ++ ) {
    	    	String biopsyLabel = k+1 + "" ;
    	    	mcFileNameL = "mcL"+biopsyLabel+".txt" ;
    	      PrintStream output = new PrintStream(new FileOutputStream(modelDirectory + "/" + mcFileNameL)) ;
    	      output.println (totalNum+"") ;
            for (int i = 0 ; i < totalNum ; i ++ ) {
                output.println (i + " 1 0 gcL"+biopsyLabel+"_" + i + " meanL"+biopsyLabel+"_" +i + " covarL"+biopsyLabel+"") ;
            }
            output.close () ;
        }
    }
    
    public void generatingMixtureFileL () throws java.io.IOException {
    	  for (int k = 0 ; k < numberOfBiopsies ; k ++ ) {
    	    	String biopsyLabel = k+1 + "" ;
    	    	mixtureFileNameL = "mixtureL"+biopsyLabel+".txt" ;
    	      PrintStream output = new PrintStream(new FileOutputStream(modelDirectory + "/" + mixtureFileNameL)) ;
    	      output.println (totalNum+"") ;
            for (int i = 0 ; i < totalNum ; i ++ ) {
                output.println (i + " 1 mixtureL"+biopsyLabel+"_"+i+" 1 unityDPMF gcL"+biopsyLabel+"_" + i) ;
            }
            output.close () ;
        }
    }
    
    public void generatingCollectionFileL () throws java.io.IOException {
    	  for (int k = 0 ; k < numberOfBiopsies ; k ++ ) {
    	    	String biopsyLabel = k+1 + "" ;
    	    	collectionFileNameL = "collectionL"+biopsyLabel+".txt" ;
    	      PrintStream output = new PrintStream(new FileOutputStream(modelDirectory + "/" + collectionFileNameL)) ;
    	      output.println ("1             ") ;
    	      output.println ("0             ") ;
    	      output.println ("pL"+biopsyLabel+"_given_G_P") ;
    	      output.println (totalNum+"") ;
            for (int i = 0 ; i < totalNum ; i ++ ) {
                output.println ("mixtureL"+biopsyLabel+"_"  + i) ;
            }
            output.close () ;
        }
    }
        
    public static void main(String args[]) throws java.io.IOException {    	  
        if (args.length != 6 ) {
            System.err.println("Usage:  java generatingGausianMeansAL5Copies dir numberOfGenotypes numberOfPrevalenceLevels precision numberOfBiopsies c_parameter_1-...-c_parameter_m"); 
            System.exit(-1); 
        }
        Integer numberOfGenotypesInt = new Integer (args[1]) ;
        Integer numberOfPrevalenceLevelsInt = new Integer (args[2]) ;
        Double  precisionDou = new Double (args[3]) ;
        Integer numberOfBiopsiesInt = new Integer (args[4]) ;
        int nob = numberOfBiopsiesInt.intValue () ;
        String [] tokens = args[5].split("~", -1) ;
        double [] cs = new double [nob] ;
        for (int i = 0 ; i < nob ; i ++ ) {
            Double cDou = new Double (tokens[i]) ;
            cs [i] = cDou.doubleValue () ;
        }
        generatingGausianMeansAL5Copies my = new generatingGausianMeansAL5Copies (args[0], numberOfGenotypesInt.intValue (), numberOfPrevalenceLevelsInt.intValue (), precisionDou.doubleValue (), nob, cs) ;
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
