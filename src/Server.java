import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private boolean isActive;

    private static Integer calculate(String basicInput) {
        String[] input = basicInput.split("(?=[+-])");
        int result = 0;
        for (String s : input) {
            try {
                result += Integer.parseInt(s);
            } catch (Exception e){
                return null;
            }
        }
        return result;
    }

    Server() throws IOException {
        serverSocket = new ServerSocket(18757);
        isActive = false;
    }

    public void stop() {
        try {
            isActive = false;
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Cannot close server");
        }
    }

    @Override
    public void run() {
        isActive = true;
        while (isActive) {
            Socket client;
            BufferedReader reader;
            PrintWriter writer;
            try {
                client = serverSocket.accept();
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                writer = new PrintWriter(client.getOutputStream(), true);
            } catch (IOException e) {
                System.out.println("Cannot access client: " + e.getMessage());
                continue;
            }

            //Calculating
            try {
                String inp = reader.readLine();
                while (inp != null) {
                    Integer ans = calculate(inp);
                    if (ans != null) {
                        writer.println(calculate(inp));
                    } else {
                        writer.println("Unavailable input");
                    }
                    inp = reader.readLine();
                }
            } catch (IOException e) {
                System.out.println("Cannot read/write from/to client: " + e.getMessage());
            }


            try {
                client.close();
            } catch (IOException e) {
                System.out.println("Cannot close client: " + e.getMessage());
            }

        }
    }
}
