package Peer;

import Network.NetworkManager;

import java.util.Arrays;

public class ClientServerRunner {
    public static void main(String[] args) {
        NetworkManager nm = new NetworkManager();
        Client cTest = new Client(5555);
        Server sTest = new Server(5555);
        try {
            cTest.join(sTest.getServerSocket().getLocalSocketAddress());
            sTest.join(nm.getAllLocalIPAddresses());
            cTest.update("sync\\");
            System.out.println(Arrays.toString(sTest.receive(cTest.getcSocket())));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
