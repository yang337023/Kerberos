package Message;
import java.util.Arrays;

public class a {
    public static void  main(String agrs[]){
        String mys = "aB";
        byte[] mybytes = String2Byte.String2Bina(new String(mys));
        for(byte myb:mybytes){
            System.out.print(myb);
        }
    }

}
