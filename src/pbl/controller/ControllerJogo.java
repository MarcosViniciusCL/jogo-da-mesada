/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.controller;

import pbl.exception.AguadarVezException;
import java.io.IOException;
import pbl.model.Dado;

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

    private ControllerJogo() {
        this.controllerConexao = ControllerConexao.getInstance();
        this.dado = new Dado();
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

}
