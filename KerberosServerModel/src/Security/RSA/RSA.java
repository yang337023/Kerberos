package Security.RSA;
import java.math.BigInteger;

public class RSA {
	
	/**
	 * RSA����һ��byte
	 * @param Key ��������ʱ�ù�Կ����ǩ��ʱ��˽Կ
	 * @param src 
	 * @return 
	 */
	public static byte[] encrypt(String Key, byte[] src){
		
		BigInteger a = new BigInteger(KeyManger.getKeyMain(Key));
		BigInteger n = new BigInteger(KeyManger.getKeyN(Key));
		
		BigInteger m = new BigInteger(src);
		BigInteger c = m.modPow(a, n);
		
        return c.toByteArray();
	}
	
	/**
	 * RSA����һ��byte
	 * @param Key ��������ʱ��˽Կ����֤ǩ��ʱ�ù�Կ
	 * @param src 
	 * @return 
	 */
	public static byte[] decrypt(String Key, byte[] src){
		
		StringBuffer buffer = new StringBuffer();
		BigInteger a = new BigInteger(KeyManger.getKeyMain(Key));
		BigInteger n = new BigInteger(KeyManger.getKeyN(Key));
		
		BigInteger c = new BigInteger(src);
		BigInteger m = c.modPow(a, n);
		
		byte[] tarBytes = m.toByteArray();
		
		for(int i=0;i<tarBytes.length;i++){
			buffer.append((char)tarBytes[i]);
        }
        return buffer.toString().getBytes();
	}
	
	public static void main(String[] args){
		for(int i=0; i< 2; i++){
		KeyManger keyManger = new KeyManger();
		String publicKey = keyManger.getPublicKey();
		String privateKey = keyManger.getPrivateKey();
		System.out.println(publicKey+"\n"+privateKey);
		
		}
		
	}
}
