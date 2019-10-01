package com.redes.lab1.udp;

import java.io.*; 
import java.net.*;
import java.time.Duration;
import java.util.Date; 

class UDPClient { 
    public static void main(String args[]) throws Exception 
    { 
	
	BufferedReader inFromUser = 
	    new BufferedReader(new InputStreamReader(System.in)); 
	
	DatagramSocket clientSocket = new DatagramSocket(); 
	clientSocket.setSoTimeout((int)Duration.ofSeconds(2).toMillis());
	
	//InetAddress IPAddress = InetAddress.getByName("150.165.54.115");
	InetAddress IPAddress = InetAddress.getByName("localhost");
	
	byte[] sendData = new byte[1024]; 
	byte[] receiveData = new byte[1024];
	
	String sentence = inFromUser.readLine(); 
	
	enviar(inFromUser, clientSocket, IPAddress, receiveData,sentence); 
    }

	private static void enviar(BufferedReader inFromUser, DatagramSocket clientSocket, InetAddress IPAddress,
			byte[] receiveData,String sentence) throws IOException {
		try {
			byte[] sendData;
			sendData = sentence.getBytes();         
			DatagramPacket sendPacket = 
			    new DatagramPacket(sendData, sendData.length, IPAddress, 9876); 
			
			clientSocket.send(sendPacket); 
						
			DatagramPacket receivePacket = 
			    new DatagramPacket(receiveData, receiveData.length); 
			
			clientSocket.receive(receivePacket);
			String modifiedSentence = 
			    new String(receivePacket.getData()); 
			System.out.println("FROM SERVER:" + modifiedSentence); 
			
			sendData = new String("ACK").getBytes();
			
			sendPacket = 
				    new DatagramPacket(sendData, sendData.length, IPAddress, 9876); 

			clientSocket.send(sendPacket);
			clientSocket.close();
		}catch (SocketTimeoutException e) {
			e.printStackTrace();
			enviar(inFromUser, clientSocket, IPAddress, receiveData,sentence);
		}
	}

	
} 
