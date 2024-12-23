#!/bin/bash
echo "Installation du Simple HTTP Server..."

# Vérifie si Java est installé
if ! command -v java &> /dev/null
then
    echo "Java n'est pas installé! Veuillez installer Java 11 ou supérieur."
    exit 1
fi

# Crée le dossier d'installation
INSTALL_DIR="/opt/SimpleHttpServer"
sudo mkdir -p "$INSTALL_DIR"

# Copie les fichiers
sudo cp "build/launch4j/SimpleHttpServer.exe" "$INSTALL_DIR/"
sudo cp -r "src/main/resources/htdocs/" "$INSTALL_DIR/htdocs/"
sudo cp "src/main/resources/config.properties" "$INSTALL_DIR/"

# Crée un raccourci sur le bureau
DESKTOP_FILE="$HOME/Desktop/SimpleHttpServer.desktop"
echo "[Desktop Entry]" > "$DESKTOP_FILE"
echo "Name=Simple HTTP Server" >> "$DESKTOP_FILE"
echo "Exec=java -jar $INSTALL_DIR/SimpleHttpServer.exe" >> "$DESKTOP_FILE"
echo "Type=Application" >> "$DESKTOP_FILE"
chmod +x "$DESKTOP_FILE"

echo "Installation terminée!"
echo "Un raccourci a été créé sur votre bureau."
