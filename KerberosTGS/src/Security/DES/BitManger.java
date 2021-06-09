package Security.DES;
import java.util.BitSet;


public class BitManger extends BitSet {

	/**
	 * ���л�ID
	 */
	private static final long serialVersionUID = 1L;
	
	private byte[] bytes = null;
	
	public BitManger(int nbits){
		super(nbits);
		bytes = new byte[nbits/8];
	}
	
	/**
	 * ��ʼ��һ��bit������
	 * @param bytes �������С������8��byte��������ʼ��һ��64λ��bit������
	 * @throws Exception param should no more than 64bit
	 */
	public BitManger(byte[] bytes) throws Exception{
		super(64);
		this.bytes = bytes;
		if(this.bytes.length > 8){
			throw new Exception("param should no more than 64bit");
		}
		for(int i=0; i < this.bytes.length; i++){
			for(int j=0; j < 8; j++){
				if((this.bytes[i]&(0x01 << j))!= 0x00)
					set(i*8+j);
			}
		}
	}

	/*
	 * ��IP��ԭʼ���Ĵ���
	 */
	public void contentIP(){
		BitManger temp = new BitManger(64);
		int i;
		for(i=0; i<64; i++){
			temp.set(i, get(i));
		}
		
		for(i=0; i<64; i++){
			set(i,temp.get(Table.IP[i]-1));
		}
		
		temp = null;
	}
	
	/*
	 * ��IPR��ָ�����
	 */
	public void contentIPR(){
		BitManger temp = new BitManger(64);
		int i;
		for(i=0; i<64; i++){
			temp.set(i, get(i));
		}
		
		for(i=0; i<64; i++){
			set(i,temp.get(Table.IPR[i]-1));
		}
		
		temp = null;
	}
	/**
	 *
	 * @return ����һϵ�м��ܻ���ܲ������byte����
	 */
	public byte[] getBytes(){
		byte[] result = new byte[this.bytes.length];
		for(int i=0; i < this.bytes.length; i++){
			for(int j=0; j < 8; j++){
				if(get(i*8+j)){
					result[i] |= 0x01 << j;
				}
			}
		}
		return result;
	}
	
	/**
	 * ����һ�����ݿ���Ҳ�
	 * @return
	 */
	public BitManger getRight(){
		BitManger right = new BitManger(32);
		int i;
		for(i=0;i<32;i++)
			right.set(i,get(i+32));
		return right;
	}
	
	/**
	 * ����㵽�յ�ѭ��nλ
	 * @param start ���
	 * @param end �յ�
	 * @param n 
	 * @throws Exception 
	 */
	public void rotateLeft(int start, int end, int n) throws Exception{
		if(start < 0 || end < 0 || start > 64 || end > 64 || start > end){
			throw new Exception("illegal index of statr or end in rotateLeft(int start, int end, int n)");
		}
		int i;
		boolean[] temp = new boolean[n];
		for(i=0; i<n; i++){
			temp[i] = get(i+start);
		}
		for(i = start ; i < end-n ; i++){
			set(i,get(i+n));
		}
		for(i = 0; i < n; i++){
			set(i + end -n,temp[i]);
		}
	}
	
	/**
	 * ���ҽ���
	 */
	public void switchLR(){
		try {
			rotateLeft(0,64,32);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String toString(){
		StringBuilder b = new StringBuilder();
		for(int i=0; i < 64; i++){
			if(get(i))
				b.append('1');
			else
				b.append('0');
		}
		return b.toString();
	}
}
