package Lab4;

import java.sql.*;
public class Selects {
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public Selects(Connection con, Statement stmt){
        this.con = con;
        this.stmt = stmt;
    }

    public ResultSet query(String select, String from, String where){
        try{
            stmt=con.createStatement();
            rs=stmt.executeQuery(select + " " + from + " " + where); 
        }
        catch(Exception e){
            System.out.println(e);
        }

        return rs;
    }
}
