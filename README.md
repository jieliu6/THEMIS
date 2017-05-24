# File organization

## Source code (under ./src/):  java code for file generation
* `generatingGausianMeansAL5Copies.java`  // generate Gaussian means for different genotypes and prevalence levels
* `generatingGausianMeansAL5CopiesNoCNAs.java`  // generate Gaussian means for different genotypes and prevalence levels when there is no CNA event
* `updateOffDiagonalElementsForEMUntieVariances.java`  // update the diagonal elements in the transition matrices and process the input as the required format for GMTK, this piece of code is used for joint analysis of multiple biopsies 
* `updateOffDiagonalElementsForEMUntieVariancesIndividualAnalysis.java` // update the diagonal elements in the transition matrices and process the input as the required format for GMTK, this piece of code is used for individual biopsy analysis
* `llRat.py` // compare log-likelihood with that from last iteration 

## Dataset (under ./data/): 
Example dataset used by THEMIS is `combinedData_01-1-B2_01-2-B2_01-4-B3.txt`

## Documentation (under ./doc/): 
The documentation of the source code and model specification files are provided in `doc.md`

## Model specification files (under `./model/`):
* `hmm_factorialModel.mtr`  // master file which specifies the parameter of the Dynamic Bayesian Network (DBN)
* `hmm_factorialModel.str`  // structure file which specifies the structure of the DBN
* `constants.inc`  // constants used in the structure file and master file
* `initCovars.txt`  // initial values for covariances in the Gaussians
* `initDpmfs.txt`  // initial values for Gaussian mixtures
* `init_hmm_factorialModel.params`  // initial values for other parameters
* `params.notrain`  // which parameters are not to be estimated. If a parameter is specified in this file, it will be fixed at its initial value rather than being estimated
* `trainTestIndividualAnalysis.sh` // contains the script for training the model and testing the model for individual biopsy analysis.
* `trainTestJointAnalysis.sh` // contains the script for training the model and testing the model for multiple biopsy joint analysis.

## Tutorial (under ./tutorial/)
A tutorial for replicating our analysis on the sample dataset is provided in `tutorial.md`

## Directory/file explanation is provided in `readme.md`

#### Our manuscript can be accessed via http://biorxiv.org/content/early/2017/04/06/125138

