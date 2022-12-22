package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeWithOneGenericParamHandler;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.shared.HasId;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("rawtypes")
public class RegistryHandler
        extends AbstractTypeWithOneGenericParamHandler<Registry>
        implements TypeHandler<Registry> {
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER;
    private final RegistryFactory REGISTRY_FACTORY;

    public RegistryHandler(PersistentValuesHandler persistentValuesHandler,
                           RegistryFactory registryFactory) {
        super(
                new RegistryArchetype(),
                persistentValuesHandler,
                archetype -> registryFactory.make((HasId) archetype)
        );
        PERSISTENT_VALUES_HANDLER =
                Check.ifNull(persistentValuesHandler, "persistentValuesHandler");
        REGISTRY_FACTORY = Check.ifNull(registryFactory, "registryFactory");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Registry read(String valuesString) throws IllegalArgumentException {
        Check.ifNullOrEmpty(valuesString, "valuesString");
        RegistryDTO dto = JSON.fromJson(valuesString, RegistryDTO.class);
        TypeHandler handler = PERSISTENT_VALUES_HANDLER.getTypeHandler(dto.typeName);
        Registry registry = REGISTRY_FACTORY
                .make(PERSISTENT_VALUES_HANDLER.generateArchetype(dto.typeName));
        for (String serializedValue : dto.serializedValues) {
            registry.add((HasId) handler.read(serializedValue));
        }
        return registry;
    }

    @Override
    public String write(Registry registry) {
        Check.ifNull(registry, "registry");
        String internalType = getProperTypeName(registry.getArchetype());
        TypeHandler handler = PERSISTENT_VALUES_HANDLER.getTypeHandler(internalType);
        RegistryDTO dto = new RegistryDTO();
        dto.typeName = internalType;
        dto.serializedValues = new String[registry.size()];
        int index = 0;
        for (Object item : registry) {
            //noinspection unchecked
            dto.serializedValues[index++] = handler.write(item);
        }
        return JSON.toJson(dto);
    }

    private static class RegistryDTO {
        String typeName;
        String[] serializedValues;
    }

    private static class RegistryArchetype implements Registry {

        @Override
        public void add(HasId hasId) throws IllegalArgumentException {

        }

        @Override
        public void addAll(Object[] objects) throws IllegalArgumentException {

        }

        @Override
        public void addAll(HasId[] hasIds) throws IllegalArgumentException {

        }

        @Override
        public void addAll(Collection collection) throws IllegalArgumentException {

        }

        @Override
        public boolean contains(String s) throws IllegalArgumentException {
            return false;
        }

        @Override
        public boolean contains(HasId hasId) throws IllegalArgumentException {
            return false;
        }

        @Override
        public HasId get(String s) throws IllegalArgumentException {
            return null;
        }

        @Override
        public boolean remove(String s) throws IllegalArgumentException {
            return false;
        }

        @Override
        public boolean remove(HasId hasId) throws IllegalArgumentException {
            return false;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public void clear() {

        }

        @Override
        public List representation() {
            return null;
        }

        @Override
        public Iterator iterator() {
            return null;
        }

        @Override
        public Object getArchetype() {
            return 0;
        }

        @Override
        public String getInterfaceName() {
            return Registry.class.getCanonicalName();
        }
    }
}
