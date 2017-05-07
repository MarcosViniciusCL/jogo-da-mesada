/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marcos
 */
public class Tabuleiro {
    private List<Peao> tabuleiro;
    private List<Jogador> jogadores;

    public Tabuleiro(int dias, List jogadores) {
        gerarTabuleiro(dias);
        this.jogadores = jogadores;
    }
    
    /**
     * Gera a lista que se comportará como um tabuleiro, todos espaços
     * serão nulos.
     * @param dias - Quantidades de dias que a partida terá.
     */
    private void gerarTabuleiro(int dias){
        this.tabuleiro = new ArrayList();
        for(int i = 0; i <= dias; i++)
            tabuleiro.add(null);
    }
    
    /**
     * Pega todos o jogadores que estão na lista e atualiza suas posições
     * no tabuleiro.
     */
    public void atualizarTabuleiro(){
        for (int i=0; i<tabuleiro.size(); i++) {
            tabuleiro.set(i,null);
        }
        for (int i=0; i<jogadores.size(); i++) {
            Jogador jogador = jogadores.get(i);
            tabuleiro.add(jogador.getPeao().getPosicao(), jogador.getPeao());
        }
    }

    public List<Peao> getTabuleiro() {
        return this.tabuleiro;
    }
    
}
