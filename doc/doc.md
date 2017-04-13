

A short [GMTK tutorial](http://melodi.ee.washington.edu/~halloj3/pdfs/gmtk_hmm_example.pdf) by [John T. Halloran](http://melodi.ee.washington.edu/~halloj3/) is highly recommended. 


# Structure file `hmm_factorialModel.str`

* Observed variable S: whether this site is a start of a chromosome

~~~~
variable : S {
type : discrete observed S_OBS:S_OBS cardinality S_CARD;
conditionalparents : nil using DenseCPT("internal:UnityScore");
  }
~~~~

* Observed variable D: whether this site is germline heterozygous site or a germline homozygous site hosting a SNV
 
~~~~  
variable : D {
type : discrete observed D_OBS:D_OBS cardinality D_CARD;
conditionalparents : nil using DenseCPT("internal:UnityScore"); 
  }  
~~~~  
  
* Hidden variable G, the genotype of the CNA event  

~~~~
variable : G {
type : discrete hidden cardinality G_CARD;
switchingparents: S(0) using mapping ("internal:copyParent");
conditionalparents : nil using DenseCPT("pG")
		               | nil using DenseCPT("pG_uni");
  }
~~~~

* Virtual evidence variable for transition probability of G. The following example is for |G|=5.
 
~~~~  
variable : LOG_DIST_G {
type : continuous observed LOG_DIST_OBS:LOG_DIST_OBS;
switchingparents: S(0), G(-1), G(0) using mapping ("g_diagCheck");
weight:  scale 1 
	 | scale G_OD_OBS_0:G_OD_OBS_0
	 | scale G_OD_OBS_1:G_OD_OBS_1
	 | scale G_OD_OBS_2:G_OD_OBS_2
	 | scale G_OD_OBS_3:G_OD_OBS_3
	 | scale G_OD_OBS_4:G_OD_OBS_4
	 | scale G_OD_OBS_5:G_OD_OBS_5
	 | scale G_OD_OBS_6:G_OD_OBS_6
	 | scale G_OD_OBS_7:G_OD_OBS_7
	 | scale G_OD_OBS_8:G_OD_OBS_8
	 | scale G_OD_OBS_9:G_OD_OBS_9
	 | scale G_OD_OBS_10:G_OD_OBS_10
	 | scale G_OD_OBS_11:G_OD_OBS_11
	 | penalty G_D_COEFF_OBS_0:G_D_COEFF_OBS_0 shift G_TRANSITION_SHIFT
	 | penalty G_D_COEFF_OBS_1:G_D_COEFF_OBS_1 shift G_TRANSITION_SHIFT
	 | penalty G_D_COEFF_OBS_2:G_D_COEFF_OBS_2 shift G_TRANSITION_SHIFT
	 | penalty G_D_COEFF_OBS_3:G_D_COEFF_OBS_3 shift G_TRANSITION_SHIFT
	 | penalty G_D_COEFF_OBS_4:G_D_COEFF_OBS_4 shift G_TRANSITION_SHIFT
	 | penalty G_D_COEFF_OBS_5:G_D_COEFF_OBS_5 shift G_TRANSITION_SHIFT
	 | penalty G_D_COEFF_OBS_6:G_D_COEFF_OBS_6 shift G_TRANSITION_SHIFT
	 | penalty G_D_COEFF_OBS_7:G_D_COEFF_OBS_7 shift G_TRANSITION_SHIFT
	 | penalty G_D_COEFF_OBS_8:G_D_COEFF_OBS_8 shift G_TRANSITION_SHIFT
	 | penalty G_D_COEFF_OBS_9:G_D_COEFF_OBS_9 shift G_TRANSITION_SHIFT
	 | penalty G_D_COEFF_OBS_10:G_D_COEFF_OBS_10 shift G_TRANSITION_SHIFT
	 | penalty G_D_COEFF_OBS_11:G_D_COEFF_OBS_11 shift G_TRANSITION_SHIFT;
conditionalparents : nil using mixture("internal:UnityScore")
		   | nil using mixture("internal:UnityScore")
		   | nil using mixture("internal:UnityScore")
		   | nil using mixture("internal:UnityScore")
		   | nil using mixture("internal:UnityScore")
		   | nil using mixture("internal:UnityScore")
		   | nil using mixture("internal:UnityScore")
		   | nil using mixture("internal:UnityScore")
		   | nil using mixture("internal:UnityScore")
		   | nil using mixture("internal:UnityScore")
		   | nil using mixture("internal:UnityScore")
		   | nil using mixture("internal:UnityScore")
		   | nil using mixture("internal:UnityScore")
		   | nil using mixture("mixture_ggDiag0")
		   | nil using mixture("mixture_ggDiag1")
		   | nil using mixture("mixture_ggDiag2")
		   | nil using mixture("mixture_ggDiag3")
		   | nil using mixture("mixture_ggDiag4")
		   | nil using mixture("mixture_ggDiag5")
		   | nil using mixture("mixture_ggDiag6")
		   | nil using mixture("mixture_ggDiag7")
		   | nil using mixture("mixture_ggDiag8")
		   | nil using mixture("mixture_ggDiag9")
		   | nil using mixture("mixture_ggDiag10")
		   | nil using mixture("mixture_ggDiag11");
}
~~~~

* Hidden variable Z, the clone in which the CNA event occurs 

~~~~
variable : Z {
type : discrete hidden cardinality Z_CARD;
switchingparents: S(0) using mapping ("internal:copyParent");
conditionalparents : nil using DenseCPT("pZ")
		               | nil using DenseCPT("pZ_uni");
  }  
~~~~

* Virtual evidence variable for transition probability of Z. The following example is for |Z|=5.

~~~~
variable : LOG_DIST_Z {

type : continuous observed LOG_DIST_OBS:LOG_DIST_OBS;
switchingparents: S(0), Z(-1), Z(0) using mapping ("z_diagCheck");
weight:  scale 1 
	 | scale Z_OD_OBS_0:Z_OD_OBS_0
	 | scale Z_OD_OBS_1:Z_OD_OBS_1
	 | scale Z_OD_OBS_2:Z_OD_OBS_2
	 | scale Z_OD_OBS_3:Z_OD_OBS_3
	 | scale Z_OD_OBS_4:Z_OD_OBS_4
	 | penalty Z_D_COEFF_OBS_0:Z_D_COEFF_OBS_0 shift Z_TRANSITION_SHIFT
	 | penalty Z_D_COEFF_OBS_1:Z_D_COEFF_OBS_1 shift Z_TRANSITION_SHIFT
	 | penalty Z_D_COEFF_OBS_2:Z_D_COEFF_OBS_2 shift Z_TRANSITION_SHIFT
	 | penalty Z_D_COEFF_OBS_3:Z_D_COEFF_OBS_3 shift Z_TRANSITION_SHIFT
	 | penalty Z_D_COEFF_OBS_4:Z_D_COEFF_OBS_4 shift Z_TRANSITION_SHIFT;
conditionalparents : nil using mixture("internal:UnityScore")
		   | nil using mixture("internal:UnityScore")
		   | nil using mixture("internal:UnityScore")
		   | nil using mixture("internal:UnityScore")
		   | nil using mixture("internal:UnityScore")
		   | nil using mixture("internal:UnityScore")
		   | nil using mixture("mixture_zzDiag0")
		   | nil using mixture("mixture_zzDiag1")
		   | nil using mixture("mixture_zzDiag2")
		   | nil using mixture("mixture_zzDiag3")
		   | nil using mixture("mixture_zzDiag4");
  }
~~~~

* Prevalence variable: P1_2, the prevalence of the second clone in the first tumor biopsy

~~~~
variable : P1_2 {
type : discrete observed P1_2_OBS:P1_2_OBS cardinality P_CARD; 
conditionalparents : nil using DenseCPT("internal:UnityScore"); 
  }
~~~~

* Observed variable: Allelic ratio in first biopsy. The first five scenarios correspond to the five clones (Z=0,...,4) and D=0. The next five scenarios correspond to the five clones (Z=0,...,4) and D=1.

~~~~ 
variable : A1 {
type : continuous observed A1_OBS:A1_OBS;
switchingparents: Z(0),D(0) using mapping ("dt_Z_D");
conditionalparents : G(0),P1_2(0) using mixture collection("pA1_given_G_P_D0") mapping("dtA_given_G_P") 
                   | G(0),P1_1(0) using mixture collection("pA1_given_G_P_D0") mapping("dtA_given_G_P")
                   | nil using mixture ("mixtureA1_D0_0_noCNAs")
                   | nil using mixture ("mixtureA1_D0_0_noCNAs")
                   | nil using mixture ("mixtureA1_D0_0_noCNAs")
                   | G(0),P1_2(0) using mixture collection("pA1_given_G_P_D1") mapping("dtA_given_G_P")
                   | G(0),P1_1(0) using mixture collection("pA1_given_G_P_D1") mapping("dtA_given_G_P")
                   | nil using mixture ("internal:ZeroScore") 
                   | nil using mixture ("internal:ZeroScore") 
                   | nil using mixture ("internal:ZeroScore");
  }
~~~~

* Observed variable: log tumor-normal read depth ratio in first biopsy. The five scenarios correspond to the five clones (Z=0,...,4).

~~~~  
variable : L1 {
type : continuous observed L1_OBS:L1_OBS;
switchingparents: Z(0) using mapping ("internal:copyParent");
conditionalparents : G(0),P1_2(0) using mixture collection("pL1_given_G_P") mapping("dtL_given_G_P")
                   | G(0),P1_1(0) using mixture collection("pL1_given_G_P") mapping("dtL_given_G_P")
                   | nil using mixture ("mixtureL1_0_noCNAs") 
                   | nil using mixture ("mixtureL1_0_noCNAs") 
                   | nil using mixture ("mixtureL1_0_noCNAs");
  }  
~~~~  

# Master file `hmm_factorialModel.mtr`

* emission probability of A given its parents G and P

~~~~
dtA_given_G_P
2
  -1 { p0 * P_CARD + p1  }
~~~~

* emission probability of L given its parents G and P 

~~~~
dtL_given_G_P
2
  -1 { p0 * P_CARD + p1  }  
~~~~

* distinguish germline heterozygous site and germline homozygous site

~~~~
dt_Z_D
2
  -1 { p0 + Z_CARD * p1 }
~~~~

* for transition matrix of G

~~~~
g_diagCheck
3 
0 2 0 default
  -1 { 0 }
  -1 { ( p1 == p2 ) ? (p2 + G_CARD + 1) : (p1+1) }
~~~~

* for transition matrix of Z

~~~~
z_diagCheck
3 
0 2 0 default
  -1 { 0 }
  -1 { ( p1 == p2 ) ? (p2 + Z_CARD + 1) : (p1+1) }
~~~~

# Other model specification files
* constants.inc  // constants used in the structure file and master file
* initCovars.txt  // initial values for covariance in the Gaussians
* initDpmfs.txt  // initial values for Gaussian mixtures
* init_hmm_factorialModel.params  // initial values for other parameters
* params.notrain  // which parameters are not to be estimated. If a parameter is specified in this file, it will be fixed at its initial value rather than being estimated
* em_covar.txt // current estimate of the transition parameters \sigma^2_{G,j} and \sigma^2_{Z,j}. The first |G| rows are for \sigma^2_{G,j} and the next |Z| rows are for \sigma^2_{Z,j} (usually initialized as 100).
* em_training_data.txt // the directory and file name of the input data for GMTK







