package Lab4;

import java.sql.*;

import java.util.Scanner;


public class EstablishConnection {

    private String databaseName;
    private Connection con;
    private Statement stmt;
    private ResultSet rs;
    private Scanner scan;

    private Inserts inserts;
    private Selects selects;

    public EstablishConnection(String database){
        databaseName = database;
        scan = new Scanner(System.in);
    }

    private void connect(){
        try{  
			con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/" + databaseName,"root","root");
			}
		catch(Exception e)
		{
			System.out.println(e);
		} 

        inserts = new Inserts(con, stmt);
        selects = new Selects(con, stmt);

    }

    private void disconnect(){
        try
        {
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        scan.close();
    }

    /*
     * Feature 1
     * Display the schedule of all trips for a given StartLocationName and Destination Name, 
     * and Date. In addition to these attributes, the schedule includes: Scheduled StartTime, 
     * ScheduledArrivalTime , DriverID, and BusID. 
     */
    private void displaySchedule(){
        String select = "SELECT trip.TripNumber, StartLocationName, " +
                        "DestinationName, Date," +
                        "ScheduledStartTime, ScheduledArrivalTime," +
                        "DriverName, BusID";
        String from =   "FROM trip_offering JOIN trip";
        String where =  "ON trip_offering.TripNumber = trip.TripNumber";

        try{
            rs = selects.query(select, from, where);

            System.out.format("%5s%15s%15s%10s%15s%15s%10s%10s\n",
                "Trip#", "Where", "Destination",
                "Date", "Start Time", "Arrival Time",
                "Driver", "Bus");
            while (rs.next()){
                int tripNumber = rs.getInt("TripNumber");
                String startLocationName = rs.getString("StartLocationName");
                String destinationName = rs.getString("DestinationName");
                String date = rs.getString("Date");
                String schedStartTime = rs.getString("ScheduledStartTime");
                String schedArrivalTime = rs.getString("ScheduledArrivalTime");
                String driverName = rs.getString("DriverName");
                int busID = rs.getInt("BusID");

                System.out.format("%5d%15s%15s%10s%15s%15s%10s%10d\n",
                                    tripNumber, startLocationName, destinationName,
                                    date, schedStartTime, schedArrivalTime,
                                    driverName, busID);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }

    }
    
    /*
     * Feature 2
     * Edit the trip offering table.
     * Delete a trip.
     * Add a set of trip offerings.
     * Change the driver for a given Trip offering.
     * Change the bus for a given Trip offering.
     */
    private void editTripOffering(){
        String entry = "";
        while (!entry.equals("done")){
            if(entry.equals("1")){
                deleteTripOffering();
            }
            else if(entry.equals("2")){
                addTripOffering();
            }
            else if(entry.equals("3")){
                updateDriver();
            }
            else if(entry.equals("4")){
                updateBus();
            }

            System.out.println("1) Delete Trip\n" +
                               "2) Add Trip\n" +
                               "3) Change Driver\n" +
                               "4) Change Bus\n" +
                               "Type 'done' to return");
            System.out.print("Next Action: ");
            entry = scan.nextLine();
        }
    }

    /*
     * Feature 3
     * Display the stops of a given trip.
     */
    private void displayStops(){
        String select = "SELECT trip.TripNumber, StopNumber, SequenceNumber, DrivingTime";
        String from = "FROM trip JOIN trip_stop_info";
        String where = "ON trip.TripNumber = trip_stop_info.TripNumber";

        try{
            rs = selects.query(select, from, where);

            System.out.format("%6s%8s%13s%6s\n",
                            "Trip #", "Stop #",
                            "Sequence #", "Time");
            while(rs.next()){
                int tripNumber = rs.getInt("TripNumber");
                int stopNumber = rs.getInt("StopNumber");
                int sequenceNumber = rs.getInt("SequenceNumber");
                int drivingTime = rs.getInt("DrivingTime");
                
                System.out.format("%6d%8d%13d%6d\n",
                                    tripNumber, stopNumber,
                                    sequenceNumber, drivingTime);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    /*
     * Feature 4
     * Display the schedule of a driver on a given date.
     */
    private void displayDriverSchedule(){
        String select = "SELECT *";
        String from = "FROM trip_offering";
        String where = "WHERE DriverName = '";

        System.out.print("Driver Name: ");
        where += scan.nextLine() + "'" + " AND DATE = '";
        System.out.print("Date: ");
        where += scan.nextLine() + "'";

        try{
            rs = selects.query(select, from, where);

            System.out.format("%5s%10s%15s%15s%10s%10s\n",
                "Trip#", "Date", 
                "Start Time", "Arrival Time",
                "Driver", "Bus");
            while (rs.next()){
                int tripNumber = rs.getInt("TripNumber");
                
                String date = rs.getString("Date");
                String schedStartTime = rs.getString("ScheduledStartTime");
                String schedArrivalTime = rs.getString("ScheduledArrivalTime");
                String driverName = rs.getString("DriverName");
                int busID = rs.getInt("BusID");

                System.out.format("%5d%10s%15s%15s%10s%10d\n",
                                    tripNumber, date,
                                    schedStartTime, schedArrivalTime,
                                    driverName, busID);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    /*
     * Feature 5
     * Add a Driver.
     */
    private void addDriver(){
        String insert = "INSERT INTO driver";
        String values = "values (";

        System.out.print("Driver Name: ");
        values += "'" + scan.nextLine() + "'";
        System.out.print("Telephone Number: ");
        values += "," + "'" + scan.nextLine() + "'" + ")";
   

        inserts.insert(insert, values);
    }
    
    /*
     * Feature 6
     * Add a Bus.
     */
    private void addBus(){
        String insert = "INSERT INTO bus";
        String values = "values (";

        System.out.print("Bus ID: ");
        values += scan.nextLine() ;
        System.out.print("Model: ");
        values += "," + "'" + scan.nextLine() + "'";
        System.out.print("Year: ");
        values += "," + scan.nextLine() + ")";


        inserts.insert(insert, values);
    }

    /*
     * Feature 7
     * Delete a bus.
     */
    private void deleteBus(){
        String delete = "DELETE FROM bus";
        String where = "WHERE BusID=";
        
        System.out.print("Bus ID: ");
        
        where += scan.nextLine();
        
        try{
            stmt=con.createStatement();
            stmt.executeUpdate(delete + " " + where);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    private void deleteTripOffering(){
        String delete = "DELETE FROM trip_offering";
        String where = "WHERE TripNumber=";

        System.out.print("Trip Number: ");
        where += scan.nextLine() + " AND Date = '";
        System.out.print("Date: ");
        where += scan.nextLine() + "' AND ScheduledStartTime = '";
        System.out.print("Scheduled Start Time: ");
        where += scan.nextLine() + "'";
        try{
            stmt=con.createStatement();
            stmt.executeUpdate(delete + " " + where);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    private void addTripOffering(){
        String entry = "";
        while (!entry.equals("done")){

            String insert = "INSERT INTO trip_offering";
            String values = "values (";

            String tripNumber, date, scheduledStartTime, scheduledArrivalTime, driverName, busID;
            System.out.print("Trip Number: ");
            tripNumber = scan.nextLine();
            System.out.print("Date: ");
            date  = scan.nextLine();
            System.out.print("Scheduled Start Time: ");
            scheduledStartTime = scan.nextLine();
            System.out.print("Scheduled Arrival Time: ");
            scheduledArrivalTime = scan.nextLine();
            System.out.print("Driver Name: ");
            driverName = scan.nextLine();
            System.out.print("Bus ID: ");
            busID = scan.nextLine();

            values += tripNumber + ", '" + date + "' , '" + scheduledStartTime + "' , '" +
                    scheduledArrivalTime + "' , '" + driverName + "' , " + busID + ")";

            inserts.insert(insert, values);

            System.out.println("Do you have more inputs?");
            System.out.print("Type 'done' if no more: ");
            entry = scan.nextLine();
        }
    }

    private void updateDriver(){
        String update = "UPDATE trip_offering";
        String set = "SET DriverName = '";
        String where = "WHERE TripNumber = ";

        System.out.print("Trip Number: ");
        where += scan.nextLine() + " AND Date = '";
        System.out.print("Date: ");
        where += scan.nextLine() + "' AND ScheduledStartTime = '";
        System.out.print("Scheduled Start Time: ");
        where += scan.nextLine() + "'";

        System.out.print("New Driver Name: ");
        set += scan.nextLine() + "'";

        try{
            stmt=con.createStatement();
            stmt.executeUpdate(update + " " + set + " " + where);

            displaySchedule();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    private void updateBus(){
        String update = "UPDATE trip_offering";
        String set = "SET BusID = '";
        String where = "WHERE TripNumber = ";

        System.out.print("Trip Number: ");
        where += scan.nextLine() + " AND Date = '";
        System.out.print("Date: ");
        where += scan.nextLine() + "' AND ScheduledStartTime = '";
        System.out.print("Scheduled Start Time: ");
        where += scan.nextLine() + "'";

        System.out.print("New Bus ID: ");
        set += scan.nextLine() + "'";

        try{
            stmt=con.createStatement();
            stmt.executeUpdate(update + " " + set + " " + where);

            displaySchedule();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }



    public void start(){
        connect();
    }

    public void menuOpen(){
        String entry = "";
        displaySchedule();
        while (!entry.equals("done")){

            if(entry.equals("1")){
                displaySchedule();
            }
            else if(entry.equals("2")){
                editTripOffering();
            }
            else if(entry.equals("3")){
                displayStops();
            }
            else if(entry.equals("4")){
                displayDriverSchedule();
            }
            else if(entry.equals("5")){
                addDriver();
            }
            else if(entry.equals("6")){
                addBus();
            }
            else if(entry.equals("7")){
                deleteBus();
            }



            System.out.println("1) Display Schedule\n" +
                                "2) Edit Schedule\n" +
                                "3) Display Stops\n" +
                                "4) Display Driver Schedule\n" +
                                "5) Add a Driver\n" +
                                "6) Add a Bus\n" +
                                "7) Delete a Bus\n" +
                                "Type 'done' to end");
            System.out.print("Next Action: ");
            entry = scan.nextLine();
        }

        disconnect();
    }

    
}
