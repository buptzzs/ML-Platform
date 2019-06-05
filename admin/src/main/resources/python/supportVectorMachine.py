import pickle
from sklearn import svm
from sklearn.metrics import accuracy_score
from sklearn.externals.joblib import dump, load
import argparse
import os
import numpy as np
import pandas as pd
from sklearn.model_selection import ShuffleSplit

model = 'svm'


def main(args):
    model_name = args.model_name
    model_dir = os.path.join(args.root, "model")  # get model dir
    data_dir = os.path.join(args.root, "data")  # get data dir

    data_path = os.path.join(data_dir, args.inFile)
    print('load data from'+data_path)
    
    data = pickle.load(open(data_path, 'rb'))
    out_path = os.path.join(data_dir, args.outFileName+'.csv')
    assert 'data' in data
    if args.train:
        ratio = args.ratio
        clf = svm.SVC(kernel=args.kernel,C=args.C,coef0=args.coef0)

        assert 'target' in data

        features = data['data']
        labels = data['target']

        rs = ShuffleSplit(n_splits=1, test_size=ratio)
        train_index, val_index = next(rs.split(features, labels))

        x_train = features[train_index]
        x_test = features[val_index]

        y_train = labels[train_index]
        y_test = labels[val_index]

        clf.fit(x_train, y_train)
        y_pred = clf.predict(x_test)


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
        dump(clf, model_path)
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

    parser.add_argument('--kernel', type=str, default='rbf')
    parser.add_argument('--C', type=float, default=1.0)
    parser.add_argument('--coef0', type=float, default=0.0)


    args = parser.parse_args()
    print(args)

    main(args)
