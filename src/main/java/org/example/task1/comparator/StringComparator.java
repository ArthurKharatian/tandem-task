package org.example.task1.comparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class StringComparator implements Comparator<String> {

    @Override
    public int compare(String s1, String s2) {
        if (s1 == null && s2 == null) return 0;
        if (s1 == null) return -1;
        if (s2 == null) return 1;
        if (s1.equals(s2)) return 0;
        if (s1.length() == 0) return -1;
        if (s2.length() == 0) return 1;

        Iterator<String> words1 = splitString(s1).iterator();
        Iterator<String> words2 = splitString(s2).iterator();
        while (words1.hasNext() && words2.hasNext()) {
            String word1 = words1.next();
            String word2 = words2.next();

            if (!word1.equals(word2))
                return compareSubString(word1, word2);
        }

        if (words1.hasNext()) return 1;
        if (words2.hasNext()) return -1;

        return 0;
    }

    private static Iterable<String> splitString(String s) {
        List<String> result = new ArrayList<>();
        s = s.replaceAll("\\D", " ");

        for (String split : s.split(" ")) {
            split = split.trim();
            if (!split.isEmpty()) {
                result.add(split);
            }
        }

        return result;
    }

    private static int compareSubString(String s1, String s2) {
        int i1 = Integer.parseInt(s1);
        int i2 = Integer.parseInt(s2);
        return Integer.compare(i1, i2);
    }

}
