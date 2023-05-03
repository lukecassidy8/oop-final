package com.example.oopfinal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.application.Platform;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * The type Return item controller.
 */
public class ReturnItemController implements Initializable {
    @FXML
    private TableView<Item> tableItems;
    @FXML
    private TableColumn<Item, Integer> columnID;
    @FXML
    private TableColumn <Item, String> columnItemName;
    @FXML
    private TableColumn <Item, String> columnDesc;
    @FXML
    private TableColumn <Item, Date>columnLoanDate;
    @FXML
    private TableColumn <Item, Date> columnReturnDate;
    @FXML
    private TableColumn <Item, Integer> columnLoanedOut;
    @FXML
    private ComboBox<String> comboBoxItems;
    @FXML
    private ComboBox<Integer> comboBoxSelectID;
    @FXML
    private Button getComboBoxItems;
    @FXML
    private Button btnConfirmReturn;
    @FXML
    private Button btnViewAllLoanedOutItems;
    private final ObservableList<Item> itemList = FXCollections.observableArrayList();
    @FXML
    private ImageView imageViewLogo;
    @FXML
    private Label labelSelectID;
    @FXML
    private ImageView imageViewQuit;
    @FXML
    private ImageView imageViewFaults;
    @FXML
    private ImageView imageViewLoan;
    @FXML
    private ImageView imageViewHome;
    private Stage stage;
    private Scene scene;
    private Parent root;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxItems.getItems().addAll("24 Inch Monitor", "27 Inch Monitor", "Mouse", "Keyboard", "Monitor Stand",
                "Headset", "Laptop Charger", "Ergonomic Chair", "Earphones", "HDMI Cable",
                "Standard-issue laptop", "Development Laptop");
        comboBoxSelectID.setVisible(false);
        btnConfirmReturn.setVisible(false);
        labelSelectID.setVisible(false);
        Image logoImage = new Image("file:/C:/Users/luke.cassidy/Desktop/library system logo new.png");
        imageViewLogo.setImage(logoImage);
        imageViewLogo.setFitHeight(90);
        imageViewLogo.setFitWidth(90);
        Image logoutImage = new Image("file:/C:/Users/luke.cassidy/Desktop/logout.png");
        imageViewQuit.setImage(logoutImage);
        imageViewQuit.setFitWidth(50);
        imageViewQuit.setFitHeight(50);
        Image loadLoanPage = new Image("file:/C:/Users/luke.cassidy/Desktop/loan.png");
        imageViewLoan.setImage(loadLoanPage);
        imageViewLoan.setFitWidth(50);
        imageViewLoan.setFitHeight(50);
        imageViewLoan.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("SearchItems.fxml"));
                    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Image viewFaultsImage = new Image("file:/C:/Users/luke.cassidy/Desktop/faults.png");
        imageViewFaults.setImage(viewFaultsImage);
        imageViewFaults.setFitHeight(50);
        imageViewFaults.setFitWidth(50);
        imageViewFaults.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("faults.fxml"));
                    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Image homeImage = new Image("file:/C:/Users/luke.cassidy/Desktop/home.png");
        imageViewHome.setImage(homeImage);
        imageViewHome.setFitHeight(50);
        imageViewHome.setFitWidth(50);
        imageViewHome.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("homepage.fxml"));
                    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Gets search database connection.
     *
     * @return the search database connection
     * @throws SQLException the sql exception
     */
    public Connection getSearchDatabaseConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "Amanda140222!");
    }

    /**
     * Gets items.
     *
     * @return the items
     */
    public ObservableList<Item> getItems() {
        itemList.clear();
        try(
                Connection connection = getSearchDatabaseConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM items WHERE loanedOut = 1")

        ){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    Item item = new Item(
                            resultSet.getString("itemName"),
                            resultSet.getInt("itemID"),
                            resultSet.getDate("loanDate"),
                            resultSet.getDate("returnDate"),
                            resultSet.getString("itemDesc"),
                            resultSet.getInt("loanedOut")
                    );
                    itemList.add(item);
                }
            }
        }catch (SQLException e){
            throw new RuntimeException("Can't search items", e);
        }
        return itemList;
    }

    /**
     * Gets items from combo box.
     *
     * @param itemName the item name
     * @return the items from combo box
     */
    public ObservableList<Item> getItemsFromComboBox(String itemName) {
        itemList.clear();
        try(Connection connection = getSearchDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM items WHERE itemName = ? AND loanedOut = 1")
        ){
            preparedStatement.setString(1, itemName);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    comboBoxSelectID.getItems().add(resultSet.getInt("itemID"));
                    Item item = new Item(
                            resultSet.getString("itemName"),
                            resultSet.getInt("itemID"),
                            resultSet.getDate("loanDate"),
                            resultSet.getDate("returnDate"),
                            resultSet.getString("itemDesc"),
                            resultSet.getInt("loanedOut")
                    );
                    itemList.add(item);
                }
            }
        }catch (SQLException e){
            throw new RuntimeException("Can't search items", e);
        }
        return itemList;
    }

    /**
     * View all loaned out items.
     */
    public void viewAllLoanedOutItems(){
        getItems();
        columnID.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        columnItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        columnDesc.setCellValueFactory(new PropertyValueFactory<>("itemDesc"));
        columnLoanDate.setCellValueFactory(new PropertyValueFactory<>("loanDate"));
        columnReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        columnLoanedOut.setCellValueFactory(new PropertyValueFactory<>("loanedOut"));
        tableItems.setItems(itemList);
        tableItems.refresh();
    }

    /**
     * Get item from combo box.
     */
    public void getItemFromComboBox(){
        String itemName = comboBoxItems.getValue();
        comboBoxSelectID.getItems().clear();
        getItemsFromComboBox(itemName);
        columnID.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        columnItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        columnDesc.setCellValueFactory(new PropertyValueFactory<>("itemDesc"));
        columnLoanDate.setCellValueFactory(new PropertyValueFactory<>("loanDate"));
        columnReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        columnLoanedOut.setCellValueFactory(new PropertyValueFactory<>("loanedOut"));
        tableItems.setItems(itemList);
        tableItems.refresh();
        comboBoxSelectID.setVisible(true);
        btnConfirmReturn.setVisible(true);
        labelSelectID.setVisible(true);
    }

    /**
     * Validate return date.
     *
     * @throws SQLException the sql exception
     */
    public void validateReturnDate() throws SQLException {
        Connection connection = getSearchDatabaseConnection();
        Integer selectedValue = comboBoxSelectID.getValue();
        LocalDateTime timeNow = LocalDateTime.now();
        String getReturnDateQuery = "SELECT returnDate FROM items WHERE itemID = ?";
        String returnQuery = "UPDATE items SET loanedOut = ? WHERE itemID = ?";
        PreparedStatement prepareStatementReturnQuery = connection.prepareStatement(returnQuery);
        prepareStatementReturnQuery.setBoolean(1, false);
        prepareStatementReturnQuery.setInt(2, selectedValue);
        PreparedStatement preparedReturnDateQuery = connection.prepareStatement(getReturnDateQuery);
        preparedReturnDateQuery.setInt(1, selectedValue);
        try {
            ResultSet resultSet = preparedReturnDateQuery.executeQuery();
            if (resultSet.next()){
                LocalDateTime returnDateTime = resultSet.getTimestamp("returnDate").toLocalDateTime();
                long currentDay = System.currentTimeMillis() / (24 * 60 * 60 * 1000);
                long returnDay = returnDateTime.toLocalDate().toEpochDay();
                long difference = returnDay - currentDay;
                if (timeNow.isBefore(returnDateTime)){
                    long overdueCharge = difference * 5;
                    infoBox("This item is overdue, this item is " + difference + " days overdue and you owe £" +overdueCharge, null, "Overdue Item");
                } else {
                    prepareStatementReturnQuery.executeUpdate();
                    infoBox("This item has returned.", null, "Returned Successfully");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Info box.
     *
     * @param infoMessage the info message
     * @param headerText  the header text
     * @param title       the title
     */
    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
    /**
     * Quit.
     */
    public void quit(){
        Platform.exit();
    }
}
