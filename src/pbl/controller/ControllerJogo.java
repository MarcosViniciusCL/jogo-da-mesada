/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.controller;

import pbl.exception.DinheiroInsuficienteException;
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
    private List<Jogador> jogadoresFinalizaram;
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
        Peao p = new Peao(qntMeses);
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
        Jogador j = new Jogador(identificador, nome, p);
        jogadores.add(j);
        if (j.getIdentificacao() == jogadorPrincipal.getIdentificacao()) {
            jogadorPrincipal = j;
        }
        telaPrincipal.atualizarInformacoesTela();
    }

    /**
     * Joga o dado e retorna o valor que o dado permaneceu.
     *
     * @return valor - Valor final do dado
     * @throws AguadarVezException - Ainda não é a vez do jogador.
     */
    public int jogarDado() {
        return dado.jogarDado(); //Joga o dado e aguarda o valor sorteado.
    }

    public boolean verificarSeJogadorPrincipalTemSaldo(double valor) {

        return jogadorPrincipal.getConta().consultarSaldo() >= valor;
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
     * Salva a carta que o usuario comprou.
     *
     * @param c
     * @throws pbl.exception.DinheiroInsuficienteException
     */
    public void compraEntretenimento(int idJogador, int codigoCarta) {
        Carta c = PilhaCartasComprasEntretenimento.buscarCarta(codigoCarta);
        Jogador jogador = buscarJogador(idJogador);

        jogador.getConta().sacar(c.getValor());
        jogador.addCartaCompEntret(c);
        atualizarTela();
        novaMensagemConsole(jogador, "Fiz um otimo negocio comprei " + c.getNome());
    }

    /**
     * Cria uma nova instancia da lista bolão de esportes
     */
    public void novoBolaoDeEsportes() {
        bilhetesBolao = new ArrayList<>();
    }

    /**
     * Procura um bilhete do bolão de esportes pelo numero
     *
     * @param numero do bilhete
     * @return
     */
    public BilheteBolao buscarBilheteBolao(int numero) {
        for (BilheteBolao b : bilhetesBolao) { //procura o numero do bilhete
            if (b.getNumero() == numero) {
                return b;
            }
        }
        return null;
    }

    /**
     * Adiciona um participante para um bolão
     *
     * @param numero numero do bilhete escolhido para o jogador
     * @param idJogador id do jogador que adiquiriu o bilhete
     * @throws NumeroBilheteJaEscolhidoException caso o jogador escolha um
     * numero que já foi escolhido
     */
    public void participarBolao(int idJogador, int numero) {
        Jogador jogador = buscarJogador(idJogador);
        bilhetesBolao.add(new BilheteBolao(numero, idJogador)); //adiciona o bilhete a lista
        novaMensagemConsole(jogador, "Escolhi o numero " + numero);
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
        BilheteBolao premiado = buscarBilheteBolao(numeroSorteado); //busca o bilhete premiado

        for (BilheteBolao b : bilhetesBolao) { //cria a lista de participante e busca o ganhador
            Jogador jogador = buscarJogador(b.getIdJogador());
            if (!b.equals(premiado)) { //se o bilhete não foi premiado
                participantes.add(jogador);  //adiciona o jogador a lista de participantes
            } else {
                ganhador = jogador; //caso seja premiado seta o ganhador
            }
        }

        bolaoEsportes(ganhador, participantes); //realiza o deposito e as transferencias de valores para a conta do ganhador
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
        novaMensagemConsole(ganhador, "Ganhei o bolão HUHU");
    }

    /**
     * Metodo em que o jogador vende um propriedade
     *
     * @param idJogador
     * @param codigoCarta
     * @throws ErroNaBuscaDeCartaOuVendedor
     */
    public void achouUmComprador(int idJogador, int codigoCarta) {
        Jogador jogador = buscarJogador(idJogador);
        Carta carta = PilhaCartasComprasEntretenimento.buscarCarta(codigoCarta);

        jogador.removerCartaCompEntret(codigoCarta);
        jogador.getConta().depositar(carta.getValor() * 1.5);
        novaMensagemConsole(jogador, "Fiz um otimo negocio vendi " + carta.getNome());
    }

    /**
     * O jogador que caiu na casa recebe 5000 do banco;
     *
     */
    public void premio(int idJogador) {
        Jogador jogador = buscarJogador(idJogador);
        jogador.getConta().depositar(cPremio);
        novaMensagemConsole(jogador, "Ganhei $5.000,00 HUHU");
    }

    /**
     * Jogador que caiu na casa perde o valor indicado na casa sorte grande de
     * saldo
     *
     */
    public void praiaNoDomingo(int idJogador) {
        Jogador jogador = buscarJogador(idJogador);
        if (!jogador.getConta().sacar(cDomingoPraia)) { //verifica se o jogador tem saldo suficiente
            jogador.getConta().realizarEmprestimo(cDomingoPraia); //se não realiza um emprestimo
            jogador.getConta().sacar(cDomingoPraia);
            novaMensagemConsole(jogador, "Fiz um emprestimo no valor de " + cDomingoPraia);
        }
        sorteGrande.adicionarDinheiro(cDomingoPraia); //adiciona o valor a sorte grande
        novaMensagemConsole(jogador, "Após a diversão a conta, paguei $" + cDomingoPraia);
    }

    /**
     * Adiciona o dinheiro no jogador que ganhou o concurso da banda de arrocha.
     *
     * @param idJogador
     */
    public void concursoBandaArrocha(int idJogador) {
        Jogador jogador = buscarJogador(idJogador);
        jogador.getConta().depositar(cConcursoBandaArrocha);
        novaMensagemConsole(jogador, "Ganhei $" + cConcursoBandaArrocha + " no concurso da banda de arrocha.");
    }

    /**
     * Transfere 100 de todos os jogadores para o jogador que caiu na casa.
     *
     */
    public void felizAniversario(int idJogador) {
        Jogador jogador = buscarJogador(idJogador);
        for (Jogador j : jogadores) {
            if (!j.getConta().transferir(jogador.getConta(), cFelizAniversario)) { //se o jogador não possuir saldo suficiente
                jogador.getConta().realizarEmprestimo(cFelizAniversario); //faz emprestimo
                j.getConta().transferir(jogador.getConta(), cFelizAniversario);
            }
        }
        novaMensagemConsole(jogador, "todos me deram $" + cFelizAniversario + ", obrigado.");
    }

    /**
     * Desconta 100 vezes o numero que caiu no dado do jogador que que caiu na
     * casa
     *
     * @param jogador que caiu na casa
     * @param valorDado
     */
    public void vendeNegocioOcasiao(int idJogador, int valorDado) {
        Jogador jogador = buscarJogador(idJogador);

        if (!jogador.getConta().sacar(valorDado * cNegocioOcasiao)) { //se o jogador não possuir saldo suficiente
            jogador.getConta().realizarEmprestimo(valorDado * cNegocioOcasiao); //realiza emprestimo
            jogador.getConta().sacar(valorDado * cNegocioOcasiao);
            novaMensagemConsole(jogador, "Realizei um emprestimo de $" + cNegocioOcasiao);
        }
        novaMensagemConsole(jogador, "Paguei " + cNegocioOcasiao + ", negócio de ocasião.");
    }

    /**
     * tranfere 100 vezes o numero do dado de um jogador para o local sorte
     * grande.
     *
     * @param jogador
     * @param valorDado
     */
    public void maratonaBeneficente(int idJogador, int valorDado) {
        Jogador jogador = buscarJogador(idJogador);

        if (!jogador.getConta().sacar(valorDado * cMaratonaBeneficente)) {
            jogador.getConta().realizarEmprestimo(valorDado * cMaratonaBeneficente);
            jogador.getConta().sacar(valorDado * cMaratonaBeneficente);
            novaMensagemConsole(jogador, "Realizei um emprestimo no valor de $" + (valorDado * cMaratonaBeneficente));
        }
        novaMensagemConsole(jogador, "Hoje é o dia de fazer uma boa ação amigos, obrigado pela contribuição, será destinada ao sorte grande de: $" + (valorDado * cMaratonaBeneficente));
        sorteGrande.adicionarDinheiro(valorDado * 100);
    }

    /**
     * Jogador caiu na casa sorte grande, recebe todo o valor depositado na sua
     * conta
     *
     * @param jogador
     */
    public void sorteGrande(int idJogador) {
        Jogador jogador = buscarJogador(idJogador);
        jogador.getConta().depositar(sorteGrande.pegarValor());
        novaMensagemConsole(jogador, "Meu dia de sorte, conseguir sorte grande");
    }

    public void florestaAmazonica(int idJogador) {
        Jogador jogador = buscarJogador(idJogador);

        if (!jogador.getConta().sacar(cFlorestaAmazonica)) { //verifica se o jogador tem saldo para pagar a carta
            jogador.getConta().realizarEmprestimo(cFlorestaAmazonica); //se não realiza um emprestimo
        }
        sorteGrande.adicionarDinheiro(cFlorestaAmazonica);  //deposita o valor no campo sorte grande
        novaMensagemConsole(jogador, "Adicionei " + cFlorestaAmazonica + " no sorte grande");
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
    public void moverPeao(int idJogador, int valorDado) {
        Jogador jogador = buscarJogador(idJogador);

        jogador.getPeao().andarCasas(valorDado); //jogador anda o numero de casas correspondente ao valor do dado

        if (valorDado == 6 && sorteGrande.temDinheiro()) { //Ganhou sorte grande
            sorteGrande(jogador.getIdentificacao());
        }

        atualizarTela();

        if (jogador.getIdentificacao() == jogadorPrincipal.getIdentificacao()) { //se o jogador for eu
            acaoCasa(valorDado);
            if (enviarPassaVez(jogador.getPeao().getPosicao())) { //Verifica se é uma jogada especial.
                controllerConexao.passarVez();
            }
        } else { //demais jogadores
            acaoCasaOutroJogador(jogador, valorDado);
        }

        atualizarTela(); //Informa a tela que é necessário uma atualizar pelo fato que houve alteração de estados dos objetos;
    }

    private boolean enviarPassaVez(int posicao) {
        if (posicao == 6 || posicao == 13 || posicao == 20 || posicao == 27) { //Especial, bolao de esporte.
            return false;
        } else if (posicao == 8) { //Especial, Banda de arrocha
            return false;
        }
        return true;
    }

    /**
     * Move o peão do jogador que pertence a esse cliente.
     *
     * @param valorDado
     */
    /*public void moverPeao(int valorDado) {
        if (valorDado == 6 && sorteGrande.temDinheiro()) { //Ganhou sorte grande
            sorteGrande(jogadorPrincipal.getIdentificacao());
        }
        this.jogadorPrincipal.getPeao().andarCasas(valorDado);
        atualizarTela(); //Informa a tela que é necessário uma atualizar pelo fato que houve alteração de estados dos objetos;
        acaoCasa(valorDado); //Chama o metodo que será responsavel por executar uma ação de acordo com a casa que o peao caiu
        if (jogadorPrincipal.getPeao().getPosicao() != 8) { //Caso o jogador tenha caido em concurso de banda  de arrocha não deve enviar a mensagem para passar a vez.
            controllerConexao.passaVez(valorDado); //Informa ao grupo que o dado foi jogado e qual valor caiu. 
        }
    }*/
    /**
     * Metodo executado quando o jogador pede um emprestimo.
     *
     * @param valorEmprestimo
     */
    public void pedirEmprestimo(int idJogador, double valorEmprestimo) {
        Jogador jogador = buscarJogador(idJogador);
        jogador.getConta().realizarEmprestimo(valorEmprestimo);
        novaMensagemConsole(jogador, "Realizei um emprestimo no valor de $: " + valorEmprestimo);
    }

    /**
     * Metodo para depositar na conta do jogador.
     *
     * @param valor
     */
    public void depositar(double valor) {
        jogadorPrincipal.getConta().depositar(valor);
        atualizarTela();
    }

    /**
     * Metodo usado pelo controller conexão para adiciona valores no sorte
     * grande.
     *
     * @param valor
     */
    public void adicionarSorteGrande(double valor) {
        sorteGrande.adicionarDinheiro(valor);
    }

    public void zerarSorteGrande() {
        sorteGrande.pegarValor();
    }

    public void finalizarPartida(int idJogador) {
        Jogador jogador = buscarJogador(idJogador);

        jogadoresFinalizaram.add(jogador); //adiciona o jogador a lista dos que finalizaram

        novaMensagemConsole(jogador, "Cheguei ao final, mas ainda posso participar do bolão de esportes e concurso de banda de arrocha");

        if (jogadoresFinalizaram.size() == jogadores.size()) { //se todos os jogadores estiverem finalizado
            String mensagemFinalServer = "";
            for (Jogador j : jogadores) { //pega o id, nome e saldo de todos os jogadores
                mensagemFinalServer += j.getIdentificacao() + ";" + j.getNome() + ";" + j.getSaldoFinal() + ";";
            }
            controllerConexao.finalizarPartida(mensagemFinalServer); //envia as informações para o servidor
        }
    }

    public void finalGeralPartida(String[] ids, String[] nomes, String[] saldos) {

    }

    /**
     * Veirifica qual casa o jogador se encontra e realiza as funções
     * necessarias
     */
    private void acaoCasa(int valorDado) {
        int casa = this.jogadorPrincipal.getPeao().getPosicao(); //Retorna a posição do piao do jogador;
        switch (casa) {
            case 1: //Correio, 1 carta
                telaPrincipal.abrirJanelaPegarCartaCorreio(1);
                break;
            case 2: //Prêmio! Você ganhou $5.000
                premio(jogadorPrincipal.getIdentificacao());
                break;
            case 3: //Correio, 3 carta
                telaPrincipal.abrirJanelaPegarCartaCorreio(3);
                break;
            case 4: //Compras e entretenimento
                telaPrincipal.acaoComprarCarta();
                break;
            case 5: //Correio, 2 carta
                telaPrincipal.abrirJanelaPegarCartaCorreio(2);
                break;
            case 6: //Bolão de esportes, O banco aplica 1000 e cada jogador aplica 100
                novoBolaoDeEsportes();
                telaPrincipal.participarBolaoEsportes();
                break;
            case 7: //Domingo de praia, pague $500
                praiaNoDomingo(jogadorPrincipal.getIdentificacao());
                break;
            case 8: //Concurso de Banda de Arrocha, o primeiro jogador que tirar um 3 ganha $1.000
                telaPrincipal.abrirJanelaBandaArrocha();
                break;
            case 9: //Você achou um comprador
                telaPrincipal.abrirJanelaVendeCartaCE();
                break;
            case 10: //Feliz Aniversário, Ganhe $100 de cada jogador e parabens
                felizAniversario(jogadorPrincipal.getIdentificacao());
                break;
            case 11: //Correio, 1 carta
                telaPrincipal.abrirJanelaPegarCartaCorreio(1);
                break;
            case 12: //Compras e entretenimento
                telaPrincipal.acaoComprarCarta();
                break;
            case 13: //Bolão de esportes, O banco aplica 1000 e cada jogador aplica 100
                novoBolaoDeEsportes();
                telaPrincipal.participarBolaoEsportes();
                break;
            case 14: //Ajude a floresta amazonica, doe $400
                florestaAmazonica(jogadorPrincipal.getIdentificacao());
                break;
            case 15: //Compras e entretenimento
                telaPrincipal.acaoComprarCarta();
                break;
            case 16: //Correio, 3 carta
                telaPrincipal.abrirJanelaPegarCartaCorreio(3);
                break;
            case 17: //Você achou um comprador
                telaPrincipal.abrirJanelaVendeCartaCE();
                break;
            case 18: //Lanchonete, pague $600
                lanchonete(jogadorPrincipal.getIdentificacao());
                break;
            case 19: //Correio, 1 carta
                telaPrincipal.abrirJanelaPegarCartaCorreio(1);
                break;
            case 20: //Bolão de esportes, o banco entra com $1.000 cada um entra com $100
                novoBolaoDeEsportes();
                telaPrincipal.participarBolaoEsportes();
                break;
            case 21: //Negócios de ocasião seu por apenas $100 mais X nº dado
                vendeNegocioOcasiao(jogadorPrincipal.getIdentificacao(), valorDado);
                break;
            case 22: //Correio, 1 carta
                telaPrincipal.abrirJanelaPegarCartaCorreio(1);
                break;
            case 23: //Você achou um comprador
                telaPrincipal.abrirJanelaVendeCartaCE();
                break;
            case 24: //Correio, 2 carta
                telaPrincipal.abrirJanelaPegarCartaCorreio(2);
                break;
            case 25: //Compras e entretenimento
                telaPrincipal.acaoComprarCarta();
                break;
            case 26: //Você achou um comprador
                telaPrincipal.abrirJanelaVendeCartaCE();
                break;
            case 27: //Bolão de esportes, o banco entra com $1.000 cada um entra com $100
                novoBolaoDeEsportes();
                telaPrincipal.participarBolaoEsportes();
                break;
            case 28: //Compras no shopping
                comprasShopping(jogadorPrincipal.getIdentificacao());
                break;
            case 29: //Você achou um comprador
                telaPrincipal.abrirJanelaVendeCartaCE();
                break;
            case 30: //Maratona beneficiente, Os outros jogadores doam $100 X nº no dado
                maratonaBeneficente(jogadorPrincipal.getIdentificacao(), valorDado);
                break;
            case 31: //Oba!!! Dia da Mesada, receba $3.500
                diaDaMesada(jogadorPrincipal);
                controllerConexao.finalizarPartida();
                break;
            default:
                break;
        }
    }

    private void acaoCasaOutroJogador(Jogador jogador, int valorDado) {
        int posicao = jogador.getPeao().getPosicao();

        switch (posicao) {
            case 2: //Prêmio! Você ganhou $5.000
                premio(jogador.getIdentificacao());
                break;
            case 6: //Bolão de esportes, O banco aplica 1000 e cada jogador aplica 100
                novoBolaoDeEsportes();
                break;
            case 7: //Domingo de praia, pague $500
                praiaNoDomingo(jogador.getIdentificacao());
                break;
            case 8: //Concurso de Banda de Arrocha, o primeiro jogador que tirar um 3 ganha $1.000
                telaPrincipal.abrirJanelaBandaArrocha();
                break;
            case 10: //Feliz Aniversário, Ganhe $100 de cada jogador e parabens
                felizAniversario(jogador.getIdentificacao());
                break;
            case 13: //Bolão de esportes, O banco aplica 1000 e cada jogador aplica 100
                novoBolaoDeEsportes();
                break;
            case 14: //Ajude a floresta amazonica, doe $400
                florestaAmazonica(jogador.getIdentificacao());
                break;
            case 18: //Lanchonete, pague $600
                lanchonete(jogador.getIdentificacao());
                break;
            case 20: //Bolão de esportes, o banco entra com $1.000 cada um entra com $100
                novoBolaoDeEsportes();
                break;
            case 21: //Negócios de ocasião seu por apenas $100 mais X nº dado
                vendeNegocioOcasiao(jogador.getIdentificacao(), valorDado);
                break;
            case 27: //Bolão de esportes, o banco entra com $1.000 cada um entra com $100
                novoBolaoDeEsportes();
                break;
            case 28: //Compras no shopping
                comprasShopping(jogador.getIdentificacao());
                break;
            case 30: //Maratona beneficiente, Os outros jogadores doam $100 X nº no dado
                maratonaBeneficente(jogador.getIdentificacao(), valorDado);
                break;
            case 31: //Oba!!! Dia da Mesada, receba $3.500
                diaDaMesada(jogador);
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
    public void comprasShopping(int idJogador) {
        Jogador jogador = buscarJogador(idJogador);

        if (!jogador.getConta().sacar(cShopping)) { //verifica se o jogador tem saldo para pagar
            jogador.getConta().realizarEmprestimo(cShopping);
            jogador.getConta().sacar(cShopping);
        }
        novaMensagemConsole(jogador, "Dia de compras no shopping gastei: " + cShopping);
    }

    /**
     * O jogador que caiu na casa paga de conta da lanchonete
     *
     */
    public void lanchonete(int idJogador) {
        Jogador jogador = buscarJogador(idJogador);

        if (!jogador.getConta().sacar(cLanchonete)) { //verifica se o jogador tem saldo para pagar
            jogador.getConta().realizarEmprestimo(cLanchonete);
            jogador.getConta().sacar(cLanchonete);
        }
        novaMensagemConsole(jogador, "Paguei " + cLanchonete + " na lanchonete.");
    }

    //*************************************** METODOS DA CARTA CORREIO
    /**
     * metodo paga caso o jogador retire um carta do tipo conta do monte
     *
     * @param jogador jogador que recebeu a conta
     * @param pagarAgora verifica se o jogador solicitou o pagamento imediato
     * @param codCarta codigo da carta que o jogador sortou
     */
    public void contas(int idJogador, boolean pagarAgora, int codCarta) {
        Jogador jogador = buscarJogador(idJogador);
        Carta c = PilhaCartasCorreios.buscarCarta(codCarta);
        if (pagarAgora) { //se o jogador desejou pagar na hora
            if (!jogador.getConta().sacar(c.getValor())) { //verifica se o jogador tem saldo
                jogador.getConta().realizarEmprestimo(c.getValor()); //se não realiza um emprestimo
                jogador.getConta().sacar(c.getValor()); //saca o valor
                novaMensagemConsole(jogador, "Realizei um emprestimo de: $" + c.getValor());
            }
            novaMensagemConsole(jogador, "Paguei a conta: " + c.getDescrição());
        } else { //caso o jogador prefira pagar depois
            jogador.addCartaCorreio(c);
            novaMensagemConsole(jogador, "Tenho uma nova conta de : " + c.getDescrição() + ", no valor de: $" + c.getValor());
        }
    }

    /**
     * transfere o valor contido na carta para um vizinho da escolha do jogador
     * que caiu na casa
     *
     * @param idVizinho
     * @param codCarta codigo da carta sorteada
     */
    public void dinheiroExtra(int idJogador, int idVizinho, int codCarta) {
        Jogador jogador = buscarJogador(idJogador);
        Jogador vizinho = buscarJogador(idVizinho);
        Carta c = PilhaCartasCorreios.buscarCarta(codCarta);

        if (!vizinho.getConta().transferir(jogador.getConta(), c.getValor())) { //verifica se o vizinho tem saldo para transferir para o vizinho
            vizinho.getConta().realizarEmprestimo(c.getValor()); //caso não realiza um emprestimo
            vizinho.getConta().transferir(jogador.getConta(), c.getValor()); //transfere o valor
            novaMensagemConsole(vizinho, "Realizei um emprestimo no valor de $: " + c.getValor());
        }
        novaMensagemConsole(vizinho, "Dinheiro extra, paguei $" + c.getValor() + "para o vizinho" + jogador.getNome());
    }

    /**
     *
     * @param idVizinho
     * @param codCarta
     */
    public void pagueUmVizinhoAgora(int idJogador, int idVizinho, int codCarta) {
        Jogador vizinho = buscarJogador(idVizinho);
        Jogador jogador = buscarJogador(idJogador);

        Carta c = PilhaCartasCorreios.buscarCarta(codCarta);
        if (!jogador.getConta().transferir(vizinho.getConta(), c.getValor())) { //verifica se o jogador tem saldo para transferir para o vizinho
            jogador.getConta().realizarEmprestimo(c.getValor()); //caso não realiza um emprestimo
            jogador.getConta().transferir(vizinho.getConta(), c.getValor()); //transfere o valor
            novaMensagemConsole(jogador, "Realizei um emprestimo no valor $: " + c.getValor());
        }
        novaMensagemChat("Paguei " + c.getValor() + " para o vizinho" + vizinho.getNome()); //Adiciona a informação no chat
    }

    /**
     * Debita da conta do jogador que sorteou essa carta o valor da carta
     *
     * @param codCarta codigo da carta sorteada
     */
    public void doacao(int idJogador, int codCarta) {
        Jogador jogador = buscarJogador(idJogador);
        Carta c = PilhaCartasCorreios.buscarCarta(codCarta);

        if (!jogador.getConta().sacar(c.getValor())) { //verifica se o jogador tem saldo para pagar a carta
            jogador.getConta().realizarEmprestimo(c.getValor()); //se não realiza um emprestimo
            novaMensagemConsole(jogador, "Realizei um emprestimo no valor de $: " + c.getValor());
        }
        sorteGrande.adicionarDinheiro(c.getValor());  //deposita o valor no campo sorte grande
        novaMensagemConsole(jogador, "Adicionei " + c.getValor() + " no sorte grande");
    }

    public void cobrancaMonstro(int idJogador, boolean pagarAgora, int codCarta) {
        Jogador jogador = buscarJogador(idJogador);
        Carta c = PilhaCartasCorreios.buscarCarta(codCarta);

        if (pagarAgora) { //verifica se o jogador deseja pagar a carta agora
            if (!jogador.getConta().sacar((c.getValor() * 1.1))) { //verifica se o jogador tem saldo suficiente
                jogador.getConta().realizarEmprestimo(c.getValor()); //se não realiza um empretimo
                jogador.getConta().sacar((c.getValor() * 1.1)); //saca o valor
                novaMensagemConsole(jogador, "Realizei um emprestimo de: $" + c.getValor());
            }
            novaMensagemConsole(jogador, "Paguei a cobrança monstro: " + c.getDescrição() + ", no valor de: $" + c.getValor()
                    + "mais: $" + (c.getValor() * 0.1) + "referente aos juros");
        } else { //caso o jogador decida pagar depois
            jogador.addCartaCorreio(c);
            novaMensagemConsole(jogador, "Tenho uma nova cobrança monstro de: " + c.getDescrição() + ", no valor de: $" + c.getValor());
        }
    }

    public void irParaFrenteAgora(int idJogador, boolean irComprasEntretenimento) {
        Jogador jogador = buscarJogador(idJogador);
        if (irComprasEntretenimento) {
            jogador.getPeao().irParaProximaCasaComprasEntretenimento();
            novaMensagemConsole(jogador, "Fui!!! Vou fazer uma grande Compra");
        } else {
            jogador.getPeao().irParaProximaCasaAcheiComprador();
            novaMensagemChat("Fui!!! Vou fazer uma grande Venda");
        }
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
        if (mens[mens.length - 1].trim().length() <= 0 || mens.length < 3) { //Testa se a mensagem veio sem a identificação;
            nome = "Anônimo disse: ";
            mensagem = mens[1];
        } else if (!mens[mens.length - 1].trim().equals("#")) { //Testa se a mensagem foi enviada pelo servidor, caso não, é executado
            if (this.jogadorPrincipal.getIdentificacao() == Integer.parseInt(mens[mens.length - 1].trim())) { //Testa se o proprio jogador mandou a mensagem
                nome = "Você disse: ";
                mensagem = mens[1];
            } else { //Caso não tenha sido o proprio jogador, ele procura quem mandou a mensagem.
                Jogador j = buscarJogador(Integer.parseInt(mens[mens.length - 1].trim()));
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
        if (telaPrincipal != null) {
            telaPrincipal.atualizarInformacoesTela();
        }
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

    public int getValorDado() {
        return dado.getValorDado();
    }

    public Principal getTelaPrincipal() {
        return this.telaPrincipal;
    }

    private void novaMensagemConsole(Jogador jogador, String mensagem) {
        String nome = jogador.getNome() + ": ";

        if (jogador.equals(jogadorPrincipal)) {
            nome = "Eu: ";
        }

        chat.novaMensagem(nome + mensagem);

        atualizarTela();
    }

    public ControllerConexao getControllerConexao() {
        return controllerConexao;
    }

}
