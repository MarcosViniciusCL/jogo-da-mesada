/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.view;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import pbl.controller.ControllerConexao;
import pbl.controller.ControllerJogo;
import pbl.model.jogo.Carta;
import pbl.view.util.CartasCorreioTableModel;

/**
 *
 * @author marcos
 */
public class JanelaPegarCartaCorreio extends javax.swing.JDialog {

    private int quantCarta;
    private ControllerJogo controllerJogo;
    private CartasCorreioTableModel modelTable;

    /**
     * Creates new form JanelaPegarCarta
     *
     * @param title
     * @param quantCarta
     */
    public JanelaPegarCartaCorreio(String title, int quantCarta) {
        super();
        this.controllerJogo = ControllerJogo.getInstance();
        this.quantCarta = quantCarta;
        initComponents();
        botaoX();

        atualizarInformacoes();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabelQuantCartas = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabelQuantCartas.setText("Pegue X cartas correiros");

        jButton1.setText("Pegar Carta");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Cartas Correios:");

        this.modelTable = new pbl.view.util.CartasCorreioTableModel();
        jTable1.setModel(this.modelTable);
        jScrollPane1.setViewportView(jTable1);

        jButton2.setText("Executar Ação");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelQuantCartas)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelQuantCartas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (this.quantCarta > 0) {
            Carta carta = controllerJogo.pegarCartaCorreio();
            modelTable.addCarta(carta);
            quantCarta--;
            atualizarInformacoes();
        } else {
            JOptionPane.showMessageDialog(null, "Você já pegou a quantidade de cartas.\nRealize as ações");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int select = jTable1.getSelectedRow();
        if (select > -1) {
            CartasCorreioTableModel model = (CartasCorreioTableModel) jTable1.getModel();
            Carta carta = model.getCarta(select);
            switch (carta.getCodigo()) {
                case 0:
                    model.remCarta(select);
                    break;
                case 1: //pague a um vizinho agora
                    controllerJogo.pagueUmVizinhoAgora(Integer.parseInt(JOptionPane.showInputDialog("ID VIZINHO")), carta.getCodigo());
                    model.remCarta(select);
                    break;
                case 2:
                    controllerJogo.dinheiroExtra(Integer.parseInt(JOptionPane.showInputDialog("ID VIZINHO")), carta.getCodigo());
                    model.remCarta(select);
                    break;
                case 3: //Carta doação
                    controllerJogo.doacao(carta.getCodigo());
                    model.remCarta(select);
                    break;
                case 4: //Carta va para frente agora
                    new VaParaFrenteAgora((Frame)this.getOwner(), true).show();
                    model.remCarta(select);
                    break;
                case 5: //Carta contas a pagar
                    int pagarAgora = JOptionPane.showConfirmDialog(null, "Você deseja pagar agora?","Pagar Agora", JOptionPane.YES_NO_OPTION);
                    if(pagarAgora == JOptionPane.YES_OPTION){ //se o jogador desejar pagar no momento
                        controllerJogo.contas(controllerJogo.getJogador().getIdentificacao(), true, carta.getCodigo());
                    }else{
                        controllerJogo.contas(controllerJogo.getJogador().getIdentificacao(), false, carta.getCodigo());
                    }
                    model.remCarta(select);
                    break;
                case 6: //carta cobraça monstro
                    int pagarAgora1 = JOptionPane.showConfirmDialog(null, "Você deseja pagar agora?","Pagar Agora", JOptionPane.YES_NO_OPTION);
                    if(pagarAgora1 == JOptionPane.YES_OPTION){ //se o jogador desejar pagar no momento
                        controllerJogo.cobrancaMonstro(controllerJogo.getJogador().getIdentificacao(),true, carta.getCodigo());
                    }else{
                        controllerJogo.cobrancaMonstro(controllerJogo.getJogador().getIdentificacao(),false, carta.getCodigo());
                    }
                    model.remCarta(select);
                    break;
                default:
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma carta antes.");
        }
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelQuantCartas;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    private void atualizarInformacoes() {
        jLabelQuantCartas.setText("Pegue " + this.quantCarta + " carta(s) correios");
    }

    private void botaoX() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                if (quantCarta > 0) {
                    JOptionPane.showMessageDialog(null, "Você só pode sair quando pegar todas as cartas");
                } else if (jTable1.getModel().getRowCount() > 0) {
                    JOptionPane.showMessageDialog(null, "Você deve realizar todas as ações antes de sair.");
                } else {
                    dispose();
                }
            }
        });
    }
}
