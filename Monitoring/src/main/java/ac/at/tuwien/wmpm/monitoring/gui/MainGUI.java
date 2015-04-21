package ac.at.tuwien.wmpm.monitoring.gui;

import javax.swing.*;

/**
 * Created by dietl_ma on 21/04/15.
 */
public class MainGUI extends JFrame{
    private JPanel rootPanel;

    /**
     * Instantiates a new main gui.
     */
    public MainGUI(){
        setSize(300,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(rootPanel);
        setTitle("Montoring");

        setVisible(true);
    }
}
