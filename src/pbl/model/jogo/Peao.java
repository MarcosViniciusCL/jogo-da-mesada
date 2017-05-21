/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.model.jogo;

//import com.sun.prism.paint.Color;

/**
 *
 * @author marcos
 */
public class Peao {
  //  private final Color cor;
    private int posicao; //Posição no tabuleiro

    /*public Peao(Color cor) {
        this.cor = cor;
        this.posicao = 0;
    }*/

    public int getPosicao() {
        return posicao;
    }
    
    public int andarCasas(int casa){
        posicao+=casa;
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }
    
    
    
    
    
    
            
}
