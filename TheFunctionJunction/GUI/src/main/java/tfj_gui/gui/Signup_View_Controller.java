package tfj_gui.gui;

import Database.DBconnection.Connect;
import Login.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.*;

public class Signup_View_Controller
{
    Date DOB;
    static int custid=1000;
    @FXML
    private TextField FirstNameTF;
    @FXML
    private TextField LastNameTF;
    @FXML
    private TextField EmailIdTF;
    @FXML
    private TextField PhoneNumberTF;
    @FXML
    private Button SignUpFinalButton;
    @FXML
    private CheckBox ConfirmDetailsCheckbox;
    @FXML
    private DatePicker DOBTF = new DatePicker();
    @FXML
    private PasswordField PasswordTF;
    @FXML
    private TextField LoginIdTF;
    @FXML
    private Button AvailableButton;
    @FXML
    private Button LoginButton;
    @FXML
    private Label AvailabilityLabel;
    @FXML
    protected void AvailableButtonClicked()
    {
        Customer c = new Customer(FirstNameTF.getText(), LastNameTF.getText(),PhoneNumberTF.getText(),EmailIdTF.getText(),LoginIdTF.getText(),PasswordTF.getText(),((TextField) DOBTF.getEditor()).getText());

        if (c.Availability(LoginIdTF.getText()))
        {
            AvailabilityLabel.setText("Available!!!");
        }
        else AvailabilityLabel.setText("Not Available!!!");
    }
    @FXML
    private void LoginButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login_View.fxml"));
        Stage stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(),1080,720);
        stage.setTitle("THE FUNCTION JUNCTION");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void getDateFromPicker(ActionEvent event)
    {
        LocalDate dob = DOBTF.getValue();
        DOB = Date.valueOf(dob);
    }

    @FXML
    private void SignUpFinalButtonClicked() throws SQLException {
        Customer c1 = new Customer(FirstNameTF.getText(), LastNameTF.getText(),PhoneNumberTF.getText(),EmailIdTF.getText(),LoginIdTF.getText(),PasswordTF.getText(), ((TextField) DOBTF.getEditor()).getText());
        Connection c = Connect.createConnection();
        try{
            String query="select max(customerid) from customer";
            Statement statement=c.createStatement();
            ResultSet rs=statement.executeQuery(query);
            if(rs.next()){
                custid=rs.getInt(1)+1;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            c.close();
        }
        c1.Signup(FirstNameTF.getText(), LastNameTF.getText(),PhoneNumberTF.getText(),EmailIdTF.getText(),LoginIdTF.getText(),PasswordTF.getText(),DOB.toString(),String.valueOf(custid));
        System.out.println(custid);
    }
}
