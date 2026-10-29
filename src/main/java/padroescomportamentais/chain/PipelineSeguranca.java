package padroescomportamentais.chain;

public class PipelineSeguranca {

    private Handler primeiroHandler;

    public PipelineSeguranca() {
        this.primeiroHandler = montarCadeia();
    }

    private Handler montarCadeia() {
        Handler autenticacao = new AutenticacaoHandler();
        Handler autorizacao = new AutorizacaoHandler();
        Handler rateLimit = new RateLimitHandler();

        autenticacao.setProximo(autorizacao);
        autorizacao.setProximo(rateLimit);

        return autenticacao;
    }

    public boolean processar(Requisicao requisicao) {
        System.out.println("\n=== INICIANDO PIPELINE DE SEGURANÇA ===");
        System.out.println("Requisição para: " + requisicao.getRecurso());
        System.out.println("IP: " + requisicao.getIpOrigem());
        System.out.println("Nível de acesso: " + requisicao.getNivelAcesso());

        boolean resultado = primeiroHandler.tratarRequisicao(requisicao);

        if (resultado) {
            System.out.println("REQUISICAO APROVADA: Acesso concedido ao recurso.");
        } else {
            System.out.println("REQUISICAO BLOQUEADA: Acesso negado.");
        }

        return resultado;
    }

    public void configurarCadeia(Handler primeiro) {
        this.primeiroHandler = primeiro;
    }
}