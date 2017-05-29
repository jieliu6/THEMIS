import java.util.*;
import java.io.*;

/**
* generatingModelSpecificationFilesIndividualAnalysis.java generates model specification files for individual biopsy analysis within GMTK/THEMIS.
*
* author: Jie Liu
* contact: liu6@uw.edu*
*/

public class generatingModelSpecificationFilesIndividualAnalysis {
    
    private int numberOfClones ; 
    
    private String modelDirectory ;
    private String initCovarFileName = "initCovars.txt" ;
    private String strucutreFileName = "hmm_factorialModel.str" ;
    private String masterFileName = "hmm_factorialModel.mtr" ;
    private String noTrainFileName = "params.notrain" ;
    private String constantFileName = "constants.inc" ;
    private String initParameterFileName = "init_hmm_factorialModel.params" ;
    private String initDpmfFileName = "initDpmfs.txt" ;
        
    public generatingModelSpecificationFilesIndividualAnalysis (String dir, int num1) {
    	  modelDirectory = dir ;
    	  numberOfClones = num1 ;
    }
    
    public void generateInitCovarFile () throws java.io.IOException {
        PrintStream output = new PrintStream(new FileOutputStream(modelDirectory + "/" + initCovarFileName)) ;
        output.println ("3") ;
        output.println ("0") ;
        output.println ("covarA1_D0 1 1.0") ;
        output.println ("1") ;
        output.println ("covarA1_D1 1 1.0") ;
        output.println ("2") ;
        output.println ("covarL1 1 1.0") ;
    	  output.close () ;
    }
    
