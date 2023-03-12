import numpy as np
import matplotlib.pyplot as plt
import tomllib

with open("config.toml", "rb") as f:
  config = tomllib.load(f)
  STATS_FILE = config["statistics"]["times"]

# Read execution times from file
measurements: list[tuple[float, float]] = []

with open(STATS_FILE, "r") as f:
  for line in f:
    measurements.append([float(x) for x in line.rstrip("\n").split(" ")])


# CIM vs BRUTE FORCE EXECUTION TIMES

method_measurements = measurements[:10]

# Execution times for each method
cim_method = [x[0] for x in method_measurements]
brute_force = [x[1] for x in method_measurements]

# Calculate the mean and standard deviation for each method
mean_cim = np.mean(cim_method)
std_cim = np.std(cim_method)
mean_bf = np.mean(brute_force)
std_bf = np.std(brute_force)

# _, ax = plt.subplots() # create a single axis
_, (ax1, ax2) = plt.subplots(1, 2) # create two separate figures

# The `bar` function creates a bar plot with error bars showing the mean and standard deviation for each method.
# The `capsize` parameter sets the length of the error bar caps.
ax1.bar(['CIM', 'Brute Force'], [mean_cim, mean_bf], yerr=[std_cim, std_bf], capsize=5)
ax1.set_ylabel('Execution Time (ms)')
ax1.grid()


# M vs EXECUTION TIMES

# Ideal M lookup
M_measurements = [x[0] for x in measurements[10:]] # get just the CIM method measurements
M = np.linspace(2, len(M_measurements) + 1, len(M_measurements))

# Create a scatter plot of the samples of F(M) against the values of M
ax2.scatter(M, M_measurements, color='blue', label='Execution Time')

# Estimate the errors associated with the samples using standard deviation
error = np.std(M_measurements)

# Add error bars to the plot based on the estimated errors
plt.errorbar(M, M_measurements, yerr=error, fmt='none', ecolor='blue', capsize=3, label='Errors')

ax2.set_ylabel('Execution Time (ms)')
ax2.set_xlabel('M parameter value')
ax2.grid()

# Show the plots
plt.show()
