


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server extends UnicastRemoteObject implements ServerIF{
	private static final long serialVersionUID = 1L;
	private ArrayList<ClientIF> clientlist;
	private ArrayList<ClientIF> clientlist2;
	String word="���";
	ClientIF fail=null;
	int start=0;
	int check=0;

	String last_turn=null;
	protected Server() throws RemoteException {
		clientlist = new ArrayList<ClientIF>();
		clientlist2 = new ArrayList<ClientIF>();
		
	}

	
	public void registerClient(ClientIF client, String name) throws RemoteException {
		this.clientlist.add(client);
		if(clientlist.size() == 1)
			last_turn = name;
		
//		for(int i=0;i<clientlist.size();i++) {
//			
//		}
		System.out.print("\n���� ������ ��� : ");
		for(int i=0;i<clientlist.size();i++) {
			System.out.print(clientlist.get(i).getname()+"  ");
			clientlist.get(i).recive(clientlist.get(clientlist.size()-1).getname()+"��(��) �����Ͽ����ϴ�.");
		
		}
		
				
		
	}
	
	public void removeClient(ClientIF client) throws RemoteException{
		      if(last_turn.equals(client.getname())) {
		         for(int i=0;i<clientlist.size();i++) {
		            if(clientlist.get(i).getname().equals(client.getname())) {
		               if(i==clientlist.size()-1)
		                  last_turn = clientlist.get(0).getname();
		               else
		                  last_turn = clientlist.get(i+1).getname();
		            }
		         }
		      }
		      this.clientlist.remove(client);
	}
	
	public String checkword() throws RemoteException{
		return word;
	}

	
	public void sendAll(String word, String turn) throws RemoteException {
//		if(!last_turn.equals(null)) {
//			last_turn = turn;
//		}
		System.out.print("\n���� ������ ��� : ");
		for(int i=0;i<clientlist.size();i++) {
			System.out.print(i+" "+clientlist.get(i).getname()+"  ");
//			clientlist.get(i).recive(clientlist.get(clientlist.size()-1).getname()+"��(��) �����Ͽ����ϴ�.");
		
		}
		
//		System.out.println(check);
//		System.out.println("\n");
//		System.out.println(start);
//		System.out.println("\n"+last_turn+"\n");

		if(last_turn.equals(turn)) {
		for(int i=0;i<clientlist.size();i++) {
			if(clientlist.get(i).getname().equals(turn)) {
				check=1;
				if(word.equals("start")) {
					start=1;
					check=2;
					}
			}
		}
		if(check==2 && start>0) {
			for(int i=0;i<clientlist.size();i++) {
				clientlist.get(i).recive("���þ� : "+this.word+"\n"+"next : "+turn);
			}
		}
		else if(check==1&&start>0) {
		String[] everyword = this.word.split("->");
		int wordcheck=0;
		for(int i=0;i<everyword.length;i++) {
//			System.out.println(everyword[i]);
			if(everyword[i].equals(word))
				wordcheck++;
		}
			
		if((word.charAt(0) == this.word.charAt(this.word.length()-1)) && wordcheck==0) {
		this.word = this.word+"->"+word;
		for(int i=0;i<clientlist.size();i++) {
			if(clientlist.get(i).getname().equals(turn)) {
				if(i==(clientlist.size()-1))
					turn = clientlist.get(0).getname();
				else
					turn = clientlist.get(i+1).getname();
				break;
			}
		}
		
		for(int i=0;i<clientlist.size();i++) {
			clientlist.get(i).recive(this.word+"\n"+"next : "+turn);
		}
			last_turn = turn;
		}
//		else if((word.charAt(0) == this.word.charAt(this.word.length()-1)) && wordcheck==0){
//			
//		}
		else {
			if(word.equals("quit")) {
				for(int i=0;i<clientlist.size();i++) {
					clientlist.get(i).recive(turn+"��(��) �����Ͽ����ϴ�.\n");
				}
			}
			else {
			for(int i=0;i<clientlist.size();i++) {
				if(clientlist.get(i).getname().equals(turn)) {
					if(i==(clientlist.size()-1))
						turn = clientlist.get(0).getname();
					else
						turn = clientlist.get(i+1).getname();
//					temp= this.clientlist.get(i);
					fail = clientlist.get(i);
					this.clientlist2.add(fail);
					clientlist.get(i).recive("Ʋ�Ƚ��ϴ�. Ż��");
					clientlist.remove(i);
					break;
				}
			}
			for(int i=0;i<clientlist.size();i++) {
				clientlist.get(i).recive("Ʋ�Ƚ��ϴ�."+fail.getname() +" Ż��\n"+this.word+"\n"+"next : "+turn);
			}
			last_turn = turn;
			}
			if(clientlist.size() == 1) {
				last_turn = clientlist.get(0).getname();
				clientlist2.add(clientlist.get(0));
				clientlist.clear();
			for(int i=0;i<clientlist2.size();i++) {
				clientlist2.get(i).recive("����ڴ� "+clientlist2.get(clientlist2.size()-1).getname() +"�Դϴ�. ����ڰ� �������� ���� ������ �����մϴ�.\n");
				clientlist.add(clientlist2.get(i));
			}
			clientlist2.clear();
			this.word = "�б�";
			check=0;
			start=0;
			}
		}
		}
		}
		else if(word.equals("quit")) {
			for(int i=0;i<clientlist.size();i++) {
				clientlist.get(i).recive(turn+"��(��) �����Ͽ����ϴ�.\n");
			}
			if(clientlist.size() == 1) {
				last_turn = clientlist.get(0).getname();
				clientlist2.add(clientlist.get(0));
				clientlist.clear();
			for(int i=0;i<clientlist2.size();i++) {
				clientlist2.get(i).recive("����ڴ� "+clientlist2.get(clientlist2.size()-1).getname() +"�Դϴ�. ����ڰ� �������� ���� ������ �����մϴ�.\n");
				clientlist.add(clientlist2.get(i));
			}
			clientlist2.clear();
			this.word = "�б�";
			check=0;
			start=0;
			}
		}
	}
	

}
