package br.com.bancotr.services;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.h2.engine.Database;

import Entities.LoginEntity;
import br.com.bancotr.controller.CriaContaController;
import br.com.bancotr.persistence.DatabasePersistence;
import br.com.bancotr.persistence.ServersManagers;

public class CriaContaService {
	
	private static LoginEntity login;
	

	
	public static void main(String[] args) throws IOException {
		DatabasePersistence.init();
		ServerSocket servidor = new ServerSocket(12349);
			
			while(true){
				 
				System.out.println("Servidor do cadastro FUNCIONANDO!");
				Socket cliente = servidor.accept();
				
				ObjectInputStream obj = new ObjectInputStream(cliente.getInputStream());
				Object aux;
				
				try {
				 aux = obj.readObject();
				
				if(aux instanceof LoginEntity){
					login = (LoginEntity) aux;
				
				CriaContaController criaconta = new CriaContaController();
				int result = criaconta.create(cliente,login);
				
				if(result == 1){
					PrintWriter message = new PrintWriter(cliente.getOutputStream());
					message.println("Salvo com sucesso!");
					message.close();
				}
				if(result == -1){
					PrintWriter message = new PrintWriter(cliente.getOutputStream());
					message.println("Tamanho de caracteres da senha errado");
					message.close();
				}
				if(result == -2){
					PrintWriter message = new PrintWriter(cliente.getOutputStream());
					message.println("Formato de senha errado");
					message.close();
				}
				if(result == -3){
					PrintWriter message = new PrintWriter(cliente.getOutputStream());
					message.println("Tamanho de caracteres de conta deve ser de 6!");
					message.close();
				}
				
				
				
				//System.out.println(cliente.getOutputStream());
				}
				
			
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		if(false) servidor.close();	 
			
	}
	}
	
}
