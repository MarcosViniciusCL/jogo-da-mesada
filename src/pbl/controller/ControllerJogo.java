/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.controller;

import pbl.exception.AguadarVezException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import pbl.model.jogo.Dado;
import pbl.model.jogo.Jogador;

/**
 *
 * @author marcos
 */
public class ControllerJogo {

    private static ControllerJogo controllerJogo; //Instancia da propria classe.
    private final ControllerConexao controllerConexao; //Instancia do controller de conexão.

    //ATRIBUTOS DO JOGO
    private boolean minhaVez; //Variavel para informar se é a vez do cliente jogar o dado;
    private final Dado dado; //Dado do jogo
    private String ultimaMens; //Fica salva a ultima mensagem que foi enviada ao grupo para reenvio caso tenha perda.
    private List <Jogador> jogadores;
    private double sorteGrande;
    
    private ControllerJogo() {
        this.controllerConexao = ControllerConexao.getInstance();
        this.dado = new Dado();
        jogadores = new ArrayList<>();
    }


    //****************************************** METODOS RESPONSAVEIS PELA AÇÃO DO JOGO ************************************

    /**
     * Joga o dado e retorna o valor que o dado permaneceu.
     *
     * @return valor - Valor final do dado
     * @throws AguadarVezException - Ainda não é a vez do jogador.
     */
    public int jogarDado() throws AguadarVezException {
        int valor;
        if (minhaVez) {
            valor = dado.jogarDado(); //Joga o dado e aguarda o valor sorteado.
        } else {
            throw new AguadarVezException(); //Caso não seja a vez  do jogador lança a exception
        }
        return valor;
    }

    public void achouUmComprador(){
        
    }
    /**
     * O jogador que caiu na casa recebe 5000 do banco;
     * @param jogador jogador que caiu na casa
     */
    public void premio(Jogador jogador){
        jogador.getConta().depositar(5000);
    }
    
    /**
     * todos os jogadores que participaram do bolão pagam 100 para o ganhador
     * o banco paga 1000 para o ganhador
     * @param jogador
     * @param qtdJogadores 
     */
    public void bolaoEsportes(Jogador ganhador, List <Jogador> participantes){
        for(Jogador j: participantes){ // cada participante paga 100 reais ao jogador que ganhou o bolao
            if(!j.getConta().transferir(ganhador.getConta(), 100)){ //se o jogador não tiver saldo suficiente
                j.getConta().realizarEmprestimo(100); //realiza um emprestimo
                j.getConta().transferir(ganhador.getConta(), 100);
            }
        }
        ganhador.getConta().depositar(1000); //banco deposita 1000 para o ganhador
    }
    
    /**
     * Jogador que caiu na casa perde o valor indicado na casa sorte grande de saldo 
     * @param jogador que caiu na casa
     */
    public void praiaNoDomingo(Jogador jogador){
        if(!jogador.getConta().sacar(valorIndicadoNaSorteGrande)){ //verifica se o jogador tem saldo suficiente
             jogador.getConta().realizarEmprestimo(valorIndicadoNaSorteGrande); //se não realiza um emprestimo
             jogador.getConta().sacar(valorIndicadoNaSorteGrande);
         }
         sorteGrande +=valorIndicadoNaSorteGrande; //adiciona o valor a sorte grande
    }
        
    /**
     * Deposita 1000 na conta do jogador que tirou 3 no dado
     * @param jogador jogador ganhador
     */
    public void concursoBandaArrocha(Jogador jogador){
        jogador.getConta().depositar(1000);
    }
    
    /**
     * Transfere 100 de todos os jogadores para o jogador que caiu na casa.
     * @param jogador jogador que caiu na casa
     */
    public void felizAniversario(Jogador jogador){
        for(Jogador j: jogadores){
            if(!j.getConta().transferir(jogador.getConta(), 100)){ //se o jogador não possuir saldo suficiente
                jogador.getConta().realizarEmprestimo(100); //faz emprestimo
                j.getConta().transferir(jogador.getConta(), 100);
            }
        }
    }
    
    /**
     * Desconta 100 vezes o numero que caiu no dado do jogador que que caiu na casa
     * @param jogador que caiu na casa
     * @param valorDado 
     */
    public void vendeNegocioOcasiao(Jogador jogador, int valorDado){
        if(!jogador.getConta().sacar(valorDado*100)){ //se o jogador não possuir saldo suficiente
            jogador.getConta().realizarEmprestimo(valorDado*100); //realiza emprestimo
            jogador.getConta().sacar(valorDado*100);
        }
    }
    
    /**
     * tranfere 100 vezes o numero do dado de um jogador para o local sorte grande.
     * @param jogador
     * @param valorDado 
     */
    public void maratonaBeneficente(Jogador jogador, int valorDado){
        if(!jogador.getConta().sacar(valorDado*100)){
           jogador.getConta().realizarEmprestimo(valorDado*100);
           jogador.getConta().sacar(valorDado*100);
        }
        sorteGrande += valorDado*100;
    }

    /**
     * Jogador caiu na casa sorte grande, recebe todo o valor depositado na sua conta
     * @param jogador 
     */
    public void sorteGrande(Jogador jogador){
        jogador.getConta().depositar(sorteGrande);
        sorteGrande = 0;
    }
    
    /**
     * Jogador recebe a mesada e paga emprestimo ou os juros se o saldo não for suficiente
     * @param jogador 
     */
    public void diaDaMesada(Jogador jogador){ 
        jogador.getConta().depositar(3500);
        if(jogador.isEndGame()){
            jogador.pagarDividasFimJogo();
        }else{
            jogador.pagarDividasFimRodada();
        }
    }
    
    
    //****************************************** METODOS RESPONSAVEIS PELA COMUNICAÇÃO ************************************
    
    /**
     * Reenvia a ultima mensagem que foi enviada ao grupo multcast.
     *
     * @throws java.io.IOException
     */
    public void reenviarUltMensGRP() throws IOException {
        controllerConexao.enviarMensagemGRP(ultimaMens);
    }
    
    
    
    //****************************************** METODOS DO CONTROLLER ************************************
    
    public static ControllerJogo getInstance() {
        if (controllerJogo == null) {
            controllerJogo = new ControllerJogo();
        }
        return controllerJogo;
    }

    void seletorAcao(String[] mens) {
        System.out.println(Arrays.toString(mens));
    }
    
    /**
     * Retorna a lista de jogadores
     * @return 
     */
    public List<Jogador> getJogadores(){
        return this.jogadores;
    }
    
    /**
     * Busca um jogador pelo id
     * @param idJogador 
     * @return  
     */
    public Jogador buscarJogador(int idJogador){
        for(Jogador j: jogadores){
            if(j.getIdentificacao()==idJogador){
                return j;
            }
        }
        return null;
    }

}
