package systemController;


import java.io.IOException;


import java.io.InputStream;
import businessController.OwnerAddEmployeeController;
import businessController.OwnerAddServiceController;
import businessController.OwnerBusinessHoursController;
import businessController.OwnerCreateBookingController;
import businessController.OwnerCustomizeLayoutController;
import businessController.OwnerMenuController;
import businessController.OwnerRegisterController;
import businessController.OwnerViewAllBookingController;
import businessController.OwnerViewEmployeeController;
import businessController.OwnerViewNewBookingController;
import customerController.CustomerBookingAvailabilityController;
import customerController.CustomerCreateBookingController;
import customerController.CustomerMenuController;
import customerController.CustomerRegisterController;
import database.DatabaseManager;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This class create pages for different functionalities and provide a simple default view for clients.
 * @author ranlu
 */
public class ViewController{			//facade

	private String username;
	private DatabaseManager databaseManager;
	private Stage stage;
    private AnchorPane container;
    private String message;				//welcome message for business owner
    private String headerColor,footerColor;
    private String logoURL;

	public ViewController(){}

    /**
     * create appointment booking system database,
     * create all tables and insert entities for them.
     * @throws IOException If any exceptions occur.
     */
    public void initDatabase(DatabaseManager databaseManager) throws IOException{

    	this.databaseManager = databaseManager;
    	databaseManager.deleteDatabase();
    	databaseManager.createNewDatabase("AppointmentBookingSystem.db");

    	databaseManager.createBusinessTable();
    	databaseManager.createCustomerInfoTable();
    	databaseManager.createEmployeeTable();
    	databaseManager.createBusinessTimeTable();
    	databaseManager.createWorkingTimeTable();
    	databaseManager.createBookingTable();
    	databaseManager.createServiceTable();

    	databaseManager.insertInitialEntitiesForBusiness();
    	databaseManager.insertInitialEntitiesForCustomerInfo();
        databaseManager.insertInitialEntitiesForEmployee();
        databaseManager.insertInitialEntitiesForBusinessHours();
        databaseManager.insertInitialEntitiesForWorkingTime();
    	databaseManager.insertInitialEntitiesForBooking();
        databaseManager.insertInitialEntitiesForService();
    }

    public void setHeader(String headerColor){
    	this.headerColor = headerColor;
    }

    public void setFooter(String footerColor){
    	this.footerColor = footerColor;
    }
    public void setLogoURL(String logoURL){
    	this.logoURL = logoURL;
    }
    /**
     * Pass username as a primary key.
     * @param username received from loginController, pass to next level controller.
     */
	public void setUserName(String username){
		this.username = username;
	}

    public void initStage(Stage stage)
    {
        this.stage = stage;
        stage.setResizable(false);
        createMain();
        gotoLogin();
        stage.show();
    }

    public void setContainer(AnchorPane container)
    {
        this.container = container;
    }

