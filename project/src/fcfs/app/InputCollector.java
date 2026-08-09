package fcfs.app;

import fcfs.model.Process;

import javax.swing.JOptionPane;

/**
 * Collects the scheduling input from the user through JOptionPane dialogs:
 * a welcome screen, the number of processes, then an arrival and burst time
 * for each process (auto-labelled P1..Pn).
 *
 * Non-numeric input re-prompts instead of crashing, negative arrival and
 * non-positive burst times are rejected, and a process count of 0 or less is
 * rejected. If the user presses Cancel or closes a dialog, null is returned so
 * the program can exit cleanly.
 */
public class InputCollector {

    /**
     * Runs the full input flow.
     *
     * @return The processes entered by the user, or null if the user cancelled.
     */
    public Process[] collectProcesses() {
        JOptionPane.showMessageDialog(null,
                "the FCFS (First-Come-First-Serve) Scheduling Simulation!\n\nPress OK to Start",
                "Welcome to", JOptionPane.INFORMATION_MESSAGE);

        // Ask for the number of processes, repeating until the value is valid.
        int count = 0;
        while (true) {
            String input = JOptionPane.showInputDialog(null, "Enter the number of processes ►");
            if (input == null) {
                return null;                    // Cancel or window closed
            }
            try {
                count = Integer.parseInt(input.trim());
                if (count <= 0) {
                    showError("The number of processes must be greater than 0.");
                    continue;                   // ask again
                }
                break;                          // valid, move on
            } catch (NumberFormatException e) {
                showError("Please enter a valid whole number.");
            }
        }

        Process[] processes = new Process[count];
        for (int i = 0; i < count; i++) {
            String id = "P" + (i + 1);          // auto-label P1..Pn

            // Ask for the arrival time (cannot be negative).
            int arrival = 0;
            while (true) {
                String input = JOptionPane.showInputDialog(null,
                        "Enter arrival time for process " + id + " ►");
                if (input == null) {
                    return null;
                }
                try {
                    arrival = Integer.parseInt(input.trim());
                    if (arrival < 0) {
                        showError("Arrival time cannot be negative.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    showError("Please enter a valid whole number.");
                }
            }

            // Ask for the burst time (must be greater than 0).
            int burst = 0;
            while (true) {
                String input = JOptionPane.showInputDialog(null,
                        "Enter burst time for process " + id + " ►");
                if (input == null) {
                    return null;
                }
                try {
                    burst = Integer.parseInt(input.trim());
                    if (burst <= 0) {
                        showError("Burst time must be greater than 0.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    showError("Please enter a valid whole number.");
                }
            }

            processes[i] = new Process(id, arrival, burst);
        }
        return processes;
    }

    /**
     * Shows a validation error dialog.
     *
     * @param message The explanation shown to the user.
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }
}
