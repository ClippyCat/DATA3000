package fcfs.algorithm;

import fcfs.adt.QueueImplementation;
import fcfs.adt.QueueInterface;
import fcfs.model.Process;

/**
 * This class simulates the FCFS scheduling algorithm.
 *
 * FCFS stands for First-Come, First-Served.
 * This means the process that arrives first will run first.
 *
 * My part:
 * - Sort processes by arrival time
 * - Add processes into the queue
 * - Remove processes from the queue to simulate execution
 * - Track each process's start time
 * - Track each process's completion time
 */
public class FCFSAlgorithm {

    // Queue used to store processes in arrival order.
    private QueueInterface<Process> processQueue;

    // Stores the processes after they have been simulated.
    private Process[] scheduledProcesses;

    /**
     * Constructor.
     * Creates an empty queue for the FCFS simulation.
     */
    public FCFSAlgorithm() {
        processQueue = new QueueImplementation<>();
    }

    /**
     * Runs the FCFS simulation.
     *
     * @param processes The list of processes entered by the user.
     * @return The processes in the order they were executed.
     */
    public Process[] simulate(Process[] processes) {

        // Check if the process list is empty.
        if (processes == null || processes.length == 0) {
            throw new IllegalArgumentException("Process list cannot be empty.");
        }

        // Clear the queue before starting a new simulation.
        processQueue.clear();

        // Sort processes by arrival time first.
        Process[] sortedProcesses = sortByArrivalTime(processes);

        // Create the result array.
        scheduledProcesses = new Process[sortedProcesses.length];

        // Add every process into the queue in arrival order.
        for (int i = 0; i < sortedProcesses.length; i++) {
            processQueue.enqueue(sortedProcesses[i]);
        }

        // currentTime keeps track of the CPU clock.
        int currentTime = 0;

        // index is used to store each completed process in the result array.
        int index = 0;

        // Continue until all processes have been executed.
        while (!processQueue.isEmpty()) {

            // Remove the next process from the front of the queue.
            Process currentProcess = processQueue.dequeue();

            // If the CPU is free before the process arrives,
            // move currentTime forward to the arrival time.
            if (currentTime < currentProcess.getArrivalTime()) {
                currentTime = currentProcess.getArrivalTime();
            }

            // The process starts when the CPU begins running it.
            int startTime = currentTime;

            // Completion time is start time plus burst time.
            int completionTime = startTime + currentProcess.getBurstTime();

            // Waiting time is how long the process waits before it starts.
            int waitingTime = startTime - currentProcess.getArrivalTime();

            // Turnaround time is total time in the system: completion minus arrival.
            int turnaroundTime = completionTime - currentProcess.getArrivalTime();

            // Store the calculated scheduling values in the process.
            currentProcess.setStartTime(startTime);
            currentProcess.setCompletionTime(completionTime);
            currentProcess.setWaitingTime(waitingTime);
            currentProcess.setTurnaroundTime(turnaroundTime);

            // Save this process in execution order.
            scheduledProcesses[index] = currentProcess;

            // Move the CPU clock forward.
            currentTime = completionTime;

            // Move to the next result position.
            index++;
        }

        // Return the completed process list.
        return scheduledProcesses;
    }

    /**
     * Sorts processes by arrival time using insertion sort.
     *
     * I used insertion sort because the queue implementation cannot use java.util,
     * and this keeps the project simple and beginner-friendly.
     *
     * @param processes The original process list.
     * @return A new process list sorted by arrival time.
     */
    private Process[] sortByArrivalTime(Process[] processes) {

        // Create a copy so the original array is not changed directly.
        Process[] sortedProcesses = new Process[processes.length];

        for (int i = 0; i < processes.length; i++) {

            // Check for null process.
            if (processes[i] == null) {
                throw new IllegalArgumentException("Process cannot be null.");
            }

            sortedProcesses[i] = processes[i];
        }

        // Insertion sort by arrival time.
        for (int i = 1; i < sortedProcesses.length; i++) {

            Process currentProcess = sortedProcesses[i];
            int j = i - 1;

            // Move processes with larger arrival times one position forward.
            while (j >= 0 &&
                    sortedProcesses[j].getArrivalTime() > currentProcess.getArrivalTime()) {

                sortedProcesses[j + 1] = sortedProcesses[j];
                j--;
            }

            // Put the current process in the correct location.
            sortedProcesses[j + 1] = currentProcess;
        }

        return sortedProcesses;
    }

    /**
     * Returns the scheduled processes after simulation.
     *
     * @return The processes in FCFS execution order.
     */
    public Process[] getScheduledProcesses() {
        return scheduledProcesses;
    }

    /**
     * Calculates the average waiting time across all scheduled processes.
     *
     * Must be called after simulate(), since it relies on the waiting time
     * already having been calculated and stored on each process.
     *
     * @return The average waiting time, or 0 if no processes have been scheduled.
     */
    public double calculateAverageWaitingTime() {
        if (scheduledProcesses == null || scheduledProcesses.length == 0) {
            return 0;
        }

        int totalWaitingTime = 0;
        for (int i = 0; i < scheduledProcesses.length; i++) {
            totalWaitingTime += scheduledProcesses[i].getWaitingTime();
        }

        return (double) totalWaitingTime / scheduledProcesses.length;
    }

    /**
     * Calculates the average turnaround time across all scheduled processes.
     *
     * Must be called after simulate(), since it relies on the turnaround time
     * already having been calculated and stored on each process.
     *
     * @return The average turnaround time, or 0 if no processes have been
     *         scheduled.
     */
    public double calculateAverageTurnaroundTime() {
        if (scheduledProcesses == null || scheduledProcesses.length == 0) {
            return 0;
        }

        int totalTurnaroundTime = 0;
        for (int i = 0; i < scheduledProcesses.length; i++) {
            totalTurnaroundTime += scheduledProcesses[i].getTurnaroundTime();
        }

        return (double) totalTurnaroundTime / scheduledProcesses.length;
    }
}
