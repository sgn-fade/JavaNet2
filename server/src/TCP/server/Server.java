package TCP.server;

import TCP.intefaces.Executable;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    //MetroCardBank bank;
    private ServerSocket serverSocket = null;
    private final int port;
    private boolean isStopped;
    public Server(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        Server srv = new Server(7891);
        srv.start();
    }
    @Override
    public void run() {

        try {
            this.serverSocket = new ServerSocket(port);
            System.out.println("Metro Server started");
            while (!isStopped) {
                System.out.println("New Client Waiting...");
                Socket clientSocket = serverSocket.accept();
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

                Executable ex = (Executable) in.readObject();
                double startTime = System.nanoTime();
                Object output = ex.execute();
                double endTime = System.nanoTime();
                double completionTime = endTime - startTime;
                ResultImpl r = new ResultImpl(output, completionTime);
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

                out.writeObject(r);
                out.flush();
                out.close();
                in.close();

            }
        } catch (IOException e) {
            if (isStopped) {
                System.out.println("Server stopped!");
                return;
            }
            System.out.println("Error: " + e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            stopServer();
            System.out.println("Metro Server stopped");
        }
    }

    public synchronized void stopServer() {
        try {
            serverSocket.close();
            isStopped = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