    public void generateStructureFile () throws java.io.IOException {
        PrintStream output = new PrintStream(new FileOutputStream(modelDirectory + "/" + strucutreFileName)) ;
        output.println ("GRAPHICAL_MODEL hmm_factorialModel") ;
        output.println () ;
        output.println ("#include \"constants.inc\"") ;
        output.println () ;
        output.println ("frame: 0 {") ;
        output.println () ;
        output.println ("variable : G {") ;
        output.println ("type : discrete hidden cardinality G_CARD;") ;
        output.println ("conditionalparents : nil using DenseCPT(\"pG\");") ;
        output.println ("  }") ;
        output.println () ;
        output.println ("variable : Z {") ;
        output.println ("type : discrete hidden cardinality Z_CARD;") ;
        output.println ("conditionalparents : nil using DenseCPT(\"pZ\");") ;
        output.println ("  }") ;
        output.println () ;
        
        for (int i = 0 ; i < numberOfClones ; i ++ ) {
            output.println ("variable : P"+(i+1)+" {") ;
            output.println ("type : discrete hidden cardinality P_CARD;") ;
            output.println ("conditionalparents : nil using DenseCPT(\"pP\");") ;
            output.println ("  }") ;
            output.println () ;
        }
        
        output.println ("variable : D {") ;
        output.println ("type : discrete observed D_OBS:D_OBS cardinality D_CARD;") ;
        output.println ("conditionalparents : nil using DenseCPT(\"internal:UnityScore\");") ;
        output.println ("  }") ;
        output.println () ;
        
        output.println ("variable : A {") ;
        output.println ("type : continuous observed A_OBS:A_OBS;") ;
        output.println ("switchingparents: Z(0),D(0) using mapping (\"dt_Z_D\");") ;
        output.print ("conditionalparents :") ; 
        for (int i = 0 ; i < numberOfClones ; i ++ ) {
            output.println ("G(0),P"+(i+1)+"(0) using mixture collection(\"pA1_given_G_P_D0\") mapping(\"dtA_given_G_P\")") ;
            output.print ("                   | ") ;
        }
        for (int i = 0 ; i < numberOfClones-1 ; i ++ ) {
            output.println ("G(0),P"+(i+1)+"(0) using mixture collection(\"pA1_given_G_P_D1\") mapping(\"dtA_given_G_P\")") ;
            output.print ("                   | ") ;
        }
        output.println ("G(0),P"+numberOfClones+"(0) using mixture collection(\"pA1_given_G_P_D1\") mapping(\"dtA_given_G_P\");") ;
        output.println ("  }") ;
        output.println () ;
        
        output.println ("variable : L {") ;
        output.println ("type : continuous observed L_OBS:L_OBS;") ;
        output.println ("switchingparents: Z(0) using mapping (\"internal:copyParent\");") ;
        output.print ("conditionalparents : ") ;
        for (int i = 0 ; i < numberOfClones-1 ; i ++ ) {
            output.println ("G(0),P"+(i+1)+"(0) using mixture collection(\"pL1_given_G_P\") mapping(\"dtL_given_G_P\")") ;
            output.print ("                   | ") ;
        }
        output.println ("G(0),P2(0) using mixture collection(\"pL1_given_G_P\") mapping(\"dtL_given_G_P\");") ;
        output.println ("  }") ;
        output.println ("}") ;
        output.println () ;
        
        output.println ("frame: 1 {") ;
        output.println () ;
        output.println ("variable : S {") ;
        output.println ("type : discrete observed S_OBS:S_OBS cardinality S_CARD; ") ;
        output.println ("conditionalparents : nil using DenseCPT(\"internal:UnityScore\");") ;
        output.println ("  }") ;
        output.println () ;
        output.println ("variable : G {") ;
        output.println ("type : discrete hidden cardinality G_CARD;") ;
        output.println ("switchingparents: S(0) using mapping (\"internal:copyParent\");") ;
        output.println ("conditionalparents : nil using DenseCPT(\"pG\")") ;
        output.println ("		   | nil using DenseCPT(\"pG_uni\");") ;
        output.println ("  }") ;
        output.println () ;
        output.println ("variable : LOG_DIST_G {") ;
        output.println ("type : continuous observed LOG_DIST_OBS:LOG_DIST_OBS;") ;
        output.println ("switchingparents: S(0), G(-1), G(0) using mapping (\"g_diagCheck\");") ;
        output.println ("weight:  scale 1 ") ;
        output.println ("	 | scale G_OD_OBS_0:G_OD_OBS_0") ;
        output.println ("	 | scale G_OD_OBS_1:G_OD_OBS_1") ;
        output.println ("	 | scale G_OD_OBS_2:G_OD_OBS_2") ;
        output.println ("	 | scale G_OD_OBS_3:G_OD_OBS_3") ;
        output.println ("	 | scale G_OD_OBS_4:G_OD_OBS_4") ;
        output.println ("	 | scale G_OD_OBS_5:G_OD_OBS_5") ;
        output.println ("	 | scale G_OD_OBS_6:G_OD_OBS_6") ;
        output.println ("	 | scale G_OD_OBS_7:G_OD_OBS_7") ;
        output.println ("	 | scale G_OD_OBS_8:G_OD_OBS_8") ;
        output.println ("	 | scale G_OD_OBS_9:G_OD_OBS_9") ;
        output.println ("	 | scale G_OD_OBS_10:G_OD_OBS_10") ;
        output.println ("	 | scale G_OD_OBS_11:G_OD_OBS_11") ;
        output.println ("	 | penalty G_D_COEFF_OBS_0:G_D_COEFF_OBS_0 shift G_TRANSITION_SHIFT") ;
        output.println ("	 | penalty G_D_COEFF_OBS_1:G_D_COEFF_OBS_1 shift G_TRANSITION_SHIFT") ;
        output.println ("	 | penalty G_D_COEFF_OBS_2:G_D_COEFF_OBS_2 shift G_TRANSITION_SHIFT") ;
        output.println ("	 | penalty G_D_COEFF_OBS_3:G_D_COEFF_OBS_3 shift G_TRANSITION_SHIFT") ;
        output.println ("	 | penalty G_D_COEFF_OBS_4:G_D_COEFF_OBS_4 shift G_TRANSITION_SHIFT") ;
        output.println ("	 | penalty G_D_COEFF_OBS_5:G_D_COEFF_OBS_5 shift G_TRANSITION_SHIFT") ;
        output.println ("	 | penalty G_D_COEFF_OBS_6:G_D_COEFF_OBS_6 shift G_TRANSITION_SHIFT") ;
        output.println ("	 | penalty G_D_COEFF_OBS_7:G_D_COEFF_OBS_7 shift G_TRANSITION_SHIFT") ;
        output.println ("	 | penalty G_D_COEFF_OBS_8:G_D_COEFF_OBS_8 shift G_TRANSITION_SHIFT") ;
        output.println ("	 | penalty G_D_COEFF_OBS_9:G_D_COEFF_OBS_9 shift G_TRANSITION_SHIFT") ;
        output.println ("	 | penalty G_D_COEFF_OBS_10:G_D_COEFF_OBS_10 shift G_TRANSITION_SHIFT") ;
        output.println ("	 | penalty G_D_COEFF_OBS_11:G_D_COEFF_OBS_11 shift G_TRANSITION_SHIFT;") ;
        output.println ("conditionalparents : nil using mixture(\"internal:UnityScore\")") ;
        output.println ("		   | nil using mixture(\"internal:UnityScore\")") ;
        output.println ("		   | nil using mixture(\"internal:UnityScore\")") ;
        output.println ("		   | nil using mixture(\"internal:UnityScore\")") ;
        output.println ("		   | nil using mixture(\"internal:UnityScore\")") ;
        output.println ("		   | nil using mixture(\"internal:UnityScore\")") ;
        output.println ("		   | nil using mixture(\"internal:UnityScore\")") ;
        output.println ("		   | nil using mixture(\"internal:UnityScore\")") ;
        output.println ("		   | nil using mixture(\"internal:UnityScore\")") ;
        output.println ("		   | nil using mixture(\"internal:UnityScore\")") ;
        output.println ("		   | nil using mixture(\"internal:UnityScore\")") ;
        output.println ("		   | nil using mixture(\"internal:UnityScore\")") ;
        output.println ("		   | nil using mixture(\"internal:UnityScore\")") ;
        output.println ("		   | nil using mixture(\"mixture_ggDiag0\")") ;
        output.println ("		   | nil using mixture(\"mixture_ggDiag1\")") ;
        output.println ("		   | nil using mixture(\"mixture_ggDiag2\")") ;
        output.println ("		   | nil using mixture(\"mixture_ggDiag3\")") ;
        output.println ("		   | nil using mixture(\"mixture_ggDiag4\")") ;
        output.println ("		   | nil using mixture(\"mixture_ggDiag5\")") ;
        output.println ("		   | nil using mixture(\"mixture_ggDiag6\")") ;
        output.println ("		   | nil using mixture(\"mixture_ggDiag7\")") ;
        output.println ("		   | nil using mixture(\"mixture_ggDiag8\")") ;
        output.println ("		   | nil using mixture(\"mixture_ggDiag9\")") ;
        output.println ("		   | nil using mixture(\"mixture_ggDiag10\")") ;
        output.println ("		   | nil using mixture(\"mixture_ggDiag11\");") ;
        output.println ("}") ;
        output.println () ;
        
        output.println ("variable : Z {") ;
        output.println ("type : discrete hidden cardinality Z_CARD;") ;
        output.println ("switchingparents: S(0) using mapping (\"internal:copyParent\");") ;
        output.println ("conditionalparents : nil using DenseCPT(\"pZ\")") ;
        output.println ("		   | nil using DenseCPT(\"pZ_uni\"); ") ;
        output.println ("}") ;
        output.println () ;
        
        output.println ("variable : LOG_DIST_Z { ") ;
        output.println ("type : continuous observed LOG_DIST_OBS:LOG_DIST_OBS;") ;
        output.println ("switchingparents: S(0), Z(-1), Z(0) using mapping (\"z_diagCheck\");") ;
        output.println ("weight:  scale 1") ;
        for (int i = 0 ; i < numberOfClones ; i ++ ) {
            output.println ("	 | scale Z_OD_OBS_"+i+":Z_OD_OBS_"+i) ;
        }
        for (int i = 0 ; i < numberOfClones ; i ++ ) {
            output.print ("	 | penalty Z_D_COEFF_OBS_"+i+":Z_D_COEFF_OBS_"+i+" shift Z_TRANSITION_SHIFT") ;
            if (i<numberOfClones-1) {
                output.println () ;
            } else {
            	  output.println (";") ;
            }
        }
        output.println ("conditionalparents : nil using mixture(\"internal:UnityScore\")") ;
        for (int i = 0 ; i < numberOfClones ; i ++ ) {
            output.println ("		   | nil using mixture(\"internal:UnityScore\")") ;
        }
        for (int i = 0 ; i < numberOfClones ; i ++ ) {
            output.print ("		   | nil using mixture(\"mixture_zzDiag"+i+"\")") ;
            if (i<numberOfClones-1) {
                output.println () ;
            } else {
            	  output.println (";") ;
            }
        }
        output.println ("}") ;
        output.println () ;
        
        for (int i = 0 ; i < numberOfClones ; i ++ ) {
            output.println ("variable : P"+(i+1)+" {") ;
            output.println ("type : discrete hidden cardinality P_CARD;") ;
            output.println ("conditionalparents : P"+(i+1)+"(-1) using DeterministicCPT(\"pP_chunk_cpt\");") ;
            output.println ("  }") ;
            output.println () ; 
        }
        
        
        output.println ("variable : D { ") ;
        output.println ("type : discrete observed D_OBS:D_OBS cardinality D_CARD; ") ;
        output.println ("conditionalparents : nil using DenseCPT(\"internal:UnityScore\"); ") ;
        output.println ("  }") ;
        output.println () ;
        
        output.println ("variable : A {") ;
        output.println ("type : continuous observed A_OBS:A_OBS;") ;
        output.println ("switchingparents: Z(0),D(0) using mapping (\"dt_Z_D\");") ;
        output.print ("conditionalparents : ") ; 
        for (int i = 0 ; i < numberOfClones ; i ++ ) {
            output.println ("G(0),P"+(i+1)+"(0) using mixture collection(\"pA1_given_G_P_D0\") mapping(\"dtA_given_G_P\")") ;
            output.print ("                   | ") ;
        }
        for (int i = 0 ; i < numberOfClones-1 ; i ++ ) {
            output.println ("G(0),P"+(i+1)+"(0) using mixture collection(\"pA1_given_G_P_D1\") mapping(\"dtA_given_G_P\")") ;
            output.print ("                   | ") ;
        }
        output.println ("G(0),P"+numberOfClones+"(0) using mixture collection(\"pA1_given_G_P_D1\") mapping(\"dtA_given_G_P\");") ;
        output.println ("  }") ;
        output.println () ;
        
        output.println ("variable : L {") ;
        output.println ("type : continuous observed L_OBS:L_OBS;") ;
        output.println ("switchingparents: Z(0) using mapping (\"internal:copyParent\");") ;
        output.print ("conditionalparents : ") ;
        for (int i = 0 ; i < numberOfClones-1 ; i ++ ) {
            output.println ("G(0),P"+(i+1)+"(0) using mixture collection(\"pL1_given_G_P\") mapping(\"dtL_given_G_P\")") ;
            output.print ("                   | ") ;
        }
        output.println ("G(0),P2(0) using mixture collection(\"pL1_given_G_P\") mapping(\"dtL_given_G_P\");") ;
        output.println ("  }") ;
        output.println ("}") ;
        output.println () ;
        
        output.println ("chunk 1:1;") ;
        
    	  output.close () ;
    }
    
