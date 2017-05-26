/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.model.banco;


import java.io.Serializable;
import java.util.ArrayList;

/**
 *Classe que guarda os dados de uma instancia de conta 
 * @author emerson
 */
public class Conta implements Serializable{
    private double saldo;
    private Emprestimo emprestimo;
 
    /**
     * Cria uma conta com saldo inicial
     * @param saldo 
     */
    public Conta(double saldo){
        this.saldo = saldo;
        this.emprestimo = new Emprestimo(0);
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }
    
    /**
     * devolve o valor do saldo
     * @return 
     */
    public double consultarSaldo(){
        return saldo;
    }
    
    /**
     * Adiciona um valor ao saldo
     * @param valor 
     */
    public void depositar(double valor){
        saldo += valor;
    }
    
    /**
     * Realiza um saque, diminuindo valor relativo ao saldo
     * @param valor
     * @return 
     */
    public boolean sacar(double valor){
        if(saldo>=valor){
            saldo -= valor;
            return true;
        }
        return false;
    }
    
    
    /**
     * Transfere o valor de uma conta para outra
     * @param conta
     * @param valor
     * @return 
     */
    public boolean transferir(Conta conta, double valor){
        if(this.sacar(valor)){ //debita o valor do saldo da conta atual
            conta.depositar(valor); //adiciona valor a outra conta
            return true;
        }
        return false;
    }
    
    /**
     * Realiza um emprestimo
     * @param valor 
     */
    public void realizarEmprestimo(double valor){
        emprestimo.addValor(valor);
        depositar(valor);
    }
    
    /**
     * Paga o valor total referente a um emprestimo, se a conta tiver saldo
     * @return 
     */
    public boolean pagarEmprestimo(){
        if(this.sacar(emprestimo.getValorTotal())){ //verifica se a conta tem saldo suficiente para pagar o emprestimo, se sim realiza o saque
            emprestimo.pagarEmprestimo(); //zera o valor do emprestimo
            return true;
        }
        return false;
    }
    
    public boolean pagarJurosEmprestimo(){
        if(this.sacar(emprestimo.getValorJuros())){ //verifica se a conta tem saldo suficiente para pagar os juros do emprestimo, se sim realiza o saque
            emprestimo.pagarJuros(); //zera o valor dos juros
            return true;
        }
        return false;
    }
}
