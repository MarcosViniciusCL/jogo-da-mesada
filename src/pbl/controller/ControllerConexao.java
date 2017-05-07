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

    private static ControllerConexao controlConexao;

    private MulticastSocket grupoMulticast; //Grupo que o cliente pertence no multicast;
    private int porta; //Porta do grupo multcast;
    private Socket servidor; //Servidor do jogo;

    private ControllerConexao() {}

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
        this.grupoMulticast = new MulticastSocket(porta); //Criando instância de grupo. 
        grupoMulticast.joinGroup(InetAddress.getByName(endGrupo)); //Entrando no grupo de multicast
    }

    /**
     * Sair do grupo multcast que o cliente está conectado no momento.
     * @throws IOException 
     */
    public void sairGrupoMult() throws IOException {
        if (grupoMulticast != null) {
            this.grupoMulticast.leaveGroup(grupoMulticast.getInetAddress()); //Saindo do grupo multicast;
        }
    }

    /**
     * Envia a mensagem para todos que estão no grupo de multicast.
     *
     * @param mens - Mensagem que será enviada ao grupo.
     * @throws java.net.SocketException
     */
    public void enviarMensagemGRP(String mens) throws SocketException, IOException {
        byte[] b = mens.getBytes();
        new DatagramSocket().send(new DatagramPacket(b, b.length, this.grupoMulticast.getInetAddress(), 12347));
    }

    /**
     * Recebe mensagens do grupo multicast.
     *
     * @return String - Retorna a mensagem recebida.
     * @throws IOException
     */
    public String receberMensagemGRP() throws IOException {
        if (grupoMulticast != null) {
            byte rec[] = new byte[256];
            DatagramPacket mens = new DatagramPacket(rec, rec.length);
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
