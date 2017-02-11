package client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {


    public static void main(String[] args) {

        try {
            Socket serverSocket = new Socket("localhost", 22222);

            //Reads from server
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(serverSocket.getInputStream())
            );

            //Writes to server
            PrintWriter printWriter = new PrintWriter(
                    serverSocket.getOutputStream(), true
            );

            //Prints welcome msg
            System.out.println(bufferedReader.readLine());

            //Client input system
            Scanner input = new Scanner(System.in);

            while (true) {
                System.out.println("Server says: " + bufferedReader.readLine());
                System.out.println("Client: ");
                printWriter.println(input.nextLine());
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
