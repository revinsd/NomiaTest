import actionCounter.ActionCounter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActionCounterTest {
    private ActionCounter actionCounter;
    private final int currentTimestamp = (int) (System.currentTimeMillis() / 1000);

    @BeforeEach
    void init() {
        actionCounter = new ActionCounter();
    }

    @Test
    void call_multipleSameTime() {
        actionCounter.call(currentTimestamp);
        actionCounter.call(currentTimestamp);

        assertContainsTimestamp(currentTimestamp, 2);
    }

    @Test
    void call_multipleDifferentTime() {
        var secondTimestamp = currentTimestamp + 1;
        actionCounter.call(currentTimestamp);
        actionCounter.call(secondTimestamp);

        assertContainsTimestamp(currentTimestamp, 1);
        assertContainsTimestamp(secondTimestamp, 1);
    }

    @Test
    void call_incorrectTimestamp() {
        actionCounter.call(-1);

        assertContainsTimestamp(-1, 0);
    }

    @Test
    void getActions_multipleDifferentTime() {
        actionCounter.call(currentTimestamp);
        actionCounter.call(currentTimestamp - 1);

        var resultCount = actionCounter.getActions(currentTimestamp);
        assertEquals(2, resultCount);
    }

    @Test
    void getActions_multipleSameTime() {
        actionCounter.call(currentTimestamp);
        actionCounter.call(currentTimestamp);

        var resultCount = actionCounter.getActions(currentTimestamp);
        assertEquals(2, resultCount);
    }

    @Test
    void getActions_doesntMatchCriteria() {
        actionCounter.call(currentTimestamp - 300);
        actionCounter.call(currentTimestamp);
        actionCounter.call(currentTimestamp + 1);

        var resultCount = actionCounter.getActions(currentTimestamp);
        assertEquals(1, resultCount);
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    private void assertContainsTimestamp(int expectedTimestamp, int expectedCount) {
        var actionsByTimestampField = ActionCounter.class.getDeclaredField("actionsByTimestamp");
        actionsByTimestampField.setAccessible(true);
        var actionsByTimestamp = ((Map<Integer, Integer>) (actionsByTimestampField.get(actionCounter)));
        assertEquals(expectedCount, actionsByTimestamp.getOrDefault(expectedTimestamp, 0));
    }
}
