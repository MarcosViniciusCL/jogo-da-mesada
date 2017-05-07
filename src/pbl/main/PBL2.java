/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.main;

import java.io.IOException;
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
        ControllerConexao controlCon = ControllerConexao.getInstance();
        controlCon.assinarGrupoMult("239.0.0.1", 12347);
        controlCon.enviarMensagemGRP(JOptionPane.showInputDialog("Mensagem:"));
        JOptionPane.showMessageDialog(null, controlCon.receberMensagemGRP());
    }

}
