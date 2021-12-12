package com.sparta.iomanager.controller;

import com.sparta.iomanager.model.util.Logger;
import com.sparta.iomanager.model.util.Constants;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserInput {
    private static Scanner sc = new Scanner(System.in);
    String num;
    int num2 = 0;
      //List<Integer> options = new ArrayList<>();
    public List<Integer> userInput(){
        System.out.println("Please chose an option of insertion method. \n 1: Multi Threaded Insertion \n 2: Non Threaded Insertion");
         num = sc.nextLine();
        List<Integer> options = new ArrayList<>();
        if (num == null || num.isEmpty()){
            System.out.println("Invalid Input");
            userInput();
        }
        try {
            num2 = Integer.parseInt(num);
        }catch (NumberFormatException e){
            Logger.logger.error(e.toString());
            Logger.logger.error("Error Occurred, Please enter value again");
            userInput();
        }
        if (num2 == Constants.MULTI_THREADED){
                options.add(num2);
                int numThread = numThreadSelection();
                options.add(numThread);
        }
        else if (num2 == Constants.NON_THREADED){
                options.add(2);
        }
        else
            {
                Logger.logger.warn("Invalid Input");
                System.out.println("Invalid Input!");
                userInput();
            }
        return options;
    }


    private static int numThreadSelection(){
        int numThread = 0;
        while(true){
            System.out.println("Please enter number of threads to use!");

            try{
                numThread  = sc.nextInt();
                break;
            }catch (InputMismatchException e){
                System.out.println("Enter a valid input");
                sc.next();
                }
            }
        return numThread;
    }

}
