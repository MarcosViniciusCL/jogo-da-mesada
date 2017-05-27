/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.controller;

import pbl.exception.AguadarVezException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import pbl.exception.ErroComunicacaoServidorException;
import pbl.exception.ErroNaBuscaDeCartaOuVendedor;
import pbl.exception.NenhumJogadorGanhouBolaoException;
import pbl.exception.NumeroBilheteJaEscolhidoException;
import pbl.model.jogo.BilheteBolao;
import pbl.model.jogo.Carta;
import pbl.model.jogo.Dado;
import pbl.model.jogo.Jogador;
import pbl.model.jogo.Peao;
import pbl.model.jogo.PilhaCartasComprasEntretenimento;
import pbl.model.jogo.PilhaCartasCorreios;
import pbl.view.Principal;

/**
 *
 * @author marcos
 */
public class ControllerJogo {

    private static ControllerJogo controllerJogo; //Instancia da propria classe.
    private static ControllerConexao controllerConexao; //Instancia do controller de conexão.

    //ATRIBUTOS DO JOGO
    private boolean minhaVez = true;//(TESTE) //Variavel para informar se é a vez do cliente jogar o dado;
    private final Dado dado; //Dado do jogo
    private String ultimaMens; //Fica salva a ultima mensagem que foi enviada ao grupo para reenvio caso tenha perda.
    private List<Jogador> jogadores;
    private double sorteGrande;
    private int qntMeses;
    private Jogador jogadorPrincipal;
    private List<BilheteBolao> bilhetesBolao;

    private Principal telaPrincipal;

    //CONSTANTES DO JOGO
    private final double cPremio = 5000;
    private final double cBolao = 100;
    private final double cSorteGrande = 0;
    private final double cMesada = 3500;
    private final double cConcursoBandaArrocha = 1000;
    private final double cFelizAniversario = 100;
    private final double cNegocioOcasiao = 100;
    private final double cMaratonaBeneficente = 100;
    private final double cShopping = 1000;
    private final double cLanchonete = 600;

    private ControllerJogo() {
        this.dado = new Dado();
        jogadores = new ArrayList<>();
    }

    //****************************************** METODOS RESPONSAVEIS PELA AÇÃO DO JOGO ************************************
    public void criarJogadorPrincipal(int identificador, String nome) {
        this.jogadorPrincipal = new Jogador(identificador, nome, new Peao());
    }

