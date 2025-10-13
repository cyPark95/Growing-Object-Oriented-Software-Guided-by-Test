package learningtest.junit;

import java.util.Objects;

public class Entry {

    private final String name;
    private final String value;

    public Entry(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Entry entry)) {
            return false;
        }

        return name.equals(entry.name) && value.equals(entry.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
