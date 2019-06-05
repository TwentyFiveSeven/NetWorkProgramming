

import java.lang.Thread.State;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;

public class Clientrunner {

	public static void main(String[] args) {
		try {
		String ServerURL = "rmi://"+args[0]+"/"+args[1];
		ServerIF server = (ServerIF) Naming.lookup(ServerURL);
			Thread thread = new Thread(new Client(args[2], server));
			thread.start();
			while(true) {
			TimeUnit.SECONDS.sleep(1);
			if(thread.getState() == State.TERMINATED) {
	//			System.out.println("aaaa");
				System.exit(0);
			}
	//			thread.stop();
			}
		}catch(Exception e) {
			System.out.println("방장권한이 없습니다.");
			System.exit(0);
		}
	}

}
