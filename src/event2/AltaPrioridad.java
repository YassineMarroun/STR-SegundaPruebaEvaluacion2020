/**
 * La clase AltaPrioridad se utiliza para crear hilos de alta prioridad.
 * Recibe un objeto Event, el cual se usa para llamar al metodo waitAltaPrioridad.
 */
package event2;

public class AltaPrioridad implements Runnable{
	Event event;

	//El metodo constructor inicializa el atributo event
	//con el objeto pasado como parametro desde el main.
	public AltaPrioridad(Event event) {
		this.event = event;
	}
	
	//Contiene el codigo que sera ejecutado por un hilo.
	@Override
	public void run() {
		event.waitAltaPrioridad();
	}
}