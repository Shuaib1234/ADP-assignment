/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.assignment3project;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import static java.lang.String.format;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * @author Shuaib Allie 217148867
 */
public class ReadSerFile {

ObjectInputStream read;
FileWriter writ1,writ2;

BufferedWriter buffWritCus,buffWritSup;
ArrayList<Customer> customer= new ArrayList<>();
ArrayList<Supplier> supplier= new ArrayList<>();


//2a is below
    public void openingOfTheFile(){
        try{
            read = new ObjectInputStream( new FileInputStream( "stakeholder.ser" ) ); 
            System.out.println("*** ser file created and opened for reading  ***");               
        }
        catch (IOException ioe){
            System.out.println("error opening ser file: " + ioe.getMessage());
            System.exit(1);
        }
    }
    public void readingOfTheFile(){
        try{
           while(true){
               Object object = read.readObject();
               String cust ="Customer";
               String sup = "Supplier";
               String name = object.getClass().getSimpleName();
               if ( name.equals(cust)){
                   customer.add((Customer)object);
               } else if ( name.equals(sup)){
                   supplier.add((Supplier)object);
               } else {
                   System.out.println("It didn't work");
               }
           } 
        }
        catch (EOFException eofe) {
            System.out.println("End of file reached");
        }
        catch (ClassNotFoundException ioe) {
            System.out.println("Class error reading ser file: "+ ioe);
        }
        catch (IOException ioe) {
            System.out.println("Error reading ser file: "+ ioe);
        }
        
        sortMethod();
        
    }
    public void closingOfTheFile(){
        try{
            read.close( ); 
        }
        catch (IOException ioe){            
            System.out.println("error closing ser file: " + ioe.getMessage());
            System.exit(1);
        }
    }
    
    //3a and 2b combined below
 
    public void sortMethod(){
        String[] sortingCustomerNames = new String[customer.size()];
        String[] sortingSupplierNames= new String[supplier.size()];
        ArrayList<Customer> sortingCustomer= new ArrayList<>();
        ArrayList<Supplier> sortingSupplier= new ArrayList<>();
        int countCus = customer.size();
        int countSup = supplier.size();
        for (int i = 0; i < countCus; i++) {
            sortingCustomerNames[i] = customer.get(i).getStHolderId();
        }
        Arrays.sort(sortingCustomerNames);
        
        for (int i = 0; i < countCus; i++) {
            for (int j = 0; j < countCus; j++) {
                if (sortingCustomerNames[i].equals(customer.get(j).getStHolderId())){
                    sortingCustomer.add(customer.get(j));
                }
            }
        }
       
        customer = sortingCustomer;
        
        for(int i= 0; i<countSup;i++){
            sortingSupplierNames[i]= supplier.get(i).getName();
        }
        Arrays.sort(sortingSupplierNames);
        
        for (int i=0; i<countSup;i++){
            for (int j=0; j< countSup;j++){
                if(sortingSupplierNames[i].equals(supplier.get(j).getName())){
                    sortingSupplier.add(supplier.get(j));
                }
            }
        }
       supplier= sortingSupplier;
    }
    
  
    //2e is shown below
    public void showCustomers() throws ParseException{
        try{
            writ1 = new FileWriter("customerOutFile.txt");
          
            buffWritCus = new BufferedWriter(writ1);
            
            buffWritCus.write(String.format("%-10s \n", "=================== CUSTOMERS ========================"));
            
            buffWritCus.write(String.format("%-4s %-10s %-12s %-13s %-13s\n", "ID","Name","Surname","Date of Birth",  "             Age"));
             
            buffWritCus.write(String.format("%-10s \n", "======================================================"));
            
            for (int i = 0; i < customer.size(); i++) {
                Date date = new Date();
                SimpleDateFormat day = new SimpleDateFormat("dd-MM-yyyy");
                
              
                
               
                
                
                
                String days = customer.get(i).getDateOfBirth();
                date = (Date)day.parse(days);
               buffWritCus.write(String.format("%-5s %-10s %-12s %-15s %-15s \n", customer.get(i).getStHolderId(), customer.get(i).getFirstName(), customer.get(i).getSurName(),date,2021-Integer.valueOf((customer.get(i).getDateOfBirth().substring(0,4)))));
               
            }
            
           
            int trueCounter=0;
             int falseCounter=0;
             Boolean canPayTheRent=true;
             Boolean cannotPayTheRent=false;
   for(int i=0;i<customer.size();i++)
            if(customer.get(i).getCanRent()==canPayTheRent)
            {
                trueCounter=trueCounter +1; 
                
                
            }buffWritCus.write(String.format("%-15s %-15s \n", "\nNumber of customers who can rent:     ",  trueCounter));
            for(int i=0;i<customer.size();i++)
            if(customer.get(i).getCanRent()==cannotPayTheRent)
            {
                falseCounter=falseCounter +1; 
                
                
            }buffWritCus.write(String.format("%-15s %-15s \n", "Number of customers who cannot rent:  ",falseCounter));
          
        }
        catch(IOException yoyo )
        {
            System.out.println(yoyo);
            System.exit(1);
        }
        try{
            buffWritCus.close( ); 
        }
        catch (IOException ioe){            
            System.out.println("error closing text file: " + ioe.getMessage());
            System.exit(1);
        }
    }
    
    //3b is shown below
    public void showSuppliers(){
        try{
            
            writ2= new FileWriter("supplierOutFile.txt");
           
            buffWritSup = new BufferedWriter(writ2);
           
            buffWritSup.write(String.format("%-10s \n", "========================== SUPPLIERS ============================="));
            
            buffWritSup.write(String.format("%-5s %-20s %-20s %-17s\n", "ID","Product Name","Product Type","Product Description"));
            
            buffWritSup.write(String.format("%-12s \n", "=================================================================="));
            
            for (int i = 0; i < supplier.size(); i++) {
                buffWritSup.write(String.format("%-5s %-20s %-15s %-15s \n", supplier.get(i).getStHolderId(), supplier.get(i).getName(), supplier.get(i).getProductType(),supplier.get(i).getProductDescription()));
                
            }
          
        }
        catch(IOException yoyo )
        {
            System.out.println(yoyo);
            System.exit(1);
        }
        try{
            buffWritSup.close( ); 
        }
        catch (IOException ioe){            
            System.out.println("Error in the closing of text file: " + ioe.getMessage());
            System.exit(1);
        }
    }
 
    
      
    
public static void main(String args[]) throws ParseException  {
ReadSerFile obj=new ReadSerFile(); 
obj.openingOfTheFile();
obj.readingOfTheFile();
obj.closingOfTheFile();
obj.showCustomers();
obj.showSuppliers();

     }    
     
}
//end of the coding
