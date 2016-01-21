import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class SocketLinkingManager extends Thread{

    Queue<Socket>           socketQueue;
    Socket                  socket1;
    Socket                  socket2;
    int[] numOfLinkedClientPair;

    public SocketLinkingManager(LinkedList<Socket> socketQueue, int[] numOfLinkedClientPair) {
        this.socketQueue = socketQueue;
        this.numOfLinkedClientPair = numOfLinkedClientPair;
    }

    @Override
    public void run() {

        while (true) {

            synchronized (this) {
                if (socketQueue.size() >= 2) {
                    socket1 = socketQueue.poll();
                    socket2 = socketQueue.poll();
                    new MessageSendingThread(socket1, socket2, numOfLinkedClientPair).start();
                    new MessageSendingThread(socket2, socket1, numOfLinkedClientPair).start();
                    numOfLinkedClientPair[0] ++;
                    System.out.println( "\n현재 채팅방 수 : " + numOfLinkedClientPair[0] + "개");
                }
            }
            socket1 = null;
            socket2 = null;
        }
    }

}
