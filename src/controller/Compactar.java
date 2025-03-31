package controller;

import java.io.*;
import java.util.zip.*;

public class Compactar {

    public static void compactarEmZIP(String downloadDir, String outputZIP, String fileExtension) {
        try {
            // Cria o arquivo ZIP
            try (FileOutputStream fileOut = new FileOutputStream(outputZIP);
                 ZipOutputStream zipOut = new ZipOutputStream(fileOut)) {

                // Verifica todos os arquivos no diretório com a extensão fornecida
                File folder = new File(downloadDir);
                File[] files = folder.listFiles((dir, name) -> name.endsWith(fileExtension));

                if (files != null && files.length > 0) {
                    for (File file : files) {
                        // Cria uma nova entrada para o arquivo no ZIP
                        ZipEntry entry = new ZipEntry(file.getName());
                        zipOut.putNextEntry(entry);

                        // Escreve o conteúdo do arquivo no ZIP
                        try (FileInputStream fileInput = new FileInputStream(file)) {
                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = fileInput.read(buffer)) != -1) {
                                zipOut.write(buffer, 0, bytesRead);
                            }
                        }
                        zipOut.closeEntry();  // Fecha a entrada do arquivo
                    }
                    System.out.println("Arquivos compactados com sucesso em: " + outputZIP);
                } else {
                    System.out.println("Nenhum arquivo " + fileExtension + " encontrado para compactar.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
