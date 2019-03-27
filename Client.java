import java.net.*;
import java.util.Scanner;
import java.io.*; 
  
public class Client 
{ 
    // initialize socket and input output streams 
    private Socket socket            = null; 
    //private DataInputStream  input   = null; 
    private DataOutputStream out     = null; 
    private DataInputStream in = null;
  
    // constructor to put ip address and port 
    public Client(String address, int port) 
    { 
        // establish a connection 
        try
        { 
            socket = new Socket(address, port); 
            System.out.println("Connected"); 
  
            // takes input from terminal  
  
            // sends output to the socket 
            out    = new DataOutputStream(socket.getOutputStream()); 
            in = new DataInputStream(socket.getInputStream());
        } 
        catch(UnknownHostException u) 
        { 
            System.out.println(u); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
  
        // string to read message from input 
        String line = ""; 
        Scanner sc = new Scanner(System.in);
        // keep reading until "Over" is input 
        while (!line.equals("Over")) 
        { 
            try
            { 
                
                line = sc.nextLine();
                out.writeUTF(line); 
                System.out.println(in.readUTF());
            } 
            catch(IOException i) 
            { 
                System.out.println(i); 
            } 
        } 
  
        // close the connection 
        try
        { 
            sc.close();
            out.close(); 
            socket.close(); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
    } 
  
    public static void main(String args[]) 
    { 
        Client client = new Client("127.0.0.1", 5000); 
    } 
} 