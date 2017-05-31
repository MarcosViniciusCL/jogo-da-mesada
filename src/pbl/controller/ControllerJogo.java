/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.controller;

import pbl.exception.AguadarVezException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import pbl.exception.ErroComunicacaoServidorException;
import pbl.exception.ErroNaBuscaDeCartaOuVendedor;
import pbl.exception.NenhumJogadorGanhouBolaoException;
import pbl.exception.NumeroBilheteJaEscolhidoException;
import pbl.model.jogo.BilheteBolao;
import pbl.model.jogo.Carta;
import pbl.model.jogo.Chat;
import pbl.model.jogo.Dado;
import pbl.model.jogo.Jogador;
import pbl.model.jogo.Peao;
import pbl.model.jogo.PilhaCartasComprasEntretenimento;
import pbl.model.jogo.PilhaCartasCorreios;
import pbl.model.jogo.SorteGrande;
import pbl.view.Principal;

/**
 *
 * @author marcos
 */
public class ControllerJogo {

    private static ControllerJogo controllerJogo; //Instancia da propria classe.
    private static ControllerConexao controllerConexao; //Instancia do controller de conexão.

    //ATRIBUTOS DO JOGO
    private boolean minhaVez;//Variavel para informar se é a vez do cliente jogar o dado;
    private final Dado dado; //Dado do jogo
    private String ultimaMens; //Fica salva a ultima mensagem que foi enviada ao grupo para reenvio caso tenha perda.
    private List<Jogador> jogadores;
    private int qntMeses;
    private Jogador jogadorPrincipal;
    private List<BilheteBolao> bilhetesBolao;
    private Chat chat;
    private final SorteGrande sorteGrande;

    //INTERFACE GRAFICA
    private Principal telaPrincipal;

    //CONSTANTES DO JOGO
    private final double cPremio = 5000;
    private final double cBolao = 100;
    private final double cDomingoPraia = 500;
    private final double cMesada = 3500;
    private final double cConcursoBandaArrocha = 1000;
    private final double cFelizAniversario = 100;
    private final double cFlorestaAmazonica = 400;
    private final double cNegocioOcasiao = 100;
    private final double cMaratonaBeneficente = 100;
    private final double cShopping = 1000;
    private final double cLanchonete = 600;

    private ControllerJogo() {
        this.minhaVez = true; //TROCAR DEPOIS, CERTO É FALSE;
        this.sorteGrande = new SorteGrande();
        this.dado = new Dado();
        this.jogadores = new ArrayList<>();
        this.chat = new Chat();
    }

    //****************************************** METODOS RESPONSAVEIS PELA AÇÃO DO JOGO ************************************
    public void criarJogadorPrincipal(int identificador, String nome) {
        Peao p = new Peao();
        this.jogadorPrincipal = new Jogador(identificador, nome, p);
    }

    /**
     * Adiciona os jogadores que estarão na partida.
     *
     * @param identificador
     * @param nome
     */
    public void adicionarJogadores(int identificador, String nome) {
        Peao p = new Peao();
        jogadores.add(new Jogador(identificador, nome, p));
        telaPrincipal.atualizarInformacoesTela();
    }

    /**
     * Joga o dado e retorna o valor que o dado permaneceu.
     *
     * @return valor - Valor final do dado
     * @throws AguadarVezException - Ainda não é a vez do jogador.
     */
    public int jogarDado() throws AguadarVezException {
        int valor;
        if (minhaVez) {
            valor = dado.jogarDado(); //Joga o dado e aguarda o valor sorteado.
        } else {
            throw new AguadarVezException(); //Caso não seja a vez  do jogador lança a exception
        }
        return valor;
    }

    /**
     * Pega uma carta correio aleatoria;
     *
     * @return
     */
    public Carta pegarCartaCorreio() {
        return PilhaCartasCorreios.pegarCarta();
    }
        
    /**
     * Caso o jogador queira a carta, ela vai ser adicionada na pilha;
     * @param c
     */
    public void adicionarCartaCompra(Carta c){
        jogadorPrincipal.addCartaCompEntret(c);
    }

    /**
     * Cria uma nova instancia da lista bolão de esportes
     */
    public void novoBolaoDeEsportes() {
        bilhetesBolao = new ArrayList<>();
    }

    public void compraEntretenimento() {
        telaPrincipal.acaoComprarCarta();
        atualizarTela();
    }

