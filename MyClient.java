import java.io.*;
import java.net.*;

public class MyClient {

    private Socket socket;
    private DataOutputStream out;
    private BufferedReader in;

    public MyClient() {
        try {
            socket = new Socket("127.0.0.1", 50000);
            out = new DataOutputStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendMessage(String cmd) {
        byte[] cmdBytes = (cmd + "\n").getBytes();
        String response = "";
        try {
            out.write(cmdBytes);
            response = in.readLine();
            out.flush();
            System.out.println(cmd + " response = " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void close() {
        try {
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            MyClient client = new MyClient();
            client.sendMessage("HELO");
            client.sendMessage("AUTH " + System.getProperty("user.name"));
            client.sendMessage("REDY");
            client.sendMessage("GETS All");
            client.sendMessage("OK");
            client.sendMessage("QUIT");
            client.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}