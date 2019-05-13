import numpy as np
import os
import time
import argparse

def main(args):
    print('开始数数游戏')
    print('首先创建一个大小为1000000的数组，然后不断打印数组数字')
    print('过程持续3分钟')
    pid =os.getpid()
    print('pid:',pid)
    begin_time = time.time()
    arr = np.random.randn(1000000)
    i = 0
    while True:
        cur_time = time.time()
        time_cost = cur_time - begin_time
        if time_cost>i and time_cost<i+1:
            print(np.random.choice(arr, 1))
            i+=1
        if time_cost > args.time:
            print(time_cost)
            break
if __name__ == '__main__':

    parser = argparse.ArgumentParser()

    parser.add_argument('--inFile', type=str, help='input file path')
    parser.add_argument('--outFileName', type=str, help="output file's name")
    parser.add_argument('--root', type=str, help="file root")
    parser.add_argument('--time', type=int, default=60)

    args = parser.parse_args()
    print(args)

    main(args)
