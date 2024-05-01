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
        List<String> yList = new ArrayList<>();
        
        for(String data: yearList){
            yList.add(data);
        }
        
        ObservableList listData = FXCollections.observableArrayList(yList);
        crud_year.setItems(listData);
    }
    
    // Arraylist for year dropdown option for the UI
    private String[] genderList = {"Male", "Female", "Others"};
    public void studentGenderList() {
        List<String> yList = new ArrayList<>();
        
        for(String data: yearList){
            yList.add(data);
        }
        
        ObservableList listData = FXCollections.observableArrayList(yList);
        crud_year.setItems(listData);
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
