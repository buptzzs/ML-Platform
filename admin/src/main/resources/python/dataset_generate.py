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
        raise "不支持的文件类型，仅支持csv,json"

    cols = list(df.columns)

    if args.label_col not in cols:
        print(f'label列{args.label_col},不存在，将被忽略')
        feature_cols = cols
        label_col = None
    else:
        label_col = args.label_col
        print(f'label column:{label_col}')
        cols.remove(args.label_col)
        feature_cols = cols

    data = []
    for col in cols:
        data.append(df[col].tolist())
    feature_list = []
    for record in zip(*data):
        d = {}
        for i in range(len(record)):
            d[cols[i]] = record[i]
        feature_list.append(d)
    v = DictVectorizer(sparse=False)
    features = v.fit_transform(feature_list)

    result = {}
    result['data'] = features
    if label_col is not None:
        label = np.array(df[label_col].tolist())
        result['target'] = label
    
    pickle.dump(result, open(out_path+'.dataset','wb'))
    print(f'dataset save to{out_path}.dataset')

if __name__ == '__main__':

    parser = argparse.ArgumentParser()

    parser.add_argument('--inFile', type=str, help='input file path')
    parser.add_argument('--outFileName', type=str, help="output file's name")
    parser.add_argument('--root', type=str, help="file root")
    parser.add_argument('--label_col', type=str, default='label')

    args = parser.parse_args()
    print(args)

    main(args)
