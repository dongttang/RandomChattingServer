import sun.plugin2.message.Message;

import java.net.Socket;
import java.util.PriorityQueue;

public class SocketLinkingManager extends Thread{

    PriorityQueue<Socket>   socketQueue;
    Socket                  socket1;
    Socket                  socket2;

    public SocketLinkingManager( PriorityQueue<Socket> socketQueue ) {
        this.socketQueue = socketQueue;
    }

    @Override
    public void run() {

        while (true) {

            if ( socketQueue.size() >= 2 ) {
                socket1 = socketQueue.poll();
                socket2 = socketQueue.poll();
                new MessageSendingThread(socket1, socket2).start();
                new MessageSendingThread(socket2, socket1).start();

                socket1 = null;
                socket2 = null;
            }
        }



    }




}
