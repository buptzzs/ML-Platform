import argparse
import os
import numpy as np
import pandas as pd
from sklearn.impute import SimpleImputer
import pickle
from sklearn.feature_extraction import DictVectorizer


def main(args):
    data_dir = os.path.join(args.root, "data")  # get data dir

    data_path = os.path.join(data_dir, args.inFile)
    out_path = os.path.join(data_dir, args.outFileName)

    if(data_path.endswith('.csv')):
        df = pd.read_csv(data_path)
    elif data_path.endswith('.json'):
        df = pd.read_json(data_path)
    else:
        raise "unsuport type! please use csv,json"

    cols = list(df.columns)

    del_cols = args.col
    del_cols = set([col.strip() for col in args.col.split(',')])

    for col in del_cols:
        if col not in cols:
            print(f'column {args.col} not exist, this operation will be ignored')
        else:
            print(u"before delete")
            print(df.head())
            print(f'delete:{col}')
            print("after delete:")
            df = df.drop(col, axis=1)
            print(df.head())

    df.to_json(out_path+'.json', orient="records")

if __name__ == '__main__':

    parser = argparse.ArgumentParser()

    parser.add_argument('--inFile', type=str, help='input file path')
    parser.add_argument('--outFileName', type=str, help="output file's name")
    parser.add_argument('--root', type=str, help="file root")
    parser.add_argument('--col', type=str, default='')

    args = parser.parse_args()
    print(args)

    main(args)
