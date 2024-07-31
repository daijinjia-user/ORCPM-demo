import csv
import numpy as np
import matplotlib.pyplot as plt

def load_data(file_path):
    data = csv.reader(open(file_path, 'r'))
    fx = {}
    fy = {}
    for index, line in enumerate(data):
        if index == 0:
            continue
        if str(line[0]) not in fx.keys() and len(fx.keys()) > 20:
            break
        if str(line[0]) not in fx.keys():
            fx[str(line[0])] = [float(line[2])]
            fy[str(line[0])] = [float(line[3])]
        else:
            fx[str(line[0])].append(float(line[2]))
            fy[str(line[0])].append(float(line[3]))
    return fx, fy

if __name__ == '__main__':
    fx, fy = load_data('E:/program/JS/demo/src/main/resources/static/dataset/dataset.csv')
    # 定义颜色变量
    colors = ['black', 'gray', 'lightcoral', 'red', 'chocolate', 'orange', 'gold', 'yellow', 'palegreen','green', 'lime', 'aquamarine','cyan','deepskyblue','blue','pink','fuchsia']
    plt.figure(figsize=(10, 8), dpi=100, facecolor='w')
    for i, feature in enumerate(fx.keys()):
        x1 = fx.get(feature)
        y1 = fy.get(feature)
        # 画图
        plt.scatter(x1, y1, c=colors[i % len(colors)], s=6, alpha=0.4)
    plt.savefig("E:/program/JS/demo/src/main/resources/static/picture/data.png", dpi=100)
    print("saving picture successful")
