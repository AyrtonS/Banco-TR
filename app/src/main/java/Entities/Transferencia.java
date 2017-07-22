package Entities;

import java.io.Serializable;

/**
 * Created by matrix on 02/07/17.
 */

public class Transferencia implements Serializable{

    private static final long serialVersionUID = 2378144354288124359L;

    private String contaA;
    private double valor;
    private String contaB;

    public String getContaA() {
        return contaA;
    }
    public void setContaA(String contaA) {
        this.contaA = contaA;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    public String getContaB() {
        return contaB;
    }
    public void setContaB(String contaB) {
        this.contaB = contaB;
    }

}
