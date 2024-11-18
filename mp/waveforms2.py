import matplotlib.pyplot as plt
import numpy as np

def differential_manchester(data):
    """
    Generate Polar Differential Manchester encoding.

    Parameters:
        data (list): Binary input sequence (e.g., [0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1]).

    Returns:
        tuple: Time and encoded signal arrays for plotting.
    """
    time = []
    signal = []
    current_level = -1  # Initial signal level (-1 for low)

    for i, bit in enumerate(data):
        t_start = i
        t_mid = i + 0.5
        t_end = i + 1

        time.extend([t_start, t_mid, t_end])  # Add time points for start, mid, and end

        if bit == 1:
            current_level *= -1
            signal.extend([current_level, -current_level, -current_level])
        else:
            signal.extend([current_level, -current_level, -current_level])

    return np.array(time), np.array(signal)

# Input binary sequence
binary_data = [1,0,1,0,1,1,0]

# Generate time and signal arrays
time, signal = differential_manchester(binary_data)

# Plotting the waveform
plt.figure(figsize=(10, 3))
plt.step(time, signal, where='post', color='red', linewidth=2)
plt.xticks(range(len(binary_data) + 1))
plt.yticks([-1, 0, 1], labels=["-1", "0", "1"])
plt.xlabel('Time', fontsize=12, color='black')
plt.ylabel('Amplitude', fontsize=12, color='black')
plt.title('Differential Manchester Encoding', fontsize=14, color='black')
plt.grid(axis='x', linestyle='--', color='black', alpha=0.5)

# Annotate binary values above the waveform
for i, bit in enumerate(binary_data):
    plt.text(i + 0.25, 1.1, str(bit), fontsize=12, color='black', ha='center')

# Styling the plot
plt.axhline(0, color='black', linewidth=0.2)
plt.axvline(0, color='black', linewidth=0.2)
plt.ylim(-1.5, 1.5)  # Set y-axis limits
plt.tight_layout()
plt.show()