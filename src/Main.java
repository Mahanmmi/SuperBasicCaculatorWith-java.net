import java.io.IOException;

public class Main {
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
            new ClientInput("localhost", 18757);
        } catch (IOException e) {
            System.out.println("Cannot create socket");
        }
        System.out.println("Done testing!");

    }
}
