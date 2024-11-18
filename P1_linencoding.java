// 1) Write a program in C++/JAVA to implement - Unipolar NRZ, Polar NRZ, NRZ Inverted, Bipolar Encoding, Manchester Encoding and 
// Differential Manchester Encoding.
// bit = 01001100011
// dman 1,  -1,1,  1,-1,  1,-1,  1,-1,  -1,1,  1,-1,  1,-1,  1,-1,  1,-1,  -1,1,  1,-1
// Differential Manchester: [-1, 1,   1, -1,   1, -1,   1, -1,   -1, 1,   1, -1,   1, -1,   1, -1,   1, -1,   -1, 1,   1, -1]
import java.util.*;

public class P1_linencoding 
{
    public static int[] unipolar(int b[])
    {
        int uni[] = new int[b.length];
        for(int i=0; i<b.length; i++)
        {
            uni[i] = b[i];
        }
        return uni;
    }

    public static int[] nrzL(int b[])
    {
        int nrzl[] = new int[b.length];
        for(int i=0; i<b.length; i++)
        {
            if(b[i]==0)
            {
                nrzl[i] = 1;
            }
            else
            {
                nrzl[i] = -1;
            }
        }
        return nrzl;
    }

    public static int[] nrzI(int b[])
    {
        int nrzi[] = new int[b.length];
        int bit=1;
        for(int i=0; i<b.length; i++)
        {
            if(b[i]==1)
            {
                bit*=-1;
            }
            nrzi[i]=bit;
        }
        return nrzi;
    }

    public static int[] man(int b[])
    {
        int man[] = new int[b.length*2];
        int j = 0;
        for(int i=0; i<b.length; i++)
        {
            if(b[i]==0)
            {
                j=i*2;
                man[j]=1;
                man[j+1]=0;
                
            }
            else
            {
                j=i*2;
                man[j]=0;
                man[j+1]=1;
            }
        }
        return man;
    }

    public static int[] dman(int b[])
    {
        int dman[] = new int[b.length*2];
        int ll = 1, j=0;
        for(int i=0; i<b.length; i++)
        {
            if(b[i]==0)
            {
                j=i*2;
                dman[j] = -ll;
                dman[j+1] = ll;
            }
            else
            {
                j=i*2;
                dman[j] = ll;
                ll = -ll;
                dman[j+1] = ll;
            }
        }
        return dman;
    }

    public static int[] rz(int b[])
    {
        int rz[] = new int[b.length*2];
        int j = 0;
        for(int i=0; i<b.length; i++)
        {
            if(b[i]==0)
            {
                j=i*2;
                rz[j]=-1;
                rz[j+1]=0;
                
            }
            else
            {
                j=i*2;
                rz[j]=1;
                rz[j+1]=0;
            }
        }
        return rz;
    }

    public static int[] ami(int b[])
    {
        int ami[] = new int[b.length];
        int j = 1;
        for(int i=0; i<b.length; i++)
        {
            if(b[i]==1)
            {
                j*=-1;
                ami[i]=j;                
            }
            else
            {
                ami[i]=0;
            }
        }
        return ami;
    }

    public static int[] pse(int b[])
    {
        int ami[] = new int[b.length];
        int j = 1;
        for(int i=0; i<b.length; i++)
        {
            if(b[i]==0)
            {
                j*=-1;
                ami[i]=j;                
            }
            else
            {
                ami[i]=0;
            }
        }
        return ami;
    }


    public static void main(String[] args) 
    {
        
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter bit stream(only 0s and 1s): ");
        String b = sc.nextLine();
        if(!b.matches("[01]+"))
        {
            System.out.println("Invalid input, Enter 0s and 1s");
            return;
        }

        int bits[] = new int[b.length()];
        for(int i=0; i<b.length(); i++)
        {
            bits[i] = b.charAt(i) - '0';
        }

        System.out.println("\nUNIPOLAR\n NRZ: " + Arrays.toString(unipolar(bits)));
        System.out.println("\nPOLAR:");
        System.out.println(" NRZ-l: " + Arrays.toString(nrzL(bits)));
        System.out.println(" NRZ-I: " + Arrays.toString(nrzI(bits)));
        System.out.println(" Manchester(IEEE): " + Arrays.toString(man(bits)));
        System.out.println(" DIfferntial Manchester(IEEE): " + Arrays.toString(dman(bits)));
        System.out.println(" RZ(Return to Zero): " + Arrays.toString(rz(bits)));
        System.out.println("\nBIPOLAR:");
        System.out.println(" AMI(Alternate Mark Inversion): " + Arrays.toString(ami(bits)));
        System.out.println(" Pseudoternary: " + Arrays.toString(pse(bits)));
        System.out.println();
    }
}
