package fcfs.model;

/**
 * Represents a process used in the FCFS scheduling algorithm.
 */
public class Process {

    private String processId;
    private int arrivalTime;
    private int burstTime;

    // Scheduling results calculated by FCFSAlgorithm.
    private int startTime;
    private int completionTime;
    private int waitingTime;
    private int turnaroundTime;

    
    /* Creates a process using its basic scheduling information.*/
       
     
    public Process(String processId, int arrivalTime, int burstTime) {
        setProcessId(processId);
        setArrivalTime(arrivalTime);
        setBurstTime(burstTime);
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        if (processId == null || processId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Process ID cannot be empty."
            );
        }

        this.processId = processId;
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
}
