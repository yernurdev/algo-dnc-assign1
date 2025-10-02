import pandas as pd
import matplotlib.pyplot as plt

cols = ['algo', 'n', 'time', 'comparisons', 'allocations', 'depth']
df = pd.read_csv("metrics.csv", names=cols)

for col in ['n','time','comparisons','allocations','depth']:
    df[col] = pd.to_numeric(df[col])

metrics_to_plot = ['time', 'depth', 'comparisons', 'allocations']

for metric in metrics_to_plot:
    plt.figure(figsize=(8,5))
    for algo in df['algo'].unique():
        sub = df[df['algo']==algo]
        plt.plot(sub['n'], sub[metric], marker='o', label=algo)
    plt.xlabel("Array size n")
    plt.ylabel(metric)
    plt.title(f"{metric} vs n")
    plt.legend()
    plt.grid(True)
    plt.tight_layout()
    plt.savefig(f"plots/{metric}.png")  # сохраняем в папку plots
    plt.close()
