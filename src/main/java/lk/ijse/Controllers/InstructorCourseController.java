package lk.ijse.Controllers;

import javafx.beans.property.SimpleStringProperty;
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
import lk.ijse.BO.Instructor_Course_BO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.DAO.Impl.InstructorDAO;
import lk.ijse.DAO.Impl.Instructor_Course_DAO;
import lk.ijse.DAO.Impl.Student_CourseDAO;
import lk.ijse.DTO.InstructorDTO;
import lk.ijse.DTO.Instructor_Course_DTO;
import lk.ijse.Entity.Instructor;
import lk.ijse.Entity.Instructor_Course;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InstructorCourseController {

    public Label lblID;
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private ComboBox<String> cmbCourse;

    @FXML
    private ComboBox<String> cmbInstructor;

    @FXML
    private TableColumn<?, ?> colCourse;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<Instructor_Course, String> colInstructor;
    @FXML
    private TableColumn<?, ?> colStudent;

    @FXML
    private DatePicker dateCourse;

    @FXML
    private TableView<Instructor_Course> tblInstructorCourse;

    Student_CourseDAO studentCourseDAO = (Student_CourseDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DaoType.Student_Course);
    InstructorDAO instructorDAO = (InstructorDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DaoType.Instructor);
    Instructor_Course_BO instructorCourseBo = (Instructor_Course_BO) BOFactory.getBoFactory().getBo(BOFactory.BoType.Instructor_Course);
    Instructor_Course_DAO instructorCourseDao = (Instructor_Course_DAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DaoType.Instructor_Course);


    public void initialize() throws SQLException, ClassNotFoundException {
        getAllIdsEmail();
        getAllIdsBatch();
        setCellValueFactory();
        getAll();

        tblInstructorCourse.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cmbInstructor.setValue(newValue.getInstructor().getInstructorEmail());
                cmbCourse.setValue(newValue.getCourseName());
                dateCourse.setValue(newValue.getCourseDate().toLocalDate());
                lblID.setText(String.valueOf(newValue.getId()));
            }
        });

    }

    private void getAll() {
        ObservableList<Instructor_Course> obList = FXCollections.observableArrayList();

        try {
            List<Instructor_Course> instructorCourses = instructorCourseDao.getAll();
            for (Instructor_Course ic : instructorCourses) {
                obList.add(ic);
            }
            tblInstructorCourse.setItems(obList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));
        //set to Instructor name in table
        colInstructor.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getInstructor().getInstructorName())
        );
        colCourse.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("courseName"));
        colDate.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("courseDate"));

    }

    private void getAllIdsEmail() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> Emails = instructorDAO.getEmails();

            for (String s : Emails) {
                obList.add(s);
            }
            cmbInstructor.setItems(obList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void getAllIdsBatch() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> names = studentCourseDAO.getAllCourseBatch();

            // Remove duplicates using Set
            Set<String> batchNumber = new HashSet<>(names);
            for (String s : batchNumber) {
                obList.add(s);
            }
            cmbCourse.setItems(obList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnAddOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String InstructorEmail = cmbInstructor.getValue();
        String CourseBatch = cmbCourse.getValue();
        LocalDate date = dateCourse.getValue();



    try {

        Instructor instructor = instructorDAO.searchByEmail(InstructorEmail);
        InstructorDTO instructorDTO = new InstructorDTO(
                instructor.getInstructorId(),
                instructor.getInstructorName(),
                instructor.getInstructorEmail(),
                instructor.getInstructorPhone(),
                instructor.getInstructorAddress()
        );

        Instructor_Course_DTO instructorCourseDto = new Instructor_Course_DTO(
                0,
                instructorDTO,
                CourseBatch,
                java.sql.Date.valueOf(date)
        );

        boolean isSave = instructorCourseBo.save(instructorCourseDto);
        if (isSave) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Course Assigned Successfully!");
            alert.show();
            getAll();
            clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Course Not Assigned!");
            alert.show();
        }

    } catch (Exception e) {
        throw new RuntimeException(e);}

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
clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = lblID.getText();

        try {
            boolean isDeleted = instructorCourseBo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Course Assignment deleted successfully!").show();
                clear();
                getAll();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete Course Assignment!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }

    }

    public void cmbInstructorOnAction(ActionEvent actionEvent) {
    }

    public void cmbCourseOnAction(ActionEvent actionEvent) {
    }

    public void clear(){
        getAll();
        cmbCourse.setValue(null);
        cmbInstructor.setValue(null);
        dateCourse.setValue(null);
    }
}
