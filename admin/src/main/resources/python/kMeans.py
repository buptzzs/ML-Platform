import pickle
from sklearn import cluster
from sklearn.metrics import adjusted_rand_score
from sklearn.externals.joblib import dump, load
import argparse
import os
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
        regr = cluster.KMeans(n_clusters=k, random_state=random_state)

        assert 'target' in data

        features = data['data']
        labels = data['target']

        pred = regr.fit_predict(features)

        # The Adjusted rand index
         print('Adjusted rand index: \n',adjusted_rand_score(labels, pred))

        model_path = os.path.join(model_dir,f'{model_name}_{model}.model')
        dump(regr, model_path)
    else:
        # TODO: How to Save the prediction?
        model_path = os.path.join(model_dir,args.model_path)
        regr = load(args.model)
        x = data['data']
        pred = regr.fit_predict(x)
        out_path = os.path.join(data_dir, args.outFileName)
        print(pred)

if __name__ == '__main__':

    parser = argparse.ArgumentParser()

    parser.add_argument('--inFile', type=str, help='input file path')
    parser.add_argument('--outFileName', type=str, help="output file's name")
    parser.add_argument('--root', type=str, help="file root")

    parser.add_argument('--train', type=bool, default=True)
    parser.add_argument('--n_clusters', type=int, default=0)
    parser.add_argument('--model_name', type=str)
    parser.add_argument('--model_path', type=str)


    args = parser.parse_args()
    print(args)

    main(args)
