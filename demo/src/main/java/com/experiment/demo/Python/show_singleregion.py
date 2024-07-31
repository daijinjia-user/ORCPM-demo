import csv
import matplotlib.pyplot as plt
import os
import shutil
def clear_folder(folder_path):
    for filename in os.listdir(folder_path):
        file_path = os.path.join(folder_path, filename)
        try:
            if os.path.isfile(file_path) or os.path.islink(file_path):
                os.unlink(file_path)  # 删除文件或符号链接
            elif os.path.isdir(file_path):
                shutil.rmtree(file_path)  # 删除目录及其内容
        except Exception as e:
            print(f'Failed to delete {file_path}. Reason: {e}')

# 示例用法
folder_path = 'E:/program/JS/demo/src/main/resources/static/picture/region'
clear_folder(folder_path)
colors = ['red', 'chocolate', 'orange', 'gold', 'yellow', 'palegreen','green', 'lime', 'aquamarine','cyan','deepskyblue','blue','pink','fuchsia','black', 'gray', 'lightcoral']
#划区域
featureList = []
with open('E:/program/JS/demo/src/main/resources/static/dataset/dataset.csv', 'r') as f:
        reader = csv.reader(f)
        next(reader)  # skip header row
        data = [(float(row[2]), float(row[3])) for row in reader]
with open('E:/program/JS/demo/src/main/resources/localPattern.csv', 'r') as f:
        reader = csv.reader(f)
        data1 = [(row[0]) for row in reader]
        featureList = list(data1[0])

with open('E:/program/JS/demo/src/main/resources/eachregions.csv', 'r') as f:
    reader = csv.reader(f)
    data2 = [(row[0], float(row[1]), float(row[2]), row[3]) for row in reader]
other = 'Other'
grouped_data = {}
grouped_data[other] = {'x': [], 'y': []}
for d in data:
    grouped_data[other]['x'].append(d[0])
    grouped_data[other]['y'].append(d[1])
for d in data2:
    if d[0] not in grouped_data:
        grouped_data[d[0]] = {d[3]:{'x': [], 'y': []}}
    if d[3] not in grouped_data[d[0]]:
        grouped_data[d[0]][d[3]] = {'x': [], 'y': []}
    grouped_data[d[0]][d[3]]['x'].append(d[1])
    grouped_data[d[0]][d[3]]['y'].append(d[2])
for group, values in grouped_data.items():
    if group == other:
        continue
    x_min, x_max = float('inf'), float('-inf')
    y_min, y_max = float('inf'), float('-inf')
    fig, ax = plt.subplots(figsize=(12, 10), dpi=300)
    plt.rcParams['legend.fontsize'] = 16
    ax.scatter(grouped_data['Other']['x'], grouped_data['Other']['y'], color='lightgray', label='Other', s=30)
    i = 0
    for feature, potion in values.items():
        if feature not in featureList:
            ax.scatter(potion['x'], potion['y'], color='lightgray', s=30)
        else:
            ax.scatter(potion['x'], potion['y'],  color=colors[i], label=feature, s=30)
            # 设置坐标轴范围以限制在当前区域的数据点上
            x_min, x_max = min(min(potion['x']), x_min), max(max(potion['x']), x_max)
            y_min, y_max = min(min(potion['y']), y_min), max(max(potion['y']), y_max)
            i+=1

    buffer = 4000  # 添加一个缓冲区，使点不紧贴边界
    ax.set_xlim(x_min - buffer, x_max + buffer)
    ax.set_ylim(y_min - buffer, y_max + buffer)
    ax.legend(loc='upper right')
    plt.savefig("E:/program/JS/demo/src/main/resources/static/picture/region/"+group+".png", dpi=600)