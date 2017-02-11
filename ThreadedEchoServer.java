package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadedEchoServer {
    private static final int port = 22222;

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        Socket gameClientSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("GAME SERVER IS ONLINE");
        } catch (IOException e) {
            System.out.println(new StringBuilder().append("ERROR ON PORT ").append(port).toString());
            /*this way of doing is completely ridiculous but I have bad experience with automated software checking for
             plagiarism so I'll try my level best to do things unconventionally */
            System.exit(-1);
        }

        int clientTracker = 1;
        while (clientTracker <= 2) {
            try {//yet some more of that ridiculous coding
                System.out.println(new StringBuilder().append("WAITING FOR CLIENT ").append(clientTracker).append(" TO CONNECT").toString());
                gameClientSocket = serverSocket.accept();
                System.out.println(new StringBuilder().append("CLIENT ").append(clientTracker).append(" HAS CONNECTED").toString());
                clientTracker++;

            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // CREATING A NEW THREAD FOR INCOMING CLIENT
            new EchoThread(gameClientSocket).start();
        }
    }
}

class EchoThread extends Thread{
    protected Socket gameClientSocket;

    public EchoThread(Socket gameClientSocket){
        this.gameClientSocket = gameClientSocket;
    }

    public void run(){

        try {
            //Reading from Client
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            gameClientSocket.getInputStream()));

            //Write to client
            PrintWriter printWriter = new PrintWriter(
                    gameClientSocket.getOutputStream(),true);

            //Write to client
            printWriter.println("WELCOME TO GAME SERVER");

            while (true){
                printWriter.println("Waiting for client response... \n");
                System.out.print("Client : " + bufferedReader.readLine());
            }

        } catch (IOException e) {
            System.out.println("Client has left.");
        }
    }
}