    public void setMessage(String message){
    	this.message = message;
    }
    /**
     * Create the main page, set default container.
     */
    public void createMain()
    {
        try {
            MainController main = (MainController) createScene("Main.fxml");
            main.initViewController(this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Switch to the the page for login.
     */
    public void gotoLogin()
    {
        try {
            LoginController login = (LoginController) setScene("Login.fxml");
            login.initViewController(this);
            login.initDatabaseManager(databaseManager);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Switch to the business menu.
     */
    public void gotoBusinessMenu()
    {
        try {
        	OwnerMenuController businessMenu = (OwnerMenuController) setScene("BusinessMenu.fxml");
        	businessMenu.initViewController(this);
        	businessMenu.initDatabaseManager(databaseManager);
        	businessMenu.setUsername(username);
        	businessMenu.initMessage(message);
        	businessMenu.initLogo(logoURL);
        	businessMenu.setHeader(headerColor);
        	businessMenu.setFooter(footerColor);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Switch to the customer menu.
     */
    public void gotoCustomerMenu()
    {
        try {
        	CustomerMenuController customerMenu = (CustomerMenuController) setScene("CustomerMenu.fxml");
        	customerMenu.initViewController(this);
        	customerMenu.initDatabaseManager(databaseManager);
        	customerMenu.setUsername(username);
        	customerMenu.welcomeMessage();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Switch to the customer register.
     */
    public void gotoCustomerRegister()
    {
        try {
        	CustomerRegisterController customerRegister = (CustomerRegisterController) setScene("CustomerRegister.fxml");
        	customerRegister.initViewController(this);
        	customerRegister.initDatabaseManager(databaseManager);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Switch to the owner register.
     */
    public void gotoOwnerRegister()
    {
        try {
        	OwnerRegisterController ownerRegister = (OwnerRegisterController) setScene("OwnerRegister.fxml");
        	ownerRegister.initViewController(this);
        	ownerRegister.initDatabaseManager(databaseManager);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Switch to the add new service.
     */
    public void gotoService()
    {
        try {
            OwnerAddServiceController service = (OwnerAddServiceController) setScene("AddServices.fxml");
            service.initViewController(this);
            service.initDatabaseManager(databaseManager);
            service.setUsername(username);
            service.initDuration();
        	service.setHeader(headerColor);
        	service.setFooter(footerColor);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Switch to the add new employee or update employee information.
     */
    public void gotoEmployee()
    {
        try {
            OwnerAddEmployeeController employee = (OwnerAddEmployeeController) setScene("AddEmployee.fxml");
            employee.initViewController(this);
            employee.initDatabaseManager(databaseManager);
            employee.setUsername(username);
            employee.initTimeUnit();
        	employee.setHeader(headerColor);
        	employee.setFooter(footerColor);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Switch to the add or update business hours.
     */
    public void gotoBusinessHours()
    {
        try {
        	OwnerBusinessHoursController business = (OwnerBusinessHoursController) setScene("AddBusinessHours.fxml");
        	business.initViewController(this);
        	business.initDatabaseManager(databaseManager);
        	business.setUsername(username);
        	business.initBusinessHours();
        	business.setHeader(headerColor);
        	business.setFooter(footerColor);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Switch to view all bookings.
     */
    public void gotoAllBookings()
    {
        try {
        	OwnerViewAllBookingController booking = (OwnerViewAllBookingController) setScene("ViewAllBookings.fxml");
        	booking.initViewController(this);
        	booking.initDatabaseManager(databaseManager);
        	booking.setUsername(username);
        	booking.populateTable();
        	booking.setHeader(headerColor);
        	booking.setFooter(footerColor);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Switch to view new bookings, new bookings are those after current date and time.
     */
    public void gotoNewBookings()
    {
        try {
        	OwnerViewNewBookingController booking = (OwnerViewNewBookingController) setScene("ViewNewBookings.fxml");
        	booking.initViewController(this);
        	booking.initDatabaseManager(databaseManager);
        	booking.setUsername(username);
        	booking.populateTable();
        	booking.setHeader(headerColor);
        	booking.setFooter(footerColor);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Switch to the page for creating a booking.
     */
    public void gotoOwnerCreateBooking()
    {
        try {
        	OwnerCreateBookingController booking = (OwnerCreateBookingController) setScene("OwnerCreateBooking.fxml");
        	booking.initViewController(this);
        	booking.initDatabaseManager(databaseManager);
        	booking.setUsername(username);
        	booking.getEmployee();
        	booking.getService();
        	booking.setHeader(headerColor);
        	booking.setFooter(footerColor);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Switch to the page for viewing all workers' availabilities.
     */
    public void gotoViewWorkers()
    {
        try {
        	OwnerViewEmployeeController employee = (OwnerViewEmployeeController) setScene("ShowAllWorkers.fxml");
        	employee.initViewController(this);
        	employee.initDatabaseManager(databaseManager);
        	employee.setUsername(username);
        	employee.populateTable();
        	employee.setHeader(headerColor);
        	employee.setFooter(footerColor);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Switch to the page for customer creating a booking.
     */
    public void gotoCustomerCreateBooking()
    {
        try {
        	CustomerCreateBookingController customer = (CustomerCreateBookingController) setScene("CustomerCreateBooking.fxml");
        	customer.initViewController(this);
        	customer.initDatabaseManager(databaseManager);
        	customer.setUsername(username);
        	customer.initOwnerList();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Customer checks booking availability.
     */
    public void gotoViewBookingAvailability(){
        try {
        	CustomerBookingAvailabilityController booking = (CustomerBookingAvailabilityController) setScene("ViewBookingAvailability.fxml");
        	booking.initViewController(this);
        	booking.initDatabaseManager(databaseManager);
        	booking.initOwnerList();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Customer checks booking availability.
     */
    public void gotoLayout(){
        try {
        	OwnerCustomizeLayoutController layout = (OwnerCustomizeLayoutController) setScene("CustomizeLayout.fxml");
        	layout.initViewController(this);
        	layout.setHeader(headerColor);
        	layout.setFooter(footerColor);
        	layout.initStage(stage);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Sets the scene to the supplied fxml document - Used for initially
     * creating the  stage with Main.fxml.
     * @param  fxml The filename of the view to switch to.
     * @return Returns the controller object to allow objects to be passed to
     * it.
     *
     * This was based on the tutorial FXML-LoginDemo provided by Oracle.
     */
    public Initializable createScene(String fxml) throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = getClass().getResourceAsStream("/view/"+fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(getClass().getResource("/view/"+fxml));
        Parent pane;
        try {
            pane = (Parent) loader.load(in);
        }
        finally {
            in.close();
        }
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        return (Initializable) loader.getController();
    }

    /* Switches the contents of the scene to another fxml document provided */
    public Initializable setScene(String fxml) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/view/"+fxml));
        Node node = (Node) loader.load();
        /* Anchor to all sides */
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        container.getChildren().setAll(node);
        return (Initializable) loader.getController();
    }
}
