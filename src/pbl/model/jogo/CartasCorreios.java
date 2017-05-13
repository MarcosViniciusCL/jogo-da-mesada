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
public class CartasCorreios {

    private final List<String> cartas;
    private int index;

    public CartasCorreios() {
        this.cartas = new ArrayList();
        this.index = 0;
        criarCartas();
    }

    public String pegarCarta() {
        if(index >= cartas.size())
            return "SEM CARTAS";
        String tipo =  cartas.get(index);
        this.index++;
        return tipo;
    }
    
    public void embaralharCartas(){
        Collections.shuffle(this.cartas);
        this.index = 0;
    }
    
    private void criarCartas(){
        this.cartas.add("CONTAS");
        this.cartas.add("PAGUE A UM VIZINHO AGORA");
        this.cartas.add("DINHEIRO EXTRA");
        this.cartas.add("DOAÇÕES");
        this.cartas.add("COBRANÇA MOSTRO");
        this.cartas.add("VÁ EM FRENTE AGORA");
        embaralharCartas();
    }
}

