import argparse
import os
import numpy as np
import pandas as pd
from sklearn.impute import SimpleImputer
def main(args):
    data_dir = os.path.join(args.root, "data")  # get data dir

    data_path = os.path.join(data_dir, args.inFile)
    out_path = os.path.join(data_dir, args.outFileName)

    params = args.strategy[1:-1].replace('{','').split('},') #解析json字符串
    columns_param = []
    for param in params:
        column_param = {}
        key_values = param.split(",")
        print(key_values)
        for key_value in key_values:
            item = key_value.replace('}','').split(':')
            column_param[item[0]] = item[1]
        columns_param.append(column_param)

    # 这里暂时不对缺失值做要求
    if(data_path.endswith('.csv')):
        df = pd.read_csv(data_path)
    elif data_path.endswith('.json'):
        df = pd.read_json(data_path)
    else:
        raise "不支持的文件类型，仅支持csv,json"

    for column_param in columns_param:
        column = column_param['column']
        strategy = column_param['value']
        print(f'fill column {column} with strategy {strategy}')
        if strategy == 'mean':
            df[column] = df[column].fillna(df[column].mean())
        elif strategy == '0':
            df[column] = df[column].fillna(0)
        elif strategy == 'bfill':
            df[column] = df[column].fillna(method='bfill')
        elif strategy == 'ffill':
            df[column] = df[column].fillna(method='ffill')
        else:
            raise "错误的参数"

    df.to_json(out_path+'.json', orient="records")

if __name__ == '__main__':

    parser = argparse.ArgumentParser()

    parser.add_argument('--inFile', type=str, help='input file path')
    parser.add_argument('--outFileName', type=str, help="output file's name")
    parser.add_argument('--root', type=str, help="file root")
    parser.add_argument('--strategy', type=str, default='') # [['',''],['','']]


    args = parser.parse_args()
    print(args)

    main(args)
