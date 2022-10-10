package com.example.springboot;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.File;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.List;
import java.net.URL;

import com.example.entity.Consul;
import com.example.entity.Policies;

@RestController
public class ConsulTest {
	
	@GetMapping("/Consul/Spring/registrar")
	public String registrarSpring(@RequestBody Consul service) throws FileNotFoundException, IOException {
		
		File archivoJSON = new File(this.getClass().getResource("../data/spring.json").getFile());
		FileInputStream fis = new FileInputStream(archivoJSON);		
		
		int character = 0;
		String json = "";
		while((character = fis.read()) != -1) {
			json += ((char) character);
		}
		
		URL consulPath = new URL("https://127.0.0.1:8531/v1/agent/service/register?replace-existing-checks=true");
		HttpURLConnection conexion = (HttpURLConnection) consulPath.openConnection();
		conexion.setDoInput(true);
		conexion.setDoOutput(true);
		conexion.setRequestMethod("PUT");
		conexion.setRequestProperty("X-Consul-Token", service.getToken());
		conexion.setRequestProperty("Content-Type", "application/json");
		conexion.setRequestProperty("Accept", "application/json");

		OutputStream os = conexion.getOutputStream();
		byte[] data = json.getBytes("UTF-8");
		os.write(data,0,data.length);		
		fis.close();		
		
		return conexion.getResponseMessage();
	}
	
	@GetMapping("/Consul/Spring/desregistrar")
	public String desregistrarSpring(@RequestBody Consul data) throws IOException {
		URL consulPath = new URL("https://127.0.0.1:8531/v1/agent/service/deregister/" + data.getId());
		HttpURLConnection conexion = (HttpURLConnection) consulPath.openConnection();
		conexion.setRequestMethod("PUT");
		conexion.setRequestProperty("X-Consul-Token", data.getToken());
		conexion.setRequestProperty("Accept", "application/json");
		return conexion.getResponseMessage();
	}
	
	@PostMapping("/Consul/Spring/Path/registrar")
	public String registrarPath(@RequestBody Consul service) throws IOException {
		URL consulPath = new URL("https://127.0.0.1:8531/v1/agent/service/register?replace-existing-checks=true");
		HttpURLConnection conexion = (HttpURLConnection) consulPath.openConnection();
		conexion.setDoInput(true);
		conexion.setDoOutput(true);
		conexion.setRequestMethod("PUT");
		conexion.setRequestProperty("X-Consul-Token", service.getToken());
		conexion.setRequestProperty("Content-Type", "application/json");
		conexion.setRequestProperty("Accept", "application/json");

		File archivoJSON = new File(this.getClass().getResource("../data/spring.json").getFile());
		FileInputStream fis = new FileInputStream(archivoJSON);		
		
		int character = 0;
		String sjson = "";
		while((character = fis.read()) != -1) {
			sjson += ((char) character);
		}
		
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		Gson gson = builder.create();		
		Configuracion servicio = gson.fromJson(sjson, Configuracion.class);
		List<String> tags = servicio.getTags();
		for(String tag : tags) {
			if(tag.equals(service.getPath())) {
				return "path registrado anteriormente";
			}
		}
		tags.add(service.getPath());
		
		String json = gson.toJson(servicio);
		OutputStream os = conexion.getOutputStream();
		byte[] data = json.toString().getBytes("UTF-8");
		os.write(data,0,data.length);
		
		fis.close();
		
		FileWriter w = new FileWriter(archivoJSON);
		w.write(json);
		w.close();
		
		return conexion.getResponseMessage();
	}
	
	@PostMapping("/Consul/Spring/Path/desregistrar")
	public String desregistrarPath(@RequestBody Consul service) throws IOException {
		URL consulPath = new URL("https://127.0.0.1:8531/v1/agent/service/register?replace-existing-checks=true");
		HttpURLConnection conexion = (HttpURLConnection) consulPath.openConnection();
		conexion.setDoInput(true);
		conexion.setDoOutput(true);
		conexion.setRequestMethod("PUT");
		conexion.setRequestProperty("X-Consul-Token", "ccab7857-4425-f52b-1411-fd89b9e19649");
		conexion.setRequestProperty("Content-Type", "application/json");
		conexion.setRequestProperty("Accept", "application/json");

		File archivoJSON = new File(this.getClass().getResource("../data/spring.json").getFile());
		FileInputStream fis = new FileInputStream(archivoJSON);		
		
		int character = 0;
		String sjson = "";
		while((character = fis.read()) != -1) {
			sjson += ((char) character);
		}
		
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		Gson gson = builder.create();		
		Configuracion servicio = gson.fromJson(sjson, Configuracion.class);
		List<String> tags = servicio.getTags();
		tags.remove(service.getPath());
		
		String json = gson.toJson(servicio);
		OutputStream os = conexion.getOutputStream();
		byte[] data = json.toString().getBytes("UTF-8");
		os.write(data,0,data.length);
		
		fis.close();
		
		FileWriter w = new FileWriter(archivoJSON);
		w.write(json);
		w.close();
		
		return conexion.getResponseMessage();
	}
	
