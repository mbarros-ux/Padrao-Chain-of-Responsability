package padroescomportamentais.chain;

public class AutenticacaoHandler extends Handler {

    @Override
    public boolean tratarRequisicao(Requisicao requisicao) {
        System.out.println("[Autenticacao] Verificando token JWT...");

        if (requisicao.getToken() == null || requisicao.getToken().isBlank()) {
            System.out.println("  [Autenticacao] REJEITADA: Token ausente ou inválido.");
            return false;
        }

        if (!requisicao.getToken().startsWith("Bearer ")) {
            System.out.println("  [Autenticacao] REJEITADA: Formato de token inválido.");
            return false;
        }

        System.out.println("  [Autenticacao] Token válido. Passando para próximo handler.");
        return passarParaProximo(requisicao);
    }
}