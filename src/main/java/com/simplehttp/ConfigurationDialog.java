package com.simplehttp;

import java.awt.*;
import java.io.*;
import java.util.Properties;
import javax.swing.*;

public class ConfigurationDialog extends JDialog {
    private JTextField hostField;
    private JTextField portField;
    private JTextField documentRootField;
    private JCheckBox phpEnabledCheckBox;
    private Properties properties;

    public ConfigurationDialog(JFrame parent) {
        super(parent, "Configuration du serveur", true);
        setSize(500, 300);
        setLocationRelativeTo(parent);
        
        properties = new Properties();
        loadCurrentConfig();
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.0;
        
        // Host
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Host:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        hostField = new JTextField(properties.getProperty("server.host", "0.0.0.0"));
        hostField.setPreferredSize(new Dimension(200, 25));
        panel.add(hostField, gbc);
        
        // Port
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Port:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        portField = new JTextField(properties.getProperty("server.port", "8081"));
        portField.setPreferredSize(new Dimension(200, 25));
        panel.add(portField, gbc);
        
        // Document Root
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Document Root:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        documentRootField = new JTextField(properties.getProperty("server.document_root", "./htdocs"));
        documentRootField.setPreferredSize(new Dimension(200, 25));
        panel.add(documentRootField, gbc);
        
        // Bouton parcourir
        gbc.gridx = 2;
        gbc.weightx = 0.0;
        JButton browseButton = new JButton("...");
        browseButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                documentRootField.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });
        panel.add(browseButton, gbc);
        
        // PHP Enabled Checkbox
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 3;
        phpEnabledCheckBox = new JCheckBox("Activer PHP");
        phpEnabledCheckBox.setSelected(Boolean.parseBoolean(properties.getProperty("server.php_enabled", "true")));
        panel.add(phpEnabledCheckBox, gbc);
        
        // Panel pour les boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        JButton okButton = new JButton("Enregistrer");
        okButton.setPreferredSize(new Dimension(100, 30));
        okButton.addActionListener(e -> {
            if (saveConfiguration()) {
                dispose();
            }
        });
        
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(okButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(cancelButton);
        
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setResizable(false);
    }
    
    private void loadCurrentConfig() {
        try (InputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        } catch (IOException ex) {
            // Si le fichier n'existe pas, on utilisera les valeurs par défaut
        }
    }
    
    private boolean saveConfiguration() {
        try {
            int port = Integer.parseInt(portField.getText().trim());
            if (port < 1 || port > 65535) {
                JOptionPane.showMessageDialog(this, 
                    "Le port doit être entre 1 et 65535",
                    "Erreur de validation",
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            properties.setProperty("server.host", hostField.getText().trim());
            properties.setProperty("server.port", String.valueOf(port));
            properties.setProperty("server.document_root", documentRootField.getText().trim());
            properties.setProperty("server.php_enabled", String.valueOf(phpEnabledCheckBox.isSelected()));
            
            try (OutputStream output = new FileOutputStream("config.properties")) {
                properties.store(output, "Configuration du serveur HTTP");
            }
            
            JOptionPane.showMessageDialog(this,
                "Configuration enregistrée avec succès.\nRedémarrez le serveur pour appliquer les changements.",
                "Succès",
                JOptionPane.INFORMATION_MESSAGE);
            
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Le port doit être un nombre valide",
                "Erreur de validation",
                JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "Erreur lors de l'enregistrement de la configuration : " + e.getMessage(),
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}