
package com.example.springboot;

import com.example.entity.KafkaIMS;

public class escuchaKafka implements Runnable{

	private volatile boolean salir = false;
	private int local_indice = 0;
	private String PID, topico;
	
	@Override
	public void run() {
		this.PID = Thread.currentThread().getId() + "";
		while(!this.salir) {
			this.local_indice++;
			KafkaTest t = new KafkaTest();
			KafkaIMS k = new KafkaIMS();
			k.setTopico(topico);
			String mensaje = t.obtenerMensajes(k);
			if(!mensaje.isBlank()) {
				System.out.println(this.local_indice + "- [PID]: " + this.PID + ", mensaje: " + mensaje);
			}else {
				System.out.println(this.local_indice + "- [PID]: " + this.PID + " <espera>");
			}
			try {
			Thread.sleep(1000);
			}catch (InterruptedException exc) {
				System.out.println(exc);
			}
		}
		System.out.println("Terminado con exito");
	}
	
	public void terminar() {
		this.salir = true;
	}
	
	public String getPID() {
		return PID;
	}

	public boolean esPID(String PID) {
		return this.PID.equals(PID) || this.PID == PID || Integer.parseInt(this.PID) == Integer.parseInt(PID);
	}

	public boolean isSalir() {
		return salir;
	}

	public String getTopico() {
		return topico;
	}

	public void setTopico(String topico) {
		this.topico = topico;
	}
}
