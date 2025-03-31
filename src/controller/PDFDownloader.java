package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PDFDownloader {


    public static void downloadPDF(String pdfURL, String downloadDir) {
        try {
            URL url = new URL(pdfURL);  // Cria a URL a partir da URL fornecida
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();  // Abre a conexão
            httpConn.setRequestMethod("GET");
            httpConn.setConnectTimeout(5000);  // Tempo limite de conexão
            httpConn.setReadTimeout(5000);  // Tempo limite de leitura

            int responseCode = httpConn.getResponseCode();  // Obtém o código de resposta HTTP
            if (responseCode == HttpURLConnection.HTTP_OK) {  // Se a resposta for 200 OK
                // Extrai o nome do arquivo da URL
                String nomeArquivo = pdfURL.substring(pdfURL.lastIndexOf("/") + 1);
                String caminhoArquivo = downloadDir + File.separator + nomeArquivo;

                // Abre os fluxos de entrada e saída
                try (InputStream inputStream = httpConn.getInputStream();
                     FileOutputStream outputStream = new FileOutputStream(caminhoArquivo)) {

                    byte[] buffer = new byte[4096];  // Buffer para ler os dados
                    int leituraBytes;

                    // Lê e escreve os dados do PDF em pedaços
                    while ((leituraBytes = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, leituraBytes);  // Escreve os dados no arquivo
                    }
                    System.out.println("PDF baixado com sucesso: " + caminhoArquivo);
                }
            } else {
                System.out.println("Não foi possível baixar o PDF. Código de resposta: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
