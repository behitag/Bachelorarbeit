# Bachelorarbeit

Vorstellung der Ordner:
- tools: Tools für das Erhobung der CPU-Werte und Auslesen der Screenshots (.ps1-script in \tools\ps1 erfordert Admin-Rechte)
- var0: enthält 2 extra Anpassungen mittels batch-script und REST-API(siehe schriftliche Arbeit)
- var1: Variante 1
- var2: Variante 2
- var3: Variante 3
- var4: Variante 4
#-------------------------------------------------------------------------------------------------

Um die Simulationen durchzuführen müssten folgende Technologien installiert sein:
- Java
- Docker
- PostgreSQL Server
- pgAdmin
- RabbitMQ (und Admin-Plugin aktivieren)

#----------------------------------------------------------------------------------------------

Um die Simulationen durchzuführen:
- in den gewünschten Varianten switchen, z.B. var1
- mittels Maven den Project bilder und die JAR datei ausführen lassen --> MSA speichert dadurch die Belege in RMQ (20 Millionen Belege)
- MSA kann vorher natürlich bei Bedarf gestoppt werden. Die Belege bleiben unberührt.
- in MSB switchen
- den Befehl "docker-compose up --build" ausführen, dadurch werden 4 Docker Kontainer mit den nötigen PostgreSQL und RMQ Verbindungen gebildet, sie fangen sofort an die Belge zu bearbeiten und in PostgreSQL zu speichern.
- MSb kann auch nativ in Java ausgeführt werden, dafür müssen jedoch die Settings in application.properties entsprechend geändert werden. Es kann dafür nach den Kommentare in Quellcode orientiert werden.

# -------------------------------------------------------------------------------------------------

Um batch-insert zu testen, kann der Variabel "groupSize" in der Datei var0\REST-API\msb\src\main\java\HKR\MSB\controller\BelegListener.java entsprechend angepasst werden.







