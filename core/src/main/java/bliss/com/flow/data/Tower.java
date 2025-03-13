package bliss.com.flow.data;

import com.badlogic.gdx.utils.Array;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Data model for tower - primary component and building block for Defense of the Elements.
 * This class is designed to be loaded from JSON configuration.
 */
@Data
@NoArgsConstructor
public class Tower {
    // Tower identification
    private String id;
    private String name;
    private String description;
    private int tier; // 0=primal, 1=elemental, 2=fusion, 3=legendary, 4=godlike

    // Element and visual properties
    private ElementType elementType;
    private String iconAsset;
    private String modelAsset;
    private String practicalEffectAsset;

    // Core stats
    private float damage;
    private float attackSpeed;
    private float range;
    private int goldCost;
    private int spiritCost;
    private int maxLevel = 3;

    // Special abilities
    private Array<SpecialEffect> specialEffects = new Array<>();

    // Upgrade properties
    private String[] upgradesTo;
    private Map<String, String> upgradeRequirements = new HashMap<>();
    private Map<String, Float> levelBonuses = new HashMap<>();

    /**
     * Calculate tower's damage per second (DPS)
     */
    public float calculateDPS() {
        return damage * attackSpeed;
    }

    /**
     * Get the stat value for a specific level
     * @param statName
     * @param level
     * @return specific level's stat value
     */
    public float getStatForLevel(String statName, int level) {
        if (level <= 1) {
            switch (statName) {
                case "damage": return damage;
                case "attackSpeed": return attackSpeed;
                case "range": return range;
                default: return 0;
            }
        }

        // Apply level multipliers
        float baseValue = getStatForLevel(statName, 1);
        float multiplier = levelBonuses.getOrDefault(statName, 1.0f);

        // Apply compounding multiplier for each level
        for (int i=1; i<level; i++) {
            baseValue *= multiplier;
        }
        return baseValue;
    }

    /**
     * Special effect by a tower
     */
    @Data
    @NoArgsConstructor
    public static class SpecialEffect {
        private String type;        // stun, slow, burn, chain, pierce
        private float chance;       // 0.0~1.0 probability
        private float value;        // effect strength, duration
        private String description;
    }
}
