import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class SocketQueueManager extends Thread {

    Queue<Socket>           socketQueue;
    ServerSocket            serverSocket;
    Socket                  acceptedSocket;

    public SocketQueueManager (LinkedList<Socket> socketQueue, ServerSocket serverSocket ) {
        this.socketQueue    = socketQueue;
        this.serverSocket   = serverSocket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                acceptedSocket = serverSocket.accept();
                socketQueue.offer(acceptedSocket);
                acceptedSocket = null;
            } catch (IOException e) {e.printStackTrace();}
        }
    }


}
