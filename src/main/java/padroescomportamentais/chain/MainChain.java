package padroescomportamentais.chain;

public class MainChain {
    public static void main(String[] args) {
        PipelineSeguranca pipeline = new PipelineSeguranca();

        System.out.println("CENÁRIO 1: Requisição válida");
        pipeline.processar(new Requisicao("Bearer abc123", 5, "192.168.1.10", "/api/admin"));

        System.out.println("\n\nCENÁRIO 2: Token inválido");
        pipeline.processar(new Requisicao("", 5, "192.168.1.10", "/api/admin"));

        System.out.println("\n\nCENÁRIO 3: Nível de acesso insuficiente");
        pipeline.processar(new Requisicao("Bearer xyz789", 1, "192.168.1.20", "/api/admin"));
    }
}