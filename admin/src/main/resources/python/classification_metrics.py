import pickle
from sklearn import linear_model
from sklearn.metrics import  accuracy_score, f1_score
from sklearn.externals.joblib import dump, load
import argparse
import os
import numpy as np
import pandas as pd



def main(args):
    data_dir = os.path.join(args.root, "data")  # get data dir

    data_path = os.path.join(data_dir, args.inFile)
    print('load data from'+data_path)
    df = pd.read_csv(data_path)
    pred = df['pred'].tolist()
    target = df['target'].tolist()

    print('Evaluation:')
    print(f'Data Size:{df.size}')
    acc = accuracy_score(pred, target)
    f1 = f1_score(pred, target, average=None)
    print(f'Accuracy:{acc}, f1 score for each class:{f1}')


if __name__ == '__main__':

    parser = argparse.ArgumentParser()

    parser.add_argument('--inFile', type=str, help='input file path')
    parser.add_argument('--outFileName', type=str, help="output file's name")
    parser.add_argument('--root', type=str, help="file root")

    args = parser.parse_args()
    print(args)

    main(args)
