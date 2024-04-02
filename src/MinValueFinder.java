public class MinValueFinder extends Thread {
    private final int[] array;
    private final int start;
    private final int end;
    private int minValue = Integer.MAX_VALUE;

    public MinValueFinder(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        minValue = findMinValueQuickSort(array, start, end - 1); // end - 1, because QuickSort uses end as the last index
    }

    private int findMinValueQuickSort(int[] array, int low, int high) {
        if (low == high) {
            return array[low];
        }

        int mid = (low + high) / 2;
        int leftMin = findMinValueQuickSort(array, low, mid);
        int rightMin = findMinValueQuickSort(array, mid + 1, high);

        return Math.min(leftMin, rightMin);
    }

    public int getMinValue() {
        return minValue;
    }
}
