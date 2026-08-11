package fcfs.model;

public class Process {

    // Basic process information
    private String processID;
    private int arrivalTime;
    private int burstTime;

    // Scheduling information
    private int startTime;
    private int completionTime;
    private int waitingTime;
    private int turnaroundTime;

    public Process(String processId, int arrivalTime, int burstTime) {

        setProcessId(processId);
        setArrivalTime(arrivalTime);
        setBurstTime(burstTime);

        this.startTime = 0;
        this.completionTime = 0;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
    }

    public String getProcessID() {
        return processID;
    }

    public void setProcessId(String processId) {
        if (processId == null || processId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Process ID cannot be empty."
            );
        }

        this.processID = processId;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        if (arrivalTime < 0) {
            throw new IllegalArgumentException(
                    "Arrival time cannot be negative."
            );
        }

        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        if (burstTime <= 0) {
            throw new IllegalArgumentException(
                    "Burst time must be greater than zero."
            );
        }

        this.burstTime = burstTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

	public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    @Override
    public String toString() {
        return processID
                + "\t"
                + arrivalTime
                + "\t"
                + burstTime
                + "\t"
                + startTime
                + "\t"
                + completionTime
                + "\t"
                + waitingTime
                + "\t"
                + turnaroundTime;
    }
}

