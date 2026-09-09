import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private static final int PORT = 8010;
    private static final int CLIENT_COUNT = 100;

    public Runnable getRunnable() {
        return () -> {
            try (
                Socket socket = new Socket(InetAddress.getByName("localhost"), PORT);
                PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader fromServer = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
                )
            ) {
                toServer.println("Hello from client " + socket.getLocalSocketAddress());
                String response = fromServer.readLine();
                System.out.println("Response from server: " + response);
            } catch (IOException ex) {
                System.err.println("Client connection failed: " + ex.getMessage());
            }
        };
    }

    public static void main(String[] args) {
        Client client = new Client();

        for (int i = 0; i < CLIENT_COUNT; i++) {
            Thread thread = new Thread(client.getRunnable(), "client-" + (i + 1));
            thread.start();
        }
    }
}
