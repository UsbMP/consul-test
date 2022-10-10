
package com.example.springboot;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.UnsupportedEncodingException;

import javax.mail.internet.MimeMessage;
import javax.mail.MessagingException;

import com.example.entity.Correo;

@RestController
public class SMTPtest {

	@Autowired
	private JavaMailSender sender;
	
	@GetMapping("/email/enviar")
	public String enviarCorreo(@RequestBody Correo envio) throws MessagingException, UnsupportedEncodingException {
		MimeMessage mensaje = sender.createMimeMessage();
		MimeMessageHelper formato = new MimeMessageHelper(mensaje, true);
		
		formato.setFrom("uamp_890@hotmail.com","Pruebas");
		formato.setTo(envio.getEmail());
		formato.setSubject("Pruebas de texto");
		formato.setText(envio.getContenido());		
		sender.send(mensaje);
		return "ok";
	}
	
}
