package Lab4;

import javax.swing.*;
import java.awt.GridLayout;

public class uiWindow {
    private JFrame frame;


    private JPanel sched;
    private JList schedList;
    


    public uiWindow(){
        frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout
                (2, 1, 0, 20));

        frame.setTitle("Bus Schedule");
        frame.setSize(800, 700);
        frame.setVisible(true);

    }

    private void generateScheduleList(){

    }

}
