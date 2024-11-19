package se2203b.ipayroll;

import javafx.beans.property.*;

import java.util.Date;

public class Deduction {

    private StringProperty deductionID;
    private DoubleProperty amount;
    private DoubleProperty percentOfEarnings;
    private ObjectProperty<Date> startDate;
    private ObjectProperty<Date> stopDate;
    private DoubleProperty upperLimit;
    private ObjectProperty<DeductionSource> deductionSource;
    private ObjectProperty<Employee> employee;
    private static int serialNumber = 0;


    public Deduction(){
        this.deductionID = new SimpleStringProperty(String.valueOf(serialNumber));
        this.amount = new SimpleDoubleProperty();
        this.percentOfEarnings = new SimpleDoubleProperty();
        this.upperLimit = new SimpleDoubleProperty();
        this.startDate = new SimpleObjectProperty<Date>();
        this.stopDate = new SimpleObjectProperty<Date>();
        this.deductionSource = new SimpleObjectProperty();
        this.employee = new SimpleObjectProperty<Employee>();}
    public Deduction(double amount, double percentOfEarnings, Date startDate, Date stopDate, double upperLimit,
                   DeductionSource deductionSource, Employee employee) {
        this.deductionID = new SimpleStringProperty(String.valueOf(serialNumber));
        this.amount = new SimpleDoubleProperty(amount);
        this.percentOfEarnings = new SimpleDoubleProperty(percentOfEarnings);
        this.upperLimit = new SimpleDoubleProperty(upperLimit);
        this.startDate = new SimpleObjectProperty<Date>(startDate);
        this.stopDate = new SimpleObjectProperty<Date>(stopDate);
        this.deductionSource = new SimpleObjectProperty(deductionSource);
        this.employee = new SimpleObjectProperty<Employee>(employee);
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public void setDeductionID(String deductionID) {
        this.deductionID.set(deductionID);
    }

    public void setDeductionSource(DeductionSource deductionSource) {
        this.deductionSource.set(deductionSource);
    }

    public void setPercentOfEarnings(double percentOfEarnings) {
        this.percentOfEarnings.set(percentOfEarnings);
    }
    public void setUpperLimit(double upperLimit) {
        this.upperLimit.set(upperLimit);
    }


    public void setEmployee(Employee employee) {
        this.employee.set(employee);
    }

    public void setStartDate(Date startDate) {
        this.startDate.set(startDate);
    }

    public void setStopDate(Date stopDate) {
        this.stopDate.set(stopDate);
    }

    public ObjectProperty<Date> startDateProperty() {
        return startDate;
    }

    public ObjectProperty<Date> stopDateProperty() {
        return stopDate;
    }
    public Date getStopDate() {
        return stopDate.get();
    }

    public Date getStartDate() {
        return startDate.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public DoubleProperty percentOfEarningsProperty() {
        return percentOfEarnings;
    }
    public DoubleProperty upperLimitProperty() {
        return upperLimit;
    }


    public ObjectProperty<DeductionSource> deductionSourceProperty() {
        return deductionSource;
    }

    public ObjectProperty<Employee> employeeProperty() {
        return employee;
    }

    public StringProperty DeductionIDProperty() {
        return deductionID;
    }

    public double getAmount() {
        return amount.get();
    }

    public double getPercentOfEarnings() {
        return percentOfEarnings.get();
    }
    public double getUpperLimit() {
        return upperLimit.get();
    }

    public DeductionSource getDeductionSource() {
        return deductionSource.get();
    }

    public Employee getEmployee() {
        return employee.get();
    }

    public String getDeductionID() {
        return deductionID.get();
    }

    public static void setSerialNumber(int serialNumber) {
        Deduction.serialNumber = serialNumber;
    }

}
