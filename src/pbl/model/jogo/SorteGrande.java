/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.model.jogo;

/**
 *
 * @author marcos
 */
public class SorteGrande {

    private double valortotal;

    public SorteGrande() {
        this.valortotal = 0.00;
    }

    public void adicionarDinheiro(double valor) {
        valortotal += valor;
    }

    public double pegarValor() {
        if (valortotal > 0.00) {
            double temp = valortotal;
            valortotal = 0.00;
            return temp;
        }
        return 0.00;
    }
    
    public double getValor(){
        return valortotal;
    }

}
