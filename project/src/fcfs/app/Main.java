package fcfs.app;

import fcfs.algorithm.FCFSAlgorithm;
import fcfs.model.Process;

import javax.swing.JOptionPane;

/**
 * Entry point for the FCFS scheduling simulation.
 *
 * This class wires together the input collection, simulation, and result
 * display.
 */
public class Main {

    public static void main(String[] args) {
        InputCollector inputCollector = new InputCollector();
        Process[] processes = inputCollector.collectProcesses();

        if (processes == null) {
            return; // user cancelled input, exit cleanly
        }

        try {
            FCFSAlgorithm scheduler = new FCFSAlgorithm();
            Process[] results = scheduler.simulate(processes);

            ResultDisplay resultDisplay = new ResultDisplay();
            resultDisplay.showResults(results);

            double averageWaitingTime = scheduler.calculateAverageWaitingTime();
            double averageTurnaroundTime = scheduler.calculateAverageTurnaroundTime();
            resultDisplay.showAverages(averageWaitingTime, averageTurnaroundTime);
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(null,
                    exception.getMessage(),
                    "Simulation Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
