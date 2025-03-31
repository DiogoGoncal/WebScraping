package dataTransformation;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ModificarCSV {
    private final Map<String, String> substituicoes = new HashMap<>();

    // Construtor que recebe múltiplas substituições
    public ModificarCSV(Map<String, String> substituicoes) {
        this.substituicoes.putAll(substituicoes);
    }

    // Método para processar o CSV aplicando todas as substituições
    public void processarCsv(String inputFilePath, String outputFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                for (Map.Entry<String, String> entry : substituicoes.entrySet()) {
                    line = line.replace(entry.getKey(), entry.getValue());
                }
                writer.write(line);
                writer.newLine();
            }

            System.out.println("Arquivo processado com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }

