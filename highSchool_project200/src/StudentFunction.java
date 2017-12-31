
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lenovo
 */
public class StudentFunction {
     public static void insertUpdateDelete(char operation,Integer id,String name,String fathername,String mathername,String bdate,String gender,int sclass,String phone,String par_address,String cur_address ,FileInputStream fin,Integer len)
    {

          Connection con=null;
          
          con=AllConnection.getConnection();
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            con=(Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/highschool?zeroDateTimeBehavior=convertToNull", "root","");
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(null, ex);
//        }
        PreparedStatement ps;
        if(operation=='i')
        {
            try {
                
                ps=con.prepareStatement("INSERT INTO `student_info`(`id`, `name`, `father`, `mather`, `dob`, `gender`, `class`, `phone`, `par_address`, `cur_address`,`image`) "
                        + "VALUES (?,?,?,?,?,?,?,?,?,?,?)");
                  
                 ps.setInt(1, id);
                 ps.setString(2, name);
                 ps.setString(3, fathername);
                 ps.setString(4,mathername);
                 ps.setString(5,bdate);
                 ps.setString(6, gender);
                 ps.setInt(7, sclass);
                 ps.setString(8,phone);
                 ps.setString(9,par_address);
                 ps.setString(10, cur_address);
                 ps.setBlob(11,fin,len);
               
                 if(ps.executeUpdate()>0)
                 {
                     JOptionPane.showMessageDialog(null,"new student data added");
                 }
                 else
                     JOptionPane.showMessageDialog(null,"new student data not added");
            }
                 catch (SQLException ex) {    
                /// JOptionPane.showMessageDialog(null,"add failed");
                 JOptionPane.showMessageDialog(null,ex);
            }
        }
        
         if(operation=='u')
        {
            
            try {
                 ps=con.prepareStatement("UPDATE `student_info` SET `name`=?,`father`=?,`mather`=?,`dob`=?,`gender`=?,`class`=?,`phone`=?,`par_address`=?,`cur_address`=?,`image`=? WHERE `id`=?");
                 ps.setString(1, name);
                 ps.setString(2, fathername);
                 ps.setString(3,mathername);
                 ps.setString(4,bdate);
                 ps.setString(5, gender);
                 ps.setInt(6, sclass);
                 ps.setString(7,phone);
                 ps.setString(8,par_address);
                 ps.setString(9, cur_address);
                 ps.setBinaryStream(10, fin, len);
                 ps.setInt(11, id);
                 if(ps.executeUpdate()>0)
                 {
                     JOptionPane.showMessageDialog(null," student data updated");
                 }
                 else
                     JOptionPane.showMessageDialog(null," student data not updated");
            }
                 catch (SQLException ex) {    
                JOptionPane.showMessageDialog(null,"add failed");
                 JOptionPane.showMessageDialog(null,ex);
            }
        }
         if(operation=='d')
        {
            int Check=JOptionPane.showConfirmDialog(null,"student's result information will be deleted also","Delete informantion",JOptionPane.OK_CANCEL_OPTION,0);
            
           
            if(Check==JOptionPane.OK_OPTION)
            {
              try {
                
                ps=con.prepareStatement("DELETE FROM `student_info` WHERE id = ?");
                 
                  ps.setInt(1, id);
               
                 if(ps.executeUpdate()>0)
                 {
                     JOptionPane.showMessageDialog(null," student information deleted");
                 }
                 else
                     JOptionPane.showMessageDialog(null," student information not deleted");
            }
                 catch (SQLException ex) {    
                 JOptionPane.showMessageDialog(null,"add failed");
                 JOptionPane.showMessageDialog(null,ex);
            }
              
            }
          
        }
        
    }


   public void fillStudentJtable(JTable table,String value)
   {

         Connection con=null;
         con=AllConnection.getConnection();
         
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            con=(Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/highschool?zeroDateTimeBehavior=convertToNull", "root","");
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(null, ex);
//        }
       
       PreparedStatement ps;
        try {
            
           ps=con.prepareStatement("SELECT * FROM `student_info` WHERE CONCAT(name,father,mather,par_address,cur_address,phone) LIKE ?");
            ///  ps=con.prepareStatement("SELECT * FROM `student` ");
            ///  ps=con.prepareStatement("SELECT * FROM `student_info` ");
            
            
            ps.setString(1,"%"+value+"%");
            
            ResultSet rs=ps.executeQuery();
            
            DefaultTableModel model=(DefaultTableModel) table.getModel();
            
            Object[] rowadd;
            
            while(rs.next())
            {
                rowadd=new Object[13];
                
                rowadd[0]=rs.getInt(1);
                rowadd[1]=rs.getString(2);
                rowadd[2]=rs.getString(3);
                rowadd[3]=rs.getString(4);
                rowadd[4]=rs.getString(5);
                rowadd[5]=rs.getString(6);
                rowadd[6]=rs.getInt(7);
                rowadd[7]=rs.getString(8);
                rowadd[8]=rs.getString(9);
                rowadd[9]=rs.getString(10);
                rowadd[10]=rs.getBinaryStream(11);
              
                model.addRow(rowadd);
                    
                
            }
            
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, ex);
        }
      }
    public static int countRecord(String tableName) 
    {     
        int total=0;
        try {
           
            
             Connection con=null;
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            con=(Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/highschool?zeroDateTimeBehavior=convertToNull", "root","");
//        } catch (ClassNotFoundException | SQLException ex) {
//            JOptionPane.showMessageDialog(null, ex);
//        }
            con=AllConnection.getConnection();
            Statement st = null;
            try {
                st = con.createStatement();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
             ResultSet rs=st.executeQuery("SELECT count(*) from "+tableName);
              ///  ResultSet rs=st.executeQuery("SELECT count(*) from student_info");.
            while(rs.next())
            {
                total=rs.getInt(1);
            }
         
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, ex);
        }
        return total;
        
    }

   
}
