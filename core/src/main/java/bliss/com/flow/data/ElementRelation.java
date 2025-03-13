package bliss.com.flow.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines the effectiveness relationships between elements.
 * This class is designed to be loaded from JSON configuration.
 */
@Data
@NoArgsConstructor
public class ElementRelation {
    private static final float DEFAULT_EFFECTIVENESS = 1.0f;
    private static ElementRelation relation;

    private Map<ElementType, Map<ElementType, Float>> effectiveness = new HashMap<>();

    public static ElementRelation getRelation() {
        if (relation == null) {
            relation  =new ElementRelation();
            relation.initializeDefaults();
        }
        return relation;
    }

    private void initializeDefaults() {
        for (ElementType attacker : ElementType.values()) {
            Map<ElementType, Float> relations = new HashMap<>();
            for (ElementType defender : ElementType.values()) {
                relations.put(defender, DEFAULT_EFFECTIVENESS);
            }
            effectiveness.put(attacker, relations);
        }
    }

    public float getEffectiveness(ElementType attacker, ElementType defender) {
        Map<ElementType, Float> attackMap = effectiveness.get(attacker);
        if (attackMap == null) {
            return DEFAULT_EFFECTIVENESS;
        }
        Float value = attackMap.get(defender);
        return value != null ? value : DEFAULT_EFFECTIVENESS;
    }

    public void setEffectiveness(ElementType attacker, ElementType defender, float value) {
        effectiveness.computeIfAbsent(attacker, k -> new HashMap<>())
            .put(defender, value);
    }

    public void configureStandardEffectiveness() {
        // Normal element (neutral to all)
        for (ElementType type : ElementType.values()) {
            setEffectiveness(ElementType.NORMAL, type, 1.0f);
            setEffectiveness(type, ElementType.NORMAL, 1.0f);
        }

        // Earth
        setEffectiveness(ElementType.EARTH, ElementType.NATURE, 1.5f);
        setEffectiveness(ElementType.EARTH, ElementType.METAL, 1.5f);
        setEffectiveness(ElementType.EARTH, ElementType.AIR, 0.75f);
        setEffectiveness(ElementType.EARTH, ElementType.ENERGY, 0.75f);

        // Nature
        setEffectiveness(ElementType.NATURE, ElementType.WATER, 1.5f);
        setEffectiveness(ElementType.NATURE, ElementType.ENERGY, 1.5f);
        setEffectiveness(ElementType.NATURE, ElementType.EARTH, 0.75f);
        setEffectiveness(ElementType.NATURE, ElementType.FIRE, 0.75f);

        // Water
        setEffectiveness(ElementType.WATER, ElementType.FIRE, 1.5f);
        setEffectiveness(ElementType.WATER, ElementType.AIR, 1.5f);
        setEffectiveness(ElementType.WATER, ElementType.NATURE, 0.75f);
        setEffectiveness(ElementType.WATER, ElementType.METAL, 0.75f);

        // Fire
        setEffectiveness(ElementType.FIRE, ElementType.NATURE, 1.5f);
        setEffectiveness(ElementType.FIRE, ElementType.METAL, 1.5f);
        setEffectiveness(ElementType.FIRE, ElementType.WATER, 0.75f);
        setEffectiveness(ElementType.FIRE, ElementType.AIR, 0.75f);

        // Air
        setEffectiveness(ElementType.AIR, ElementType.EARTH, 1.5f);
        setEffectiveness(ElementType.AIR, ElementType.FIRE, 1.5f);
        setEffectiveness(ElementType.AIR, ElementType.WATER, 0.75f);
        setEffectiveness(ElementType.AIR, ElementType.ENERGY, 0.75f);

        // Energy
        setEffectiveness(ElementType.ENERGY, ElementType.EARTH, 1.5f);
        setEffectiveness(ElementType.ENERGY, ElementType.AIR, 1.5f);
        setEffectiveness(ElementType.ENERGY, ElementType.NATURE, 0.75f);
        setEffectiveness(ElementType.ENERGY, ElementType.METAL, 0.75f);

        // Metal
        setEffectiveness(ElementType.METAL, ElementType.WATER, 1.5f);
        setEffectiveness(ElementType.METAL, ElementType.ENERGY, 1.5f);
        setEffectiveness(ElementType.METAL, ElementType.EARTH, 0.75f);
        setEffectiveness(ElementType.METAL, ElementType.FIRE, 0.75f);
    }

    public boolean loadFromFile(FileHandle file) {
        try {
            Json json = new Json();
            ElementRelation loaded = json.fromJson(ElementRelation.class, file);

            if (loaded != null && loaded.effectiveness != null) {
                this.effectiveness = loaded.effectiveness;
                return true;
            }
            return false;
        } catch (Exception e) {
            Gdx.app.error("ElementRelation", "Error loading from file: " + e.getMessage());
            return false;
        }
    }

    public boolean saveToFile(FileHandle file) {
        try {
            Json json = new Json();
            file.writeString(json.prettyPrint(this), false);
            return true;
        } catch (Exception e) {
            Gdx.app.error("ElementRelation", "Error saving to file: " + e.getMessage());
            return false;
        }
    }
}
