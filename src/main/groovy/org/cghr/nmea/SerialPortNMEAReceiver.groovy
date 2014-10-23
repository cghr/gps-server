package org.cghr.nmea

import gnu.io.CommPortIdentifier
import gnu.io.SerialPort
import net.sf.marineapi.nmea.event.SentenceEvent
import net.sf.marineapi.nmea.event.SentenceListener
import net.sf.marineapi.nmea.io.SentenceReader
import net.sf.marineapi.nmea.sentence.SentenceValidator

/**
 * Serial port example using GNU/RXTX libraries (see readme.txt). Scans through
 * all COM ports and seeks for NMEA 0183 data with default settings (4800
 * baud, 8 data bits, 1 stop bit and no parity). If NMEA data is found, starts
 * printing out all sentences the device is broadcasting.
 *
 * Notice that on Linux you may need to set read/write privileges on correct
 * port (e.g. <code>sudo chmod 666 /dev/ttyUSB0<code>) or add your user in
 * dialout group before running this example.
 *
 * @author Kimmo Tuukkanen
 */
public class SerialPortNMEAReceiver implements SentenceListener {

    GPSFixSentenceParser parser = new GPSFixSentenceParser()
    boolean hasFix = false
    Map gps = [:]



    public void readingPaused() {
        println("-- Paused --")
    }


    public void readingStarted() {
        println("-- Started --")
    }


    public void readingStopped() {
        println("-- Stopped --")
    }

    public void sentenceRead(SentenceEvent event) {

        // here we receive each sentence read from the port
        String sentence = event.getSentence().toSentence()
        if (!sentence.contains("GPGGA"))
            return

        if (parser.hasGPSFix(sentence)) {
            Map gps = parser.getGPSFix(sentence)
            this.hasFix = true
            this.gps = gps
        }


    }

    /**
     * Scan serial ports for NMEA data.
     *
     * @return SerialPort from which NMEA data was found, or null if data was
     *         not found in any of the ports.
     */
    private SerialPort getSerialPort() {
        try {
            Enumeration<?> e = CommPortIdentifier.getPortIdentifiers()

            while (e.hasMoreElements()) {
                CommPortIdentifier id = (CommPortIdentifier) e.nextElement()

                if (id.getPortType() == CommPortIdentifier.PORT_SERIAL) {

                    SerialPort sp = (SerialPort) id.open("SerialExample", 30)

                    sp.setSerialPortParams(4800, SerialPort.DATABITS_8,
                            SerialPort.STOPBITS_1, SerialPort.PARITY_NONE)

                    InputStream is = sp.getInputStream()
                    InputStreamReader isr = new InputStreamReader(is)
                    BufferedReader buf = new BufferedReader(isr)

                    println("Scanning port " + sp.getName())

                    // try each port few times before giving up
                    for (int i = 0; i < 5; i++) {
                        try {
                            String data = buf.readLine()
                            if (SentenceValidator.isValid(data)) {
                                println("NMEA data found!")
                                return sp
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace()
                        }
                    }
                    is.close()
                    isr.close()
                    buf.close()
                }
            }
            println("ERROR: Insert the device properly.Check if the light is blinking")

        } catch (Exception e) {
            e.printStackTrace()
        }

        return null
    }

    private void init() {

        println "Initializing Serial Port listener...."

        try {
            SerialPort sp = getSerialPort()

            if (sp != null) {
                InputStream is = sp.getInputStream()
                SentenceReader sr = new SentenceReader(is)
                sr.addSentenceListener(this)
                sr.start()
            }

        } catch (IOException e) {
            e.printStackTrace()
        }
    }


}