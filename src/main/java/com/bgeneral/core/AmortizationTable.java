package com.bgeneral.core;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * AmortizationTable is the only public entry point to the core layer of the application.
 */
public class AmortizationTable extends Loan{

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
    private final LocalDate initialDate;
    private final double initialBalance;
    private final double interestRate;
    private final double monthlyFee;
    private final double financialFee;
    private final double downpayment;
    private final int loanMonthTerm;
    private final int loanYearsTerm;
    private final List<List<String>> rows = new ArrayList<>();
    private final List<Integer> indexes = new ArrayList<>();
    private final List<String> balance = new ArrayList<>();
    private final List<String> interest = new ArrayList<>();
    private final List<String> capital = new ArrayList<>();
    private final List<String> payment = new ArrayList<>();
    private final List<String> days = new ArrayList<>();
    private final List<String> date = new ArrayList<>();
    private final int interestRatePercentage;
    private int dividends;
    private String lastDividendDifference;

    public AmortizationTable(double loanAmount, double monthlyFee, int years, int interestRatePercentage, String loanDisbursementDate) {
        super(loanAmount,monthlyFee,years,interestRatePercentage,loanDisbursementDate);
        this.initialBalance = getLoanAmount();
        this.initialDate = getLoanDisbursementDate();
        this.interestRate = getInterestRate();
        this.interestRatePercentage = interestRatePercentage;
        this.monthlyFee = getMonthlyFee();
        this.loanMonthTerm = getMonths();
        this.loanYearsTerm = getTermByYears();
        this.financialFee = getFinancialFee();
        this.downpayment = getDownPayment();
    }

    public void generateAmortizationTable() {
        generateAmortizationData();
        rowGeneartor();
        System.out.println( "----Tabla de Amortización----");
        System.out.println(formatAsTable(rows));
    }

    private void rowGeneartor(){
        rows.add(Arrays.asList("Monto", currencyFormatOf(initialBalance), "", currencyFormatOf(downpayment), "", ""));
        rows.add(Arrays.asList("Años", String.valueOf(loanYearsTerm), "Meses", String.valueOf(loanMonthTerm), "", ""));
        rows.add(Arrays.asList("Tasa", interestRatePercentage +"%", "", "Financiera", "", "Diferencia ultimo dividendo"));
        rows.add(Arrays.asList("Cuota", currencyFormatOf(monthlyFee), "", currencyFormatOf(financialFee), "", lastDividendDifference));
        rows.add(Arrays.asList("", "", "", "", "", ""));
        rows.add(Arrays.asList("Dividendos tabla", String.valueOf(dividends), "", "", "", ""));
        rows.add(Arrays.asList("Fecha", "Saldo", "Días", "Intereses", "Abono Capital", "Pago"));
        rows.add(Arrays.asList(formatter.format(this.initialDate), currencyFormatOf(initialBalance), "", "", "", ""));

        for (int i : indexes)
        {
            rows.add(Arrays.asList(date.get(i), balance.get(i), days.get(i),
                    interest.get(i), capital.get(i), payment.get(i) ));
        }
    }

    private void generateAmortizationData() {
        LocalDate prevDate = this.initialDate;
        double currentBalance = this.initialBalance;
        double currentCapital = 0;
        double currentInterest;
        double currentPayment;
        int i=0;

        while(currentBalance!=0){
            double difference = currentBalance-currentCapital;
            long currentPeriodDays;

            LocalDate currentDate = prevDate.plusMonths(1);
            currentPeriodDays = prevDate.until(currentDate, ChronoUnit.DAYS);
            prevDate = currentDate;

            if (difference > 0){
                currentBalance = difference;
                currentInterest = getCurrentInterest(currentBalance,currentPeriodDays);
                currentCapital = getCurrentCapital(currentBalance,currentInterest);
                currentPayment = currentInterest+currentCapital;

            } else {
                currentBalance = 0;
                currentInterest = 0;
                currentCapital = 0;
                currentPayment = 0;
            }

            balance.add(currencyFormatOf(currentBalance));
            date.add(formatter.format(currentDate));
            days.add(String.valueOf(currentPeriodDays));
            interest.add(currencyFormatOf(currentInterest));
            capital.add(currencyFormatOf(currentCapital));
            payment.add(currencyFormatOf(currentPayment));
            indexes.add(i);
            if(currentBalance>0){
                this.lastDividendDifference = currencyFormatOf(this.monthlyFee-currentPayment);
                this.dividends++;
            }
            i++;
        }
    }

    private double getCurrentCapital(double currentBalance, double currentInterest) {
        double monthlyFeeCurrentInterestDifference = this.monthlyFee-currentInterest;
        if( monthlyFeeCurrentInterestDifference < currentBalance){
            if(monthlyFeeCurrentInterestDifference > 0){
                return monthlyFeeCurrentInterestDifference;
            } else return 0;
        } else return currentBalance;
    }

    private double getCurrentInterest(double currentBalance, long currentPeriodDays) {
        double v = roundDecimals(currentBalance * this.interestRate / 365);
        return v*currentPeriodDays;
    }

    private double roundDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private String currencyFormatOf(double value) {
        double roundedValue = roundDecimals(value);
        Locale usa = new Locale("en", "US");
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);
        return dollarFormat.format(roundedValue);
    }

    private static String formatAsTable(List<List<String>> rows)
    {
        int[] maxLengths = new int[rows.get(0).size()];
        for (List<String> row : rows)
        {
            for (int i = 0; i < row.size(); i++)
            {
                maxLengths[i] = Math.max(maxLengths[i], row.get(i).length());
            }
        }

        StringBuilder formatBuilder = new StringBuilder();
        for (int maxLength : maxLengths)
        {
            formatBuilder.append("%-").append(maxLength + 2).append("s");
        }
        String format = formatBuilder.toString();

        StringBuilder result = new StringBuilder();
        for (List<String> row : rows)
        {
            result.append(String.format(format, (Object[]) row.toArray(new String[0]))).append("\n");
        }
        return result.toString();
    }
}