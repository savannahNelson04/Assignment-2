public class Job implements Comparable<Job> {
    private int id;
    private int processingTime;
    private int priority;
    private int arrivalTime;
    private boolean hasPriority;

    public Job(int id, int processingTime) {
        this.id = id;
        this.processingTime = processingTime;
        this.priority = 0;
        this.arrivalTime = 0;
        this.hasPriority = false;
    }

    // Static factory methods for Task 2 and 3
    public static Job createWithPriority(int id, int processingTime, int priority) {
        Job job = new Job(id, processingTime);
        job.priority = priority;
        job.hasPriority = true;
        return job;
    }

    public static Job createWithArrivalTime(int id, int processingTime, int arrivalTime) {
        Job job = new Job(id, processingTime);
        job.arrivalTime = arrivalTime;
        return job;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public boolean hasPriority() {
        return hasPriority;
    }

    @Override
    public int compareTo(Job other) {
        return Integer.compare(this.processingTime, other.processingTime);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
