// Jun Park
// Still working on

import java.net.*;
import java.io.*;

public class Server
{
    static String name, IP = "";
    int PORT;
    static volatile boolean finished = false;

    InetAddress group;
    MulticastSocket socket;

    public Server(String IP, int PORT, String Mark, TicTacToe game) {
        this.IP = IP;
        this.PORT = PORT;
        try {
            group = InetAddress.getByName(IP);
            socket = new MulticastSocket(PORT);

            // Since we are deploying
            socket.setTimeToLive(0);
            //this on localhost only (For a subnet set it as 1)

            socket.joinGroup(group);
            Thread t = new Thread(new ReadThread(socket, group, PORT, game));
            t.start();
        } catch(SocketException se) {
            System.out.println("Error creating socket");
            se.printStackTrace();
        } catch(IOException ie) {
            System.out.println("Error reading/writing from/to socket");
            ie.printStackTrace();
        }
    }

    public boolean sendMsg(String text) {
        try {
            String message = "";
            if(text.equals("exit")) {
                finished = true;
                socket.leaveGroup(group);
                socket.close();
            }

            message = name + ": " + message;
            byte[] buffer = message.getBytes();
            DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, group, PORT);
            socket.send(datagram);

        } catch(SocketException se) {
            System.out.println("Error creating socket");
            se.printStackTrace();
            return false;
        } catch(IOException ie) {
            System.out.println("Error reading/writing from/to socket");
            ie.printStackTrace();
            return false;
        }
        return true;
    }
}


class ReadThread implements Runnable {
    private MulticastSocket socket;
    private InetAddress group;
    private int port;
    private boolean finished = false;
    private String returnV = "";
    private static final int MAX_LEN = 1000;

    ReadThread(MulticastSocket socket, InetAddress group, int port, TicTacToe g) {
        this.socket = socket;
        this.group = group;
        this.port = port;
    }

    @Override
    public void run() {
        while(!Server.finished) {
            byte[] buffer = new byte[ReadThread.MAX_LEN];
            DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, group, port);
            String message;
            try {
                socket.receive(datagram);
                message = new String(buffer,0,datagram.getLength(),"UTF-8");
                if(!message.startsWith(Server.name)) {
                    System.out.println(message);
                    returnV = message;
                    finished = true;
                }
            }
            catch(IOException e) {
                System.out.println("Socket closed!");
            }
        }
    }
}