/**
 * La clase ControlEvent contiene el metodo main para iniciar el programa.
 */
package event2;
import java.util.Scanner;

public class ControlEvent {

	//La variable constante ALTABAJAMAX define el maximo numero de hilos.
	static final int ALTABAJAMAX = 100;
	
	//Las variables threadsAlta, threadsBaja y threadSignal van almacenando
	//los hilos creados de alta prioridad, baja prioridad y signal.
	static Thread[] threadsAlta = new Thread[ALTABAJAMAX];
	static Thread[] threadsBaja = new Thread[ALTABAJAMAX];
	static Thread threadSignal = new Thread();
	
	//Objeto Event sobre el que van a trabajar todos los hilos creados.
	static Event event = new Event();
	
	//El metodo main crea los distintos hilos de alta prioridad y baja prioridad.
	//Se van despertando los hilos, primero los de alta prioridad y despues los de baja prioridad.
	//En la cola de hilos dormidos estan ambos, por lo que, se despiertan todos de golpe, van finalizando los de alta prioridad
	//y los de baja prioridad se vuelven a dormir hasta que hayan sido liberados todos los hilos de alta prioridad. 
	public static void main(String[] args) throws InterruptedException {
		
		//La variable numHilos recoge el numero de hilos a crear, tanto de alta prioridad, como de baja prioridad.
		int numHilos;
		
		//La variable hilosIniciados se utiliza para mantener un control de los arrays usados para los hilos.
		int hilosIniciados = 0;
		
		//La variable sc recoge por teclado el numero de hilos a crear.
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\nIntroduzca el numero de hilos a crear (maximo " + ALTABAJAMAX + "): ");
		numHilos = Integer.parseInt(sc.nextLine());
		
		System.out.println("\n--> Se inician los hilos <--\n");
		
		//Se van a crear, de forma alterna, el mismo numero de hilos de alta prioridad e hilos de baja prioridad.
		while(hilosIniciados < numHilos) {
			threadsBaja[hilosIniciados] = new Thread(new BajaPrioridad(event));
			threadsBaja[hilosIniciados].start();
			
			Thread.sleep(500);
			
			threadsAlta[hilosIniciados] = new Thread(new AltaPrioridad(event));
			threadsAlta[hilosIniciados].start();
			
			Thread.sleep(500);
			
			hilosIniciados++;
		}
		
		System.out.println("\n--> Se despiertan los hilos <--");
		
		//Mientras haya hilos dormidos, se indica el Thread ID del hilo que se quiere libera.
		//Se despiertan todos los hilos y se van recorriendo hasta dar con el hilo que corresponde con el ID introducido,
		//se finaliza dicho hilo, mientras los demas se vuelven a dormir.
		while(checkThreads(numHilos)) {
			System.out.println("\n--> Introduzca el ID del hilo que quiera despertar: ");
			event.setID(sc.nextLine());
			
			threadSignal = new Thread(new SignalEvent(event));
			threadSignal.start();
			
			Thread.sleep(500);
		}
		sc.close();
		System.out.println("\nFIN\n");
	}
	
	
	//El metodo checkThreads comprueba si existen hilos tanto de alta, como de baja prioridad sin finalizar.
	//Asi, se controla que el programa finaliza cuando no quede ningun hilo sin finalizar.
	public static boolean checkThreads(int hilos) {
		int i;
		for(i = 0; i < hilos; i++) {
			if(!threadsBaja[i].getState().toString().equals("TERMINATED")) {
				return(true);
			}
			if(!threadsAlta[i].getState().toString().equals("TERMINATED")) {
				return(true);
			}
		}
		return(false);
	}
}