/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.model.jogo;

import java.util.ArrayList;
import java.util.List;
import pbl.controller.ControllerJogo;

/**
 *
 * @author marcos
 */
public class Tabuleiro {
    private ControllerJogo controller = ControllerJogo.getInstance();
    private List<Peao> peoes;
    private int qtdDias;

    public Tabuleiro(int dias) {
        this.peoes = new ArrayList();
        this.qtdDias = dias;
        gerarTabuleiro();
    }
    
    /**
     * Gera a lista que se comportará como um tabuleiro
     * @param dias - Quantidades de dias que a partida terá.
     */
    private void gerarTabuleiro(){
        List <Jogador>jogadores = controller.getJogadores();
        for(Jogador j: jogadores){
            peoes.add(j.getPeao());
        }
    }
    
    public List<Peao> getTabuleiro() {
        return peoes;
    }
    
}
