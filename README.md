GPS Utility module which directly talks to connected GPS devices via NMEA 0183 protocol and makes the GPS fix data available on a socket server listening
to port:4444

Run the Utility:
===============

 Requirements:
 =============
 * JDK 1.7 or higher
 * gradle build system
 
 1.Clone the project.
 2.Unzip and cd to the project.
 3.Run gradle fatJar which creates a gps-server.jar with dependencies.
 4.Run the jar with (java -jar gps-server.jar)
 5.This will run the gps-server and makes the GPS fix data available at socket port:4444
 
How it Works: 