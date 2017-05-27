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
import javax.swing.ImageIcon;

/**
 *
 * @author marcos
 */
public class Peao extends javax.swing.JLabel {

    private final Color cor;
    private int x; //Posição no Jpanel
    private int y;
    private int posicao; //Casa do tabuleiro.
    private int mesAtual;

    public Peao() {
        this.cor = Color.red;
        this.x = 0;
        this.y = 0;
        configurar();
    }

    private void configurar() {
        this.setSize(30, 30);
        //   this.setIcon(new ImageIcon(this.getClass().getResource("/pbl/view/icones/peaoT.png")));
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //Deixa mais bonitinho
        g.setColor(cor);
        g.fillOval(0, 0, 40, 40);
    }

    /**
     * Caminha as casas do tabuleiro.
     *
     * @param casa
     */
    public void andarCasas(int casa) {
        int col = 0, lin = 0;
        if (posicao + casa > 30) {
            posicao = casa; //Coloca o peão no inicio do tabuleiro, caso ta tenha completado o primeiro mes;
        } else {
            posicao += casa;
        }
        switch (posicao) { //Coloca a posição X e Y do peao de acordo com a posição no tabuleiro.
            case 0:
                lin = 0;
                col = 0;
                break;
            case 1:
                lin = 0;
                col = 103;
                break;
            case 2:
                lin = 0;
                col = 205;
                break;
            case 3:
                lin = 0;
                col = 307;
                break;
            case 4:
                lin = 0;
                col = 409;
                break;
            case 5:
                lin = 0;
                col = 511;
                break;
            case 6:
                lin = 0;
                col = 613;
                break;
            case 7:
                //INICIO DA SEGUNDA SEMANA;
                lin = 97;
                col = 0;
                break;
            case 8:
                lin = 97;
                col = 103;
                break;
            case 9:
                lin = 97;
                col = 205;
                break;
            case 10:
                lin = 97;
                col = 307;
                break;
            case 11:
                lin = 97;
                col = 409;
                break;
            case 12:
                lin = 97;
                col = 511;
                break;
            case 13:
                lin = 97;
                col = 613;
                break;
            case 14:
                //INICIO DA TERCEIRA SEMANA
                lin = 193;
                col = 0;
                break;
            case 15:
                lin = 193;
                col = 103;
                break;
            case 16:
                lin = 193;
                col = 205;
                break;
            case 17:
                lin = 193;
                col = 307;
                break;
            case 18:
                lin = 193;
                col = 409;
                break;
            case 19:
                lin = 193;
                col = 511;
                break;
            case 20:
                lin = 193;
                col = 613;
                break;
            case 21:
                //INICIO DA QUARTA SEMANA
                lin = 289;
                col = 0;
                break;
            case 22:
                lin = 289;
                col = 103;
                break;
            case 23:
                lin = 289;
                col = 205;
                break;
            case 24:
                lin = 289;
                col = 307;
                break;
            case 25:
                lin = 289;
                col = 409;
                break;
            case 26:
                lin = 289;
                col = 511;
                break;
            case 27:
                lin = 289;
                col = 613;
                break;
            case 28:
                //INICIO DA QUINTA SEMANA
                lin = 385;
                col = 0;
                break;
            case 29:
                lin = 385;
                col = 103;
                break;
            case 30:
                lin = 385;
                col = 205;
                break;
            default:
                break;
        }
        x = col;
        y = lin;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }
    
    /**
     * procura e move o peao para proxima casa achei um comprador
     */
    public void irParaProximaCasaAcheiComprador(){
        if(posicao<9){
            andarCasas(9);
        }else if(posicao<17){
            andarCasas(17);
        }else if(posicao<23){
            andarCasas(23);
        }else if(posicao<26){
            andarCasas(26);
        }else if(posicao<29){
            andarCasas(29);
        }
    }
    
    /**
     * procura e move o peao para proxima casa comprarEntretenimento
     */
    public void irParaProximaCasaComprasEntretenimento(){
        if(posicao<4){
            andarCasas(4);
        }else if(posicao<12){
            andarCasas(12);
        }else if(posicao<15){
            andarCasas(15);
        }else if(posicao<25){
            andarCasas(25);
        }
    }

    @Override
    public int getX() {
        return x;
    }

    public void setX(int lin) {
        this.x = lin;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int col) {
        this.y = col;
    }

}
