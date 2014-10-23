package org.cghr.socket

import org.cghr.nmea.SerialPortNMEAReceiver

/**
 * Created by ravitej on 23/10/14.
 */
class GPSSocketServer {

    public static void main(String[] args) {

        int port = 4444
        SerialPortNMEAReceiver receiver = new SerialPortNMEAReceiver()
        receiver.init()

        ServerSocket server = new ServerSocket(port)
        println "Server Started @ port $port"

        while (true) {

            server.accept { socket ->

                socket.withStreams { input, output ->

                    def reader = input.newReader()
                    def buffer = reader.readLine()
                    println "Request received: "
                    Map gps = receiver.gps
                    output << receiver.hasFix ? gps.latitude + ";" + gps.longitude : "0;0"
                }
                println 'Processing/thread completed'

            }

        }


    }
}