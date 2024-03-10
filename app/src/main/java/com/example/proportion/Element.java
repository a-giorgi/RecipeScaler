package com.example.proportion;

public class Element {

    private String name;
    private double quantity;
    private double initialValue;

    public Element(String name, double quantity) {
        this.name = name;
        this.quantity = quantity;
        this.initialValue = quantity;
    }

    public double getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(double initialValue) {
        this.initialValue = initialValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

}
