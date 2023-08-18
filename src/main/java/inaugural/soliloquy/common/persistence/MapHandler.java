package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeWithTwoGenericParamsHandler;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.shared.Cloneable;

import java.util.Collection;
import java.util.Set;

@SuppressWarnings("rawtypes")
public class MapHandler
        extends AbstractTypeWithTwoGenericParamsHandler<Map>
        implements TypeHandler<Map> {
    private final MapFactory MAP_FACTORY;

    public MapHandler(PersistentValuesHandler persistentValuesHandler,
                      MapFactory mapFactory) {
        super(
                new MapArchetype(),
                persistentValuesHandler,
                archetype1 -> archetype2 -> mapFactory.make(archetype1, archetype2)
        );
        MAP_FACTORY = mapFactory;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public Map read(String valuesString) throws IllegalArgumentException {
        Check.ifNullOrEmpty(valuesString, "valuesString");
        var dto = JSON.fromJson(valuesString, DTO.class);
        var keyHandler = PERSISTENT_VALUES_HANDLER.getTypeHandler(dto.keyType);
        var valueHandler = PERSISTENT_VALUES_HANDLER.getTypeHandler(dto.valueType);
        Map map = MAP_FACTORY.make(PERSISTENT_VALUES_HANDLER.generateArchetype(dto.keyType),
                PERSISTENT_VALUES_HANDLER.generateArchetype(dto.valueType));
        for (int i = 0; i < dto.keys.length; i++) {
            map.put(keyHandler.read(dto.keys[i]),
                    valueHandler.read(dto.values[i]));
        }
        return map;
    }

    @Override
    public String write(Map map) {
        Check.ifNull(map, "map");
        var keyValueType = getProperTypeName(map.firstArchetype());
        var valueValueType = getProperTypeName(map.secondArchetype());
        var keyHandler = PERSISTENT_VALUES_HANDLER.getTypeHandler(keyValueType);
        var valueHandler = PERSISTENT_VALUES_HANDLER.getTypeHandler(valueValueType);
        var dto = new DTO();
        dto.keyType = keyValueType;
        dto.valueType = valueValueType;
        dto.keys = new String[map.size()];
        dto.values = new String[map.size()];
        var indexCounter = 0;
        for (var key : map.keySet()) {
            dto.keys[indexCounter] = keyHandler.write(key);
            dto.values[indexCounter] = valueHandler.write(map.get(key));
            indexCounter++;
        }
        return JSON.toJson(dto);
    }

    private static class DTO {
        String keyType;
        String valueType;
        String[] keys;
        String[] values;
    }

    private static class MapArchetype implements Map {

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(Object o) {
            return false;
        }

        @Override
        public boolean containsValue(Object o) {
            return false;
        }

        @Override
        public Object get(Object o) {
            return null;
        }

        @Override
        public Object put(Object o, Object o2) {
            return null;
        }

        @Override
        public Object remove(Object o) {
            return null;
        }

        @Override
        public void putAll(java.util.Map map) {

        }

        @Override
        public void clear() {

        }

        @Override
        public Set keySet() {
            return null;
        }

        @Override
        public Collection values() {
            return null;
        }

        @Override
        public Set<Entry> entrySet() {
            return null;
        }

        @Override
        public Cloneable makeClone() {
            return null;
        }

        @Override
        public Object firstArchetype() throws IllegalStateException {
            return 0;
        }

        @Override
        public Object secondArchetype() throws IllegalStateException {
            return 0;
        }

        @Override
        public String getInterfaceName() {
            return Map.class.getCanonicalName();
        }
    }
}
