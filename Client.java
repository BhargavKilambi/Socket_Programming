import java.net.*;
import java.util.Scanner;
import java.io.*; 
import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
  
public class Client 
{ 
    private BigInteger p,q,N,phi,e,d;

    private int bitlength = 1024;

    private Random r;
    // initialize socket and input output streams 
    private Socket socket            = null; 
    private DataOutputStream out     = null; 
    public Client(String address, int port) 
    { 
        r = new Random();

        p = BigInteger.probablePrime(bitlength, r);

        q = BigInteger.probablePrime(bitlength, r);

        N = p.multiply(q);

        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        e = BigInteger.probablePrime(bitlength / 2, r);

        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0)

        {

            e.add(BigInteger.ONE);

        }

        d = e.modInverse(phi);

        try
        { 
            socket = new Socket(address, port); 
            System.out.println("Connected"); 
  
            out    = new DataOutputStream(socket.getOutputStream()); 
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
        
        try {
            out.writeUTF(e.toString() + ",," + phi.toString() + ",," + N.toString());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        // keep reading until "Over" is input 
        while (!line.equals("Over")) 
        { 
            try
            { 
                System.out.println("\n-------------\nEnter Message:");
                if(!line.equals("Over")){
                line = sc.nextLine();
                byte[] encr = encrypt(line.getBytes());
                BigInteger b = new BigInteger(encr);
                out.writeUTF(b.toString());
                
                System.out.println("Encrypted Message Sent Successfully!");
                }
                
            } 
            catch(IOException i) 
            { 
                //
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

 

    // Encrypt message

    public byte[] encrypt(byte[] message)

    {

        return (new BigInteger(message)).modPow(e, N).toByteArray();

    }

 

    // Decrypt message

    public byte[] decrypt(byte[] message)

    {

        return (new BigInteger(message)).modPow(d, N).toByteArray();

    }
} 
