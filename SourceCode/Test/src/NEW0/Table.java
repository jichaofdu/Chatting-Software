package NEW0;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class Table {

    public Vector<User> allUser = new Vector <User>(50); //The friend list of the user.
    public Vector<Socket> online = new Vector<Socket>(50);
    public Table(){
    	allUser.clear();
    	online.clear();
    	readFromFile();
    }
    public void readFromFile(){
    	try {	
    		Scanner scanner = new Scanner(new FileInputStream("src/user.txt"));
    		
    		while(scanner.hasNext()){
    			User temp = new User();
    			temp.isLogin = Integer.parseInt( scanner.next());
    			temp.id = Integer.parseInt( scanner.next());
    			temp.nickname = scanner.next() ;
    			temp.password = scanner.next() ;
    			
    			int friend_count = Integer.parseInt( scanner.next());
    			for (int i = 0; i < friend_count ; ++i)
    				temp.friendList.add(Integer.valueOf(scanner.next()));
    			this.allUser.addElement(temp);
    		}scanner.close();
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} 
    }
    public void writeToFile(){
    	try{
    	     BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/user.txt")));
    	     for (int i= 0 ; i < allUser.size(); ++i){
    	    	 writer.write(allUser.get(i).isLogin + " ");
    	    	 writer.write(allUser.get(i).id + " ");
    	    	 writer.write(allUser.get(i).nickname + " ");
    	    	 writer.write(allUser.get(i).password + " ");
    	    	 writer.write(allUser.get(i).friendList.size() + " ");
    	    	 for (int j= 0 ; j < allUser.get(i).friendList.size() ; ++j){
    	    		 writer.write(allUser.get(i).friendList.get(j) + " "); 
    	    	 }
    	    	 writer.write("\r\n");
    	     }
    	     writer.close();
    	}catch(Exception e){
    	}
    }
    public void showAllInfo(){
    	System.out.println("==============ALL INFO===============");
    	System.out.println("total user = " + allUser.size());
    	for (int i = 0 ; i < allUser.size(); ++i){
    		System.out.println(allUser.get(i).nickname);
    	}
    	System.out.println("=====================================");
    }
}
