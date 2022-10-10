
package com.example.springboot;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.LongSerializer;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.Consumer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.concurrent.ExecutionException;
import java.util.Collections;
import java.util.Properties;
import java.util.ArrayList;
import java.util.List;

import com.example.entity.KafkaIMS;

@RestController
public class KafkaTest {
	
	private List<escuchaKafka> hilos = new ArrayList<>();

	@GetMapping("/Kafka/Evento-1")
	public String obtenerMensajes(@RequestBody KafkaIMS k) {
		
		String[] texto = {""};
		
		final Properties configuracion = new Properties();
		configuracion.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		configuracion.put(ConsumerConfig.GROUP_ID_CONFIG, k.getTopico());
		configuracion.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configuracion.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		
		final Consumer<String, String> cliente = new KafkaConsumer<>(configuracion);		
		cliente.subscribe(Collections.singletonList(k.getTopico()));
		ConsumerRecords<String, String> data = cliente.poll(10000);
		data.forEach(linea -> {
			texto[0] += linea.value() + "\n";
		});
		cliente.close();
		return texto[0];
	}
	
	@PostMapping("/Kafka/Evento-1/enviar")
	public String enviarMensaje(@RequestBody KafkaIMS k) throws InterruptedException, ExecutionException {
		
		String respuesta = "";
		
		final Properties configuracion = new Properties();		
		configuracion.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		configuracion.put(ProducerConfig.CLIENT_ID_CONFIG, "evento-1");
		configuracion.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
		configuracion.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		
		final Producer<String, String> publicador = new KafkaProducer<>(configuracion);		
		try {
			final ProducerRecord<String, String> linea = new ProducerRecord<String, String>("evento-1", k.getMensaje());
			RecordMetadata meta = publicador.send(linea).get();
			respuesta = "Key = " + linea.key() + " mensaje = " + linea.value() + " partition = " + meta.partition() + " " + meta.offset(); 
		} finally {
			publicador.flush();
			publicador.close();
		}
		return respuesta;
	}
	
	@PostMapping("Kafka/Evento-1/iniciar")
	public String iniciarEsperarMensajes(@RequestBody KafkaIMS k) {
		escuchaKafka task = new escuchaKafka();
		task.setTopico(k.getTopico());
		this.hilos.add(task);
		new Thread(task).start();
		return "PID registrado: " + task.getPID();
	}
	
	@GetMapping("Kafka/Evento-1/activos")
	public String obtenerIniciados(@RequestBody KafkaIMS k) {
		String salida = "";
		for(escuchaKafka hilo : this.hilos) {
			if(hilo.getTopico().equals(k.getTopico())) {
				salida += hilo.getPID() + " " + (!hilo.isSalir() ? "activo" : "terminado") + ",";
			}
		}
		if(!salida.isBlank()) {
			salida = salida.substring(0,salida.length()-1);
		}
		return salida;
	}
	
	@PostMapping("Kafka/Evento-1/terminar")
	public String terminarEsperarMensajes(@RequestBody KafkaIMS k) throws InterruptedException {
		String razon = "PID no encontrado, PID registrados [";
		for(escuchaKafka hilo : this.hilos) {
			if(hilo.getPID().equals(k.getPID())) {
				hilo.terminar();
				return "terminado PID: " + k.getPID() + "";
			}else {
				razon += hilo.getPID() + " - " + k.getPID() + ",";
			}
		}
		razon = razon.substring(0, razon.length() - 1) + "]"; 
		return "no terminado:" + razon;
	}
	
}