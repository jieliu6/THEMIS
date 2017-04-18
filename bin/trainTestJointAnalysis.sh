#$ -S /bin/bash
#$ -N medUModel4
#$ -cwd
# author: Jie Liu and John T. Halloran
# contact: liu6@uw.edu

javac generatingGausianMeansAL5Copies.java
javac generatingGausianMeansAL5CopiesNoCNAs.java
javac updateOffDiagonalElementsForEMUntieVariances.java

# initialize Gaussians
java generatingGausianMeansAL5Copies 12 20 0.05 3 0.18~0.093~0.113
java generatingGausianMeansAL5CopiesNoCNAs 12 20 0.05 3 0.18~0.093~0.113

DATA="updateOffDiagonalElementsForEMUntieVariances subject1 4 3 2 6~14~3~8~5~13"
NUMFLOATS="41"

EMTRAIN="/net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/tools/gmtk1.4.0/bin/gmtkEMtrain"
maxiters=100
thresh=0.000005


function covars {
# get covariances from the file
    grep -o 'covar_ggDiag0 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' > em_covar.txt
    grep -o 'covar_ggDiag1 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> em_covar.txt
    grep -o 'covar_ggDiag2 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> em_covar.txt
    grep -o 'covar_ggDiag3 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> em_covar.txt
    grep -o 'covar_ggDiag4 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> em_covar.txt
    grep -o 'covar_ggDiag5 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> em_covar.txt
    grep -o 'covar_ggDiag6 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> em_covar.txt
    grep -o 'covar_ggDiag7 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> em_covar.txt
    grep -o 'covar_ggDiag8 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> em_covar.txt
    grep -o 'covar_ggDiag9 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> em_covar.txt
    grep -o 'covar_ggDiag10 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> em_covar.txt
    grep -o 'covar_ggDiag11 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> em_covar.txt
    grep -o 'covar_zzDiag0 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> em_covar.txt
    grep -o 'covar_zzDiag1 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> em_covar.txt
    grep -o 'covar_zzDiag2 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> em_covar.txt
    grep -o 'covar_zzDiag3 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> em_covar.txt
    grep -o 'covar_zzDiag4 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> em_covar.txt
}

function test {
    TEST="em_training_data.txt"
    ALIGN="hmm_factorialModel_vitVals.txt"

# get covariances from the file
    covars $1

    java $DATA
 /net/noble/vol1/home/liu6/extendSpace/proj/2015itomic/tools/gmtk1.4.0/bin/gmtkViterbi \
	-strFile hmm_factorialModel.str \
	-triFile hmm_factorialModel.str.trifile \
	-inputMasterFile train_hmm_factorialModel.mtr \
	-inputTrainableParameters $1 \
	-of1 $TEST -fmt1 ascii -nf1 $NUMFLOATS -ni1 8  \
	-island T -lst 20000 \
	-verbosity 10 -vitValsFile $ALIGN

}

function train {
    TRAIN="em_training_data.txt"
    INITPARAMS="init_hmm_factorialModel.params"

    java $DATA
# take first step
    PARAMS="trained_factorialModel_0.params"
    $EMTRAIN \
    	-strFile hmm_factorialModel.str \
    	-triFile hmm_factorialModel.str.trifile \
    	-inputMasterFile hmm_factorialModel.mtr \
    	-inputTrainableParameters $INITPARAMS \
    	-outputTrainableParameters $PARAMS \
    	-of1 $TRAIN -fmt1 ascii -nf1 $NUMFLOATS -ni1 8 -dirichletPriors T \
    	-maxE 1 -lldp $thresh -objsNotToTrain params.notrain -random T \
    	-allocateDenseCpts 2 \
    	-island T -lst 20000 -verb 58 \
    	-llStoreFile ll_iter0.txt \
    	2> err.txt 1>curr.txt

    currLL=$(cat ll_iter0.txt)
    prevLL="ll_iter0.txt"
    echo "Iteration 0, log-likelihood $oldLL"

########### produce data given first iteration
    java $DATA

    covars $PARAMS

    INITPARAMS=$PARAMS

    # create master file for iterations > 0, where 
    # we avoid double defining the Gaussian means, covariances, and components
    grep -v 'COVAR_IN_FILE\|DPMF_IN_FILE\|MEAN_IN_FILE\|MC_IN_FILE\|MX_IN_FILE' hmm_factorialModel.mtr > train_hmm_factorialModel.mtr


######################## iteratre til convergence
    for((i=1; i<$maxiters; i++))
    do
	oldLL=$currLL
	PARAMS="trained_factorialModel_${i}.params"

	echo "$INITPARAMS $PARAMS"
	$EMTRAIN \
	    -strFile hmm_factorialModel.str \
	    -triFile hmm_factorialModel.str.trifile \
	    -inputMasterFile train_hmm_factorialModel.mtr \
	    -inputTrainableParameters $INITPARAMS \
	    -outputTrainableParameters $PARAMS \
	    -of1 $TRAIN -fmt1 ascii -nf1 $NUMFLOATS -ni1 8 -dirichletPriors T \
	    -maxE 1 -lldp $thresh -objsNotToTrain params.notrain \
	    -llStoreFile ll_iter${i}.txt \
	    -island T -lst 20000 -verb 58 \
	    2> err.txt 1>curr.txt

	currLL=$(cat ll_iter${i}.txt)
	echo "Iteration $i, log-likelihood $currLL"

	covars $PARAMS

	INITPARAMS=$PARAMS

    # check convergence
	echo "currLL: $currLL,  oldLL: $oldLL"
	LLratio=$(python llRat.py --currLL ll_iter${i}.txt --oldLL $prevLL --tol $thresh --return_ratio)
	echo "Iteration $i, log-likelihood ratio $LLratio"
	converged=$(python llRat.py --currLL ll_iter${i}.txt --oldLL $prevLL --tol $thresh)
	echo $converged
	if (($converged))
	then
	    echo "Converged after $i iterations"

	    echo "Testing using trained parameters"
	    test $PARAMS
	    break
	fi

########### produce data given most recent iteration
	java $DATA
	prevLL="ll_iter${i}.txt"
    done
}

train
