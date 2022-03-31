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
