import pickle
from sklearn import ensemble
from sklearn.metrics import accuracy_score
from sklearn.externals.joblib import dump, load
import argparse
import os
import numpy as np
import pandas as pd
from sklearn.model_selection import ShuffleSplit

model = 'ensemble'


def main(args):
    model_name = args.model_name
    model_dir = os.path.join(args.root, "model")  # get model dir
    data_dir = os.path.join(args.root, "data")  # get data dir

    data_path = os.path.join(data_dir, args.inFile)
    print('load data from'+data_path)
    
    data = pickle.load(open(data_path, 'rb'))
<<<<<<< HEAD
    out_path = os.path.join(data_dir, args.outFileName+'.csv')
=======
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
    assert 'data' in data
    if args.train:
        ratio = args.ratio
        regr = ensemble.GradientBoostingRegressor(n_estimators=args.n_estimators,learning_rate=args.learning_rate)

        assert 'target' in data

        features = data['data']
        labels = data['target']

        rs = ShuffleSplit(n_splits=1, test_size=ratio)
        train_index, val_index = next(rs.split(features, labels))

        x_train = features[train_index]
        x_test = features[val_index]

        y_train = labels[train_index]
        y_test = labels[val_index]

        regr.fit(x_train, y_train)
        y_pred = regr.predict(x_test)

        # The accuracy
        print('Accuracy: \n',accuracy_score(y_test, y_pred))
        df = pd.DataFrame({
            'pred': y_pred,
            'target': y_test,
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
    parser.add_argument('--ratio', type=float, default=0.2)
    parser.add_argument('--model_name', type=str)
    parser.add_argument('--model_path', type=str)

    parser.add_argument('--n_estimators', type=int, default=100)
    parser.add_argument('--learning_rate', type=float, default=0.1)


    args = parser.parse_args()
    print(args)

    main(args)