/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.model.jogo;

//import com.sun.prism.paint.Color;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;


/**
 *
 * @author marcos
 */
public class Peao extends javax.swing.JLabel{
    private final Color cor;
    private int lin; //Posição no tabuleiro
    private int col;

    public Peao(Color cor) {
        this.cor = cor;
        this.lin = 0;
        this.col = 0;
        
    }
    

    
    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //Deixa mais bonitinho
        g.setColor(cor);
        g.fillOval(0, 0, 20, 20);
    }
    /**
     * Caminha as casas do tabuleiro.
     * @param casa 
     */
    public void andarCasas(int casa){
        if(col + casa > 6){
            col = col+casa-6-1;
            lin++;
        } else{
            col += casa;
        }
        if(lin > 4){
            lin=0;
        }
    }

    public int getLinha() {
        return lin;
    }

    public void setLinha(int lin) {
        this.lin = lin;
    }

    public int getColuna() {
        return col;
    }

    public void setColuna(int col) {
        this.col = col;
    }


    
    
    
    
    
    
            
}
