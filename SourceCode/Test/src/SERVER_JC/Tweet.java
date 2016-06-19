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

    public String getContent(){
        return content;
    }

    public Time getPostTime(){
        return this.postTime;
    }

}
