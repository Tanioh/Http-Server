@echo off
echo Installation du Simple HTTP Server...

:: Vérifie si Java est installé
java -version >nul 2>&1
if errorlevel 1 (
    echo Java n'est pas installé! Veuillez installer Java 11 ou supérieur.
    pause
    exit /b 1
)

:: Crée le dossier d'installation
set INSTALL_DIR=C:\Program Files\SimpleHttpServer
if not exist "%INSTALL_DIR%" mkdir "%INSTALL_DIR%"

:: Copie les fichiers
xcopy /s /y "build\launch4j\SimpleHttpServer.exe" "%INSTALL_DIR%"
xcopy /s /y "src\main\resources\htdocs" "%INSTALL_DIR%\htdocs\"
copy /y "src\main\resources\config.properties" "%INSTALL_DIR%\"

:: Crée un raccourci sur le bureau
powershell "$WS = New-Object -ComObject WScript.Shell; $SC = $WS.CreateShortcut('%userprofile%\Desktop\Simple HTTP Server.lnk'); $SC.TargetPath = '%INSTALL_DIR%\SimpleHttpServer.exe'; $SC.Save()"

echo Installation terminée!
echo Un raccourci a été créé sur votre bureau.
pause