/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.view.util;

import java.util.ArrayList;
import java.util.List;
import pbl.model.jogo.Carta;

/**
 *
 * @author marcos
 */
public class CartasComprasEntretTableModel extends javax.swing.table.AbstractTableModel{

    private List<Carta> cartas;
    private String[] colunas;

    public CartasComprasEntretTableModel() {
        cartas= new ArrayList<>();
        this.colunas = new String[] {"NOME", "VALOR", "VALOR VENDA"};
    }

    
    public CartasComprasEntretTableModel(List cartas) {
        this.cartas = cartas;
        this.colunas = new String[] {"NOME", "VALOR", "VALOR VENDA"};
    }
    
    
    @Override
    public int getRowCount() {
        return cartas.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }
    
    @Override
    public String getColumnName(int columnIndex){
        return colunas[columnIndex];
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int colunIndex){
        return false;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex){
        Carta carta = cartas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return carta.getNome();
            case 1:
                return carta.getValor();
            case 2:
                return carta.getValor()*1.5;
            default:
                throw new IndexOutOfBoundsException("index da coluna está fora do escopo: \""+columnIndex+"\"");
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex){
        Carta carta = cartas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                break;
            case 1:
                carta.setNome((String) aValue);
                break;
            case 2:
                carta.setValor(Double.parseDouble((String)aValue));
                break;
            default:
                throw new IndexOutOfBoundsException("index da coluna está fora do escopo: \""+columnIndex+"\"");
        }
    }
    
    public void addCarta(Carta carta){
        cartas.add(carta);
        int ultIndex = getRowCount() -1;
        fireTableRowsInserted(ultIndex, ultIndex);
    }
    
    public void remCarta(int indexCarta){
        cartas.remove(indexCarta);
        fireTableRowsDeleted(indexCarta, indexCarta);
    }
    
    public Carta getCarta(int indexLinha){
        return cartas.get(indexLinha);
    }
    
    public boolean estaVazia(){
        return cartas.isEmpty();
    }
    
    public void limpar(){
        cartas.clear();
        fireTableDataChanged();
    }

    
}