    public void generateMasterFile () throws java.io.IOException {
        PrintStream output = new PrintStream(new FileOutputStream(modelDirectory + "/" + masterFileName)) ;
        output.println ("%Master file") ;
        output.println () ;
        output.println ("#include \"constants.inc\"") ;
        output.println () ;
        output.println ("% DT_IN_FILE dts.txt ascii") ;
        output.println ("DT_IN_FILE inline 5") ;
        output.println ("0") ;
        output.println ("dtA_given_G_P") ;
        output.println ("2") ;
        output.println ("  -1 { p0 * P_CARD + p1  }") ;
        output.println () ;
        output.println ("1") ;
        output.println ("dtL_given_G_P") ;
        output.println ("2") ;
        output.println ("  -1 { p0 * P_CARD + p1  }") ;
        output.println () ;
        output.println ("2") ;
        output.println ("dt_Z_D") ;
        output.println ("2") ;
        output.println ("  -1 { p0 + Z_CARD * p1 }") ;
        output.println ("") ;
        output.println ("3") ;
        output.println ("g_diagCheck") ;
        output.println ("3") ;
        output.println ("0 2 0 default") ;
        output.println ("  -1 { 0 }") ;
        output.println ("  -1 { ( p1 == p2 ) ? (p2 + G_CARD + 1) : (p1+1) }") ;
        output.println ("") ;
        output.println ("4") ;
        output.println ("z_diagCheck") ;
        output.println ("3") ;
        output.println ("0 2 0 default") ;
        output.println ("  -1 { 0 }") ;
        output.println ("  -1 { ( p1 == p2 ) ? (p2 + Z_CARD + 1) : (p1+1) }") ;
        output.println ("") ;
        output.println ("DETERMINISTIC_CPT_IN_FILE inline 1") ;
        output.println ("%CPT_num") ;
        output.println ("%CPT_name") ;
        output.println ("%num_parents") ;
        output.println ("%parent_cardinalities") ;
        output.println ("%self_cardinality") ;
        output.println ("%dt_mapping_name") ;
        output.println ("0") ;
        output.println ("pP_chunk_cpt") ;
        output.println ("1") ;
        output.println ("P_CARD") ;
        output.println ("P_CARD") ;
        output.println ("internal:copyParent") ;
        output.println ("") ;
        output.println ("COVAR_IN_FILE initCovars.txt ascii") ;
        output.println ("DPMF_IN_FILE initDpmfs.txt ascii") ;
        output.println ("%%%%%%%%%%%%% A, D = 0") ;
        output.println ("MEAN_IN_FILE meansA_D0.txt ascii") ;
        output.println ("MC_IN_FILE mcA1_D0.txt ascii") ;
        output.println ("MX_IN_FILE mixtureA1_D0.txt ascii") ;
        output.println ("NAME_COLLECTION_IN_FILE collectionA1_D0.txt ascii") ;
        output.println ("%%%%%%%%%%%%% A, D = 1") ;
        output.println ("MEAN_IN_FILE meansA_D1.txt ascii") ;
        output.println ("MC_IN_FILE mcA1_D1.txt ascii") ;
        output.println ("MX_IN_FILE mixtureA1_D1.txt ascii") ;
        output.println ("NAME_COLLECTION_IN_FILE collectionA1_D1.txt ascii") ;
        output.println ("%%%%%%%%%%%%% L") ;
        output.println ("MEAN_IN_FILE meansL1.txt ascii") ;
        output.println ("MC_IN_FILE mcL1.txt ascii") ;
        output.println ("MX_IN_FILE mixtureL1.txt ascii") ;
        output.println ("NAME_COLLECTION_IN_FILE collectionL1.txt ascii") ;
        
    	  output.close () ;
    }
    
