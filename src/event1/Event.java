/**
 * La clase Event contiene los métodos necesarios para interactuar con los distintos hilos.
 * En esta clase se crea un monitor del objeto instanciado de la misma
 * y así aseguramos la exclusión mutua en el uso de dicho objeto.
 */
package event1;

public class Event {
	
	//La variable contadorAlta mantiene la cuenta del número de hilos de alta prioridad que están dormidos.
	private short contadorAlta;
	
	//El método constructor inicializa la variable contadorAlta.
	public Event() {
		contadorAlta = 0;
	}
	
	
	//El método waitAltaPrioridad gestiona los hilos de alta prioridad.
	//También se encarga de incrementar y decrementar la variable contadorAlta.
	//Al comenzar, libera el objeto y se queda a la espera de una notificación.
	public synchronized void waitAltaPrioridad() {
		contadorAlta++;
		System.out.println("Thread " + Thread.currentThread().getId() + " -- Alta -- Se DUERME (wait) -- contadorAlta: " + contadorAlta);
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		contadorAlta--;
		System.out.println("Thread " + Thread.currentThread().getId() + " -- Alta -- Se DESPIERTA (notifyAll) -- contadorAlta: " + contadorAlta);
	}
	
	
	//El método waitBajaPrioridad gestiona los hilos de baja prioridad.
	//Al comenzar, libera el objeto y se queda a la espera de una notificación.
	//Depende de la variable contadorAlta,
	//hasta que no es igual a cero los hilos de baja prioridad se iran durmiendo.
	public synchronized void waitBajaPrioridad() {
		do {
			System.out.println("Thread " + Thread.currentThread().getId() + " -- Baja -- Se DUERME (wait)");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (contadorAlta > 0);
			System.out.println("Thread " + Thread.currentThread().getId() + " -- Baja -- Se DESPIERTA (notifyAll)");
			
	}
	
	
	//El metodo signalEvent despierta a todos los hilos que se encuentren en la cola de hilos,
	//tanto de baja prioridad, como de alta prioridad.
	public synchronized void signalEvent() {
		notifyAll();
		System.out.println("\nsignalEvent -- Despierta a TODOS (notifyAll) -- Thread " + Thread.currentThread().getId());
	}
}