    /**
     * Adiciona um participante para um bolão
     *
     * @param numero numero do bilhete escolhido para o jogador
     * @param idJogador id do jogador que adiquiriu o bilhete
     * @throws NumeroBilheteJaEscolhidoException caso o jogador escolha um
     * numero que já foi escolhido
     */
    public void participarBolao(int numero, int idJogador) throws NumeroBilheteJaEscolhidoException {
        for (BilheteBolao b : bilhetesBolao) { //verifica se o numero já foi escolhido
            if (b.getNumero() == numero) {
                throw new NumeroBilheteJaEscolhidoException(); //exceção para caso o numero já tenha sido escolhido
            }
        }
        bilhetesBolao.add(new BilheteBolao(numero, idJogador)); //adiciona o bilhete a lista
    }

    /**
     * Encerra um bolão de esportes
     *
     * @param numeroSorteado numero do bilhete sorteado
     * @throws NenhumJogadorGanhouBolaoException Caso o numero sorteado não
     * tenha sido escolhido por nenhum jogador
     */
    public void finalizarBolao(int numeroSorteado) throws NenhumJogadorGanhouBolaoException {
        List<Jogador> participantes = new ArrayList<>();
        Jogador ganhador = null;
        for (BilheteBolao b : bilhetesBolao) { //percorre a lista de bilhetes buscando o numero sortedo
            if (b.getNumero() == numeroSorteado) { //caso encontre o numero sorteado
                ganhador = buscarJogador(b.getIdJogador());
            } else { //caso nao adiciona a lista de participantes do bolão
                participantes.add(buscarJogador(b.getIdJogador()));
            }
        }
        if (ganhador == null) {
            throw new NenhumJogadorGanhouBolaoException();
        }
        bolaoEsportes(ganhador, participantes); //realiza o deposito e as transferencias de valores para a conta do ganhador
    }

    /**
     * Metodo em que o jogador vende um propriedade
     *
     * @param codigoCarta
     * @throws ErroNaBuscaDeCartaOuVendedor
     */
    public void achouUmComprador(int codigoCarta) throws ErroNaBuscaDeCartaOuVendedor {
        Carta carta = PilhaCartasComprasEntretenimento.buscarCarta(codigoCarta);

        if (jogadorPrincipal == null || carta == null) {
            throw new ErroNaBuscaDeCartaOuVendedor();
        }
        jogadorPrincipal.removerCartaCompEntret(codigoCarta);
        jogadorPrincipal.getConta().depositar(carta.getValor() * 1.5);
    }

    /**
     * O jogador que caiu na casa recebe 5000 do banco;
     *
     */
    public void premio() {
        jogadorPrincipal.getConta().depositar(cPremio);
        novaMensagemChat("Ganhei $5.000,00 HUHU");
    }

    /**
     * todos os jogadores que participaram do bolão pagam 100 para o ganhador o
     * banco paga 1000 para o ganhador
     *
     * @param ganhador
     * @param participantes
     */
    public void bolaoEsportes(Jogador ganhador, List<Jogador> participantes) {
        for (Jogador j : participantes) { // cada participante paga 100 reais ao jogador que ganhou o bolao
            if (!j.getConta().transferir(ganhador.getConta(), cBolao)) { //se o jogador não tiver saldo suficiente
                j.getConta().realizarEmprestimo(cBolao); //realiza um emprestimo
                j.getConta().transferir(ganhador.getConta(), cBolao);
            }
        }
        ganhador.getConta().depositar(1000); //banco deposita 1000 para o ganhador
    }

    /**
     * Jogador que caiu na casa perde o valor indicado na casa sorte grande de
     * saldo
     *
     */
    public void praiaNoDomingo() {
        if (!jogadorPrincipal.getConta().sacar(cDomingoPraia)) { //verifica se o jogador tem saldo suficiente
            jogadorPrincipal.getConta().realizarEmprestimo(cDomingoPraia); //se não realiza um emprestimo
            jogadorPrincipal.getConta().sacar(cDomingoPraia);
        }
        sorteGrande.adicionarDinheiro(cDomingoPraia); //adiciona o valor a sorte grande
        controllerConexao.doacao(cDomingoPraia);
        novaMensagemChat("Paguei " + cDomingoPraia + " para sorte grande");
    }

