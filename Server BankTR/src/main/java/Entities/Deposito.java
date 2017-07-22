package Entities;

import java.io.Serializable;

public class Deposito implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4265586851582621862L;
	
	private String conta;
	private double valor;
	
	public String getConta() {
		return conta;
	}
	public void setConta(String conta) {
		this.conta = conta;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
}
