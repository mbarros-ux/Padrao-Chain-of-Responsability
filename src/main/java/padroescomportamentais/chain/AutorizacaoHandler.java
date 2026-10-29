package padroescomportamentais.chain;

public class AutorizacaoHandler extends Handler {

    private static final int NIVEL_MINIMO_ADMIN = 3;

    @Override
    public boolean tratarRequisicao(Requisicao requisicao) {
        System.out.println("[Autorizacao] Verificando permissões para: " + requisicao.getRecurso());

        if (requisicao.getNivelAcesso() < NIVEL_MINIMO_ADMIN) {
            System.out.println("  [Autorizacao] REJEITADA: Nível de acesso insuficiente ("
                    + requisicao.getNivelAcesso() + ").");
            return false;
        }

        System.out.println("  [Autorizacao] Permissão concedida. Passando para próximo handler.");
        return passarParaProximo(requisicao);
    }
}