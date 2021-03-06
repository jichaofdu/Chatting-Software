package SERVER_JC;

/**
 * Created by chaoj on 2016-06-19.
 */
public class ChatMessage {
    private int userId;
    private String username;
    private String content;
    private String time;

    public ChatMessage(int id, String name, String content, String time){
        this.userId = id;
        this.username = name;
        this.content = content;
        this.time = time;
    }

    public int getUserId(){
        return userId;
    }

    public void setUserId(int newId){
        this.userId = newId;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String newUsername){
        this.username = newUsername;
    }

    public String getContent(){
        return this.content;
    }

    public void setContent(String newContent){
        this.content = newContent;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String newTime){
        this.time = newTime;
    }

}
