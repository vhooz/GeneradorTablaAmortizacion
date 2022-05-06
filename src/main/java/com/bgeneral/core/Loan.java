package com.bgeneral.core;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Loan class is only accesible by classes inside the core layer(package).
 */
class Loan {

    private final double loanAmount;
    private final double downPayment;
    private final double interestRate;
    private final double financialFee;
    private final double monthlyFee;
    private final int termByYears;
    private final int termByMonths;
    private final LocalDate loanDisbursementDate;

    protected Loan(double loanAmount, double monthlyFee, int termByYears, double interestRatePercentage, String loanDisbursementDateStr) {
        this.loanAmount = loanAmount;
        this.monthlyFee = monthlyFee;
        this.termByYears = termByYears;
        this.interestRate = setInterestRate(interestRatePercentage);
        this.downPayment = setDownPayment(interestRate,loanAmount);
        this.termByMonths = setTermByMonths(termByYears);
        this.financialFee = setFinancialFee();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
        this.loanDisbursementDate = LocalDate.parse(loanDisbursementDateStr, formatter);
    }

    private double setInterestRate(double interestRate) {
        return interestRate/100;
    }

    private double setDownPayment(double interest, double loanAmount) {
        return interest*loanAmount;
    }

    private int setTermByMonths(int years) {
        return 12*years;
    }

    /**
     * EXCEL ECUATION =ROUND((Tasa/12)/(1-POWER(1+Tasa/12, -Anios*12))*Monto,2)
     * @return double
     */
    private double setFinancialFee() {
        double x = this.interestRate/12;
        double base = 1 + x;
        double exponent = -(double) this.termByYears *12;
        double pow = Math.pow(base,exponent);
        double y = 1 - pow;
        double output = (x/y)* this.loanAmount;
        return Math.round( output * 100.0 ) / 100.0;
    }

    protected int getMonths() {
        return this.termByMonths;
    }

    protected double getDownPayment() {
        return this.downPayment;
    }

    protected double getFinancialFee() {
        return this.financialFee;
    }

    protected double getLoanAmount() {
        return this.loanAmount;
    }

    protected double getInterestRate() {
        return this.interestRate;
    }

    protected double getMonthlyFee() {
        return this.monthlyFee;
    }
    protected int getTermByYears() {
        return termByYears;
    }

    protected LocalDate getLoanDisbursementDate() {
        return loanDisbursementDate;
    }

}

