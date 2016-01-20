import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.PriorityQueue;

public class SocketQueueManager extends Thread {

    PriorityQueue<Socket>   socketQueue;
    ServerSocket            serverSocket;
    Socket                  acceptedSocket;

    public SocketQueueManager ( PriorityQueue<Socket> socketQueue, ServerSocket serverSocket ) {
        this.socketQueue    = socketQueue;
        this.serverSocket   = serverSocket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                acceptedSocket = serverSocket.accept();
                socketQueue.add(acceptedSocket);
                acceptedSocket = null;
            } catch (IOException e) {e.printStackTrace();}
        }
    }


}
