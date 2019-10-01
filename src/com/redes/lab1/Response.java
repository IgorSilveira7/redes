package com.redes.lab1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class Response {
		
	private String recurso;
	
	private String statusLine;
	private String contentTypeLine;
	private String contentLenght;
	private String CRLF="\r\n";
	private byte[] entityBody;
	
	public Response(String recurso) {
		this.recurso = recurso;
		gerarReponse();
	}
	

	private void gerarReponse() {
		File file = new File(recurso);
		if(!file.exists()) {
			responseNotFound();
		}else {
			responseOk(file);
		}
	}

	private void responseOk(File file) {
		
		this.statusLine = "HTTP/1.0 200 Ok" + CRLF;
		this.contentTypeLine = String.format("Content-Type: %s %s",getContentType(file.getName()), CRLF);
		try {
			this.entityBody = Files.readAllBytes(file.toPath());
		} catch (IOException e) {
		}
		this.contentLenght = String.format("Content-Lenght: %d %s", this.entityBody.length, CRLF);
	}

	private String getContentType(String file) {
		String retorno = "text/plain";
		if(file.contains(".jpg")) {
			retorno = "image/jpeg";
		}else if(file.contains(".mp3")) {
			retorno = "audio/mpeg";
		}else if(file.contains(".mp4")) {
			retorno =  "video/mp4";
		}
		return retorno;
	}

	private void responseNotFound() {
		
		statusLine = "HTTP/1.0 404 Not Found" + CRLF;
		contentTypeLine = "Content-Type: text/html; charset=utf-8"  + CRLF;
		entityBody = new String("<HTML>" + 
				"<HEAD><TITLE> Não encontrado.</TITLE></HEAD>" +
				"<BODY> Arquivo " + this.recurso + " não encontrado.</BODY></HTML>").getBytes(Charset.forName("utf-8"));
	}


	public String getStatusLine() {
		return statusLine;
	}


	public void setStatusLine(String statusLine) {
		this.statusLine = statusLine;
	}


	public String getContentTypeLine() {
		return contentTypeLine;
	}


	public void setContentTypeLine(String contentTypeLine) {
		this.contentTypeLine = contentTypeLine;
	}


	public String getContentLenght() {
		return contentLenght;
	}


	public void setContentLenght(String contentLenght) {
		this.contentLenght = contentLenght;
	}


	public String getCRLF() {
		return CRLF;
	}


	public void setCRLF(String cRLF) {
		CRLF = cRLF;
	}


	public byte[] getEntityBody() {
		return entityBody;
	}


	public void setEntityBody(byte[] entityBody) {
		this.entityBody = entityBody;
	}
	
	
	

}
