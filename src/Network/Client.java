package Network;

import Controller.MainController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Thread {

    private static final String IP = "127.0.0.1";
    private static final int PORT = 12345;

    private MainController controller;
    private final DataOutputStream dos;
    private final DataInputStream dis;

    private boolean running;
    private int id;

    public Client(MainController controller) throws IOException{
        this.controller = controller;
        Socket socket = new Socket(IP, PORT);
        dos = new DataOutputStream(socket.getOutputStream());
        dis = new DataInputStream(socket.getInputStream());
        id = -1;
    }

    public void startServerConnection(){
        running = true;
        start();
    }

    @Override
    public void run(){
        try{
            while(running){
                String action = dis.readUTF();
                String[] elements = action.split("/");

                switch (elements[0]){
                    case "action":
                        action = elements[1];
                        controller.oponentCoinPressed(action);
                        break;
                    case "turn":
                        controller.oponentPassedTurn(elements[1]);
                        break;
                    case "setTurn":
                        controller.setTurn(elements[1]);
                        break;
                    case "setID":
                        int i = 0;
                        if(elements[1].equals("1")){ i = 1;}
                        if(elements[1].equals("0")){ i = 0;}
                        controller.setID(i);
                        this.id = i;
                        break;
                    case "winner":
                        if(elements[1].equals("yes")){
                            controller.setWinner();
                        } else {
                            controller.setLooser();
                        }
                        break;
                    default:
                        System.out.println("PRINGAO");
                }

            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void stopIt(){
        running = false;
    }

    public void sendAction(String hourPressed){
        try{
            dos.writeUTF(hourPressed);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendPassTurn(){
        try{
            dos.writeUTF("turn/ignore");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void confirmWinner() {
        try{
            String toSend = "winnerIs/".concat(Integer.toString(this.id));
            dos.writeUTF(toSend);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