    public void generateNoTrainFile () throws java.io.IOException {
        PrintStream output = new PrintStream(new FileOutputStream(modelDirectory + "/" + noTrainFileName)) ;
        output.println ("%DENSECPT pX") ;
        output.println ("%DENSECPT pO_given_X") ;
        output.println ("MEAN *") ;
        output.println ("DENSECPT pG_uni") ;
        output.println ("DENSECPT pZ_uni") ;
        output.println ("% DPMF *") ;
        output.println ("% SPMF *") ;
        output.println ("% DLINKMAT *") ;
        output.println ("% WEIGHTMAT *") ;
        output.println ("% REALMAT *") ;
        output.println ("% COMPONENT *") ;
        output.println ("% MIXTURE *") ;
    	  output.close () ;
    }
    
    public void generateConstantFile () throws java.io.IOException {
        PrintStream output = new PrintStream(new FileOutputStream(modelDirectory + "/" + constantFileName)) ;
        output.println ("#define LOG_DIST_OBS 0") ;
        int count = 1 ;
        for (int i = 0 ; i < 12 ; i ++ ) {
            output.println ("#define G_D_COEFF_OBS_"+i+" "+count);
            count++; 
        }
        for (int i = 0 ; i < numberOfClones ; i ++ ) {
            output.println ("#define Z_D_COEFF_OBS_"+i+" "+count);
            count++; 
        }
        for (int i = 0 ; i < 12 ; i ++ ) {
            output.println ("#define G_OD_OBS_"+i+" "+count);
            count++; 
        }
        for (int i = 0 ; i < numberOfClones ; i ++ ) {
            output.println ("#define Z_OD_OBS_"+i+" "+count);
            count++; 
        }
        output.println ("#define A_OBS "+count);
        count++; 
        output.println ("#define L_OBS "+count);
        count++; 
        output.println ("#define D_OBS "+count);
        count++; 
        output.println ("#define S_OBS "+count);
        output.println () ;
        
        output.println ("#define G_CARD 12") ;
        output.println ("#define Z_CARD " + numberOfClones) ;
        output.println ("#define P_CARD 20") ;
        output.println ("#define D_CARD 2") ;
        output.println ("#define S_CARD 2") ;
        output.println ("#define G_CROSS_G_CARD 144") ;
        int zsqr = numberOfClones*numberOfClones ;
        output.println ("#define Z_CROSS_Z_CARD "+zsqr) ;
        output.println ("#define G_TRANSITION_SHIFT 0.08333333333333333") ;
        output.println ("#define Z_TRANSITION_SHIFT "+String.valueOf(1.0/numberOfClones)) ;        
        
    	  output.close () ;
    }
    