    /**
     * Deposita 1000 na conta do jogador que tirou 3 no dado
     *
     * @param jogador jogador ganhador
     */
    public void concursoBandaArrocha(Jogador jogador) {
        jogador.getConta().depositar(cConcursoBandaArrocha);
    }

    /**
     * Transfere 100 de todos os jogadores para o jogador que caiu na casa.
     *
     * @param jogador jogador que caiu na casa
     */
    public void felizAniversario(Jogador jogador) {
        for (Jogador j : jogadores) {
            if (!j.getConta().transferir(jogador.getConta(), cFelizAniversario)) { //se o jogador não possuir saldo suficiente
                jogador.getConta().realizarEmprestimo(cFelizAniversario); //faz emprestimo
                j.getConta().transferir(jogador.getConta(), cFelizAniversario);
            }
        }
    }

    /**
     * Desconta 100 vezes o numero que caiu no dado do jogador que que caiu na
     * casa
     *
     * @param jogador que caiu na casa
     * @param valorDado
     */
    public void vendeNegocioOcasiao(Jogador jogador, int valorDado) {
        if (!jogador.getConta().sacar(valorDado * cNegocioOcasiao)) { //se o jogador não possuir saldo suficiente
            jogador.getConta().realizarEmprestimo(valorDado * cNegocioOcasiao); //realiza emprestimo
            jogador.getConta().sacar(valorDado * cNegocioOcasiao);
        }
    }

    /**
     * tranfere 100 vezes o numero do dado de um jogador para o local sorte
     * grande.
     *
     * @param jogador
     * @param valorDado
     */
    public void maratonaBeneficente(Jogador jogador, int valorDado) {
        if (!jogador.getConta().sacar(valorDado * cMaratonaBeneficente)) {
            jogador.getConta().realizarEmprestimo(valorDado * cMaratonaBeneficente);
            jogador.getConta().sacar(valorDado * cMaratonaBeneficente);
        }
        sorteGrande.adicionarDinheiro(valorDado * 100);
    }

    /**
     * Jogador caiu na casa sorte grande, recebe todo o valor depositado na sua
     * conta
     *
     * @param jogador
     */
    public void sorteGrande(Jogador jogador) {
        jogador.getConta().depositar(sorteGrande.pegarValor());
    }

    public void florestaAmazonica() {
        if (!jogadorPrincipal.getConta().sacar(cFlorestaAmazonica)) { //verifica se o jogador tem saldo para pagar a carta
            jogadorPrincipal.getConta().realizarEmprestimo(cFlorestaAmazonica); //se não realiza um emprestimo
        }
        sorteGrande.adicionarDinheiro(cFlorestaAmazonica);  //deposita o valor no campo sorte grande
        controllerConexao.doacao(cFlorestaAmazonica);
        novaMensagemChat("Adicionei " + cFlorestaAmazonica + " no sorte grande");
    }

    /**
     * Jogador recebe a mesada e paga emprestimo ou os juros se o saldo não for
     * suficiente
     *
     * @param jogador
     */
    public void diaDaMesada(Jogador jogador) {
        jogador.getConta().depositar(cMesada);
        if (jogador.getMes() == qntMeses) {
            jogador.pagarDividasFimJogo();
        } else {
            jogador.pagarDividasFimRodada();
        }
    }

    /**
     * Move o peão de qualquer jogador na partida.
     *
     * @param idJogador
     * @param casas
     */
    public void moverPeao(int idJogador, int casas) {
        Jogador jogador = buscarJogador(idJogador);
        if (jogador != null) {
            jogador.getPeao().andarCasas(casas);
            atualizarTela(); //Informa a tela que é necessário uma atualizar pelo fato que houve alteração de estados dos objetos;
        }
    }

    /**
     * Move o peão do jogador que pertence a esse cliente.
     *
     * @param valor
     */
    public void moverPeao(int valor) {
        this.jogadorPrincipal.getPeao().andarCasas(valor);
        atualizarTela(); //Informa a tela que é necessário uma atualizar pelo fato que houve alteração de estados dos objetos;
        acaoCasa(); //Chama o metodo que será responsavel por executar uma ação de acordo com a casa que o peao caiu
        controllerConexao.dadoJogado(valor); //Informa ao grupo que o dado foi jogado e qual valor caiu. 
    }

    public void pedirEmprestimo(double valorEmprestimo) {
        this.jogadorPrincipal.getConta().realizarEmprestimo(valorEmprestimo);
        atualizarTela();
    }

