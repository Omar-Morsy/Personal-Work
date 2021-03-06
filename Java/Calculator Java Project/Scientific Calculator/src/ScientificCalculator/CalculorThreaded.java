/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScientificCalculator;

import java.io.IOException;

public class CalculorThreaded {
	
	public CalculorThreaded() {
		
	}


public double doOperation (String opCode , double num1 , double num2) throws IOException {
        switch (opCode) {
            case "+":
                return num1 + num2 ;
           
            case "-":
                return num1 - num2;
            
            case "*":
                return num1 * num2;
            
            case "/":
                return num1 / num2;
            
            case "x^3":
            	return num1 * num1 * num1;
            
            case "x!" :
            	if (num1 == 0) {
            		return 0;
            	}
            	num2 = 1;
            	if (num1 < 0) {
            		num1 = -num1;
            	}
              for (int i = 1; i <= num1; i++) {
            	  num2 *=i;
              }
              if (num1 < 0) {
            	  return -num2;
                } else {
                return num2;
               }
            case "sqrt":
            	if (num1 < 0) {
            		throw new IOException("Error Invalid Calc.");
            	}
                return Math.sqrt(num1);
               
            case "sin":   
            	return Math.sin(num1);
            	
            case "cos":
            	return Math.cos(num1);
            
            case "tan":
               return Math.tan(num1);
             
            case "ln":
            	if (num1 < 0) {
            		throw new IOException("Error Invalid Calc.");
            	}
            	return Math.log(num1) + Math.log(2.71828);
            
            case "log":
            	if (num1 < 0) {
            		throw new IOException("Error Invalid Calc.");
            	}
            	return Math.log(num1);
            	
            case "1/x":
            	if (num1 == 0) {
            		throw new IOException("Error Invalid Calc.");
            	}
            	return 1 / num1;
            	
            case "e^x":
            	return Math.exp(num1);
            	
            case "x^2":
            	return Math.pow(num1, 2);
            	
            case "x^y":
            	return Math.pow(num1, num2);
            	
            case "|x|":	
            	return Math.abs(num1);
           
            default :
            	throw new IOException("Error Invalid Calc.");     
        }
    }
    
    
}
