package dataTransformation;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PDFExtrair {
    public static void main(String[] args) {
        extrair();
    }

    public static void extrair() {
        try {
            // Carrega o PDF
            File pdfFile = new File("resultados/Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf");
            PDDocument document = PDDocument.load(pdfFile);

            // Extrai o texto usando PDFTextStripper
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            // Processa o texto extraído para uma tabela (exemplo simples)
            String[] lines = text.split("\n");

            // Grava as informações extraídas em um arquivo CSV com separador ';'
            File csvFile = new File("resultados/anexoI.csv");
            CSVWriter writer = new CSVWriter(new FileWriter(csvFile), ';', CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

            for (String line : lines) {
                // Aqui você pode dividir as linhas com base em um separador e salvar em uma tabela
                String[] values = line.split("\\s+");
                writer.writeNext(values);
            }

            writer.close();
            document.close();

            System.out.println("Gravado no CSV com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}