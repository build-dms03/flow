package bliss.com.flow.core.ecs;

import lombok.Getter;
import lombok.Setter;

/**
 * Base component class for the ECS system.
 * Components store data and behaviors for entries
 */
public abstract class Component {
    @Getter @Setter
    protected Entity entity;

    @Getter
    protected boolean active = true;

    protected void initialize() {
        // default, does nothing
    }

    public void update(float deltaTime) {
        // default, does nothing
    }

    public void dispose() {

    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        if (entity == null) return null;
        return entity.get(componentClass);
    }

    public boolean hasComponent(Class<? extends Component> componentClass) {
        if (entity == null) return false;
        return entity.has(componentClass);
    }
}
