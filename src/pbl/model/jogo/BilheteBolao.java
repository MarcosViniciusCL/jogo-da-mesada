/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.model.jogo;

/**
 *
 * @author emerson
 */
public class BilheteBolao {
    int numero;
    int idJogador;

    public BilheteBolao(int numero, int idJogador) {
        this.numero = numero;
        this.idJogador = idJogador;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(int idJogador) {
        this.idJogador = idJogador;
    }
    
    
}
