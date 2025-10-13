package learningtest.junit;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CatalogTest {

    private final Catalog catalog = new Catalog();
    private final Entry entry = new Entry("fish", "chips");

    @Before
    public void fullTheCatalog() {
        catalog.add(entry);
    }

    @Test
    public void containsAnAddedEntry() {
        assertTrue(catalog.contains(entry));
    }

    @Test
    public void indexesEntriesByName() {
        assertEquals(entry, catalog.entryFor("fish"));
        assertNull(catalog.entryFor("missing name"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotAddTwoEntriesWithTheSameName() {
        catalog.add(new Entry("fish", "peas"));
    }
}
