
public class TcpSyphonImage {
	
public static final int COMPRESSION_TURBOJPEG = 0x04;
public static final int COMPRESSION_JPEG = 0x00;
	
	private byte[] byImage;
	private byte[] byHeader;
	private int iImageSize;
	private int iHeight;
	private int iWidth;
	
	TcpSyphonImage(){
		byHeader = new byte[16];
		for(int i=0; i<16; i++){
			byHeader[i]=0;
		}
	}
	
	public void setImage(byte[] pImage, int pImageSize, int pWidth, int pHeight, int pCompression){
		this.byImage = pImage;
		this.iImageSize = pImageSize;
		this.iWidth = pWidth;
		this.iHeight = pHeight;		
		this.createHeader(pWidth, pHeight, pImageSize, pCompression);
	}
	
	
	private void createHeader(int pWidth, int pHeight, int pImageSize, int pCompression){
		byHeader[0] = (byte)pCompression;
		byHeader[4] = getBytes(pWidth)[1]; // width, LO Byte
		byHeader[5] = getBytes(pWidth)[0]; // width, HI Byte
		byHeader[8] = getBytes(pHeight)[1]; // height
		byHeader[9] = getBytes(pHeight)[0]; // height
		byHeader[12] = getBytes(pImageSize+16)[1];
		byHeader[13] = getBytes(pImageSize+16)[0];
	}
	
	/**
	 * Returns the necessary header + actual image-data
	 * @return
	 */
	public synchronized byte[] getTcpSyphonData(){
		byte[] byRet = new byte[16 + this.iImageSize];
		
		System.arraycopy(byHeader, 0, byRet, 0, 16);
		System.arraycopy(byImage, 0, byRet, 16, iImageSize);
		return byRet;
	}
	
	@Deprecated
	public void setImage(byte[] pImage){
		this.byImage = pImage;
	}
	
	@Deprecated
	public byte[] getImage(){
		return this.byImage;
	}
	
	@Deprecated
	public void setImageSize(int pSize){
		this.iImageSize = pSize;
	}
	
	@Deprecated
	public int getImageSize(){
		return this.iImageSize;
	}
	
	@Deprecated
	public int getHeight(){
		return this.iHeight;
	}
	
	@Deprecated
	public int getWidth(){
		return this.iWidth;
	}
	
	@Deprecated
	public void setHeader(byte[] pHeader){
		this.byHeader = pHeader;
	}
	
	@Deprecated
	public byte[] getHeader(){
		return this.byHeader;
	}
	
	/**
	 * Returns a 2-Element Byte-array containing hi- and lo- byte
	 * 
	 * @param pVal < 65536 (0xFF 0xFF)
	 * @return
	 */
	private static byte[] getBytes(int pVal) {
		if (pVal < 65536) {
			return new byte[] { (byte) (pVal >>> 8), // HI
					(byte) pVal }; // LO
		} else {
			return null; // LAME!
		}
	}

}
