import pickle
from sklearn import linear_model
from sklearn.metrics import mean_squared_error, r2_score
from sklearn.externals.joblib import dump, load
import argparse
import os
import numpy as np
import pandas as pd
from sklearn.model_selection import ShuffleSplit
from sklearn.ensemble import RandomForestClassifier


def main(args):
    model_name = args.model_name
    model_dir = os.path.join(args.root, "model")  # get model dir
    data_dir = os.path.join(args.root, "data")  # get data dir

    data_path = os.path.join(data_dir, args.inFile)
    print('load data from'+data_path)
    
    data = pickle.load(open(data_path, 'rb'))
    out_path = os.path.join(data_dir, args.outFileName+'.csv')
    assert 'data' in data
    print(args.train)
    if args.train == 'true':
        ratio = args.ratio
        clf = RandomForestClassifier(n_estimators=args.n_estimators, max_depth=args.max_depth,
                                    random_state=args.random_state, bootstrap=args.bootstrap, warm_start=args.warm_start,
                                    min_samples_split=args.min_samples_split, min_samples_leaf=args.min_samples_leaf,
                                    n_jobs=args.n_jobs,min_weight_fraction_leaf=args.min_weight_fraction_leaf
                                    )

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

        train_pred = clf.predict(x_train)
        acc = np.sum(y_train == train_pred) / y_train.shape[0]
        print(f"Train Acc:{acc}")

        y_pred = clf.predict(x_test)
        # The coefficients
        print('Feature Importances: \n', clf.feature_importances_)

        # The mean squared error
        y_prob = clf.predict_proba(x_test)

        df = pd.DataFrame({
            'pred': y_pred,
            'target': y_test,
            'prob': y_prob[:,1] # 这里有待商榷
        })
        print(f'validation results save to:{args.outFileName}.csv')
        df.to_csv(out_path)
        print("Some results of validation:")
        print(df.head())

        model_path = os.path.join(model_dir,f'{model_name}_rf.model')
        dump(clf, model_path)
    else:
        # TODO: How to Save the prediction?
        model_path = os.path.join(model_dir,args.model_path)
        clf = load(model_path)
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

    parser.add_argument('--train', type=str, default="train")
    parser.add_argument('--ratio', type=float, default=0.2)

    parser.add_argument('--max_depth', type=int, default=2)
    parser.add_argument('--bootstrap', type=bool, default=True)
    parser.add_argument('--n_estimators', type=int, default=10)
    parser.add_argument('--warm_start', type=bool, default=False)
    parser.add_argument('--min_samples_split', type=int, default=2)
    parser.add_argument('--min_samples_leaf', type=int, default=1)
    parser.add_argument('--min_weight_fraction_leaf', type=float, default=0.0)
    parser.add_argument('--n_jobs', type=int, default=-1)
    parser.add_argument('--random_state', type=int, default=0)



    parser.add_argument('--model_name', type=str)
    parser.add_argument('--model_path', type=str)


    args = parser.parse_args()
    print(args)

    main(args)
