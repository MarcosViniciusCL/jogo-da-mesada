/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.model.jogo;

import java.util.ArrayList;
import java.util.List;
import pbl.model.banco.Conta;
import pbl.model.banco.Emprestimo;

/**
 *
 * @author marcos
 */
public class Jogador {
    private int identificacao;
    private int mes;
    private Peao peao;
    private String nome;
    private List<Carta> contas;
    private Emprestimo cobrancaMonstro;
    private List<Carta> cartasCompEntret;
    private Conta conta;
    

    public Jogador(int identificacao, String nome, Peao peao) {
        this.identificacao = identificacao;
        this.mes = peao.getQuantMes();
        this.nome = nome;
        this.peao = peao;
        this.contas = new ArrayList();
        this.cartasCompEntret = new ArrayList();
        this.cobrancaMonstro = new Emprestimo(0);
        conta = new Conta(3000.0);
    }

    public int getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(int identificacao) {
        this.identificacao = identificacao;
    }

    public List<Carta> getCartasCorreios() {
        return contas;
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
        if(carta.getNome().equals("CONTAS")){ //caso seja uma conta
            contas.add(carta);
        }else if(carta.getNome().equals("COBRANÇA MONSTRO")){ //caso seja uma cobrança monstro
            cobrancaMonstro.addValor(carta.getValor());
        }   
    }
    
    public void addCartaCompEntret(Carta carta){
        cartasCompEntret.add(carta);
    }
    
    public void removerCartaCompEntret(int codigoCarta){
        Carta cRem = null;
        for(Carta c: cartasCompEntret){
            if(c.getCodigo()==codigoCarta){
                cRem = c;
                break;
            }
        }
        cartasCompEntret.remove(cRem);
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
        for(Carta c: contas){
            valorTotal += c.getValor();
        }
        return valorTotal;
    }
    
    /**
     * calcula o total da cobrança monstro mais os juros do jogador
     * @return a soma de todas as cobranças monstros e seu juros
     */
    private double calcularValorTotalCobrancaMonstro(){
        return cobrancaMonstro.getValorTotal();
    }
    
    /**
     * Calcula o total dos juros da cobrança monstro
     * @return a soma dos juros da cobrança monstro
     */
    private double calcularValorJurosCobrancaMonstro(){
        return cobrancaMonstro.getValorJuros();
    }
    
    /**
     * Se o jogador tiver saldo suficiente paga todas as contas
     * @return verdadeiro ou falso
     */
    public boolean pagarTodasContas(){
        if(this.getConta().sacar(calcularValorTotalContas())){
           contas = new ArrayList<>();
           return true;
        }
        return false;
    }
    
    /**
     * Se o jogador tiver saldo suficiente paga todas as cobranças monstros mais o juros
     * @return verdadeiro ou falso
     */
    public boolean pagarTodasCobrancasMonstros(){
        if(this.getConta().sacar(calcularValorTotalCobrancaMonstro())){ //verifica se o jogador tem saldo suficiente para pagar toda cobrança monstro
            cobrancaMonstro.pagarEmprestimo();
            return true;
        }
        return false;
    }
    
    /**
     * Se o jogador tiver saldo suficiente paga todos os juros da cobrança monstro
     * @return verdadeiro ou falso
     */
    public boolean pagarJurosCobrancaMonstro(){
        if(this.getConta().sacar(calcularValorJurosCobrancaMonstro())){
            cobrancaMonstro.pagarJuros();
            return true;
        }
        return false;
    }
    
    /**
     * paga todas as dividas do jogador, se não for possivel, paga o juro
     * paga todas as dividas do jogador, se não for possivel, paga o juro
     * das mesmas
     */
    public String pagarDividasFimRodada(){
        String acoes = "";
        
        if(!pagarTodasContas()){
            conta.realizarEmprestimo(calcularValorTotalContas());
            pagarTodasContas();
            acoes += this.nome+": realizou um emprestimo para pagar as contas\n";
        }
        acoes += this.nome+": pagou todas as contas\n";
        if(!pagarTodasCobrancasMonstros()){
            if(!pagarJurosCobrancaMonstro()){
                conta.realizarEmprestimo(calcularValorJurosCobrancaMonstro());
                pagarJurosCobrancaMonstro();
                acoes += this.nome+": realizou um emprestimo para pagar a(s) cobranca(s) monstro\n";
            }
            acoes += this.nome+": pagou os juros da(s) cobranca(s) monstro\n";
        }else{
            acoes += this.nome+": pagou todas a(s) cobranca(s) monstro\n";
        }
        if(!conta.pagarEmprestimo()){
            conta.pagarJurosEmprestimo();
            acoes += this.nome+": pagou os juros do emprestimo\n";
        }else{
            acoes += this.nome+": pagou o emprestimo\n";
        }
        return acoes;
    }
    
    /**
     * Pagar todas as dividas, se possivel os emprestimos
     * metodo para ser usado no fim da partida
     */
    public String pagarDividasFimJogo(){
        String acoes = "";
        
        if(!pagarTodasContas()){
            conta.realizarEmprestimo(calcularValorTotalContas());
            pagarTodasContas();
        }
        acoes += this.nome+": pagou todas as contas\n";
        if(!pagarTodasCobrancasMonstros()){
            conta.realizarEmprestimo(calcularValorTotalCobrancaMonstro());
            pagarTodasCobrancasMonstros();
        }
        acoes += this.nome+": pagou a(s) cobrança(s) monstro\n";
        conta.pagarEmprestimo();
        acoes += this.nome+": pagou o emprestimo\n";
        return acoes;
    }
    
    /**
     * Calcula o saldo final do jogador, e se ele ainda possuir emprestimo, esse valor fica
     * negativado
     * @return 
     */
    public double getSaldoFinal(){
        return (conta.consultarSaldo()-conta.getEmprestimo().getValorTotal());
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
