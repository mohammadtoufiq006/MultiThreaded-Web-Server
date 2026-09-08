import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

  public void run() {
    int port = 8010;
    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(10000);
    } catch(IOException e) {
      e.printStackTrace();
      return;
    }
    
    while(true) {
      try{
        System.out.println("Server is listening on port: " + port);
        Socket acceptedConnection = serverSocket.accept();
        System.out.println("Connection accepted from client" + acceptedConnection.getRemoteSocketAddress());
        PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream());
        BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream()));
        toClient.println("Hello from the server!");
        toClient.close();
        fromClient.close();
        acceptedConnection.close();
      } catch(IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    Server server = new Server();
    try{
      server.run();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}