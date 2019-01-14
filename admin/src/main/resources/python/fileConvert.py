import argparse
import pandas as pd
import os

support_type = set(['csv', 'txt', 'json'])

def main(args):
    data_dir = os.path.join(args.root, "data") # get data dir

    file_path = os.path.join(data_dir, args.inFile)
    in_type = args.inType
    out_type = args.outType
    out_filename = args.outFileName

    print(f'read file, type:{in_type}, path:{file_path}')
    assert in_type in support_type
    assert out_type in support_type
    assert os.path.exists(file_path)

    if in_type == 'json':
        df = pd.read_json(file_path)
    else:
        df = pd.read_csv(file_path, sep=',')

    print('read file done! data head:')
    print(df.head())

    if out_type == 'json':
        out_file_path = out_filename + '.json'
        df.to_json(os.path.join(data_dir, out_file_path), orient="records")
    else:
        out_file_path = out_filename + '.csv'
        df.to_csv(os.path.join(data_dir, out_file_path), index=False)

    print(f'convert to {out_file_path} done!')



if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    
    parser.add_argument('--inType', type=str, 
                        help='information', default="csv")
    parser.add_argument('--outType', type=str, 
                        help='information', default="csv")
    parser.add_argument('--inFile', type=str, help='input file path')
    parser.add_argument('--outFileName', type=str, help="output file's name")
    parser.add_argument('--root', type=str, help="file root")


    args = parser.parse_args()
    print(parser.parse_args())
    '''
    args.inFile = 'demoConvert.json'
    args.root = 'files/admin/data'
    args.outFileName = 'demoConvert2'
    args.inType = 'json'
    args.outType = 'csv'
    '''
    main(args)

    

    


    
