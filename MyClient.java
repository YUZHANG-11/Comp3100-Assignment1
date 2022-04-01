import java.io.*;
import java.net.*;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

public class MyClient {
    private Socket socket;
    private DataOutputStream out;
    private BufferedReader in;
    private CharBuffer cb;

    public MyClient() { // construct class fixed properties in Constructors
        try {
            socket = new Socket("127.0.0.1", 50000); // port
            cb = CharBuffer.allocate(40960);
            out = new DataOutputStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendMessage(String cmd) { // create a method receive a command and return a response back
        byte[] cmdBytes = (cmd + "\n").getBytes();
        String response = "";
        try {
            out.write(cmdBytes);
            in.read(cb);
            cb.flip();
            response = cb.toString(); // get all server nodes with one line
            System.out.println(cmd + " response: " + response);
            cb.clear();
            out.flush(); // after finish every write do flush
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void close() { // create a method to close the whole program process
        try {
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ServerNode> getServerList(String servers) {
        String[] lines = servers.split("\n"); // split server nodes by "\n"
        List<ServerNode> serverNodeList = new ArrayList<>();
        for (String line : lines) {
            String[] elements = line.split(" "); // split server nodes again by space
            ServerNode serverNode = new ServerNode();

            serverNode.setServerType(elements[0]); // Manually loop through the fixed 9 properties
            serverNode.setServerID(Integer.parseInt(elements[1]));
            serverNode.setState(elements[2]);
            serverNode.setCurStartTime(Integer.parseInt(elements[3]));
            serverNode.setCore(Integer.parseInt(elements[4]));
            serverNode.setMemory(Integer.parseInt(elements[5]));
            serverNode.setDisk(Integer.parseInt(elements[6]));
            serverNode.setwJobs(Integer.parseInt(elements[7]));
            serverNode.setrJobs(Integer.parseInt(elements[8])); // iterate over each server node and make them have attributes

            serverNodeList.add(serverNode); // add them into the Arraylist
        }
        return serverNodeList;
    }

    public static void main(String[] args) {

        MyClient client = new MyClient();

        client.sendMessage("HELO"); // establish connection
        client.sendMessage("AUTH " + System.getProperty("user.name"));
        String jobStr = client.sendMessage("REDY");
        client.sendMessage("GETS All"); // achieve all server nodes
        String servers = client.sendMessage("OK");
        List<ServerNode> nodeList = client.getServerList(servers);
        List<ServerNode> biggestCoreNodeList = new ArrayList<>();
        for (ServerNode node : nodeList) {
            if (biggestCoreNodeList.isEmpty() || (biggestCoreNodeList.get(0).getCore() == node.getCore()
                    && biggestCoreNodeList.get(0).getServerType().equals(node.getServerType()))) {
                biggestCoreNodeList.add(node);
            } else if (biggestCoreNodeList.get(0).getCore() < node.getCore()) {
                biggestCoreNodeList.clear();
                biggestCoreNodeList.add(node);
            }
        }
        client.sendMessage("OK");
        int round = 0;
        while (!jobStr.equals("NONE\n")) {
            if (jobStr.startsWith("JOBN")) {
                Job job = client.getJobFromString(jobStr);
                client.sendMessage("SCHD " + job.getJobID() + " " + biggestCoreNodeList.get(round).getServerType() + " "
                        + biggestCoreNodeList.get(round).getServerID());
                if (round < biggestCoreNodeList.size() - 1) {
                    round++;
                } else {
                    round = 0;
                }
            }
            jobStr = client.sendMessage("REDY");
        }
        client.sendMessage("QUIT"); // shutdown
        client.close();
    }

    private Job getJobFromString(String jobStr) {
        Job job = new Job();
        String[] elements = jobStr.split(" "); // split server nodes again by space
        job.setSubmitTime(Integer.parseInt(elements[1])); // Manually loop through the fixed 9 properties
        job.setJobID(Integer.parseInt(elements[2]));
        job.setEstRuntime(Integer.parseInt(elements[3]));
        job.setCore(Integer.parseInt(elements[4]));
        job.setMemory(Integer.parseInt(elements[5])); // iterate over each job and make them have attributes
        job.setDisk(Integer.parseInt(elements[6].replace("\n", ""))); // Remove the last newline to prevent no conversion
        return job;
    }


    public class Job {
        private int submitTime;
        private int jobID;
        private int estRuntime;
        private int core;
        private int memory;
        private int disk;

        public int getSubmitTime() {
            return submitTime;
        }

        public void setSubmitTime(int submitTime) {
            this.submitTime = submitTime;
        }

        public int getJobID() {
            return jobID;
        }

        public void setJobID(int jobID) {
            this.jobID = jobID;
        }

        public int getEstRuntime() {
            return estRuntime;
        }

        public void setEstRuntime(int estRuntime) {
            this.estRuntime = estRuntime;
        }

        public int getCore() {
            return core;
        }

        public void setCore(int core) {
            this.core = core;
        }

        public int getMemory() {
            return memory;
        }

        public void setMemory(int memory) {
            this.memory = memory;
        }

        public int getDisk() {
            return disk;
        }

        public void setDisk(int disk) {
            this.disk = disk;
        }
    }

    public class ServerNode {
        private String serverType;
        private int serverID;
        private String state;
        private int curStartTime;
        private int core;
        private int memory;
        private int disk;
        private int wJobs;
        private int rJobs;

        public String getServerType() {
            return serverType;
        }

        public void setServerType(String serverType) {
            this.serverType = serverType;
        }

        public int getServerID() {
            return serverID;
        }

        public void setServerID(int serverID) {
            this.serverID = serverID;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public int getCurStartTime() {
            return curStartTime;
        }

        public void setCurStartTime(int curStartTime) {
            this.curStartTime = curStartTime;
        }

        public int getCore() {
            return core;
        }

        public void setCore(int core) {
            this.core = core;
        }

        public int getMemory() {
            return memory;
        }

        public void setMemory(int memory) {
            this.memory = memory;
        }

        public int getDisk() {
            return disk;
        }

        public void setDisk(int disk) {
            this.disk = disk;
        }

        public int getwJobs() {
            return wJobs;
        }

        public void setwJobs(int wJobs) {
            this.wJobs = wJobs;
        }

        public int getrJobs() {
            return rJobs;
        }

        public void setrJobs(int rJobs) {
            this.rJobs = rJobs;
        }

        @Override
        public String toString() {
            return "ServerNode{" +
                    "serverType='" + serverType + '\'' +
                    ", serverID=" + serverID +
                    ", state='" + state + '\'' +
                    ", curStartTime=" + curStartTime +
                    ", core=" + core +
                    ", memory=" + memory +
                    ", disk=" + disk +
                    ", wJobs=" + wJobs +
                    ", rJobs=" + rJobs +
                    '}';
        }
    }

}