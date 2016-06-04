/**
 * Created by Chao Ji on 2016-06-04.
 */

import java.util.Vector;

public class FileTag {
    private String filename;
    private int size;
    private Vector<Integer> holderList;

    public FileTag(String filename,int size){
        this.filename = filename;
        this.size = size;
        this.holderList = new Vector<>();
    }

    public String getFilename(){
        String name = this.filename;
        return name;
    }

    public int getFileSize(){
        return size;
    }

    public void addHolder(int userId){
        holderList.add(userId);
    }

    public void clearHolder(){
        holderList.removeAllElements();
    }
}
