/**
 * Created by Chao Ji on 2016-06-04.
 */
package SERVER_JC;
import java.io.File;
import java.util.Vector;

import java.util.Vector;

public class Group {
    private int id;
    public Vector<ChatMessage> messageList;
    private Vector<Integer> memberList;

    public Group(int newId){
        this.id = newId;
        this.memberList = new Vector<>();
        this.messageList = new Vector<>();
    }

    public int getGroupId(){
        return id;
    }

    public void addMember(int userId){
        memberList.add(userId);
    }

    public void addMessage(ChatMessage msg){
        this.messageList.add(msg);
    }


}
