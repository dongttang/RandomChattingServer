import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MessageSendingThread extends Thread {

    Socket messageSendingSocket;
    Socket messageReceievingSocket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    public MessageSendingThread(Socket messageSendingSocket, Socket messageReceievingSocket) {
        this.messageSendingSocket       = messageSendingSocket;
        this.messageReceievingSocket    = messageReceievingSocket;
    }

    @Override
    public void run() {
        try {

            dataOutputStream            = new DataOutputStream(messageSendingSocket.getOutputStream());
            dataInputStream             = new DataInputStream(messageReceievingSocket.getInputStream());
            String sendingAddress       = messageSendingSocket.getInetAddress().toString();
            String receievingAddress    = messageReceievingSocket.getInetAddress().toString();

            System.out.println(sendingAddress + " >> " + receievingAddress + " : 스트림 연결됨");

        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                String message = dataInputStream.readUTF().toString();
                dataOutputStream.writeUTF(message);
                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                String sendingAddress = messageSendingSocket.getInetAddress().toString();
                String receievingAddress = messageReceievingSocket.getInetAddress().toString();
                System.out.println(sendingAddress + " >> " + receievingAddress + " : 스트림 종료");
            }
        }
    }

}
