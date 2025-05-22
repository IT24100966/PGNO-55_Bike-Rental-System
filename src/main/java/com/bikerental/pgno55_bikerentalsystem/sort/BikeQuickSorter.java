package com.bikerental.pgno55_bikerentalsystem.sort;

import com.bikerental.pgno55_bikerentalsystem.model.Bike;

/**
 * BikeQuickSorter
 *
 * This class implements Quick Sort using the partition method taught in lecture.
 * It sorts bikes based on availability: available bikes (true) come before unavailable (false).
 */
public class BikeQuickSorter {

    public static void quickSort(Bike[] bikes, int low, int high) {
        if (low < high) {
            int pi = partition(bikes, low, high);
            quickSort(bikes, low, pi - 1);
            quickSort(bikes, pi + 1, high);
        }
    }

    private static int partition(Bike[] bikes, int low, int high) {
        Bike pivot = bikes[high]; // A[r]
        int pivotValue = pivot.isAvailable() ? 0 : 1; // ✅ Available bikes should come first

        int i = low - 1;

        for (int j = low; j < high; j++) {
            int currentValue = bikes[j].isAvailable() ? 0 : 1;

            if (currentValue <= pivotValue) {
                i++;
                swap(bikes, i, j);
            }
        }

        swap(bikes, i + 1, high);
        return i + 1;
    }

    private static void swap(Bike[] bikes, int i, int j) {
        Bike temp = bikes[i];
        bikes[i] = bikes[j];
        bikes[j] = temp;
    }
}
