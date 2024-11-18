import matplotlib.pyplot as plt
import numpy as np


# Define the normalized frequency range from 0 to 2 (with 500 points)
f_R = np.linspace(0, 2, 500)


# Define the spectral density function for NRZ-L encoding
def spectral_nrz_l(f_R):
   # The power spectral density of NRZ-L follows the sinc squared function
   return np.sinc(f_R)**2


# Define the spectral density function for NRZ-I encoding
def spectral_nrz_i(f_R):
   # NRZ-I spectral density is derived from NRZ-L with an additional cosine factor
   return np.sinc(f_R)**2 * (1 + np.cos(np.pi * f_R)) / 2


# Define the spectral density function for Bipolar-AMI encoding
def spectral_bipolar_ami(f_R):
   # Bipolar-AMI spectral density is a modified version of the sinc function
   return (np.sinc(f_R) * np.sin(np.pi * f_R / 2))**1.4


# Define the spectral density function for Pseudoternary encoding
def spectral_pseudoternary(f_R):
   # Pseudoternary is similar to AMI but with a slight difference in the exponent
   return (np.sinc(f_R) * np.sin(np.pi * f_R / 2))**1.424


# Define the spectral density function for Manchester encoding
def spectral_manchester(f_R):
   # Manchester encoding has a squared sinc function with a sin term
   return (np.sinc(f_R) * np.sin(np.pi * f_R / 2))**2


# Define the spectral density function for Differential Manchester encoding
def spectral_differential_manchester(f_R):
   # Differential Manchester uses a sinc function applied to half the frequency
   return np.sinc(f_R / 2)**2 * np.sinc(f_R / 2)**2


# Plot the spectral densities for all encoding schemes
plt.figure(figsize=(10, 6))  # Create a figure with a specific size


# Plot each spectral density with its respective label and linestyle
plt.plot(f_R, spectral_nrz_l(f_R), label='NRZ-L', linestyle='--')
plt.plot(f_R, spectral_nrz_i(f_R), label='NRZI', linestyle='--')
plt.plot(f_R, spectral_bipolar_ami(f_R), label='AMI', linestyle='-.')
plt.plot(f_R, spectral_pseudoternary(f_R), label='Pseudoternary', linestyle='-.')
plt.plot(f_R, spectral_manchester(f_R), label='Manchester')
# Optionally plot Differential Manchester (commented out)
# plt.plot(f_R, spectral_differential_manchester(f_R), label='Differential Manchester')


# Add title and labels to the plot
plt.title('Spectral Density of Various Signal Encoding Schemes')
plt.xlabel('Normalized Frequency (f/R)')
plt.ylabel('Mean Square Voltage per Unit Bandwidth')


# Add a legend, grid, and set y-axis limits for better visualization
plt.legend()
plt.grid(True)
plt.ylim(0, 1.4)


# Display the plot
plt.show()
