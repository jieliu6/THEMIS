## Step 1. Download and install GMTK

The Graphical Models Toolkit (GMTK) is an open source, publicly available toolkit for rapidly prototyping statistical models using dynamic graphical models (DGMs) and dynamic Bayesian networks (DBNs). 
It can be download at its [website](http://melodi.ee.washington.edu/gmtk/). Please follow its instruction to download and install.
Please note that GMTK is for Linux only. 
Therefore, our THEMIS model can only be run on a Linux machine.

## Step 2. Prepare input file 

Please refer to __Input data files__ section in ./doc/doc.md for the format of the input data files.
One example data file is /data/combinedData_01-1-B2_01-2-B2_01-4-B3.txt.
If your data file is already in this format, please go forward to Step 3.
If you start with the bam files of the biopsies (and the corresponding index files) for analysis, please follow the nine preprocessing steps in order to generate input files for THEMIS.

2.1. Identify germline heterozygous sites

e.g. samtools mpileup -uv -I -f normal.bam | bcftools call -v -c - | grep -e "#" -e "0/1" > normHetSNPs.vcf

2.2. Identify SNVs with MuTect 

2.3. Generate bed files for heterozygous sites and homozygous sites

e.g. germlineHeteroSites.bed and snvSites.bed which will be used in the next step

2.4. perform MuTect (version 1.1.7) 

java -Xmx20g -jar /MUTECTDIR/mutect-1.1.7.jar --analysis_type MuTect --reference_sequence human_g1k_v37.fa --dbsnp dbsnp_138.b37.vcf --intervals germlineHeteroSites.bed --cosmic b37_cosmic_v54_120711.vcf --input_file:normal normal.bam --input_file:tumor tumor.bam --out /MUTECTRESULTDIR/MuTect.call_stats_BIOPSYID_heteroPositions.txt --coverage_file /MUTECTRESULTDIR/MuTect.coverage.wig_BIOPSYID_heteroPositions.txt --fraction_contamination 0 --force_output --force_alleles

java -Xmx20g -jar /MUTECTDIR/mutect-1.1.7.jar --analysis_type MuTect --reference_sequence human_g1k_v37.fa --dbsnp dbsnp_138.b37.vcf --intervals snvSites.bed --cosmic b37_cosmic_v54_120711.vcf --input_file:normal normal.bam --input_file:tumor tumor.bam --out /MUTECTRESULTDIR/MuTect.call_stats_BIOPSYID_snvPositions.txt --coverage_file /MUTECTRESULTDIR/MuTect.coverage.wig_BIOPSYID_snvPositions.txt --fraction_contamination 0 --force_output --force_alleles

2.5. process MuTect raw result and extract read counts from bam files

e.g. extract read counts from MuTect.call_stats_BIOPSYID_heteroPositions.txt and MuTect.call_stats_BIOPSYID_snvPositions.txt from last step

2.6. Use HMMcopy to correct gc content and mappability (please fellow HMMcopy's instruction)

2.7. Combine MuTect processed data (in step 2.5) and HMMcopy processed data (in step 2.6) and prepare data file for GMTK (follow the example data file)

## Step 3. Execute shell scripts for model training/testing
Note that if you have multiple biopsies (usually from the same patient) to analyze at the same time (termed *joint analysis*), you have to analyze individual biopsies separately (termed *individual analysis*) first.
In the individual analysis, we can understand the number of clones within each biopsy, the prevalence of clones and the genotype of each clone.
Then we can enumerate all possible phylogenetic structures for joint analysis.
The candidate phylogenetic structure should be specified as a text file named *structure.txt* in the same directory as joint analysis.
If you only have one biopsy to analyze, please perform individual analysis directly.
For individual analysis, you only have to create an empty directory (with a directory name you like).

* `trainTestIndividualAnalysisAuto.sh` contains the script for training the model and testing the model for individual biopsy analysis.
* `trainTestJointAnalysisAuto.sh` contains the script for training the model and testing the model for multiple biopsy joint analysis.

A number of options are available for running the scripts, such as the input data files, the model parameters, and some setting parameters for GMTK.
Please refer to __Shell script files__ section in doc/doc.md for these options, which can make the use of our code easy.
For reproduce our analysis on the example dataset *combinedData_01-1-B2_01-2-B2_01-4-B3.txt*, example scripts are as follows.

Example 1 (individual analysis): ./trainTestIndividualAnalysisAuto.sh -gp /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/tools/gmtk1.4.0/bin/ -trf em_training_data.txt -tef em_training_data.txt -odf /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/src/GitHubTHEMIS/THEMIS/data/combinedData_01-1-B2_01-2-B2_01-4-B3.txt -tdf /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/src/GitHubTHEMIS/THEMIS/data/trainWithLogSiteDistance_01-1-B2.txt -sd /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/src/GitHubTHEMIS/THEMIS/src/ -md /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/src/GitHubTHEMIS/THEMIS/model/individualAnalysisAuto/ -bn 3 -bi 0 -gn 12 -zn 2 -pln 20 -pl 0.05 -c 0.18 -tvg 70 -tvz 70 -l 6000 -v 10 

Example 2 (joint analysis): ./trainTestJointAnalysisAuto.sh -gp /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/tools/gmtk1.4.0/bin/ -trf em_training_data.txt -tef em_training_data.txt -odf /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/src/GitHubTHEMIS/THEMIS/data/combinedData_01-1-B2_01-2-B2_01-4-B3.txt -tdf /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/src/GitHubTHEMIS/THEMIS/data/trainWithLogSiteDistance_01-1-B2_01-2-B2_01-4-B3.txt -sd /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/src/GitHubTHEMIS/THEMIS/src/ -md /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/src/GitHubTHEMIS/THEMIS/model/jointAnalysisAuto/ -bn 3 -cn 6 -gn 12 -zn 6 -pln 20 -pl 0.05 -c 0.18~0.093~0.113 -ps 6~14~3~8~5~13 -tvg 70 -tvz 70 -l 15000 -v 10 

## Step 4. Understand the output of THEMIS (i.e. Viterbi path) 

The outputs of THEMIS are in the directories `./model/jointAnalysisAuto/` and `./model/individualAnalysisAuto/`, including

* `hmm_factorialModel_vitVals.txt`  // Viterbi path inferred from THEMIS, which contains the genotype at each genomic site and the clone in which the mutation occurs
* `curr.txt`  // screen print from GMTK
* `err.txt`  // error messages from GMTK
* `em_covar.txt`  // estimated parameters for transition of G and Z variables. The first |G| rows are for \sigma^2_{G,j} and the next |Z| rows are for \sigma^2_{Z,j}.
* `jt_info.txt`  // junction tree, automatically generated by GMTK
* `ll_iter{k}.txt`  // log likelihood yielded in the k-th iteration  
* `trained_factorialModel_{k}.params`  // parameters estimated in the k-th iteration  


