package com.github.chatserver;
import java.net.*;
import java.io.*;
import java.util.*;

/*
 * ”POST”コマンド
 */
public class PostCommand extends Command {
    public void run(String[] command, ChatClientHandler user) throws IOException {
        serverResult = command[0] + " ";
        if(command.length == 1) { //コマンドの引数が正常でないなら
            commandResult = "CommandNotFound";
            showTerminal(commandResult, serverResult, user); //ターミナルに出力
            return;
        }
        String postMessage = command[1]; //送信するメッセージ
        for(Map.Entry<String, ChatClientHandler> entry : ChatServer.userAssembly.entrySet()) {
            String name = entry.getKey(); //ユーザの名前を取り出す
            ChatClientHandler receiver = entry.getValue(); //受信するユーザのオブジェクトを取り出す
            if(receiver != user) { //受信者が自分自身なら送信を行わない
                receiver.getPostMessage(postMessage, user.getUserName()); //ユーザにメッセージを受け取らせる 引数は(メッセージ, 送信者)
                commandResult += name + ", "; //誰に送信したか
            }
        }
        if(commandResult == null) { //誰にも送信できなかったら
            commandResult = "no one receive message";
        } else {
            commandResult = commandResult.substring(4, commandResult.length()-2); //末尾を削除 ", "　先頭から４番目から開始
        }
        serverResult += command[1];
        showTerminal(commandResult, serverResult, user); //ターミナルに出力
    }
}