    public void generateInitParameterFile () throws java.io.IOException {
        PrintStream output = new PrintStream(new FileOutputStream(modelDirectory + "/" + initParameterFileName)) ;
        output.println ("%dense PMFs") ;
        output.println ("0") ;
        output.println () ;
        output.println ("%sparse PMFs") ;
        output.println ("0") ;
        output.println () ;
        output.println ("%means") ;
        output.println ("1") ;
        output.println ("0") ;
        output.println ("dist_mean 1 0.0") ;
        output.println () ;
        output.println ("%diagonal covariance matrices") ;        
        output.println (""+(numberOfClones+12)) ;
        for (int i = 0 ; i < 12 ; i++) {
            output.println (i) ;
            output.println ("covar_ggDiag"+i+" 1 339.3215") ;
        }
        for (int i = 0 ; i < numberOfClones ; i++) {
            output.println ((i+12)) ;
            output.println ("covar_zzDiag"+i+" 1 339.3215") ;
        }
        output.println () ;
        output.println ("%dlink matrices ") ;
        output.println ("0 ") ;
        output.println () ;
        output.println ("%weight matrices ") ;
        output.println ("0") ;
        output.println () ;
        output.println ("% MDCPTs") ;
        output.println ("5 %number of DenseCPTs to follow") ;
        output.println () ;
        output.println ("%GMTK notation is as follows: ") ;
        output.println ("%cpt_index") ;
        output.println ("%cpt_name") ;
        output.println ("%number of parents") ;
        output.println ("%cardinality of parents") ;
        output.println ("%cardinality of self") ;
        output.println ("%dense_cpt values") ;
        output.println () ;
        output.println ("0") ;
        output.println ("pG") ;
        output.println ("0 % number parents") ;
        output.println ("12 % cardinalities") ;
        output.println ("5.0e-02 5.0e-02 4.5e-01 5.0e-02 5.0e-02 5.0e-02 5.0e-02 5.0e-02 5.0e-02 5.0e-02 5.0e-02 5.0e-02") ;
        
        output.println () ;
        output.println ("1") ;
        output.println ("pZ") ;
        output.println ("0 % number parents") ;
        output.println (numberOfClones+" % cardinalities") ;
        String zvalues = "" ;
        for (int i = 0 ; i < numberOfClones ; i ++ ) {
            zvalues = String.valueOf(1.0/numberOfClones + " " + zvalues) ;
        }
        output.println (zvalues) ;
        
        output.println () ;
        output.println ("2") ;
        output.println ("pG_uni") ;
        output.println ("0 % number parents") ;
        output.println ("12 % cardinalities") ;
        String gvalues = "" ;
        for (int i = 0 ; i < 12 ; i ++ ) {
            gvalues = String.valueOf(1.0/12 + " " + gvalues) ;
        }
        output.println (gvalues) ;
        
        output.println () ;
        output.println ("3") ;
        output.println ("pZ_uni") ;
        output.println ("0 % number parents") ;
        output.println (numberOfClones+" % cardinalities") ;
        zvalues = "" ;
        for (int i = 0 ; i < numberOfClones ; i ++ ) {
            zvalues = String.valueOf(1.0/numberOfClones + " " + zvalues) ;
        }
        output.println (zvalues) ;
        
        output.println () ;
        output.println ("4") ;
        output.println ("pP") ;
        output.println ("0 % number parents") ;
        output.println ("20 % cardinalities") ;
        String pvalues = "" ;
        for (int i = 0 ; i < 20 ; i ++ ) {
            pvalues = String.valueOf(1.0/20 + " " + pvalues) ;
        }
        output.println (pvalues) ;
        
        output.println () ;
        output.println ("% Gaussian Components") ;        
        output.println (""+(numberOfClones+12)) ;
        for (int i = 0 ; i < 12 ; i++) {
            output.println (i) ;
            output.println ("1") ;
            output.println ("0 gc_ggDiag"+i) ;
            output.println ("dist_mean covar_ggDiag"+i) ;
        }
        for (int i = 0 ; i < numberOfClones ; i++) {
            output.println ((i+12)) ;
            output.println ("1") ;
            output.println ("0 gc_zzDiag"+i) ;
            output.println ("dist_mean covar_zzDiag"+i) ;
        }
        
        output.println () ;
        output.println ("% Mixtures of Gaussians") ;        
        output.println (""+(numberOfClones+12)) ;
        for (int i = 0 ; i < 12 ; i++) {
            output.println (i) ;
            output.println ("1") ;
            output.println ("mixture_ggDiag"+i) ;
            output.println ("1 unityDPMF") ;
            output.println ("gc_ggDiag"+i) ;            
        }
        for (int i = 0 ; i < numberOfClones ; i++) {
            output.println ((i+12)) ;
            output.println ("1") ;
            output.println ("mixture_zzDiag"+i) ;
            output.println ("1 unityDPMF") ;
            output.println ("gc_zzDiag"+i) ; 
        }
        
        output.println () ;
        output.println ("% Gaussian Switching Mixtures of Gaussians") ;
        output.println ("0") ;
        output.println () ;
        output.println ("% Logistic-Regression-based Switching Mixutres of Gaussians") ;
        output.println ("0") ;
        output.println () ;
        output.println ("% MLP-based Switching Mixtures of Gaussians") ;
        output.println ("0") ;
        output.println () ;
        
    	  output.close () ;
    }
    
