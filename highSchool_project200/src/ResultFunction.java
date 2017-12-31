
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

public class ResultFunction {

    public static void insertUpdateDelete(char operation, Integer id, String semester, Integer sclass, Double gpa) {
        Connection con=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=(Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/highschool?zeroDateTimeBehavior=convertToNull", "root","");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        PreparedStatement ps;
        if (operation == 'i') {
            try {

                ps = con.prepareStatement("INSERT INTO `result_info`(`id`, `semester`, `class`, `gpa`) VALUES (?,?,?,?)");
                ps.setInt(1, id);
                ps.setString(2, semester);
                ps.setInt(3, sclass);
                ps.setDouble(4, gpa);

                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "student's result data added");
                } else {
                    JOptionPane.showMessageDialog(null, "student's result data not added");
                }
            } catch (SQLException ex) {
                ///JOptionPane.showMessageDialog(null,"add failed");
                JOptionPane.showMessageDialog(null, "Student ID does not exit,Please check again");
            }
        }

        if (operation == 'u') {

            try {

//               ps=con.prepareStatement("UPDATE student SET firstName = ?, lastName = ?, sex = ?, birthday = ?, phone = ?, address = ? WHERE id = ?");
                ps = con.prepareStatement("UPDATE `result_info` SET `semester`=?,`class`=?,`gpa`=? WHERE `id`=?");

                ps.setString(1, semester);
                ps.setInt(2, sclass);
                ps.setDouble(3, gpa);
                ps.setInt(4, id);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, " student's result data updated");
                } else {
                    JOptionPane.showMessageDialog(null, " student's result data not updated");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "add failed");
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        if (operation == 'd') {
            int YesOrNo = JOptionPane.showConfirmDialog(null, "student's result information will be deleted", "Delete result informantion", JOptionPane.OK_CANCEL_OPTION, 0);

            //// return 0 for Yes
            if (YesOrNo == JOptionPane.OK_OPTION) {
                try {

                    ps = con.prepareStatement("DELETE FROM `result_info` WHERE id = ?");

                    ps.setInt(1, id);

                    if (ps.executeUpdate() > 0) {
                        JOptionPane.showMessageDialog(null, " student's result information deleted");
                    } else {
                        JOptionPane.showMessageDialog(null, " student's result information not deleted");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "add failed");
                    JOptionPane.showMessageDialog(null, ex);
                }

            }

        }

    }

    public void fillResultJtable(JTable table, String valueToSearch) {
         Connection con=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=(Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/highschool?zeroDateTimeBehavior=convertToNull", "root","");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

        PreparedStatement ps;
        try {
            ps = con.prepareStatement("SELECT * FROM `result_info` WHERE CONCAT(id,semester,gpa,class) LIKE ?");
            ///  ps=con.prepareStatement("SELECT * FROM `student` ");
//          ps=con.prepareStatement("SELECT * FROM `result_info`");

            ps.setString(1, "%" + valueToSearch + "%");

            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            Object[] row;
            while (rs.next()) {

                row = new Object[13];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getInt(3);
                row[3] = rs.getDouble(4);
//                 row[4]=rs.getString(5);

//                 row[0]=ps.getInt(1);
//                 row[1]=ps.getString(2);
//                 row[2]=ps.getInt(3);
//                 row[3]=ps.getDouble(4);
                model.addRow(row);

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "probleming happening");
            JOptionPane.showMessageDialog(null, ex);
            System.out.println(ex);
        }
    }
    
    

    public static int countRecord(String tableName) {
        int total = 0;
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
            ResultSet rs = st.executeQuery("SELECT count(*) from " + tableName);
            ///  ResultSet rs=st.executeQuery("SELECT count(*) from student_info");

            while (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return total;

    }

    void fillStudentJtable(JTable jTable1, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
