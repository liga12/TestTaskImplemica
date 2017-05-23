package com.vladimir.zubencko.task1;

import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BracketService {
    public static void main(String[] args) {
        int number = 0;
        try {
            BracketService bracketService = new BracketService();                   //create object
            number = bracketService.getNumberFromKeyboard();                        // number from keyboard
            BigInteger properBrackets = bracketService.calculateValidBrackets(number);
            System.out.println("Proper brackets = "
                    + properBrackets);                                    //print the number of correct options brackets
        } catch (InputMismatchException e) {
            System.out.println("Illegal value: value be must number");
        }catch ( IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        catch (StackOverflowError e) {
            System.out.println("Value = " + number + " too big to calculate");
        } finally {
            System.exit(0);                                                  //close program
        }
    }

    private int getNumberFromKeyboard() {
        System.out.println("Enter number in the range [1,7440]: ");
        Scanner keyboard = new Scanner(System.in); //getting data from the keyboard
        int number = keyboard.nextInt();
        if (number > 7440 || number < 1) { //check range
            //generate IllegalArgumentException with message
            throw new IllegalArgumentException
                    ("Illegal value: value must number in the range [1,7440]: number = " +number);
        }
        return number;
    }

    // implement the formula brackets = {n!/2*n!*(n!+1)}
    private BigInteger calculateValidBrackets(int number) {
        if (number < 1) {
            throw new IllegalArgumentException
                    ("Illegal value: value must number  in the range [1,7440]: number = " + number);
        }
        BigInteger secondArgument = BigInteger.valueOf(number);                                // n!
        BigInteger firstArgument = BigInteger.valueOf(2).multiply(secondArgument);             // 2*n!
        BigInteger thirdArgument = secondArgument.add(BigInteger.ONE);                         // n!+1
        return calculateFactorial(firstArgument).divide(calculateFactorial(secondArgument).
                multiply(calculateFactorial(thirdArgument)));
    }

    // implement the formula n! = {if n>0 (n-1)!*n}{if n=0 n!=1}
    private BigInteger calculateFactorial(BigInteger n) {
        return n.equals(BigInteger.ZERO) ? BigInteger.ONE : calculateFactorial(n.subtract(BigInteger.ONE)).multiply(n);
    }
}