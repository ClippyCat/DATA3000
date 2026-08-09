package fcfs.app;

import fcfs.model.Process;

import javax.swing.JOptionPane;

/**
 * Renders the simulation output through JOptionPane dialogs: first the
 * per-process results (one line per process), then the averages screen.
 *
 * The formatting deliberately matches the sample runs exactly, including the
 * "«...»" separators, the "►" markers, and the averages line where the waiting
 * value sits directly against "►" while the turnaround value has a leading space.
 */
public class ResultDisplay {

    /**
     * Shows the per-process results table (one process per line).
     *
     * @param results The processes carrying their computed waiting/turnaround times.
     */
    public void showResults(Process[] results) {
        String message = "";
        for (int i = 0; i < results.length; i++) {
            Process process = results[i];
            message += "Process " + process.getProcessID()
                    + " «...» Arrival Time ► " + process.getArrivalTime()
                    + " «...» Waiting Time ► " + process.getWaitingTime()
                    + " «...» Turnaround Time ► " + process.getTurnaroundTime();
            if (i < results.length - 1) {
                message += "\n";                // put each process on its own line
            }
        }
        JOptionPane.showMessageDialog(null, message,
                "Results", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows the averages screen.
     *
     * @param averageWaitingTime    The average waiting time.
     * @param averageTurnaroundTime The average turnaround time.
     */
    public void showAverages(double averageWaitingTime, double averageTurnaroundTime) {
        // Note: no space after "►" on the waiting line, a space on the turnaround line,
        // to match the sample runs exactly.
        String message = "Average Waiting Time ►" + averageWaitingTime
                       + "\nAverage Turnaround Time ► " + averageTurnaroundTime;
        JOptionPane.showMessageDialog(null, message,
                "Message", JOptionPane.INFORMATION_MESSAGE);
    }
}
