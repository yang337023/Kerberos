package Message;

public class String2Byte {
    public static byte[] String2Bina(String s){
        char[] text=s.toCharArray();
        byte[] result = new byte[8*text.length];
        for(int i=0;i<text.length;i++){
            // result +=Integer.toBinaryString(text[i])+ " ";
            String tmp = Integer.toBinaryString(text[i]);
            if(tmp.length()!=8){
                int rest = 8-tmp.length();
                for(int j=0;j<rest;j++){
                    tmp="0"+tmp;
                }
            }
//            System.out.println(tmp);
            for (int j=0;j<tmp.length();j++){
                result[i*8+j]= (byte) Integer.parseInt(String.valueOf(tmp.charAt(j)));
            }
        }
        return result;
    }
}
