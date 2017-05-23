package com.vladimir.zubencko.task3;

import java.math.BigInteger;

public class FactorialService {

    public static void main(String[] args) {
        FactorialService factorialService = new FactorialService(); // create object
        BigInteger factorial = factorialService.calculateFactorial(100); // set number for search factorial
        BigInteger sumDigits = factorialService.calculateSumDigitsByNumber(factorial); // sum of the digits
        System.out.println(sumDigits);  //print factorial
    }

    // implement the formula n! = {if n>0 (n-1)!*n}{if n=0 n!=1}
    private BigInteger calculateFactorial(int n) {
        return n == 0 ? BigInteger.ONE : calculateFactorial(n - 1).multiply(BigInteger.valueOf(n));
    }

    // implement the formula sum = {if n>0 sum((n/10)+(n%10))}{if n=0 sum=0}
    private BigInteger calculateSumDigitsByNumber(BigInteger n) {
        return n.equals(BigInteger.ZERO) ? n :
                calculateSumDigitsByNumber(n.divide(BigInteger.TEN)).add(n.remainder(BigInteger.TEN));
    }


}
