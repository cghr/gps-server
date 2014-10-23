package org.cghr.socket

/**
 * Created by ravitej on 24/10/14.
 */
class Client {


    public static void main(String[] args) {

        def s = new Socket("localhost", 4444);
        s.withStreams { input, output ->
            output << "requesting server ...\n"
            def response = input.newReader().readLine()

            if (response == "0;0")
                println "GPS Not Fixed"
            else
                println "GPS Fix Data :" + response


        }

    }
}
