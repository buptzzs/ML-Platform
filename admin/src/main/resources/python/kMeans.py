import pickle
from sklearn import cluster
from sklearn.metrics import adjusted_rand_score
from sklearn.externals.joblib import dump, load
import argparse
import os
import numpy as np
import pandas as pd
from sklearn.model_selection import ShuffleSplit
model = 'cluster'


def main(args):
    model_name = args.model_name
    model_dir = os.path.join(args.root, "model")  # get model dir
    data_dir = os.path.join(args.root, "data")  # get data dir

    data_path = os.path.join(data_dir, args.inFile)
    print('load data from'+data_path)
    #data_path = 'diabetes.dataset'
    data = pickle.load(open(data_path, 'rb'))
    out_path = os.path.join(data_dir, args.outFileName+'.csv')
    assert 'data' in data
    if args.train:
        k = args.n_clusters
        regr = cluster.KMeans(n_clusters=k, random_state='random_state',n_init = args.n_init,max_iter=args.max_iter)


        features = data['data']

        pred = regr.fit_predict(features)

        df = pd.DataFrame({
            'pred': pred,
            'target': features,
        })
        print(f'validation results save to:{args.outFileName}.csv')
        df.to_csv(out_path)
        print("Some results of validation:")
        print(df.head())
        
        model_path = os.path.join(model_dir,f'{model_name}_{model}.model')
        dump(regr, model_path)
    else:
        # TODO: How to Save the prediction?
        model_path = os.path.join(model_dir,args.model_path)
        clf = load(args.model)
        x = data['data']
        pred = clf.predict(x)
        df = pd.DataFrame({
            'pred': pred,
        })        
        df.to_csv(out_path)

if __name__ == '__main__':

    parser = argparse.ArgumentParser()

    parser.add_argument('--inFile', type=str, help='input file path')
    parser.add_argument('--outFileName', type=str, help="output file's name")
    parser.add_argument('--root', type=str, help="file root")
    parser.add_argument('--train', type=bool, default=True)
    parser.add_argument('--model_name', type=str)
    parser.add_argument('--model_path', type=str)

    parser.add_argument('--n_clusters', type=int, default=0)
    parser.add_argument('--n_int', type=int, default=10)
    parser.add_argument('--max_iter', type=int, default=300)
    args = parser.parse_args()
    print(args)

    main(args)
