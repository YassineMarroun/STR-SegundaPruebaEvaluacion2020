/**
 * La clase SignalEvent se utiliza para despertar todos los hilos dormidos.
 * Recibe un objeto Event, el cual se usa para llamar al metodo signalEvent.
 */
package event2;

public class SignalEvent implements Runnable{
	Event event;

	//El metodo constructor inicializa el atributo event
	//con el objeto pasado como parametro desde el main.
	public SignalEvent(Event event) {
		this.event = event;
	}
	
	//Contiene el codigo que sera ejecutado por un hilo.
	@Override
	public void run() {
		event.signalEvent();
	}
}