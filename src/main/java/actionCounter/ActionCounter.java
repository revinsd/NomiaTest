package actionCounter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActionCounter {
    private final Map<Integer, Integer> actionsByTimestamp;

    public ActionCounter() {
        actionsByTimestamp = new ConcurrentHashMap<>();
    }

    public void call(int timestamp) {
        if (timestamp < 0) {
            return;
        }
        actionsByTimestamp.merge(timestamp, 1, (k, v) -> ++v);
    }

    public int getActions(int timestamp) {
        int actionsCount = 0;
        for (int i = timestamp - 299; i <= timestamp; i++) {
            actionsCount += actionsByTimestamp.getOrDefault(i, 0);
        }
        return actionsCount;
    }
}
