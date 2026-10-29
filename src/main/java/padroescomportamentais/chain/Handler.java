package padroescomportamentais.chain;

public abstract class Handler {
    private Handler proxHandler;

    public void setProximo(Handler proxHandler) {
        this.proxHandler = proxHandler;
    }

    protected Handler getProximo() {
        return proxHandler;
    }

    public abstract boolean tratarRequisicao(Requisicao requisicao);

    protected boolean passarParaProximo(Requisicao requisicao) {
        if (proxHandler != null) {
            return proxHandler.tratarRequisicao(requisicao);
        }

        return true;
    }
}