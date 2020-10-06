/**
 * La clase Event contiene los métodos necesarios para interactuar con los distintos hilos.
 * En esta clase se crea un monitor del objeto instanciado de la misma
 * y así aseguramos la exclusión mutua en el uso de dicho objeto.
 */
package event2;

public class Event {
	
	//La variable id  guarda el Thread ID del hilo que se quiere despertar.
	private String id;
	
	
	//El método constructor inicializa la variable id como una cadena vacia.
	public Event() {
		id = "";
	}
	
	
	//El metodo setID establece el atributo id de la clase Event con el valor de id pasado como parametro,
	//el valor introducido por teclado.
	public void setID(String id) {
		this.id = id;
	}
	
	
	//El método waitAltaPrioridad gestiona los hilos de alta prioridad.
	//Al comenzar, libera el objeto y se queda a la espera de una notificación.
	//En cada iteracion se comprueba si el id del hilo corresponde con el id del hilo que se busca para finalizar,
	//si es asi, se finaliza el hilo y si no es, se vuelve a dormir dicho hilo.
	public synchronized void waitAltaPrioridad() {
		
		//Variable temporal para guardar el Thread del hilo.
		String idTemp = "";
		
		do {
			System.out.println("Thread " + Thread.currentThread().getId() + " -- Alta -- Se DUERME (wait)");
			try {
				wait();
			} catch (InterruptedException e) {
			}
			idTemp = Long.toString(Thread.currentThread().getId());
		} while(!id.contentEquals(idTemp));
		
		System.out.println("Thread " + Thread.currentThread().getId() + " -- Alta -- Se DESPIERTA (notifyAll)");
	}
	
	
	//El método waitBajaPrioridad gestiona los hilos de baja prioridad.
	//Al comenzar, libera el objeto y se queda a la espera de una notificación.
	//En cada iteracion se comprueba si el id del hilo corresponde con el id del hilo que se busca para finalizar,
	//si es asi, se finaliza el hilo y si no es, se vuelve a dormir dicho hilo.
	public synchronized void waitBajaPrioridad() {
		
		//Variable temporal para guardar el Thread del hilo.
		String idTemp = "";
			
		do {
			System.out.println("Thread " + Thread.currentThread().getId() + " -- Baja -- Se DUERME (wait)");
				try {
					wait();
				} catch (InterruptedException e) {
				}
				idTemp = Long.toString(Thread.currentThread().getId());
		} while(!id.contentEquals(idTemp));
			
		System.out.println("Thread " + Thread.currentThread().getId() + " -- Baja -- Se DESPIERTA (notifyAll)");
	}
	
	
	//El metodo signalEvent despierta a todos los hilos que se encuentren en la cola de hilos,
	//tanto de baja prioridad, como de alta prioridad.
	public synchronized void signalEvent() {
		notifyAll();
		System.out.println("\nsignalEvent -- Despierta a TODOS (notifyAll) -- Thread " + Thread.currentThread().getId());
	}
}