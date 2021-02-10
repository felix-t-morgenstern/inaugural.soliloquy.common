package inaugural.soliloquy.common.persistentvaluetypehandlers;

import inaugural.soliloquy.tools.persistentvaluetypehandlers.PersistentTypeHandler;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.factories.EntityUuidFactory;
import soliloquy.specs.common.valueobjects.EntityUuid;

public class PersistentEntityUuidHandler extends PersistentTypeHandler<EntityUuid>
        implements PersistentValueTypeHandler<EntityUuid> {
    private final EntityUuidFactory ENTITY_UUID_FACTORY;
    private final EntityUuid ARCHETYPE;

    public PersistentEntityUuidHandler(EntityUuidFactory entityUuidFactory) {
        ENTITY_UUID_FACTORY = entityUuidFactory;
        ARCHETYPE = entityUuidFactory.createFromLongs(0,0);
    }

    @Override
    public EntityUuid getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public EntityUuid read(String serializedValue) throws IllegalArgumentException {
        if (serializedValue == null || serializedValue.equals("")) {
            return null;
        } else {
            return ENTITY_UUID_FACTORY.createFromString(serializedValue);
        }
    }

    @Override
    public String write(EntityUuid entityUuid) {
        if (entityUuid == null) {
            return "";
        } else {
            return entityUuid.toString();
        }
    }
}
