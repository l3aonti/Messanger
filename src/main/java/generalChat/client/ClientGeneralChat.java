package generalChat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class ClientGeneralChat implements Runnable {
    Socket sock;

    public ClientGeneralChat() {
        try {
            Thread.sleep(2000);
            sock = new Socket("127.0.0.1", 2255);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        Thread one = new Thread(new MessagesHandler());
        Thread two = new Thread(new MessageWriter());
        one.setName("handler");
        two.setName("writter");
        one.start();
        two.start();
    }

    class MessagesHandler implements Runnable {

        @Override
        synchronized public void run() {
            String message;
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                while (true) {
                    if ((message = reader.readLine()) != null) {
                        System.out.println(message);
                    }
                }
            } catch (Exception e) {}
        }
    }
    class MessageWriter implements Runnable{
        String[] str = {"Hello", "How old do u", "I like banana", "Lets Talking"};
        @Override
        synchronized public void run() {
            try {
                Thread.sleep(2000);
                PrintWriter writer = new PrintWriter(sock.getOutputStream());
                while (true){
                    Thread.sleep(2000);
                    writer.println(random());
                    writer.flush();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        String random(){
            int r = (int)(Math.random() * 4);
            return str[r];
        }
    }
}
