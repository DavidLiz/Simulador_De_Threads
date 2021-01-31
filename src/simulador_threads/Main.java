package simulador_threads;
import java.util.concurrent.Semaphore;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    
    public static void main(String args[]) {        
        boolean estado;
        
        Semaphore r1 = new Semaphore (5);
        Semaphore r2 = new Semaphore (4);
        Semaphore r3 = new Semaphore (5);
        Semaphore r4 = new Semaphore (4);
        Semaphore r5 = new Semaphore (3);
        
        ArrayList<Integer> somaR1 = new ArrayList<Integer> ();
        ArrayList<Integer> somaR2 = new ArrayList<Integer> ();
        ArrayList<Integer> somaR3 = new ArrayList<Integer> ();
        ArrayList<Integer> somaR4 = new ArrayList<Integer> ();
        ArrayList<Integer> somaR5 = new ArrayList<Integer> ();
        
        
        T1 thread_1 = new T1 (r3, r1, 3, 1, somaR3, somaR1, false);
        T2 thread_2 = new T2 (r2, r4, 2, 3, somaR2, somaR4, false);
        T3 thread_3 = new T3 (r5, 3, somaR5, false);
        T4 thread_4 = new T4 (r2, r1, 1, 5, somaR2, somaR1, false);
        T5 thread_5 = new T5 (r4, r3, r1, 2, 2, 1,somaR4, somaR3, somaR1, false);         
    }
}
