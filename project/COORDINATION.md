# Project: FCFS Scheduling Algorithm using Queues

### Part 1 — Process class  (`model`)
`Process(String id, int arrivalTime, int burstTime)`, getters for all three,
get/set for `waitingTime` and `turnaroundTime` (Part 3 fills those in), and a
`toString()`. Also create the four package folders with compiling stubs of
every class so the team can build from day one.
Note: `java.lang.Process` exists and we shadow it — never import it.

### Part 2 — QueueImplementation  (`adt`)
`Node<T>` plus `QueueImplementation<T> implements QueueInterface<T>` —
`enqueue`, `dequeue`, `getFront`, `isEmpty`, `clear`. Keep both a front and a
back reference so `enqueue` is O(1); walking to the tail every time is the
classic mark loss. `clear()` resets front, back and the size counter.
`dequeue`/`getFront` on empty throw `IllegalStateException` (that is what the
supplied JavaDoc says — don't swap it for `NoSuchElementException`).
`QueueImplementationTest.java`: self-contained `main` harness, no JUnit, same
style as A3's `ArrayStackTest` — FIFO ordering, empty-then-refill, clear, both
exception paths. Pure logic, no dependency on anyone else's code.

### Part 3 — FCFSAlgorithm  (`algorithm`)
`FCFSAlgorithm(Process[])`, `simulate()`, `getResults()`,
`getAverageWaitingTime()`, `getAverageTurnaroundTime()`.
Constructor sorts by arrival time then enqueues — hand-write an insertion sort,
`java.util.Arrays.sort` is off limits. `simulate()` dequeues one at a time
tracking `currentTime`: `start = max(currentTime, arrival)` (covers the idle-CPU
gap none of the samples exercise), `waiting = start - arrival`,
`completion = start + burst`, `turnaround = completion - arrival`,
`currentTime = completion`. Averages as `double` — the display shows `5.0` and
`21.2`, so no integer division. Guard the empty-array case.
Biggest single piece; build against Part 1 and 2's stubs, don't wait.

### Part 4 — Input collection  (`app`)
`InputCollector` — the welcome `showMessageDialog`, then `showInputDialog` for
the process count and each arrival/burst pair, auto-labelling `P1`…`Pn`,
returning `Process[]`. The marks are in the error handling: non-numeric input
re-prompts instead of throwing `NumberFormatException`, negative arrival and
non-positive burst are rejected, a count of 0 or less is rejected, and Cancel
or closing the window returns `null` — exit cleanly, don't NPE.

### Part 5 — Results display + Main  (`app`)
`ResultDisplay` + `Main`. `Main` is ~15 lines — collect, simulate, show
results, show averages — wrapped in a try/catch that shows an error dialog
rather than a console stack trace.
- title `Welcome to`: `the FCFS (First-Come-First-Serve) Scheduling Simulation!\n\nPress OK to Start`
- prompts `Enter the number of processes ►`, `Enter arrival time for process P1 ►`, `Enter burst time for process P1 ►`
- title `Results`, one `\n`-joined line each: `Process P1 «...» Arrival Time ► 0 «...» Waiting Time ► 0 «...» Turnaround Time ► 4`
- title `Message`: `Average Waiting Time ►5.0\nAverage Turnaround Time ► 9.0`

### Part 6 — QA, packaging, presentation lead  (cross-cutting)
Both sample runs end to end against the numbers below, plus edge cases: one process, two with the same arrival time, a gap in arrivals (0 and 50 — checks
Part 3's idle handling), large bursts. Enforce JavaDoc, inline comments on the
non-obvious lines, naming and indentation across all packages, no debug
printing. Build the JAR, write the readme, assemble the ZIP. Own the deck:
problem, challenges, architecture, who did what, walkthrough, live demo,
testing strategy.

### Test numbers (verified against both sample screenshots)
Run 1 — arrivals 0,1,2,3,4 / bursts 4,3,2,6,5:
waits 0,3,5,6,11 (avg 5.0), turnarounds 4,6,7,12,16 (avg 9.0).
Run 2 — arrivals 0–9 / bursts 4,3,2,6,5,7,5,7,8,10:
waits 0,3,5,6,11,15,21,25,31,38 (avg 15.5),
turnarounds 4,6,7,12,16,22,26,32,39,48 (avg 21.2).
