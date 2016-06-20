package SERVER_JC;
import java.sql.Time;

public class Tweet {
    private int writerId;
    private String content;
    private Time postTime;

    public Tweet(int id,String content,Time time){
        this.writerId = id;
        this.content = content;
        this.postTime = time;
    }

    public int getWriterId(){
        return  writerId;
    }

    public void setWriterId(int id){
        this.writerId = id;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String newContent){
        this.content = newContent;
    }

    public Time getPostTime(){
        return this.postTime;
    }

    public void setPostTime(Time newTime){
        this.postTime = newTime;
    }

}