    public void depositar(double valor) {
        jogadorPrincipal.getConta().depositar(valor);
        atualizarTela();
    }

    public void adicionarSorteGrande(double valor) {
        sorteGrande.adicionarDinheiro(valor);
    }

    public void sacar(double valor) {
        jogadorPrincipal.getConta().sacar(valor);
        atualizarTela();
    }

    /**
     * Veirifica qual casa o jogador se encontra e realiza as funções
     * necessarias
     */
    private void acaoCasa() {
        int casa = this.jogadorPrincipal.getPeao().getPosicao(); //Retorna a posição do piao do jogador;
        switch (casa) {
            case 1: //Correio, 1 carta
                telaPrincipal.abrirJanelaPegarCartaCorreio(1);
                break;
            case 2: //Prêmio! Você ganhou $5.000
                premio();
                break;
            case 3: //Correio, 3 carta
                telaPrincipal.abrirJanelaPegarCartaCorreio(3);
                break;
            case 4: //Compras e entretenimento
                compraEntretenimento();
                break;
            case 5: //Correio, 2 carta
                telaPrincipal.abrirJanelaPegarCartaCorreio(2);
                break;
            case 6: //Bolão de esportes, O banco aplica 1000 e cada jogador aplica 100
                break;
            case 7: //Domingo de praia, pague $500
                praiaNoDomingo();
                break;
            case 8: //Concurso de Banda de Rock, o primeiro jogador que tirar um 3 ganha $1.000
                break;
            case 9: //Você achou um comprador
                break;
            case 10: //Feliz Aniversário, Ganhe $100 de cada jogador e parabens
                break;
            case 11: //Correio, 1 carta
                telaPrincipal.abrirJanelaPegarCartaCorreio(1);
                break;
            case 12: //Compras e entretenimento
                compraEntretenimento();
                break;
            case 13: //Bolão de esportes, O banco aplica 1000 e cada jogador aplica 100
                break;
            case 14: //Ajude a floresta amazonica, doe $400
                florestaAmazonica();
                break;
            case 15: //Compras e entretenimento
                compraEntretenimento();
                break;
            case 16: //Correio, 3 carta
                telaPrincipal.abrirJanelaPegarCartaCorreio(3);
                break;
            case 17: //Você achou um comprador
                break;
            case 18: //Lanchonete, pague $600
                lanconete();
                break;
            case 19: //Correio, 1 carta
                telaPrincipal.abrirJanelaPegarCartaCorreio(1);
                break;
            case 20: //Bolão de esportes, o banco entra com $1.000 cada um entra com $100
                break;
            case 21: //Negócios de ocasião seu por apenas $100 mais X nº dado
                break;
            case 22: //Correio, 1 carta
                telaPrincipal.abrirJanelaPegarCartaCorreio(1);
                break;
            case 23: //Você achou um comprador
                break;
            case 24: //Correio, 2 carta
                telaPrincipal.abrirJanelaPegarCartaCorreio(2);
                break;
            case 25: //Compras e entretenimento
                compraEntretenimento();
                break;
            case 26: //Você achou um comprador
                break;
            case 27: //Bolão de esportes, o banco entra com $1.000 cada um entra com $100
                break;
            case 28: //Compras no shopping
                break;
            case 29: //Você achou um comprador
                break;
            case 30: //Maratona beneficiente, Os outros jogadores doam $100 X nº no dado
                break;
            case 31: //Oba!!! Dia da Mesada, receba $3.500 
                break;
            default:
                break;
        }
    }
    //*************************************** CASAS FORA DO MANUAL

    /**
     * O jogador que caiu na casa paga 500 de conta do shopping
     *
     * @param jogador jogador que caiu na casa
     */
    public void comprasShopping(Jogador jogador) {
        if (!jogador.getConta().sacar(cShopping)) { //verifica se o jogador tem saldo para pagar
            jogador.getConta().realizarEmprestimo(cShopping);
            jogador.getConta().sacar(cShopping);
        }
    }

    /**
     * O jogador que caiu na casa paga de conta da lanchonete
     *
     */
    public void lanconete() {
        if (!jogadorPrincipal.getConta().sacar(cLanchonete)) { //verifica se o jogador tem saldo para pagar
            jogadorPrincipal.getConta().realizarEmprestimo(cLanchonete);
            jogadorPrincipal.getConta().sacar(cLanchonete);
        }
        novaMensagemChat("LANCHONETE: PAGUEI " + cLanchonete + "");
    }

