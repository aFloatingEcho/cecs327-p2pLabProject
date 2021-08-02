package Network;

import java.util.List;

public class NetworkManagerRunner {
    public static void main(String[] args) throws InterruptedException {
        NetworkManager nm = new NetworkManager();
//        List<String> ipLst = nm.getLocalIPAddresses();
        System.out.println(nm.getLocalIPAddresses());
//        System.out.println(ipLst);
    }
}
