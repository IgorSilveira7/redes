package com.redes.lab1.udp;

import java.io.IOException;
import java.net.*;
import java.time.Duration; 

class UDPServer { 
    public static void main(String args[]) throws Exception 
    { 
	
	DatagramSocket serverSocket = new DatagramSocket(9876); 
	
	byte[] receiveData = new byte[1024]; 
	byte[] sendData  = new byte[1024]; 
	
	while(true) 
	    { 
		
		DatagramPacket receivePacket = 
		    new DatagramPacket(receiveData, receiveData.length); 
		serverSocket.receive(receivePacket); 
		String sentence = new String(receivePacket.getData()); 
		
		InetAddress IPAddress = receivePacket.getAddress(); 
		
		int port = receivePacket.getPort(); 
	
		
		//String capitalizedSentence = sentence.toUpperCase(); 
		
		String[] entrada = sentence.split(" ");
	    
		float x = Float.valueOf(entrada[1]);
		float y = Float.valueOf(entrada[2]);
		    
		float resultado = calculadora(entrada[0],x,y); 
		System.out.println("X: " + x);
		System.out.println("y: " + y);
		//sendData = capitalizedSentence.getBytes();
		
		sendData = String.valueOf(resultado).getBytes();
		
		envia(serverSocket, receiveData, sendData, IPAddress, port); 
		
		
	    } 
    }

	private static void envia(DatagramSocket serverSocket, byte[] receiveData, byte[] sendData, InetAddress IPAddress,
			int port) throws IOException {
		try {
			DatagramPacket receivePacket;
			DatagramPacket sendPacket = 
			    new DatagramPacket(sendData, sendData.length, IPAddress, 
					       port);
			
			DatagramSocket serverSocketConfirm = new DatagramSocket(9876); 
			
			serverSocketConfirm.setSoTimeout((int) Duration.ofSeconds(2).toMillis());
			
			serverSocketConfirm.send(sendPacket); 
		
			receivePacket = new DatagramPacket(receiveData, receiveData.length,IPAddress,port);
			
			serverSocketConfirm.receive(receivePacket);
			
			//String modifiedSentence = new String(receivePacket.getData());
			
		}catch (SocketTimeoutException e) {
			envia(serverSocket, receiveData, sendData, IPAddress, port);
		}
	}
    
    private static float calculadora(String operacao, float x, float y) {
		float resultado = 0;
		switch (operacao) {
		case "ADD":
			resultado = x + y;
			break;
		case "SUB":
			resultado  = x-y;
			break;
		case "MULT":
			resultado = x*y;
			break;
		case "DIV":
			if(y > 0) {
				resultado = x/y;
			}else {
				resultado = Float.NaN;
			}
			break;
		case "EXP":
			resultado = (float) Math.pow(x, y);
			break;
		}
		return resultado;
	} 
}  