    //*************************************** METODOS DA CARTA CORREIO
    /**
     * metodo paga caso o jogador retire um carta do tipo conta do monte
     *
     * @param jogador jogador que recebeu a conta
     * @param pagarAgora verifica se o jogador solicitou o pagamento imediato
     * @param codCarta codigo da carta que o jogador sortou
     */
    public void contas(Jogador jogador, boolean pagarAgora, int codCarta) {
        Carta c = PilhaCartasCorreios.buscarCarta(codCarta);
        if (pagarAgora) { //se o jogador desejou pagar na hora
            if (!jogador.getConta().sacar(c.getValor())) { //verifica se o jogador tem saldo
                jogador.getConta().realizarEmprestimo(c.getValor()); //se não realiza um emprestimo
                jogador.getConta().sacar(c.getValor()); //saca o valor
            }
        } else { //caso o jogador prefira pagar depois
            jogador.addCartaCorreio(c);
        }
    }

    /**
     * transfere o valor contido na carta para um vizinho da escolha do jogador
     * que caiu na casa
     *
     * @param idVizinho
     * @param codCarta codigo da carta sorteada
     */
    public void dinheiroExtra(int idVizinho, int codCarta) {
        Jogador vizinho = buscarJogador(idVizinho);
        Carta c = PilhaCartasCorreios.buscarCarta(codCarta);
        if (!vizinho.getConta().transferir(jogadorPrincipal.getConta(), c.getValor())) { //verifica se o vizinho tem saldo para transferir para o vizinho
            vizinho.getConta().realizarEmprestimo(c.getValor()); //caso não realiza um emprestimo
            vizinho.getConta().transferir(jogadorPrincipal.getConta(), c.getValor()); //transfere o valor
        }
        controllerConexao.dinheiroExtra(idVizinho, c.getValor());
        novaMensagemChat("\"DINHEIRO EXTRA\" PEGUEI " + c.getValor() + " DE " + vizinho.getNome());
    }

    /**
     *
     * @param idVizinho
     * @param codCarta
     */
    public void pagueUmVizinhoAgora(int idVizinho, int codCarta) {
        Jogador vizinho = buscarJogador(idVizinho);
        Carta c = PilhaCartasCorreios.buscarCarta(codCarta);
        if (!jogadorPrincipal.getConta().transferir(vizinho.getConta(), c.getValor())) { //verifica se o jogador tem saldo para transferir para o vizinho
            jogadorPrincipal.getConta().realizarEmprestimo(c.getValor()); //caso não realiza um emprestimo
            jogadorPrincipal.getConta().transferir(vizinho.getConta(), c.getValor()); //transfere o valor
        }
        controllerConexao.pagueUmVizinhoAgora(idVizinho, c.getValor()); //Enviando valor para o vizinho;
        novaMensagemChat("\"PAGUE A UM VIZINHO\" PAGUEI " + c.getValor() + " PARA " + vizinho.getNome()); //Adiciona a informação no chat
    }

    /**
     * Debita da conta do jogador que sorteou essa carta o valor da carta
     *
     * @param codCarta codigo da carta sorteada
     */
    public void doacao(int codCarta) {
        Carta c = PilhaCartasCorreios.buscarCarta(codCarta);
        if (!jogadorPrincipal.getConta().sacar(c.getValor())) { //verifica se o jogador tem saldo para pagar a carta
            jogadorPrincipal.getConta().realizarEmprestimo(c.getValor()); //se não realiza um emprestimo
        }
        sorteGrande.adicionarDinheiro(c.getValor());  //deposita o valor no campo sorte grande
        controllerConexao.doacao(c.getValor());
        novaMensagemChat("Adicionei " + c.getValor() + " no sorte grande");
    }

    public void cobrancaMonstro(Jogador jogador, boolean pagarAgora, int codCarta) {
        Carta c = PilhaCartasCorreios.buscarCarta(codCarta);
        if (pagarAgora) { //verifica se o jogador deseja pagar a carta agora
            if (!jogador.getConta().sacar((c.getValor() * 1.1))) { //verifica se o jogador tem saldo suficiente
                jogador.getConta().realizarEmprestimo(c.getValor()); //se não realiza um empretimo
                jogador.getConta().sacar((c.getValor() * 1.1)); //saca o valor
            }
        } else { //caso o jogador decida pagar depois
            jogador.addCartaCorreio(c);
        }
    }

