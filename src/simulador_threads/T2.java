package simulador_threads;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class T2 extends Thread {
    
    Semaphore r2;
    Semaphore r4;
    ArrayList<Integer> somaR2;
    ArrayList<Integer> somaR4;
    int requisitaR2;
    int requisitaR4;
    int totalR2 = 0;
    int totalR4 = 0;
    boolean estado;
    
    public T2 (Semaphore r2, Semaphore r4, int requisitaR2, int requisitaR4, ArrayList<Integer> somaR2, ArrayList<Integer> somaR4, boolean estado  ){
        this.r2 = r2;
        this.r4 = r4;
        this.requisitaR2 = requisitaR2;
        this.requisitaR4 = requisitaR4;
        this.estado = estado;
        this.somaR2 = somaR2;
        this.somaR4 = somaR4;
        start();
    }
    
    @Override
    public void run(){
        System.out.println("O PROCESSO 2 solicitou " +requisitaR2+ " instâncias de R2 e "+requisitaR4+ " instâncias de R4");
        Estado();
        if (estado == false){
            System.out.println("PROCESSO 2 EM ESTADO INSEGURO!... MODO ESPERA 凸(｀0´)凸");
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
                    Logger.getLogger(T2.class.getName()).log(Level.SEVERE, null, ex);
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
        somaR4.add (requisitaR4);
        for (int i = 0; i < somaR2.size(); i++) {
            totalR2 = totalR2 + somaR2.get(i);
        }
        for (int i = 0; i < somaR4.size(); i++) {
            totalR4 = totalR4 + somaR4.get(i);
        }
        
        if(totalR2 <= r2.availablePermits() && totalR4 <= r4.availablePermits()){
            estado = true;
        }
        else{
            estado = false;
        }
    }
    
    public void Alocando() throws InterruptedException{
        Thread.sleep(1000);
        System.out.println("Alocando " +requisitaR2+ " instâncias de R2 e " +requisitaR4+ " instâncias de R4 no PROCESSO 2");
        r2.tryAcquire(requisitaR2);
        r4.tryAcquire(requisitaR4);
    }
    
    public void Executando() throws InterruptedException{
        System.out.println("(ﾉ◕ヮ◕)ﾉ PROCESSO 2 EXECUTANDO *:･ﾟ✧");
        Thread.sleep(5000);
    }
    
    public void Encerrando(){
        System.out.println("O PROCESSO 2 ENCERROU SUA EXECUÇÃO (。･∀･)ﾉ");
        System.out.println("O PROCESSO 2 devolveu " +requisitaR2+ " instâncias de R2 e " +requisitaR4+ " instâncias de R4 ao sistema");
        r2.release(requisitaR2);
        r4.release(requisitaR4);
        estado = false;
        somaR2.clear();
        somaR4.clear();
    }
    
}
