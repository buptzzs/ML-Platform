import argparse
import pandas as pd
import os

support_type = set(['csv', 'txt', 'json'])


def main(args):
    data_dir = os.path.join(args.root, "data")  # get data dir

    file_path = os.path.join(data_dir, args.inFile)

    in_type = file_path.split('.')[-1]
    print(f'read file, type:{in_type}, path:{file_path}')
    assert in_type in support_type
    assert os.path.exists(file_path)

    if in_type == 'json':
        df = pd.read_json(file_path)
    else:
        df = pd.read_csv(file_path, sep=',')

    print('read file done! data head:')
    print(df.head())

if __name__ == '__main__':
    parser = argparse.ArgumentParser()

    parser.add_argument('--inFile', type=str, help='input file path')
    parser.add_argument('--outFileName', type=str, help="output file's name")
    parser.add_argument('--root', type=str, help="file root")

    args = parser.parse_args()
    #print(parser.parse_args())

    main(args)
