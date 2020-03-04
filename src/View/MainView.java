package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map;

public class MainView extends JFrame implements MouseMotionListener {

    private Map<String, JPanel> panelMap;
    private Map<String, JButton> coinMap;
    private Map<String, JPanel> boardMap;

    private JButton jbTurn;
    private JPanel jpTurnIndicator;
    private JPanel jpClientId;

    public MainView(){
        getContentPane().setLayout(new GridLayout(7,7));
        setLocationRelativeTo(null);
        setSize(300,300);
        setTitle("12-Coins");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addMouseMotionListener(this);

        panelMap = new HashMap<String, JPanel>();
        coinMap = new HashMap<String, JButton>();
        boardMap = new HashMap<String, JPanel>();

        generateBoard();
    }

    public void coinPressed(String coinName){
        coinMap.get(coinName).setText("");
    }

    private void generateBoard() {
        generateIDPanel();
        filln(2);
        generateCoin(12);
        filln(2);
        generateTurnIndicator();
        filln(2);
        generateCoin(11);
        filln(1);
        generateCoin(1);
        filln(2);
        filln(1);
        generateCoin(10);
        filln(3);
        generateCoin(2);
        filln(1);
        generateCoin(9);
        filln(5);
        generateCoin(3);
        filln(1);
        generateCoin(8);
        filln(3);
        generateCoin(4);
        filln(1);
        filln(2);
        generateCoin(7);
        filln(1);
        generateCoin(5);
        filln(2);
        filln(3);
        generateCoin(6);
        filln(2);
        generatePassTurnButton();
        //jpTurnIndicator.setBackground(Color.BLUE);

    }

    private void generateIDPanel() {
        jpClientId = new JPanel();
        getContentPane().add(jpClientId);
    }

    public void setClientID(int id){
        if (id == 0) jpClientId.setBackground(Color.BLACK);
        if (id == 1) jpClientId.setBackground(Color.WHITE);
    }

    private void generateTurnIndicator() {
        jpTurnIndicator = new JPanel();
        jpTurnIndicator.setBackground(Color.BLACK);
        getContentPane().add(jpTurnIndicator);
    }

    private void generatePassTurnButton() {
        JPanel jpEmpty = new JPanel();
        jbTurn = new JButton("Pass");
        jpEmpty.add(jbTurn);
        getContentPane().add(jpEmpty);
    }

    public void setTurnColor(String color){
        if (color.equals("GREEN")) {
            jpTurnIndicator.setBackground(Color.GREEN);
        } else {
            if(color.equals("RED")) {
                jpTurnIndicator.setBackground(Color.RED);
            } else {
                jpTurnIndicator.setBackground(Color.BLACK);
            }
        }
    }

    public Color getTurnIndicatorColor(){
        return jpTurnIndicator.getBackground();
    }

    private void generateCoin(int hour) {

        String toConcat = Integer.toString(hour);

        String panelName = "jpEmpty".concat(toConcat);
        panelMap.put(panelName, new JPanel());
        //panelMap.get(panelName).setBorder(BorderFactory.createLineBorder(Color.BLACK));

        String coinName = "jbCoin".concat(toConcat);


        coinMap.put(coinName, new JButton(toConcat));

        panelMap.get(panelName).add(coinMap.get(coinName));
        getContentPane().add(panelMap.get(panelName));
    }

    private void filln(int n) {
        for(int i = 0; i < n; i++){
            JPanel jpEmpty = new JPanel();
            //jpEmpty.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            getContentPane().add(jpEmpty);
        }
    }

    public void registerControler(ActionListener a){
        for(int i = 1; i < 13; i++){
            String coinName = "jbCoin".concat(Integer.toString(i));
            coinMap.get(coinName).setActionCommand("COIN/".concat(coinName));
            coinMap.get(coinName).addActionListener(a);
        }

        jbTurn.setActionCommand("TURN/ignore");
        jbTurn.addActionListener(a);
    }

    public boolean isWin() {

        boolean allDown = true;

        for(int i = 1; i < 13; i++){
            String coinName = "jbCoin".concat(Integer.toString(i));
            if(!coinMap.get(coinName).getText().equals("")){
                allDown = false;
            }
        }

        return allDown;
    }

    public void setWinner() {
        for(int i = 1; i < 13; i++){
            String coinName = "jbCoin".concat(Integer.toString(i));
            coinMap.get(coinName).setText("W");
        }
    }

    public void setLooser(){
        for(int i = 1; i < 13; i++){
            String coinName = "jbCoin".concat(Integer.toString(i));
            coinMap.get(coinName).setText("L");
        }
    }

    public void mouseMoved(MouseEvent me){
        int x = me.getX();
        int y = me.getY();
        //System.out.println("Mouse Move Event: (" + x + " , " + y + ")");
    }

    public void mouseDragged(MouseEvent me){
        int x = me.getX();
        int y = me.getY();
        //System.out.println("Mouse Dragg Event: (" + x + " , " + y + ")");
    }

}
