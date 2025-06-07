import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Unlike reactive code, virtual threads allow programming in the familiar, sequential thread-per-request style.
 * Writing scalable applications with sequential code is made possible by allowing many virtual threads
 * to share a platform thread (the name given to the conventional threads provided by the operating system).
 * When a virtual thread has to wait or is blocked, the platform thread will execute another virtual thread.
 * That allows us to run several million (!) virtual threads with just a few operating system threads.
 */
public class VirtualThreadsDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        /*
         * Implementation via platform thread: ExecutorService executor = Executors.newFixedThreadPool(100)
         */
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Task> tasks = new ArrayList<>();
            for (int i = 0; i < 1_000; i++) {
                tasks.add(new Task(i));
            }

            long time = System.currentTimeMillis();

            List<Future<Integer>> futures = executor.invokeAll(tasks);

            long sum = 0;
            for (Future<Integer> future : futures) {
                sum += future.get();
            }

            time = System.currentTimeMillis() - time;

            System.out.println("sum = " + sum + "; time = " + time + " ms");
        }



    }

    public static class Task implements Callable<Integer> {
        private final int number;

        public Task(int number) {
            this.number = number;
        }

        @Override
        public Integer call() throws Exception {
            System.out.printf(
                    "Thread %s - Task %d waiting...%n", Thread.currentThread().getName(), number);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.printf(
                        "Thread %s - Task %d canceled.%n", Thread.currentThread().getName(), number);
                return -1;
            }

            System.out.printf(
                    "Thread %s - Task %d finished.%n", Thread.currentThread().getName(), number);
            return ThreadLocalRandom.current().nextInt(100);
        }
    }
}


