#!/usr/bin/env python
import argparse

if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument("--currLL", type = str)
    parser.add_argument("--oldLL", type = str)
    parser.add_argument("--tol", type = float, default = 0.0001)
    parser.add_argument("--return_ratio", action = "store_true", default = False, dest = "return_ratio")
    args = parser.parse_args()

    fid = open(args.currLL)
    currLL = float(fid.read())
    fid.close()

    fid = open(args.oldLL)
    oldLL = float(fid.read())
    fid.close()

    llRatio = abs(currLL - oldLL) / abs(oldLL)

    if args.return_ratio:
        print llRatio
    else:
        if llRatio < args.tol:
            print "1"
        else:
            print "0"
