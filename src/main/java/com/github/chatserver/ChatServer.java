package com.github.chatserver;
import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServer {
    private ServerSocket server;

    public void listen() {
        try {
            server = new ServerSocket(18080);
            System.out.println("server start on port 18080");

            while(true) {
                Socket socket = server.accept();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main (String[] args) {
        ChatServer echo = new ChatServer();
        echo.listen();
    }
    
}
