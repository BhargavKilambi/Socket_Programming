// A Java program for a Server 
import java.net.*; 
import java.io.*; 
import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

public class Server 
{ 
    private static BigInteger p;

    private static BigInteger q;

    private static BigInteger N;

    private static BigInteger phi;

    private static BigInteger e;

    private static BigInteger d;

    private static int bitlength = 1024;

    private static Random r;
	//initialize socket and input stream 
	private Socket		 socket = null; 
	private ServerSocket server = null; 
    private DataInputStream in	 = null; 
    private DataOutputStream out = null;

	// constructor with port 
	public Server(int port) 
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
		// starts server and waits for a connection 
		try
		{ 
			server = new ServerSocket(port); 
			System.out.println("Server started"); 

			System.out.println("Waiting for a client ..."); 

			socket = server.accept(); 
			System.out.println("Client accepted"); 

			// takes input from the client socket 
			in = new DataInputStream( 
				new BufferedInputStream(socket.getInputStream())); 

			String line = ""; 

			// reads message from client until "Over" is sent 
			while (!line.equals("Over")) 
			{ 
				try
				{ 
					line = in.readUTF(); 
                    System.out.println(line); 
                    String teststring = line;
                    System.out.println("Encrypting String: " + teststring);

                    System.out.println("String in Bytes: "
            
                            + bytesToString(teststring.getBytes()));
            
                    // encrypt
            
                    byte[] encrypted = encrypt(teststring.getBytes());
                    System.out.println(encrypted);
                    // decrypt
            
                    byte[] decrypted = decrypt(encrypted);
            
                    System.out.println("Decrypting Bytes: " + bytesToString(decrypted));
                    out = new DataOutputStream(socket.getOutputStream());
                    out.writeUTF("Decrypted Bytes: " + bytesToString(decrypted)); 
                    System.out.println("Decrypted String: " + new String(decrypted));

				} 
				catch(IOException i) 
				{ 
					System.out.println(i); 
				} 
			} 
			System.out.println("Closing connection"); 

			// close connection 
			socket.close(); 
            in.close(); 
            out.close();
		} 
		catch(IOException i) 
		{ 
            System.out.println(i); 
            
		} 
	} 

	public static void main(String args[]) 
	{ 
		Server server = new Server(5000); 
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

