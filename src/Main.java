import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static void clientTester() {
        Socket client;
        PrintWriter writer;
        BufferedReader reader;
        try {
            client = new Socket("localhost", 18757);
            writer = new PrintWriter(client.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            System.out.println("Can't connect");
            return;
        }
        System.out.println("Connected!");


        while (true) {
            try {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("im done being merciful")) {
                    try {
                        reader.close();
                        writer.close();
                        client.close();
                    } catch (IOException e) {
                        System.out.println("Cannot close client");
                    }
                    return;
                }
                writer.println(input);
                System.out.println(reader.readLine());
            } catch (IOException e) {
                System.out.println("Cannot write: " + e.getMessage());
            }
        }
    }

    private static void initServer() {
        Server server = null;
        try {
            server = new Server();
        } catch (IOException e) {
            System.out.println("Cannot create server");
        }
        if (server != null) {
            Thread serverThread = new Thread(server);
            serverThread.start();
        }
    }

    public static void main(String[] args) {
        initServer();
        System.out.println("Server Created");
        try {
            new ClientInput("localhost",18757);
        } catch (IOException e) {
            System.out.println("Cannot create socket");
        }
        System.out.println("Done testing!");

    }
}
