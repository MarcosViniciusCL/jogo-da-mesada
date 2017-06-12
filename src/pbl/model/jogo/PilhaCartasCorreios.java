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
public abstract class PilhaCartasCorreios {

    private static final List<Carta> cartas = new ArrayList();
    private static int index = 0;
    
    /**
     * Devolve uma carta da pilha
     * @return 
     */
    public static Carta pegarCarta() {
        if(cartas.size()==0){ //se não existirem cartas, cria-se
            criarCartas();
        }
        
        if(index >= cartas.size()){ //quando a pilha de cartas chegar ao fim
            embaralharCartas();
        }
        Carta tipo =  cartas.get(index);
        index++;
        return tipo;
    }
    
    /**
     * Embaralha as cartas do monte, e zera o id da pilha de carta
     */
    private static void embaralharCartas(){
        Collections.shuffle(cartas);
        index = 0;
    }
    
    private static void criarCartas(){
        
        cartas.add(new Carta(1, "PAGUE A UM VIZINHO AGORA", "", 500.00));
        cartas.add(new Carta(2, "DINHEIRO EXTRA", "", 800.00));
        cartas.add(new Carta(3, "DOAÇÕES", "", 300.00));
        cartas.add(new Carta(4, "VÁ EM FRENTE AGORA", "", 0));
        cartas.add(new Carta(5, "CONTAS", "", 0));
        cartas.add(new Carta(6, "COBRANÇA MOSTRO", "", 0));
        embaralharCartas();
    }
    
    /**
     * Devolve uma carta apartir de seu id
     * @param codigo
     * @return 
     */
    public static Carta buscarCarta(int codigo){
        if(cartas.isEmpty()){ //se não existirem cartas, cria-se
            criarCartas();
        }
        for(Carta c: cartas){
            if(c.getCodigo()==codigo){
                return c;
            }
        }
        return null;
    }
    
    public static void start(){
    }
}

