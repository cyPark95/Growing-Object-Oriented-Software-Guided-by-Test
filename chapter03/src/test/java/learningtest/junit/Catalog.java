package learningtest.junit;

import java.util.ArrayList;
import java.util.List;

public class Catalog {

    private final List<Entry> entries = new ArrayList<>();

    public void add(Entry entry) {
        if (containsName(entry.getName())) {
            throw new IllegalArgumentException("learningtest.junit.Entry with name '" + entry.getName() + "' already exists");
        }

        entries.add(entry);
    }

    public boolean contains(Entry entry) {
        return entries.contains(entry);
    }

    public Entry entryFor(final String name) {
        return entries.stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private boolean containsName(String name) {
        return entries.stream().anyMatch(e -> e.getName().equals(name));
    }
}
