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
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author marcos
 */
public class ControllerConexao {

//    private static ControllerConexao controlConexao; //Instancia da propria classe.
    private final ControllerJogo controllerJogo; //Instancia do controller do jogo.

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

    //CONSTANTES COM PROTOCOLO DE COMUNICAÇÃO(GRUPOMULTCAST)
    private final int protMensChat = 300; //<- Protocolo para envio de mensagem pelo chat
    private final int protPassaVez = 201; //<- Protocolo que informa que o dado foi jogado;
    private final int protPagarVizinho = 4012; //<- Protocolo que informa a carta pagra vizinho agora;
    private final int protDinheiroExtra = 4013; //<- Protocolo que informa a carta dinheiro extra
    private final int protDoacaoSorteGrande = 4014; // <- Doação para o sorte grande
    private final int protGanheiSorteGrande = 4015; // <- Informa que ganhou sorte grande;
    private final int protFelizAniversario = 501; // <- Feliz aniversario;

    public ControllerConexao(ControllerJogo controllerJogo) {
        this.controllerJogo = controllerJogo;
        this.monitorMensGRP = null;
        this.idJogAtual = 1;
        this.identificador = 0;
    }

    //********************************* METODOS DO JOGO ******************************************************************
    public void passaVez(int valorDado) {
        enviarMensagemGRP(protPassaVez + ";" + valorDado);
    }

    public void pagueUmVizinhoAgora(int idVizinho, double valor) {
        enviarMensagemGRP(protPagarVizinho + ";" + idVizinho + ";" + valor);
    }

    public void dinheiroExtra(int idVizinho, double valor) {
        enviarMensagemGRP(protDinheiroExtra + ";" + idVizinho + ";" + valor);
    }

    public void doacao(double valor) {
        enviarMensagemGRP(protDoacaoSorteGrande + ";" + valor);
    }

    public void felizAniversario(double valor) {
        enviarMensagemGRP(protFelizAniversario + ";" + valor + ";");
    }

    /**
     * Envia mensagem para o grupo informando que ganhou o valor do sorte
     * grande, todos os outros clientes devem zerar o sorte grande.
     */
    public void ganheiSorteGrande() {
        enviarMensagemGRP(protGanheiSorteGrande + ";");
    }

    /**
     * Seleciona o que será feito de acordo com a mensagem recebida pelo grupo.
     *
     * @param str
     */
    private void seletorAcao(String[] str) {
        int aux = Integer.parseInt(str[0]);
        switch (aux) {
            case protMensChat: //Apenas mensagem para chat.
                controllerJogo.adicionarMensChat(str); //Adiciona a mensagem recebida no chat;
                break;
            case 111: //Informando que a sala está cheia.
                controllerJogo.adicionarMensChat("111;SALA CHEIA; #".split(";")); //Adiciona a mensagem no chat;
                int i = Integer.parseInt(str[1]); //Numero de jogadores
                int iId = 2,
                 iNome = 3;
                for (int j = 0; j < i; j++) {
                    controllerJogo.adicionarJogadores(Integer.parseInt(str[iId].trim()), str[iNome].trim()); //Adicionando jogadores;
                    iId += 2;
                    iNome += 2;
                }
                controllerJogo.setMinhaVez(isMinhaVez());
                break;
            case protPassaVez: //Informa que algum jogador jogou o dado.
                controllerJogo.moverPeao(Integer.parseInt(str[2].trim()), Integer.parseInt(str[1].trim())); //Movendo peao de jogadores
                incrementarJogador();
                controllerJogo.setMinhaVez(isMinhaVez());
                break;
            case protPagarVizinho: //Protocolo para pagar a um vizinho;
                if (Integer.parseInt(str[1].trim()) == identificador) { //Testa se o dinheiro foi para mim
                    controllerJogo.depositar(Double.parseDouble(str[2].trim()));
                }
                break;
            case protDinheiroExtra: //Protocolo dinheiro extra, tomar do vizinho ameaçando;
                if (Integer.parseInt(str[1].trim()) == identificador) { //Testa se o dinheiro foi para mim
                    controllerJogo.sacar(Double.parseDouble(str[2].trim()));
                }
                break;
            case protDoacaoSorteGrande: //Protocolo doação, adiciona dinheiro no sorte grande
                if (Integer.parseInt(str[2].trim()) != identificador) { //Caso tenha sido enviado por mim, não há necessidade de adicionar novamente
                    controllerJogo.adicionarSorteGrande(Double.parseDouble(str[1].trim()));
                }
                break;
            case protFelizAniversario: //Protocolo feliz aniversario
                if (Integer.parseInt(str[2].trim()) != identificador) { //Verifica se foi o proprio jogador que mandou a mensagem
                    controllerJogo.sacar(Double.parseDouble(str[1].trim()));
                }
                break;
            case protGanheiSorteGrande: //Informa que que ganhou sorte grande, os clientes devem tirar o dinheiro que tinha do sorte grande.
                if (Integer.parseInt(str[2].trim()) != identificador) {
                    controllerJogo.zerarSorteGrande();
                }
                break;
            default:
                break;
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

    public void entraSala(String nome, int maxJogadores, int quantMeses) throws ErroComunicacaoServidorException, IOException {
        if (servidor == null) {
            if (JOptionPane.showConfirmDialog(null, "Servidor local?") == 0) {
                conectarServidor("localhost");
            } else {
                conectarServidor(JOptionPane.showInputDialog("IP Servidor"));
            }
        }

        enviarMensagemServidor("100;" + nome + ";" + maxJogadores + ";" + quantMeses); //Solicitação para entrar em uma sala;
        String resp = receberMensagemServidor();
        System.out.println(resp);
        if (resp != null) {
            String[] str = resp.split(";");
            //str[1] - endGrupo, str[2] - porta, str[3] - numero de identificação
            this.identificador = Integer.parseInt(str[3]); //Identificando o cliente;
            if (identificador == 1) {
                this.superNo = true; //Torna o cliente super no;
            }
            assinarGrupoMult(str[1], Integer.parseInt(str[2])); //Assina o grupo que o servidor mandou;
            this.maxJogadores = maxJogadores;
            controllerJogo.criarJogadorPrincipal(identificador, nome); //Cria o jogador principal no controller de jogo
            monitorMensGRP();
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
    public void enviarMensagemGRP(String mens) {
        mens = mens + ";" + identificador;
        if (mens != null) {
            DatagramPacket env;
            try {
                env = new DatagramPacket(mens.getBytes(), mens.length(), InetAddress.getByName(this.endGrupo), this.porta);
                grupoMulticast.send(env);
            } catch (UnknownHostException ex) {
                Logger.getLogger(ControllerConexao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ControllerConexao.class.getName()).log(Level.SEVERE, null, ex);
            }

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

    public void novaMensChat(String mens) {
        enviarMensagemGRP(protMensChat + ";" + mens);
    }

    private void incrementarJogador() {
        this.idJogAtual++;
        if (this.idJogAtual > this.maxJogadores) {
            this.idJogAtual = 1;
        }
    }

    private boolean isMinhaVez() {
        return idJogAtual == identificador;
    }

}
