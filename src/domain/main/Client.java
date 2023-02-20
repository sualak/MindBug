package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable
{
    private Socket server;
    private BufferedReader in;
    private PrintWriter out;

    //Localhost mit ip adresse des hosts ersetzen
    private final String ipAdresse = "localhost";

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }


    @Override
    public void run() {
        try {
            server = new Socket(ipAdresse, 4999);

            out = new PrintWriter(server.getOutputStream(), true);
            out.println("is it working");

            in = new BufferedReader(new InputStreamReader(server.getInputStream()));

            String str = in.readLine();
            System.out.println("server : " + str);
            str = in.readLine();
            System.out.println(str);
            InputHandler inputHandler = new InputHandler();
            Thread t = new Thread(inputHandler);
            t.start();

            String inMassage;
            while ((inMassage = in.readLine()) != null)
            {
                System.out.println(inMassage);
            }
        }
        catch (Exception e) {
            shutdown();
        }
    }

    public void shutdown()
    {
        try {
            in.close();
            out.close();
            server.close();
        } catch (IOException e) {
        }


    }

    class InputHandler implements Runnable
    {

        @Override
        public void run() {
            try {
                BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));

                boolean connected = true;
                while (connected)
                {
                    String massage = inReader.readLine();
                    if(massage.equals("disconnect all sockeds closed"))
                    {
                        inReader.close();
                        shutdown();
                        connected = false;
                    } else {
                        out.println(massage);
                    }
                }
            }
            catch (Exception e)
            {
                shutdown();
            }
        }
    }
}
