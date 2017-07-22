package Entities;

/**
 * Created by matrix on 02/07/17.
 */

import java.io.Serializable;

public class Saque implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4178357788871702130L;

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
