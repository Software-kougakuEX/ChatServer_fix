package com.github.chatserver;
import java.net.*;
import java.io.*;
import java.util.*;


/*
 * ”BYE”コマンド
 */
public class ByeCommand extends Command {
    public void run(String[] command, ChatClientHandler user) throws IOException {
        serverResult = command[0] + " ";
        commandResult = "bye " + user.getUserName();
        showTerminal(commandResult, serverResult, user); //ターミナルに出力
        ChatServer.userAssembly.remove(user.getUserName()); //ユーザの集合から削除
        user.close();
    }
}
