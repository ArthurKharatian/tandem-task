package org.example.task1.comparator;

import java.util.Arrays;
import java.util.Comparator;

public class StringArrayComparator implements Comparator<String[]> {

    private final int columnIndex;
    private final StringComparator stringComparator = new StringComparator();
    public StringArrayComparator(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    @Override
    public int compare(String[] s1, String[] s2) {
        if (Arrays.equals(s1, s2)) return 0;
        int requiredLength = columnIndex;
        if (s1.length < requiredLength && s2.length < requiredLength) return 0;
        if (s1.length < requiredLength) return -1;
        if (s2.length < requiredLength) return 1;

        return stringComparator.compare(s1[columnIndex], s2[columnIndex]);
    }

}
