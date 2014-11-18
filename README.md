GPS Utility module which directly talks to connected GPS devices via NMEA 0183 protocol and makes the GPS fix data available on a socket server listening
to port:4444

Requirements:
=============

 * JDK 1.7 or higher
 * gradle build system
 
Run the Utility:
===============

 * Clone the project.
 * Unzip and cd to the project.
 * Run `gradle fatJar` which creates a gps-server.jar with dependencies in the build directory
 * Run the jar with command (java -jar gps-server.jar)
 * This will run the gps-server and makes the GPS fix data available at socket port:4444 provided the GPS device is connected and working
 
How it Works:
=============
  GPS Server uses marine-api library to parse the NMEA sentences emitted by various devices connected to COMM port.Gps Server receives all the 
  NMEA sentences sent by the GPS device and filters out the sentence that has a GPS fix.
  eg. 
  
  $--GGA,hhmmss.ss,llll.ll,a,yyyyy.yy,a,x,xx,x.x,x.x,M,x.x,M,x.x,xxxx<br>
    hhmmss.ss = UTC of position <br>
    llll.ll = latitude of position<br>
    a = N or S<br>
    yyyyy.yy = Longitude of position<br>
    a = E or W <br>
    x = GPS Quality indicator (0=no fix, 1=GPS fix, 2=Dif. GPS fix)<br> 
    xx = number of satellites in use <br>
    x.x = horizontal dilution of precision<br> 
    x.x = Antenna altitude above mean-sea-level<br>
    M = units of antenna altitude, meters <br>
    x.x = Geoidal separation<br>
    M = units of geoidal separation, meters<br> 
    x.x = Age of Differential GPS data (seconds)<br> 
    xxxx = Differential reference station ID <br>
  
