/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc;

import classes.studentData;
import database.database;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author pc
 */
public class CRUDController implements Initializable {

    @FXML
    private Button crud_addBtn;

    @FXML
    private Button crud_clearBtn;
    
    @FXML
    private Button crud_deleteBtn;
    
    @FXML
    private Button crud_updateBtn;

    @FXML
    private TableColumn<studentData, String> crud_col_course;

    @FXML
    private TableColumn<studentData, String> crud_col_fullName;

    @FXML
    private TableColumn<studentData, String> crud_col_gender;

    @FXML
    private TableColumn<studentData, String> crud_col_studentNumber;

    @FXML
    private TableColumn<studentData, String> crud_col_year;
    
    @FXML
    private TableView<studentData> crud_tableView;  

    @FXML
    private ComboBox<?> crud_course;    

    @FXML
    private TextField crud_fullName;

    @FXML
    private ComboBox<?> crud_gender;

    @FXML
    private TextField crud_studentNumber;      

    @FXML
    private ComboBox<?> crud_year;
    
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    
    private Alert alert;
    
    public void studentAddBtn(){
                       
        connect = database.connect();
        
        try {
            
            if(crud_studentNumber.getText().isEmpty()
                    || crud_fullName.getText().isEmpty()
                    || crud_year.getSelectionModel().getSelectedItem() == null
                    || crud_course.getSelectionModel().getSelectedItem() == null
                    || crud_gender.getSelectionModel().getSelectedItem() == null){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank fields");
                alert.showAndWait();
            } else {
                String checkData = "SELECT student_number FROM student_info WHERE student_number = "
                        + crud_studentNumber.getText();

                prepare = connect.prepareStatement(checkData);
                result = prepare.executeQuery();

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Student Number: " + crud_studentNumber.getText() + " is already taken");
                    alert.showAndWait();
                } else {
                    String insertData = "INSERT INTO student_info (student_number, full_name, year, course, gender, date)"
                            + " VALUES(?,?,?,?,?,?)";
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, crud_studentNumber.getText());
                    prepare.setString(2, crud_fullName.getText());
                    prepare.setString(3, (String) crud_year.getSelectionModel().getSelectedItem());
                    prepare.setString(4, (String) crud_course.getSelectionModel().getSelectedItem());
                    prepare.setString(5, (String) crud_gender.getSelectionModel().getSelectedItem());
                    
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    
                    prepare.setString(6, String.valueOf(sqlDate));
                    
                    prepare.executeUpdate();
                    
                    // To update the tableView
                    studentShowData();
                }
            }                                                
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Arraylist for year dropdown option for the UI
    private String[] yearList = {"1st Year", "2nd Year", "3rd Year", "4th Year", "5th Year", "6th Year"};
    public void studentYearList() {
        List<String> yList = new ArrayList<>();
        
        for(String data: yearList){
            yList.add(data);
        }
        
        ObservableList listData = FXCollections.observableArrayList(yList);
        crud_year.setItems(listData);
    }
    
    // Arraylist for year dropdown option for the UI
    private String[] courseList = {"BSIT", "BSCS", "BSCpE", "BSCE"};
    public void studentCourseList() {
        List<String> cList = new ArrayList<>();
        
        for(String data: courseList){
            cList.add(data);
        }
        
        ObservableList listData = FXCollections.observableArrayList(cList);
        crud_course.setItems(listData);
    }
    
    // Arraylist for year dropdown option for the UI
    private String[] genderList = {"Male", "Female", "Others"};
    public void studentGenderList() {
        List<String> gList = new ArrayList<>();
        
        for(String data: genderList){
            gList.add(data);
        }
        
        ObservableList listData = FXCollections.observableArrayList(gList);
        crud_gender.setItems(listData);
    }
    
    public ObservableList<studentData> studentListData(){
        ObservableList<studentData> listData = FXCollections.observableArrayList();
        
        String selectData = "SELECT * FROM student_info";
        
        connect = database.connect();
        
        try {
            
            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();
            
            studentData sData;
            
            while(result.next()){
                sData = new studentData(result.getInt("student_number")
                        , result.getString("full_name")
                        , result.getString("year")
                        , result.getString("course")
                        , result.getString("gender"));
                
                listData.add(sData);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    
    private ObservableList<studentData> studentData;
    public void studentShowData(){
        studentData = studentListData();
        
        crud_col_studentNumber.setCellValueFactory(new PropertyValueFactory<>("studentNumber"));
        crud_col_fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        crud_col_year.setCellValueFactory(new PropertyValueFactory<>("year"));
        crud_col_course.setCellValueFactory(new PropertyValueFactory<>("course"));
        crud_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        
        crud_tableView.setItems(studentData);
    }
    
    public void studentSelectData(){
        studentData sData = crud_tableView.getSelectionModel().getSelectedItem();
        int num = crud_tableView.getSelectionModel().getSelectedIndex();
        
        if((num - 1) < - 1) return;
        
        crud_studentNumber.setText(String.valueOf(sData.getStudentNumber()));
        crud_fullName.setText(sData.getFullName());
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        studentYearList();
        studentCourseList();
        studentGenderList();
        
    }
    
}
