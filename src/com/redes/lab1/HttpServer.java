package com.redes.lab1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class HttpServer {
	
	public static void main(String[] args) throws Exception{		
		String requestMessageLine, fileName;
		
		ServerSocket listenSocket = new ServerSocket(8080);
		
		while (true) {
			Socket connectionSocket = listenSocket.accept();
		
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			requestMessageLine = inFromClient.readLine();
		
			StringTokenizer tokenizedLine = new StringTokenizer(requestMessageLine);
		
			if(tokenizedLine.nextToken().equals("GET")){
				fileName = tokenizedLine.nextToken();
				if(fileName.startsWith("/"))
					fileName = fileName.substring(1);
				
				Response response = new Response(fileName);
				
				outToClient.writeBytes(response.getStatusLine());
				outToClient.writeBytes(response.getContentTypeLine());
				outToClient.writeBytes(response.getCRLF());
				outToClient.write(response.getEntityBody());
			}
			connectionSocket.close();
		}		
	}

}
