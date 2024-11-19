package se2203b.ipayroll;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DeductionSource {
    private StringProperty code;
    private StringProperty name;
    private static int serialNumber = 0;


    public DeductionSource() {
        this.code = new SimpleStringProperty(String.valueOf(serialNumber));
        this.name = new SimpleStringProperty();
    }

    public DeductionSource(String code, String name) {
        this.code = new SimpleStringProperty(code);
        this.name = new SimpleStringProperty(name);
    }

    public void setCode(String c) {
        code.set(c);
    }

    public void setName(String n) {
        name.set(n);
    }

    public String getCode() {
        return code.get();
    }

    public String getName() {
        return name.get();
    }
    public StringProperty nameProperty() {
        return name;
    }
    public StringProperty codeProperty() {
        return code;
    }

    public static void setSerialNumber(int serialNumber) {
        DeductionSource.serialNumber = serialNumber;
    }

}

