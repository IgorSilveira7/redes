package com.redes.lab1;

import java.io.*; 
import java.net.*; 

class TCPServer { 
    
    public static void main(String argv[]) throws Exception 
    { 
	String clientSentence; 
	String capitalizedSentence; 
	
	ServerSocket welcomeSocket = new ServerSocket(6789); 
	
	while(true) { 
            Socket connectionSocket = welcomeSocket.accept(); 
	    
	    BufferedReader inFromClient = 
		new BufferedReader(new
		    InputStreamReader(connectionSocket.getInputStream())); 
	    
	    DataOutputStream  outToClient = 
		new DataOutputStream(connectionSocket.getOutputStream()); 
	    
	    clientSentence = inFromClient.readLine(); 
	    
	    String[] entrada = clientSentence.split(" ");
	    
	    float x = Float.valueOf(entrada[1]);
	    float y = Float.valueOf(entrada[2]);
	    
	    float resultado = calculadora(entrada[0],x,y); 
	    
	    outToClient.writeBytes("" + resultado + '\n'); 
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

