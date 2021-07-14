/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scientific.calculator;

import ScientificCalculator.CalculorThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {
     public static void main(String argv[]) throws IOException{
         
         ServerSocket  listner = new ServerSocket (9090);
         int clientnum = 1 ;
      try {
          while (true) {                     
              Serverthread thread= new Serverthread(listner.accept(),clientnum++);
              thread.start();
          }
      }finally {
          
      } 
    }
}


class Serverthread extends Thread{
    private Socket socket;
    private int clinetNum;
    
    //Constructor
     public  Serverthread(Socket socket, int clientNum){
         this.socket =socket;
         this.clinetNum=clientNum;
     }
     
    //run 
     public void run(){
         try{
        //initialize Readr and Writer 
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        
       while (true) {
          String opCode = "";
          String reply = " ";
          double  res , num1 ,num2;
          opCode = br.readLine();
          num1 =Double.parseDouble(br.readLine());
          num2 =Double.parseDouble(br.readLine());
        
       CalculorThreaded helper =  new CalculorThreaded();
    
          res = helper.doOperation(opCode, num1, num2);
          if (res == -1 ) {
              reply = "NotValid";
          }else {
              reply = Double.toString(res);
          }        
          pw.println(reply);
          pw.flush();
          
       }
        
         }catch(IOException e) {
            System.out.println(e.toString());
         }finally{
             try {
                 socket.close();
             } catch (IOException ex) {
                 Logger.getLogger(Serverthread.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
     }
}
