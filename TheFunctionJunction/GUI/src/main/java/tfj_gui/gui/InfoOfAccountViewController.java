package tfj_gui.gui;

import Database.DBconnection.Connect;
import Login.*;

import Login.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.ResourceBundle;

import static Database.DBconnection.Connect.con;
import static Database.DBconnection.Connect.createConnection;

public class InfoOfAccountViewController implements Initializable {
    @FXML
    public  Label PhoneNumber;
    @FXML
    public  Label Email;
    @FXML
    public Label MembershipStatus;
    @FXML
    public Label LoginId;
    @FXML
    public Label Age;
    @FXML
    private Button HomeButton;
    @FXML
    private Button NewBookingButton;
    @FXML
    private Button MyAccountButton;
    @FXML
    private Button MyBookingsButton;
    @FXML
    private Button ChangePasswordButton;
    @FXML
    private Button AboutUsButton;
    @FXML
    private Button HelpButton;
    @FXML
    private Button LogoutButton;
    @FXML
    private Button ExitButton;

    @FXML
    protected void GoToHome(ActionEvent event) throws IOException {
        ControllerFunctions.GoToHome(event);
    }

    @FXML
    protected void NewBookingButtonClicked(ActionEvent event) throws IOException {
        ControllerFunctions.NewBookingButtonClicked(event);
    }

    @FXML
    protected void MyAccountButtonClicked(ActionEvent event) throws IOException, SQLException {
        ControllerFunctions.MyAccountButtonClicked(event);
    }

    @FXML
    protected void MyBookingsButtonClicked(ActionEvent event) throws IOException {
        ControllerFunctions.MyBookingsButtonClicked(event);
    }

    @FXML
    protected void ChangePasswordButtonClicked(ActionEvent event) throws IOException {
        ControllerFunctions.ChangePasswordButtonClicked(event);
    }

    @FXML
    protected void AboutUsButtonClicked(ActionEvent event) throws IOException {
        ControllerFunctions.AboutUsButtonClicked(event);
    }

    @FXML
    protected void HelpButtonClicked(ActionEvent event) throws IOException {
        ControllerFunctions.HelpButtonClicked(event);
    }

    @FXML
    protected void LogoutButtonClicked(ActionEvent event) throws IOException {
        ControllerFunctions.LogoutButtonClicked(event);
    }

    @FXML
    protected void ExitButtonClicked(ActionEvent event) throws IOException {
        ControllerFunctions.ExitButtonClicked(event);
    }

    @FXML
    private Label Name;


    //Connect JDBC

    //search the database for required login id
//    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Send_Data_Between need = Send_Data_Between.getInstance();
            Customer c1 = need.getCustomer();
            String LoginID = c1.getLoginId();
        try {
            Connection con = Connect.createConnection();
            String query1 = "SELECT  * FROM customer WHERE loginid=?";
            PreparedStatement preStatement = con.prepareStatement(query1);
            preStatement.setString(1, LoginID);
            ResultSet rs1 = preStatement.executeQuery();
            if (rs1.next()) {
                Name.setText(rs1.getString(3));
                PhoneNumber.setText(rs1.getString(5));
                Email.setText(rs1.getString(6));
                MembershipStatus.setText(rs1.getString(9));
                LoginId.setText(rs1.getString(1));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = formatter.parse(rs1.getString(7));
                Instant instant = date.toInstant();
                ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
                LocalDate localDate = zone.toLocalDate();
                Period period = Period.between(localDate, LocalDate.now());
                String age = String.valueOf(period.getYears());
                Age.setText(age);
            }
        } catch (SQLException | ParseException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection();
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}