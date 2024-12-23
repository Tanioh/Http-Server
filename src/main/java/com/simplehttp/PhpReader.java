package com.simplehttp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class PhpReader {
    public static String contentPhp(File f, String method, String parameters) throws IOException {
        System.out.println("Fichier : " + f.getAbsolutePath());
        System.out.println("Méthode : " + method);
        System.out.println("Paramètres : " + parameters);

        Vector<String> commands = new Vector<>();
        String filePath = f.getAbsolutePath(); // Chemin complet du fichier PHP
        if ("GET".equalsIgnoreCase(method)) {
            commands.add("php");
            commands.add("-r");
            commands.add("parse_str('" + parameters + "', $_GET); include('" + filePath.replace("\\", "\\\\") + "');");
        } else if ("POST".equalsIgnoreCase(method)) {
            commands.add("php");
            commands.add("-r");
            commands.add("parse_str('" + parameters + "', $_POST); include('" + filePath.replace("\\", "\\\\") + "');");
        }

        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        // Lire la sortie du script PHP
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }

        try {
            int exitCode = process.waitFor();  // Gérer l'exception InterruptedException
            if (exitCode != 0) {
                throw new IOException("Erreur lors de l'exécution de PHP (code de sortie : " + exitCode + ")");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // Restaurer l'état d'interruption
            throw new IOException("Le processus PHP a été interrompu", e);
        }

        return output.toString();
    }
}
