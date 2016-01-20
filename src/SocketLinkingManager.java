import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class SocketLinkingManager extends Thread{

    Queue<Socket>           socketQueue;
    Socket                  socket1;
    Socket                  socket2;

    public SocketLinkingManager(LinkedList<Socket> socketQueue) {
        this.socketQueue = socketQueue;
    }

    @Override
    public void run() {

        while (true) {

            synchronized (this) {
                if (socketQueue.size() >= 2) {
                    socket1 = socketQueue.poll();
                    socket2 = socketQueue.poll();
                    new MessageSendingThread(socket1, socket2).start();
                    new MessageSendingThread(socket2, socket1).start();
                }
            }
            socket1 = null;
            socket2 = null;
        }
    }

}
