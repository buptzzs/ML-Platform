import argparse
import os
import numpy as np
import pandas as pd
from sklearn.impute import SimpleImputer

def main(args):
    data_dir = os.path.join(args.root, "data")  # get data dir

    data_path = os.path.join(data_dir, args.inFile)
    out_path = os.path.join(data_dir, args.outFileName)

    # 这里暂时不对缺失值做要求
    if(data_path.endswith('.csv')):
        df = pd.read_csv(data_path)
    elif data_path.endswith('.json'):
        df = pd.read_json(data_path)
    else:
        raise "不支持的文件类型，仅支持csv,json"
    if args.strategy == 'mean':
        df = df.fillna(df.mean())
    elif args.strategy == '0':
        df = df.fillna(0)
    elif args.strategy == 'bfill':
        df = df.fillna(method='bfill')
    elif args.strategy == 'ffill':
        df = df.fillna(method='ffill')

    df.to_json(out_path+'.json', orient="records")

if __name__ == '__main__':

    parser = argparse.ArgumentParser()

    parser.add_argument('--inFile', type=str, help='input file path')
    parser.add_argument('--outFileName', type=str, help="output file's name")
    parser.add_argument('--root', type=str, help="file root")
    parser.add_argument('--strategy', type=str, default='mean')


    args = parser.parse_args()
    print(args)

    main(args)
