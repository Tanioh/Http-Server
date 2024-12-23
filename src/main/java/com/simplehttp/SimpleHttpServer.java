import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.swing.*;

public class SimpleHttpServer {
    private static String SERVER_HOST;
    private static int SERVER_PORT;
    private static String DOCUMENT_ROOT;
    private static boolean PHP_ENABLED;

    private static JFrame frame;
    private static JButton startButton;
    private static JButton configButton;
    private static JTextArea logArea;

    private static ServerSocket serverSocket; // Le serveur doit être contrôlé pour pouvoir être arrêté
    private static Thread serverThread; // Le thread du serveur doit aussi être contrôlé

    public static void main(String[] args) {
        // Créer l'interface graphique
        createGUI();
    }

    private static void createGUI() {
        // Créer la fenêtre principale
        frame = new JFrame("Serveur HTTP Simple");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // Créer un panneau pour les boutons
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Créer le bouton pour démarrer le serveur
        startButton = new JButton("Démarrer le serveur");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadConfig(); // Charger la configuration avant de démarrer le serveur
                startServer(); // Démarrer le serveur
            }
        });
        panel.add(startButton);

        // Créer le bouton pour ouvrir le fichier de configuration
        configButton = new JButton("Ouvrir le fichier de configuration");
        configButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openConfigFile(); // Ouvrir le fichier de configuration
            }
        });
        panel.add(configButton);

        // Créer une zone de texte pour afficher les logs
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);

        // Ajouter les composants à la fenêtre
        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Afficher la fenêtre
        frame.setVisible(true);
    }

    private static void loadConfig() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            properties.load(input);

            // Charger les valeurs depuis le fichier
            SERVER_HOST = properties.getProperty("server.host", "0.0.0.0");
            SERVER_PORT = Integer.parseInt(properties.getProperty("server.port", "8081"));
            DOCUMENT_ROOT = properties.getProperty("server.document_root", "./htdocs");
            PHP_ENABLED = Boolean.parseBoolean(properties.getProperty("server.php_enabled", "true"));

            logArea.append("Configuration chargée :\n");
            logArea.append("Host : " + SERVER_HOST + "\n");
            logArea.append("Port : " + SERVER_PORT + "\n");
            logArea.append("Document Root : " + DOCUMENT_ROOT + "\n");
            logArea.append("PHP activé : " + PHP_ENABLED + "\n");
        } catch (IOException ex) {
            logArea.append("Erreur lors de la lecture du fichier de configuration : " + ex.getMessage() + "\n");
        }
    }

    private static void startServer() {
        // Si le serveur est déjà en cours d'exécution, l'arrêter avant de démarrer un nouveau serveur
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
                logArea.append("Ancien serveur arrêté.\n");
            } catch (IOException e) {
                logArea.append("Erreur lors de l'arrêt du serveur : " + e.getMessage() + "\n");
            }
        }

        serverThread = new Thread(() -> {
            try {
                // Démarrer le serveur avec le nouveau port
                serverSocket = new ServerSocket(SERVER_PORT, 50, InetAddress.getByName(SERVER_HOST));
                logArea.append("Serveur en écoute sur le port " + SERVER_PORT + "...\n");

                while (true) {
                    try (Socket clientSocket = serverSocket.accept()) {
                        logArea.append("Connexion reçue de " + clientSocket.getInetAddress() + "\n");
                        handleClient(clientSocket);
                    } catch (Exception e) {
                        logArea.append("Erreur lors du traitement de la requête : " + e.getMessage() + "\n");
                    }
                }
            } catch (IOException e) {
                logArea.append("Erreur lors du démarrage du serveur : " + e.getMessage() + "\n");
            }
        });
        serverThread.start();
    }

    private static void handleClient(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        OutputStream out = clientSocket.getOutputStream();

        // Lire la requête HTTP
        String requestLine = in.readLine();
        if (requestLine == null || requestLine.isEmpty()) {
            sendResponse(out, "HTTP/1.1 400 Bad Request", "text/html", "<h1>400 Bad Request</h1>");
            return;
        }
        logArea.append(requestLine + "\n");

        String[] requestParts = requestLine.split(" ");
        if (requestParts.length < 2) {
            sendResponse(out, "HTTP/1.1 400 Bad Request", "text/html", "<h1>400 Bad Request</h1>");
            return;
        }

        String httpMethod = requestParts[0];
        String path = requestParts[1];
        String filePath = DOCUMENT_ROOT + (path.equals("/") ? "" : path);

        // Lire les en-têtes HTTP
        Map<String, String> headers = new HashMap<>();
        String line;
        int contentLength = 0;

        while ((line = in.readLine()) != null && !line.isEmpty()) {
            String[] headerParts = line.split(": ", 2);
            if (headerParts.length == 2) {
                headers.put(headerParts[0], headerParts[1]);
                if ("Content-Length".equalsIgnoreCase(headerParts[0])) {
                    contentLength = Integer.parseInt(headerParts[1]);
                }
            }
        }

        // Lire le corps de la requête pour POST
        StringBuilder body = new StringBuilder();
        if ("POST".equalsIgnoreCase(httpMethod) && contentLength > 0) {
            char[] buffer = new char[contentLength];
            in.read(buffer, 0, contentLength);
            body.append(buffer);
        }

        // Traiter le chemin demandé
        File file = new File(filePath);
        if (!file.exists()) {
            sendResponse(out, "HTTP/1.1 404 Not Found", "text/html", "<h1>404 Not Found</h1>");
            return;
        }

        // Dans la méthode handleClient(), remplacez la partie qui gère les répertoires par :
    if (file.isDirectory()) {
        File indexHtml = new File(file, "index.html");
        File indexPhp = new File(file, "index.php");

        if (indexHtml.exists()) {
            file = indexHtml;
        } else if (indexPhp.exists() && PHP_ENABLED) {
            file = indexPhp;
        } else {
            // Générer la liste des fichiers
            StringBuilder fileListHtml = new StringBuilder("<html><body><h1>Index of " + path + "</h1><ul>");
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    String fileName = f.getName();
                    String fileUrl = path.endsWith("/") ? path + fileName : path + "/" + fileName;
                    fileListHtml.append("<li><a href=\"").append(fileUrl).append("\">")
                            .append(fileName)
                            .append(f.isDirectory() ? "/" : "")
                            .append("</a></li>");
                }
            }
            fileListHtml.append("</ul></body></html>");

            sendResponse(out, "HTTP/1.1 200 OK", "text/html", fileListHtml.toString());
            return;
        }
    }

        // Gérer les fichiers
        if (file.getName().endsWith(".php")) {
            if (PHP_ENABLED) {
                // Utiliser PhpReader pour exécuter le script PHP
                String responseBody = PhpReader.contentPhp(file, httpMethod, body.toString());
                sendResponse(out, "HTTP/1.1 200 OK", "text/html", responseBody);
            } else {
                // Si PHP est désactivé, envoyer une erreur 403 Forbidden
                sendResponse(out, "HTTP/1.1 403 Forbidden", "text/html", 
                    "<h1>403 Forbidden</h1><p>Le support PHP est désactivé sur ce serveur.</p>");
            }
        } else {
            // Lire le contenu du fichier
            String contentType = determineContentType(file.getName());
            String responseBody = new String(Files.readAllBytes(file.toPath()));
            sendResponse(out, "HTTP/1.1 200 OK", contentType, responseBody);
        }
    }

    private static void sendResponse(OutputStream out, String status, String contentType, String body) throws IOException {
        // Format de date pour les en-têtes HTTP
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String now = dateFormat.format(new Date());

        // Générer un ETag simple basé sur le hash du contenu
        String eTag = "\"" + String.valueOf(body.hashCode()) + "\"";

        // Construire la réponse avec tous les en-têtes
        StringBuilder response = new StringBuilder();
        response.append(status).append("\r\n");
        response.append("Content-Type: ").append(contentType).append("\r\n");
        response.append("Content-Length: ").append(body.length()).append("\r\n");
        response.append("Server: SimpleHTTP/1.1\r\n");
        response.append("Date: ").append(now).append("\r\n");
        response.append("Last-Modified: ").append(now).append("\r\n");
        response.append("ETag: ").append(eTag).append("\r\n");
        response.append("Cache-Control: public, max-age=86400\r\n"); // Cache d'un jour
        response.append("Connection: close\r\n");
        response.append("Access-Control-Allow-Origin: *\r\n"); // Permet CORS
        response.append("\r\n");
        response.append(body);

        // Envoyer la réponse
        out.write(response.toString().getBytes());
        out.flush();
    }

    private static String determineContentType(String fileName) {
        if (fileName.endsWith(".html")) return "text/html";
        if (fileName.endsWith(".css")) return "text/css";
        if (fileName.endsWith(".js")) return "application/javascript";
        if (fileName.endsWith(".png")) return "image/png";
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) return "image/jpeg";
        if (fileName.endsWith(".gif")) return "image/gif";
        return "application/octet-stream";
    }

    private static void openConfigFile() {
        ConfigurationDialog dialog = new ConfigurationDialog(frame);
        dialog.setVisible(true);
    }
}
