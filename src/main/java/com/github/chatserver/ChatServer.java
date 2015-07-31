package com.github.chatserver;
import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServer {
    private ServerSocket server;
    public static TreeMap<String, ChatClientHandler> userAssembly = new TreeMap<String, ChatClientHandler>();
    public static HashMap<String, Command> commandMap = new HashMap<String, Command>(); 

    public void listen() {
        try {
            server = new ServerSocket(18080);
            System.out.println("server start on port 18080");
            setCommandMap(); 
            while(true) {
                Socket socket = server.accept();
                int n = ChatClientHandler.getUserNum(); 
                System.out.println("undefined" + n + ": client" + n + "connected");
                ChatClientHandler handler = new ChatClientHandler(socket);
                userAssembly.put(handler.getUserName(), handler); 
                handler.start(); 
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main (String[] args) {
        ChatServer echo = new ChatServer();
        echo.listen();
    }

    void setCommandMap() {
      commandMap.put("whoami", new WhoamiCommand());
      commandMap.put("name", new NameCommand());
      commandMap.put("post", new PostCommand());
      commandMap.put("help", new HelpCommand());
      commandMap.put("bye", new ByeCommand());
    }    
}
