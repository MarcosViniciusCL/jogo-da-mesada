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
import pbl.exception.DinheiroInsuficienteException;
import pbl.exception.ErroNaBuscaDeCartaOuVendedor;
import pbl.exception.NenhumJogadorGanhouBolaoException;

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
    private final int protFinalizarPartidaGeral = 112; // <- mensagem para final da partida
    private final int protMensChat = 300; //<- Protocolo para envio de mensagem pelo chat
    private final int protPassaVez = 201; //<- Protocolo que informa que o dado foi jogado;
    private final int protPagarVizinho = 4012; //<- Protocolo que informa a carta pagra vizinho agora;
    private final int protDinheiroExtra = 4013; //<- Protocolo que informa a carta dinheiro extra
    private final int protDoacao = 4014; // <- Doação para o sorte grande
    private final int sorteGrande = 4015; // <- Informa que ganhou sorte grande;
    private final int protVaParaFrenteAgora = 4016; // <- Informa que o jogador tirou a casa va para frente agora
    private final int protContasPagar = 4017; // <- jogador sorteou uma carta do tipo conta
    private final int protCobrancaMonstro = 4018; // <- jogador sorteou uma carta do tipo cobrança monstro 
    private final int protFelizAniversario = 501; // <- Feliz aniversario;
    private final int protConcBandaArrocha = 601; //<- Deve jogar o dado para ver quem vai ganhar.
    private final int protAcheiUmComprador = 701; // <- Achei um comprador
    private final int protComprasEntretenimentos = 801; // <- compras e entretenimentos
    private final int protJogada = 901;
    private final int protJogadaEspecial = 1001;
    private final int protPassarVez = 1101;
    private final int protParticiparBolaoEsportes = 2101;
    private final int protFinalizarBolaoEsportes = 2102;
    private final int protFinalizarPartida = 3102; //<-um jogador acaba a partida

    public ControllerConexao(ControllerJogo controllerJogo) {
        this.controllerJogo = controllerJogo;
        this.monitorMensGRP = null;
        this.idJogAtual = 1;
        this.identificador = 0;
    }

    //********************************* METODOS DE ENVIO DE MENSAGENS DO JOGO ******************************************************************
    public void novaJogada(int valorDado) {
        enviarMensagemGRP(protJogada + ";" + valorDado);
    }

    public void novaJogadaEspecial(int valorDado) {
        enviarMensagemGRP(protJogada + ";" + valorDado);
    }

    public void passarVez() {
        enviarMensagemGRP(protPassarVez + "");
    }

    public void passaVez(int valorDado) {
        enviarMensagemGRP(protPassaVez + ";" + valorDado);
    }

    public void pagueUmVizinhoAgora(int idVizinho, int codCarta) {
        enviarMensagemGRP(protPagarVizinho + ";" + idVizinho + ";" + "codCarta");
    }

    public void dinheiroExtra(int idVizinho, int codCarta) {
        enviarMensagemGRP(protDinheiroExtra + ";" + idVizinho + ";" + codCarta);
    }

    public void felizAniversario(double valor) {
        enviarMensagemGRP(protFelizAniversario + "");
    }

    /**
     * Envia mensagem para o grupo informando que ganhou o valor do sorte
     * grande, todos os outros clientes devem zerar o sorte grande.
     */
    public void sorteGrande() {
        enviarMensagemGRP(sorteGrande + "");
    }

    public void vaParaFrenteAgora(boolean compraEnt) {
        if (compraEnt) {
            enviarMensagemGRP(protVaParaFrenteAgora + ";0");
        } else {
            enviarMensagemGRP(protVaParaFrenteAgora + ";1");
        }
    }

    public void conta(int idCarta, int pagarAgora) {
        enviarMensagemGRP(protContasPagar + ";" + pagarAgora + ";" + idCarta);
    }

    public void cobrancaMonstro(int idCarta, int pagarAgora) {
        enviarMensagemGRP(protCobrancaMonstro + ";" + pagarAgora + ";" + idCarta);
    }

    public void doacao(int codigoCarta) {
        enviarMensagemGRP(protDoacao + ";" + codigoCarta);
    }

    public void resultadoBandaArrocha(boolean ganhou) {
        if (ganhou) {
            enviarMensagemGRP(protConcBandaArrocha + ";1");
        } else {
            enviarMensagemGRP(protConcBandaArrocha + ";0");
        }
    }

    public void acheiUmComprador(int codigoCarta) {
        enviarMensagemGRP(protAcheiUmComprador + ";" + codigoCarta);
    }

    public void compraEntrenimentos(int codigoCarta) {
        enviarMensagemGRP(protComprasEntretenimentos + ";" + codigoCarta);
    }

    public void participarBolaoEsportes(int numero) {
        enviarMensagemGRP(protParticiparBolaoEsportes + ";" + numero);
    }

    public void encerrarBolaoEsporte(int idGanhador) {
        enviarMensagemGRP(protFinalizarBolaoEsportes + ";" + idGanhador);
    }

    public void finalizarPartida() {
        enviarMensagemGRP(protFinalizarPartida + "");
    }

    //******************************** METODOS DE RECEPÇÃO DOS DADOS DO JOGO ****************************
    private void novaJogadaR(String[] str) {
        int idJogador = Integer.parseInt(str[0].trim());
        int valorDado = Integer.parseInt(str[2].trim());
        controllerJogo.moverPeao(idJogador, valorDado);
    }

    private void novaJogadaEspecialR(String[] str) {
        int idJogador = Integer.parseInt(str[0]);
        int valorDado = Integer.parseInt(str[2]);

    }

    public void cadastrarJogadores(String[] str) {
        int i = Integer.parseInt(str[2]); //Numero de jogadores
        int iId = 3, iNome = 4;
        for (int j = 0; j < i; j++) {
            controllerJogo.adicionarJogadores(Integer.parseInt(str[iId].trim()), str[iNome].trim()); //Adicionando jogadores;
            iId += 2;
            iNome += 2;
        }
    }

    private void passarVezR(String[] str) {
        int idJogador = Integer.parseInt(str[0]);

        incrementarJogador();
        controllerJogo.setMinhaVez(isMinhaVez());
    }

    private void pagueUmVizinhoAgoraR(String[] str) {
        int idJogador = Integer.parseInt(str[0]);
        int idVizinho = Integer.parseInt(str[2]);
        int codCarta = Integer.parseInt(str[3]);

        controllerJogo.pagueUmVizinhoAgora(idJogador, idVizinho, codCarta);
    }

    private void dinheiroExtraR(String[] str) {
        int idJogador = Integer.parseInt(str[0].trim());
        int idVizinho = Integer.parseInt(str[2].trim());
        int codCarta = Integer.parseInt(str[3].trim());

        controllerJogo.dinheiroExtra(idJogador, idVizinho, codCarta);
    }

    private void doacaoR(String[] str) {
        int idJogador = Integer.parseInt(str[0]);
        int codigoCarta = Integer.parseInt(str[2]);

        controllerJogo.doacao(idJogador, codigoCarta);
    }

    private void felizAniversarioR(String[] str) {
        int idJogador = Integer.parseInt(str[0]);

        controllerJogo.felizAniversario(idJogador);
    }

    private void sorteGrandeR(String[] str) {
        int idJogador = Integer.parseInt(str[0]);

        controllerJogo.sorteGrande(idJogador);
    }

    private void vaParaFrenteAgoraR(String[] str) {
        int idJogador = Integer.parseInt(str[0]);
        boolean irCompraEnt = false;

        if (str[2].equals("0")) {
            irCompraEnt = true;
        }

        controllerJogo.irParaFrenteAgora(idJogador, irCompraEnt);
    }

    private void contaPagarR(String[] str) {
        int idJogador = Integer.parseInt(str[0]);
        boolean pagarAgora = false;
        int codCarta = Integer.parseInt(str[3]);

        if (str[2].equals("0")) {
            pagarAgora = true;
        }

        controllerJogo.contas(idJogador, pagarAgora, codCarta);
    }

    private void cobrancaMonstroR(String[] str) {
        int idJogador = Integer.parseInt(str[0].trim());
        boolean pagarAgora = false;
        int codCarta = Integer.parseInt(str[3].trim());

        if (str[2].trim().equals("0")) {
            pagarAgora = true;
        }

        controllerJogo.cobrancaMonstro(idJogador, pagarAgora, codCarta);
    }

    private void acheiUmCompradorR(String[] str) {
        int idJogador = Integer.parseInt(str[0]);
        int codCarta = Integer.parseInt(str[2]);

        controllerJogo.achouUmComprador(idJogador, codCarta);
    }

    private void comprasEntretenimentosR(String[] str) {
        int idJogador = Integer.parseInt(str[0]);
        int codigoCarta = Integer.parseInt(str[2]);

        controllerJogo.compraEntretenimento(idJogador, codigoCarta);
    }

    private void participarBolaoEsportesR(String[] str) {
        int idJogador = Integer.parseInt(str[0].trim());
        int numero = Integer.parseInt(str[2].trim());

        controllerJogo.participarBolao(idJogador, numero);

        if (isMinhaVez() && isMinhaVezNaoRegular(str[0].trim())) { //Verifica se foi este cliente que iniciou o bolão e se todos ja escolheram o numero
            /*Todos os jogadores ja escolheram, deve-se chamar o metodo para jogar o dado e ver o ganhador*/
            controllerJogo.getTelaPrincipal().abrirJanelaJogarDadoBolaEsporte();
        } else if (isMinhaVezNaoRegular(str[0].trim())) { //Verifica se é sua vez não regular
            controllerJogo.getTelaPrincipal().escolheParticiparBolaoEsportes(); //Caso seja, abre a janela para escolher se quer participar. 
        }

    }

    private void finalizarBolaoEsportesR(String[] str) {
        int idGanhador = Integer.parseInt(str[2].trim());
        try {
            controllerJogo.finalizarBolao(idGanhador);
        } catch (NenhumJogadorGanhouBolaoException ex) {
            Logger.getLogger(ControllerConexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void concursoBandaArrochaR(String[] str) {
        int idJogador = Integer.parseInt(str[0].trim());
        int resulConc = Integer.parseInt(str[2].trim());

        if (resulConc == 1) { //Verifica se alguem ganhou o concurso;
            controllerJogo.concursoBandaArrocha(idJogador);
            if (isMinhaVez()) { //Verifica se esse cliente é o jogador que caiu na casa, caso seja, ele passa a vez para o proximo.
                passarVez();
            }
        } else if (resulConc == 0 && isMinhaVezNaoRegular(str[0].trim())) { //Se ninguem ganhou e é minha vez.
            controllerJogo.getTelaPrincipal().abrirJanelaBandaArrocha();
        }
    }

    private void finalizarPartidaR(String[] str) {
        int idJogador = Integer.parseInt(str[0]);

        controllerJogo.finalizarPartida(idJogador);
    }

    private void finalizarPartidaGeralR(String[] str) {
        String[] ids = null;
        String[] nomes = null;
        String[] saldos = null;

        int j = 0;

        for (int i = 2; i < str.length; i = i + 3) {
            ids[j] = str[i];
            nomes[j] = str[i + 1];
            saldos[j] = str[i + 2];
            j++;
        }

        controllerJogo.finalGeralPartida(ids, nomes, saldos);
    }

    private void participarConcursoArrocha(String[] str) {

    }

    /**
     * Seleciona o que será feito de acordo com a mensagem recebida pelo grupo.
     *
     * @param str
     */
    private void seletorAcao(String[] str) {
        int aux = Integer.parseInt(str[1].trim());
        switch (aux) {
            case protFinalizarPartidaGeral:
                finalizarPartidaGeralR(str);
                break;
            case protJogada:
                novaJogadaR(str);
                break;
            case protJogadaEspecial:
                break;
            case protPassarVez:
                passarVezR(str);
                break;
            case protMensChat: //Apenas mensagem para chat.
                controllerJogo.adicionarMensChat(str); //Adiciona a mensagem recebida no chat;
                break;
            case 111: //Informando que a sala está cheia.
                controllerJogo.adicionarMensChat("111;SALA CHEIA; #".split(";")); //Adiciona a mensagem no chat;
                cadastrarJogadores(str);
                controllerJogo.setMinhaVez(isMinhaVez());
                break;
            case protPassaVez: //Informa que algum jogador jogou o dado.
                controllerJogo.moverPeao(Integer.parseInt(str[2].trim()), Integer.parseInt(str[1].trim())); //Movendo peao de jogadores
                incrementarJogador();
                controllerJogo.setMinhaVez(isMinhaVez());
                break;
            case protPagarVizinho: //Protocolo para pagar a um vizinho;
                pagueUmVizinhoAgoraR(str);
                break;
            case protDinheiroExtra: //Protocolo dinheiro extra, tomar do vizinho ameaçando;
                dinheiroExtraR(str);
                break;
            case protDoacao:
                doacaoR(str);
                break;
            case protFelizAniversario: //Protocolo feliz aniversario
                felizAniversarioR(str);
                break;
            case sorteGrande: //Informa que que ganhou sorte grande, os clientes devem tirar o dinheiro que tinha do sorte grande.
                sorteGrandeR(str);
                break;
            case protVaParaFrenteAgora: //atualiza o peão do jogador que tirou a carta sorte grande
                vaParaFrenteAgoraR(str);
                break;
            case protContasPagar:
                contaPagarR(str);
                break;
            case protCobrancaMonstro:
                cobrancaMonstroR(str);
                break;
            case protConcBandaArrocha: //Receber mensagem da banda de arrocha.
                concursoBandaArrochaR(str);
                break;
            case protAcheiUmComprador:
                acheiUmCompradorR(str);
                break;
            case protComprasEntretenimentos:
                comprasEntretenimentosR(str);
                break;
            case protParticiparBolaoEsportes:
                participarBolaoEsportesR(str);
                break;
            case protFinalizarPartida:
                finalizarBolaoEsportesR(str);
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

    public void finalizarPartida(String mensagem) {
        enviarMensagemServidor(protFinalizarPartida + ";" + this.endGrupo + ";" + mensagem); //envia informaçoes para o servidor
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
        mens = identificador + ";" + mens;
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

    private boolean isMinhaVezNaoRegular(String idUltQueJogou) {
        int idUltJogador = Integer.parseInt(idUltQueJogou);
        int idProx;
        if (idUltJogador == maxJogadores) {
            idProx = 1;
        } else {
            idProx = ++idUltJogador;
        }
        return idProx == identificador;
    }

}
