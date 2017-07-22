package br.com.bancotr.persistence;

import java.io.IOException;
import java.net.ServerSocket;

public class ServersManagers {
	
	private static ServerSocket servidor; 
	private static ServerSocket servidor2;
	
	public ServersManagers() throws IOException{
		servidor = new ServerSocket(12346);
		servidor2 = new ServerSocket(12349);
	}

	public static ServerSocket getServidor() {
		return servidor;
	}

	public static void setServidor(ServerSocket servidor) {
		ServersManagers.servidor = servidor;
	}

	public static ServerSocket getServidor2() {
		return servidor2;
	}

	public static void setServidor2(ServerSocket servidor2) {
		ServersManagers.servidor2 = servidor2;
	}
	
	
	
	
}
