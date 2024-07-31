import csv
import matplotlib.pyplot as plt
coreFeature = ""
def plot_data(filename):
    with open(filename, 'r') as f:
        reader = csv.reader(f)
        next(reader)  # skip header row
        data = [(row[0], int(row[1]), float(row[2]), float(row[3])) for row in reader]

    with open('E:/program/JS/demo/src/main/resources/coreFeature.csv', 'r') as f:
            reader = csv.reader(f)
            data1 = [(row[0]) for row in reader]
    coreFeature = data1[0]
    grouped_data = {}
    grouped_data['Other'] = {'x': [], 'y': []}
    for d in data:
            # Check if first field is 'A', otherwise exit
        if d[0] == coreFeature:
            if d[0] not in grouped_data:
                grouped_data[d[0]] = {'x': [], 'y': []}
            grouped_data[d[0]]['x'].append(d[2])
            grouped_data[d[0]]['y'].append(d[3])
            continue
        else:
            grouped_data['Other']['x'].append(d[2])
            grouped_data['Other']['y'].append(d[3])

    fig, ax = plt.subplots(figsize=(12, 10),dpi=300)
    plt.rcParams['legend.fontsize'] = 16
    for group, values in grouped_data.items():
        if group == 'Other':
#             continue
            ax.scatter(values['x'], values['y'], color = 'lightgray', label=group, s=2)
        elif group == coreFeature:
            ax.scatter(values['x'], values['y'], color = 'orange',label=group, s=2)
        else:
            ax.scatter(values['x'], values['y'], color = 'green',label=group, s=2)
    ax.legend(loc='upper right')
    plt.savefig("E:/program/JS/demo/src/main/resources/static/picture/coreInstances.png", dpi=600)

plot_data('E:/program/JS/demo/src/main/resources/static/dataset/dataset.csv')

#划区域
with open('E:/program/JS/demo/src/main/resources/static/dataset/dataset.csv', 'r') as f:
        reader = csv.reader(f)
        next(reader)  # skip header row
        data = [(float(row[2]), float(row[3])) for row in reader]

with open('E:/program/JS/demo/src/main/resources/regions.csv', 'r') as f:
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

fig, ax = plt.subplots(figsize=(12, 10),dpi=300)
plt.rcParams['legend.fontsize'] = 16
for group, values in grouped_data.items():
    if group == other:
        ax.scatter(values['x'], values['y'], color='lightgray', label='Regions Of '+ str(coreFeature), s=2)
    else:
        ax.scatter(values['x'], values['y'],  s=2)

ax.legend(loc='upper left')

plt.savefig("E:/program/JS/demo/src/main/resources/static/picture/coreRegion.png", dpi=600)