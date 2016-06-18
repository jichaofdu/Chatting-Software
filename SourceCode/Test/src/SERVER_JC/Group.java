/**
 * Created by Chao Ji on 2016-06-04.
 */
package SERVER_JC;
import java.io.File;
import java.util.Vector;

public class Group {
    private int id;
    private Vector<Integer> memberList;
    private Vector<FileTag> fileTagList;

    public Group(int newId){
        this.id = newId;
        this.memberList = new Vector<>();
        this.fileTagList = new Vector<>();
    }

    public int getGroupId(){
        return id;
    }

    public void addMember(int userId){
        memberList.add(userId);
    }

    public void addFileTag(){

    }


}
