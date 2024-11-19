/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se2203b.ipayroll;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Rzan
 */
public class AddNewEmployeeProfileController implements Initializable {
    @FXML
    private TabPane tb;
    @FXML
    private TextField id;
    @FXML
    private TextField fullName;
    @FXML
    private TextField city;
    @FXML
    private TextField province;
    @FXML
    private TextField phone;
    @FXML
    private TextField SIN;
    @FXML
    private TextField postalCode;
    @FXML
    private TextField martialStatus;
    @FXML
    private TextField jobName;
    @FXML
    private TextField skillCode;
    @FXML
    private DatePicker DOB;
    @FXML
    private DatePicker DOH;
    @FXML
    private DatePicker DOLP;
    @FXML
    Button cancelBtn;
    @FXML
    Button saveBtn;
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
    private TableView<Object> deductionTable;
    @FXML
    private TableView<Object> earningTable;
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

    private DataStore userAccountAdapter;
    private DataStore employeeTableAdapter;
    private DataStore earningTableAdapter;
    private DataStore earningSourceTableAdapter;
    private DataStore deductionTableAdapter;
    private DataStore deductionSourceTableAdapter;

    private IPayrollController iPAYROLLController;

    private Employee employee = new Employee();
    private ObservableList<Object> earningData = FXCollections.observableArrayList();
    private static ObservableList<String> earningSourceData = FXCollections.observableArrayList();
    private ObservableList<Object> deductionData = FXCollections.observableArrayList();
    private static ObservableList<String> deductionSourceData = FXCollections.observableArrayList();


    public void setDataStore(DataStore account, DataStore profile, DataStore earning, DataStore earningSource1, DataStore deduction, DataStore deductionSource1) {
        userAccountAdapter = account;
        employeeTableAdapter = profile;
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

    }

    public void setIPayrollController(IPayrollController controller) {
        iPAYROLLController = controller;
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void save() {
        try {
            UserAccount account = new UserAccount();
            employee.setID(this.id.getText());
            employee.setFullName(this.fullName.getText());
            employee.setCity(this.city.getText());
            employee.setProvince(this.province.getText());
            employee.setPostalCode(this.postalCode.getText());
            employee.setPhone(this.phone.getText());
            employee.setSIN(this.SIN.getText());
            employee.setMartialStatus(this.martialStatus.getText());
            employee.setJobName(this.jobName.getText());
            employee.setSkillCode(this.skillCode.getText());
            employee.setDOB(java.sql.Date.valueOf(this.DOB.getValue()));
            employee.setDOH(java.sql.Date.valueOf(this.DOH.getValue()));
            employee.setDOLP(java.sql.Date.valueOf(this.DOLP.getValue()));
            employee.setUserAccount(account);

            employee.setWorkHours(Double.parseDouble(this.workingHours.getText()));
            employee.setPayType(this.payType.getValue());
            employee.setEarningStatementType(this.statementType.getValue());
            employee.setExempt(this.exempt.isSelected());

            employeeTableAdapter.addNewRecord(employee);

            for (int i = 0; i < earningTable.getItems().size(); i++) {
                Earning earning = (Earning) earningTable.getItems().get(i);
                List<String> keys = earningTableAdapter.getKeys();
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

            for (int i = 0; i < deductionTable.getItems().size(); i++) {
                Deduction deduction = (Deduction) deductionTable.getItems().get(i);
                List<String> keys = deductionTableAdapter.getKeys();
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
    void addDeductionSource(ActionEvent event) throws SQLException {
        if (!deductionSource.getValue().isEmpty() && deductionSource.getValue().toString() != "") {
            DeductionSource deductionSource1 = new DeductionSource();

            List<String> codes = deductionSourceTableAdapter.getKeys();
            if (codes.isEmpty()) {
                deductionSource1.setCode("1");
                DeductionSource.setSerialNumber(1);
                deductionSource1.setName(this.deductionSource.getValue());
                deductionSourceTableAdapter.addNewRecord(deductionSource1);
            } else {
                int maxCode = Integer.parseInt(codes.getFirst());
                for (int i = 1; i < codes.size(); i++) {
                    if (Integer.parseInt(codes.get(i)) > maxCode) {
                        maxCode = Integer.parseInt(codes.get(i));
                    }
                }

                maxCode++;
                deductionSource1.setCode(String.valueOf(maxCode));
                DeductionSource.setSerialNumber(maxCode);

                deductionSource1.setName(this.deductionSource.getValue());
                deductionSourceTableAdapter.addNewRecord(deductionSource1);
            }
            List<String> list = new ArrayList<>();
            list.add(deductionSource.getValue());
            deductionSourceData.addAll(list);
            deductionSource.setItems(deductionSourceData);
        }
    }

    @FXML
    void addEarningSource(ActionEvent event) throws SQLException {
        if (!earningSource.getValue().isEmpty() && earningSource.getValue().toString() != "") {
            EarningSource earningSource1 = new EarningSource();

            List<String> codes = earningSourceTableAdapter.getKeys();
            if (codes.isEmpty()) {
                earningSource1.setCode("1");
                EarningSource.setSerialNumber(1);
                earningSource1.setName(this.earningSource.getValue());
                earningSourceTableAdapter.addNewRecord(earningSource1);
            } else {
                int maxCode = Integer.parseInt(codes.getFirst());
                for (int i = 1; i < codes.size(); i++) {
                    if (Integer.parseInt(codes.get(i)) > maxCode) {
                        maxCode = Integer.parseInt(codes.get(i));
                    }
                }

                maxCode++;
                earningSource1.setCode(String.valueOf(maxCode));
                EarningSource.setSerialNumber(maxCode);

                earningSource1.setName(this.earningSource.getValue());
                earningSourceTableAdapter.addNewRecord(earningSource1);
            }
            List<String> list = new ArrayList<>();
            list.add(earningSource.getValue());
            earningSourceData.addAll(list);
            earningSource.setItems(earningSourceData);
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

    }

}
