import argparse



if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--info', type=str, help='information', default="default")
    parser.parse_args()
    print(parser.parse_args())
    print('test')

