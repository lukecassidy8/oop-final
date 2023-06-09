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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * The type Faults controller.
 */
public class FaultsController implements Initializable {
    @FXML
    private TableView<Fault> tableFaults;
    @FXML
    private TableColumn<Fault, Integer> columnFaultID;
    @FXML
    private TableColumn<Fault, Integer> columnItemID;
    @FXML
    private TableColumn<Fault, String> columnFaultDescription;
    @FXML
    private TableColumn<Fault, String> columnSeverity;
    @FXML
    private TableColumn<Fault, Date> columnCreatedAt;
    @FXML
    private TableColumn<Fault, String> columnItemName;
    @FXML
    private ComboBox<String> comboBoxItems;
    @FXML
    private ComboBox<Integer>comboBoxSelectID;
    @FXML
    private Button btnSelectID;
    @FXML
    private ImageView imageViewQuit;
    @FXML
    private ImageView imageViewHome;
    @FXML
    private ImageView imageViewLogo;
    @FXML
    private ImageView imageViewLoan;
    @FXML
    private ImageView imageViewReturn;
    @FXML
    private Label labelSelectID;
    @FXML
    private TextField textDescription;
    @FXML
    private Label labelFaultInfo;
    @FXML
    private ComboBox comboBoxSeverity;
    @FXML
    private Button btnConfirmAddFault;

    private Stage stage;
    private Scene scene;
    private final ObservableList<Fault> faultList = FXCollections.observableArrayList();
    private final ObservableList<Item> itemList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxItems.getItems().addAll("24 Inch Monitor", "27 Inch Monitor", "Mouse", "Keyboard", "Monitor Stand",
                "Headset", "Laptop Charger", "Ergonomic Chair", "Earphones", "HDMI Cable",
                "Standard-issue laptop", "Development Laptop");
        comboBoxSelectID.setVisible(false);
        btnSelectID.setVisible(false);
        labelSelectID.setVisible(false);
        Image logoImage = new Image(getClass().getResourceAsStream("/images/sidebar/logo.png"));
        imageViewLogo.setImage(logoImage);
        imageViewLogo.setFitHeight(90);
        imageViewLogo.setFitWidth(90);
        Image logoutImage = new Image(getClass().getResourceAsStream("/images/sidebar/logout.png"));
        imageViewQuit.setImage(logoutImage);
        imageViewQuit.setFitWidth(50);
        imageViewQuit.setFitHeight(50);
        Image homeImage = new Image(getClass().getResourceAsStream("/images/sidebar/home.png"));
        imageViewHome.setImage(homeImage);
        imageViewHome.setFitWidth(50);
        imageViewHome.setFitHeight(50);
        imageViewHome.setOnMouseClicked(new EventHandler<>() {
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
        Image returnImage = new Image(getClass().getResourceAsStream("/images/sidebar/return.png"));
        imageViewReturn.setImage(returnImage);
        imageViewReturn.setFitHeight(50);
        imageViewReturn.setFitWidth(50);
        imageViewReturn.setOnMouseClicked(new EventHandler<>() {
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
        Image loadLoanPage = new Image(getClass().getResourceAsStream("/images/sidebar/loan.png"));
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
        textDescription.setVisible(false);
        labelFaultInfo.setVisible(false);
        comboBoxSeverity.setVisible(false);
        btnConfirmAddFault.setVisible(false);
    }

    /**
     * Gets search database connection.
     *
     * @return the search database connection
     * @throws SQLException the sql exception
     */
    public Connection getSearchDatabaseConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "Amanda140222!");
    }

    /**
     * Gets item id from combo box.
     *
     * @param itemName the item name
     * @return the item id from combo box
     */
    public ObservableList<Item> getItemIDFromComboBox(String itemName) {
        itemList.clear();
        try(Connection connection = getSearchDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM items WHERE itemName = ?")
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
     * Get faults observable list.
     *
     * @param itemID the item id
     * @return the observable list
     */
    public ObservableList<Fault> getFaults(Integer itemID){
        faultList.clear();
        try(Connection connection = getSearchDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from faults WHERE itemID = ?")
        ) {
            preparedStatement.setInt(1, itemID);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    Fault fault = new Fault(
                            resultSet.getInt("faultID"),
                            resultSet.getInt("itemID"),
                            resultSet.getString("description"),
                            resultSet.getString("severity"),
                            resultSet.getTimestamp("createdAt").toLocalDateTime(),
                            resultSet.getTimestamp("updatedAt").toLocalDateTime()
                    );
                    faultList.add(fault);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't search items",e);
        }
        return faultList;
    }

    /**
     * View faults.
     */
    public void viewFaults(){
        Integer itemID = comboBoxSelectID.getValue();
        if (comboBoxSelectID.getSelectionModel().isEmpty()){
            infoBox("Please select an item ID", null, "Error");
            return;
        }
        getFaults(itemID);
        columnItemID.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        columnFaultID.setCellValueFactory(new PropertyValueFactory<>("faultID"));
        columnFaultDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnSeverity.setCellValueFactory(new PropertyValueFactory<>("severity"));
        columnCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        tableFaults.setItems(faultList);
        tableFaults.refresh();
    }

    /**
     * Get items from combo box.
     */
    public void getItemsFromComboBox(){
        String itemName = comboBoxItems.getValue();
        if (comboBoxItems.getSelectionModel().isEmpty()){
            infoBox("Please select an item", null, "Error");
            return;
        }
        comboBoxSelectID.getItems().clear();
        getItemIDFromComboBox(itemName);
        columnItemID.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        columnFaultID.setCellValueFactory(new PropertyValueFactory<>("faultID"));
        columnFaultDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnSeverity.setCellValueFactory(new PropertyValueFactory<>("severity"));
        columnCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        tableFaults.setItems(faultList);
        tableFaults.refresh();
        comboBoxSelectID.setVisible(true);
        btnSelectID.setVisible(true);
        labelSelectID.setVisible(true);
    }

    /**
     * Add fault.
     */
    public void addFault(){
        textDescription.setVisible(true);
        labelFaultInfo.setVisible(true);
        comboBoxSeverity.setVisible(true);
        btnConfirmAddFault.setVisible(true);
        comboBoxSeverity.getItems().addAll("Critical", "High", "Medium", "Low");
    }

    /**
     * Add new fault.
     */
    public void addNewFault() {
        int itemID = comboBoxSelectID.getValue();
        int newFaultID = 0;
        try (Connection connection = getSearchDatabaseConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS entry_count FROM faults;");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int entry_count = resultSet.getInt("entry_count");
                newFaultID = entry_count + 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int faultID = newFaultID;
        String description = textDescription.getText();
        String severity = comboBoxSeverity.getValue().toString();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        Fault fault = new Fault(faultID, itemID, description, severity, createdAt, updatedAt);
        tableFaults.getItems().add(fault);
        if (description.isEmpty()){
            infoBox("Please populate description", null, "Error");
            return;
        }
        if (severity.isEmpty()){
            infoBox("Please populate the severity", null, "Error");
            return;
        }

        try (Connection connection = getSearchDatabaseConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO faults (faultID, itemID, description, severity, createdAt) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, faultID);
            preparedStatement.setInt(2, itemID);
            preparedStatement.setString(3, description);
            preparedStatement.setString(4, severity);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(createdAt));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
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
