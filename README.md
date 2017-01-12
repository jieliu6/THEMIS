# THEMIS
Tumor Heterogeneity Extensible Modeling via an Integrative System

The whole package includes *pre-modeling data preprocessing code*, *model source code and running script*, and *post-modeling result visualization code*. 


## Data preprocessing code

We assume that bam files of the biopsies (and the corresponding index files) are available for analysis. 
Nine preprocessing steps are needed in order to generate input files for THEMIS.

1. Identify germline heterozygous sites

2. Identify SNVs 

3. Generate bed files for heterozygous sites and homozygous sites

4. perform MuTect and extract read counts from bam files

5. process MuTect raw result  

6. generate wig file from bam file with HMMcopy 

7. correct gc content and mappability

8. Combine MuTect processed data and HMMcopy processed data

9. Generate data files for GMTK


## Source code of THEMIS model

1. download and install GMTK

2. download THEMIS model specification files and running scripts

3. run THEMIS

4. process results


## Result processing code

The R plotting code can be download here.






