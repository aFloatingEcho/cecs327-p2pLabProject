public class NodeRunner {
    public static void main(String[] args) {
        Node nTest = new Node();
        // initializes communication between a Client and a Server
        nTest.runNode();
        nTest.leaveNetwork();
    }
}
