import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;



public class Bonjour {
	
private int iPort = 59852;
	
	
	public Bonjour(){		
	}

	
	/**
	 * Registeres the Bonjour Service in order for the TCPSClient ot find us and
	 * initiate communication
	 * 
	 * @param pServicename
	 */
	public void registerBonjour(){
		this.registerBonjour("TCPSyphonJAVA", new NetworkTools().enumLocalIPs().get(0), iPort);
	}
	
	
	public void registerBonjour(String pServicename, InetAddress pInetAddress, int pPort) {
		System.out.println("Bonjour.registerBonjour() on Interface " + pInetAddress.getHostAddress());
		try {
			JmDNS jmdns = JmDNS.create( pInetAddress);
			this.iPort = pPort;
			ServiceInfo info = ServiceInfo.create("_osc._udp.local", pServicename, iPort, 0, 0, "");	//OSC Erkennung, VDMX, TouchOSC, etc
//			ServiceInfo info = ServiceInfo.create("_tl_tcpvt._tcp.local", pServicename, iPort, 0, 0, ""); //TCPSClient
			
			jmdns.registerService(info);
			System.out.println("Registered JmDNS, Application ready");

		} catch (Exception e) {
			System.out.println("registerBonjour():" + e.getMessage());
		}
	}
	
	public int getPort(){
		return iPort;
	}
	
	/**
	 * 21.09.2017
	 */
	public void discover(){
		try {
            // Create a JmDNS instance
            JmDNS jmdns = JmDNS.create( new NetworkTools().enumLocalIPs().get(0) );
            //JmDNS jmdns = JmDNS.create();

            // Add a service listener
            jmdns.addServiceListener("_osc._udp.local.", new SampleListener());

            // Wait a bit
            Thread.sleep(30000);
        } catch (UnknownHostException e) {
            System.out.println("UnknownHostException:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException:" + e.getMessage());
        } catch (InterruptedException e) {
			System.out.println("InterruptedException:" + e.getMessage() );
		}
	}
	
	private static class SampleListener implements ServiceListener {
        @Override
        public void serviceAdded(ServiceEvent event) {
            System.out.println("Service added: " + event.getInfo());
            System.out.println("Service added: " + event.getInfo().getHostAddress().toString());
            System.out.println("Service added: " + event.getInfo().getQualifiedName().toString());
            System.out.println("Service added: " + event.getInfo().getServer());
            System.out.println("Service added: " + event.getInfo().getApplication());
            System.out.println("Service added: " + event.getDNS().getHostName());  //Local
            System.out.println("Service added: " + event.getName());//Remote <LEER>Application<LEER>Instanz
            //System.out.println("Service added: " + event.getDNS().getServiceInfo("_server", "_port") );
            System.out.println("Service added: " + event.getInfo().getProtocol());
            System.out.println("Service added: " + event.getInfo().getPropertyString("server") );
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
            System.out.println("Service removed: " + event.getInfo());
        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            System.out.println("Service resolved: " + event.getInfo());
        }
    }
	
}//Class

