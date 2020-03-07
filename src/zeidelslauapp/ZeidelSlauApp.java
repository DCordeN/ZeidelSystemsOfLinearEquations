package zeidelslauapp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class ZeidelSlauApp{
    
    static int N = 4;
    
    public static void main(String[] args) throws FileNotFoundException {
        double[][] A = new double[N][N];
        double[] b = new double[N];
        double[] x = new double[N];
        
        Scanner sc = new Scanner(new BufferedReader(new FileReader("matrix.txt")));
        
        while(sc.hasNextLine()){
            for(int i = 0; i < N; i++){
                String[] line = sc.nextLine().trim().split(" ");
                for(int j = 0; j < N; j++)
                    A[i][j] = Double.parseDouble(line[j]);
            }
        }
        
        double temp_s = 0;
        boolean flag = false;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++)
                if(i != j)
                    temp_s += Math.abs(A[i][j]);
            if(temp_s > A[i][i])
                flag = true;
            
        }
        
        if(flag == false){
            Scanner sc2 = new Scanner(new BufferedReader(new FileReader("vector.txt")));

            while(sc2.hasNextLine()){
                String[] line = sc2.nextLine().trim().split(" ");
                for(int i = 0; i < N; i++)
                    b[i] = Double.parseDouble(line[i]);
            }

            System.out.println("A = ");
            print_m(A);
            System.out.println("b = ");
            print_v(b);
            System.out.println("Seildel's method.");
            System.out.println("********************");
            transformSystem(A, b, x);
            System.out.println("System after transforming: ");
            print_sys(A, b, x);
            System.out.println("****************************************");
            System.out.println("****************************************");

            solution(A, b, x);
        }
        else{
            System.out.println("A = ");
            print_m(A);
            System.out.println("WRONG MATRIX!");   
        }
    }
    
    
    static void transformSystem(double[][] A, double[] b, double[] x){
        double key;
        for(int i = 0; i < N; i++)
            if(A[i][0] != 0){
                key = A[i][i];
                b[i] /= key;
                for(int j = 0; j < N; j++)
                    if(j != i)
                        A[i][j] /= -key;
                    else
                        A[i][j] /= key;
            }
    }
    
    static void print_sys(double[][] A, double[] b, double[] x){
        for(int i = 0; i < N; i++){
            System.out.print("x" + i + " = " + b[i]);
            for(int j = 0; j < N; j++){
                if(i != j)
                    if(A[i][j] < 0)
                        System.out.print(A[i][j] + "x" + j);
                    else
                        System.out.print("+" + A[i][j] + "x" + j);
                if(i == j && j != N-1){
                    j++;
                    System.out.print(A[i][j] + "x" + j);   
                }
            }    
            System.out.println("");
        }
    }
    static void print_m(double[][] a){
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++)
                System.out.print(a[i][j] + "\t");
            System.out.println("");
        }
        System.out.println("");
    }
    
    static void print_v(double[] v){
        for(int i = 0; i < N; i++)
                System.out.print(v[i] + "\t");
        System.out.println("");
    }
    
    static void solution(double[][] a, double[] b, double[] x){
        System.out.println("Table of roots:");
        System.out.print("k = " + 0 + "\t");
        x[0] = b[0];        
        print_v(x);
        double sum;
        double eps = 0.0000001;
        double max_diff = 0;
        int k = 0;
        do{
            for(int i = 0; i < N; i++){
                max_diff = 0;
                sum = 0;
                for(int j = 0; j < N; j++)
                    if(j != i)
                        sum += a[i][j]*x[j];
                if(Math.abs(x[i] - b[i] - sum) > max_diff)
                    max_diff = Math.abs(x[i] - b[i] - sum);
                x[i] = b[i] + sum;    
            }                
            k++;
            System.out.print("k = " + k + "\t");
            print_v(x);
        }while(max_diff > eps);
        System.out.println("eps = " + eps);        
        System.out.println("****************************************"); 
        System.out.println("Final answer: ");
        print_v(x);
    }
    
}
