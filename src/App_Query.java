import java.net.MulticastSocket;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class App_Query {
	
    public static void main(String[] args) {
//        if( args.length == 0 ) runClient();
//        if(args[0].equals("s")) runServer();
//        else runClient();
    	
    	//runClient();
    	runServer();
    }

    //static String mcastAddr = "239.255.100.100";  // Chosen at random from local network block at http://www.iana.org/assignments/multicast-addresses/multicast-addresses.xhtml
    //static int port = 4446;
    
    static String mcastAddr = "224.0.0.251";  // Chosen at random from local network block at http://www.iana.org/assignments/multicast-addresses/multicast-addresses.xhtml
    static int port = 5353;

    public static void runServer() {
        while (true) {
            try {
                MulticastSocket s = new MulticastSocket(port);
                InetAddress group = InetAddress.getByName(mcastAddr);
                s.joinGroup(group);

                byte[] recData = new byte[100];
                DatagramPacket receivePacket = new DatagramPacket(recData, recData.length);
                s.receive(receivePacket);
                String strrec = new String(recData,0,receivePacket.getLength());
                print("server received: " + strrec);
                print("from: " + receivePacket.getAddress().toString());

                if(strrec.equals("Are you there?")) {
                    String msg = "Here I am";
                    byte[] msgData = msg.getBytes();
                    DatagramPacket msgPacket = new DatagramPacket(msgData, msgData.length, receivePacket.getAddress(), receivePacket.getPort());
                    s.send(msgPacket);
                    print("server sent: " + msg + "\n");
                } else {
                    print("Didn't send; unrecognized message.");
                }
                
                print("sending response");
                byte[] msgData = new byte[]{ 0x00, 0x00, (byte) 0x84, 0x00, 0x00, 0x01, 0x00, 0x01, 0x00, 0x00, 0x00, 0x04, 0x09, 0x5f, 0x74, 0x6c, 0x5f, 0x74, 0x63, 0x70, 0x76, 0x74, 0x04, 0x5f, 0x74, 0x63, 0x70, 0x05, 0x6c, 0x6f, 0x63, 0x61, 0x6c, 0x00, 0x00, 0x0c, 0x00, 0x01, (byte) 0xc0, 0x0c, 0x00, 0x0c, 0x00, 0x01, 0x00, 0x00, 0x00, 0x0a, 0x00, 0x1c, 0x19, 0x54, 0x43, 0x50, 0x53, 0x79, 0x70, 0x68, 0x6f, 0x6e, 0x2d, 0x69, 0x63, 0x68, 0x2e, 0x6c, 0x6f, 0x63, 0x61, 0x6c, 0x64, 0x6f, 0x6d, 0x61, 0x69, 0x6e, (byte) 0xc0, 0x0c, (byte) 0xc0, 0x32, 0x00, 0x21, 0x00, 0x01, 0x00, 0x00, 0x00, 0x0a, 0x00, 0x0c, 0x00, 0x00, 0x00, 0x00, 0x1e, 0x61, 0x03, 0x49, 0x63, 0x68, (byte) 0xc0, 0x1b, (byte) 0xc0, 0x32, 0x00, 0x10, 0x00, 0x01, 0x00, 0x00, 0x00, 0x0a, 0x00, 0x01, 0x00, (byte) 0xc0, 0x60, 0x00, 0x1c, 0x00, 0x01, 0x00, 0x00, 0x00, 0x0a, 0x00, 0x10, (byte) 0xfe, (byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0c, (byte) 0xe2, 0x50, 0x1e, 0x60, 0x47, 0x60, (byte) 0xb0, (byte) 0xc0, 0x60, 0x00, 0x01, 0x00, 0x01, 0x00, 0x00, 0x00, 0x0a, 0x00, 0x04, (byte) 0xc0, (byte) 0xa8, 0x01, 0x41};
                DatagramPacket msgPacket = new DatagramPacket(msgData, msgData.length, receivePacket.getAddress(), receivePacket.getPort());
                s.send(msgPacket);

            } catch (Exception e) {
                print(e.toString());
            }
        }
    }

    public static void runClient() {
        try {
            DatagramSocket s = new DatagramSocket();

            String msg = "QU ARE YOU THERE";  // Magic string
            byte[] msgData = msg.getBytes();
            
            msgData = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x09 , 0x5f , 0x74 , 0x6c , 0x5f , 0x74 , 0x63 , 0x70 , 0x76 , 0x74 , 0x04 , 0x5f , 0x74 , 0x63 , 0x70 , 0x05 , 0x6c , 0x6f , 0x63 , 0x61 , 0x6c , 0x00 , 0x00 , 0x0c , 0x00 , 0x01};
            DatagramPacket datagramPacket = new DatagramPacket(msgData, msgData.length, InetAddress.getByName(mcastAddr), port);
            s.send(datagramPacket);
            print("client sent: " + msg);

            byte[] recData = new byte[100];
            DatagramPacket receivePacket = new DatagramPacket(recData, recData.length);
            s.receive(receivePacket);
            String strrec = new String(recData,0,receivePacket.getLength());
            print("client received: " + strrec);
            print("from " + receivePacket.getAddress().toString() + " : " + receivePacket.getPort());

            System.exit(0);
        } catch (Exception e) {
            print(e.toString());
        }
    }
    static void print(String s) {             System.out.println(s);         }
}