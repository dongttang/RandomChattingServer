import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class MessageSendingThread extends Thread {

    Socket              messageSendingSocket;
    Socket              messageReceievingSocket;
    DataInputStream     dataInputStream;
    DataOutputStream    dataOutputStream;
    String              sendingAddress;
    String              receievingAddress;

    public MessageSendingThread(Socket messageSendingSocket, Socket messageReceievingSocket) {
        this.messageSendingSocket       = messageSendingSocket;
        this.messageReceievingSocket    = messageReceievingSocket;
    }

    @Override
    public void run() {
        try {

            dataOutputStream            = new DataOutputStream(messageSendingSocket.getOutputStream());
            dataInputStream             = new DataInputStream(messageReceievingSocket.getInputStream());
            sendingAddress              = messageSendingSocket.getInetAddress().toString();
            receievingAddress           = messageReceievingSocket.getInetAddress().toString();

            System.out.println(sendingAddress + " >> " + receievingAddress + " : 스트림 연결됨");

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while (true) {
                String message = dataInputStream.readUTF().toString();
                dataOutputStream.writeUTF(message);
                dataOutputStream.flush();
                System.out.println(sendingAddress + " >> " + receievingAddress + " : " + message);
            }
        } catch (EOFException e) {
            e.printStackTrace();
            System.out.println( sendingAddress + " : 접속을 종료했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("예상치 못한 예외 발생");
        } finally {
            try {
                dataOutputStream.close();
                dataInputStream.close();
                messageSendingSocket.close();
                messageReceievingSocket.close();
                System.out.println(sendingAddress + " >> " + receievingAddress + " : 스트림 종료");
                interrupt();
            } catch (IOException e) {e.printStackTrace();}
        }
    }
}

