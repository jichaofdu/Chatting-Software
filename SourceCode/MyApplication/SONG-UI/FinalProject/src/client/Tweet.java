package client;

public class Tweet {
    private int writerId;
    private String nickname;
    private String content;
    private String postTime;


    public Tweet(int id,String nickname, String content, String time){
        this.writerId = id;
        this.nickname = nickname;
        this.content = content;
        this.postTime = time;
    }

    public String getWriterName(){
        return nickname;
    }

    public int getWriterId(){
        return  writerId;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String newContent){
        this.content = newContent;
    }

    public String getPostTime(){
        return this.postTime;
    }

}
