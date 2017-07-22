package Entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class LoginEntity implements Serializable {
	
	 	/**
	 * 
	 */
		private static final long serialVersionUID = -1407926997459366675L;
		@Id
		@GeneratedValue
		private long id;
		private int agencia;
	    private String conta;
	    private String senha;
	    private double saldo;


	    public double getSaldo() {
			return saldo;
		}

		public void setSaldo(double saldo) {
			this.saldo = saldo;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public int getAgencia() {
	        return agencia;
	    }

	    public void setAgencia(int agencia) {
	        this.agencia = agencia;
	    }

	    public String getConta() {
	        return conta;
	    }

	    public void setConta(String conta) {
	        this.conta = conta;
	    }

	    public String getSenha() {
	        return senha;
	    }

	    public void setSenha(String senha) {
	        this.senha = senha;
	    }

}
