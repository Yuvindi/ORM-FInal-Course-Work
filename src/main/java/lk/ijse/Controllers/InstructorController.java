package lk.ijse.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.Impl.UserBOImpl;
import lk.ijse.BO.InstructorBO;
import lk.ijse.BO.UserBO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.DAO.Impl.InstructorDAO;
import lk.ijse.DAO.Impl.LoginDAO;
import lk.ijse.DTO.InstructorDTO;
import lk.ijse.Entity.Instructor;
import lk.ijse.Entity.Login;
import lk.ijse.Entity.User;

import java.sql.SQLException;
import java.util.List;

public class InstructorController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colInstructorID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPhone;

    @FXML
    private Label lblInstructorID;

    @FXML
    private TableView<Instructor> tblInstructors;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;
    InstructorBO instructorBO = (InstructorBO) BOFactory.getBoFactory().getBo(BOFactory.BoType.Instructor);
    InstructorDAO instructorDAO = (InstructorDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DaoType.Instructor);
    LoginDAO loginDAO = (LoginDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DaoType.Login);
    UserBO userBO = (UserBOImpl) BOFactory.getBoFactory().getBo(BOFactory.BoType.User);

    public void initialize() throws SQLException, ClassNotFoundException {
        getAllInstructors();
        setCellValueFactory();
        lastLoginID();
        tblInstructors.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lblInstructorID.setText(String.valueOf(newValue.getInstructorId()));
                txtName.setText(newValue.getInstructorName());
                txtEmail.setText(newValue.getInstructorEmail());
                txtPhone.setText(newValue.getInstructorPhone());
                txtAddress.setText(newValue.getInstructorAddress());
                txtEmail.setDisable(true);   //if click table row email not change
            }
        });

    }
    /*get last login detail*/
    private void lastLoginID() throws SQLException, ClassNotFoundException {
        Login login = loginDAO.getLastLogin();
        UserID(login.getUserID());

    }
    /*set security for access*/
    public void UserID(String ID) throws SQLException, ClassNotFoundException {
        User user = userBO.searchByIdUser(ID);
        String position = user.getPosition();

        if ("Receptionist".equals(position)) {
            btnBack.setDisable(false);
            btnClear.setDisable(false);
            btnAdd.setDisable(true);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        } else if ("Admin".equals(position)) {
            btnAdd.setDisable(false);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnBack.setDisable(false);
            btnClear.setDisable(false);
        }
    }

    private void setCellValueFactory() {
        colInstructorID.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("instructorId"));
        colName.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("instructorName"));
        colEmail.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("instructorEmail"));
        colPhone.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("instructorPhone"));
        colAddress.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("instructorAddress"));
    }

    private void getAllInstructors() throws SQLException, ClassNotFoundException {
       try{
           ObservableList<Instructor> obList = FXCollections.observableArrayList();
           List<Instructor> instructorList = instructorDAO.getAll();
           obList.addAll(instructorList);
           tblInstructors.setItems(obList);

       } catch (SQLException | ClassNotFoundException e) {
           throw new RuntimeException(e);
       }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String name = txtName.getText().trim();
        String address = txtAddress.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();
        if (!validateInputs(name, email, phone, address)) {
            return;
        }

        try {
            if (name.isEmpty() || email.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Name and Email are required!").show();
                return;
            }

            Instructor existingInstructor = instructorDAO.searchByEmail(email);
            if (existingInstructor != null) {
                new Alert(Alert.AlertType.ERROR, "Instructor with this email already exists!").show();
                clearFields();
                return;
            }

            InstructorDTO instructorDTO = new InstructorDTO(
                    0,
                    name,
                    email,
                    phone,
                    address
            );

            boolean isAdded = instructorBO.save(instructorDTO);

            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, "Instructor saved successfully!").show();
                clearFields();
                getAllInstructors();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save instructor!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Unexpected error: " + e.getMessage()).show();
        }
    }


    @FXML
    void btnBackOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashBoard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String idText = lblInstructorID.getText();
        int id = Integer.parseInt(idText);
        String email = txtEmail.getText();

        Instructor instructor = instructorDAO.searchByEmail(email);
        if (instructor == null) {
            new Alert(Alert.AlertType.ERROR, "Instructor not found!").show();
            clearFields();
            return;
        }

        try {
            boolean isDeleted = instructorBO.delete(String.valueOf(id));

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Instructor deleted successfully!").show();
                clearFields();
                getAllInstructors();
                txtEmail.setDisable(false); // Enable email field after deletion
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete instructor!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Unexpected error: " + e.getMessage()).show();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String idText = lblInstructorID.getText();
        int id = Integer.parseInt(idText);
        String name = txtName.getText().trim();
        String address = txtAddress.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();

        if (!validateInputs(name, email, phone, address)) {

            return;
        }
        try {
            if (name.isEmpty() || email.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Name and Email are required!").show();
                return;
            }

            InstructorDTO instructorDTO = new InstructorDTO(
                    id,
                    name,
                    email,
                    phone,
                    address
            );

            boolean isUpdated = instructorBO.update(instructorDTO);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Instructor updated successfully!").show();
                clearFields();
                getAllInstructors();
                txtEmail.setDisable(false); //enable email after update
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update instructor!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Unexpected error: " + e.getMessage()).show();
        }

    }

    public void clearFields(){
        txtName.clear();
        txtAddress.clear();
        txtPhone.clear();
        txtEmail.clear();
        lblInstructorID.setText("");
        txtEmail.setDisable(false); // Enable email field when clearing
    }

    private boolean validateInputs(String name, String email, String phone, String address) {
        if (!name.matches("^[A-Za-z ]{2,}$")) {
            new Alert(Alert.AlertType.WARNING, "Invalid Name! Use only letters and at least 2 characters.").show();
            return false;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            new Alert(Alert.AlertType.WARNING, "Invalid Email! Please enter a valid email address.").show();
            return false;
        }

        if (!phone.matches("^\\d{10}$")) {
            new Alert(Alert.AlertType.WARNING, "Invalid Phone! Enter a 10-digit number.").show();
            return false;
        }

        if (address.length() < 5) {
            new Alert(Alert.AlertType.WARNING, "Invalid Address! Must be at least 5 characters long.").show();
            return false;
        }

        return true;
    }




}
