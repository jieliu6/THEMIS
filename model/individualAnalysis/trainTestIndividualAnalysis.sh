#$ -S /bin/bash
#$ -N jobNameTag
#$ -cwd
# author: Jie Liu and John T. Halloran
# contact: liu6@uw.edu

while [[ $# -gt 1 ]]
do
key="$1"

case $key in
    -gp|--gmtkPath)
    GMTKPATH="$2"
    shift # past argument
    ;;
    -sd|--sourceDir)
    SOURCEDIR="$2"
    shift # past argument
    ;;
    -md|--modelDir)
    MODELDIR="$2"
    shift # past argument
    ;;
    -trf|--trainFile)
    TRAIN="$2"
    shift # past argument
    ;;
    -tef|--testFile)
    TEST="$2"
    shift # past argument
    ;;
    -odf|--originalDataFile)
    ORIGINALDATAFILE="$2"
    shift # past argument
    ;;
    -tdf|--tempDataFile)
    TEMPDATAFILE="$2"
    shift # past argument
    ;;
    -nf|--numFloats)
    NUMFLOATS="$2"
    shift # past argument
    ;;
    -ni|--numInts)
    NUMINTS="$2"
    shift # past argument
    ;;
    -bn|--biopsyNum)
    BIOPSYNUM="$2"
    shift # past argument
    ;;
    -bi|--indexOfBiopsy)
    INDEXB="$2"
    shift # past argument
    ;;
    -gn|--genotypeNum)
    GENOTYPENUM="$2"
    shift # past argument
    ;;
    -zn|--zNum)
    ZNUM="$2"
    shift # past argument
    ;;
    -pln|--prevalenceLevelNum)
    PREVALENCELEVELNUM="$2"
    shift # past argument
    ;;
    -pl|--prevalenceLevel)
    PREVALENCELEVEL="$2"
    shift # past argument
    ;;
    -c|--cValue)
    CVALUE="$2"
    shift # past argument
    ;;
    -tvg|--transitionValueG)
    TRANSITIONG="$2"
    shift # past argument
    ;;
    -tvz|--transitionValueZ)
    TRANSITIONZ="$2"
    shift # past argument
    ;;
    -l|--lst)
    LSTVALUE="$2"
    shift # past argument
    ;;
    -v|--verbosity)
    VERBOSITY="$2"
    shift # past argument
    ;;
    --default)
    DEFAULT=YES
    ;;
    *)
            # unknown option
    ;;
esac
shift # past argument or value
done
echo GMTK PATH  = "${GMTKPATH}"
echo SOURCE CODE DIRECTORY  = "${SOURCEDIR}"
echo MODEL DIRECTORY  = "${MODELDIR}"
echo TRAIN DATA FILE     = "${TRAIN}"
echo TEST DATA FILE     = "${TEST}"
echo ORIGINAL DATA FILE     = "${ORIGINALDATAFILE}"
echo TEMP DATA FILE     = "${TEMPDATAFILE}"
echo NUM OF FLOATS    = "${NUMFLOATS}"
echo NUM OF INTS    = "${NUMINTS}"
echo BIOPSY NUMBER    = "${BIOPSYNUM}"
echo BIOPSY INDEX    = "${INDEXB}"
echo GENOTYPE NUMBER    = "${GENOTYPENUM}"
echo Z CARDINALITY    = "${ZNUM}"
echo PREVALENCE LEVEL NUMBER    = "${PREVALENCELEVELNUM}"
echo PREVALENCE LEVEL    = "${PREVALENCELEVEL}"
echo C VALUE    = "${CVALUE}"
echo TRANSITION G    = "${TRANSITIONG}"
echo TRANSITION Z    = "${TRANSITIONZ}"
echo LST VALUE    = "${LSTVALUE}"
echo VERBOSITY    = "${VERBOSITY}"

rm -rf ${MODELDIR}/hmm_factorialModel.str.trifile
${GMTKPATH}/gmtkTriangulate -str ${MODELDIR}/hmm_factorialModel.str -inputM ${MODELDIR}/hmm_factorialModel.mtr $*

javac ${SOURCEDIR}/generatingGausianMeansAL5Copies.java
javac ${SOURCEDIR}/updateOffDiagonalElementsForEMUntieVariancesIndividualAnalysis.java

