package bliss.com.flow.data;

import com.badlogic.gdx.utils.Array;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data model for unit - minion, elite, boss.
 * This class is designed to be loaded from JSON configuration.
 */
@Data
@NoArgsConstructor
public class Unit {
    // Tower identification
    private String id;
    private String name;
    private String description;

    // Type and category
    private ElementType elementType;
    private boolean isBoss;
    private String category = "minion";         // minion, elite, boss

    // Visual properties
    private String spriteAsset;
    private String deathEffectAsset;

    // Core stats
    private float health;
    private float speed;
    private float defense;                      // flat damage reduction
    private float armor;                        // percentage damage reduction

    // Rewards
    private int goldValue;
    private int spiritValue;

    // Special abilities
    private Array<SpecialAbility> abilities = new Array<>();

    /**
     * Estimates total hits for specific tower to defeat the unit,
     * accounting health, defense, armor, and elemental type interactions.
     * Primarily used for AI targeting decisions and tower efficiency calculations.
     *
     * @return
     */
    public float estimateHitsToDefeat(Tower tower) {
        if (tower == null) {
            return health;
        }

        float towerDamage = tower.getDamage();

        // Apply elemental effect
        float elementalMultiplier = 1.0f;
        if (tower.getElementType() != null) {
            // add in with relation class
        }

        float effectiveDamage = towerDamage * elementalMultiplier;
        float damageAfterDefense = Math.max(1, effectiveDamage - defense);

        float armorReduction = armor / (armor + 100f);
        float finalDamage = damageAfterDefense * (1 - armorReduction);

        return health / finalDamage;
    }

    @Data
    @NoArgsConstructor
    public static class SpecialAbility {
        private String type;                    // heal, spawn, buff
        private float triggerThreshold;         // when to trigger
        private float cooldown;                 // cooldown between usage
        private float value;                    // effect strength
        private String targetType = "self";     // self, allies
        private float range;                    // range of effect if applicable
    }
}
