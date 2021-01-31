package simulador_threads;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class T1 extends Thread {
    
    Semaphore r3;
    Semaphore r1;
    ArrayList<Integer> somaR1;
    ArrayList<Integer> somaR3;
    int requisitaR3;
    int requisitaR1;
    int totalR3 = 0;
    int totalR1 = 0;
    boolean estado;
    
    public T1 (Semaphore r3, Semaphore r1, int requisitaR3, int requisitaR1, ArrayList<Integer> somaR3, ArrayList<Integer> somaR1, boolean estado  ){
        this.r3 = r3;
        this.r1 = r1;
        this.requisitaR3 = requisitaR3;
        this.requisitaR1 = requisitaR1;
        this.estado = estado;
        this.somaR3 = somaR3;
        this.somaR1 = somaR1;
        start();
    }
    
    @Override
    public void run(){
        System.out.println("O PROCESSO 1 solicitou " +requisitaR3+ " instâncias de R3 e "+requisitaR3+ " instâncias de R1");
        Estado();
        if (estado == false){
            System.out.println("PROCESSO 1 EM ESTADO INSEGURO!... MODO ESPERA 凸(｀0´)凸");
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(T1.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(true){
            if (estado == true){
                try {
                    Alocando();
                    Executando();
                } catch (InterruptedException ex) {
                    Logger.getLogger(T1.class.getName()).log(Level.SEVERE, null, ex);
                }
                Encerrando();
                break;
            }
            else{
                Estado();
            }
        }
    }
    
    
    public void Estado(){
        somaR3.add (requisitaR3);
        somaR1.add (requisitaR1);
        for (int i = 0; i < somaR3.size(); i++) {
            totalR3 = totalR3 + somaR3.get(i);
        }
        for (int i = 0; i < somaR1.size(); i++) {
            totalR1 = totalR1 + somaR1.get(i);
        }
        
        if(totalR3 <= r3.availablePermits() && totalR1 <= r1.availablePermits()){
            estado = true;
        }
        else{
            estado = false;
        }
    }
    
    public void Alocando() throws InterruptedException{
        Thread.sleep(1000);
        System.out.println("Alocando " +requisitaR3+ " instâncias de R3 e " +requisitaR1+ " instâncias de R1 no PROCESSO 1");
        r3.tryAcquire(requisitaR3);
        r1.tryAcquire(requisitaR1);
    }
    
    public void Executando() throws InterruptedException{
        System.out.println("(ﾉ◕ヮ◕)ﾉ PROCESSO 1 EXECUTANDO *:･ﾟ✧");
        Thread.sleep(5000);
    }
    
    public void Encerrando(){
        System.out.println("O PROCESSO 1 ENCERROU SUA EXECUÇÃO (。･∀･)ﾉ");
        System.out.println("O PROCESSO 1 devolveu " +requisitaR3+ " instâncias de R3 e " +requisitaR1+ " instâncias de R1 ao sistema");
        r3.release(requisitaR3);
        r1.release(requisitaR1);
        estado = false;
        somaR1.clear();
        somaR3.clear();

    }
    
}
