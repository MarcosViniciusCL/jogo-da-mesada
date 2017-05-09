/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import javax.swing.JOptionPane;
import pbl.controller.ControllerConexao;

/**
 *
 * @author marcos
 */
public class PBL2 {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        ControllerConexao control = ControllerConexao.getInstance();
        control.assinarGrupoMult("239.0.0.1", 12347);
        control.receberMensagemGRP();
        while (true) {
            control.enviarMensagemGRP(JOptionPane.showInputDialog("Mensagem"));
        }

    }

}
