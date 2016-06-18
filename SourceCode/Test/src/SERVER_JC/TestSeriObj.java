/**
 * Created by chaoj on 2016-06-16.
 */
package SERVER_JC;
import  java.io.Serializable;

public class TestSeriObj implements Serializable{
    private int a = 213;

    public int getA(){
        return a;
    }

    public void setA(int newa){
        a = newa;
    }

}
