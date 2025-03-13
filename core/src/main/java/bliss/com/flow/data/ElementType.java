package bliss.com.flow.data;

import lombok.Getter;

/**
 * Enumeration of all element types in Defense of the Elements.
 * Used for all towers, enemies, and determining damage effect.
 */
@Getter
public enum ElementType {
    NORMAL("Normal", "Balanced against all elements"),
    EARTH("Earth", "Strong vs Nature and Metal, weak vs Air and Energy"),
    NATURE("Nature", "Strong vs Water and Energy, weak vs Earth and Fire"),
    WATER("Water", "Strong vs Fire and Air, weak vs Nature and Metal"),
    FIRE("Fire", "Strong vs Nature and Metal, weak vs Water and Air"),
    AIR("Air", "Strong vs Earth and Fire, weak vs Nature and Metal"),
    ENERGY("Energy", "Strong vs Earth and Air, weak vs Nature and Metal"),
    METAL("Metal", "Strong vs Water and Energy, weak vs Earth and Fire");

    private final String displayName;
    private final String description;

    ElementType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    /**
     * Gets identifier key used for loading associated element type asset
     * @return
     */
    public String getAssetKey() {
        return name().toLowerCase();
    }
}
