FCFS Scheduling Simulation

This project implements the First-Come-First-Served (FCFS) CPU scheduling algorithm.

How to run:
1. Open a command prompt in the project folder.
2. Create the list of Java source files:
   dir /s /b src\*.java > sources.txt
3. Compile the Java source files:
   javac -d out @sources.txt
4. Run the program:
   java -cp out fcfs.app.Main

If you are using the JAR file instead, run:
   java -jar fcfs_app.jar

Notes:
- The program uses JOptionPane dialogs for entering process data.
- The simulation calculates start time, completion time, waiting time, turnaround time, and average values.
- If you cancel any input dialog, the program exits cleanly.
