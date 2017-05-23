/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.model.jogo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author emerson
 */
public class PilhaCartasComprasEntretenimento {
    private final List<Cartas> cartas;
    private int index;

    public PilhaCartasComprasEntretenimento() {
        this.cartas = new ArrayList();
        this.index = 0;
        criarCartas();
    }

    public Cartas pegarCarta() {
        if(index >= cartas.size())
            embaralharCartas();
        Cartas tipo =  cartas.get(index);
        this.index++;
        return tipo;
    }
    
    public void embaralharCartas(){
        Collections.shuffle(this.cartas);
        this.index = 0;
    }
    
    private void criarCartas(){
        this.cartas.add(new Cartas("CONTAS", "", 0));
        this.cartas.add(new Cartas("PAGUE A UM VIZINHO AGORA", "", 0));
        this.cartas.add(new Cartas("DINHEIRO EXTRA", "", 0));
        this.cartas.add(new Cartas("DOAÇÕES", "", 0));
        this.cartas.add(new Cartas("COBRANÇA MOSTRO", "", 0));
        this.cartas.add(new Cartas("VÁ EM FRENTE AGORA", "", 0));
        embaralharCartas();
    }
}
