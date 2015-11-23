/*
 * Programa que simule un buzón de correo (recurso compartido), de forma que se 
 * pueda leer un mensaje o enviarlo. El buzón sólo puede almacenar un mensaje, de 
 * forma que para poder escribir se debe de encontrar vacío y para poder leer debe 
 * de estar lleno. Crear varios hilos lectores y escritores que manejen el buzón de
 * forma sincronizada.
 */
package ejercicio7;

/**
 *
 * @author Patri
 */
public class Ejercicio7 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Buzon buzon1 = new Buzon();
        
        //Se crea y arranca hilos de ejecucion
        //Hilos de escritura de mensaje
        new EscritorThread(buzon1, "Hola").start(); 
        new EscritorThread(buzon1, "Hola2").start();
        new EscritorThread(buzon1, "Hola3").start();
        new EscritorThread(buzon1, "Hola4").start();
        new EscritorThread(buzon1, "Hola5").start();
        new EscritorThread(buzon1, "Hola6").start();
        
       //Hilos de lectura de mensaje
        new LectorThread(buzon1).start();
        new LectorThread(buzon1).start();
        new LectorThread(buzon1).start();
        new LectorThread(buzon1).start();
        new LectorThread(buzon1).start();
        new LectorThread(buzon1).start();
        
    }

}
/**
 * Hilo que lee el mensaje
 * @author Patri
 */

class LectorThread extends Thread {

    private Buzon buzon;
    Thread thread1;

    LectorThread(Buzon b) {
        thread1 = new Thread();
        // Mantiene una copia propia del objeto compartido
        this.buzon = b;
    }

    public void run() {
        buzon.leer();
        System.out.println("Lectura" + buzon.toString());
    }
}

/**
 * Hilo que escribe el mensaje
 * @author Patri
 */

class EscritorThread extends Thread {

    private Buzon buzon;
    String mensaje;
    Thread thread1;

    EscritorThread(Buzon b, String mensaje) {
        thread1 = new Thread();
        // Mantiene una copia propia del objeto compartido
        this.buzon = b;
        this.mensaje = mensaje;
    }

    public void run() {
        buzon.escribir(mensaje);
        System.out.println("Escritura" + buzon.toString());
    }
}
/**
 * Clase monitor con metodos sincronizados
 * @author Patri
 */

class Buzon {

    String mensaje;
    boolean estaVacio;

    public Buzon() {
        //El buzon esta vacio cuando se crea
        this.estaVacio = true;
    }

    public synchronized void leer() {
        // No se puede leer el mensaje si el buzon(variable) esta vacio
        while (estaVacio == true) {
            try {
                wait(); // Se sale cuando estaVacio cambia a false
            } catch (InterruptedException e) {

            }
        }
        // Se imprime el mensaje, se borra el mensaje y se indica mediante la variable que esta el buzon vacio
        System.out.println("Mensaje:" + mensaje);
        mensaje = "";
        //Comprobamos si el mensaje esta vacio o lleno y segun eso se modifica la variable estaVacio
        if(mensaje.equalsIgnoreCase("")){
            estaVacio = true;
        }else{
           estaVacio = false; 
        }
        //Avisamos a cualquier proceso(el otro metodo) que esta a la espera que ya puede acceder al objeto
        notify();
        
        
    }

    public synchronized void escribir(String mensaje) {
        // No se puede escribir el mensaje si el buzon(variable) esta lleno
        while (estaVacio == false) {
            try {
                wait(); // Se sale cuando estaVacio cambia a true
            } catch (InterruptedException e) {

            }
        }
        this.mensaje = mensaje;
        //Comprobamos si el mensaje esta vacio o lleno y segun eso se modifica la variable estaVacio
        if(mensaje.equalsIgnoreCase("")){
            estaVacio = true;
        }else{
           estaVacio = false; 
        }
        //Avisamos a cualquier proceso(el otro metodo) que esta a la espera que ya puede acceder al objeto
        notify();
    }

    @Override
    public String toString() {
        return "Buzon{" + "mensaje=" + mensaje + ", estaVacio=" + estaVacio + '}';
    }
    
    

}
