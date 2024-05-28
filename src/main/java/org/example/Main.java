package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        String serverIp;
        int serverPort;
        int inPort;

        System.out.print("server IP: ");
        serverIp = sc.next();

        System.out.print("server port: ");
        serverPort = sc.nextInt();

        System.out.print("in port:");
        inPort = sc.nextInt();

        System.out.println("--------SESSION--------");

        sc.close();

        Socket outSocket = new Socket(serverIp,serverPort);
        ServerSocket serverSocket = new ServerSocket(inPort);

        Socket inSocket = serverSocket.accept();

        InputStream inStream = inSocket.getInputStream();
        InputStream outStream = outSocket.getInputStream();

        PrintStream prtIn = new PrintStream(inSocket.getOutputStream());
        PrintStream prtOut = new PrintStream(outSocket.getOutputStream());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    int b = 0;
                    try {
                        b = inStream.read();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.print((char)b);

                    prtOut.print((char)b);
                }
            }
        });

        thread.start();

        while(true){
            int b = 0;
            try {
                b = outStream.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.print((char)b);

            prtIn.print((char)b);
        }

    }
}
