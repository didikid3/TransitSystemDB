package Lab4;

import java.sql.*;
public class Inserts {
    private Connection con;
    private Statement stmt;

    public Inserts(Connection con, Statement stmt){
        this.con = con;
        this.stmt = stmt;
    }

    public void insert(String insert, String values){
        try{
            stmt=con.createStatement();
            stmt.executeUpdate(insert + " " + values);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
