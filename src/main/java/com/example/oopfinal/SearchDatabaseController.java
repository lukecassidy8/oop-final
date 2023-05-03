package com.example.oopfinal;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * The type Search database controller.
 */
public class SearchDatabaseController implements Initializable {

    @FXML
    private TableView <Item> tableItems;
    @FXML
    private TableColumn <Item, Integer>columnID;
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
    private Button btnConfirmLoanOut;
    @FXML
    private ImageView imageViewQuit;
    @FXML
    private ImageView imageViewHome;
    @FXML
    private ImageView imageViewLogo;
    @FXML
    private ImageView imageViewFaults;
    @FXML
    private ImageView imageViewReturn;
    private Stage stage;
    private Scene scene;
    private Parent root;


    private final ObservableList<Item> itemList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        comboBoxItems.getItems().addAll("24 Inch Monitor", "27 Inch Monitor", "Mouse", "Keyboard", "Monitor Stand",
                "Headset", "Laptop Charger", "Ergonomic Chair", "Earphones", "HDMI Cable",
                "Standard-issue laptop", "Development Laptop");
        comboBoxSelectID.setVisible(false);
        btnConfirmLoanOut.setVisible(false);
        Image logoImage = new Image("file:/C:/Users/luke.cassidy/Desktop/library system logo new.png");
        imageViewLogo.setImage(logoImage);
        imageViewLogo.setFitHeight(90);
        imageViewLogo.setFitWidth(90);
        Image logoutImage = new Image("file:/C:/Users/luke.cassidy/Desktop/logout.png");
        imageViewQuit.setImage(logoutImage);
        imageViewQuit.setFitWidth(50);
        imageViewQuit.setFitHeight(50);
        Image homeImage = new Image("file:/C:/Users/luke.cassidy/Desktop/home.png");
        imageViewHome.setImage(homeImage);
        imageViewHome.setFitWidth(50);
        imageViewHome.setFitHeight(50);
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
        Image faultsImage = new Image("file:/C:/Users/luke.cassidy/Desktop/faults.png");
        imageViewFaults.setImage(faultsImage);
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
        Image returnImage = new Image("file:/C:/Users/luke.cassidy/Desktop/return.png");
        imageViewReturn.setImage(returnImage);
        imageViewReturn.setFitHeight(50);
        imageViewReturn.setFitWidth(50);
        imageViewReturn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("returnitem.fxml"));
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
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM items")

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
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM items WHERE itemName = ? AND loanedOut = 0")
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
     * Search items.
     */
    public void searchItems() {
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
     * Gets item from combo box.
     */
    public void getItemFromComboBox() {
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
        btnConfirmLoanOut.setVisible(true);
    }

    public void printToFile() {
        if (!itemList.isEmpty()) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(new File("C:/Users/luke.cassidy/Desktop/oop-final/itemDetails.txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            for (TableColumn<Item, ?> column : tableItems.getColumns()) {
                writer.print(column.getText() + "\t");
            }
            writer.println();
            for (Item item : tableItems.getItems()) {
                writer.print(item.getItemID() + "\t");
                writer.print(item.getItemName() + "\t");
                writer.print(item.getItemDesc() + "\t");
                writer.print(item.getLoanDate() + "\t");
                writer.print(item.getReturnDate() + "\t");
                writer.print(item.getLoanedOut() + "\t");
                writer.println();
            }
            writer.close();
            infoBox("Items printed to DB", null, "Success");

        }
        else {
            infoBox("you cannot print to the file as the DB is empty", null, "Error");
        }
    }

    /**
     * Confirm loan out.
     *
     * @throws SQLException the sql exception
     */
    public void confirmLoanOut() throws SQLException {
        Connection connection = getSearchDatabaseConnection();
        Integer selectedValue = comboBoxSelectID.getValue();
        LocalDateTime timeNow = LocalDateTime.now();
        String loanOutQuery = "UPDATE items SET loanedOut = ?, loanDate = ? WHERE itemID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(loanOutQuery);
        preparedStatement.setBoolean(1, true);
        preparedStatement.setObject(2, timeNow);
        preparedStatement.setInt(3, selectedValue);
        try {
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        infoBox("This item is now loaned out", null, "Loan Out Success");
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

