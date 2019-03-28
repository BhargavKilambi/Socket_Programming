// A Java program for a Server 
import java.net.*; 
import java.io.*; 
import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;

public class Server 
{ 
    private BigInteger z,n,e,d;
	//initialize socket and input stream 
	private Socket		 socket = null; 
	private ServerSocket server = null; 
    private DataInputStream in	 = null; 
    private DataOutputStream out = null;

	// constructor with port 
	public Server(int port) 
	{ 
		// starts server and waits for a connection 
		try
		{ 
			server = new ServerSocket(port); 
			System.out.println("Server started"); 

			System.out.println("Waiting for a client ..."); 

			socket = server.accept(); 
			System.out.println("Client accepted"); 

			// takes input from the client socket 
			in = new DataInputStream(socket.getInputStream()); 

            String line = "";
            line = in.readUTF();
            String[] str = line.split(",,");
            this.e = new BigInteger(str[0]);
            this.z = new BigInteger(str[1]);
            this.n = new BigInteger(str[2]);
            this.d = e.modInverse(z);
            line = "";
			// reads message from client until "Over" is sent 
			while (!line.equals("Over")) 
			{ 
				try
				{ 
                    line = in.readUTF(); 
                    if(!line.equals("Over")){
                        System.out.println("Message Recieved");
                    BigInteger b = new BigInteger(line);
                    byte[] decrypted = decrypt(b.toByteArray());

                    System.out.println("Decrypted String: " + new String(decrypted));
                    }

				} 
				catch(IOException i) 
				{ 
					// 
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
            //
            
		} 
	} 

	public static void main(String args[]) 
	{ 
		Server server = new Server(5000); 
    }

 

    // Encrypt message

    public byte[] encrypt(byte[] message)

    {

        return (new BigInteger(message)).modPow(e, n).toByteArray();

    }

 

    // Decrypt message

    public byte[] decrypt(byte[] message)

    {

        return (new BigInteger(message)).modPow(d, n).toByteArray();

    }

}

