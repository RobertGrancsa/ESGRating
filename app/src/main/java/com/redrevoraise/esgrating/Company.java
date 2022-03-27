package com.redrevoraise.esgrating;

import java.util.ArrayList;

public class Company {
    private String company_name;
    private String environment_grade;
    private Integer environment_score;
    private String governance_grade;
    private Integer governance_score;
    private String social_grade;
    private Integer social_score;
    private String stock_symbol;
    private Integer total;
    private String total_grade;
    private String industry;
    private String sector;

    private ArrayList<Integer> ESG_score_peers;
    private ArrayList<Integer> E_score_peers;
    private ArrayList<Integer> S_score_peers;
    private ArrayList<Integer> G_score_peers;

    private ArrayList<Integer> ESG_score_predictions;
    private ArrayList<Integer> E_score_predictions;
    private ArrayList<Integer> S_score_predictions;
    private ArrayList<Integer> G_score_predictions;

    public Company() {
    }

    public Company(String company_name, String environment_grade, Integer environment_score, String governance_grade, Integer governance_score, String social_grade, Integer social_score, String stock_symbol, Integer total, String total_grade, ArrayList<Integer> ESG_score_peers, ArrayList<Integer> e_score_peers, ArrayList<Integer> s_score_peers, ArrayList<Integer> g_score_peers, String industry, String sector, ArrayList<Integer> ESG_score_predictions, ArrayList<Integer> E_score_predictions, ArrayList<Integer> S_score_predictions, ArrayList<Integer> G_score_predictions) {
        this.company_name = company_name;
        this.environment_grade = environment_grade;
        this.environment_score = environment_score;
        this.governance_grade = governance_grade;
        this.governance_score = governance_score;
        this.social_grade = social_grade;
        this.social_score = social_score;
        this.stock_symbol = stock_symbol;
        this.total = total;
        this.total_grade = total_grade;
        this.ESG_score_peers = ESG_score_peers;
        this.E_score_peers = e_score_peers;
        this.S_score_peers = s_score_peers;
        this.G_score_peers = g_score_peers;
        this.industry = industry;
        this.sector = sector;
        this.ESG_score_predictions = ESG_score_predictions;
        this.E_score_predictions = E_score_predictions;
        this.S_score_predictions = S_score_predictions;
        this.G_score_predictions = G_score_predictions;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getEnvironment_grade() {
        return environment_grade;
    }

    public void setEnvironment_grade(String environment_grade) {
        this.environment_grade = environment_grade;
    }

    public Integer getEnvironment_score() {
        return environment_score;
    }

    public void setEnvironment_score(Integer environment_score) {
        this.environment_score = environment_score;
    }

    public String getGovernance_grade() {
        return governance_grade;
    }

    public void setGovernance_grade(String governance_grade) {
        this.governance_grade = governance_grade;
    }

    public Integer getGovernance_score() {
        return governance_score;
    }

    public void setGovernance_score(Integer governance_score) {
        this.governance_score = governance_score;
    }

    public String getSocial_grade() {
        return social_grade;
    }

    public void setSocial_grade(String social_grade) {
        this.social_grade = social_grade;
    }

    public Integer getSocial_score() {
        return social_score;
    }

    public void setSocial_score(Integer social_score) {
        this.social_score = social_score;
    }

    public String getStock_symbol() {
        return stock_symbol;
    }

    public void setStock_symbol(String stock_symbol) {
        this.stock_symbol = stock_symbol;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getTotal_grade() {
        return total_grade;
    }

    public void setTotal_grade(String total_grade) {
        this.total_grade = total_grade;
    }

    public ArrayList<Integer> getESG_score_peers() {
        return ESG_score_peers;
    }

    public void setESG_score_peers(ArrayList<Integer> ESG_score_peers) {
        this.ESG_score_peers = ESG_score_peers;
    }

    public ArrayList<Integer> getE_score_peers() {
        return E_score_peers;
    }

    public void setE_score_peers(ArrayList<Integer> e_score_peers) {
        E_score_peers = e_score_peers;
    }

    public ArrayList<Integer> getS_score_peers() {
        return S_score_peers;
    }

    public void setS_score_peers(ArrayList<Integer> s_score_peers) {
        S_score_peers = s_score_peers;
    }

    public ArrayList<Integer> getG_score_peers() {
        return G_score_peers;
    }

    public void setG_score_peers(ArrayList<Integer> g_score_peers) {
        G_score_peers = g_score_peers;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public ArrayList<Integer> getESG_score_predictions() {
        return ESG_score_predictions;
    }

    public void setESG_score_predictions(ArrayList<Integer> ESG_score_predictions) {
        this.ESG_score_predictions = ESG_score_predictions;
    }

    public ArrayList<Integer> getE_score_predictions() {
        return E_score_predictions;
    }

    public void setE_score_predictions(ArrayList<Integer> e_score_predictions) {
        E_score_predictions = e_score_predictions;
    }

    public ArrayList<Integer> getS_score_predictions() {
        return S_score_predictions;
    }

    public void setS_score_predictions(ArrayList<Integer> s_score_predictions) {
        S_score_predictions = s_score_predictions;
    }

    public ArrayList<Integer> getG_score_predictions() {
        return G_score_predictions;
    }

    public void setG_score_predictions(ArrayList<Integer> g_score_predictions) {
        G_score_predictions = g_score_predictions;
    }
}
