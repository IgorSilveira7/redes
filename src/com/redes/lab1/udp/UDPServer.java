package com.redes.lab1.udp;

import java.io.*; 
import java.net.*; 

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
		
		//sendData = capitalizedSentence.getBytes();
		
		sendData = String.valueOf(resultado).getBytes();
		
		DatagramPacket sendPacket = 
		    new DatagramPacket(sendData, sendData.length, IPAddress, 
				       port); 
		
		serverSocket.send(sendPacket); 
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
