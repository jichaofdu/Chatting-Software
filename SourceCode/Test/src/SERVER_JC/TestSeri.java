/**
 * Created by chaoj on 2016-06-16.
 */
package SERVER_JC;
import java.io.*;

public class TestSeri {

    public static void main(String[] args){
        try {
            TestSeriObj obj = new TestSeriObj();
            obj.setA(13213123);
            FileOutputStream f = new FileOutputStream("tmp");
            ObjectOutputStream s = new ObjectOutputStream(f);
            s.writeObject(obj);

            FileInputStream g = new FileInputStream("tmp");
            ObjectInputStream h = new ObjectInputStream(g);

            TestSeriObj newObj = (TestSeriObj)h.readObject();
            System.out.println(newObj.getA());
        }catch(Exception e){

        }

    }
}
