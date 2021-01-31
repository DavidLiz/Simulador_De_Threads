package simulador_threads;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class T3 extends Thread {
    
    Semaphore r5;
    ArrayList<Integer> somaR5;
    int requisitaR5;
    int totalR5 = 0;
    boolean estado;
    
    public T3 (Semaphore r5, int requisitaR5, ArrayList<Integer> somaR5, boolean estado){
        this.r5 = r5;
        this.requisitaR5 = requisitaR5;
        this.estado = estado;
        this.somaR5 = somaR5;
        start();
    }
    
    @Override
    public void run(){
        System.out.println("O PROCESSO 3 solicitou " +requisitaR5+ " instâncias de R5");
        Estado();
        if (estado == false){
            System.out.println("PROCESSO 3 EM ESTADO INSEGURO!... MODO ESPERA 凸(｀0´)凸");
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
                    Logger.getLogger(T3.class.getName()).log(Level.SEVERE, null, ex);
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
        somaR5.add (requisitaR5);
        for (int i = 0; i < somaR5.size(); i++) {
            totalR5 = totalR5 + somaR5.get(i);
        }
        
        if(totalR5 <= r5.availablePermits()){
            estado = true;
        }
        else{
            estado = false;
        }
    }
    
    public void Alocando() throws InterruptedException{
        Thread.sleep(1000);
        System.out.println("Alocando " +requisitaR5+ " instâncias de R3 no PROCESSO 3");
        r5.tryAcquire(requisitaR5);
    }
    
    public void Executando() throws InterruptedException{
        System.out.println("(ﾉ◕ヮ◕)ﾉ PROCESSO 3 EXECUTANDO *:･ﾟ✧");
        Thread.sleep(5000);
    }
    
    public void Encerrando(){
        System.out.println("O PROCESSO 3 ENCERROU SUA EXECUÇÃO (。･∀･)ﾉ");
        System.out.println("O PROCESSO 3 devolveu " +requisitaR5+ " instâncias de R5 ao sistema");
        r5.release(requisitaR5);
        estado = false;
        somaR5.clear();
    }   
}