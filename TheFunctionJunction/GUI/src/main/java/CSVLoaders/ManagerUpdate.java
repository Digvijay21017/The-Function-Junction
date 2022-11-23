package CSVLoaders;

import Database.DBconnection.Connect;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class ManagerUpdate {
    private int batchSize = 20;
    static String csvFilePath="GUI/src/main/java/CSVLoaders/manager.csv";
    Connection con= Connect.createConnection();
    public void UpdateCSV(String loginid,String NewPassword){
        try{
            Statement St = con.createStatement();
            con.setAutoCommit(false);
            String Query1="Drop table if exist Manager";
            String Query2="create table manager(" +
                    "FirstName text," +
                    "LastName text," +
                    "PhoneNumber text,'" +
                    "emailID text," +
                    "LoginID text," +
                    "Password text)";

            St.executeUpdate(Query1);
            St.executeUpdate(Query2);

            String Query3 = "INSERT INTO review (FirstName, LastName, PhoneNumber, emailID, LoginID,Password) VALUES (? ,?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(Query3);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            int count = 0;
            int flag=0;
//            lineReader.readLine(); // skip header line
            FileWriter Writer= new FileWriter("tempManger.csv");
            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");
                String FirstName = data[0];
                String LastName = data[1];
                String PhoneNumber = data[2];
                String emailID = data[3];
                String LoginID = data[4];
                String Password = data[5];
                String newPassword=Password;
                String DOB=data[6];

                if (loginid.compareTo(LoginID)==0) {
                    newPassword=NewPassword;
                }
                Writer.write(FirstName+","+LastName+","+PhoneNumber+","+emailID+","+LoginID+","+newPassword+","+DOB+"\n");
                if (flag != 0) {
                    statement.setString(1, FirstName);
                    statement.setString(2, LastName);

                    statement.setString(3,PhoneNumber);

                    statement.setString(4, emailID);

                    statement.setString(5, LoginID);
                    statement.setString(6, newPassword);
                    statement.setString(7, DOB);

                    statement.addBatch();

                    if (count % batchSize == 0) {
                        statement.executeBatch();
                    }
                }

                flag++;
            }
            Writer.close();
            lineReader.close();

            // execute the remaining queries
            statement.executeBatch();

            con.commit();
            con.close();
            FileWriter writer1=new FileWriter(csvFilePath);
            lineReader=new BufferedReader(new FileReader("tempManger.csv"));
            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");
                String FirstName = data[0];
                String LastName = data[1];
                String PhoneNumber = data[2];
                String emailID = data[3];
                String LoginID = data[4];
                String Password = data[5];
                String DOB = data[6];
                writer1.write(FirstName+","+LastName+","+PhoneNumber+","+emailID+","+LoginID+","+Password+","+DOB+"\n");
            }
            writer1.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


