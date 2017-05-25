/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.model.jogo;

import java.util.ArrayList;
import java.util.List;
import pbl.model.banco.Conta;

/**
 *
 * @author marcos
 */
public class Jogador {
    private int identificacao;
    private int mes;
    private Peao peao;
    private final String nome;
    private List<Carta> cartasCorreios;
    private List<Carta> cartasCompEntret;
    private Conta conta;

    public Jogador(int identificacao, String nome, Peao peao) {
        this.identificacao = identificacao;
        this.mes = 1;
        this.nome = nome;
        this.peao = peao;
        this.cartasCorreios = new ArrayList();
        this.cartasCompEntret = new ArrayList();
        conta = new Conta(3000.0);
    }

    public int getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(int identificacao) {
        this.identificacao = identificacao;
    }

    public List<Carta> getCartasCorreios() {
        return cartasCorreios;
    }

    public List<Carta> getCartasCompEntret() {
        return cartasCompEntret;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }
    
    public void addCartaCorreio(Carta carta){
        cartasCorreios.add(carta);
    }
    
    public void addCartaCompEntret(Carta carta){
        cartasCompEntret.add(carta);
    }
    
    public void removerCartaCompEntret(int codigoCarta){
        for(Carta c: cartasCompEntret){
            if(c.getCodigo()==codigoCarta){
                cartasCompEntret.remove(c);
            }
        }
    }

    public Peao getPeao() {
        return peao;
    }

    public void setPeao(Peao peao) {
        this.peao = peao;
    }
    
    public int getMes(){
        return mes;
    }
    /**
     * Adicona 1 ao valor do mes do jogador
     * @param valorDado 
     */
    public void novoMes(int valorDado){
        mes++;
    }
    
    /**
     * Soma o valor total de todas as contas atrasadas do jogador
     * @return a soma do valor total de todas as contas
     */
    private double calcularValorTotalContas(){
        double valorTotal = 0;
        for(Carta c: cartasCorreios){
            if(c.getNome().equals("CONTAS")){
                valorTotal += c.getValor();
            }
        }
        return valorTotal;
    }
    
    /**
     * calcula o total da cobrança monstro mais os juros do jogador
     * @return a soma de todas as cobranças monstros e seu juros
     */
    private double calcularValorTotalCobrancaMonstro(){
        double valorTotal = 0;
        for(Carta c: cartasCorreios){
            if(c.getNome().equals("COBRANÇA MONSTRO")){
                valorTotal += (c.getValor()*1.1);
            }
        }
        return valorTotal;
    }
    
    /**
     * Calcula o total dos juros da cobrança monstro
     * @return a soma dos juros da cobrança monstro
     */
    private double calcularValorJurosCobrancaMonstro(){
        double valorTotal = 0;
        for(Carta c: cartasCorreios){
            if(c.getNome().equals("COBRANÇA MONSTRO")){
                valorTotal += (c.getValor()*0.1);
            }
        }
        return valorTotal;
    }
    
    /**
     * Se o jogador tiver saldo suficiente paga todas as contas
     * @return verdadeiro ou falso
     */
    public boolean pagarTodasContas(){
        return this.getConta().sacar(calcularValorTotalContas());
    }
    
    /**
     * Se o jogador tiver saldo suficiente paga todas as cobranças monstros mais o juros
     * @return verdadeiro ou falso
     */
    public boolean pagarTodasCobrancasMonstros(){
        return this.getConta().sacar(calcularValorTotalCobrancaMonstro());
    }
    
    /**
     * Se o jogador tiver saldo suficiente paga todos os juros da cobrança monstro
     * @return verdadeiro ou falso
     */
    public boolean pagarJurosCobrancaMonstro(){
        return this.getConta().sacar(calcularValorJurosCobrancaMonstro());
    }
    
    /**
     * paga todas as dividas do jogador, se não for possivel, paga o juro
     * das mesmas
     */
    public void pagarDividasFimRodada(){
        if(!pagarTodasContas()){
            conta.realizarEmprestimo(calcularValorTotalContas());
            pagarTodasContas();
        }
        if(!pagarTodasCobrancasMonstros()){
            if(!pagarJurosCobrancaMonstro()){
                conta.realizarEmprestimo(calcularValorJurosCobrancaMonstro());
                pagarJurosCobrancaMonstro();
            }
        }
        if(!conta.pagarEmprestimo()){
            conta.pagarJurosEmprestimo();
        }
    }
    
    /**
     * Pagar todas as dividas, se possivel os emprestimos
     * metodo para ser usado no fim da partida
     */
    public void pagarDividasFimJogo(){
        if(!pagarTodasContas()){
            conta.realizarEmprestimo(calcularValorTotalContas());
            pagarTodasContas();
        }
        if(!pagarTodasCobrancasMonstros()){
            conta.realizarEmprestimo(calcularValorTotalCobrancaMonstro());
            pagarTodasCobrancasMonstros();
        }
        conta.pagarEmprestimo();
    }
    
    /**
     * Calcula o saldo final do jogador, e se ele ainda possuir emprestimo, esse valor fica
     * negativado
     * @return 
     */
    public double getSaldoFinal(){
        return (conta.consultarSaldo()-conta.getEmprestimo().getValorTotal());
    }
}
