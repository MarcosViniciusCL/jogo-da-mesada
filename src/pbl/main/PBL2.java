/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.main;

import java.io.IOException;
import pbl.controller.ControllerConexao;
import pbl.exception.ErroComunicacaoServidorException;

/**
 *
 * @author marcos
 */
public class PBL2 {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws pbl.exception.ErroComunicacaoServidorException
     */
    public static void main(String[] args) throws IOException, ErroComunicacaoServidorException {

        ControllerConexao control = ControllerConexao.getInstance();
        control.entraSala(2, 1);

    }

}
