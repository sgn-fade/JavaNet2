package TCP.client;

import TCP.intefaces.Result;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
    private final String server;
    private final int port;

    public Client(String server, int port) {
        this.server = server;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            Socket client = new Socket(server, port);
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());

            System.out.println("Enter n: ");
            Scanner input = new Scanner(System.in);
            int num = input.nextInt();
            JobOne aJob = new JobOne(num);

            out.writeObject(aJob);
            out.flush();
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());

            Result r = (Result) in.readObject();
            System.out.println("result = " + r.output() + ", time taken = " + r.scoreTime() + "ns");
            out.close();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e);
        }
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 7891);
        client.start();
    }
}