    public void generateInitDpmfFile () throws java.io.IOException {
        PrintStream output = new PrintStream(new FileOutputStream(modelDirectory + "/" + initDpmfFileName)) ;
        output.println ("2              ") ;
        output.println ("0              ") ;
        output.println ("unityDPMF      ") ;
        output.println ("1              ") ;
        output.println ("1.0            ") ;
        output.println ("               ") ;
        output.println ("1              ") ;
        output.println ("uniform_2d_DPMF") ;
        output.println ("2              ") ;
        output.println ("0.5 0.5        ") ;
        
    	  output.close () ;
    }
        
    public static void main(String args[]) throws java.io.IOException {    	  
        if (args.length != 2 ) {
            System.err.println("Usage:  java generatingModelSpecificationFilesIndividualAnalysis dir numberOfClones"); 
            System.exit(-1); 
        }
        Integer numberOfClonesInt = new Integer (args[1]) ;
        generatingModelSpecificationFilesIndividualAnalysis my = new generatingModelSpecificationFilesIndividualAnalysis (args[0], numberOfClonesInt.intValue()) ;
        my.generateInitCovarFile () ;
        my.generateStructureFile () ;
        my.generateMasterFile () ; 
        my.generateNoTrainFile () ;
        my.generateConstantFile () ;
        my.generateInitParameterFile () ;
        my.generateInitDpmfFile () ;
    }
    
}
