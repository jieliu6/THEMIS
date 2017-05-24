## Step 1. download and install GMTK

The Graphical Models Toolkit (GMTK) is an open source, publicly available toolkit for rapidly prototyping statistical models using dynamic graphical models (DGMs) and dynamic Bayesian networks (DBNs). 
It can be download at its [website](http://melodi.ee.washington.edu/gmtk/). Please follow its instruction to download and install.
Please note that GMTK is for Linux only. 
Therefore, our THEMIS model can only be run on a Linux machine.

## Step 2. follow preprocessing steps and prepare input file 

If you start with the bam files of the biopsies (and the corresponding index files) for analysis, please follow the nine preprocessing steps in order to generate input files for THEMIS.
One example data file is /data/combinedData_01-1-B2_01-2-B2_01-4-B3.txt.
Please refer to __Input data files__ section in ./doc/doc.md for the format of the input data files.
If your data file is already in this format, please go forward to Step 3.

1. Identify germline heterozygous sites

2. Identify SNVs 

3. Generate bed files for heterozygous sites and homozygous sites

4. perform MuTect and extract read counts from bam files

5. process MuTect raw result  

6. generate wig file from bam file with HMMcopy 

7. correct gc content and mappability

8. Combine MuTect processed data and HMMcopy processed data

9. Generate data files for GMTK

## Step 3. set up model specification files (e.g. structure files and master files). 
Note that if you have multiple biopsies (usually from the same patient) to analyze at the same time (termed *joint analysis*), you have to analyze individual biopsies separately (termed *individual analysis*) first.
In the individual analysis, we can understand the number of clones within each biopsy and the prevalence of clones. 
Then we can enumerate all possible phylogenetic structures for joint analysis.
The files for joint analysis are in the directory `./model/jointAnalysis/`. 
The files for individual analysis are in the directory `./model/individualAnalysis/`.
If you only have one biopsy to analyze, please just use the files under `./model/individualAnalysis/`.

In the directories `./model/jointAnalysis/` and `./model/individualAnalysis/`, users have to provide model specification files, as follows. 
Please refer to doc/doc.md for documentation for these files.

* `hmm_factorialModel.mtr`   master file which specifies the parameters of the DBN
* `hmm_factorialModel.str`   structure file which specifies the structure of the DBN
* `constants.inc`   constant used in the structure file and master file
* `initCovars.txt`   initial values for covariance in the Gaussians
* `initDpmfs.txt`   initial values for Gaussian mixtures
* `init_hmm_factorialModel.params`   initial values for other parameters
* `params.notrain`   which parameters are not to be estimated. If a parameter is specified in this file, it will be fixed at its initial value rather than being estimated

Some java code is automatically called (in the shell scripts in next step) to generate a number of files which define the Gaussian distributions used by THEMIS.
These files will also be automatically generated into the same directory as the model specification files.
Please users may refer to __Input data files__ section doc/doc.md for documentation for these files, but users do not have to make any change since this part is automatically in the shell scripts in next step.

## Step 4. execute shell scripts for model training/testing

* `trainTestIndividualAnalysis.sh` contains the script for training the model and testing the model for individual biopsy analysis.
* `trainTestJointAnalysis.sh` contains the script for training the model and testing the model for multiple biopsy joint analysis.

A number of options are available for running the scripts, such as the input data files, the model parameters, and some setting parameters for GMTK.
Please refer to __Shell script files__ section in doc/doc.md for these options, which can make the use of our code easy.
For replicate our analysis on the example dataset, examples are as follows.

Example 1 (individual analysis): ./trainTestIndividualAnalysis.sh -gp /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/tools/gmtk1.4.0/bin/ -trf em_training_data.txt -tef em_training_data.txt -odf /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/src/GitHubTHEMIS/THEMIS/data/combinedData_01-1-B2_01-2-B2_01-4-B3.txt -tdf /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/src/GitHubTHEMIS/THEMIS/data/trainWithLogSiteDistance_01-1-B2.txt -sd /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/src/GitHubTHEMIS/THEMIS/src/ -md /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/src/GitHubTHEMIS/THEMIS/model/individualAnalysis/ -fn 31 -fi 2 -bn 3 -bi 0 -gn 12 -zn 2 -pln 20 -pl 0.05 -c 0.18 -l 10000 -v 10 

Example 2 (joint analysis): ./trainTestJointAnalysis.sh -gp /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/tools/gmtk1.4.0/bin/ -trf em_training_data.txt -tef em_training_data.txt -odf /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/src/GitHubTHEMIS/THEMIS/data/combinedData_01-1-B2_01-2-B2_01-4-B3.txt -tdf /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/src/GitHubTHEMIS/THEMIS/data/trainWithLogSiteDistance_01-1-B2_01-2-B2_01-4-B3.txt -sd /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/src/GitHubTHEMIS/THEMIS/src/ -md /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/src/GitHubTHEMIS/THEMIS/model/jointAnalysis/ -fn 41 -fi 8 -bn 3 -cn 6 -gn 12 -zn 5 -pln 20 -pl 0.05 -c 0.18~0.093~0.113 -ps 6~14~3~8~5~13 -l 15000 -v 10 


## Step 5. Understand the output of THEMIS (i.e. Viterbi path) 

The outputs of THEMIS are in the same directories `./model/jointAnalysis/` and `./model/individualAnalysis/`, including

* `hmm_factorialModel_vitVals.txt`  // Viterbi path inferred from THEMIS, which contains the genotype at each genomic site and the clone in which the mutation occurs
* `curr.txt`  // screen print from GMTK
* `err.txt`  // error messages from GMTK
* `em_covar.txt`  // estimated parameters for transition of G and Z variables. The first |G| rows are for \sigma^2_{G,j} and the next |Z| rows are for \sigma^2_{Z,j}.
* `jt_info.txt`  // junction tree, automatically generated by GMTK
* `ll_iter{k}.txt`  // log likelihood yielded in the k-th iteration  
* `trained_factorialModel_{k}.params`  // parameters estimated in the k-th iteration  


