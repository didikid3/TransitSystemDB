package Lab4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.GridLayout;
import java.sql.ResultSet;

public class uiWindow {
    private JFrame frame;

    private EstablishConnection ec;


    private JPanel sched;
    private JTable schedList;
    


    public uiWindow(){
        frame = new JFrame();
        schedList = new JTable();

        ec = new EstablishConnection("lab4");
        ec.start();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout
                (2, 1, 0, 20));

        frame.setTitle("Bus Schedule");
        frame.setSize(800, 700);
        frame.setVisible(true);

    }

    private void generateScheduleList(){
        ResultSet rs = ec.getSchedule();
        DefaultTableModel list = new DefaultTableModel();
        try{
            while (rs.next()){
                String tripNumber = String.valueOf(rs.getInt("TripNumber"));
                String startLocationName = rs.getString("StartLocationName");
                String destinationName = rs.getString("DestinationName");
                String date = rs.getString("Date");
                String schedStartTime = rs.getString("ScheduledStartTime");
                String schedArrivalTime = rs.getString("ScheduledArrivalTime");
                String driverName = rs.getString("DriverName");
                String busID = String.valueOf(rs.getInt("BusID"));


            }
        }
        catch(Exception e){
            System.out.println(e);
        }

    }

}
