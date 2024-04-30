/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    private TableColumn<?, ?> crud_col_course;

    @FXML
    private TableColumn<?, ?> crud_col_fullName;

    @FXML
    private TableColumn<?, ?> crud_col_gender;

    @FXML
    private TableColumn<?, ?> crud_col_studentNumber;

    @FXML
    private TableColumn<?, ?> crud_col_year;

    @FXML
    private ComboBox<?> crud_course;

    @FXML
    private Button crud_deleteBtn;

    @FXML
    private TextField crud_fullName;

    @FXML
    private ComboBox<?> crud_gender;

    @FXML
    private TextField crud_studentNumber;

    @FXML
    private TableView<?> crud_tableView;

    @FXML
    private Button crud_updateBtn;

    @FXML
    private ComboBox<?> crud_year;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
