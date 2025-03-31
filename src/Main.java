import controller.Compactar;
import controller.WebScraping;
import dataTransformation.ModificarCSV;
import dataTransformation.PDFExtrair;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        WebScraping.rasparDados(); //faz o download dos Anexos I e II
        Compactar.compactarEmZIP("resultados", "resultados/anexos.zip",".pdf"); // Compacta os Anexos
        PDFExtrair.extrair(); //Extrai o CSV dos PDFs
        Compactar.compactarEmZIP("resultados","resultados/Teste_Diogo.zip",".csv");// Compacta o CSV

        Map<String, String> substituicoes = new HashMap<>();// modifica o texto pedido
        substituicoes.put("OD", "Seg. Odontológica");
        substituicoes.put("AMB", "Seg. Ambulatorial");

        ModificarCSV modifier = new ModificarCSV(substituicoes);
        modifier.processarCsv("resultados/anexoI.csv", "resultados/anexoI_Editado.csv");

    }
}
