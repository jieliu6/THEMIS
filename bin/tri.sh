#!/usr/bin/env bash

rm -rf hmm_factorialModel.str.trifile
~/extendSpace/proj/2015itomic/tools/gmtk1.4.0/bin/gmtkTriangulate \
    -str hmm_factorialModel.str -inputM hmm_factorialModel.mtr $*
