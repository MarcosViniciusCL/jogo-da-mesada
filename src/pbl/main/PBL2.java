/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.main;

import com.sun.prism.paint.Color;
import java.util.ArrayList;
import java.util.List;
import pbl.model.CartasCorreios;
import pbl.model.Dado;
import pbl.model.Tabuleiro;
import pbl.model.*;
/**
 *
 * @author marcos
 */
public class PBL2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Peao peao = new Peao(Color.RED);
        Jogador jogador = new Jogador("Marcos Vinicius", peao);
        ArrayList listaJogadores = new ArrayList();
        listaJogadores.add(jogador);
        Tabuleiro tabuleiro = new Tabuleiro(30, listaJogadores);
        List<Peao> list = tabuleiro.getTabuleiro();
        exibirTabuleiro(list);
        jogador.getPeao().andarCasas(3);
        tabuleiro.atualizarTabuleiro();
        list = tabuleiro.getTabuleiro();
        exibirTabuleiro(list);
        
    }

    private static void exibirTabuleiro(List<Peao> list) {
        for (int i=0; i<list.size(); i++) {
            System.out.println(list.get(i).getNome());
        }
    }

}
