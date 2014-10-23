package org.cghr.nmea

import spock.lang.Specification


/**
 * Created by ravitej on 23/10/14.
 */
class GPSFixSentenceParserSpec extends Specification {

    GPSFixSentenceParser parser

    String fixSentence = '$GPGGA,123519,4807.038,N,01131.000,E,1,08,0.9,545.4,M,46.9,M,,*47'
    String nonFixSentence = '$GPGGA,123519,,,,,0,08,0.9,545.4,M,46.9,M,,*47'

    def setup() {
        parser = new GPSFixSentenceParser()
    }

    def "should return false for non-gps fix sentence"() {
        expect:
        parser.hasGPSFix(nonFixSentence) == false
    }

    def "should return true for gps fix sentence"() {

        expect:
        parser.hasGPSFix(fixSentence) == true
    }

    def "should get gps readings for a valid gps sentence"() {
        expect:
        parser.getGPSFix(fixSentence) == [latitude: '4807.038', longitude: '01131.000']
    }


}