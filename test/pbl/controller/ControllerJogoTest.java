/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.controller;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pbl.model.jogo.Carta;
import pbl.model.jogo.Chat;
import pbl.model.jogo.Jogador;
import pbl.model.jogo.Peao;
import pbl.view.Principal;

/**
 *
 * @author marcos
 */
public class ControllerJogoTest {
    ControllerJogo controller;
    
    public ControllerJogoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        controller = ControllerJogo.getInstance();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of criarJogadorPrincipal method, of class ControllerJogo.
     */
    @Test
    public void testCriarJogadorPrincipal() {
        System.out.println("criarJogadorPrincipal");
        int identificador = 0;
        String nome = "";
        ControllerJogo instance = null;
        instance.criarJogadorPrincipal(identificador, nome);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of adicionarJogadores method, of class ControllerJogo.
     */
    @Test
    public void testAdicionarJogadores() {
        System.out.println("adicionarJogadores");
        int identificador = 0;
        String nome = "";
        ControllerJogo instance = null;
        instance.adicionarJogadores(identificador, nome);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of jogarDado method, of class ControllerJogo.
     */
    @Test
    public void testJogarDado() {
        System.out.println("jogarDado");
        ControllerJogo instance = null;
        int expResult = 0;
        int result = instance.jogarDado();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of verificarSeJogadorPrincipalTemSaldo method, of class ControllerJogo.
     */
    @Test
    public void testVerificarSeJogadorPrincipalTemSaldo() {
        System.out.println("verificarSeJogadorPrincipalTemSaldo");
        double valor = 0.0;
        ControllerJogo instance = null;
        boolean expResult = false;
        boolean result = instance.verificarSeJogadorPrincipalTemSaldo(valor);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of pegarCartaCorreio method, of class ControllerJogo.
     */
    @Test
    public void testPegarCartaCorreio() {
        System.out.println("pegarCartaCorreio");
        ControllerJogo instance = null;
        Carta expResult = null;
        Carta result = instance.pegarCartaCorreio();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of novoBolaoDeEsportes method, of class ControllerJogo.
     */
    @Test
    public void testNovoBolaoDeEsportes() {
        System.out.println("novoBolaoDeEsportes");
        ControllerJogo instance = null;
        instance.novoBolaoDeEsportes();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compraEntretenimento method, of class ControllerJogo.
     */
    @Test
    public void testCompraEntretenimento() {
        System.out.println("compraEntretenimento");
        int idJogador = 0;
        int codigoCarta = 0;
        ControllerJogo instance = null;
        instance.compraEntretenimento(idJogador, codigoCarta);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of participarBolao method, of class ControllerJogo.
     */
    @Test
    public void testParticiparBolao() throws Exception {
        System.out.println("participarBolao");
        int idJogador = 0;
        int numero = 0;
        ControllerJogo instance = null;
        instance.participarBolao(idJogador, numero);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of finalizarBolao method, of class ControllerJogo.
     */
    @Test
    public void testFinalizarBolao() throws Exception {
        System.out.println("finalizarBolao");
        int numeroSorteado = 0;
        ControllerJogo instance = null;
        instance.finalizarBolao(numeroSorteado);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of achouUmComprador method, of class ControllerJogo.
     */
    @Test
    public void testAchouUmComprador() {
        System.out.println("achouUmComprador");
        int idJogador = 0;
        int codigoCarta = 0;
        ControllerJogo instance = null;
        instance.achouUmComprador(idJogador, codigoCarta);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of premio method, of class ControllerJogo.
     */
    @Test
    public void testPremio() {
        System.out.println("premio");
        int idJogador = 0;
        ControllerJogo instance = null;
        instance.premio(idJogador);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of bolaoEsportes method, of class ControllerJogo.
     */
    @Test
    public void testBolaoEsportes() {
        System.out.println("bolaoEsportes");
        Jogador ganhador = null;
        List<Jogador> participantes = null;
        ControllerJogo instance = null;
        instance.bolaoEsportes(ganhador, participantes);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of praiaNoDomingo method, of class ControllerJogo.
     */
    @Test
    public void testPraiaNoDomingo() {
        System.out.println("praiaNoDomingo");
        int idJogador = 0;
        ControllerJogo instance = null;
        instance.praiaNoDomingo(idJogador);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of concursoBandaArrocha method, of class ControllerJogo.
     */
    @Test
    public void testConcursoBandaArrocha() {
        System.out.println("concursoBandaArrocha");
        ControllerJogo instance = null;
        instance.concursoBandaArrocha();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of resultadoBandaArrocha method, of class ControllerJogo.
     */
    @Test
    public void testResultadoBandaArrocha() {
        System.out.println("resultadoBandaArrocha");
        boolean ganhou = false;
        ControllerJogo instance = null;
        instance.resultadoBandaArrocha(ganhou);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of felizAniversario method, of class ControllerJogo.
     */
    @Test
    public void testFelizAniversario() {
        System.out.println("felizAniversario");
        int idJogador = 0;
        ControllerJogo instance = null;
        instance.felizAniversario(idJogador);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of vendeNegocioOcasiao method, of class ControllerJogo.
     */
    @Test
    public void testVendeNegocioOcasiao() {
        System.out.println("vendeNegocioOcasiao");
        int idJogador = 0;
        int valorDado = 0;
        ControllerJogo instance = null;
        instance.vendeNegocioOcasiao(idJogador, valorDado);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of maratonaBeneficente method, of class ControllerJogo.
     */
    @Test
    public void testMaratonaBeneficente() {
        System.out.println("maratonaBeneficente");
        int idJogador = 0;
        int valorDado = 0;
        ControllerJogo instance = null;
        instance.maratonaBeneficente(idJogador, valorDado);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sorteGrande method, of class ControllerJogo.
     */
    @Test
    public void testSorteGrande() {
        System.out.println("sorteGrande");
        int idJogador = 0;
        ControllerJogo instance = null;
        instance.sorteGrande(idJogador);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of florestaAmazonica method, of class ControllerJogo.
     */
    @Test
    public void testFlorestaAmazonica() {
        System.out.println("florestaAmazonica");
        int idJogador = 0;
        ControllerJogo instance = null;
        instance.florestaAmazonica(idJogador);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of diaDaMesada method, of class ControllerJogo.
     */
    @Test
    public void testDiaDaMesada() {
        System.out.println("diaDaMesada");
        Jogador jogador = null;
        ControllerJogo instance = null;
        instance.diaDaMesada(jogador);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of moverPeao method, of class ControllerJogo.
     */
    @Test
    public void testMoverPeao_int_int() {
        System.out.println("moverPeao");
        int idJogador = 0;
        int casas = 0;
        ControllerJogo instance = null;
        instance.moverPeao(idJogador, casas);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of moverPeao method, of class ControllerJogo.
     */
    @Test
    public void testMoverPeao_int() {
        System.out.println("moverPeao");
        int valorDado = 0;
        ControllerJogo instance = null;
        instance.moverPeao(valorDado);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of pedirEmprestimo method, of class ControllerJogo.
     */
    @Test
    public void testPedirEmprestimo() {
        System.out.println("pedirEmprestimo");
        int idJogador = 0;
        double valorEmprestimo = 0.0;
        ControllerJogo instance = null;
        instance.pedirEmprestimo(idJogador, valorEmprestimo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of depositar method, of class ControllerJogo.
     */
    @Test
    public void testDepositar() {
        System.out.println("depositar");
        double valor = 0.0;
        ControllerJogo instance = null;
        instance.depositar(valor);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of adicionarSorteGrande method, of class ControllerJogo.
     */
    @Test
    public void testAdicionarSorteGrande() {
        System.out.println("adicionarSorteGrande");
        double valor = 0.0;
        ControllerJogo instance = null;
        instance.adicionarSorteGrande(valor);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of zerarSorteGrande method, of class ControllerJogo.
     */
    @Test
    public void testZerarSorteGrande() {
        System.out.println("zerarSorteGrande");
        ControllerJogo instance = null;
        instance.zerarSorteGrande();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of comprasShopping method, of class ControllerJogo.
     */
    @Test
    public void testComprasShopping() {
        System.out.println("comprasShopping");
        int idJogador = 0;
        ControllerJogo instance = null;
        instance.comprasShopping(idJogador);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of lanchonete method, of class ControllerJogo.
     */
    @Test
    public void testLanchonete() {
        System.out.println("lanchonete");
        int idJogador = 0;
        ControllerJogo instance = null;
        instance.lanchonete(idJogador);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of contas method, of class ControllerJogo.
     */
    @Test
    public void testContas() {
        System.out.println("contas");
        int idJogador = 0;
        boolean pagarAgora = false;
        int codCarta = 0;
        ControllerJogo instance = null;
        instance.contas(idJogador, pagarAgora, codCarta);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dinheiroExtra method, of class ControllerJogo.
     */
    @Test
    public void testDinheiroExtra() {
        System.out.println("dinheiroExtra");
        int idJogador = 0;
        int idVizinho = 0;
        int codCarta = 0;
        ControllerJogo instance = null;
        instance.dinheiroExtra(idJogador, idVizinho, codCarta);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of pagueUmVizinhoAgora method, of class ControllerJogo.
     */
    @Test
    public void testPagueUmVizinhoAgora() {
        System.out.println("pagueUmVizinhoAgora");
        int idJogador = 0;
        int idVizinho = 0;
        int codCarta = 0;
        ControllerJogo instance = null;
        instance.pagueUmVizinhoAgora(idJogador, idVizinho, codCarta);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of doacao method, of class ControllerJogo.
     */
    @Test
    public void testDoacao() {
        System.out.println("doacao");
        int idJogador = 0;
        int codCarta = 0;
        ControllerJogo instance = null;
        instance.doacao(idJogador, codCarta);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cobrancaMonstro method, of class ControllerJogo.
     */
    @Test
    public void testCobrancaMonstro() {
        System.out.println("cobrancaMonstro");
        int idJogador = 0;
        boolean pagarAgora = false;
        int codCarta = 0;
        ControllerJogo instance = null;
        instance.cobrancaMonstro(idJogador, pagarAgora, codCarta);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of irParaFrenteAgora method, of class ControllerJogo.
     */
    @Test
    public void testIrParaFrenteAgora() {
        System.out.println("irParaFrenteAgora");
        int idJogador = 0;
        boolean irComprasEntretenimento = false;
        ControllerJogo instance = null;
        instance.irParaFrenteAgora(idJogador, irComprasEntretenimento);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reenviarUltMensGRP method, of class ControllerJogo.
     */
    @Test
    public void testReenviarUltMensGRP() throws Exception {
        System.out.println("reenviarUltMensGRP");
        ControllerJogo instance = null;
        instance.reenviarUltMensGRP();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of entrarSala method, of class ControllerJogo.
     */
    @Test
    public void testEntrarSala() throws Exception {
        System.out.println("entrarSala");
        String nome = "";
        int quantJogadores = 0;
        int quantMeses = 0;
        ControllerJogo instance = null;
        instance.entrarSala(nome, quantJogadores, quantMeses);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of novaMensagemChat method, of class ControllerJogo.
     */
    @Test
    public void testNovaMensagemChat() {
        System.out.println("novaMensagemChat");
        String mens = "";
        ControllerJogo instance = null;
        instance.novaMensagemChat(mens);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of adicionarMensChat method, of class ControllerJogo.
     */
    @Test
    public void testAdicionarMensChat() {
        System.out.println("adicionarMensChat");
        String[] mens = null;
        ControllerJogo instance = null;
        instance.adicionarMensChat(mens);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getChat method, of class ControllerJogo.
     */
    @Test
    public void testGetChat() {
        System.out.println("getChat");
        ControllerJogo instance = null;
        Chat expResult = null;
        Chat result = instance.getChat();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInstance method, of class ControllerJogo.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        ControllerJogo expResult = null;
        ControllerJogo result = ControllerJogo.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTelaPrincipal method, of class ControllerJogo.
     */
    @Test
    public void testSetTelaPrincipal() {
        System.out.println("setTelaPrincipal");
        Principal frame = null;
        ControllerJogo instance = null;
        instance.setTelaPrincipal(frame);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getJogadores method, of class ControllerJogo.
     */
    @Test
    public void testGetJogadores() {
        System.out.println("getJogadores");
        ControllerJogo instance = null;
        List<Jogador> expResult = null;
        List<Jogador> result = instance.getJogadores();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPeoes method, of class ControllerJogo.
     */
    @Test
    public void testGetPeoes() {
        System.out.println("getPeoes");
        ControllerJogo instance = null;
        List<Peao> expResult = null;
        List<Peao> result = instance.getPeoes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isMinhaVez method, of class ControllerJogo.
     */
    @Test
    public void testIsMinhaVez() {
        System.out.println("isMinhaVez");
        ControllerJogo instance = null;
        boolean expResult = false;
        boolean result = instance.isMinhaVez();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMinhaVez method, of class ControllerJogo.
     */
    @Test
    public void testSetMinhaVez() {
        System.out.println("setMinhaVez");
        boolean minhaVez = false;
        ControllerJogo instance = null;
        instance.setMinhaVez(minhaVez);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getJogador method, of class ControllerJogo.
     */
    @Test
    public void testGetJogador() {
        System.out.println("getJogador");
        ControllerJogo instance = null;
        Jogador expResult = null;
        Jogador result = instance.getJogador();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscarJogador method, of class ControllerJogo.
     */
    @Test
    public void testBuscarJogador() {
        System.out.println("buscarJogador");
        int idJogador = 0;
        ControllerJogo instance = null;
        Jogador expResult = null;
        Jogador result = instance.buscarJogador(idJogador);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getValorSorteGrande method, of class ControllerJogo.
     */
    @Test
    public void testGetValorSorteGrande() {
        System.out.println("getValorSorteGrande");
        ControllerJogo instance = null;
        double expResult = 0.0;
        double result = instance.getValorSorteGrande();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getValorDado method, of class ControllerJogo.
     */
    @Test
    public void testGetValorDado() {
        System.out.println("getValorDado");
        ControllerJogo instance = null;
        int expResult = 0;
        int result = instance.getValorDado();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getControllerConexao method, of class ControllerJogo.
     */
    @Test
    public void testGetControllerConexao() {
        System.out.println("getControllerConexao");
        ControllerJogo instance = null;
        ControllerConexao expResult = null;
        ControllerConexao result = instance.getControllerConexao();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
