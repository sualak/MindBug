package main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    public static void a(String[] args) {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(4999);
            Socket s = socket.accept();
            System.out.println("client connected");
            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader bf = new BufferedReader(in);

            String str = bf.readLine();
            System.out.println("client : " + str);

            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            out.println("yes");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
