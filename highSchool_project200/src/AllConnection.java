

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class AllConnection {
    public static Connection con=null;
    public static Connection getConnection() 
    {
        
    ///   Connection con=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/highschool?zeroDateTimeBehavior=convertToNull", "root","");
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
        return con;
        
    }
     public static void closeConnection() throws SQLException
    {
        con.close();
    }
  
    
}
