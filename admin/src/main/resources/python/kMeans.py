import pickle
from sklearn import cluster
from sklearn.metrics import adjusted_rand_score
from sklearn.externals.joblib import dump, load
import argparse
import os
import pandas as pd
model = 'cluster'


def main(args):
    model_name = args.model_name
    model_dir = os.path.join(args.root, "model")  # get model dir
    data_dir = os.path.join(args.root, "data")  # get data dir

    data_path = os.path.join(data_dir, args.inFile)
    print('load data from'+data_path)
    #data_path = 'diabetes.dataset'
    data = pickle.load(open(data_path, 'rb'))
    assert 'data' in data
    if args.train:
        k = args.n_clusters
        regr = cluster.KMeans(n_clusters=k, random_state='random_state')


        features = data['data']

        pred = regr.fit_predict(features)
        
        model_path = os.path.join(model_dir,f'{model_name}_{model}.model')
        dump(regr, model_path)
    else:
        # TODO: How to Save the prediction?
        model_path = os.path.join(model_dir,args.model)
        regr = load(model_path)
        x = data['data']
        pred = regr.predict(x)
        out_path = os.path.join(data_dir, args.outFileName+'.csv')
        df = pd.DataFrame({
            'pred':pred
        })
        df.to_csv(out_path)
        print('save pred to', args.outFileName+'.csv')
        print('some results in pred:',pred[:100])

if __name__ == '__main__':

    parser = argparse.ArgumentParser()

    parser.add_argument('--inFile', type=str, help='input file path')
    parser.add_argument('--outFileName', type=str, help="output file's name")
    parser.add_argument('--root', type=str, help="file root")
    parser.add_argument('--train', type=bool, default=True)
    parser.add_argument('--model_name', type=str)
    parser.add_argument('--model_path', type=str)

    parser.add_argument('--n_clusters', type=int, default=0)

    args = parser.parse_args()
    print(args)

    main(args)
