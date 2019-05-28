import argparse
import os
import numpy as np
import pandas as pd
import pickle
from sklearn.preprocessing import normalize

def main(args):
    data_dir = os.path.join(args.root, "data")  # get data dir

    data_path = os.path.join(data_dir, args.inFile)
    out_path = os.path.join(data_dir, args.outFileName)
    data = pickle.load(open(data_path, 'rb'))
    out_path = os.path.join(data_dir, args.outFileName+'.dataset')
    # 这里暂时不对缺失值做要求

    if not (data_path.endswith('.dataset')):
        raise "输入格式必须为dataset"
    assert 'data' in data 
    data['data'] = normalize(data['data'], norm=args.norm)
    print(f'normalize features with norm type:{args.norm}')
    pickle.dump(data, open(out_path,'wb'))
    print('done')

if __name__ == '__main__':

    parser = argparse.ArgumentParser()

    parser.add_argument('--inFile', type=str, help='input file path')
    parser.add_argument('--outFileName', type=str, help="output file's name")
    parser.add_argument('--root', type=str, help="file root")
    parser.add_argument('--norm', type=str, default='l2')

    args = parser.parse_args()
    print(args)

    main(args)
