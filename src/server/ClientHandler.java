package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

class ClientHandler implements Runnable
{
    Scanner scn = new Scanner(System.in);
    private String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    boolean isloggedin;
    boolean couldMove = false;
    boolean inGame = false;
    GomokuController controller = new GomokuController();
    // constructor
    public ClientHandler(Socket s,
                         DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
        this.name = controller.generateRandomKey();
        this.s = s;
        this.isloggedin=true;
        try {
            dos.writeUTF(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        String received;
        while (true)
        {
            try
            {
                // receive the string
                received = dis.readUTF();

                System.out.println(received);

                if(received.equals("logout")){
                    this.isloggedin=false;
                    this.s.close();
                    break;
                }

                // break the string into message and recipient part
//                StringTokenizer st = new StringTokenizer(received, "#");
                StringTokenizer st = new StringTokenizer(received, "#");
                String commandUserId = st.nextToken();
                String command = st.nextToken();
                switch (command) {
                    case "GET_BOARDS":
                        dos.writeUTF(controller.getEmptyBoard());
                        break;
                    case "JOIN_TO_BOARD":
                        String boardId = st.nextToken();
                        controller.joinToBoard(name, boardId);
                        String oppositUser = controller.getBoardOwner(boardId);
                        dos.writeUTF("PLAYER_2#" + oppositUser + "#STOP");
                        this.inGame = true;
                        for (ClientHandler mc : Server.ar) {
                            if(mc.name.equals(oppositUser)) {
                                mc.couldMove = true;
                                mc.inGame = true;
                                mc.dos.writeUTF("PLAYER_2#" + commandUserId + "#MOVE");
                            }
                        }
                        break;
                    case "CREATE_BOARD":
                        controller.createNewBoard(name);
                        break;
                    case "MAKE_MOVE":
                        String i = st.nextToken();
                        String j = st.nextToken();
                        if (this.couldMove) {

                        }
                    default:
                        break;
                }
//                System.out.println(st);
//                String MsgToSend = st.nextToken();
//                String recipient = st.nextToken();

                // search for the recipient in the connected devices list.
                // ar is the vector storing client of active users
//                for (ClientHandler mc : Server.ar)
//                {
//                    // if the recipient is found, write on its
//                    // output stream
//                    if (mc.name.equals(recipient) && mc.isloggedin==true)
//                    {
//                        mc.dos.writeUTF(this.name+" : "+MsgToSend);
//                        break;
//                    }
//                }
            } catch (IOException e) {

                e.printStackTrace();
            }

        }
        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}