import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskScheduler {

    // Task 1
    public void scheduleJobsSPT(List<Job> jobs) throws IOException {
        MinPQ<Job> minHeap = new MinPQ<>(JobComparators.byProcessingTime());
        for (Job job : jobs) {
            minHeap.insert(job);
        }

        List<Job> executionOrder = new ArrayList<>();
        int currentTime = 0;
        double totalCompletionTime = 0;

        while (!minHeap.isEmpty()) {
            Job job = minHeap.delMin();
            currentTime += job.getProcessingTime();
            totalCompletionTime += currentTime;
            executionOrder.add(job);
        }

        printResults(executionOrder, totalCompletionTime / jobs.size());
    }

    // Task 2
    public void scheduleJobsPriority(List<Job> jobs) throws IOException {
        MinPQ<Job> priorityQueue = new MinPQ<>(JobComparators.byPriority());
        for (Job job : jobs) {
            priorityQueue.insert(job);
        }

        List<Job> executionOrder = new ArrayList<>();
        int currentTime = 0;
        double totalCompletionTime = 0;

        while (!priorityQueue.isEmpty()) {
            Job job = priorityQueue.delMin();
            currentTime += job.getProcessingTime();
            totalCompletionTime += currentTime;
            executionOrder.add(job);
        }

        printResults(executionOrder, totalCompletionTime / jobs.size());
    }

    // Task 3
    public void scheduleDynamicJobs(List<Job> jobs) throws IOException {
        jobs.sort(Comparator.comparingInt(Job::getArrivalTime));

        MinPQ<Job> readyQueue = new MinPQ<>(JobComparators.byProcessingTime());
        List<Job> executionOrder = new ArrayList<>();
        int currentTime = 0;
        int jobIndex = 0;
        double totalCompletionTime = 0;

        while (jobIndex < jobs.size() || !readyQueue.isEmpty()) {
            while (jobIndex < jobs.size() && jobs.get(jobIndex).getArrivalTime() <= currentTime) {
                readyQueue.insert(jobs.get(jobIndex));
                jobIndex++;
            }

            if (readyQueue.isEmpty() && jobIndex < jobs.size()) {
                currentTime = jobs.get(jobIndex).getArrivalTime();
                continue;
            }

            if (!readyQueue.isEmpty()) {
                Job job = readyQueue.delMin();
                currentTime += job.getProcessingTime();
                int JobCompletionTime = currentTime;
                totalCompletionTime += JobCompletionTime - job.getArrivalTime();
                executionOrder.add(job);
            }
        }

        printResults(executionOrder, totalCompletionTime / jobs.size());
    }

    private void printResults(List<Job> executionOrder, double avgCompletionTime) throws IOException {
        // Print execution order as IDs
        List<Integer> jobIds = new ArrayList<>();
        for (Job job : executionOrder) {
            jobIds.add(job.getId());
        }

        // Write execution order to the file
        System.out.print("Execution order:" + jobIds.toString() + "\n");

        // Write average completion time to the file
        System.out.printf("Average completion time: %.1f%n", avgCompletionTime);
    }

    // Task 1 input reading
    public List<Job> readTask1Input(String filename) throws IOException {
        List<Job> jobs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 2) {
                    jobs.add(new Job(
                            Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1])
                    ));
                }
            }
        }
        return jobs;
    }

    // Task 2 input reading
    public List<Job> readTask2Input(String filename) throws IOException {
        List<Job> jobs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 3) {
                    jobs.add(Job.createWithPriority(
                            Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2])
                    ));
                }
            }
        }
        return jobs;
    }

    // Task 3 input reading
    public List<Job> readTask3Input(String filename) throws IOException {
        List<Job> jobs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("#.*$", "").trim(); // Remove comments
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                if (parts.length == 3) {
                    jobs.add(Job.createWithArrivalTime(
                            Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2])
                    ));
                }
            }
        }
        return jobs;
    }

    public static void main(String[] args) throws IOException {
        TaskScheduler scheduler = new TaskScheduler();

        // Task 1 - SPT Scheduling
        System.out.println("Task 1 Output:");
        List<Job> jobs1 = scheduler.readTask1Input("task1-input.txt");
        scheduler.scheduleJobsSPT(jobs1);


        // Task 2 - Priority-based Scheduling
        System.out.println();
        System.out.println("Task 2 Output:");
        List<Job> jobs2 = scheduler.readTask2Input("task2-input.txt");
        scheduler.scheduleJobsPriority(jobs2);

        // Task 3 - Dynamic Arrival Scheduling
        System.out.println();
        System.out.println("Task 3 Output:");
        List<Job> jobs3 = scheduler.readTask3Input("task3-input.txt");
        scheduler.scheduleDynamicJobs(jobs3);

    }

}

