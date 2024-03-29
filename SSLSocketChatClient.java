
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
		String rmiName = null;
		String clientName = null;
		
		String sServer = "";
		int sPort = -1;
		
		sServer = args[0];
		sPort = Integer.parseInt(args[1]);
		rmiName = args[2];
		clientName =args[3];
		try {
			System.setProperty("javax.net.ssl.trustStore", "trustedcerts");
			System.setProperty("javax.net.ssl.trustStorePassword", "jkr124");
			
			f = (SSLSocketFactory) SSLSocketFactory.getDefault();
			c = (SSLSocket) f.createSocket(sServer, sPort);
			
			String[] supported = c.getSupportedCipherSuites();
			c.setEnabledCipherSuites(supported);
			printSocketInfo(c);
			c.startHandshake();

			System.out.println("방장의 권한으로 방을 개설했습니다.");
			
			String chatServerURL = "rmi://"+sServer+"/"+rmiName;
			ServerIF chatServer = (ServerIF)Naming.lookup(chatServerURL);
			
			Thread thread = new Thread(new Client(clientName, chatServer));
			thread.start();
			while(true) {
				TimeUnit.SECONDS.sleep(1);
				if(thread.getState() == State.TERMINATED) {
					System.exit(0);
				}
			}
		}catch(MalformedURLException mue) {
			System.out.println("MalformedURLException : "+mue);
		}catch(RemoteException re) {
			System.out.println("RemoteException : "+re);
		}catch(java.lang.ArithmeticException ae) {
			System.out.println("java.Lang.ArithmeticException "+ae);
		}catch(IOException io) {}
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