    /**
     * Adiciona os jogadores que estarão na partida.
     *
     * @param identificador
     * @param nome
     */
    public void adicionarJogadores(int identificador, String nome) {
        jogadores.add(new Jogador(identificador, nome, new Peao()));
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
     * Cria uma nova instancia da lista bolão de esportes
     */

    public void novoBolaoDeEsportes() {
        bilhetesBolao = new ArrayList<>();
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
     * @param idJogador id do jogador que vendeu a propriedade
     * @param codigoCarta
     * @throws ErroNaBuscaDeCartaOuVendedor
     */
    public void achouUmComprador(int idJogador, int codigoCarta) throws ErroNaBuscaDeCartaOuVendedor {
        Jogador vendedor = buscarJogador(idJogador);
        Carta carta = PilhaCartasComprasEntretenimento.buscarCarta(codigoCarta);

        if (vendedor == null || carta == null) {
            throw new ErroNaBuscaDeCartaOuVendedor();
        }
        vendedor.removerCartaCompEntret(codigoCarta);
        vendedor.getConta().depositar(carta.getValor() * 1.5);
    }

    /**
     * O jogador que caiu na casa recebe 5000 do banco;
     *
     * @param jogador jogador que caiu na casa
     */
    public void premio(Jogador jogador) {
        jogador.getConta().depositar(cPremio);
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
     * @param jogador que caiu na casa
     */
    public void praiaNoDomingo(Jogador jogador) {
        if (!jogador.getConta().sacar(cSorteGrande)) { //verifica se o jogador tem saldo suficiente
            jogador.getConta().realizarEmprestimo(cSorteGrande); //se não realiza um emprestimo
            jogador.getConta().sacar(cSorteGrande);
        }
        sorteGrande += cSorteGrande; //adiciona o valor a sorte grande
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
        sorteGrande += valorDado * 100;
    }

    /**
     * Jogador caiu na casa sorte grande, recebe todo o valor depositado na sua
     * conta
     *
     * @param jogador
     */
    public void sorteGrande(Jogador jogador) {
        jogador.getConta().depositar(sorteGrande);
        sorteGrande = 0;
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
     * @param jogador
     * @param casas
     */
    public void moverPeao(Jogador jogador, int casas) {
        jogador.getPeao().andarCasas(casas);
        atualizarTela(); //Informa a tela que é necessário uma atualizar pelo fato que houve alteração de estados dos objetos;
    }

    /**
     * Move o peão do jogador que pertence a esse cliente.
     *
     * @param valor
     */
    public void moverPeao(int valor) {
        this.jogadorPrincipal.getPeao().andarCasas(valor);
        atualizarTela(); //Informa a tela que é necessário uma atualizar pelo fato que houve alteração de estados dos objetos;
//        controllerConexao.dadoJogado(valor); //Informa ao grupo que o dado foi jogado e qual valor caiu. 
    }

    public void pedirEmprestimo(double valorEmprestimo) {
        this.jogadorPrincipal.getConta().realizarEmprestimo(valorEmprestimo);
        atualizarTela();
    }
    
    //*************************************** CASAS FORA DO MANUAL
    
    /**
     * O jogador que caiu na casa paga 500 de conta do shopping
     * @param jogador jogador que caiu na casa
     */
    public void comprasShopping(Jogador jogador){
        if(!jogador.getConta().sacar(cShopping)){ //verifica se o jogador tem saldo para pagar
            jogador.getConta().realizarEmprestimo(cShopping);
            jogador.getConta().sacar(cShopping);
        }
    }
    
    /**
     * O jogador que caiu na casa paga  de conta da lanchonete
     * @param jogador 
     */
    public void lanconete(Jogador jogador){
        if(!jogador.getConta().sacar(cLanchonete)){ //verifica se o jogador tem saldo para pagar
            jogador.getConta().realizarEmprestimo(cLanchonete);
            jogador.getConta().sacar(cLanchonete);
        }
    }
    
    //*************************************** METODOS DA CARTA CORREIO
    
    /**
     * metodo paga caso o jogador retire um carta do tipo conta do monte
     * @param jogador jogador que recebeu a conta
     * @param pagarAgora verifica se o jogador solicitou o pagamento imediato
     * @param codCarta codigo da carta que o jogador sortou
     */
    public void contas(Jogador jogador, boolean pagarAgora, int codCarta){
        Carta c = PilhaCartasCorreios.buscarCarta(codCarta);
        if(pagarAgora){ //se o jogador desejou pagar na hora
            if(!jogador.getConta().sacar(c.getValor())){ //verifica se o jogador tem saldo
                jogador.getConta().realizarEmprestimo(c.getValor()); //se não realiza um emprestimo
                jogador.getConta().sacar(c.getValor()); //saca o valor
            }
        }else{ //caso o jogador prefira pagar depois
            jogador.addCartaCorreio(c); 
        }
    }
    
    /**
     * transfere o valor contido na carta para um vizinho da escolha do jogador
     * que caiu na casa
     * @param jogador jogador que sorteou a carta
     * @param vizinho vizinho escolhido
     * @param codCarta codigo da carta sorteada
     */
    public void dinheiroExtra(Jogador jogador, Jogador vizinho, int codCarta){
        Carta c = PilhaCartasCorreios.buscarCarta(codCarta);
        if(!vizinho.getConta().transferir(jogador.getConta(), c.getValor())){ //verifica se o vizinho tem saldo para transferir para o vizinho
            vizinho.getConta().realizarEmprestimo(c.getValor()); //caso não realiza um emprestimo
            vizinho.getConta().transferir(jogador.getConta(), c.getValor()); //transfere o valor
        }
    }
    
    /**
     * 
     * @param jogador
     * @param vizinho
     * @param codCarta 
     */
    public void pagueUmVizinhoAgora(Jogador jogador, Jogador vizinho, int codCarta){
        Carta c = PilhaCartasCorreios.buscarCarta(codCarta);
        if(!jogador.getConta().transferir(vizinho.getConta(), c.getValor())){ //verifica se o jogador tem saldo para transferir para o vizinho
            jogador.getConta().realizarEmprestimo(c.getValor()); //caso não realiza um emprestimo
            jogador.getConta().transferir(vizinho.getConta(), c.getValor()); //transfere o valor
        }
    }
    
    /**
     * Debita da conta do jogador que sorteou essa carta o valor da carta
     * @param jogador jogador que sorteou a carta
     * @param codCarta codigo da carta sorteada
     */
    public void doacao(Jogador jogador, int codCarta){
        Carta c = PilhaCartasCorreios.buscarCarta(codCarta);
        if(!jogador.getConta().sacar(c.getValor())){ //verifica se o jogador tem saldo para pagar a carta
            jogador.getConta().realizarEmprestimo(c.getValor()); //se não realiza um emprestimo
        }
        sorteGrande += c.getValor(); //deposita o valor no campo sorte grande
    }
    
    public void cobrancaMonstro(Jogador jogador, boolean pagarAgora, int codCarta){
        Carta c = PilhaCartasCorreios.buscarCarta(codCarta);
        if(pagarAgora){ //verifica se o jogador deseja pagar a carta agora
            if(!jogador.getConta().sacar((c.getValor()*1.1))){ //verifica se o jogador tem saldo suficiente
                jogador.getConta().realizarEmprestimo(c.getValor()); //se não realiza um empretimo
                jogador.getConta().sacar((c.getValor()*1.1)); //saca o valor
            }
        }else{ //caso o jogador decida pagar depois
            jogador.addCartaCorreio(c); 
        }
    }
    
    public void irParaFrenteAgora(Jogador jogador, boolean irComprasEntretenimento){
        if(irComprasEntretenimento){
            jogador.getPeao().irParaProximaCasaComprasEntretenimento();
        }else{
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

    //****************************************** METODOS DO CONTROLLER ************************************
    public static ControllerJogo getInstance() {
        if (controllerJogo == null) {
            controllerJogo = new ControllerJogo();
            controllerConexao = new ControllerConexao(controllerJogo);
        }
        return controllerJogo;
    }

    private void atualizarTela() {
    //    if (telaPrincipal != null) {
         telaPrincipal.atualizarInformacoesTela();
    //    }
    }

    public void setTelaPrincipal(Principal frame) {
        this.telaPrincipal = frame;
    }

    public void seletorAcao(String[] mens) {
        System.out.println(Arrays.toString(mens));
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

}
