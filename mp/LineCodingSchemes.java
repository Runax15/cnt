/*
1) Write a program in C++/JAVA to implement - 
Unipolar NRZ, Polar NRZ, NRZ Inverted, Bipolar Encoding, Manchester Encoding and Differential Manchester Encoding.
 */

import java.util.Arrays;
import java.util.Scanner;

public class LineCodingSchemes {

    public static int[] unipolarNRZ(String data){
        int[] encoded = new int[data.length()];
        for(int i=0;i<data.length();i++){
            encoded[i]= data.charAt(i)== '1' ? 1: 0;
        }
    return encoded;
    }

    public static int[] polarNRZ(String data){
        int[] encoded = new int[data.length()];
        for(int i=0;i<data.length();i++){
            encoded[i]= data.charAt(i)== '1' ? 1: -1;
        }
    return encoded;
    }

    public static int[] NRZInverted(String data){
        int[] encoded = new int[data.length()];
        int currentSignal = 1;
        for(int i=0;i<data.length();i++){
            if(data.charAt(i) == '1'){
                currentSignal = -currentSignal;
            }
            encoded[i] = currentSignal;
        }
    return encoded;
    }

    public static int[] BipolarNRZ(String data){
        int[] encoded = new int[data.length()];
        int lastPositive = -1;
        for(int i=0;i<data.length();i++){
            if(data.charAt(i) == '1'){
                lastPositive = -lastPositive;
                encoded[i]=lastPositive;
            }
            else{
                encoded[i] = 0;
            }
           
        }
    return encoded;
    }

    

    public static int[] Manchester(String data){
        int[] encoded = new int[data.length() * 2];
        for(int i=0;i<data.length();i++){
            if(data.charAt(i) == '1'){
                encoded[2*i]=1;
                encoded[2*i+1]=-1;
            }
            else{
                encoded[2*i]=-1;
                encoded[2*i+1]=1;
            }
           
        }
    return encoded;
    }

    public static int[] differentialManchester(String data) {
        int[] encoded = new int[data.length() * 2];
        int currentSignal = 1;
        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == '0') {
                encoded[2 * i] = -currentSignal;
                encoded[2 * i + 1] = currentSignal;
            } else {
                encoded[2 * i] = currentSignal;
                currentSignal = -currentSignal;
                encoded[2 * i + 1] = currentSignal;
            }
        }
        return encoded;
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the input String");
        String data = sc.nextLine();
        
        System.out.println("Input value: "+ data);
        System.out.println("Unipolar NRZ: "+ Arrays.toString(unipolarNRZ(data)));
        System.out.println("Polar NRZ: "+ Arrays.toString(polarNRZ(data)));
        System.out.println("NRZ Inverted: "+ Arrays.toString(NRZInverted(data)));
        System.out.println("Bipolar NRZ: "+ Arrays.toString(BipolarNRZ(data)));
        System.out.println("Manchester: "+ Arrays.toString(Manchester(data)));
        System.out.println("Differential Manchester: "+ Arrays.toString(differentialManchester(data)));

        sc.close();
    }
}
