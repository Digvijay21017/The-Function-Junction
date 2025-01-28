package tfj_gui.gui;

import Database.DBconnection.Connect;
import Database.TableView.Custom;
import Database.TableView.Event;
import Database.TableView.Venue;
import Database.TableView.Wedding;
import Login.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class NewBookingCustomViewController implements Initializable {
    int next_booking_id;
    @FXML
    public Label DisplayInformationLabel;
    @FXML
    public TextField guests;
    @FXML
    public Button GetCost;
    @FXML
    public Label RemarVenue1;
    @FXML
    public Label RemarVenue;
    @FXML
    public TextField guests1;
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
    private Button Next1;
    @FXML
    private Button GoBack;

    @FXML
    protected void GoToHome(ActionEvent event) throws IOException {
        Send_Data_Between inst = Send_Data_Between.getInstance();
        if (inst.getCheck() == 1) {
            ManagerControllerFunctions.GoToHome(event);
        } else {
            ControllerFunctions.GoToHome(event);
        }
    }

    @FXML
    protected void NewBookingButtonClicked(ActionEvent event) throws IOException {

    }

    @FXML
    protected void MyAccountButtonClicked(ActionEvent event) throws IOException {
        Send_Data_Between inst = Send_Data_Between.getInstance();
        if (inst.getCheck() == 1) {
            ManagerControllerFunctions.MyAccountButtonClicked(event);
        } else {
            ControllerFunctions.MyAccountButtonClicked(event);
        }
    }

    @FXML
    protected void MyBookingsButtonClicked(ActionEvent event) throws IOException {
        Send_Data_Between inst = Send_Data_Between.getInstance();
        if (inst.getCheck() == 1) {
            ManagerControllerFunctions.MyBookingsButtonClicked(event);
        } else {
            ControllerFunctions.MyBookingsButtonClicked(event);
        }
    }

    @FXML
    protected void ChangePasswordButtonClicked(ActionEvent event) throws IOException {
        Send_Data_Between inst = Send_Data_Between.getInstance();
        if (inst.getCheck() == 1) {
            ManagerControllerFunctions.ChangePasswordButtonClicked(event);
        } else {
            ControllerFunctions.ChangePasswordButtonClicked(event);
        }
    }

    @FXML
    protected void AboutUsButtonClicked(ActionEvent event) throws IOException {
        Send_Data_Between inst = Send_Data_Between.getInstance();
        if (inst.getCheck() == 1) {
            ManagerControllerFunctions.AboutUsButtonClicked(event);
        } else {
            ControllerFunctions.AboutUsButtonClicked(event);
        }
    }

    @FXML
    protected void HelpButtonClicked(ActionEvent event) throws IOException {
        Send_Data_Between inst = Send_Data_Between.getInstance();
        if (inst.getCheck() == 1) {
            ManagerControllerFunctions.HelpButtonClicked(event);
        } else {
            ControllerFunctions.HelpButtonClicked(event);
        }
    }

    @FXML
    protected void LogoutButtonClicked(ActionEvent event) throws IOException {
        Send_Data_Between inst = Send_Data_Between.getInstance();
        if (inst.getCheck() == 1) {
            ManagerControllerFunctions.LogoutButtonClicked(event);
        } else {
            ControllerFunctions.LogoutButtonClicked(event);
        }
    }

    @FXML
    protected void ExitButtonClicked(ActionEvent event) throws IOException {
        Send_Data_Between inst = Send_Data_Between.getInstance();
        if (inst.getCheck() == 1) {
            ManagerControllerFunctions.ExitButtonClicked(event);
        } else {
            ControllerFunctions.ExitButtonClicked(event);
        }
    }

    private final String[] Venue_Choices = new Venue().getAllVenueNames().toArray(new String[0]);
    @FXML
    private ChoiceBox<String> Venue_choicebox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Venue_choicebox.getItems().addAll(Venue_Choices);
        Venue_choicebox.setOnAction(this::Choice_in_Choice_box);
    }

    private String myChoice_venue;

    protected void Choice_in_Choice_box(ActionEvent event) {
        myChoice_venue = Venue_choicebox.getValue();
        getDescription();
    }

    @FXML
    private DatePicker Start_Date = new DatePicker();

    protected void getStartDate(ActionEvent event) {
        LocalDate SD = Start_Date.getValue();
    }

    @FXML
    private DatePicker End_Date = new DatePicker();

    protected void getEndDate(ActionEvent event) {
        LocalDate ED = End_Date.getValue();
    }

    @FXML
    protected void NextNewBooking(ActionEvent event) throws IOException, SQLException {
        Venue v = new Venue(myChoice_venue, no_of_guests);
        v.setCapacity();
        totalCapacity = v.getCapacity();
        no_of_guests = Integer.parseInt(guests.getText());
        if (totalCapacity < no_of_guests) {
            DisplayInformationLabel.setText("Venue capacity is insufficient");
        } else {
            SetBooking();
            if (w1.getBookingStatus().compareToIgnoreCase("Booked") == 0) {
                Add_to_DB();
                UpdateEvent();
                Send_Data_Between inst = Send_Data_Between.getInstance();
                if (inst.getCheck() == 1) {
                    ManagerControllerFunctions.GoToHome(event);
                } else {
                    ControllerFunctions.GoToHome(event);
                }

            }
            else DisplayInformationLabel.setText("Venue not available");

        }
    }

    public void DisplayFinalCost(ActionEvent actionEvent) {
        Venue v = new Venue(myChoice_venue, no_of_guests);
        v.setCapacity();
        totalCapacity = v.getCapacity();
        no_of_guests = Integer.parseInt(guests.getText());
        if (totalCapacity < no_of_guests) {
            DisplayInformationLabel.setText("Venue capacity is insufficient");
        } else SetBooking();
    }

    @FXML
    protected void ReturnToHome(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("NewBooking_View.fxml"));
        Stage stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(),1080,720);
        stage.setTitle("THE FUNCTION JUNCTION");
        stage.setScene(scene);
        stage.show();
    }

    private String getDescription() {
        String desc = "";
        Connection con = Connect.createConnection();
        try {
            Statement stmt = con.createStatement();
            String query = "Select * from venue where name='" + (myChoice_venue) + "'";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                desc = "Capacity=" + (rs.getInt(3)) + "\nPrice per day :-" + (rs.getInt(4)) + "\n" + (rs.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connect.closeConnection();
        }
        return desc;
    }

    private int totalCapacity;
    private int no_of_guests;

//    public void getPass(String loginID) {
//        String password = "";
//        try {
//            Connection conn = Connect.createConnection();
//            String query = "Select * from customer where loginid=?";
//            PreparedStatement preStatement = conn.prepareStatement(query);
//            preStatement.setString(1, loginID);
//            ResultSet rs = preStatement.executeQuery();
//            if (rs.next()) {
//                Name = rs.getString("Name");
//                Email = rs.getString("Email");
//                PhNumber = rs.getString("PhoneNumber");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            Connect.closeConnection();
//        }
//    }

    protected String GetBookingID() {
        String bookingid = "0";
        Connection c = Connect.createConnection();
        try {
            String query = "Select max(BookingID) from bookinghistory";
            Statement s1 = c.createStatement();
            ResultSet rs = s1.executeQuery(query);
            if (rs.next()) {
                bookingid = rs.getString(1);
                int bs = Integer.parseInt(bookingid) + 1;
                bookingid = String.valueOf(bs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookingid;
    }

    double Venue_cost;
    Custom w1 = new Custom();

    protected void SetBooking() {

        LocalDate startdate = Start_Date.getValue();
        LocalDate enddate = End_Date.getValue();
        Date sd = Date.valueOf(startdate);
        Date ed = Date.valueOf(enddate);
        String venue = Venue_choicebox.getValue();
        Venue v1 = new Venue(venue, Integer.parseInt(guests.getText()));
        no_of_guests = Integer.parseInt(guests.getText());
        v1.setCapacity();
        totalCapacity = v1.getCapacity();
        String booking_status = v1.getBookingStatus(sd, ed);
        double Final_Cost;
        if (booking_status.compareToIgnoreCase("Available") == 0) {
            next_booking_id = Integer.parseInt(GetBookingID());
            w1.setBookingStatus("Booked");
            w1.setStartDate(sd);
            w1.setEndDate(ed);
            w1.setVenue(v1);
            Venue_cost = w1.CalculateCost();
            Final_Cost = Venue_cost;
            w1.setFinalCost(Final_Cost);
            DisplayInformationLabel.setText("\nVenue Cost : " + (Venue_cost)
                            + "\nFinal_Cost = " + (Final_Cost));
        }
    }
    protected void Add_to_DB() throws SQLException {
        Send_Data_Between instance = Send_Data_Between.getInstance();
        Customer c1 = instance.getCustomer();
        String LoginID = c1.getLoginId();
        Connection dbcon = Connect.createConnection();
        String Query1 = "select * from customer where LoginID = ?";
        PreparedStatement ps = dbcon.prepareStatement(Query1);
        ps.setString(1,LoginID);
        ResultSet rs = ps.executeQuery();
        String name="",email="",ph_number="";
        if(rs.next())
        {
            name = rs.getString(3);
            email = rs.getString(6);
            ph_number = rs.getString(5);
        }

        String query2 = "insert into bookinghistory values(?,?,?,?,?,?,?,?)";
        PreparedStatement ps1 = dbcon.prepareStatement(query2);
        ps1.setString(1,GetBookingID());
        ps1.setString(2,name);
        ps1.setString(3,email);
        ps1.setString(4,ph_number);
        ps1.setString(5,myChoice_venue);
        ps1.setString(6,w1.getStartDate().toString());
        ps1.setString(7,w1.getEndDate().toString());
        ps1.setString(8,"Booked");
        ps1.executeUpdate();
    }
    public static String managerDetails(){
        Connection conn= Connect.createConnection();
        StringBuilder ans=new StringBuilder();
        ans.append("Contact the following for more details");
        try{String query="Select * from manager";
            Statement preStatement=conn.prepareStatement(query);
            ResultSet rs=preStatement.executeQuery(query);
            while(rs.next()){
                ans.append("\nName ").append(rs.getString("name")).append(" Contact ").append(rs.getString("phonenumber"));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            Connect.closeConnection();
        }
        return ans.toString();
    }
    public void UpdateEvent() throws SQLException {
        Send_Data_Between need=Send_Data_Between.getInstance();
        Customer k=need.getCustomer();
        Customer c=Customer.getDetailsCustomer(k.getLoginId());
        Event.updateEvent(w1,c,Integer.parseInt(String.valueOf(next_booking_id)),guests1.getText());

    }
}
