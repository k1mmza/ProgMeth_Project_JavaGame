package map.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EventManager {

    private List<GameEvent> events;
    private Random random;

    public EventManager() {
        random = new Random();
        events = new ArrayList<>();

        events.add(new HealingEvent());
        events.add(new RiskGoldEvent());
        events.add(new AncientStatueEvent());
        events.add(new FreePotionEvent());
        // add more here
    }

    public GameEvent getRandomEvent() {
        return events.get(random.nextInt(events.size()));
    }
}
