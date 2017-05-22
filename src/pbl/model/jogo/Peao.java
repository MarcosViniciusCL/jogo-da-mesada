/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.model.jogo;

//import com.sun.prism.paint.Color;

import javax.swing.JPanel;


/**
 *
 * @author marcos
 */
public class Peao extends JPanel{
  //  private final Color cor;
    private int x; //Posição no tabuleiro
    private int y;

    /*public Peao(Color cor) {
        this.cor = cor;
        this.posicao = 0;
    }*/

    
    /**
     * Caminha as casas do tabuleiro.
     * @param casa 
     */
    public void andarCasas(int casa){
        if(y + casa > 6){
            y = y+casa-6;
            x++;
        }
        if(x > 4){
            x = 0;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    
    
    
    
    
    
            
}
