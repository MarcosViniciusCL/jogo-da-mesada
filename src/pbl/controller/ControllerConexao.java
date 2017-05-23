/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.controller;

import pbl.exception.ErroComunicacaoServidorException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author marcos
 */
public class ControllerConexao {

    private static ControllerConexao controlConexao; //Instancia da propria classe.
    private static ControllerJogo controllerJogo; //Instancia do controller do jogo.

    //ATRIBUTOS PARA CONEXÃO
    private MulticastSocket grupoMulticast; //Grupo que o cliente pertence no multicast;
    private String endGrupo; //Endereco do grupo que o cliente pertece;
    private int porta; //Porta do grupo multcast;
    private Socket servidor; //Servidor do jogo;
    private PrintStream enviar; //Envio de mensagem ao servidor;
    private BufferedReader receber; //Recebimento de mensagem do servidor;
    private Thread monitorMensGRP; //Thread que monitora recebimento de mensagem multicast;

    //ATRIBUTOS DA SALA DO JOGO
    private int identificador; // Numero para identificar o cliente;
    private boolean superNo; //Administrador do grupo.
    private int idJogAtual; //Contém o ID do jogador que está jogando no momento;
    private int maxJogadores; //Numero maximo de jogadores que deve ter na sala.

    private ControllerConexao() {
        ControllerConexao.controllerJogo = null;
        this.monitorMensGRP = null;
        this.idJogAtual = 0;
        this.identificador = 0;
    }

    //********************************* METODOS DO JOGO ******************************************************************
    public void entraSala(int maxJogadores, int quantMeses) throws ErroComunicacaoServidorException, IOException {
        if (servidor == null) {
            System.out.print("Servidor: ");
            conectarServidor(new Scanner(System.in).next());
        }

        enviarMensagemServidor("101;" + "MARCOS;" + maxJogadores + ";" + quantMeses); //Solicitação para entrar em uma sala;
        String resp = receberMensagemServidor();
        if (resp != null) {
            String[] str = resp.split(";");
            //str[1] - endGrupo, str[2] - porta, str[3] - numero de identificação
            this.identificador = Integer.parseInt(str[3]); //Identificando o cliente;
            if (identificador == 1) {
                this.superNo = true; //Torna o cliente super no;
            }
            assinarGrupoMult(str[1], Integer.parseInt(str[2])); //Assina o grupo que o servidor mandou;
            this.maxJogadores = maxJogadores;
            monitorMensGRP();
        }

    }

    //********************************* METODOS DE COMUNICAÇÃO COM SERVIDOR(TCP) *****************************************
    /**
     * Conecta ao servidor que irá criar a partida.
     *
     * @param ipServe
     * @throws IOException
     */
    public void conectarServidor(String ipServe) throws IOException {
        if (servidor == null || servidor.isClosed()) {
            servidor = new Socket(ipServe, 12345);
            this.enviar = new PrintStream(this.servidor.getOutputStream());
            this.receber = new BufferedReader(new InputStreamReader(this.servidor.getInputStream()));
        }
    }

    private void enviarMensagemServidor(String mens) {
        if (enviar != null) {
            this.enviar.println(mens);
        }
    }

    private String receberMensagemServidor() throws ErroComunicacaoServidorException {
        if (receber != null) {
            try {
                return this.receber.readLine();
            } catch (IOException ex) {
                throw new ErroComunicacaoServidorException();
            }
            //return null;
        }
        return null;
    }

    //********************************** COMUNICAÇÃO COM GRUPO MULTICAST **********************************
    /**
     * Assina um grupo multcast.
     *
     * @param endGrupo - Endereço referente ao grupo.
     * @param porta - Porta do grupo multcast.
     * @throws IOException
     */
    private void assinarGrupoMult(String endGrupo, int porta) throws IOException {
        this.porta = porta;
        this.endGrupo = endGrupo;
        this.grupoMulticast = new MulticastSocket(porta); //Criando instância de grupo. 
        grupoMulticast.joinGroup(InetAddress.getByName(endGrupo)); //Entrando no grupo de multicast
    }

    /**
     * Sair do grupo multcast que o cliente está conectado no momento.
     *
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
        mens = mens+";"+identificador;
        if (mens != null) {
            DatagramPacket env = new DatagramPacket(mens.getBytes(), mens.length(), InetAddress.getByName(this.endGrupo), this.porta);
            grupoMulticast.send(env);
        }
    }

    /**
     * Recebe mensagens do grupo multicast.
     *
     * @throws IOException
     */
    private void receberMensagemGRP() throws IOException {
        if (grupoMulticast != null) {
            byte buff[] = new byte[1024];
            DatagramPacket mens = new DatagramPacket(buff, buff.length);
            grupoMulticast.receive(mens);
            novaMensReceb(new String(mens.getData()));

        }
    }

    /**
     * Ativa o monitoramento no grupo multcast e informa quando há uma nova
     * mensagem.
     */
    private void monitorMensGRP() {
        if (grupoMulticast != null) {
            monitorMensGRP = new Thread() {
                @Override
                public void run() {
                    byte[] buff;
                    DatagramPacket mens;
                    while (true) {
                        buff = new byte[1024];
                        mens = new DatagramPacket(buff, buff.length);
                        try {
                            grupoMulticast.receive(mens);
                            novaMensReceb(new String(mens.getData()));
                        } catch (IOException ex) {
                            Logger.getLogger(ControllerConexao.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };
            monitorMensGRP.start();
        }
    }

    //Chamada quando o monitor de mensagem recebe uma nova mensagem.
    private void novaMensReceb(String mens) {
        String[] str = mens.split(";");

        //controllerJogo.seletorAcao(str);
        this.seletorAcao(str);

    }

    public static ControllerConexao getInstance() {
        if (controlConexao == null) {
            ControllerConexao.controlConexao = new ControllerConexao();
            ControllerConexao.controllerJogo = ControllerJogo.getInstance();
        }
        return ControllerConexao.controlConexao;
    }

    private void seletorAcao(String[] str) {
        switch (str[0]) {
            case "MSG": //Apenas mensagem para chat.
                controllerJogo.seletorAcao(str);
                break;
            case "111": //Informando que a sala está cheia.
                controllerJogo.seletorAcao("MSG;A SALA ESTÁ COMPLETA,#".split(";"));
                break;
            case "200":
                controllerJogo.seletorAcao(str);
                break;
            case "201":
                controllerJogo.seletorAcao("MSG;O JOGO VAI COMEÇAR;#".split(";"));
                if (this.identificador == this.idJogAtual){ //Verifica se é a vez do jogador.
                    controllerJogo.seletorAcao("MSG;SUA VEZ DE JOGAR".split(";"));
                }
                break;
            case "203":
                incrementarJogador(); //Incrementa a variavel que informa qual será o jogador da vez;
                if (this.identificador == this.idJogAtual){ //Verifica se é a vez do jogador.
                    controllerJogo.seletorAcao("MSG;SUA VEZ DE JOGAR".split(";"));
                }
                break;
            default:
                break;
        }
    }

    private void incrementarJogador() {
        this.idJogAtual++;
        if (this.idJogAtual > this.maxJogadores) {
            this.idJogAtual = 1;
        }
    }

}
