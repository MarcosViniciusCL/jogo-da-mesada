/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.model.banco;

/**
 *
 * @author emerson
 */
public class Emprestimo {
    private double valor;
    private double valorJuros;
    
    /**
     * Cria um emprestimo
     * @param valor 
     */
    public Emprestimo(double valor){
        this.valor = valor;
        this.valorJuros = valor * 0.1;
    }
    
    /**
     * devolve o valor dos juros
     * @return 
     */
    public double getValorJuros(){
        return valorJuros;
    }
    
    /**
     * devolve a soma do valor do emprestimo e dos juros somados
     * @return 
     */
    public double getValorTotal(){
        return valor+valorJuros;
    }
    
    /**
     * aumenta o valor do emprestimo
     * @param valor 
     */
    public void addValor(double valor){
        this.valor += valor;
        this.valorJuros += valor * 0.1;
    }
    
    /**
     * zera os valores do emprestimo
     */
    public void pagarEmprestimo(){
        this.valor = 0;
        this.valorJuros = 0;
    }
    
    /**
     * zera o valor dos juros
     */
    public void pagarJuros(){
        this.valorJuros = 0;
    }
}
