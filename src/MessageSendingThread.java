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
    int[]               numOfLinkedClientPair;

    public MessageSendingThread(Socket messageSendingSocket, Socket messageReceievingSocket, int[] linkedClientPair) {
        this.messageSendingSocket       = messageSendingSocket;
        this.messageReceievingSocket    = messageReceievingSocket;
        this.numOfLinkedClientPair = linkedClientPair;
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
            System.out.println(sendingAddress + " >> " + receievingAddress + " : 스트림 연결도중 오류 발생");
        }finally {
            interrupt();
        }


        try {
            while (true) {
                String message = dataInputStream.readUTF().toString();
                dataOutputStream.writeUTF(message);
                dataOutputStream.flush();
                System.out.println(sendingAddress + " >> " + receievingAddress + " : " + message);
            }
        } catch (EOFException e) {
            numOfLinkedClientPair[0] --;
            System.out.println( "\n현재 채팅방 수 : " + numOfLinkedClientPair[0] + "개");
            System.out.println( sendingAddress + " : 접속을 종료했습니다.");

        } catch (Exception e) {
            System.out.println( sendingAddress + " : 예외 발생... 반대편 스레드에서 접속을 끊었을 수 있습니다.");
        } finally {
            try {
                dataOutputStream.close();
                dataInputStream.close();
                messageSendingSocket.close();
                messageReceievingSocket.close();
                System.out.println(sendingAddress + " >> " + receievingAddress + " : 스트림 종료");
                interrupt();
            } catch (IOException e) {
                System.out.println(sendingAddress + " >> " + receievingAddress + " : 스트림 종료도중 오류 발생");
            }
        }
    }
}

