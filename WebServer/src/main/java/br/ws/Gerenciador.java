package br.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import br.Produtos;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@WebService(serviceName = "Gerenciador")
public class Gerenciador {

    private static List<Produtos> listaProdutos; // Lista de produtos
    private static List<String> logs; // Lista de LOGS

    public Gerenciador() {
        listaProdutos = new ArrayList<>();
        logs = new ArrayList<>();
    }

    @WebMethod(operationName = "consultarProduto")
    public Produtos consultarProduto(@WebParam(name = "id") int id) {
        for (Produtos produto : listaProdutos) {
            if (produto.getId() == id) {
                return produto;
            }
        }
        return null; // Produto não encontrado
    }

    private final Lock lock = new ReentrantLock();

    @WebMethod(operationName = "adicionarProduto")
    public String adicionarProduto(@WebParam(name = "nome") String nome, @WebParam(name = "quantidade") int quantidade, @WebParam(name = "preco") double preco) {
        lock.lock(); // Adquire o bloqueio

        try {
            // Verifica se já existe um produto com o mesmo nome
            for (Produtos produto : listaProdutos) {
                if (produto.getNome().equalsIgnoreCase(nome)) {
                    return "Já existe um produto com o mesmo nome.";
                }
            }

            int novoId = gerarNovoId();
            System.out.println("============");
            System.out.print(novoId);
            System.out.print(nome);
            System.out.print(quantidade);
            System.out.println(preco);
            System.out.print("============");

            try {
                System.out.println("Estou Esperando Aqui");
                Thread.sleep(5000); // Atraso de 5 segundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Produtos novoProduto = new Produtos(novoId, nome, quantidade, preco);
            listaProdutos.add(novoProduto);
            System.out.println(listaProdutos);
            System.out.println("Acabei");
            logs.add("Cadastrado novo Produto: " + nome + " Quantidade: " + quantidade + " Valor: " + preco);
            return "Produto adicionado com sucesso! ID: " + novoId;
        } finally {
            lock.unlock(); // Libera o bloqueio no final
        }
    }

    @WebMethod(operationName = "venderProduto")
    public String venderProduto(@WebParam(name = "id") int id, @WebParam(name = "quantidade") int quantidade) {
        lock.lock(); // Adquire o bloqueio
        try {

            try {
                System.out.println("Estou Esperando Aqui");
                Thread.sleep(5000); // Atraso de 5 segundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Produtos produto = consultarProduto(id);

            if (produto == null) {
                return "Produto não encontrado.";
            }

            int estoqueAtual = produto.getQuantidade();

            if (estoqueAtual < quantidade) {
                return "Não foi possível vender. Estoque insuficiente.";
            }

            produto.setQuantidade(estoqueAtual - quantidade);
            
            logs.add("Venda Realizada! - Produto de ID: " + id + " Quantidade: " + quantidade);


            return "Venda realizada com sucesso!";
        } finally {
            lock.unlock(); // Libera o bloqueio no final
        }

    }

    @WebMethod(operationName = "atualizarProduto")
    public String atualizarProduto(@WebParam(name = "id") int id, @WebParam(name = "nome") String nome, @WebParam(name = "quantidade") int quantidade, @WebParam(name = "preco") double preco) {
        lock.lock(); // Adquire o bloqueio
        try {

            try {
                System.out.println("Estou Esperando Aqui");
                Thread.sleep(5000); // Atraso de 5 segundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (Produtos produto : listaProdutos) {
                if (produto.getId() == id) {
                    produto.setNome(nome);
                    produto.setQuantidade(quantidade);
                    produto.setPreco(preco);
                    logs.add("Produto Atualizado! - Produto de ID: " + id + "Novo Nome: " + nome + "Nova QTD: " + quantidade + "Novo Preco: " + preco);
                    return "Produto atualizado com sucesso!";
                }
            }
            return "Produto não encontrado.";
        } finally {
            lock.unlock(); // Libera o bloqueio no final
        }
    }

    @WebMethod(operationName = "removerProduto")
    public String removerProduto(@WebParam(name = "id") int id) {
        lock.lock(); // Adquire o bloqueio
        try {

            try {
                System.out.println("Estou Esperando Aqui");
                Thread.sleep(5000); // Atraso de 5 segundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (Produtos produto : listaProdutos) {
                if (produto.getId() == id) {
                    listaProdutos.remove(produto);
                    logs.add("Produto com ID: " + id +"Removido!");
                    return "Produto removido com sucesso!";
                }
            }
            return "Produto não encontrado.";
        } finally {
            lock.unlock(); // Libera o bloqueio no final
        }

    }

    // Método para gerar um novo ID para o produto
    private int gerarNovoId() {
        int maiorId = 0;
        for (Produtos produto : listaProdutos) {
            if (produto.getId() > maiorId) {
                maiorId = produto.getId();
            }
        }
        return maiorId + 1;
    }

    // Método para listar produtos
    @WebMethod(operationName = "listarProdutos")
    public List<Produtos> listarProdutos() {
        return listaProdutos;
    }
    
    @WebMethod(operationName = "listarLogs")
    public List<String> listarLogs() {
        return logs;
    }

    public List<Produtos> getListaProdutos() {
        System.out.println(listaProdutos);
        return listaProdutos;
    }

}
