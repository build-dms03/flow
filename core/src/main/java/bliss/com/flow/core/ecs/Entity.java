package bliss.com.flow.core.ecs;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import lombok.Getter;

import java.util.UUID;

/**
 * Base entity class for the ECS system.
 * An entity is essentially just an ID with associated components.
 */
public class Entity {
    @Getter
    private final String id;

    @Getter
    private boolean active = true;

    private final ObjectMap<Class<? extends Component>, Component> components = new ObjectMap<>();
    private final Array<Component> componentArray = new Array<>();

    /**
     * Create a new entity with a random UUID
     */
    public Entity() {
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Create a new entity with the specified ID
     * @param id
     */
    public Entity(String id) {
        this.id = id;
    }

    public Entity add(Component component) {
        Class<? extends Component> componentClass = component.getClass();

        // remove any existing component of this type

        // add the component

        // initialize the component
    }

    public Entity remove(Class<? extends Component> componentClass) {
        Component component = components.get(componentClass);
        if (component != null) {

        }
    }

    public boolean has(Class<? extends Component> componentClass) {
        return components.containsKey(componentClass);
    }

    public <T extends Component> T get(Class<T> componentClass) {
        return (T) components.get(componentClass);
    }

    public Array<Component> getComponents() {
        return componentArray;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public void update(float deltaTime) {
        if (!active) {
            return;
        }

        for (Component component : componentArray) {

        }
    }

    public void dispose() {

    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Entity other = (Entity) object;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Entity(" + id + ", components=" + componentArray.size + ")";
    }
}
