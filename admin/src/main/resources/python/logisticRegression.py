import pickle
from sklearn import linear_model
from sklearn.metrics import mean_squared_error, r2_score
from sklearn.externals.joblib import dump, load
import argparse
import os
import pandas as pd
model = 'linear_model'


def main(args):
    model_name = args.model_name
    model_dir = os.path.join(args.root, "model")  # get model dir
    data_dir = os.path.join(args.root, "data")  # get data dir

    data_path = os.path.join(data_dir, args.inFile)
    print('load data from'+data_path)
    
    data = pickle.load(open(data_path, 'rb'))
    assert 'data' in data
    if args.train:
        ratio = args.ratio
        clf = linear_model.LogisticRegression(dual=args.dual,C=args.C,fit_intercept=args.fit_intercept,intercept_scaling=args.intercept_scaling)

        assert 'target' in data

        features = data['data']
        labels = data['target']

        ratio_num = int(features.shape[0] * ratio)
        x_train = features[ratio_num:]
        x_test = features[:ratio_num]

        y_train = labels[ratio_num:]
        y_test = labels[:ratio_num]

        clf.fit(x_train, y_train.astype(int))
        y_pred = clf.predict(x_test)

        # The coefficients
        print('Coefficients: \n', clf.coef_)
        # The mean squared error
        print("Mean squared error: %.2f"
            % mean_squared_error(y_test, y_pred))

        model_path = os.path.join(model_dir,f'{model_name}_{model}.model')
        dump(clf, model_path)
    else:
         # TODO: How to Save the prediction?
        model_path = os.path.join(model_dir,args.model)
        clf = load(model_path)
        x = data['data']
        pred = clf.predict(x)
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
    parser.add_argument('--ratio', type=float, default=0.2)
    parser.add_argument('--model_name', type=str)
    parser.add_argument('--model_path', type=str)

    parser.add_argument('--dual', type=bool, default=False)
    parser.add_argument('--C', type=float, default=1.0)
    parser.add_argument('--fit_intercept', type=bool, default=True)
    parser.add_argument('--intercept_scaling', type=float, default=1.0)
      
    args = parser.parse_args()
    
    print(args)

    main(args)