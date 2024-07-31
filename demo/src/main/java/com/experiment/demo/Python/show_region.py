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
folder_path = 'E:/program/JS/demo/src/main/resources/static/picture/patternRegion'
clear_folder(folder_path)

#划区域
localPattern = ""
with open('E:/program/JS/demo/src/main/resources/static/dataset/dataset.csv', 'r') as f:
        reader = csv.reader(f)
        next(reader)  # skip header row
        data = [(float(row[2]), float(row[3])) for row in reader]
with open('E:/program/JS/demo/src/main/resources/localPattern.csv', 'r') as f:
        reader = csv.reader(f)
        data1 = [(row[0]) for row in reader]
        localPattern = data1[0]
with open('E:/program/JS/demo/src/main/resources/eachregions.csv', 'r') as f:
    reader = csv.reader(f)
    data2 = [(row[0], float(row[1]), float(row[2])) for row in reader]
other = 'Other'
grouped_data = {}
grouped_data[other] = {'x': [], 'y': []}
for d in data:
    grouped_data[other]['x'].append(d[0])
    grouped_data[other]['y'].append(d[1])
for d in data2:
    if d[0] not in grouped_data:
        grouped_data[d[0]] = {'x': [], 'y': []}
    grouped_data[d[0]]['x'].append(d[1])
    grouped_data[d[0]]['y'].append(d[2])
# 初始化 x_min, x_max, y_min, y_max
x_min, x_max = float('inf'), float('-inf')
y_min, y_max = float('inf'), float('-inf')
fig, ax = plt.subplots(figsize=(12, 10),dpi=300)
plt.rcParams['legend.fontsize'] = 16
for group, values in grouped_data.items():
    if group == other:
        ax.scatter(values['x'], values['y'], color='lightgray', label='Regions Of '+ localPattern, s=30)
    else:
        x_min, x_max = min(min(values['x']), x_min), max(max(values['x']), x_max)
        y_min, y_max = min(min(values['y']), y_min), max(max(values['y']), y_max)
        ax.scatter(values['x'], values['y'],  s=30)


buffer = 10000  # 添加一个缓冲区，使点不紧贴边界
ax.set_xlim(x_min - buffer, x_max + buffer)
ax.set_ylim(y_min - buffer, y_max + buffer)
ax.legend(loc='upper left')
plt.savefig("E:/program/JS/demo/src/main/resources/static/picture/patternRegion/localRegions"+localPattern+".png", dpi=600)