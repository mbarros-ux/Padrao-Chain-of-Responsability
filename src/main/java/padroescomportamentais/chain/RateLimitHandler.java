package padroescomportamentais.chain;

import java.util.HashMap;
import java.util.Map;

public class RateLimitHandler extends Handler {

    private static final int LIMITE_REQUISICOES = 5;
    private final Map<String, Integer> contadorPorIp = new HashMap<>();

    @Override
    public boolean tratarRequisicao(Requisicao requisicao) {
        System.out.println("[RateLimit] Verificando limite de requisições para IP: " + requisicao.getIpOrigem());

        int contador = contadorPorIp.getOrDefault(requisicao.getIpOrigem(), 0) + 1;
        contadorPorIp.put(requisicao.getIpOrigem(), contador);

        if (contador > LIMITE_REQUISICOES) {
            System.out.println("  [RateLimit] REJEITADA: Limite excedido (" + contador + "/" + LIMITE_REQUISICOES + ").");
            return false;
        }

        System.out.println("  [RateLimit] Dentro do limite (" + contador + "/" + LIMITE_REQUISICOES + "). Passando para próximo handler.");
        return passarParaProximo(requisicao);
    }
}