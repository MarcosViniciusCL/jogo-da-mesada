/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.model.jogo;

import java.util.Random;

/**
 *
 * @author marcos
 */
public class Dado {

    private int valor;
    private int valorAnterior;
    private final Random gerador;

    public Dado() {
        this.valor = 0;
        this.valorAnterior = 0;
        gerador = new Random();
    }

    public int jogarDado() {
        valorAnterior = valor;
        valor = 0;
        while (valor == 0) {
            valor = gerador.nextInt(7);
        }
        return valor;
    }
    
    public int getValorDado(){
        return valor;
    }
    
    public int getValorAntDado(){
        return valorAnterior;
    }

}
