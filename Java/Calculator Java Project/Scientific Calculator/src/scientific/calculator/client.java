package scientific.calculator;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class client {
    private Socket Socket;
    private BufferedReader br;
    private PrintWriter pw;
 
    
    //request 
    public void  ConencttoServer( String ip , int PortNum) throws IOException{
        
        this.Socket=  new Socket(ip , PortNum); 
    }    
    //read
    public String ReadFromServer () throws IOException{
         br = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
        return br.readLine();
    }
    
    //wrtie 
    public void WriteToServer (String msg) throws IOException {
      pw = new PrintWriter(Socket.getOutputStream());
      pw.println(msg);
      pw.flush();
    }    
    public void WriteIntToServer (int  msg) throws IOException {
      pw = new PrintWriter(Socket.getOutputStream());
      pw.println(msg);
      pw.flush();
    }
    public void CloseConenction () throws IOException{
        this.Socket.close();
    }
    
    public String getUserInput(){
        Scanner sc =  new Scanner (System.in);
        return sc.nextLine();        
    }
    
    public int getNumfromUser(){
        Scanner sc =  new Scanner (System.in);
        return sc.nextInt();        
    }
    public void log (String msg){
        System.out.println(msg);
    }
    
    public static void main (String argv[]) throws IOException{
        client clinet=  new client ();
        clinet.ConencttoServer("127.0.0.1", 9090);
        String fromServer = " ";
        //String toServer = " ";
        String opCode = " ";
        int num1 = 0;
        int num2 = 0;
       
       try {
        clinet.log("plase  enter some thing ");
           while (true) { 
              clinet.log("Enter opeerationCode : ");
              opCode=  clinet.getUserInput();
            
              clinet.log("Enter Num 1  : ");
              num1=  clinet.getNumfromUser();
      
              clinet.log("Enter Num 2  : ");
              num2=  clinet.getNumfromUser();
          
              //write to server
              clinet.WriteToServer(opCode);
              clinet.WriteIntToServer(num1);
              clinet.WriteIntToServer(num2);
             
              
              //read
              fromServer = clinet.ReadFromServer();
              
              //print 
              clinet.log(fromServer);
           
           }
        } finally {
             clinet.CloseConenction();
        }
        
    
    
 }
}
