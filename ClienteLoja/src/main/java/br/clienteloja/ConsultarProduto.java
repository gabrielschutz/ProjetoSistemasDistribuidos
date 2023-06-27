package br.clienteloja;

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPConnection;
import jakarta.xml.soap.SOAPConnectionFactory;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPEnvelope;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.soap.SOAPPart;

public class ConsultarProduto {

    public String callSoapWebService(String soapEndpointUrl, String soapAction, int id) {
        try {
            // Criar conexão SOAP
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Enviar SOAP Message para o servidor
            SOAPMessage soapRequest = createSOAPRequest(id);
            SOAPMessage soapResponse = soapConnection.call(soapRequest, soapEndpointUrl);

            // Extrair resposta do servidor
            Produtos produto = extractResponse(soapResponse);
            if (produto != null) {
                StringBuilder result = new StringBuilder();
                result.append("Produto encontrado:\n");
                result.append("ID: ").append(produto.getId()).append("\n");
                result.append("Nome: ").append(produto.getNome()).append("\n");
                result.append("Quantidade: ").append(produto.getQuantidade()).append("\n");
                result.append("Preço: ").append(produto.getPreco());
                return result.toString();
            } else {
                return "Produto não encontrado.";
            }
        } catch (Exception e) {
            return "ERRO:\n" + e.getMessage();
        }
    }

    private SOAPMessage createSOAPRequest(int id) throws Exception {
        // Criar mensagem SOAP
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        // Criar envelope SOAP
        createSoapEnvelope(soapMessage, id);
        soapMessage.saveChanges();

        return soapMessage;
    }

    private void createSoapEnvelope(SOAPMessage soapMessage, int id) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // Verificar no WSDL o namespace utilizado
        String myNamespace = "ws";
        String myNamespaceURI = "http://ws.br/";

        // Preencher SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

        // Preencher SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("consultarProduto", myNamespace);

        SOAPElement idElem = soapBodyElem.addChildElement("id");
        idElem.addTextNode(String.valueOf(id));
    }

    private Produtos extractResponse(SOAPMessage soapMessage) throws SOAPException {
        SOAPBody soapBody = soapMessage.getSOAPBody();
        SOAPElement responseElement = (SOAPElement) soapBody.getElementsByTagName("return").item(0);

        if (responseElement != null) {
            String idStr = getElementTextContent(responseElement, "id");
            String nome = getElementTextContent(responseElement, "nome");
            String quantidadeStr = getElementTextContent(responseElement, "quantidade");
            String precoStr = getElementTextContent(responseElement, "preco");

            if (idStr != null && nome != null && quantidadeStr != null && precoStr != null) {
                int id = Integer.parseInt(idStr);
                int quantidade = Integer.parseInt(quantidadeStr);
                double preco = Double.parseDouble(precoStr);

                Produtos produto = new Produtos(id, nome, quantidade, preco);
                return produto;
            }
        }

        return null; // Produto não encontrado ou dados incompletos
    }

    private String getElementTextContent(SOAPElement parentElement, String elementName) {
        SOAPElement element = (SOAPElement) parentElement.getElementsByTagName(elementName).item(0);
        if (element != null) {
            return element.getTextContent();
        }
        return null;
    }
}