java -cp ${SOURCEDIR} generatingGausianMeansAL5Copies ${MODELDIR} $GENOTYPENUM $PREVALENCELEVELNUM $PREVALENCELEVEL 1 $CVALUE

maxiters=100
thresh=0.000001

# specify training data file name for GMTK
echo "${TEMPDATAFILE}" > ${MODELDIR}/em_training_data.txt

# specify initial estimate of transition matrix
echo "${TRANSITIONG}" > ${MODELDIR}/em_covar.txt
for((i=1; i<$GENOTYPENUM; i++))
do
echo "${TRANSITIONG}" >> ${MODELDIR}/em_covar.txt
done
for((i=0; i<$ZNUM; i++))
do
echo "${TRANSITIONZ}" >> ${MODELDIR}/em_covar.txt
done

function covars {
# get covariances from the file
    grep -o 'covar_ggDiag0 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' > ${MODELDIR}/em_covar.txt
    grep -o 'covar_ggDiag1 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> ${MODELDIR}/em_covar.txt
    grep -o 'covar_ggDiag2 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> ${MODELDIR}/em_covar.txt
    grep -o 'covar_ggDiag3 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> ${MODELDIR}/em_covar.txt
    grep -o 'covar_ggDiag4 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> ${MODELDIR}/em_covar.txt
    grep -o 'covar_ggDiag5 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> ${MODELDIR}/em_covar.txt
    grep -o 'covar_ggDiag6 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> ${MODELDIR}/em_covar.txt
    grep -o 'covar_ggDiag7 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> ${MODELDIR}/em_covar.txt
    grep -o 'covar_ggDiag8 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> ${MODELDIR}/em_covar.txt
    grep -o 'covar_ggDiag9 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> ${MODELDIR}/em_covar.txt
    grep -o 'covar_ggDiag10 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> ${MODELDIR}/em_covar.txt
    grep -o 'covar_ggDiag11 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> ${MODELDIR}/em_covar.txt
    grep -o 'covar_zzDiag0 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> ${MODELDIR}/em_covar.txt
    grep -o 'covar_zzDiag1 1 .*$' $1 | grep -o '[[:digit:]]*\.[[:digit:]]*.*$' >> ${MODELDIR}/em_covar.txt
}

function test {
    ALIGN="${MODELDIR}/hmm_factorialModel_vitVals.txt"

# get covariances from the file
    covars $1

    java -cp ${SOURCEDIR} updateOffDiagonalElementsForEMUntieVariancesIndividualAnalysis ${MODELDIR}/em_covar.txt $ORIGINALDATAFILE $TEMPDATAFILE $GENOTYPENUM $ZNUM $BIOPSYNUM $INDEXB
 ${GMTKPATH}/gmtkViterbi \
	-strFile ${MODELDIR}/hmm_factorialModel.str \
	-triFile ${MODELDIR}/hmm_factorialModel.str.trifile \
	-inputMasterFile ${MODELDIR}/train_hmm_factorialModel.mtr \
	-inputTrainableParameters $1 \
	-of1 ${MODELDIR}/${TEST} -fmt1 ascii -nf1 $NUMFLOATS -ni1 $NUMINTS  \
	-island T -lst $LSTVALUE \
	-verb $VERBOSITY -vitValsFile $ALIGN

}

