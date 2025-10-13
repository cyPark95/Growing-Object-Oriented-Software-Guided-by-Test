package learningtest.hamcrest;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MatcherTest {

    @Test
    public void matcher_containsString() {
        String sentence = "Yes, we have no bananas today";

        assertTrue(containsString("bananas").matches(sentence));
        assertFalse(containsString("mangoes").matches(sentence));
    }

    @Test
    public void matcher_withAssertThat() {
        String sentence = "Yes, we have no bananas today";

        assertThat(sentence, containsString("bananas"));
        assertThat(sentence, not(containsString("mangoes")));
    }
}
