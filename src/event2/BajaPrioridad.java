/**
 * La clase BajaPrioridad se utiliza para crear hilos de baja prioridad.
 * Recibe un objeto Event, el cual se usa para llamar al metodo waitBajaPrioridad.
 */
package event2;

public class BajaPrioridad implements Runnable{
	Event event;

	//El metodo constructor inicializa el atributo event
	//con el objeto pasado como parametro desde el main.
	public BajaPrioridad(Event event) {
		this.event = event;
	}
	
	//Contiene el codigo que sera ejecutado por un hilo.
	@Override
	public void run() {
		event.waitBajaPrioridad();
	}
}