	@PutMapping("/Consul/Spring/token/main")
	public String crearMainToken() throws IOException {
		URL consulPath = new URL("https://127.0.0.1:8531/v1/acl/bootstrap");
		HttpURLConnection conexion = (HttpURLConnection) consulPath.openConnection();
		conexion.setDoInput(true);
		conexion.setDoOutput(true);
		conexion.setRequestMethod("PUT");
		conexion.setRequestProperty("Accept", "application/json");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
		String linea;
		StringBuffer buffer = new StringBuffer();
		
		while((linea = br.readLine()) != null){
			buffer.append(linea);
		}
		br.close();
		return buffer.toString();
	}
	
	@PutMapping("/Consul/Spring/token")
	public String crearToken(@RequestBody Consul service) throws IOException {
		URL consulPath = new URL("https://127.0.0.1:8531/v1/acl/token");
		HttpURLConnection conexion = (HttpURLConnection) consulPath.openConnection();
		conexion.setDoInput(true);
		conexion.setDoOutput(true);
		conexion.setRequestMethod("PUT");
		conexion.setRequestProperty("X-Consul-Token", service.getToken());
		conexion.setRequestProperty("Accept", "application/json");
		
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		Gson g = builder.create();
		JsonObject pre_json = new JsonObject();
		pre_json.addProperty("Description", service.getDescription());
		JsonArray js_array = new JsonArray();
		List<Policies> list_police = service.getPolicies();
		for(Policies p : list_police) {
			JsonObject json_policies = new JsonObject();
			json_policies.addProperty("ID", p.getId());
			json_policies.addProperty("Name", p.getName());
			js_array.add(json_policies);
		}
		pre_json.addProperty("Local", service.getLocal());
		pre_json.addProperty("ExpirationTTL", service.getexpirationTTL());
		System.out.println(service.toString());
		pre_json.add("Policies", js_array);
		String json = g.toJson(pre_json);
		OutputStream os = conexion.getOutputStream();
		byte[] data = json.toString().getBytes("UTF-8");
		os.write(data,0,data.length);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
		String linea;
		StringBuffer buffer = new StringBuffer();
		
		while((linea = br.readLine()) != null){
			buffer.append(linea);
		}
		br.close();
		return buffer.toString();
	}
	
	@GetMapping("/Consul/Spring/token/consulta")
	public String consultarToken(@RequestBody Consul service) throws IOException {
		URL consulPath = new URL("https://127.0.0.1:8531/v1/acl/token/"+service.getSearch());
		HttpURLConnection conexion = (HttpURLConnection) consulPath.openConnection();
		conexion.setRequestMethod("GET");
		conexion.setRequestProperty("X-Consul-Token", service.getToken());
		conexion.setRequestProperty("Accept", "application/json");
		BufferedReader br = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
		String linea;
		StringBuffer buffer = new StringBuffer();
		
		while((linea = br.readLine()) != null){
			buffer.append(linea);
		}
		br.close();
		return buffer.toString();
	}
	
	@DeleteMapping("/Consul/Spring/token/eliminar")
	public String eliminarToken(@RequestBody Consul service) throws IOException {
		URL consulPath = new URL("https://127.0.0.1:8531/v1/acl/token/"+service.getSearch());
		HttpURLConnection conexion = (HttpURLConnection) consulPath.openConnection();
		conexion.setRequestMethod("DELETE");
		conexion.setRequestProperty("X-Consul-Token", service.getToken());
		conexion.setRequestProperty("Accept", "application/json");
		return conexion.getResponseMessage() + " " + conexion.getResponseCode();
	}
	
	@GetMapping("/Consul/Spring/tokens/consulta")
	public String consultarTodosToken(@RequestBody Consul service) throws IOException {
		URL consulPath = new URL("https://127.0.0.1:8531/v1/acl/tokens");
		HttpURLConnection conexion = (HttpURLConnection) consulPath.openConnection();
		conexion.setRequestMethod("GET");
		conexion.setRequestProperty("X-Consul-Token", service.getToken());
		conexion.setRequestProperty("Accept", "application/json");
		BufferedReader br = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
		String linea;
		StringBuffer buffer = new StringBuffer();
		
		while((linea = br.readLine()) != null){
			buffer.append(linea);
		}
		br.close();
		return buffer.toString();
	}
}
