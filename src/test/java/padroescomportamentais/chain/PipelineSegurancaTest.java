package padroescomportamentais.chain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PipelineSegurancaTest {

    private PipelineSeguranca pipeline;
    private RateLimitHandler rateLimitHandler;

    @BeforeEach
    void setUp() {
        pipeline = new PipelineSeguranca();

        rateLimitHandler = new RateLimitHandler();
    }

    @Test
    void deveAprovarRequisicaoValida() {
        Requisicao requisicao = new Requisicao("Bearer token-valido", 5, "10.0.0.1", "/api/admin");
        assertTrue(pipeline.processar(requisicao));
    }

    @Test
    void deveRejeitarRequisicaoComTokenAusente() {
        Requisicao requisicao = new Requisicao("", 5, "10.0.0.1", "/api/admin");
        assertFalse(pipeline.processar(requisicao));
    }

    @Test
    void deveRejeitarRequisicaoComTokenEmFormatoInvalido() {
        Requisicao requisicao = new Requisicao("token-sem-prefixo", 5, "10.0.0.1", "/api/admin");
        assertFalse(pipeline.processar(requisicao));
    }

    @Test
    void deveRejeitarRequisicaoComNivelDeAcessoInsuficiente() {
        Requisicao requisicao = new Requisicao("Bearer token-valido", 1, "10.0.0.1", "/api/admin");
        assertFalse(pipeline.processar(requisicao));
    }

    @Test
    void deveInterromperCadeiaNoPrimeiroHandlerQueFalhar() {
        Requisicao requisicao = new Requisicao("", 1, "10.0.0.1", "/api/admin");
        assertFalse(pipeline.processar(requisicao));
    }

    @Test
    void devePermitirRequisicoesDentroDoRateLimit() {
        // Cria uma cadeia isolada para este teste
        Handler autenticacao = new AutenticacaoHandler();
        Handler autorizacao = new AutorizacaoHandler();
        Handler rateLimit = new RateLimitHandler();
        autenticacao.setProximo(autorizacao);
        autorizacao.setProximo(rateLimit);

        PipelineSeguranca pipelineTeste = new PipelineSeguranca();
        pipelineTeste.configurarCadeia(autenticacao);

        Requisicao requisicao = new Requisicao("Bearer token", 5, "10.0.0.5", "/api/data");
        assertTrue(pipelineTeste.processar(requisicao));
    }

    @Test
    void deveDemonstrarQueOrdemDosHandlersAfetaResultado() {

        Handler rateLimit = new RateLimitHandler();
        Handler autenticacao = new AutenticacaoHandler();
        Handler autorizacao = new AutorizacaoHandler();

        rateLimit.setProximo(autenticacao);
        autenticacao.setProximo(autorizacao);

        PipelineSeguranca pipelineInvertida = new PipelineSeguranca();
        pipelineInvertida.configurarCadeia(rateLimit);

        Requisicao requisicao = new Requisicao("Bearer token", 5, "10.0.0.9", "/api/admin");
        assertTrue(pipelineInvertida.processar(requisicao));
    }
}