package simulador_threads;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class T4 extends Thread {
    
    Semaphore r2;
    Semaphore r1;
    ArrayList<Integer> somaR2;
    ArrayList<Integer> somaR1;
    int requisitaR2;
    int requisitaR1;
    int totalR2 = 0;
    int totalR1 = 0;
    boolean estado;
    
    public T4 (Semaphore r2, Semaphore r1, int requisitaR2, int requisitaR1, ArrayList<Integer> somaR2, ArrayList<Integer> somaR1, boolean estado){
        this.r2 = r2;
        this.r1 = r1;
        this.requisitaR2 = requisitaR2;
        this.requisitaR1 = requisitaR1;
        this.estado = estado;
        this.somaR2 = somaR2;
        this.somaR1 = somaR1;
        start();
    }
    
    @Override
    public void run(){
        System.out.println("O PROCESSO 4 solicitou " +requisitaR2+ " instâncias de R2 e "+requisitaR1+ " instâncias de R1");
        Estado();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(T1.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (estado == false){
            System.out.println("PROCESSO 4 EM ESTADO INSEGURO!... MODO ESPERA 凸(｀0´)凸");
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
        somaR2.add (requisitaR2);
        somaR1.add (requisitaR1);
        for (int i = 0; i < somaR2.size(); i++) {
            totalR2 = totalR2 + somaR2.get(i);
        }
        for (int i = 0; i < somaR1.size(); i++) {
            totalR1 = totalR1 + somaR1.get(i);
        }
        
        if(totalR2 <= r2.availablePermits() && totalR1 <= r1.availablePermits()){
            estado = true;
        }
        else{
            estado = false;
        }
    }
    
    public void Alocando() throws InterruptedException{
        Thread.sleep(1000);
        System.out.println("Alocando " +requisitaR2+ " instâncias de R2 e " +requisitaR1+ " instâncias de R1 no PROCESSO 4");
        r2.tryAcquire(requisitaR2);
        r1.tryAcquire(requisitaR1);
    }
    
    public void Executando() throws InterruptedException{
        System.out.println("(ﾉ◕ヮ◕)ﾉ PROCESSO 4 EXECUTANDO *:･ﾟ✧");
        Thread.sleep(5000);
    }
    
    public void Encerrando(){
        System.out.println("O PROCESSO 4 ENCERROU SUA EXECUÇÃO (。･∀･)ﾉ");
        System.out.println("O PROCESSO 4 devolveu " +requisitaR2+ " instâncias de R2 e " +requisitaR1+ " instâncias de R1 ao sistema");
        r2.release(requisitaR2);
        r1.release(requisitaR1);
        estado = false;
        somaR1.clear();
        somaR2.clear();
    }
    
}