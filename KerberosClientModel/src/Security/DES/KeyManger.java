package Security.DES;

/**
 * ��64λ������Կ����ȡ��56Ϊ������16��48λ����Կ
 * @author gaoshao
 *
 */
public class KeyManger {
	
	private static KeyManger instance = null;
	
	private String srcKey = "GAoShAO$";
	
	private BitManger[] keys = new BitManger[16];
	
	/**
	 * ʹ��Ĭ�ϵ�ԭʼ��Կ����keys
	 * @throws Exception 
	 */
	private KeyManger() throws Exception{
		initKeys();
	}
	
	/**
	 * ��ʼ��keys
	 * @throws Exception 
	 */
	private void initKeys() throws Exception{
		byte[] bytes = this.srcKey.getBytes();
		BitManger temp = new BitManger(bytes);
		BitManger srcKeyBit = new BitManger(56);
		int i;
		for(i=0; i < 56; i++){
			srcKeyBit.set(i, temp.get(Table.PC1[i]-1));
		}
		for(i=0; i < 16; i++){
			keys[i] = new BitManger(48);
			srcKeyBit.rotateLeft(0, 28, Table.Loop[i]);
			srcKeyBit.rotateLeft(28, 56, Table.Loop[i]);
			for(int j=0; j < 48; j++)
				keys[i].set(j,srcKeyBit.get(Table.PC2[j]-1));
		}
	}
	
	/**
	 * ����KeyManger��ʵ��
	 * @return
	 */
	public static KeyManger getKeyManger(){
		if(instance == null){
			try {
				instance = new KeyManger();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	/**
	 * �޸�Ĭ�ϵ�ԭʼ��Կ
	 * @param srcKey 
	 * @throws Exception 
	 */
	public void setSrcKey(String srcKey) throws Exception{
		this.srcKey = srcKey;
		initKeys();
	}
	
	/**
	 * 
	 * @param index ��Կ�׺�
	 * @return ���ر��Ϊindex����Կ��
	 */
	public BitManger getKey(int index){
		return keys[index];
	}
}
