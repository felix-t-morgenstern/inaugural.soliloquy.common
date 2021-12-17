package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.factories.EntityUuidFactory;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.EntityUuid;

public class EntityUuidHandler extends AbstractTypeHandler<EntityUuid> {
    private final EntityUuidFactory ENTITY_UUID_FACTORY;

    // TODO: Ensure entityUuidFactory is non-null
    public EntityUuidHandler(EntityUuidFactory entityUuidFactory) {
        super(entityUuidFactory.createFromLongs(0,0));
        ENTITY_UUID_FACTORY = entityUuidFactory;
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

    @Override
    public String toString() {
        return TypeHandler.class.getCanonicalName() + "<" +
                EntityUuid.class.getCanonicalName() + ">";
    }

    @Override
    public int hashCode() {
        return (TypeHandler.class.getCanonicalName() + "<" +
                EntityUuid.class.getCanonicalName() + ">").hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof EntityUuidHandler && obj.hashCode() == hashCode();
    }
}
