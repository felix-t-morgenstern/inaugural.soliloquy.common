package inaugural.soliloquy.common.persistentvaluetypehandlers;

import soliloquy.specs.common.entities.IPersistentValueTypeHandler;
import soliloquy.specs.common.factories.IEntityUuidFactory;
import soliloquy.specs.common.valueobjects.IEntityUuid;

public class PersistentEntityUuidHandler extends PersistentTypeHandler<IEntityUuid>
        implements IPersistentValueTypeHandler<IEntityUuid> {
    private final IEntityUuidFactory ENTITY_UUID_FACTORY;
    private final IEntityUuid ARCHETYPE;

    public PersistentEntityUuidHandler(IEntityUuidFactory entityUuidFactory) {
        ENTITY_UUID_FACTORY = entityUuidFactory;
        ARCHETYPE = entityUuidFactory.createFromLongs(0,0);
    }

    @Override
    public IEntityUuid getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public IEntityUuid read(String valueString) throws IllegalArgumentException {
        if (valueString == null || valueString.equals("")) {
            return null;
        } else {
            return ENTITY_UUID_FACTORY.createFromString(valueString);
        }
    }

    @Override
    public String write(IEntityUuid entityUuid) {
        if (entityUuid == null) {
            return "";
        } else {
            return entityUuid.toString();
        }
    }
}
