package inaugural.soliloquy.common.persistence;

import com.google.gson.Gson;
import inaugural.soliloquy.tools.persistence.PersistentDataStructureWithOneGenericParamHandler;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.persistence.PersistentRegistryHandler;
import soliloquy.specs.common.persistence.PersistentValueTypeHandler;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.shared.HasId;

@SuppressWarnings("rawtypes")
public class PersistentRegistryHandlerImpl
        extends PersistentDataStructureWithOneGenericParamHandler<Registry>
        implements PersistentRegistryHandler {
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER;
    private final RegistryFactory REGISTRY_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public PersistentRegistryHandlerImpl(PersistentValuesHandler persistentValuesHandler,
                                         RegistryFactory registryFactory) {
        if (persistentValuesHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentRegistryHandlerImpl: persistentValuesHandler must be non-null");
        }
        PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
        if (registryFactory == null) {
            throw new IllegalArgumentException(
                    "PersistentRegistryHandlerImpl: registryFactory must be non-null");
        }
        REGISTRY_FACTORY = registryFactory;
    }

    @Override
    public Registry generateArchetype(String valueType) throws IllegalArgumentException {
        String innerType = getInnerType(valueType, Registry.class);

        return REGISTRY_FACTORY.make(
                (HasId) PERSISTENT_VALUES_HANDLER.generateArchetype(innerType));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Registry read(String valuesString) throws IllegalArgumentException {
        if (valuesString == null) {
            throw new IllegalArgumentException(
                    "PersistentRegistryHandlerImpl.read: valuesString must be non-null");
        }
        if (valuesString.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentRegistryHandlerImpl.read: valuesString must be non-empty");
        }
        RegistryDTO dto = new Gson().fromJson(valuesString, RegistryDTO.class);
        PersistentValueTypeHandler handler =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(dto.typeName);
        Registry registry =
                REGISTRY_FACTORY.make(
                        (HasId) PERSISTENT_VALUES_HANDLER.generateArchetype(dto.typeName));
        for (String serializedValue : dto.serializedValues) {
            registry.add((HasId)handler.read(serializedValue));
        }
        return registry;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String write(Registry registry) {
        if (registry == null) {
            throw new IllegalArgumentException(
                    "PersistentRegistryHandlerImpl.write: registry is null");
        }
        String internalType = getProperTypeName(registry.getArchetype());
        PersistentValueTypeHandler handler =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(internalType);
        RegistryDTO dto = new RegistryDTO();
        dto.typeName = internalType;
        dto.serializedValues = new String[registry.size()];
        int index = 0;
        for (Object item : registry) {
            dto.serializedValues[index++] = handler.write(item);
        }
        return new Gson().toJson(dto);
    }

    @Override
    public String toString() {
        return PersistentRegistryHandlerImpl.class.getCanonicalName();
    }

    @Override
    public int hashCode() {
        return PersistentRegistryHandlerImpl.class.getCanonicalName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PersistentRegistryHandlerImpl && obj.hashCode() == hashCode();
    }

    private class RegistryDTO {
        String typeName;
        String[] serializedValues;
    }
}
