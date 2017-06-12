/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.view;

import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import pbl.controller.ControllerConexao;
import pbl.controller.ControllerJogo;
import pbl.exception.AguadarVezException;
import pbl.exception.DinheiroInsuficienteException;
import pbl.model.jogo.Carta;
import pbl.model.jogo.Jogador;
import pbl.model.jogo.Peao;
import pbl.model.jogo.PilhaCartasComprasEntretenimento;
import pbl.view.util.CartasComprasEntretTableModel;

/**
 *
 * @author marcos
 */
public class Principal extends javax.swing.JFrame {

    /**
     * Creates new form Principal
     */
    private Principal tela;
    private final ControllerJogo controllerJogo;
    private final ControllerConexao controllerConexao;
    private CartasComprasEntretTableModel modelCartas;

    public Principal(String title) {
        super(title);
        initComponents();
        controllerJogo = ControllerJogo.getInstance();
        controllerConexao = controllerJogo.getControllerConexao();
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
        jPanelChat = new javax.swing.JPanel();
        jTextFieldChat = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPaneChat = new javax.swing.JScrollPane();
        jTextAreaChat = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldExibSaldoJogador = new javax.swing.JTextField();
        jTextFieldExibDividaJogador = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaInfJog = new javax.swing.JTextArea();
        jButtonRealEmprestimo = new javax.swing.JButton();
        jTextFieldExibNome = new javax.swing.JTextField();
        jButtonJogaDado = new javax.swing.JButton();
        tabuleiro1 = new pbl.view.Tabuleiro();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldExibSorteGrande = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaInfoCasas = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setLayout(null);

        jTextFieldChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldChatActionPerformed(evt);
            }
        });

        jLabel5.setText("Chat");

        jScrollPaneChat.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneChat.setToolTipText("");

        jTextAreaChat.setColumns(20);
        jTextAreaChat.setRows(5);
        jTextAreaChat.setInheritsPopupMenu(true);
        jTextAreaChat.setLineWrap(true);
        jTextAreaChat.setWrapStyleWord(true);
        jScrollPaneChat.setViewportView(jTextAreaChat);

        javax.swing.GroupLayout jPanelChatLayout = new javax.swing.GroupLayout(jPanelChat);
        jPanelChat.setLayout(jPanelChatLayout);
        jPanelChatLayout.setHorizontalGroup(
            jPanelChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextFieldChat)
            .addGroup(jPanelChatLayout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPaneChat, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
        );
        jPanelChatLayout.setVerticalGroup(
            jPanelChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelChatLayout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneChat, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel2.setText("SALDO:");

        jLabel3.setText("DEPITO:");

        jTextFieldExibSaldoJogador.setEditable(false);

        jTextFieldExibDividaJogador.setEditable(false);

        jLabel4.setText("Informações dos jogadores");

        jTextAreaInfJog.setEditable(false);
        jTextAreaInfJog.setColumns(20);
        jTextAreaInfJog.setRows(5);
        jScrollPane2.setViewportView(jTextAreaInfJog);

        jButtonRealEmprestimo.setText("Emprestimo");
        jButtonRealEmprestimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRealEmprestimoActionPerformed(evt);
            }
        });

        jTextFieldExibNome.setEditable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldExibDividaJogador)
                    .addComponent(jTextFieldExibSaldoJogador, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonRealEmprestimo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jTextFieldExibNome)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jTextFieldExibNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldExibSaldoJogador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonRealEmprestimo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldExibDividaJogador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jButtonJogaDado.setText("Jogar Dado");
        jButtonJogaDado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonJogaDadoActionPerformed(evt);
            }
        });

        tabuleiro1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tabuleiro1MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tabuleiro1MouseReleased(evt);
            }
        });

        jLabel6.setText("SORTE GRANDE:");

        jTextFieldExibSorteGrande.setEditable(false);

        jLabel7.setText("CARTAS: COMPRAS E ENTRETENIMENTOS");

        modelCartas = new pbl.view.util.CartasComprasEntretTableModel();
        jTable1.setModel(modelCartas);
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Informação das casas");

        jTextAreaInfoCasas.setEditable(false);
        jTextAreaInfoCasas.setColumns(20);
        jTextAreaInfoCasas.setRows(5);
        jTextAreaInfoCasas.setLineWrap(true);
        jTextAreaInfoCasas.setWrapStyleWord(true);
        jTextAreaInfoCasas.setText("CLICK SOBRE UMA CASA PARA OBTER INFORMAÇÃO");
        jScrollPane3.setViewportView(jTextAreaInfoCasas);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldExibSorteGrande, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonJogaDado, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tabuleiro1, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextFieldExibSorteGrande, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonJogaDado)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tabuleiro1, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonJogaDadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonJogaDadoActionPerformed
        int valor = controllerJogo.jogarDado();
        jButtonJogaDado.setText("Jogar dado: " + valor);
        controllerConexao.novaJogada(6);
    }//GEN-LAST:event_jButtonJogaDadoActionPerformed

    private void jButtonRealEmprestimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRealEmprestimoActionPerformed
        String valor = JOptionPane.showInputDialog("Valor do emprestimo");
        if (valor != null | !valor.isEmpty()) {
            valor = valor.trim().replace(",", ".");
            controllerJogo.pedirEmprestimo(controllerJogo.getJogador().getIdentificacao(), Integer.parseInt(valor));
        }

        atualizarInformacoesTela();
    }//GEN-LAST:event_jButtonRealEmprestimoActionPerformed

    private void jTextFieldChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldChatActionPerformed
        String text = jTextFieldChat.getText().trim();
        if (!text.trim().equals("")) {
            controllerJogo.novaMensagemChat(text);
            jTextFieldChat.setText("");
        }
    }//GEN-LAST:event_jTextFieldChatActionPerformed

    private void tabuleiro1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabuleiro1MouseReleased
        int x = evt.getX(), y = evt.getY();
        mostrarInfoCasa(y, x);
    }//GEN-LAST:event_tabuleiro1MouseReleased

    private void tabuleiro1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabuleiro1MouseExited
        jTextAreaInfoCasas.setText("CLICK SOBRE UMA CASA PARA OBTER INFORMAÇÃO");
    }//GEN-LAST:event_tabuleiro1MouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonJogaDado;
    private javax.swing.JButton jButtonRealEmprestimo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelChat;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPaneChat;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextAreaChat;
    private javax.swing.JTextArea jTextAreaInfJog;
    private javax.swing.JTextArea jTextAreaInfoCasas;
    private javax.swing.JTextField jTextFieldChat;
    private javax.swing.JTextField jTextFieldExibDividaJogador;
    private javax.swing.JTextField jTextFieldExibNome;
    private javax.swing.JTextField jTextFieldExibSaldoJogador;
    private javax.swing.JTextField jTextFieldExibSorteGrande;
    private pbl.view.Tabuleiro tabuleiro1;
    // End of variables declaration//GEN-END:variables

    //************************* METODOS PARA ABRIR NOVAS JANELAS ******************************
    /**
     * Abre a janela para jogador pegar as cartas correios e realizar a ação
     *
     * @param quantCarta
     */
    public void abrirJanelaPegarCartaCorreio(int quantCarta) {
        JanelaPegarCartaCorreio jpc = new JanelaPegarCartaCorreio("Pegar Carta", quantCarta);
        jpc.setLocationRelativeTo(null);
        jpc.setResizable(false);
        jpc.setModal(true);
        jpc.setVisible(true);
    }
    
    /**
     * Janela que o jogador opta por participar do bolão de esportes
     */
    public void escolheParticiparBolaoEsportes(){
        if(JOptionPane.showConfirmDialog(null, "Deseja Participar do bolão de esportes?") == 0){
            participarBolaoEsportes();
        } else {
            controllerConexao.participarBolaoEsportes(0); //Manda zero, numero fora do intervalo, para informar que não participará do bolão. 
        }
    }
    
    /**
     * abre a janela o jogador desejar participar do bolão de esportes 
     */
    public void participarBolaoEsportes(){
        new BolaoDeEsportes(this, true).show();
    }

    /**
     * Abre a janela para o jogador selecionar a carta que deseja vender
     */
    public void abrirJanelaVendeCartaCE() {
        if (!modelCartas.estaVazia()) {
            JanelaVendeCartaCompraEntre jvce = new JanelaVendeCartaCompraEntre(jTable1);
            jvce.setLocationRelativeTo(null);
            jvce.setResizable(false);
            jvce.setModal(true);
            jvce.setVisible(true);
            atualizarInformacoesTela();
        } else {
            JOptionPane.showMessageDialog(null, "Você caiu na casa \"Achou um Comprador\", mas você não tem cartas para vender.");
        }
    }

    public void abrirJanelaBandaArrocha() {
        JanelaBandaArrocha jba = new JanelaBandaArrocha();
        jba.setLocationRelativeTo(null);
        jba.setResizable(false);
        jba.setModal(true);
        jba.setVisible(true);
    }

    public void acaoComprarCarta() {
        Carta c = PilhaCartasComprasEntretenimento.pegarCarta();
        JOptionPane.showMessageDialog(null, "Você caiu na casa \"Compras e Entretenimento\" informe se você vai querer a carta a seguir.");

        if (JOptionPane.showConfirmDialog(null, "Você quer a carta " + c.getNome() + " que vale $" + c.getValor()) == 0) {
            while (true) {
                if(controllerJogo.verificarSeJogadorPrincipalTemSaldo(c.getValor())){
                    controllerJogo.compraEntretenimento(controllerJogo.getJogador().getIdentificacao(), c.getCodigo());
                    modelCartas.addCarta(c);
                    break;
                }else{
                    if (JOptionPane.showConfirmDialog(null, "Você não tem dinheiro suficiente, deseja realizar um emprestimo?") == 0) {
                        jButtonRealEmprestimoActionPerformed(null);
                    } else {
                        break;
                    }
                }

            }
        }

    }

    /**
     * Atualiza todas as informações dos jogadores na tela principal e atualiza
     * o tabuleiro em seguida.
     */
    public void atualizarInformacoesTela() {
        //Atualizando dados da conta bancaria do jogador
        Jogador j = controllerJogo.getJogador();
        jTextFieldExibNome.setText(j.getNome());
        jTextFieldExibSaldoJogador.setText(String.format("%.2f", j.getConta().consultarSaldo()));
        jTextFieldExibDividaJogador.setText(String.format("%.2f", j.getConta().getEmprestimo().getValorTotal()));

        //Atualizando exibição de sorte grande;
        jTextFieldExibSorteGrande.setText(String.format("%.2f", controllerJogo.getValorSorteGrande()));

        //Atualizando informações dos jogadores;
        List<Jogador> l = controllerJogo.getJogadores();
        jTextAreaInfJog.setText("");
        for (Jogador jog : l) {
            jTextAreaInfJog.append(jog.getIdentificacao() + " - " + jog.getNome() + ": R$" + jog.getConta().consultarSaldo() + "\n");
        }

        //Atualizando mensagem do chat
        String[] str = jTextAreaChat.getText().split("\n");
        String nova = controllerJogo.getChat().getUltimaMens();
        if (!str[str.length - 1].equals(nova.replace("\n", ""))) { //Verificando duplicidade de mensagem;
            jTextAreaChat.append(nova);
            jScrollPaneChat.getViewport().setViewPosition(new Point(0, jScrollPaneChat.getVerticalScrollBar().getMaximum()));//Coloca o scroll no fim do texto.
        }

        //Habilitar botão para jogar dado
        jButtonJogaDado.setEnabled(controllerJogo.isMinhaVez());

        //Atualizar cartas
//        List<Carta> list = controllerJogo.getJogador().getCartasCompEntret();
//        modelCartas.limpar();
//        for (Carta carta : list) {
//            this.modelCartas.addCarta(carta);
//        }
        tabuleiro1.atualizarTabuleiro();
    }

    public void mostrarMensagem(String mens) {

    }
    
    private void mostrarInfoCasa(int x, int y) {
        if (x >= 0 && x <= 97) {
            if (y >= 0 && y <= 102) { //Casa 0
                jTextAreaInfoCasas.setText("00 - CASA PARTIDA: Esta casa é onde todos os peoes ficam até que todos os jogadores entrem na sala".toUpperCase());
            } else if (y >= 102 && y <= 204) { //Casa 1
                jTextAreaInfoCasas.setText("01 - CASA CORREIO: NESTA CASA O JOGADOR DEVERÁ PEGAR 1 CARTA DO TIPO CORREIO, DEVE EXECUTAR A AÇÃO QUE ESTÁ ESCRITO NA CARTA");
            } else if (y >= 205 && y <= 306) { //Casa 2
                jTextAreaInfoCasas.setText("02 - CASA PRÊMIO: AO CAIR NESTA CASA VOCÊ GANHA $5.000 DO BANCO");
            } else if (y >= 307 && y <= 408) { //Casa 3
                jTextAreaInfoCasas.setText("03 - CASA CORREIO: VOCÊ DEVE PEGAR 3 CARTAS DO TIPO CORREIO, AS AÇÕES DEVE SER FEITA NA ORDEM DE SUA PREFERÊNCIA");
            } else if (y >= 409 && y <= 510) { //Casa 4

            } else if (y >= 511 && y <= 613) { //Casa 5

            } else { //Casa 6

            }
        } else if (x >= 98 && x <= 191) {
            if (y >= 0 && y <= 102) { //Casa 7

            } else if (y >= 102 && y <= 204) { //Casa 8

            } else if (y >= 205 && y <= 306) { //Casa 9

            } else if (y >= 307 && y <= 408) { //Casa 10

            } else if (y >= 409 && y <= 510) { //Casa 11

            } else if (y >= 511 && y <= 613) { //Casa 12

            } else { //Casa 13

            }
        } else if (x >= 192 && x <= 287) {
            if (y >= 0 && y <= 102) { //Casa 14

            } else if (y >= 102 && y <= 204) { //Casa 15

            } else if (y >= 205 && y <= 306) { //Casa 16

            } else if (y >= 307 && y <= 408) { //Casa 17

            } else if (y >= 409 && y <= 510) { //Casa 18

            } else if (y >= 511 && y <= 613) { //Casa 19

            } else { //Casa 20

            }
        } else if (x >= 288 && x <= 384) {
            if (y >= 0 && y <= 102) { //Casa 21

            } else if (y >= 102 && y <= 204) { //Casa 22

            } else if (y >= 205 && y <= 306) { //Casa 23

            } else if (y >= 307 && y <= 408) { //Casa 24

            } else if (y >= 409 && y <= 510) { //Casa 25

            } else if (y >= 511 && y <= 613) { //Casa 26

            } else { //Casa 27

            }
        } else if (x >= 385 && x <= 480) {
            if (y >= 0 && y <= 102) { //Casa 28

            } else if (y >= 102 && y <= 204) { //Casa 29

            } else if (y >= 205 && y <= 306) { //Casa 30

            } else if (y >= 307 && y <= 408) { //Casa 31

            } else if (y >= 409 && y <= 510) { //Casa NULA

            } else if (y >= 511 && y <= 613) { //Casa NULA

            } else { //Casa NULA

            }
        }
    }

}
