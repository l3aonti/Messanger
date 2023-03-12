package generalChat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Vector;

public class ServerGeneralChat implements Runnable {
    private Vector<Client> clients;
    ServerSocket server;

    public ServerGeneralChat() {
        clients = new Vector<>();
        try {
            server = new ServerSocket(2255);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        Thread one = new Thread(new ServerHandler());
        Thread two = new Thread(new ServerIO());
        one.setName("sHandler");
        two.setName("io");
        one.start();
        two.start();
    }

    class ServerIO implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            }catch (Exception e){}
            while (true) {
                for (Client client : clients) {
                    BufferedReader read = null;
                    try {
                        read = new BufferedReader(
                                new InputStreamReader(client.getSocket().getInputStream()));
                        String message;
                        if((message = read.readLine())!= null){
                            sendMessage(message);
                        }
                        else{
                            continue;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }

        public void sendMessage( String message ) {
            for (Client c : clients) {
                try {
                    PrintWriter writer = new PrintWriter(c.getSocket().getOutputStream());
                    writer.println(message);
                    writer.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    class ServerHandler implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Socket sock = server.accept();
                    clients.add(new Client(sock));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    class Client {
        private String name;
        private Socket socket;

        Client(Socket socket) {
            name = "oleg";
            this.socket = socket;
            /*
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                name = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
             */
        }

        void SetName(String name) {
            this.name = name;
        }

        Socket getSocket() {
            return socket;
        }
    }
}