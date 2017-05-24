

A short [GMTK tutorial](http://melodi.ee.washington.edu/~halloj3/pdfs/gmtk_hmm_example.pdf) by [John T. Halloran](http://melodi.ee.washington.edu/~halloj3/) is highly recommended for understanding structure file, master file and how to perform training and testing in GMTK. 


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

* Virtual evidence variable for transition probability of G. The following example is for |G|=12.
 
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


# Input data files

* `combinedData_01-1-B2_01-2-B2_01-4-B3.txt` // input file for THEMIS, each row corresponds to one genomic position, and each row contains the following fields --- `Chromosome`, `Base pair position`, `Allelic ratio tumor biopsy 1`, `Allelic ratio tumor biopsy 2`, `Allelic ratio tumor biopsy 3`, `log ratio tumor biopsy 1`, `log ratio tumor biopsy 2`, `log ratio tumor biopsy 3`, `Site type (1=germline homozygous site hosting SNV,0=germline heterozygous site)`, separated by TAB

# Data file processing code (under ./src/)

* `updateOffDiagonalElementsForEMUntieVariancesIndividualAnalysis.java` // takes input data file (e.g. `combinedData_01-1-B2_01-2-B2_01-4-B3.txt`) and current estimate of transition parameters in `em_covar.txt` and writes into a file which can be used by GMTK for single biopsy individual analysis (e.g. `trainWithLogSiteDistance_01-1-B2.txt`). The output file is organized as follows. Each row corresponds to one genomic position, and each row contains the following fields --- `log distance from previous site`, `log transition for genotypes, |G| diagonal elements`, `log transition for clones, |Z| diagonal elements`, `log transition for genotypes, |G| off-diagonal elements`, `log transition for clones, |Z| off-diagonal elements`, `allelic ratios from the biopsy`, `log ratios from the biopsy`, `site type (D)`, `Same chromosome (S)`. One example of usage is

Usage:  java updateOffDiagonalElementsForEMUntieVariancesIndividualAnalysis tempParaFileName dataFileName tempDataFileName genotypeNum zNum biopsyNum biopsyIndex

Example: java updateOffDiagonalElementsForEMUntieVariancesIndividualAnalysis /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/src/GitHubTHEMIS/THEMIS/model/individualAnalysis/em_covar.txt /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/src/GitHubTHEMIS/THEMIS/data/combinedData_01-1-B2_01-2-B2_01-4-B3.txt /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/src/GitHubTHEMIS/THEMIS/data/trainWithLogSiteDistance_01-1-B2.txt 12 2 3 0

Note that the last argument 0 means that this individual analysis is for the FIRST biopsy of the three biopsies.

* `updateOffDiagonalElementsForEMUntieVariances.java` // takes input data file (e.g. `combinedData_01-1-B2_01-2-B2_01-4-B3.txt`) and current estimate of transition parameters in `em_covar.txt` and writes into a file which can be used by GMTK for multiple biopsy joint analysis (e.g. `trainWithLogSiteDistance_01-1-B2_01-2-B2_01-4-B3.txt`). The output file is organized as follows. Each row corresponds to one genomic position, and each row contains the following fields --- `log distance from previous site`, `log transition for genotypes, |G| diagonal elements`, `log transition for clones, |Z| diagonal elements`, `log transition for genotypes, |G| off-diagonal elements`, `log transition for clones, |Z| off-diagonal elements`, `allelic ratios from M biopsies`, `log ratios from M biopsies`, `site type (D)`, `Same chromosome (S)`, `prevalence levels of the clones`. One example of usage is

Usage:  java updateOffDiagonalElementsForEMUntieVariances dataDir modelDir rawDataFileName processedDataFileName biopsyNum subcloneNum genotypeNum zNum prevalences

Example: java updateOffDiagonalElementsForEMUntieVariances /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/src/GitHubTHEMIS/THEMIS/model/jointAnalysis/em_covar.txt /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/src/GitHubTHEMIS/THEMIS/data/combinedData_01-1-B2_01-2-B2_01-4-B3.txt /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/src/GitHubTHEMIS/THEMIS/data/trainWithLogSiteDistanc_01-1-B2_01-2-B2_01-4-B3.txt 3 6 12 5 6~14~3~8~5~13

Note that the last argument 6~14~3~8~5~13 stands for that the inferred prevalence levels of the six clones are (6+1)x0.05, (14+1)x0.05, (3+1)x0.05, (8+1)x0.05, (5+1)x0.05 and (13+1)x0.05.

# Shell script files

* `trainTestIndividualAnalysis.sh` // under (./model/individualAnalysis)
* `trainTestJointAnalysis.sh` // under (./model/jointAnalysis)

 * -gp // path of GMTK executables (i.e. where GMTK is installed/bin/)
 * -trf // training input file name (this file contains the directory and file name of temp train data file. User can use any name they like.)
 * -tef // testing input file name (this file contains the directory and file name of temp test data file. In typical use of THEMIS, it should be the same as -trf. In cross-validation, it should be different from -trf)
 * -odf // original data file name (i.e., input of updateOffDiagonalElementsForEMUntieVariances.java. For example, combinedData_01-1-B2_01-2-B2_01-4-B3.txt under ./data/)
 * -tdf // temp data file name (i.e., output of updateOffDiagonalElementsForEMUntieVariances.java)
 * -sd // path for source code (i.e., path of updateOffDiagonalElementsForEMUntieVariances.java)
 * -md // directory of model specification files
 * -nf // number of float (should be equivalent to the number of genotypes*2+ the cardinality of Z variable*2 + the number of biopsies in the analysis*2 + 1)
 * -ni // number of integers (in joint analysis it should be the number of clones + 2. In individual analysis, it should be 2, one for D variable and one for S variable.)
 * -bn // the number of biopsies in the joint analysis (e.g., the number of biopsies in combinedData_01-1-B2_01-2-B2_01-4-B3.txt is 3. The number should be 3, even for individual analysis)
 * -bi // the index of the individual biopsy within the multiple biopsies (only for individual analysis. For example, the parameter should be 0 if we are analyzing the first biopsy 01-1-B2 in combinedData_01-1-B2_01-2-B2_01-4-B3.txt)
 * -cn // the number of clones (only for joint analysis, the number is simply the sum of the numbers of clones in all biopsies in the joint analysis)
 * -gn // the number of genotypes (typically set as 12)
 * -zn // the cardinality of Z variable (the number of clones in the candidate phylogenetic tree, the number can be different from -cn because of possible clone merging)
 * -pln // the number of prevalence levels (typically set as 20)
 * -pl // the prevalence granularity (typically set as 0.05)
 * -ps // the prevalence levels inferred in individual analysis (only for joint analysis, separated by '~'. The number of prevalence levels should match -cn)
 * -c // C value (please refer our paper for this parameter. In joint analysis, c values in the multiple biopsies should be separated by '~'. The number of C values should match the number of biopsies in the joint analysis. In individual analysis, only one C value should be provided.)
 * -tvg // initial estimate of transition parameter for G (please refer our paper Page13 for this parameter. Typically set as 100)
 * -tvz // initial estimate of transition parameter for Z (please refer our paper Page13 for this parameter. Typically set as 100)
 * -l // lst parameter for island algorithm (We can set -lst between 1 and upper(n/3), where n is the # genomic sites. When -lst gets smaller, the memory consumption will be less, but it will take more time.)
 * -v // verbosity of output (to feed -verb parameter in GMTK. Typically set to 10 for less detail, or set as 58 for more details in the output.)




