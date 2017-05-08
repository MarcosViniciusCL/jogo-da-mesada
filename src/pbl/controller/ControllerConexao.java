/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author marcos
 */
public class ControllerConexao {

    private static ControllerConexao controlConexao; //Instancia da propria classe.
    private final ControllerJogo controllerJogo; //Instancia do controller do jogo.
    
    //ATRIBUTOS PARA CONEXÃO
    private MulticastSocket grupoMulticast; //Grupo que o cliente pertence no multicast;
    private String endGrupo; //Endereco do grupo que o cliente pertece;
    private int porta; //Porta do grupo multcast;
    private Socket servidor; //Servidor do jogo;
    
    

    private ControllerConexao() {
        this.controllerJogo = ControllerJogo.getInstance(); 
    }

    /**
     * Conecta ao servidor que irá criar a partida.
     *
     * @param ipServe
     * @throws IOException
     */
    public void conectarServidor(String ipServe) throws IOException {
        this.servidor = new Socket(ipServe, 50505); //Conectando ao servidor;
    }

    /**
     * Assina um grupo multcast.
     *
     * @param endGrupo - Endereço referente ao grupo.
     * @param porta - Porta do grupo multcast.
     * @throws IOException
     */
    public void assinarGrupoMult(String endGrupo, int porta) throws IOException {
        this.porta = porta;
        this.endGrupo = endGrupo;
        this.grupoMulticast = new MulticastSocket(porta); //Criando instância de grupo. 
        grupoMulticast.joinGroup(InetAddress.getByName(endGrupo)); //Entrando no grupo de multicast
    }

    /**
     * Sair do grupo multcast que o cliente está conectado no momento.
     * @throws IOException 
     */
    public void sairGrupoMult() throws IOException {
        if (grupoMulticast != null) {
            this.grupoMulticast.leaveGroup(InetAddress.getByName(endGrupo)); //Saindo do grupo multicast;
        }
    }

    /**
     * Envia a mensagem para todos que estão no grupo de multicast.
     *
     * @param mens - Mensagem que será enviada ao grupo.
     * @throws java.net.SocketException
     */
    public void enviarMensagemGRP(String mens) throws SocketException, IOException {
        DatagramPacket env = new DatagramPacket(mens.getBytes(), mens.length(), InetAddress.getByName(this.endGrupo), this.porta); 
        grupoMulticast.send(env);
    }

    /**
     * Recebe mensagens do grupo multicast.
     *
     * @return String - Retorna a mensagem recebida.
     * @throws IOException
     */
    public String receberMensagemGRP() throws IOException {
        if (grupoMulticast != null) {
            byte buff[] = new byte[1024];
            DatagramPacket mens = new DatagramPacket(buff, buff.length);
            grupoMulticast.receive(mens);
            return new String(mens.getData());
        }
        return null;

    }

    static public ControllerConexao getInstance() {
        if (controlConexao == null) {
            ControllerConexao.controlConexao = new ControllerConexao();
        }
        return ControllerConexao.controlConexao;
    }

}
