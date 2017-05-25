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
public class Carta {
    private int codigo;
    private String nome;
    private String descrição;
    private double valor;
    private static int cod = 0;
    
    public Carta(String nome, String descricao, double valor){
        this.nome = nome;
        this.descrição = descricao;
        this.valor = valor;
        this.codigo = cod++;
    }
    
    public int getCodigo(){
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrição() {
        return descrição;
    }

    public void setDescrição(String descrição) {
        this.descrição = descrição;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
