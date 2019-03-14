import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.io.*;
import java.util.*;

public class FileClient {
	
	private Socket s;
	
	public FileClient(String host, int port, String file) {
		try {
			s = new Socket(host, port);
			sendFile(file);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void sendFile(String file) throws IOException {
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[4096];
		
		while (fis.read(buffer) > 0) {
			dos.write(buffer);
		}
		
		fis.close();
		dos.close();	
	}
	
	public static void main(String[] args) {
//        String name = "";
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Enter File Name:");
//        name = sc.next();
//Writer writer = null;
//String content = "";
//try {
//    writer = new BufferedWriter(new OutputStreamWriter(
//          new FileOutputStream(name), "utf-8"));
//    System.out.println("Enter File Contents:");
//    content = sc.next();
//    writer.write(content);
//} catch (IOException ex) {
//    // Report
//} finally {
//   try {writer.close();} catch (Exception ex) {/*ignore*/}
//}

		FileClient fc = new FileClient("localhost", 4044, "audio.mp3");
	}

}