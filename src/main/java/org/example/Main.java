package org.example;

import generalChat.client.ClientGeneralChat;
import generalChat.server.ServerGeneralChat;

public class Main {
    public static void main(String[] args) {
        Thread one = new Thread(new ServerGeneralChat());
        Thread two = new Thread(new ClientGeneralChat());
        Thread three = new Thread(new ClientGeneralChat());
        one.start();
        two.start();
        three.start();
    }
}