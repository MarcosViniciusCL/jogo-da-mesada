/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author marcos
 */
public class ControllerConexao {
    
    private ControllerConexao controlConexao;
    
    private InetAddress grupoMulticast; //Grupo que o cliente pertence no multicast;
    private Socket servidor; //Servidor do jogo;
    
    private ControllerConexao(){}
    
    public void conectarServidor(String ipServe) throws IOException{
        this.servidor = new Socket(ipServe, 50505); //Conectando ao servidor;
    }
    
    public ControllerConexao getInstance(){
        if(controlConexao == null){
            this.controlConexao = new ControllerConexao();
        }
        return this.controlConexao;
    }
    
}
