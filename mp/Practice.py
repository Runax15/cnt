import matplotlib.pyplot as plt
import numpy as np

def plot_waveforms(encoded_data, title):
    t = np.arange(len(encoded_data))
    plt.step(t, encoded_data, where='post')
    plt.ylim(-2, 2)
    plt.title(title)
    plt.xlabel('Time')
    plt.ylabel('Amplitude')
    plt.grid(True)
    plt.show()

def differential_manchester_encoding(data):
    last_state = 1
    encoded_signal = []
    for bit in data:
        if bit == 0:
            # Transition at the start of the bit period
            encoded_signal.append(-last_state)
            encoded_signal.append(last_state)
        else:
            # No transition at the start of the bit period
            encoded_signal.append(last_state)
            encoded_signal.append(-last_state)
            last_state = -last_state
    return encoded_signal

def main():
    data = [1, 0, 1, 1, 0, 0, 1]

    unipolar_nrz = [1 if bit == 1 else 0 for bit in data]
    polar_nrz = [1 if bit == 1 else -1 for bit in data]
    nrz_inverted = []
    current = 1
    for bit in data:
        if bit == 1:
            current = -current
        nrz_inverted.append(current)
    bipolar_encoding = []
    last_non_zero = 1
    for bit in data:
        if bit == 1:
            last_non_zero = -last_non_zero
            bipolar_encoding.append(last_non_zero)
        else:
            bipolar_encoding.append(0)
    manchester_encoding = []
    for bit in data:
        if bit == 1:
            manchester_encoding.extend([1, -1])
        else:
            manchester_encoding.extend([-1, 1])
    
    # Differential Manchester Encoding
    differential_manchester_encoded = differential_manchester_encoding(data)

    # Plot time domain signals
    plot_waveforms(unipolar_nrz, "Unipolar NRZ")
    plot_waveforms(polar_nrz, "Polar NRZ")
    plot_waveforms(nrz_inverted, "NRZ Inverted")
    plot_waveforms(bipolar_encoding, "Bipolar Encoding")
    plot_waveforms(manchester_encoding, "Manchester Encoding")
    plot_waveforms(differential_manchester_encoded, "Differential Manchester Encoding")

if __name__ == "__main__":
    main()
