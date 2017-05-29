# File organization

## Source code (under ./src/):  java code for file generation
* `generatingGausianMeansAL5Copies.java`  // generate Gaussian means for different genotypes and prevalence levels
* `generatingGausianMeansAL5CopiesNoCNAs.java`  // generate Gaussian means for different genotypes and prevalence levels when there is no CNA event
* `updateOffDiagonalElementsForEMUntieVariances.java`  // update the diagonal elements in the transition matrices and process the input as the required format for GMTK, this piece of code is used for joint analysis of multiple biopsies 
* `updateOffDiagonalElementsForEMUntieVariancesIndividualAnalysis.java` // update the diagonal elements in the transition matrices and process the input as the required format for GMTK, this piece of code is used for individual biopsy analysis
* `generatingModelSpecificationFilesJointAnalysis.java` // generate model specification files for multiple biopsy joint analysis
* `generatingModelSpecificationFilesIndividualAnalysis.java` // generate model specification files for individual biopsy analysis
* `llRat.py` // compare log-likelihood with that from last iteration 

## Dataset (under ./data/): 
Example dataset used by THEMIS is `combinedData_01-1-B2_01-2-B2_01-4-B3.txt`

## Documentation (under ./doc/): 
The documentation of the source code and model specification files are provided in `doc.md`

## Model specification files (under `./model/`):
* `individualAnalysisAuto` // a directory for performing individual biopsy analysis
* `jointAnalysisAuto` // a directory for performing multiple biopsy joint analysis
* `structure.txt`  // a text file which specifies a candidate phylogenetic structure in joint analysis

## Executables (under ./bin/): shell script for performing analysis

* `trainTestIndividualAnalysisAuto.sh` // contains the script for individual biopsy analysis.
* `trainTestJointAnalysisAuto.sh` // contains the script for multiple biopsy joint analysis.

## Tutorial (under ./tutorial/)
A tutorial for replicating our analysis on the sample dataset is provided in `tutorial.md`

## Directory/file explanation is provided in `readme.md`

#### Our manuscript can be accessed via http://biorxiv.org/content/early/2017/04/06/125138

