package redes.bancoTRServer;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import Entities.Deposito;
import Entities.LoginEntity;
import Entities.Saque;
import Entities.Transferencia;
import br.com.bancotr.controller.CriaContaController;
import br.com.bancotr.persistence.DatabasePersistence;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws IOException {
		DatabasePersistence.init();
		ServerSocket servidor = new ServerSocket(12346);

		System.out.println("Porta 12345 aberta!");
		while (true) {
			Socket cliente = servidor.accept();
			System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());

			try {
				ObjectInputStream input = new ObjectInputStream(cliente.getInputStream());
				Object aux;
				aux = input.readObject();
				if (aux instanceof LoginEntity) {

					LoginEntity login = (LoginEntity) aux;

					CriaContaController controller = new CriaContaController();
					System.out.println(login.getConta());
					LoginEntity tmp = controller.getByConta(login.getConta());
					
					if (tmp != null) {
						
							System.out.println(tmp.getAgencia());
							System.out.println(tmp.getSaldo());
							ObjectOutputStream output = new ObjectOutputStream(cliente.getOutputStream());
							output.writeObject(tmp);
							output.flush();
							output.reset();
						
						
					}

				} else if (aux instanceof Transferencia) {
					Transferencia trans = (Transferencia) aux;
					CriaContaController controller = new CriaContaController();
					LoginEntity log = controller.getByConta(trans.getContaB());

					if (log != null) {
						System.out.println("ui");
						int result = controller.transferencia(cliente, trans);

						if (result == -1) {
							PrintWriter message = new PrintWriter(cliente.getOutputStream());
							message.println("Saldo Insuficiente!");
							message.close();
						} else {
							PrintWriter message = new PrintWriter(cliente.getOutputStream());
							message.println("Transferencia bem sucedida!");
							message.close();
						}
					}
				} else if (aux instanceof Saque) {
					Saque sq = (Saque) aux;
					CriaContaController controller = new CriaContaController();
					LoginEntity log = controller.getByConta(sq.getConta());
					if (log != null) {
						int result = controller.saque(cliente, log, sq.getValor());
						if (result == -1) {
							PrintWriter message = new PrintWriter(cliente.getOutputStream());
							message.println("Valor Indisponivel para Saque!");
							message.close();
						} else {
							PrintWriter message = new PrintWriter(cliente.getOutputStream());
							message.println("Saque efetivado!");
							message.close();
						}
					}
				}else if(aux instanceof Deposito){
					Deposito dep = (Deposito) aux;
					CriaContaController controller = new CriaContaController();
					LoginEntity log = controller.getByConta(dep.getConta());
					if (log != null) {
						int result = controller.deposito(cliente, log, dep.getValor());
						if (result == -1) {
							PrintWriter message = new PrintWriter(cliente.getOutputStream());
							message.println("Conta não encontrada!");
							message.close();
						} else {
							PrintWriter message = new PrintWriter(cliente.getOutputStream());
							message.println("Deposito efetivado!");
							message.close();
						}
					}
				}

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EOFException e) {
				System.out.println("");
			}

			// input.close();
			// }

			if (false) {
				servidor.close();
				cliente.close();
			}
		}

	}

}
