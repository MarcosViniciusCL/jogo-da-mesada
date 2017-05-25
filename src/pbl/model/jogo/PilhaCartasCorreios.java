/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.model.jogo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author marcos
 */
public class PilhaCartasCorreios {

    private final List<Carta> cartas;
    private int index;

    public PilhaCartasCorreios() {
        this.cartas = new ArrayList();
        this.index = 0;
        criarCartas();
    }

    public Carta pegarCarta() {
        if(index >= cartas.size())
            embaralharCartas();
        Carta tipo =  cartas.get(index);
        this.index++;
        return tipo;
    }
    
    public void embaralharCartas(){
        Collections.shuffle(this.cartas);
        this.index = 0;
    }
    
    private void criarCartas(){
        this.cartas.add(new Carta("CONTAS", "", 0));
        this.cartas.add(new Carta("PAGUE A UM VIZINHO AGORA", "", 0));
        this.cartas.add(new Carta("DINHEIRO EXTRA", "", 0));
        this.cartas.add(new Carta("DOAÇÕES", "", 0));
        this.cartas.add(new Carta("COBRANÇA MONSTRO", "", 0));
        this.cartas.add(new Carta("VÁ EM FRENTE AGORA", "", 0));
        embaralharCartas();
    }
}