    public void irParaFrenteAgora(Jogador jogador, boolean irComprasEntretenimento) {
        if (irComprasEntretenimento) {
            jogador.getPeao().irParaProximaCasaComprasEntretenimento();
        } else {
            jogador.getPeao().irParaProximaCasaAcheiComprador();
        }
        atualizarTela();
    }

    //****************************************** METODOS RESPONSAVEIS PELA COMUNICAÇÃO ************************************
    /**
     * Reenvia a ultima mensagem que foi enviada ao grupo multcast.
     *
     * @throws java.io.IOException
     */
    public void reenviarUltMensGRP() throws IOException {
        controllerConexao.enviarMensagemGRP(ultimaMens);
    }

    /**
     * Solicita ao servidor para entrar em um sala.
     *
     * @param nome
     * @param quantJogadores
     * @param quantMeses
     * @throws ErroComunicacaoServidorException
     * @throws IOException
     */
    public void entrarSala(String nome, int quantJogadores, int quantMeses) throws ErroComunicacaoServidorException, IOException {
        controllerConexao.entraSala(nome, quantJogadores, quantMeses);
    }

    /**
     * Metodo chamado quando o jogador manda uma mensagem no grupo.
     *
     * @param mens
     */
    public void novaMensagemChat(String mens) {
        controllerConexao.novaMensChat(mens);
    }

    /**
     * Metodo chamado quando chega uma nova mensagem pelo grupo
     *
     * @param mens
     */
    public void adicionarMensChat(String[] mens) {
        String nome, mensagem;
        if (mens[2].trim().length() <= 0) { //Testa se a mensagem veio sem a identificação;
            nome = "Anônimo disse: ";
            mensagem = mens[1];
        } else if (!mens[2].trim().equals("#")) { //Testa se a mensagem foi enviada pelo servidor, caso não, é executado
            if (this.jogadorPrincipal.getIdentificacao() == Integer.parseInt(mens[2].trim())) { //Testa se o proprio jogador mandou a mensagem
                nome = "Você disse: ";
                mensagem = mens[1];
            } else { //Caso não tenha sido o proprio jogador, ele procura quem mandou a mensagem.
                Jogador j = buscarJogador(Integer.parseInt(mens[2].trim()));
                if (j != null) {
                    nome = j.getNome() + " disse: ";
                    mensagem = mens[1];
                } else {
                    nome = "Anônimo disse: ";
                    mensagem = mens[1];
                }
            }
        } else { //Se foi enviada pelo servidor
            nome = "Servidor disse: ";
            mensagem = mens[1];
        }
        chat.novaMensagem(nome + mensagem);
        telaPrincipal.atualizarInformacoesTela();
    }

    public Chat getChat() {
        return this.chat;
    }

    //****************************************** METODOS DO CONTROLLER ************************************
    public static ControllerJogo getInstance() {
        if (controllerJogo == null) {
            controllerJogo = new ControllerJogo();
            controllerConexao = new ControllerConexao(controllerJogo);
        }
        return controllerJogo;
    }

    private void atualizarTela() {
        telaPrincipal.atualizarInformacoesTela();
    }

    public void setTelaPrincipal(Principal frame) {
        this.telaPrincipal = frame;
    }

    /**
     * Retorna a lista de jogadores
     *
     * @return
     */
    public List<Jogador> getJogadores() {
        return this.jogadores;
    }

    public List<Peao> getPeoes() {
        List l = new ArrayList();
        for (Jogador jogador : jogadores) {
            l.add(jogador.getPeao());
        }
        return l;
    }

    public boolean isMinhaVez() {
        return minhaVez;
    }

    public void setMinhaVez(boolean minhaVez) {
        this.minhaVez = minhaVez;
        telaPrincipal.atualizarInformacoesTela();
    }

    /**
     * Retorna o jogador principal(METODO TEMPORARIO)
     *
     * @return
     */
    public Jogador getJogador() {
        return this.jogadorPrincipal;
    }

    /**
     * Busca um jogador pelo id
     *
     * @param idJogador
     * @return
     */
    public Jogador buscarJogador(int idJogador) {
        for (Jogador j : jogadores) {
            if (j.getIdentificacao() == idJogador) {
                return j;
            }
        }
        return null;
    }

    public double getValorSorteGrande() {
        return sorteGrande.getValor();
    }
    

}
