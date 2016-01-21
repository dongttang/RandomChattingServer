import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class TcpIpServer {

    final int               SOCKET_SERVER_PORT;
    int[]                   linkedClientPair;
    ServerSocket            serverSocket;
    LinkedList<Socket>      socketQueue;

    public TcpIpServer () {
        this.SOCKET_SERVER_PORT = 7777;
        this.linkedClientPair = new int[]{0};
    }

    public void start() {
        try {
            serverSocket =  new ServerSocket(SOCKET_SERVER_PORT);
            socketQueue  =  new LinkedList<>();

            SocketQueueManager    queueManager   = new SocketQueueManager(socketQueue, serverSocket);
            SocketLinkingManager  linkingManager = new SocketLinkingManager(socketQueue, linkedClientPair);
            queueManager.start();
            linkingManager.start();
            System.out.println("서버가 시작되었습니다.");

        } catch (IOException e) {e.printStackTrace();}
    }

}
