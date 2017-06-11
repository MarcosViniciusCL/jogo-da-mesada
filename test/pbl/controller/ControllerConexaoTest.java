/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.controller;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marcos
 */
public class ControllerConexaoTest {
    
    ControllerJogo controller;
    public ControllerConexaoTest() {
    }
    
    @Before
    public void setUp() {
        controller = ControllerJogo.getInstance();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of passaVez method, of class ControllerConexao.
     */
    @Test
    public void testPassaVez() {
        controller.getControllerConexao().passaVez(2);
        controller.getControllerConexao();
    }

    /**
     * Test of pagueUmVizinhoAgora method, of class ControllerConexao.
     */
    @Test
    public void testPagueUmVizinhoAgora() {
        System.out.println("pagueUmVizinhoAgora");
        int idVizinho = 0;
        int codCarta = 0;
        ControllerConexao instance = null;
        instance.pagueUmVizinhoAgora(idVizinho, codCarta);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dinheiroExtra method, of class ControllerConexao.
     */
    @Test
    public void testDinheiroExtra() {
        System.out.println("dinheiroExtra");
        int idVizinho = 0;
        int codCarta = 0;
        ControllerConexao instance = null;
        instance.dinheiroExtra(idVizinho, codCarta);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of felizAniversario method, of class ControllerConexao.
     */
    @Test
    public void testFelizAniversario() {
        System.out.println("felizAniversario");
        double valor = 0.0;
        ControllerConexao instance = null;
        instance.felizAniversario(valor);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sorteGrande method, of class ControllerConexao.
     */
    @Test
    public void testSorteGrande() {
        System.out.println("sorteGrande");
        ControllerConexao instance = null;
        instance.sorteGrande();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of vaParaFrenteAgora method, of class ControllerConexao.
     */
    @Test
    public void testVaParaFrenteAgora() {
        System.out.println("vaParaFrenteAgora");
        boolean compraEnt = false;
        ControllerConexao instance = null;
        instance.vaParaFrenteAgora(compraEnt);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of conta method, of class ControllerConexao.
     */
    @Test
    public void testConta() {
        System.out.println("conta");
        int idCarta = 0;
        int pagarAgora = 0;
        ControllerConexao instance = null;
        instance.conta(idCarta, pagarAgora);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cobrancaMonstro method, of class ControllerConexao.
     */
    @Test
    public void testCobrancaMonstro() {
        System.out.println("cobrancaMonstro");
        int idCarta = 0;
        int pagarAgora = 0;
        ControllerConexao instance = null;
        instance.cobrancaMonstro(idCarta, pagarAgora);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of doacao method, of class ControllerConexao.
     */
    @Test
    public void testDoacao() {
        System.out.println("doacao");
        int codigoCarta = 0;
        ControllerConexao instance = null;
        instance.doacao(codigoCarta);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of resultadoBandaArrocha method, of class ControllerConexao.
     */
    @Test
    public void testResultadoBandaArrocha() {
        System.out.println("resultadoBandaArrocha");
        boolean ganhou = false;
        ControllerConexao instance = null;
        instance.resultadoBandaArrocha(ganhou);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of acheiUmComprador method, of class ControllerConexao.
     */
    @Test
    public void testAcheiUmComprador() {
        System.out.println("acheiUmComprador");
        int codigoCarta = 0;
        ControllerConexao instance = null;
        instance.acheiUmComprador(codigoCarta);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compraEntrenimentos method, of class ControllerConexao.
     */
    @Test
    public void testCompraEntrenimentos() {
        System.out.println("compraEntrenimentos");
        int codigoCarta = 0;
        ControllerConexao instance = null;
        instance.compraEntrenimentos(codigoCarta);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of conectarServidor method, of class ControllerConexao.
     */
    @Test
    public void testConectarServidor() throws Exception {
        System.out.println("conectarServidor");
        String ipServe = "";
        ControllerConexao instance = null;
        instance.conectarServidor(ipServe);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of entraSala method, of class ControllerConexao.
     */
    @Test
    public void testEntraSala() throws Exception {
        System.out.println("entraSala");
        String nome = "";
        int maxJogadores = 0;
        int quantMeses = 0;
        ControllerConexao instance = null;
        instance.entraSala(nome, maxJogadores, quantMeses);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sairGrupoMult method, of class ControllerConexao.
     */
    @Test
    public void testSairGrupoMult() throws Exception {
        System.out.println("sairGrupoMult");
        ControllerConexao instance = null;
        instance.sairGrupoMult();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of enviarMensagemGRP method, of class ControllerConexao.
     */
    @Test
    public void testEnviarMensagemGRP() {
        System.out.println("enviarMensagemGRP");
        String mens = "";
        ControllerConexao instance = null;
        instance.enviarMensagemGRP(mens);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of novaMensChat method, of class ControllerConexao.
     */
    @Test
    public void testNovaMensChat() {
        System.out.println("novaMensChat");
        String mens = "";
        ControllerConexao instance = null;
        instance.novaMensChat(mens);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
