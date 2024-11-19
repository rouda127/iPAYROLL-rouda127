package se2203b.ipayroll;

import javafx.beans.property.*;

import java.util.Date;

public class Earning {
    private static int serialNumber = 0;
    private StringProperty earningID;
    private DoubleProperty amount;
    private DoubleProperty ratePerHour;
    private ObjectProperty<Date> startDate;
    private ObjectProperty<Date> endDate;
    private ObjectProperty<EarningSource> earningSource;
    private ObjectProperty<Employee> employee;

    public Earning(){
        this.earningID = new SimpleStringProperty(String.valueOf(serialNumber));
        this.amount = new SimpleDoubleProperty();
        this.ratePerHour = new SimpleDoubleProperty();
        this.startDate = new SimpleObjectProperty<Date>();
        this.endDate = new SimpleObjectProperty<Date>();
        this.earningSource = new SimpleObjectProperty(new EarningSource());
        this.employee = new SimpleObjectProperty<Employee>();
    }
    public Earning(double amount, double ratePerHour, Date startDate, Date endDate,
                   EarningSource earningSource, Employee employee) {
        this.earningID = new SimpleStringProperty(String.valueOf(serialNumber));
        this.amount = new SimpleDoubleProperty(amount);
        this.ratePerHour = new SimpleDoubleProperty(ratePerHour);
        this.startDate = new SimpleObjectProperty<Date>(startDate);
        this.endDate = new SimpleObjectProperty<Date>(endDate);
        this.earningSource = new SimpleObjectProperty(earningSource);
        this.employee = new SimpleObjectProperty<Employee>(employee);
    }
    public Earning(String id, double amount, double ratePerHour, Date startDate, Date endDate,
                   EarningSource earningSource, Employee employee) {
        this.earningID = new SimpleStringProperty(id);

        this.amount = new SimpleDoubleProperty(amount);
        this.ratePerHour = new SimpleDoubleProperty(ratePerHour);
        this.startDate = new SimpleObjectProperty<Date>(startDate);
        this.endDate = new SimpleObjectProperty<Date>(endDate);
        this.earningSource = new SimpleObjectProperty(earningSource);
        this.employee = new SimpleObjectProperty<Employee>(employee);
    }

    public void setAmount(double value) {
        amount.set(value);
    }

    public void setEarningID(String id) {
        earningID.set(id);
    }

    public void setEarningSource(EarningSource source) {
        earningSource.set(source);
    }

    public void setRatePerHour(double rph) {
        ratePerHour.set(rph);
    }

    public void setEmployee(Employee emp) {
        employee.set(emp);
    }

    public void setStartDate(Date start) {
        startDate.set(start);
    }

    public void setEndDate(Date end) {
        endDate.set(end);
    }

    public ObjectProperty<Date> startDateProperty() {
        return startDate;
    }

    public ObjectProperty<Date> endDateProperty() {
        return endDate;
    }
    public Date getEndDate() {
        return endDate.get();
    }

    public Date getStartDate() {
        return startDate.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public DoubleProperty ratePerHourProperty() {
        return ratePerHour;
    }

    public ObjectProperty<EarningSource> earningSourceProperty() {
        return this.earningSource;
    }

    public ObjectProperty<Employee> employeeProperty() {
        return this.employee;
    }

    public StringProperty earningIdProperty() {
        return earningID;
    }

    public double getAmount() {
        return amount.get();
    }

    public double getRatePerHour() {
        return ratePerHour.get();
    }

    public EarningSource getEarningSource() {
        return this.earningSource.get();
    }

    public Employee getEmployee() {
        return this.employee.get();
    }

    public String getEarningID() {
        return earningID.get();
    }
    public static void setSerialNumber(int serialNumber) {
        Earning.serialNumber = serialNumber;
    }

}
