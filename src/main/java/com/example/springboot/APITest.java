
package com.example.springboot;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.entity.Comentarios;

import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.net.URL;

@RestController
public class APITest {

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}
	
	@GetMapping("/comentarios")
	public String obtenerComentarios() throws IOException {
		URL path = new URL("http://jsonplaceholder.typicode.com/comments");
		HttpURLConnection conexion = (HttpURLConnection) path.openConnection();
		conexion.setRequestMethod("GET");
		if(conexion.HTTP_OK == conexion.getResponseCode()) {
			BufferedReader br = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
			String linea;
			StringBuffer buffer = new StringBuffer();
			
			while((linea = br.readLine()) != null){
				buffer.append(linea);
			}
			br.close();
			return buffer.toString();
		}else {
			return "NO DATA";
		}
	}
	
	@GetMapping("/comentario")
	public String obtenerComentario(@RequestBody Comentarios comentario) throws IOException {
		URL path = new URL("http://jsonplaceholder.typicode.com/comments?id="+comentario.getID());
		HttpURLConnection conexion = (HttpURLConnection) path.openConnection();
		conexion.setRequestMethod("GET");
		if(conexion.HTTP_OK == conexion.getResponseCode()) {
			BufferedReader br = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
			String linea;
			StringBuffer buffer = new StringBuffer();
			
			while((linea = br.readLine()) != null){
				buffer.append(linea);
			}
			br.close();
			return buffer.toString();
		}else {
			return "NO DATA";
		}
	}
	
	@PostMapping("/comentario/nuevo")
	public String nuevoComentario(@RequestBody Comentarios comentario) throws IOException {
		URL path = new URL("https://jsonplaceholder.typicode.com/comments");
		HttpURLConnection conexion = (HttpURLConnection) path.openConnection();
		
		Map<String, Object> mapa = new HashMap<>();
		mapa.put("postid", comentario.getPostID());
		mapa.put("id", comentario.getID());
		mapa.put("name", comentario.getName());
		mapa.put("email", comentario.getEmail());
		mapa.put("body", comentario.getBody());
		
		String json = new ObjectMapper().writeValueAsString(mapa);
		
		conexion.setDoInput(true);
		conexion.setDoOutput(true);
		conexion.setRequestMethod("POST");
		conexion.setRequestProperty("Content-Type", "application/json");
		conexion.setRequestProperty("Accept", "application/json");

		try (OutputStream out = conexion.getOutputStream()) {
			out.write(json.getBytes(), 0, json.getBytes().length);
		}
		return conexion.getResponseMessage();
	}
	
	@PutMapping("/comentario/actualizar")
	public String actualizarComentario(@RequestBody Comentarios comentario) throws IOException {
		URL path = new URL("https://jsonplaceholder.typicode.com/comments");
		HttpURLConnection conexion = (HttpURLConnection) path.openConnection();
		
		Map<String, Object> mapa = new HashMap<>();
		mapa.put("postid", comentario.getPostID());
		mapa.put("id", comentario.getID());
		mapa.put("name", comentario.getName());
		mapa.put("email", comentario.getEmail());
		mapa.put("body", comentario.getBody());
		
		String json = new ObjectMapper().writeValueAsString(mapa);
		conexion.setDoInput(true);
		conexion.setDoOutput(true);
		conexion.setRequestMethod("PUT");
		conexion.setRequestProperty("Content-Type", "application/json");
		conexion.setRequestProperty("Accept", "application/json");
		
		try (OutputStream out = conexion.getOutputStream()) {
			out.write(json.getBytes(), 0, json.getBytes().length);
		}
		return conexion.getResponseMessage();
	}
	
	@DeleteMapping("/comentario/eliminar")
	public String eliminarComentario(@RequestBody Comentarios comentario)  throws IOException {
		URL path = new URL("https://jsonplaceholder.typicode.com/comments?id="+comentario.getID());
		HttpURLConnection conexion = (HttpURLConnection) path.openConnection();
		conexion.setRequestMethod("DELETE");
		return conexion.getResponseMessage();
	}

}
