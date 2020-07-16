import java.awt.image.BufferedImage;

public class Application {

	public static void main(String[] args) {
		System.setProperty("java.net.preferIPv4Stack" , "true");
		
		
		NioServer nioServer;
		Bonjour bonjour = new Bonjour();
		bonjour.discover();
				
		
		nioServer = new NioServer( bonjour.getPort() );		
		
		ImageTools it = new ImageTools();
		TcpSyphonImage arr[] = new TcpSyphonImage[1];
		
		BufferedImage buff = it.getBufferedImage("/home/andy/Dokumente/Programmierung/TCPSyphon/001/testImage/image01_400x400.jpg");		
		byte[] byArr = it.compress(buff, 0, 0, 400, 400, 75);
		TcpSyphonImage syphonImage1 = new TcpSyphonImage();
		syphonImage1.setImage(byArr, it.getActualSize(), 400, 400, TcpSyphonImage.COMPRESSION_JPEG);
		
				
		buff = it.getBufferedImage("/home/andy/Dokumente/Programmierung/TCPSyphon/001/testImage/image02_400x400.jpg");		
		byArr = it.compress(buff, 0, 0, 400, 400, 75);
		TcpSyphonImage syphonImage2 = new TcpSyphonImage();
		syphonImage2.setImage(byArr, it.getActualSize(), 400, 400, TcpSyphonImage.COMPRESSION_JPEG);	
		
				
				
				
		(new Thread(nioServer)).start(); 
		
		while(true){
			try {
				arr[0] = syphonImage1;
				nioServer.setNewSyphonImage(arr);
				Thread.sleep(500);
				arr[0] = syphonImage2;
				nioServer.setNewSyphonImage(arr);
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
