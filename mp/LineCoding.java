import java.util.Arrays;

public class LineCoding {

    public static void main(String[] args) {
        int[] data = {1, 0, 1, 1, 0, 0, 1}; // Example data

        System.out.println("Data: " + Arrays.toString(data));
        System.out.println("Unipolar NRZ: " + Arrays.toString(unipolarNRZ(data)));
        System.out.println("Polar NRZ: " + Arrays.toString(polarNRZ(data)));
        System.out.println("NRZ Inverted: " + Arrays.toString(nrzInverted(data)));
        System.out.println("Bipolar Encoding: " + Arrays.toString(bipolarEncoding(data)));
        System.out.println("Manchester Encoding: " + Arrays.toString(manchesterEncoding(data)));
        System.out.println("Differential Manchester Encoding: " + Arrays.toString(differentialManchesterEncoding(data)));
    }

    public static int[] unipolarNRZ(int[] data) {
        int[] encoded = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            encoded[i] = data[i];
        }
        return encoded;
    }

    public static int[] polarNRZ(int[] data) {
        int[] encoded = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            encoded[i] = data[i] == 1 ? 1 : -1;
        }
        return encoded;
    }

    public static int[] nrzInverted(int[] data) {
        int[] encoded = new int[data.length];
        int current = 1;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == 1) {
                current = -current;
            }
            encoded[i] = current;
        }
        return encoded;
    }

    public static int[] bipolarEncoding(int[] data) {
        int[] encoded = new int[data.length];
        int lastNonZero = 1;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == 1) {
                lastNonZero = -lastNonZero;
                encoded[i] = lastNonZero;
            } else {
                encoded[i] = 0;
            }
        }
        return encoded;
    }

    public static int[] manchesterEncoding(int[] data) {
        int[] encoded = new int[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            encoded[2 * i] = data[i] == 1 ? 1 : -1;
            encoded[2 * i + 1] = data[i] == 1 ? -1 : 1;
        }
        return encoded;
    }

    public static int[] differentialManchesterEncoding(int[] data) {
        int[] encoded = new int[data.length * 2];
        int lastState = 1;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == 1) {
                encoded[2 * i] = lastState;
                lastState = -lastState;
            } else {
                lastState = -lastState;
                encoded[2 * i] = lastState;
            }
            encoded[2 * i + 1] = lastState;
        }
        return encoded;
    }
}
