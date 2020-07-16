import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.libjpegturbo.turbojpeg.TJ;
import org.libjpegturbo.turbojpeg.TJCompressor;

public class ImageTools {

	private int iActualSize;
	
	public BufferedImage getBufferedImage(String pFilename){
		BufferedImage img = null;

		try{
		    img = ImageIO.read(new File(pFilename)); // eventually C:\\ImageTest\\pic2.jpg
		}catch (IOException e){
		    e.printStackTrace();
		}
		return img;
	}
	
	
	public void compressAndSave(BufferedImage pSrc, int pX, int pY, int pHeight, int pWidth){
		// for some byte[] src in RGB format, representing an image of dimensions
		// 'width' and 'height'
		try
		{
		    //TJCompressor(java.awt.image.BufferedImage srcImage, int x, int y, int width, int height)
		    TJCompressor tjc = new TJCompressor(pSrc, pX, pY, pWidth, pHeight );
		    tjc.setJPEGQuality(75);
		    tjc.setSubsamp(TJ.SAMP_420);
		    byte[] jpg_data = tjc.compress(0);
		    this.iActualSize = tjc.getCompressedSize();
		    //new java.io.FileOutputStream(new java.io.File("/home/andy/Dokumente/Programmierung/TCPSyphon/001/testImage/dump.jpg")).write(jpg_data, 0, actual_size);
		}
		catch(Exception e)
		{
		    e.printStackTrace(System.err);
		}
	}
	
	public byte[] compress(BufferedImage pSrc, int pX, int pY, int pHeight, int pWidth, int pCompression){
		byte[] jpg_data = null;
		try
		{
		    //TJCompressor(java.awt.image.BufferedImage srcImage, int x, int y, int width, int height)
		    TJCompressor tjc = new TJCompressor(pSrc, pX, pY, pWidth, pHeight );
		    tjc.setJPEGQuality(pCompression);
		    tjc.setSubsamp(TJ.SAMP_420);
		    jpg_data = tjc.compress(0);
		    this.iActualSize = tjc.getCompressedSize();
		    
		}
		catch(Exception e)
		{
		    System.out.println("compress() Exception:" + e.getMessage());
		}
		return jpg_data;
	}
	
	public int getActualSize(){
		return this.iActualSize;
	}
	
}//Class
