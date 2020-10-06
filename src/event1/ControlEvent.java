/**
 * La clase ControlEvent contiene el metodo main para iniciar el programa.
 */
package event1;
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
		
		//Se van liberando los hilos. Primero los de alta prioridad y despues los de baja prioridad.
		//Mientras existan hilos de baja prioridad dormidos, seguiremos llamando al metodo signalEvent hasta conseguir liberar todos.
		//Mientras dure el proceso, se siguen creando la misma cantidad de hilos de alta prioridad e hilos de baja prioridad que al principio.
		while(checkThreadsBaja(numHilos * 2)) {
			threadSignal = new Thread(new SignalEvent(event));
			threadSignal.start();
			
			Thread.sleep(500);
			
			if(hilosIniciados < numHilos * 2) {
				System.out.println("\n[" + (hilosIniciados - numHilos + 1) + "/" + numHilos + "] Se crea un hilo de Alta Prioridad y otro hilo de Baja Prioridad\n");
				threadsAlta[hilosIniciados] = new Thread(new BajaPrioridad(event));
				threadsAlta[hilosIniciados].start();
				
				Thread.sleep(500);
				
				threadsBaja[hilosIniciados] = new Thread(new AltaPrioridad(event));
				threadsBaja[hilosIniciados].start();
				
				Thread.sleep(500);
				
				hilosIniciados++;
			}
		}
		sc.close();
		System.out.println("\nFIN\n");
	}
	
	
	//El metodo checkThreadsBaja comprueba si existen hilos de baja prioridad sin finalizar. Asi,
	//se controla que el programa finaliza cuando no quede ningun hilo de baja prioridad sin finalizar.
	public static boolean checkThreadsBaja(int baja) {
		int i;
		for(i = 0; i < baja; i++) {
			if(!threadsBaja[i].getState().toString().equals("TERMINATED")) {
				return(true);
			}
		}
		return(false);
	}
}