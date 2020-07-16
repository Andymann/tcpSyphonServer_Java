import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class NetworkTools {

	/**
	 * Gibt eine Liste mit lokalen IP Adressen zurueck
	 * @return
	 */
	public List<InetAddress> enumLocalIPs(){
    	List<InetAddress> ip = new ArrayList<InetAddress>();
    	try{
	    	Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
	    		while (interfaces.hasMoreElements()) {
	    		  NetworkInterface networkInterface = interfaces.nextElement();
	    		  if (networkInterface.isLoopback()){
	    		    continue;    // Don't want to broadcast to the loopback interface
	    		  }
	    		  for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
	    			  InetAddress tmpIP = interfaceAddress.getAddress();
	    			  
	    			  if (tmpIP == null){
	    				  continue;
	    			  }else{
	    				  ip.add(tmpIP);
	    			  }
	    		  }
	    		}
    	}catch(Exception e){
    		System.out.println("NetworkTools.enumLocalIPs():" + e.getMessage());    		
    	}
    	return ip;
    }
}
