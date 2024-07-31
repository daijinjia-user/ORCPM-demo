import pandas as pd
import matplotlib.pyplot as plt
# 读取CSV文件并转换x和y列为浮点数
df = pd.read_csv('E:/program/JS/demo/src/main/resources/static/dataset/dataset.csv', header=1, names=['feature', 'instance', 'x', 'y'])
df['x'] = df['x'].astype(float)
df['y'] = df['y'].astype(float)

# 绘制第一个图：X坐标分布
fig1, ax1 = plt.subplots(figsize=(8, 6))
df.boxplot(column='x', by='feature', ax=ax1, showfliers=False)
ax1.set_title('')  # 删除标题
ax1.set_xlabel('')  # 删除横坐标标题
ax1.set_ylabel('')  # 删除纵坐标标题
plt.suptitle('')  # 删除默认的标题
plt.savefig("E:/program/JS/demo/src/main/resources/static/picture/box_x.png", dpi=600)

# 绘制第二个图：Y坐标分布
fig2, ax2 = plt.subplots(figsize=(8, 6))
df.boxplot(column='y', by='feature', ax=ax2, showfliers=False)
ax2.set_title('')  # 删除标题
ax2.set_xlabel('')  # 删除横坐标标题
ax2.set_ylabel('')  # 删除纵坐标标题
plt.suptitle('')  # 删除默认的标题
plt.savefig("E:/program/JS/demo/src/main/resources/static/picture/box_y.png", dpi=600)

