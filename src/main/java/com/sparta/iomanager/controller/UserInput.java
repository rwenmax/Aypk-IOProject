package com.sparta.iomanager.controller;

import com.sparta.iomanager.model.util.Logger;
import com.sparta.iomanager.model.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInput {
    private static Scanner sc = new Scanner(System.in);
    public List<Integer> userInput(){
        System.out.println("Please chose an option of insertion method. \n 1: Multi Threaded Insertion \n 2: Non Threaded Insertion");
        String num = sc.nextLine();
        List<Integer> options = new ArrayList<>();
        int num2 = 2;
        if (num == null || num.isEmpty()){
            System.out.println("Invalid Input");
            userInput();
        }
        try {
            num2 = Integer.parseInt(num);
            if (num2 == Constants.MULTI_THREADED){
                int numThread = numThreadSelection();
                options.add(num2);
                options.add(numThread);
                return options;
            }
            if (num2 == Constants.NON_THREADED){
                options.add(2);
                return options;
            }
            else
            {
                Logger.logger.warn("Invalid Input");
                System.out.println("Invalid Input!");
                userInput();
            }
        }catch (NumberFormatException e){
            Logger.logger.error(e.toString());
            Logger.logger.error("Error Occurred, Please enter value again");
            userInput();
        }
        return null;
    }


    private static int numThreadSelection(){
        System.out.println("Please enter number of threads to use!");
        if (sc.hasNextInt()){
            int numThreads = sc.nextInt();
            return numThreads;
        }
        System.out.println("Please enter valid input!");
        numThreadSelection();
        return 10;
    }

}
