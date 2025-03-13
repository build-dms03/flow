package bliss.com.flow.data;

import com.badlogic.gdx.utils.Array;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Data model for Defense of the Elements waves.
 * This class is designed to be loaded from JSON configuration.
 */
@Data
@NoArgsConstructor
public class Wave {
    // Wave identification
    private int waveNumber;
    private String name;
    private String description;

    // Timing properties
    private float timeBetweenSpawns = 1.0f;
    private float timeBeforeNextWave = 5.0f;

    // Spawn properties
    private Array<SpawnGroup> spawnGroups = new Array<>();

    // Rewards
    private int goldReward;
    private int spiritReward;
    private String crystalReward; // ElementType name as string

    // Special wave flags
    private boolean isBossWave;
    private boolean hasSpecialEvent;
    private String specialEventType;
    private String specialEventData;

    /**
     * Get the total number of units to clear in this wave
     * @return
     */
    public int getTotalUnitCount() {
        int total = 0;
        for (SpawnGroup group : spawnGroups) {
            total += group.count;
        }
        return total;
    }

    /**
     * Check if wave has units of a specific element type
     * @param elementType
     * @param enemyRegistry
     * @return
     */
    public boolean hasElementType(ElementType elementType, Map<String, Unit> enemyRegistry) {
        for (SpawnGroup group : spawnGroups) {
            Unit unit = enemyRegistry.get(group.enemyId);
            return unit != null && unit.getElementType() == elementType;
        }
        return false;
    }

    /**
     * Estimate the difficulty of this wave
     * @return
     */
    public float estimateDifficulty() {
        // combine wave unit count, types, and states
        // TODO: update placeholder implementations
        return (float) waveNumber + (isBossWave ? 10 : 0);
    }

    public static class SpawnGroup {
        private String enemyId;
        private int count;
        private float delayBeforeGroup;
        private float spawnRate = 1.0f;     // units to clear per second
        private SpawnPattern spawnPattern = SpawnPattern.SEQUENTIAL;
        private String pathId;
    }

    public enum SpawnPattern {
        SEQUENTIAL,
        BURST,
        TIMED,
        ACCELERATED,
        DECELERATED
    }
}
