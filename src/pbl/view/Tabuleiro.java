/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import pbl.model.jogo.Peao;

/**
 *
 * @author marcos
 */
public class Tabuleiro extends javax.swing.JPanel {

    /**
     * Creates new form Tabuleiro
     */
    private JPanel[][] tabuleiro;
    private final List<Peao> peoes;

    public Tabuleiro() {
        initComponents();
        this.peoes = new ArrayList<>();
        gerarGUI();
    }
    
    //Metodo para testar movimento de objeto;
    public void andar(int casas){
        Peao p = peoes.get(0);
        p.andarCasas(casas);
        atualizarLocalInterface();
    }
    
    public void adicionarPeao(Peao peao){
        peoes.add(peao);
        atualizarLocalInterface();
    }

    private void gerarGUI() {
        this.setLayout(new GridLayout(5, 7, 2, 2));
        tabuleiro = new JPanel[5][7];
        
        tabuleiro[0][0] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[0][1] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[0][2] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[0][3] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[0][4] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[0][5] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[0][6] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[1][0] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[1][1] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[1][2] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[1][3] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[1][4] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[1][5] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[1][6] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[2][0] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[2][1] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[2][2] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[2][3] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[2][4] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[2][5] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[2][6] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[3][0] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[3][1] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[3][2] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[3][3] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[3][4] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[3][5] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[3][6] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[4][0] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[4][1] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[4][2] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[4][3] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[4][4] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[4][5] = new JPanel(new GridLayout(3, 2, 1, 1));
        tabuleiro[4][6] = new JPanel(new GridLayout(3, 2, 1, 1));

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                if ((i + j) % 2 == 0) {
                    tabuleiro[i][j].setBackground(Color.black);
                } else {
                    tabuleiro[i][j].setBackground(Color.white);
                }
                this.add(tabuleiro[i][j]);
            }

        }
//        tabuleiro[0][1].add(new JLabel("BLA"));

    }

    public void atualizarLocalInterface(){
        limparTabuleiro();
        for (Peao peao : peoes) {
//            removerPeao(peao);
            int x = peao.getLinha();
            int y = peao.getColuna();
            tabuleiro[x][y].add(peao);
            tabuleiro[x][y].validate();
        }
        validate();
        
    }
    private void removerPeao(Peao p){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                tabuleiro[i][j].remove(p);
                tabuleiro[i][j].validate();
            }
        }
        validate();
    }
    private void limparTabuleiro(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                int quant = tabuleiro[i][j].getComponents().length-1;
                for (int k = 0; k < quant; k++) {
                    tabuleiro[i][j].remove(k);
                }
                //tabuleiro[i][j].removeAll();
                tabuleiro[i][j].validate();
            }
        }
        validate();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
