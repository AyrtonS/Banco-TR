package br.com.bancotr.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

import Entities.LoginEntity;
import Entities.Transferencia;
import br.com.bancotr.persistence.DatabasePersistence;

public class CriaContaController implements CriaConta {

	EntityManagerFactory emf;

	public CriaContaController() {
		emf = DatabasePersistence.getEntityManagerFactory();
	}

	public LoginEntity getByConta(String conta) {

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		String sql = "SELECT o FROM LoginEntity o where o.conta = '" + conta + "'";

		LoginEntity login = null;
		try {
			login = em.createQuery(sql, LoginEntity.class).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		em.getTransaction().commit();
		em.close();

		return login;

	}

	public int create(Socket cliente, LoginEntity login) {

		int gambi = 0;
		String senha = login.getSenha();
		String regex = "[0-9]{6}";
		String regexconta = "^[0-9]{4}-[0-9]$";

		PrintWriter message = null;

		if (senha.length() < 4 && senha.length() > 12) {
			return -1;
		}
		System.out.println("Entrou nessa bagaça");
		if (!login.getSenha().matches(regex)) {
			return -2;
		}

		if (login.getConta().length() != 6) {
			return -3;
		}

		System.out.println("chegou ate aqui ");
		EntityManager em = emf.createEntityManager();
		
		
		em.getTransaction().begin();
		em.persist(login);
		em.flush();
		em.getTransaction().commit();

		em.close();

		return 1;

	}

	public List<LoginEntity> read() {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(LoginEntity login) {
		// TODO Auto-generated method stub

	}

	public void delete(LoginEntity login) {
		// TODO Auto-generated method stub

	}

	public int saque(Socket cliente, LoginEntity login, double valor) {
		
			if (valor > login.getSaldo()) {
				return -1;
			} else {
				double newSaldo = login.getSaldo() - valor;
				login.setSaldo(newSaldo);

				EntityManager em = emf.createEntityManager();
				em.getTransaction().begin();
				em.merge(login);
				em.getTransaction().commit();
				em.close();
				return 1;
			}
		

	}

	public int deposito(Socket cliente, LoginEntity login, double valor) {
		if (getByConta(login.getConta()) != null) {

			double newSaldo = login.getSaldo() + valor;
			login.setSaldo(newSaldo);

			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			em.merge(login);
			em.getTransaction().commit();
			em.close();
			return 1;
		}
		return -1;

	}

	public int transferencia(Socket cliente, Transferencia trans) {
		LoginEntity userA = getByConta(trans.getContaA());
		LoginEntity userB = getByConta(trans.getContaB());
		
		if (userA != null) {
				
			if (userB != null) {
				//LoginEntity userB = 
				if (trans.getValor() > userA.getSaldo()) {
					return -1;
				}
				
				userA.setSaldo(userA.getSaldo() - trans.getValor());
				userB.setSaldo(userB.getSaldo() + trans.getValor());

				EntityManager em = emf.createEntityManager();
				em.getTransaction().begin();
				em.merge(userA);
				em.getTransaction().commit();
				em.close();

				System.out.println("Usuario A atualizado");

				EntityManager em1 = emf.createEntityManager();
				em1.getTransaction().begin();
				em1.merge(userB);
				em1.getTransaction().commit();
				em1.close();
				System.out.println("Usuario B atualizado");
				
			}
		}
		return 1;

	}

}
