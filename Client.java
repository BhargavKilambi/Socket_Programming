import java.net.*;
import java.util.Scanner;
import java.io.*; 
import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
  
public class Client 
{ 
    private static BigInteger p;

    private static BigInteger q;

    private static BigInteger N;

    private static BigInteger phi;

    private static BigInteger e;

    private static BigInteger d;

    private static int bitlength = 1024;

    private static Random r;
    // initialize socket and input output streams 
    private Socket socket            = null; 
    //private DataInputStream  input   = null; 
    private DataOutputStream out     = null; 
    private DataInputStream in = null;
  
    // constructor to put ip address and port 
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
        try {
            line = in.readUTF();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if(line.charAt(0)=='r'){
            System.out.println("Public Key received!");
            e = new BigInteger(line.substring(1));
            d = e.modInverse(phi);
            System.out.println(e+"\n");
            System.out.println(decrypt(("hi").getBytes()));
        }
        // keep reading until "Over" is input 
        while (!line.equals("Over")) 
        { 
            try
            { 
                
                String line1 = sc.nextLine();
                out.writeUTF(line1);
                int len = in.readInt();
                byte[] s1 = new byte[len];
                in.readFully(s1);
                System.out.println("Received Encrypted :");
                System.out.println("\n"+bytesToString(s1));
                byte[] decrypted = decrypt(s1);
                System.out.println("Decrypted:"+new String(decrypted));
                
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
    public static String bytesToString(byte[] encrypted)

    {

        String test = "";

        for (byte b : encrypted)

        {

            test += Byte.toString(b);

        }

        return test;

    }

 

    // Encrypt message

    public static byte[] encrypt(byte[] message)

    {

        return (new BigInteger(message)).modPow(e, N).toByteArray();

    }

 

    // Decrypt message

    public static byte[] decrypt(byte[] message)

    {

        return (new BigInteger(message)).modPow(d, N).toByteArray();

    }
} 