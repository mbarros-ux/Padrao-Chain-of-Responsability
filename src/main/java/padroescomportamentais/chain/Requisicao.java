package padroescomportamentais.chain;

public class Requisicao {
    private final String token;
    private final int nivelAcesso;
    private final String ipOrigem;
    private final String recurso;

    public Requisicao(String token, int nivelAcesso, String ipOrigem, String recurso) {
        this.token = token;
        this.nivelAcesso = nivelAcesso;
        this.ipOrigem = ipOrigem;
        this.recurso = recurso;
    }

    public String getToken() {
        return token;
    }

    public int getNivelAcesso() {
        return nivelAcesso;
    }

    public String getIpOrigem() {
        return ipOrigem;
    }

    public String getRecurso() {
        return recurso;
    }
}
