package tfj_gui.gui;

import Database.DBconnection.Connect;
import Database.TableView.*;
import Database.TableView.Menu;
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
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class NewBookingMarriageViewController implements Initializable
{
    @FXML
    public Label DisplayInformationLabel;
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
    private Label RemarkVenue;

    @FXML
    protected void GoToHome(ActionEvent event) throws IOException
    {
        Send_Data_Between inst=Send_Data_Between.getInstance();
        if(inst.getCheck()==1){
            ManagerControllerFunctions.GoToHome(event);}
        else{
            ControllerFunctions.GoToHome(event);
        }
    }
    @FXML
    protected void NewBookingButtonClicked(ActionEvent event) throws IOException
    {

    }
    @FXML
    protected void MyAccountButtonClicked(ActionEvent event) throws IOException
    {
        Send_Data_Between inst=Send_Data_Between.getInstance();
        if(inst.getCheck()==1){
            ManagerControllerFunctions.MyAccountButtonClicked(event);}
        else{
            ControllerFunctions.MyAccountButtonClicked(event);
        }
    }
    @FXML
    protected void MyBookingsButtonClicked(ActionEvent event) throws IOException
    {
        Send_Data_Between inst=Send_Data_Between.getInstance();
        if(inst.getCheck()==1){
            ManagerControllerFunctions.MyBookingsButtonClicked(event);}
        else{
            ControllerFunctions.MyBookingsButtonClicked(event);
        }
    }
    @FXML
    protected void ChangePasswordButtonClicked(ActionEvent event) throws IOException
    {
        Send_Data_Between inst=Send_Data_Between.getInstance();
        if(inst.getCheck()==1){
            ManagerControllerFunctions.ChangePasswordButtonClicked(event);}
        else{
            ControllerFunctions.ChangePasswordButtonClicked(event);
        }
    }
    @FXML
    protected void AboutUsButtonClicked(ActionEvent event) throws IOException
    {
        Send_Data_Between inst=Send_Data_Between.getInstance();
        if(inst.getCheck()==1){
            ManagerControllerFunctions.AboutUsButtonClicked(event);}
        else{
            ControllerFunctions.AboutUsButtonClicked(event);
        }
    }
    @FXML
    protected void HelpButtonClicked(ActionEvent event) throws IOException
    {
        Send_Data_Between inst=Send_Data_Between.getInstance();
        if(inst.getCheck()==1){
            ManagerControllerFunctions.HelpButtonClicked(event);}
        else{
            ControllerFunctions.HelpButtonClicked(event);
        }
    }
    @FXML
    protected void LogoutButtonClicked(ActionEvent event) throws IOException
    {
        Send_Data_Between inst=Send_Data_Between.getInstance();
        if(inst.getCheck()==1){
            ManagerControllerFunctions.LogoutButtonClicked(event);}
        else{
            ControllerFunctions.LogoutButtonClicked(event);
        }
    }
    @FXML
    protected void ExitButtonClicked(ActionEvent event) throws IOException
    {
        Send_Data_Between inst=Send_Data_Between.getInstance();
        if(inst.getCheck()==1){
            ManagerControllerFunctions.ExitButtonClicked(event);}
        else{
            ControllerFunctions.ExitButtonClicked(event);
        }
    }
    private final String[] Drinks_Choices = {"Lemon juice","Orange juice","Mixed fruit juice","Beer","---None---"};
    private final String[] Venue_Choices = new Venue().getAllVenueNames().toArray(new String[0]);
    private final String[] Decoration_Choices = {"Basic","Premium","Royal"};
    private final String[] Lunch_Choices ={"Basic","Premium","Royal","---None---"};
    private final String[] Snacks_Choices={"Basic","Premium","Royal","---None---"};
    private final String[] Breakfast_Choices={"Basic","Premium","Royal","---None---"};
    private final String[] Dinner_Choices={"Basic","Premium","Royal","---None---"};

    @FXML
    private ChoiceBox<String> Decoration_choicebox;
    @FXML
    private ChoiceBox<String> Breakfast_choicebox;
    @FXML
    private ChoiceBox<String> Drinks_choicebox;
    @FXML
    private ChoiceBox<String> Venue_choicebox;
    @FXML
    private ChoiceBox<String> Lunch_choicebox;
    @FXML
    private ChoiceBox<String> Snacks_choicebox;
    @FXML
    private ChoiceBox<String> Dinner_choicebox;
    @FXML
    private TextField guests;
    @FXML
    private Button GetCost;

    //@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Breakfast_choicebox.getItems().addAll();
        Breakfast_choicebox.setOnAction(this::Choice_in_Choice_box_breakfast);

        Drinks_choicebox.getItems().addAll(Drinks_Choices);
        Drinks_choicebox.setOnAction(this::Choice_in_Choice_box_drinks);

        Venue_choicebox.getItems().addAll(Venue_Choices);
        Venue_choicebox.setOnAction(this::Choice_in_Choice_box_venue);

        Decoration_choicebox.getItems().addAll(Decoration_Choices);
        Decoration_choicebox.setOnAction(this::Choice_in_Choice_box_decoration);

        Breakfast_choicebox.getItems().addAll(Breakfast_Choices);
        Breakfast_choicebox.setOnAction((this::Choice_in_Choice_box_breakfast));

        Lunch_choicebox.getItems().addAll(Lunch_Choices);
        Lunch_choicebox.setOnAction((this::Choice_in_Choice_box_lunch));

        Snacks_choicebox.getItems().addAll(Snacks_Choices);
        Snacks_choicebox.setOnAction((this::Choice_in_Choice_box_snacks));

        Dinner_choicebox.getItems().addAll(Dinner_Choices);
        Dinner_choicebox.setOnAction((this::Choice_in_Choice_box_dinner));
    }
    private String myChoice_decoration,myChoice_breakfast,myChoice_Drinks,myChoice_venue,myChoice_Lunch,myChoice_Snacks,myChoice_Dinner;
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
    protected void Choice_in_Choice_box_decoration(ActionEvent event)
    {
        myChoice_decoration=Decoration_choicebox.getValue();
        System.out.println(myChoice_decoration);
    }
    protected void Choice_in_Choice_box_drinks(ActionEvent event)
    {
        myChoice_Drinks=Drinks_choicebox.getValue();
        System.out.println(myChoice_Drinks);
    }
    protected void Choice_in_Choice_box_venue(ActionEvent event)
    {
        myChoice_venue=Venue_choicebox.getValue();
        RemarkVenue.setText(getDescription());
        System.out.println(myChoice_venue);
    }
    protected void Choice_in_Choice_box_breakfast(ActionEvent event)
    {
        myChoice_breakfast=Breakfast_choicebox.getValue();
        System.out.println(myChoice_breakfast);
    }
    protected void Choice_in_Choice_box_lunch(ActionEvent event)
    {
        myChoice_Lunch=Lunch_choicebox.getValue();
        System.out.println(myChoice_Lunch);
    }
    protected void Choice_in_Choice_box_snacks(ActionEvent event)
    {
        myChoice_Snacks=Snacks_choicebox.getValue();
        System.out.println(myChoice_Snacks);
    }
    protected void Choice_in_Choice_box_dinner(ActionEvent event)
    {
        myChoice_Dinner=Dinner_choicebox.getValue();
        System.out.println(myChoice_Dinner);
    }

    @FXML
    private DatePicker Start_Date = new DatePicker();
    protected void getStartDate(ActionEvent event)
    {
        LocalDate SD = Start_Date.getValue();
    }

    @FXML
    private DatePicker End_Date = new DatePicker();
    protected void getEndDate(ActionEvent event)
    {
        LocalDate ED = End_Date.getValue();
    }
    private double Cost;
    @FXML
    protected void NextNewBooking(ActionEvent event) throws IOException, SQLException {
        Venue v=new Venue(myChoice_venue,no_of_guests);
        v.setCapacity();
        totalCapacity=v.getCapacity();
        no_of_guests= Integer.parseInt(guests.getText());
        if(totalCapacity<no_of_guests){
            DisplayInformationLabel.setText("Venue capacity is insufficient");
        }
        else{
        SetBooking();
        if(w1.getBookingStatus().compareToIgnoreCase("Booked")==0) {
            Add_to_DB();
            UpdateEvent();
            Send_Data_Between inst = Send_Data_Between.getInstance();
            if (inst.getCheck() == 1) {
                ManagerControllerFunctions.GoToHome(event);
            } else {
                ControllerFunctions.GoToHome(event);
            }
        }
        else DisplayInformationLabel.setText("Venue not available");}
    }
    int next_booking_id=0;
    @FXML
    protected void DisplayFinalCost(ActionEvent event)
    {   Venue v=new Venue(myChoice_venue,no_of_guests);
        v.setCapacity();
        totalCapacity=v.getCapacity();
        no_of_guests= Integer.parseInt(guests.getText());
        if(totalCapacity<no_of_guests)
        {
        DisplayInformationLabel.setText("Venue capacity is insufficient");
        }
    else SetBooking();
        //DisplayInformationLabel.setText("Breakfast");
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

    private String Name;
    private String Email;
    private String PhNumber;
    private int totalCapacity;
    private int no_of_guests;
    public void getPass(String loginID){
        String password="";
        try{
            Connection conn= Connect.createConnection();
            String query="Select * from customer where loginid=?";
            PreparedStatement preStatement=conn.prepareStatement(query);
            preStatement.setString(1,loginID);
            ResultSet rs=preStatement.executeQuery();
            if(rs.next()){
                Name = rs.getString("Name");
                Email = rs.getString("Email");
                PhNumber = rs.getString("PhoneNumber");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            Connect.closeConnection();
        }
    }
    protected String GetBookingID()
    {
        String bookingid="0";
        Connection c = Connect.createConnection();
        try{
            String query="Select max(BookingID) from bookinghistory";
            Statement s1 = c.createStatement();
            ResultSet rs = s1.executeQuery(query);
            if(rs.next())
            {
                bookingid = rs.getString(1);
                int bs = Integer.parseInt(bookingid)+1;
                bookingid = String.valueOf(bs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookingid;
    }
    ArrayList<Menu> menu;
    double Venue_cost;
    double Breakfast_cost;
    double Lunch_cost;
    double Snacks_cost;
    double Drinks_cost;
    double Dinner_cost;
    Wedding w1 = new Wedding();
    protected void SetBooking()
    {

        LocalDate startdate = Start_Date.getValue();
        LocalDate enddate = End_Date.getValue();
        Date sd = Date.valueOf(startdate);
        Date ed = Date.valueOf(enddate);
        String venue = Venue_choicebox.getValue();
        Venue v1 = new Venue(venue,Integer.parseInt(guests.getText()));
        no_of_guests= Integer.parseInt(guests.getText());
        v1.setCapacity();
        totalCapacity=v1.getCapacity();
        String booking_status = v1.getBookingStatus(sd,ed);

        BreakFast b1 = new BreakFast();
        b1.setNumberOfGuests(no_of_guests);
        if(myChoice_breakfast.compareToIgnoreCase("---None---")!=0){
            b1.setBf_class(myChoice_breakfast);
            Breakfast_cost = b1.calculateCost();}
        else{
            Breakfast_cost=0;
        }
        Lunch l1 = new Lunch();
        l1.setNumberOfGuests(no_of_guests);
        if(myChoice_Lunch.compareToIgnoreCase("---None---")!=0){
            l1.setLunch_class(myChoice_Lunch);
            Lunch_cost = l1.calculateCost();}
        else{
            Lunch_cost=0;
        }

        Snacks s1 = new Snacks();
        s1.setNumberOfGuests(no_of_guests);
        if(myChoice_Snacks.compareToIgnoreCase("---None---")!=0){
            s1.setSnack_class(myChoice_Snacks);
            Snacks_cost = s1.calculateCost();}
        else{
            Snacks_cost=0;
        }

        Dinner d1 = new Dinner();
        d1.setNumberOfGuests(no_of_guests);
        if(myChoice_Dinner.compareToIgnoreCase("---None---")!=0){
            d1.setDinner_class(myChoice_Dinner);
            Dinner_cost = d1.calculateCost();}
        else{
            Dinner_cost=0;
        }

        Drinks dr1 = new Drinks();
        int drinks_index=0;
        if(Objects.equals(myChoice_Drinks, Drinks_Choices[0]))     drinks_index=1;
        if(Objects.equals(myChoice_Drinks, Drinks_Choices[1]))     drinks_index=2;
        if(Objects.equals(myChoice_Drinks, Drinks_Choices[2]))     drinks_index=3;
        if(Objects.equals(myChoice_Drinks, Drinks_Choices[3]))     drinks_index=4;
        if(Objects.equals(myChoice_Drinks, Drinks_Choices[4]))     drinks_index=0;
        dr1.setMyChoice(drinks_index);
        dr1.setNumberOfGuests(no_of_guests);
        Drinks_cost = dr1.calculateCost();
        double Menu_Cost = Breakfast_cost + Lunch_cost + Snacks_cost + Drinks_cost + Dinner_cost;
        System.out.print(Menu_Cost);
        double Final_Cost;
        if(booking_status.compareToIgnoreCase("Available") == 0)
        {
            next_booking_id = Integer.parseInt(GetBookingID());
            w1.setBookingStatus("Booked");
            w1.setChoice(myChoice_decoration);
            w1.setStartDate(sd);
            w1.setEndDate(ed);
            w1.setVenue(v1);
            Venue_cost = w1.CalculateCost();
            System.out.print(Venue_cost);
            Final_Cost = Venue_cost + Menu_Cost;
            w1.setFinalCost(Final_Cost);
            DisplayInformationLabel.setText("Breakfast Cost : "+(Breakfast_cost)+"\nLunch Cost : "+(Lunch_cost)
                    +"\nDrinks Cost : "+(Drinks_cost)
                    +"\nSnacks Cost : "+(Snacks_cost)
                    +"\nDinner Cost : "+(Dinner_cost)
                    +"\nVenue Cost : " +(Venue_cost)
                    +"\nFinal_Cost = "+(Final_Cost));
        }
//        else
//        {
//            DisplayInformationLabel.setText("Hall not available.");
//        }
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
        ps1.setString(1,String.valueOf(next_booking_id));
        ps1.setString(2,name);
        ps1.setString(3,email);
        ps1.setString(4,ph_number);
        ps1.setString(5,myChoice_venue);
        ps1.setString(6,w1.getStartDate().toString());
        ps1.setString(7,w1.getEndDate().toString());
        ps1.setString(8,"Booked");
        ps1.executeUpdate();
    }
    public void UpdateEvent() throws SQLException {
        Send_Data_Between need=Send_Data_Between.getInstance();
        Customer k=need.getCustomer();
        Customer c=Customer.getDetailsCustomer(k.getLoginId());
        Event.updateEvent(w1,c,Integer.parseInt(String.valueOf(next_booking_id)),"Marriage");

    }
}
