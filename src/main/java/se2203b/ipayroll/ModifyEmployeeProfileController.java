/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se2203b.ipayroll;

import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.*;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DoubleStringConverter;

/**
 * FXML Controller class
 *
 * @author Rzan
 */
public class ModifyEmployeeProfileController implements Initializable {

    @FXML
    private TabPane tb;
    @FXML
    private ComboBox id;
    @FXML
    private TextField fullName, city, province, phone, SIN, postalCode,
            martialStatus, jobName, skillCode;
    @FXML
    private DatePicker DOB, DOH, DOLP;
    @FXML
    Button cancelBtn, saveBtn;
    @FXML
    private TextField upperLimit;
    @FXML
    private TableColumn<Deduction, Double> upperLimitTable;
    @FXML
    private TextField value;
    @FXML
    private TableColumn<Earning, Double> valueTable;
    @FXML
    private TextField workingHours;
    @FXML
    private DatePicker startDate;
    @FXML
    private TableColumn<Earning, Date> startDateTable;
    @FXML
    private ComboBox<String> statementType;
    @FXML
    private TextField ratePerHour;
    @FXML
    private TableColumn<Earning, Double> ratePerHourTable;
    @FXML
    private ComboBox<String> payType;
    @FXML
    private TextField percentOfEarning;
    @FXML
    private TableColumn<Deduction, Double> percentOfEarningTable;
    @FXML
    private TableColumn<Deduction, Date> deductionStartDateTable;
    @FXML
    private TextField deductionValue;
    @FXML
    private TableColumn<Deduction, Double> deductionValueTable;
    @FXML
    private Tab deductionsDataTab;
    @FXML
    private ComboBox<String> earningSource;
    @FXML
    private TableColumn<Earning, String> earningSourceTable;
    @FXML
    private TableView<Object> earningTable;
    @FXML
    private TableView<Object> deductionTable;
    @FXML
    private Tab earningsDataTab;
    @FXML
    private DatePicker endDate;
    @FXML
    private TableColumn<Earning, Date> endDateTable;
    @FXML
    private CheckBox exempt;
    @FXML
    private DatePicker deductionEndDate;
    @FXML
    private TableColumn<Deduction, Date> deductionEndDateTable;
    @FXML
    private ComboBox<String> deductionSource;
    @FXML
    private TableColumn<Deduction, String> deductionSourceTable;
    @FXML
    private DatePicker deductionStartDate;
    @FXML
    private Button addDeduction;

    private DataStore userAccountTable;
    private UserAccount userAccount;
    private DataStore employeeTable;
    private DataStore earningTableAdapter;
    private DataStore earningSourceTableAdapter;
    private DataStore deductionTableAdapter;
    private DataStore deductionSourceTableAdapter;
    private Employee employee = null;
    private IPayrollController iPAYROLLController;

    final ObservableList<String> data = FXCollections.observableArrayList();
    private ObservableList<Object> earningData = FXCollections.observableArrayList();
    private ObservableList<String> earningSourceData = FXCollections.observableArrayList();
    private ObservableList<Object> deductionData = FXCollections.observableArrayList();
    private ObservableList<String> deductionSourceData = FXCollections.observableArrayList();

