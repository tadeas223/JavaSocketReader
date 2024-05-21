package org.example;

import java.io.IOException;
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

        System.out.print("server Ip: ");
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

        Scanner scIn = new Scanner(inSocket.getInputStream());
        Scanner scOut = new Scanner(outSocket.getInputStream());

        PrintStream prtIn = new PrintStream(inSocket.getOutputStream());
        PrintStream prtOut = new PrintStream(outSocket.getOutputStream());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    String msg = scIn.nextLine();

                    System.out.println(msg);

                    prtOut.println(msg);
                }
            }
        });

        thread.start();

        while(true){
            String msg = scOut.nextLine();

            System.out.println(msg);

            prtIn.println(msg);
        }

    }
}
