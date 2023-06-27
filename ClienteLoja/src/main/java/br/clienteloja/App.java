package br.clienteloja;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {

    ListarProdutos lp = new ListarProdutos();
    RemoverProduto rp = new RemoverProduto();
    AtualizarProduto up = new AtualizarProduto();
    AdicionarProduto ap = new AdicionarProduto();
    ConsultarProduto cp = new ConsultarProduto();
    VenderProduto vp = new VenderProduto();

    private static final String SOAP_ENDPOINT_URL = "http://localhost:8080/WebServer/Gerenciador?wsdl";

    @Override
    public void start(Stage stage) {
        stage.setTitle("Controle de Estoque - Versão 1.0");

        Label titleLabel = new Label("Controle de Estoque - Versão 1.0");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        titleLabel.setFont(Font.font("Times New Roman", 40));

        Button adicionarProdutoButton = new Button("Adicionar Produto");
        Button atualizarProdutoButton = new Button("Atualizar Produto");
        Button consultarProdutoButton = new Button("Consultar Produto");
        Button listarProdutosButton = new Button("Listar Produtos");
        Button removerProdutosButton = new Button("Remover Produtos");
        Button realizarVendaButton = new Button("Realizar Venda");

        adicionarProdutoButton.setMaxWidth(Double.MAX_VALUE);
        atualizarProdutoButton.setMaxWidth(Double.MAX_VALUE);
        consultarProdutoButton.setMaxWidth(Double.MAX_VALUE);
        listarProdutosButton.setMaxWidth(Double.MAX_VALUE);
        removerProdutosButton.setMaxWidth(Double.MAX_VALUE);
        realizarVendaButton.setMaxWidth(Double.MAX_VALUE);

        VBox buttonsLayout = new VBox(10);
        buttonsLayout.setAlignment(Pos.CENTER);
        buttonsLayout.setPadding(new Insets(10));
        buttonsLayout.getChildren().addAll(
                adicionarProdutoButton,
                atualizarProdutoButton,
                consultarProdutoButton,
                listarProdutosButton,
                removerProdutosButton,
                realizarVendaButton
        );

        BorderPane layout = new BorderPane();
        layout.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);

        layout.setCenter(buttonsLayout);

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.show();

        adicionarProdutoButton.setOnAction(event -> {
            Stage addProdutoStage = new Stage();
            addProdutoStage.setTitle("Adicionar Produto");

            Label titleLabelAp = new Label("Adicionar Produto");
            titleLabelAp.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            titleLabelAp.setFont(Font.font("Times New Roman", 40));

            GridPane addProdutoLayout = new GridPane();
            addProdutoLayout.setAlignment(Pos.CENTER);
            addProdutoLayout.setHgap(10);
            addProdutoLayout.setVgap(10);
            addProdutoLayout.setPadding(new Insets(10));

            Label nomeLabel = new Label("Nome do Produto:");

            TextField nomeTextField = new TextField();

            Label quantidadeLabel = new Label("Quantidade:");
            TextField quantidadeTextField = new TextField();

            Label valorLabel = new Label("Valor:");
            TextField valorTextField = new TextField();

            addProdutoLayout.addRow(0, nomeLabel, nomeTextField);
            addProdutoLayout.addRow(1, quantidadeLabel, quantidadeTextField);
            addProdutoLayout.addRow(2, valorLabel, valorTextField);

            Button salvarButton = new Button("Salvar");
            salvarButton.setOnAction(saveEvent -> {
                String nome = nomeTextField.getText();
                int quantidade = Integer.parseInt(quantidadeTextField.getText());
                double valor = Double.parseDouble(valorTextField.getText());

                String response = ap.callSoapWebService(SOAP_ENDPOINT_URL, "adicionarProduto", nome, quantidade, valor);

                Label resultadoLabel = new Label(response);
                resultadoLabel.setStyle("-fx-font-size: 14px;");

                VBox resultadoLayout = new VBox(10);
                resultadoLayout.setAlignment(Pos.CENTER);
                resultadoLayout.getChildren().addAll(resultadoLabel);

                Scene resultadoScene = new Scene(resultadoLayout, 300, 100);

                Stage resultadoStage = new Stage();
                resultadoStage.setTitle("Resultado");
                resultadoStage.setScene(resultadoScene);
                resultadoStage.show();

                addProdutoStage.close();
            });

            HBox salvarButtonLayout = new HBox(10);
            salvarButtonLayout.setAlignment(Pos.CENTER);
            salvarButtonLayout.getChildren().add(salvarButton);

            VBox addProdutoSceneLayout = new VBox(10);
            addProdutoSceneLayout.setAlignment(Pos.CENTER);
            addProdutoSceneLayout.getChildren().addAll(titleLabelAp, addProdutoLayout, salvarButtonLayout);

            Scene addProdutoScene = new Scene(addProdutoSceneLayout, 300, 250);
            addProdutoStage.setScene(addProdutoScene);
            addProdutoStage.show();
        });

        atualizarProdutoButton.setOnAction(event -> {
            Stage attProdutoStage = new Stage();
            attProdutoStage.setTitle("Atualizar Produto");

            Label titleLabelAp = new Label("Atualizar Produto");
            titleLabelAp.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            titleLabelAp.setFont(Font.font("Times New Roman", 40));

            GridPane attProdutoLayout = new GridPane();
            attProdutoLayout.setAlignment(Pos.CENTER);
            attProdutoLayout.setHgap(10);
            attProdutoLayout.setVgap(10);
            attProdutoLayout.setPadding(new Insets(10));

            Label idLabel = new Label("ID do Produto:");

            TextField idTextField = new TextField();

            Label nomeLabel = new Label("Nome do Produto:");

            TextField nomeTextField = new TextField();

            Label quantidadeLabel = new Label("Quantidade:");
            TextField quantidadeTextField = new TextField();

            Label valorLabel = new Label("Valor:");
            TextField valorTextField = new TextField();

            attProdutoLayout.addRow(0, idLabel, idTextField);
            attProdutoLayout.addRow(1, nomeLabel, nomeTextField);
            attProdutoLayout.addRow(2, quantidadeLabel, quantidadeTextField);
            attProdutoLayout.addRow(3, valorLabel, valorTextField);

            Button salvarButton = new Button("Salvar");
            salvarButton.setOnAction(saveEvent -> {

                int id = Integer.parseInt(idTextField.getText());
                String nome = nomeTextField.getText();
                int quantidade = Integer.parseInt(quantidadeTextField.getText());
                double valor = Double.parseDouble(valorTextField.getText());

                String response = up.callSoapWebService(SOAP_ENDPOINT_URL, "adicionarProduto", id, nome, quantidade, valor);

                Label resultadoLabel = new Label(response);
                resultadoLabel.setStyle("-fx-font-size: 14px;");

                VBox resultadoLayout = new VBox(10);
                resultadoLayout.setAlignment(Pos.CENTER);
                resultadoLayout.getChildren().addAll(resultadoLabel);

                Scene resultadoScene = new Scene(resultadoLayout, 300, 100);

                Stage resultadoStage = new Stage();
                resultadoStage.setTitle("Resultado");
                resultadoStage.setScene(resultadoScene);
                resultadoStage.show();

                attProdutoStage.close();
            });

            HBox salvarButtonLayout = new HBox(10);
            salvarButtonLayout.setAlignment(Pos.CENTER);
            salvarButtonLayout.getChildren().add(salvarButton);

            VBox addProdutoSceneLayout = new VBox(10);
            addProdutoSceneLayout.setAlignment(Pos.CENTER);
            addProdutoSceneLayout.getChildren().addAll(titleLabelAp, attProdutoLayout, salvarButtonLayout);

            Scene addProdutoScene = new Scene(addProdutoSceneLayout, 300, 250);
            attProdutoStage.setScene(addProdutoScene);
            attProdutoStage.show();
        });

        consultarProdutoButton.setOnAction(event -> {
            Stage consultarProdutoStage = new Stage();
            consultarProdutoStage.setTitle("Consultar Produto");

            Label titleLabelCp = new Label("Consultar Produto");
            titleLabelCp.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            titleLabelCp.setFont(Font.font("Times New Roman", 40));

            GridPane consultarProdutoLayout = new GridPane();
            consultarProdutoLayout.setAlignment(Pos.CENTER);
            consultarProdutoLayout.setHgap(10);
            consultarProdutoLayout.setVgap(10);
            consultarProdutoLayout.setPadding(new Insets(10));

            Label idLabel = new Label("ID do Produto:");

            TextField idTextField = new TextField();

            consultarProdutoLayout.addRow(0, idLabel, idTextField);

            Button consultarButton = new Button("Consultar");
            consultarButton.setOnAction(consultEvent -> {
                int id = Integer.parseInt(idTextField.getText());

                String response = cp.callSoapWebService(SOAP_ENDPOINT_URL, "consultarProduto", id);

                Label resultadoLabel = new Label(response);
                resultadoLabel.setStyle("-fx-font-size: 14px;");

                VBox resultadoLayout = new VBox(10);
                resultadoLayout.setAlignment(Pos.CENTER);
                resultadoLayout.getChildren().addAll(resultadoLabel);

                Scene resultadoScene = new Scene(resultadoLayout, 300, 150);

                Stage resultadoStage = new Stage();
                resultadoStage.setTitle("Resultado");
                resultadoStage.setScene(resultadoScene);
                resultadoStage.show();

                consultarProdutoStage.close();
            });

            HBox consultarButtonLayout = new HBox(10);
            consultarButtonLayout.setAlignment(Pos.CENTER);
            consultarButtonLayout.getChildren().add(consultarButton);

            VBox consultarProdutoSceneLayout = new VBox(10);
            consultarProdutoSceneLayout.setAlignment(Pos.CENTER);
            consultarProdutoSceneLayout.getChildren().addAll(titleLabelCp, consultarProdutoLayout, consultarButtonLayout);

            Scene consultarProdutoScene = new Scene(consultarProdutoSceneLayout, 300, 150);
            consultarProdutoStage.setScene(consultarProdutoScene);
            consultarProdutoStage.show();
        });

        listarProdutosButton.setOnAction(event -> {
            String response = lp.callSoapWebService(SOAP_ENDPOINT_URL, "listarProdutos");

            Label resultadoLabel = new Label(response);
            resultadoLabel.setStyle("-fx-font-size: 14px;");

            VBox resultadoLayout = new VBox(10);
            resultadoLayout.setAlignment(Pos.CENTER);
            resultadoLayout.getChildren().addAll(resultadoLabel);

            Scene resultadoScene = new Scene(resultadoLayout, 600, 400);

            Stage resultadoStage = new Stage();
            resultadoStage.setTitle("Lista de Produtos");
            resultadoStage.setScene(resultadoScene);
            resultadoStage.show();
        });

        removerProdutosButton.setOnAction(event -> {
            Stage removerProdutoStage = new Stage();
            removerProdutoStage.setTitle("Remover Produto");

            Label titleLabelRp = new Label("Remover Produto");
            titleLabelRp.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            titleLabelRp.setFont(Font.font("Times New Roman", 40));

            GridPane removerProdutoLayout = new GridPane();
            removerProdutoLayout.setAlignment(Pos.CENTER);
            removerProdutoLayout.setHgap(10);
            removerProdutoLayout.setVgap(10);
            removerProdutoLayout.setPadding(new Insets(10));

            Label idLabel = new Label("ID do Produto:");

            TextField idTextField = new TextField();

            removerProdutoLayout.addRow(0, idLabel, idTextField);

            Button removerButton = new Button("Remover");
            removerButton.setOnAction(removeEvent -> {
                int id = Integer.parseInt(idTextField.getText());

                String response = rp.callSoapWebService(SOAP_ENDPOINT_URL, "removerProduto", id);

                Label resultadoLabel = new Label(response);
                resultadoLabel.setStyle("-fx-font-size: 14px;");

                VBox resultadoLayout = new VBox(10);
                resultadoLayout.setAlignment(Pos.CENTER);
                resultadoLayout.getChildren().addAll(resultadoLabel);

                Scene resultadoScene = new Scene(resultadoLayout, 300, 100);

                Stage resultadoStage = new Stage();
                resultadoStage.setTitle("Resultado");
                resultadoStage.setScene(resultadoScene);
                resultadoStage.show();

                removerProdutoStage.close();
            });

            HBox removerButtonLayout = new HBox(10);
            removerButtonLayout.setAlignment(Pos.CENTER);
            removerButtonLayout.getChildren().add(removerButton);

            VBox removerProdutoSceneLayout = new VBox(10);
            removerProdutoSceneLayout.setAlignment(Pos.CENTER);
            removerProdutoSceneLayout.getChildren().addAll(titleLabelRp, removerProdutoLayout, removerButtonLayout);

            Scene removerProdutoScene = new Scene(removerProdutoSceneLayout, 300, 150);
            removerProdutoStage.setScene(removerProdutoScene);
            removerProdutoStage.show();
        });

        realizarVendaButton.setOnAction(event -> {
            Stage realizarVendaStage = new Stage();
            realizarVendaStage.setTitle("Realizar Venda");

            Label titleLabelVp = new Label("Realizar Venda");
            titleLabelVp.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            titleLabelVp.setFont(Font.font("Times New Roman", 40));

            GridPane realizarVendaLayout = new GridPane();
            realizarVendaLayout.setAlignment(Pos.CENTER);
            realizarVendaLayout.setHgap(10);
            realizarVendaLayout.setVgap(10);
            realizarVendaLayout.setPadding(new Insets(10));

            Label idLabel = new Label("ID do Produto:");
            TextField idTextField = new TextField();

            Label quantidadeLabel = new Label("Quantidade:");
            TextField quantidadeTextField = new TextField();

            realizarVendaLayout.addRow(0, idLabel, idTextField);
            realizarVendaLayout.addRow(1, quantidadeLabel, quantidadeTextField);

            Button venderButton = new Button("Vender");
            venderButton.setOnAction(venderEvent -> {
                int id = Integer.parseInt(idTextField.getText());
                int quantidade = Integer.parseInt(quantidadeTextField.getText());

                String response = vp.callSoapWebService(SOAP_ENDPOINT_URL, "venderProduto", id, quantidade);

                Label resultadoLabel = new Label(response);
                resultadoLabel.setStyle("-fx-font-size: 14px;");

                VBox resultadoLayout = new VBox(10);
                resultadoLayout.setAlignment(Pos.CENTER);
                resultadoLayout.getChildren().addAll(resultadoLabel);

                Scene resultadoScene = new Scene(resultadoLayout, 300, 100);

                Stage resultadoStage = new Stage();
                resultadoStage.setTitle("Resultado");
                resultadoStage.setScene(resultadoScene);
                resultadoStage.show();

                realizarVendaStage.close();
            });

            HBox venderButtonLayout = new HBox(10);
            venderButtonLayout.setAlignment(Pos.CENTER);
            venderButtonLayout.getChildren().add(venderButton);

            VBox realizarVendaSceneLayout = new VBox(10);
            realizarVendaSceneLayout.setAlignment(Pos.CENTER);
            realizarVendaSceneLayout.getChildren().addAll(titleLabelVp, realizarVendaLayout, venderButtonLayout);

            Scene realizarVendaScene = new Scene(realizarVendaSceneLayout, 300, 150);
            realizarVendaStage.setScene(realizarVendaScene);
            realizarVendaStage.show();
        });

    }

    public static void main(String[] args) {
        launch();
    }

}
