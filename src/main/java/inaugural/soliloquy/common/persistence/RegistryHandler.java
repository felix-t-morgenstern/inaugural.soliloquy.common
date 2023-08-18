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

    @Override
    public Registry read(String valuesString) throws IllegalArgumentException {
        Check.ifNullOrEmpty(valuesString, "valuesString");
        var dto = JSON.fromJson(valuesString, DTO.class);
        var handler = PERSISTENT_VALUES_HANDLER.getTypeHandler(dto.type);
        var registry = REGISTRY_FACTORY.make(PERSISTENT_VALUES_HANDLER.generateArchetype(dto.type));
        for (var serializedValue : dto.values) {
            registry.add((HasId) handler.read(serializedValue));
        }
        return registry;
    }

    @Override
    public String write(Registry registry) {
        Check.ifNull(registry, "registry");
        var internalType = getProperTypeName(registry.archetype());
        var handler = PERSISTENT_VALUES_HANDLER.getTypeHandler(internalType);
        var dto = new DTO();
        dto.type = internalType;
        dto.values = new String[registry.size()];
        var index = 0;
        for (var item : registry) {
            dto.values[index++] = handler.write(item);
        }
        return JSON.toJson(dto);
    }

    private static class DTO {
        String type;
        String[] values;
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
        public Object archetype() {
            return 0;
        }

        @Override
        public String getInterfaceName() {
            return Registry.class.getCanonicalName();
        }
    }
}