function train {
    INITPARAMS="${MODELDIR}/init_hmm_factorialModel.params"

    java -cp ${SOURCEDIR} updateOffDiagonalElementsForEMUntieVariancesIndividualAnalysis ${MODELDIR}/em_covar.txt $ORIGINALDATAFILE $TEMPDATAFILE $GENOTYPENUM $ZNUM $BIOPSYNUM $INDEXB
############################ take first step
    PARAMS="${MODELDIR}/trained_factorialModel_0.params"
    STRFILE="${MODELDIR}/hmm_factorialModel.str"
    TRIFILE="${MODELDIR}/hmm_factorialModel.str.trifile"
    NOTRAINFILE="${MODELDIR}/params.notrain"
    ERRORFILE="${MODELDIR}/err.txt"
    CURRFILE="${MODELDIR}/curr.txt"
    ${GMTKPATH}/gmtkEMtrain \
    	-strFile ${STRFILE} \
    	-triFile ${TRIFILE} \
    	-inputMasterFile ${MODELDIR}/hmm_factorialModel.mtr \
    	-inputTrainableParameters $INITPARAMS \
    	-outputTrainableParameters $PARAMS \
    	-of1 ${MODELDIR}/${TRAIN} -fmt1 ascii -nf1 $NUMFLOATS -ni1 $NUMINTS -dirichletPriors T \
    	-maxE 1 -lldp $thresh -objsNotToTrain $NOTRAINFILE -random T \
    	-allocateDenseCpts 2 \
    	-island T -lst $LSTVALUE \
    	-verb $VERBOSITY \
    	-llStoreFile ${MODELDIR}/ll_iter0.txt \
    	2> $ERRORFILE 1>$CURRFILE


    currLL=$(cat ${MODELDIR}/ll_iter0.txt)
    prevLL="${MODELDIR}/ll_iter0.txt"
    echo "Iteration 0, log-likelihood $currLL"

########### produce data given first iteration
    java -cp ${SOURCEDIR} updateOffDiagonalElementsForEMUntieVariancesIndividualAnalysis ${MODELDIR}/em_covar.txt $ORIGINALDATAFILE $TEMPDATAFILE $GENOTYPENUM $ZNUM $BIOPSYNUM  $INDEXB
    covars $PARAMS
    INITPARAMS=$PARAMS
    # create master file for iterations > 0, where 
    # we avoid double defining the Gaussian means, covariances, and components
    grep -v 'COVAR_IN_FILE\|DPMF_IN_FILE\|MEAN_IN_FILE\|MC_IN_FILE\|MX_IN_FILE' ${MODELDIR}/hmm_factorialModel.mtr > ${MODELDIR}/train_hmm_factorialModel.mtr

######################## iteratre til convergence
    for((i=1; i<$maxiters; i++))
    do
	oldLL=$currLL
	PARAMS="${MODELDIR}/trained_factorialModel_${i}.params"

	echo "$INITPARAMS $PARAMS"
	$GMTKPATH/gmtkEMtrain \
	    -strFile ${MODELDIR}/hmm_factorialModel.str \
	    -triFile ${MODELDIR}/hmm_factorialModel.str.trifile \
	    -inputMasterFile ${MODELDIR}/train_hmm_factorialModel.mtr \
	    -inputTrainableParameters $INITPARAMS \
	    -outputTrainableParameters $PARAMS \
	    -of1 ${MODELDIR}/${TRAIN} -fmt1 ascii -nf1 $NUMFLOATS -ni1 $NUMINTS -dirichletPriors T \
	    -maxE 1 -lldp $thresh -objsNotToTrain ${MODELDIR}/params.notrain \
	    -llStoreFile ${MODELDIR}/ll_iter${i}.txt \
    	-island T -lst $LSTVALUE \
    	-verb $VERBOSITY \
	    2> $ERRORFILE 1>$CURRFILE

	currLL=$(cat ${MODELDIR}/ll_iter${i}.txt)
	echo "Iteration $i, log-likelihood $currLL"

	covars $PARAMS

	INITPARAMS=$PARAMS

    # check convergence
	echo "currLL: $currLL,  oldLL: $oldLL"
	LLratio=$(python ${SOURCEDIR}/llRat.py --currLL ${MODELDIR}/ll_iter${i}.txt --oldLL $prevLL --tol $thresh --return_ratio)
	echo "Iteration $i, log-likelihood ratio $LLratio"
	converged=$(python ${SOURCEDIR}/llRat.py --currLL ${MODELDIR}/ll_iter${i}.txt --oldLL $prevLL --tol $thresh)
	echo $converged
	if (($converged))
	then
	    echo "Converged after $i iterations"

	    echo "Testing using trained parameters"
	    test $PARAMS
	    break
	fi

########### produce data given most recent iteration
	java -cp ${SOURCEDIR} updateOffDiagonalElementsForEMUntieVariancesIndividualAnalysis ${MODELDIR}/em_covar.txt $ORIGINALDATAFILE $TEMPDATAFILE $GENOTYPENUM $ZNUM $BIOPSYNUM $INDEXB
	prevLL="${MODELDIR}/ll_iter${i}.txt"
    done
}

train
