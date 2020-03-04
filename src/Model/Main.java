package Model;

import Controller.MainController;
import View.MainView;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            MainView v = new MainView();
            System.out.println("hola mon!!!");
            if(true){
                System.out.println("adios");
            }
            MainController c = new MainController(v);
            v.registerControler(c);
            v.setVisible(true);
        });
    }
}
