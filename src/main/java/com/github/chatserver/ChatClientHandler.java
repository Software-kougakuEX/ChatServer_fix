package com.github.chatserver;
import java.net.*;
import java.io.*;
import java.util.*;

/*
 * 各ユーザの処理を行う
 */
public class ChatClientHandler extends Thread {
    private Socket socket; //ソケット
    public BufferedReader in;
    public BufferedWriter out;
    
    private InetAddress address;
    private static int userNum = 1; //何番目のユーザかどうか
    private int userId; //ユーザ自身が覚えているid
    private final String defaultName = "undefined"; //デフォルト名
    private String userName; //ユーザ名
    
    public ChatClientHandler(Socket socket) {
        this.socket = socket;
        this.userId = userNum;
        this.userName = defaultName + String.valueOf(userNum); //名前の初期値
        this.address = socket.getInetAddress();
        userNum++; //ユーザ番号を増やす
    }
    
    public void run(){
        try{
            open();
            while(true){
                out.write("> ");
                out.flush();
                String message = receive();
                if(message.equals("")) break;
                String[] command; //入力されたコマンドを格納する
                command = message.split(" "); //コマンドは１番目　" "で区切って配列に代入する
                Command commandObject;
                commandObject =  ChatServer.commandMap.get(command[0].toLowerCase()); //入力されたコマンド名と同じkeyをもつコマンドオブジェクトを取り出す。また、大文字を小文字に変換する。
                if(ChatServer.commandMap.containsKey(command[0].toLowerCase()) == false) { //コマンドマップにないなら
                    System.out.println("aaa");
                    String commandResult = "CommadNotFound";
                    send(commandResult, command[0]);
                } else {
                    commandObject.start(command, this); //取り出したコマンドオブジェクトを実行する
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /*
     * inやoutなどの入出力ストリームを開く
     */
    private void open() throws IOException{
        InputStream socketIn = socket.getInputStream();
        OutputStream socketOut = socket.getOutputStream();
        in = new BufferedReader(new InputStreamReader(socketIn));
        out = new BufferedWriter(new OutputStreamWriter(socketOut));
    }

    /*
     * 閉じる　クライアントの処理の終了
     */
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    /*
     * コマンドを受け取る
     */
    private String receive() throws IOException {
        String message = in.readLine(); //入力をまつ
        return message;
    }

    /*
     * コマンドを入力した結果をターミナルに出力する
     */
    private void send(String commandResult, String serverResult) throws IOException {
        out.write(commandResult + "\r\n");
        out.flush();
        System.out.println("client" + getUserId() + "(" + getUserName() + "): " + serverResult.trim() + ": " + commandResult); //サーバーに結果を表示する。誰が実行したかと結果
    }
    
    /*
     * userIdを返す
     */
    public int getUserId() {
        return this.userId;
    }
    
    /*
     * アドレスを返す
     */
    public InetAddress getAddress() {
        return address;
    }
    
    /*
     * ユーザ番号を返す
     */
    public static int getUserNum() {
        return userNum;
    }

    /*
     * ユーザの名前を返す
     */
    public String getUserName() {
        return userName;
    }  
}
