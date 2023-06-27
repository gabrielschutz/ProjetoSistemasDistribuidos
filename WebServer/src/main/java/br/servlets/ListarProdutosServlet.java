package br.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import br.ws.Gerenciador;
import br.Produtos;
import java.util.List;

@WebServlet(name = "ListarProdutosServlet", urlPatterns = {"/listar-produtos"})
public class ListarProdutosServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Criação do objeto Gerenciador
            Gerenciador gerenciador = new Gerenciador();

            // Chama o método getListaProdutos para obter a lista de produtos
            List<Produtos> produtos = gerenciador.getListaProdutos();

            // Configura os atributos para serem usados na página JSP
            request.setAttribute("produtos", produtos);

            // Encaminha para a página JSP que exibirá a lista de produtos
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (Exception e) {
            // Trate qualquer exceção que possa ocorrer
            e.printStackTrace();
        }
    }
}

