package com.redrevoraise.esgrating;

public class CompanyFront {
    private String company_name;
    private String stock_symbol;
    private String total_grade;
    private Float total;

    public CompanyFront() {
    }

    public CompanyFront(String company_name, String stock_symbol, String total_grade, Float total) {
        this.company_name = company_name;
        this.stock_symbol = stock_symbol;
        this.total_grade = total_grade;
        this.total = total;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getStock_symbol() {
        return stock_symbol;
    }

    public void setStock_symbol(String stock_symbol) {
        this.stock_symbol = stock_symbol;
    }

    public String getTotal_grade() {
        return total_grade;
    }

    public void setTotal_grade(String total_grade) {
        this.total_grade = total_grade;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}
