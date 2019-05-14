import pickle
from sklearn import linear_model
from sklearn.metrics import accuracy_score, f1_score, precision_recall_curve, auc, roc_curve
from sklearn.externals.joblib import dump, load
import argparse
import os
import numpy as np
import pandas as pd
import json


def main(args):
    data_dir = os.path.join(args.root, "data")  # get data dir

    data_path = os.path.join(data_dir, args.inFile)

    metrics_path = os.path.join(args.root, "metrics", args.outFileName)

    print('load data from'+data_path)
    df = pd.read_csv(data_path)
    pred = df['pred'].tolist()
    prob = df['prob'].tolist()
    target = df['target'].tolist()

    eval_result = '*'*8 +'Evaluation' + '*'*10
    print('Evaluation:')
    print(f'Data Size:{df.size}')
    acc = accuracy_score(pred, target)
    f1 = f1_score(pred, target, average=None)
    if args.accuracy == 'true':
        eval_result += '\n' + f'Accuracy:{acc}'
    if args.f1 == 'true':
        eval_result += '\n' + f'F1:{f1}'
    print(eval_result)
    if args.txt_report == 'true':
        txt_file = open(metrics_path+'.txt','w')
        txt_file.write(eval_result)
        txt_file.close()

    if args.curve == 'roc':
        fpr, tpr, thresholds = roc_curve(target, prob)
        curve_result = {}
        curve_result['rows'] = []
        curve_result['columns'] = ['False Positive Rate','True Positive Rate']
        for i in range(len(fpr)):
             item = {}
             item['False Positive Rate'] = fpr[i]
             item['True Positive Rate'] = tpr[i]
             curve_result['rows'].append(item)
        json.dump(curve_result, open(metrics_path+'.json','w'))
    elif args.curve == 'pr':
        precision, recall, _ = precision_recall_curve(target, prob)
        curve_result = {}
        curve_result['rows'] = []
        curve_result['columns'] = ['Recall', 'Precision']
        for i in range(len(precision)):
             item = {}
             item['Recall'] = recall[i]
             item['Precision'] = precision[i]
             curve_result['rows'].append(item)
        json.dump(curve_result, open(metrics_path+'.json', 'w'))
    else:
        pass    


if __name__ == '__main__':

    parser = argparse.ArgumentParser()

    parser.add_argument('--inFile', type=str, help='input file path')
    parser.add_argument('--outFileName', type=str, help="output file's name")
    parser.add_argument('--root', type=str, help="file root")

    parser.add_argument('--f1', type=str, default='true')
    parser.add_argument('--accuracy', type=str, default='true')

    parser.add_argument('--curve', type=str, default='roc') # PR
    parser.add_argument('--txt_report',type=str, default='true')

    args = parser.parse_args()
    print(args)

    main(args)
