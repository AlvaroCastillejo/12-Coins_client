package Controller;

import Network.Client;
import View.MainView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class MainController implements ActionListener {

    private MainView view;
    private Client client;

    private int nCoins;
    private ArrayList<String> coinsSelected;

    private boolean finished;

    public MainController(MainView view){
        this.view = view;
        try {
            client = new Client(this);
            client.startServerConnection();
            nCoins = 0;
            coinsSelected = new ArrayList<>();
            finished = false;
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String[] elements = e.getActionCommand().split("/");
        String command = elements[0];
        String hourPressed = elements[1];
        switch (command){
            case "COIN":
                if(view.getTurnIndicatorColor() == Color.GREEN) {
                    if(finished){
                        System.out.println("EL JUEGO YA HA TERMINADO");
                    } else {
                        if (nCoins < 2) {
                            if(nCoins == 0){
                                nCoins++;
                                view.coinPressed(hourPressed);
                                coinsSelected.add(hourPressed);
                            } else {
                                if(hourPressed.equals("jbCoin12")){
                                    if(coinsSelected.get(coinsSelected.size()-1).equals("jbCoin1")){
                                        nCoins++;
                                        view.coinPressed(hourPressed);
                                        coinsSelected.add(hourPressed);
                                    }
                                } else {
                                    if(hourPressed.equals("jbCoin1")){
                                        if(coinsSelected.get(coinsSelected.size()-1).equals("jbCoin12")){
                                            nCoins++;
                                            view.coinPressed(hourPressed);
                                            coinsSelected.add(hourPressed);
                                        }
                                    } else {
                                        String[] coinValues = coinsSelected.get(coinsSelected.size()-1).split("n");
                                        int coinValueA = Integer.parseInt(coinValues[1]);

                                        coinValues = hourPressed.split("n");
                                        int coinValueB = Integer.parseInt(coinValues[1]);

                                        if(Math.abs(coinValueA-coinValueB)<2){
                                            nCoins++;
                                            view.coinPressed(hourPressed);
                                            coinsSelected.add(hourPressed);
                                        } else {
                                            System.out.println("ERROR: Estan separadas :D.");
                                        }
                                    }
                                }

                            }

                        } else {
                            System.out.println("ERROR: + de 2 :D");
                        }
                    }
                } else {
                    System.out.println("ERROR: No es tu turno :D");
                }

                break;
            case "TURN":
                if(view.getTurnIndicatorColor() == Color.GREEN){
                    if(finished){
                        System.out.println("EL JUEGO HA TERMINADO");
                    } else {
                        for(int i = 0; i < coinsSelected.size();i++){
                            String toSend = "action/".concat(coinsSelected.get(i));
                            client.sendAction(toSend);
                        }

                        coinsSelected = new ArrayList<>();
                        nCoins = 0;

                        view.setTurnColor("RED");

                        client.sendAction("turn/ignore");

                        if(view.isWin()){
                            System.out.println("WINNER");
                            client.confirmWinner();
                        }
                    }
                } else {
                    System.out.println("ERROR: No puedes pasar si no es tu turno :D");
                }
                break;
        }

    }

    public void setID(int id){
        view.setClientID(id);
    }

    public void oponentCoinPressed(String action) {
        view.coinPressed(action);
    }

    public void oponentPassedTurn(String element) {
        if(element.equals("0")){
            view.setTurnColor("RED");
        } else {
            view.setTurnColor("GREEN");
        }
    }

    public void setTurn(String element) {
        if(element.equals("GREEN")) view.setTurnColor("GREEN");
        if(element.equals("RED")) view.setTurnColor("RED");
    }

    public void setWinner() {
        view.setWinner();
        finished = true;
        client.stopIt();
    }

    public void setLooser() {
        view.setLooser();
        finished = true;
        client.stopIt();
    }
}
