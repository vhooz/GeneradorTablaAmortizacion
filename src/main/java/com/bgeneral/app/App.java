package com.bgeneral.app;

import com.bgeneral.core.AmortizationTable;

import java.util.Scanner;

/**
 * Calculadora de amortización.
 * Programa para imprimir la tabla de amortización de un préstamo.
 * Author: Victor Orrego.
 * Date: May 4th, 2022.
 */
public class App 
{

    public static void main( String[] args )
    {
        try{
            Scanner in = new Scanner(System.in);
            int exit = 0;
            while(exit==0){
                System.out.println("Si desea calcular la tabla de amortización predeterminada presione: 'Y' y ''ENTER.");
                System.out.println("Ingrese cualquier otra tecla para calcular una tabla con valores personalizados.");
                String y = in.nextLine();
                if(y.equalsIgnoreCase("Y")){
                    System.out.println("Calculando tabla de amortización predeterminada...");
                    double loanAmount = 10000.00;
                    double monthlyFee = 293.62;
                    int years = 5;
                    int interestRate = 25;
                    String loanDisbursementDate = "10/1/14";
                    AmortizationTable table = new AmortizationTable(loanAmount,monthlyFee,years,interestRate,loanDisbursementDate);
                    table.generateAmortizationTable();

                } else {
                    System.out.println("Por favor ingresar los datos del préstamo:");
                    System.out.println("Fecha de desembolso del préstamo (M/D/YY): ");
                    String x = in.nextLine();
                    System.out.println("Monto total del préstamo: ");
                    double loanAmount = in.nextDouble();
                    System.out.println("Tasa del préstamo en números enteros: ");
                    int interestRate = in.nextInt();
                    System.out.println("Años del préstamo en números enteros: ");
                    int years = in.nextInt();
                    System.out.println("Cuota del préstamo: ");
                    double monthlyFee = in.nextDouble();

                    System.out.println("Calculando tabla de amortización personalizada... ");
                    AmortizationTable table = new AmortizationTable(loanAmount,monthlyFee,years,interestRate,x);
                    table.generateAmortizationTable();
                }
                System.out.println("¿Desea realizar otro cálculo de tabla de amortización? Y/N.");
                String z = in.nextLine();
                if(!z.equalsIgnoreCase("Y")){
                    exit = 1;
                    System.out.println("Cerrando aplicación");
                }
            }

        } catch (Exception e) {
            System.out.println("Se generó un error, por favor verifique los datos ingresados y vuelva a intentar.");
            System.out.println("Cerrando applicación.");
        }
    }
}
