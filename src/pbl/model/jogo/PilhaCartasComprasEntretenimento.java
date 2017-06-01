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
public abstract class PilhaCartasComprasEntretenimento {
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
        cartas.add(new Carta(10, "TERRENO", "", 3000.00));
        cartas.add(new Carta(11, "BOLSA DE VALORES", "", 1000.00));
        cartas.add(new Carta(12, "RELIQUIA ANTIGA", "", 5000.00));
        cartas.add(new Carta(13, "EMPRESA", "", 2500.00));
        cartas.add(new Carta(14, "CANTOR", "", 1500.00));
        cartas.add(new Carta(15, "CAIXA 2", "", 2000.00));
        embaralharCartas();
    }
    
    public static Carta buscarCarta(int codigo){
        for(Carta c: cartas){
            if(c.getCodigo()==codigo){
                return c;
            }
        }
        return null;
    }
}
