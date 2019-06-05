
import java.rmi.Naming; 
import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.io.*;
import java.lang.Thread.State;

import javax.net.ssl.*;

public class SSLSocketChatClient {
	
	public static void main(String[] args)throws MalformedURLException, RemoteException, NotBoundException, InterruptedException{
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintStream out = System.out;
		
		SSLSocketFactory f = null;
		SSLSocket c = null;
		
		BufferedWriter w = null;
		BufferedReader r = null;

		String sServer = "";
		int sPort = -1;
		
//		if (args.length != 2) {
//			System.out.println("Usage: Classname ServerName securePort");
//			System.exit(1);
//		}
		sServer = args[0];
		sPort = Integer.parseInt(args[1]);
		try {
			System.setProperty("javax.net.ssl.trustStore", "trustedcerts");
			System.setProperty("javax.net.ssl.trustStorePassword", "jkr124");
			
			f = (SSLSocketFactory) SSLSocketFactory.getDefault();
			c = (SSLSocket) f.createSocket(sServer, sPort);
			
			String[] supported = c.getSupportedCipherSuites();
			c.setEnabledCipherSuites(supported);
			printSocketInfo(c);
			c.startHandshake();
//			c.getPort()
			System.out.println("방장시작1");
			
			String chatServerURL = "rmi://localhost/kang";
			ServerIF chatServer = (ServerIF)Naming.lookup(chatServerURL);
//			new Thread(new ChatClient(args[0],chatServer)).start();
			
			Thread thread = new Thread(new Client(args[0], chatServer));
			thread.start();
			while(true) {
				TimeUnit.SECONDS.sleep(1);
				if(thread.getState() == State.TERMINATED) {
					System.exit(0);
				}
			}
//			w = new BufferedWriter(new OutputStreamWriter(c.getOutputStream()));
//			r = new BufferedReader(new InputStreamReader(c.getInputStream()));
//			
//			String m = null;
//			while((m=r.readLine())!=null) {
//				out.println(m);
//				m = in.readLine();
//				w.write(m,0,m.length());
//				w.newLine();
//				w.flush();
//			}
//			w.close();
//			r.close();
//			c.close();
		}catch(MalformedURLException mue) {
			System.out.println("MalformedURLException : "+mue);
		}catch(RemoteException re) {
			System.out.println("RemoteException : "+re);
		}catch(java.lang.ArithmeticException ae) {
			System.out.println("java.Lang.ArithmeticException "+ae);
		}catch(IOException io) {
			try {
				w.close();
				r.close();
				c.close();
			} catch(IOException i) {
			}
		}
	}
	
	private static void printSocketInfo(SSLSocket s) {
		System.out.println("Socket class: "+s.getClass());
		System.out.println("   Remote address = "
				+s.getInetAddress().toString());
		System.out.println("   Remote port = "+s.getPort());
		System.out.println("   Local socket address = "
				+s.getLocalSocketAddress().toString());
		System.out.println("   Local address = "
				+s.getLocalAddress().toString());
		System.out.println("   Local port = "+s.getLocalPort());
		System.out.println("   Need client authentication = "
				+s.getNeedClientAuth());
		//SSLSession ss = s.getSession();
		//System.out.println("   Cipher suite = "+ss.getCipherSuite());
		//System.out.println("   Protocol = "+ss.getProtocol());
	}
}