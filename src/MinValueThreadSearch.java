import java.util.Random;

public class MinValueThreadSearch {
    private final int ARRAY_SIZE = 1_000_000_000;
    private final int THREAD_COUNT = 4;

    private final int[] array = new int[ARRAY_SIZE];

    public MinValueThreadSearch() {
        Random random = new Random();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = random.nextInt(0,1000000);
        }

        array[random.nextInt(ARRAY_SIZE)] = -1;
    }

    public void findMinValue() {
        long startTime = System.currentTimeMillis();
        MinValueFinder[] threads = new MinValueFinder[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new MinValueFinder(array, i * (ARRAY_SIZE / THREAD_COUNT), (i + 1) * (ARRAY_SIZE / THREAD_COUNT));
            threads[i].start();
        }

        for (MinValueFinder thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int globalMin = Integer.MAX_VALUE;
        int globalMinIndex = -1;
        for (int i = 0; i < ARRAY_SIZE; i++) {
            if (array[i] == -1) {
                globalMinIndex = i;
                break;
            }
        }

        for (MinValueFinder thread : threads) {
            if (thread.getMinValue() < globalMin) {
                globalMin = thread.getMinValue();
            }
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Index of the minimum value: " + globalMinIndex);
        System.out.println("Minimum value in the array: " + globalMin);
        System.out.println("Program running time: " + (endTime - startTime) + " milliseconds");
    }
}
