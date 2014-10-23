package org.cghr.nmea

/**
 * Created by ravitej on 23/10/14.
 */
class GPSFixSentenceParser {

    int gpsFixIdentifierIndex = 6
    String delimiter = ","

    boolean hasGPSFix(String sentence) {

        String[] data = sentence.split(delimiter)
        boolean hasFix = data[gpsFixIdentifierIndex] == "1"

        println(hasFix ? "Fix ✓✓✓✓✓✓✓" : "Error: No Fix xxxxxxx")
        return hasFix

    }

    Map getGPSFix(String sentence) {

        String[] data = sentence.split(delimiter)
        [latitude: data[2], longitude: data[4]]
    }


}