    public void setDataStore(DataStore account, DataStore profile, DataStore earning, DataStore earningSource1, DataStore deduction, DataStore deductionSource1) {
        userAccountTable = account;
        employeeTable = profile;
        earningTableAdapter = earning;
        earningSourceTableAdapter = earningSource1;
        deductionTableAdapter = deduction;
        deductionSourceTableAdapter = deductionSource1;

        try {
            List<Object> list = earningSourceTableAdapter.getAllRecords();
            List<String> names = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                names.add(((EarningSource) list.get(i)).getName());
            }
            earningSourceData.addAll(names);
            earningSource.setItems(earningSourceData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            List<Object> list = deductionSourceTableAdapter.getAllRecords();
            List<String> names = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                names.add(((DeductionSource) list.get(i)).getName());
            }
            deductionSourceData.addAll(names);
            deductionSource.setItems(deductionSourceData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        buildData();
    }

    public void setIPayrollController(IPayrollController controller) {
        iPAYROLLController = controller;
    }


    @FXML
    public void getProfile() {
        try {

            employee = (Employee) employeeTable.findOneRecord(id.getValue().toString());
            this.fullName.setText(employee.getFullName());
            this.city.setText(employee.getCity());
            this.province.setText(employee.getProvince());
            this.phone.setText(employee.getPhone());
            this.postalCode.setText(employee.getPhone());
            this.SIN.setText(employee.getSIN());
            this.martialStatus.setText(employee.getMartialStatus());
            this.jobName.setText(employee.getJobName());
            this.skillCode.setText(employee.getSkillCode());
            Date utilDOB = new Date(employee.getDOB().getTime());
            this.DOB.setValue(utilDOB.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            Date utilDOH = new Date(employee.getDOH().getTime());
            this.DOH.setValue(utilDOH.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            Date utilDOLP = new Date(employee.getDOLP().getTime());
            this.DOLP.setValue(utilDOLP.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

            this.payType.setValue(employee.getPayType());
            this.statementType.setValue(employee.getEarningStatementType());
            this.workingHours.setText(Double.toString(employee.getWorkHours()));
            this.exempt.setSelected(employee.getExempt());

            try {
                List<Object> list = earningTableAdapter.getAllRecords(id.getValue());
                for (int i = 0; i < list.size(); i++) {
                    EarningSource earningSource1 = (EarningSource) earningSourceTableAdapter.findOneRecord((Object) (((Earning) list.get(i)).getEarningSource().getCode()));
                    ((Earning) list.get(i)).setEarningSource(earningSource1);
                }
                ObservableList<Object> observableArrayList = FXCollections.observableArrayList(list);
                earningData.addAll(observableArrayList);
                earningTable.setItems(earningData);
            } catch (SQLException ex) {
                iPAYROLLController.displayAlert("ERROR: " + ex.getMessage());
            }
            try {
                List<Object> list = deductionTableAdapter.getAllRecords(id.getValue());
                for (int i = 0; i < list.size(); i++) {
                    DeductionSource deductionSource1 = (DeductionSource) deductionSourceTableAdapter.findOneRecord((Object) (((Deduction) list.get(i)).getDeductionSource().getCode()));
                    ((Deduction) list.get(i)).setDeductionSource(deductionSource1);
                }
                ObservableList<Object> observableArrayList = FXCollections.observableArrayList(list);
                deductionData.addAll(observableArrayList);
                deductionTable.setItems(deductionData);
            } catch (SQLException ex) {
                iPAYROLLController.displayAlert("ERROR: " + ex.getMessage());
            }

            userAccount = (UserAccount) userAccountTable.findOneRecord(employee.getUserAccount().getUserAccountName());

        } catch (SQLException ex) {
            iPAYROLLController.displayAlert("ERROR: " + ex.getMessage());
        }
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void update() {
        try {
            Employee oneEmployee = new Employee();
            oneEmployee.setID(employee.getID());
            oneEmployee.setFullName(this.fullName.getText());
            oneEmployee.setCity(this.city.getText());
            oneEmployee.setProvince(this.province.getText());
            oneEmployee.setPostalCode(this.postalCode.getText());
            oneEmployee.setPhone(this.phone.getText());
            oneEmployee.setSIN(this.SIN.getText());
            oneEmployee.setMartialStatus(this.martialStatus.getText());
            oneEmployee.setJobName(this.jobName.getText());
            oneEmployee.setSkillCode(this.skillCode.getText());
            oneEmployee.setDOB(java.sql.Date.valueOf(this.DOB.getValue()));
            oneEmployee.setDOH(java.sql.Date.valueOf(this.DOH.getValue()));
            oneEmployee.setDOLP(java.sql.Date.valueOf(this.DOLP.getValue()));
            oneEmployee.setWorkHours(Double.parseDouble(this.workingHours.getText()));
            oneEmployee.setPayType(this.payType.getValue());
            oneEmployee.setEarningStatementType(this.statementType.getValue());
            oneEmployee.setExempt(this.exempt.isSelected());


            oneEmployee.setUserAccount(userAccount);

            employeeTable.updateRecord(oneEmployee);

            earningTableAdapter.deleteOneRecord(id.getValue().toString());
            for (int i = 0; i < earningTable.getItems().size(); i++) {
                Earning earning = (Earning) earningTable.getItems().get(i);
                List<String> keys = earningTableAdapter.getKeys();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                earning.setStartDate(java.sql.Date.valueOf(dateFormat.format(earning.getStartDate())));
                if (keys.isEmpty()) {
                    earning.setEarningID("1");
                    Earning.setSerialNumber(1);
                    earningTableAdapter.addNewRecord(earning);
                } else {
                    int max = Integer.parseInt(keys.getLast());
                    Earning.setSerialNumber(max);

                    max++;
                    earning.setEarningID(String.valueOf(max));
                    earningTableAdapter.addNewRecord(earning);
                }
            }
            deductionTableAdapter.deleteOneRecord(id.getValue().toString());
            for (int i = 0; i < deductionTable.getItems().size(); i++) {
                Deduction deduction = (Deduction) deductionTable.getItems().get(i);
                List<String> keys = deductionTableAdapter.getKeys();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                deduction.setStartDate(java.sql.Date.valueOf(dateFormat.format(deduction.getStartDate())));
                if (keys.isEmpty()) {
                    deduction.setDeductionID("1");
                    Deduction.setSerialNumber(1);
                    deductionTableAdapter.addNewRecord(deduction);
                } else {
                    int max = Integer.parseInt(keys.getLast());
                    Deduction.setSerialNumber(max);

                    max++;
                    deduction.setDeductionID(String.valueOf(max));
                    deductionTableAdapter.addNewRecord(deduction);
                }
            }


        } catch (SQLException ex) {
            iPAYROLLController.displayAlert("ERROR: " + ex.getMessage());
        }

        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void delete() {
        // Check if there is a related user account
        if (employee.getUserAccount().getUserAccountName() == null) {
            try {
                employeeTable.deleteOneRecord(employee.getID());
            } catch (SQLException ex) {
                iPAYROLLController.displayAlert("ERROR: " + ex.getMessage());
            }
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        } else {
            iPAYROLLController.displayAlert("Please delete the associated user account first");
        }
    }

    @FXML
    void addDeduction(ActionEvent event) throws SQLException {
        DeductionSource deductionSource2 = (DeductionSource) deductionSourceTableAdapter.findOneRecord(deductionSource.getValue());
        Deduction deduction = new Deduction();
        if (deductionValue.getText().isEmpty()) {
            deduction.setAmount(0.0);
        } else {
            deduction.setAmount(Double.parseDouble(deductionValue.getText()));
        }
        if (percentOfEarning.getText().isEmpty()) {
            deduction.setPercentOfEarnings(0.0);
        } else {
            deduction.setPercentOfEarnings(Double.parseDouble(percentOfEarning.getText()));
        }
        if (upperLimit.getText().isEmpty()) {
            deduction.setUpperLimit(0.0);
        } else {
            deduction.setUpperLimit(Double.parseDouble(upperLimit.getText()));
        }
        if (deductionStartDate.getValue() == null) {
            deduction.setStartDate(null);
        } else {
            deduction.setStartDate(java.sql.Date.valueOf(deductionStartDate.getValue()));
        }
        if (deductionEndDate.getValue() == null) {
            deduction.setStopDate(null);
        } else {
            deduction.setStopDate(java.sql.Date.valueOf(deductionEndDate.getValue()));
        }
        deduction.setDeductionSource(deductionSource2);
        deduction.setEmployee(employee);

        List<Object> list = new ArrayList<>();
        list.add(deduction);

        ObservableList<Object> observableArrayList = FXCollections.observableArrayList(list);
        deductionData.addAll(observableArrayList);
        deductionTable.setItems(deductionData);

        deductionSource.getEditor().clear();
        deductionValue.clear();
        percentOfEarning.clear();
        upperLimit.clear();
        deductionStartDate.setValue(null);
        deductionEndDate.setValue(null);
    }

    @FXML
    void addEarning(ActionEvent event) throws SQLException {
        EarningSource earningSource2 = (EarningSource) earningSourceTableAdapter.findOneRecord(earningSource.getValue());
        Earning earning = new Earning();
        if (value.getText().isEmpty()) {
            earning.setAmount(0.0);
        } else {
            earning.setAmount(Double.parseDouble(value.getText()));
        }
        if (ratePerHour.getText().isEmpty()) {
            earning.setRatePerHour(0.0);
        } else {
            earning.setRatePerHour(Double.parseDouble(ratePerHour.getText()));
        }
        if (startDate.getValue() == null) {
            earning.setStartDate(null);
        } else {
            earning.setStartDate(java.sql.Date.valueOf(startDate.getValue()));
        }
        if (endDate.getValue() == null) {
            earning.setEndDate(null);
        } else {
            earning.setEndDate(java.sql.Date.valueOf(endDate.getValue()));
        }
        earning.setEarningSource(earningSource2);
        earning.setEmployee(employee);

        List<Object> list = new ArrayList<>();
        list.add(earning);

        ObservableList<Object> observableArrayList = FXCollections.observableArrayList(list);
        earningData.addAll(observableArrayList);
        earningTable.setItems(earningData);

        earningSource.getEditor().clear();
        value.clear();
        ratePerHour.clear();
        startDate.setValue(null);
        endDate.setValue(null);
    }

    @FXML
    void removeDeduction(ActionEvent event) {
        Deduction deduction = (Deduction) deductionTable.getSelectionModel().getSelectedItem();
        deductionTable.getItems().remove(deduction);

    }

    @FXML
    void removeEarning(ActionEvent event) throws SQLException {
        Earning earning = (Earning) earningTable.getSelectionModel().getSelectedItem();
        earningTable.getItems().remove(earning);
    }

    public void buildData() {
        try {
            data.addAll(employeeTable.getKeys());
        } catch (SQLException ex) {
            iPAYROLLController.displayAlert("ERROR: " + ex.getMessage());
        }
    }

    public void buildEarningData() {
        try {
            List<Object> list = earningTableAdapter.getAllRecords(id.getValue());
            for (int i = 0; i < list.size(); i++) {
                EarningSource earningSource1 = (EarningSource) earningSourceTableAdapter.findOneRecord((Object) (((Earning) list.get(i)).getEarningSource().getCode()));
                ((Earning) list.get(i)).setEarningSource(earningSource1);
            }
            ObservableList<Object> observableArrayList = FXCollections.observableArrayList(list);
            earningData.addAll(observableArrayList);
            earningTable.setItems(earningData);
        } catch (SQLException ex) {
            iPAYROLLController.displayAlert("ERROR: " + ex.getMessage());
        }
    }

    public void builDeductionSourcedData() {
        try {
            deductionSourceData.addAll(deductionSourceTableAdapter.getKeys());
        } catch (SQLException ex) {
            iPAYROLLController.displayAlert("ERROR: " + ex.getMessage());
        }
    }


    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id.setItems(data);

        ObservableList<String> payTypes = FXCollections.observableArrayList();
        payTypes.addAll("Hourly with card", "Hourly without card", "Salaried");
        payType.setItems(payTypes);

        ObservableList<String> statementTypes = FXCollections.observableArrayList();
        statementTypes.addAll("Weekly", "Bi-Weekly", "Semi-monthly", "Monthly", "Special");
        statementType.setItems(statementTypes);

        earningSourceTable.setCellValueFactory(cellData -> cellData.getValue().earningSourceProperty().get().nameProperty());
        valueTable.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        ratePerHourTable.setCellValueFactory(cellData -> cellData.getValue().ratePerHourProperty().asObject());
        startDateTable.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
        endDateTable.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());

        deductionSourceTable.setCellValueFactory(cellData -> cellData.getValue().deductionSourceProperty().get().nameProperty());
        deductionValueTable.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        percentOfEarningTable.setCellValueFactory(cellData -> cellData.getValue().percentOfEarningsProperty().asObject());
        upperLimitTable.setCellValueFactory(cellData -> cellData.getValue().upperLimitProperty().asObject());
        deductionStartDateTable.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
        deductionEndDateTable.setCellValueFactory(cellData -> cellData.getValue().stopDateProperty());

        earningSourceTable.setCellFactory(ComboBoxTableCell.forTableColumn(earningSourceData));
        valueTable.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        ratePerHourTable.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        startDateTable.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        endDateTable.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));

        deductionSourceTable.setCellFactory(ComboBoxTableCell.forTableColumn(deductionSourceData));
        deductionValueTable.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        percentOfEarningTable.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        upperLimitTable.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        deductionStartDateTable.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        deductionEndDateTable.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));

    }

}
