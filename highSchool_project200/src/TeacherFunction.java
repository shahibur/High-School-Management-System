
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


public class TeacherFunction {
    public static void insertUpdateDelete(char operation,Integer id,String name,String subject,String phone,String address,Double salary, FileInputStream fin,Integer len)
    {
//        Connection con=myConnection.getConnection();
          Connection con=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=(Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/highschool?zeroDateTimeBehavior=convertToNull", "root","");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        PreparedStatement ps;
        if(operation=='i')
        {
            try {
                
                ps=con.prepareStatement("INSERT INTO `teacher_info`(`id`, `name`, `subject`, `phone`, `address`, `salary`, `image`) VALUES (?,?,?,?,?,?,?)");
                  
                 ps.setInt(1, id);
                 ps.setString(2, name);
                 ps.setString(3, subject);
                 ps.setString(4,phone);
                 ps.setString(5,address);
                 ps.setDouble(6,salary);
//                 ps.setString(8,phone);
//                 ps.setString(9,par_address);
//                 ps.setString(10, cur_address);
                 ps.setBinaryStream(7,fin,len);
               
                 if(ps.executeUpdate()>0)
                 {
                     JOptionPane.showMessageDialog(null,"new student data added");
                 }
                 else
                     JOptionPane.showMessageDialog(null,"new student data not added");
            }
                 catch (SQLException ex) {    
                /// JOptionPane.showMessageDialog(null,"add failed");
                 JOptionPane.showMessageDialog(null,"Check Teacher's ID : "+ex);
            }
        }
        
         if(operation=='u')
        {
            
            try {
                
//               ps=con.prepareStatement("UPDATE student SET firstName = ?, lastName = ?, sex = ?, birthday = ?, phone = ?, address = ? WHERE id = ?");
                  ps=con.prepareStatement("UPDATE `teacher_info` SET `name`=?,`subject`=?,`phone`=?,`address`=?,`salary`=?,`image`=? WHERE `id`=?");
    
                 ps.setString(1,name);
                 ps.setString(2,subject);
                 ps.setString(3,phone);
                 ps.setString(4,address);
                 ps.setDouble(5,salary);
                 ps.setBinaryStream(6, fin, len);
                 ps.setInt(7, id);   

                 if(ps.executeUpdate()>0)
                 {
                     JOptionPane.showMessageDialog(null," Teacher data updated");
                 }
                 else
                     JOptionPane.showMessageDialog(null," Teacher data not updated");
            }
                 catch (SQLException ex) {    
                JOptionPane.showMessageDialog(null,"add failed");
                 JOptionPane.showMessageDialog(null,ex);
            }
        }
         if(operation=='d')
        {
            int check=JOptionPane.showConfirmDialog(null,"Teacher information will be deleted","Delete Teacher informantion",JOptionPane.OK_CANCEL_OPTION,0);
            
         
            if(check==JOptionPane.OK_OPTION)
            {
              try {
                
                ps=con.prepareStatement("DELETE FROM `teacher_info` WHERE id = ?");
                 
                  ps.setInt(1, id);
               
                 if(ps.executeUpdate()>0)
                 {
                     JOptionPane.showMessageDialog(null," Teacher information deleted");
                 }
                 else
                     JOptionPane.showMessageDialog(null," Teacher information not deleted");
            }
                 catch (SQLException ex) {    
                 JOptionPane.showMessageDialog(null,"add failed");
                 JOptionPane.showMessageDialog(null,ex);
            }
              
            }
          
        }
        
    }


   public void fillTeacherJtable(JTable table,String valueToSearch)
   {
        Connection con=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=(Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/highschool?zeroDateTimeBehavior=convertToNull", "root","");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
       
       PreparedStatement ps;
        try {
        ps=con.prepareStatement("SELECT * FROM `teacher_info` WHERE CONCAT(name,subject,phone,address) LIKE ?");
            ///  ps=con.prepareStatement("SELECT * FROM `student` ");
            ///  ps=con.prepareStatement("SELECT * FROM `student_info` ");
//        ps=con.prepareStatement("SELECT * FROM `teacher_info` WHERE name LIKE ?");
            
            ps.setString(1,"%"+valueToSearch+"%");
            
            ResultSet rs=ps.executeQuery();
            DefaultTableModel model=(DefaultTableModel) table.getModel();
            
            Object[] row;
            while(rs.next())
            {
                
                row=new Object[8];
                
                row[0]=rs.getInt(1);
                row[1]=rs.getString(2);
                row[2]=rs.getString(3);
                row[3]=rs.getString(4);
                row[4]=rs.getString(5);
                row[5]=rs.getDouble(6);
                row[6]=rs.getBinaryStream(7);
                model.addRow(row);
            
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
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=(Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/highschool?zeroDateTimeBehavior=convertToNull", "root","");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
            Statement st = null;
            try {
                st = con.createStatement();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
             ResultSet rs=st.executeQuery("SELECT count(*) from "+tableName);
              ///  ResultSet rs=st.executeQuery("SELECT count(*) from student_info");

            
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
