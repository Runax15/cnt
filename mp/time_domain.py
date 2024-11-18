import matplotlib.pyplot as plt
from scipy.signal import welch

# Function to print the encoded signal for visualization and debugging purposes
def print_enc(name, encoding):
   print(f"{name}: {' '.join(map(str, encoding))}")

# NRZ-L (Non-Return-to-Zero Level) encoding: 0 is mapped to 1, 1 is mapped to 0
def nrzL(data):
   return [1 if bit == 0 else 0 for bit in data]

# NRZ-I (Non-Return-to-Zero Inverted) encoding: transitions occur on 1, no change on 0
def nrzI(data):
   encoded = []
   last_level = 1  # Start at level 1 (convention for NRZ-I)
   for bit in data:
       if bit == 1:
           last_level = 1 - last_level  # Invert on 1
       encoded.append(last_level)
   return encoded

# Manchester encoding: 1 is represented by [0,1], 0 is represented by [1,0]
def manchester(data):
   encoded = []
   for bit in data:
       if bit == 1:
           encoded.extend([0, 1])
       else:
           encoded.extend([1, 0])
   return encoded

# Bipolar-AMI (Alternate Mark Inversion) encoding: 1s alternate between +1 and -1, 0 is represented by 0
def bipolar_ami(data):
   encoded = []
   last_pulse = -1  # Keeps track of the last pulse direction
   for bit in data:
       if bit == 1:
           last_pulse = -last_pulse  # Alternate between +1 and -1
           encoded.append(last_pulse)
       else:
           encoded.append(0)  # 0 is neutral (0 level)
   return encoded

# Differential Manchester encoding: 0 causes no transition at the start of the bit, 1 causes a transition
def differential_manchester(data):
   encoded = []
   last_level = 0  # Initial level is 0
   for bit in data:
       if bit == 0:
           encoded.extend([last_level, 1 - last_level])  # No transition on 0
       else:
           last_level = 1 - last_level  # Transition on 1
           encoded.extend([last_level, 1 - last_level])
   return encoded

# Unipolar encoding: straightforward 1 -> 1, 0 -> 0
def unipolar(data):
   return [1 if bit == 1 else 0 for bit in data]

# Polar encoding: 1 -> +1, 0 -> -1
def polar(data):
   return [1 if bit == 1 else -1 for bit in data]

# Function to visualize the signal encoding with graphs
def visualize(name, data, encoding, is_manchester=False):
   plt.figure(figsize=(12, 1))
   plt.title(name)  # Plot title for the encoding scheme

   # Manchester encoding requires double the x-values since each bit is split into two
   if is_manchester:
       x_values = [0.5 * i for i in range(len(encoding) + 1)]
   else:
       x_values = range(len(encoding) + 1)

   # Plotting the encoding as a step function
   plt.step(x_values, encoding + [encoding[-1]], where='post', linestyle='-', color='purple', linewidth=1.5)

   # Adjust y-axis for encodings like Bipolar and Polar which use negative values
   if "Bipolar" in name or "Polar" in name:
       plt.ylim(-1.5, 1.5)  # Y-axis range for bipolar and polar encodings
       plt.yticks([-1, 0, 1])
   else:
       plt.ylim(-0.5, 1.5)  # Y-axis for unipolar encodings
       plt.yticks([0, 1])

   # Adjust x-axis ticks for Manchester encoding (since each bit is split into two)
   if is_manchester:
       plt.xticks([0.5 * i for i in range(len(data) * 2 + 1)])
   else:
       plt.xticks(range(len(data) + 1))

   plt.grid(True)  # Add grid for better readability
   plt.show()  # Display the plot

# Main function to execute encoding and visualization
def main():
   # Get custom binary input from the user
   data = [int(x) for x in input(f"Enter the binary numbers: ").strip()]
   #print(f"Data entered: {data}")
   #data = [int(x) for x in data]

   # Generate the encoded signals for different encoding schemes
   nrzl_enc = nrzL(data)
   nrzi_enc = nrzI(data)
   man_enc = manchester(data)
   bi_enc = bipolar_ami(data)
   diff_enc = differential_manchester(data)
   uni_enc = unipolar(data)
   polar_enc = polar(data)

   # Print the encoded signals
   print_enc("NRZ-L", nrzl_enc)
   print_enc("NRZ-I", nrzi_enc)
   print_enc("Manchester", man_enc)
   print_enc("Bipolar-AMI", bi_enc)
   print_enc("Differential Manchester", diff_enc)
   print_enc("Unipolar", uni_enc)
   print_enc("Polar", polar_enc)

   # Visualize the input data first (binary form)
   visualize("Input Data", data, data)

   # Visualize each encoding scheme
   visualize("NRZ-L", data, nrzl_enc)
   visualize("NRZ-I", data, nrzi_enc)
   visualize("Manchester", data, man_enc, is_manchester=True)
   visualize("Bipolar-AMI", data, bi_enc)
   visualize("Differential Manchester", data, diff_enc, is_manchester=True)

   # Visualize additional Unipolar and Polar encodings
   visualize("Unipolar", data, uni_enc)
   visualize("Polar", data, polar_enc)

# Ensure the script runs only when executed directly
if __name__ == "__main__":
   main()
