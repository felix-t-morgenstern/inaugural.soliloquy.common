package inaugural.soliloquy.common.persistence;

import com.google.gson.Gson;
import inaugural.soliloquy.tools.persistence.AbstractTypeWithTwoGenericParamsHandler;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.List;
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
        MapDTO dto = new Gson().fromJson(valuesString, MapDTO.class);
        TypeHandler keyHandler = PERSISTENT_VALUES_HANDLER.getTypeHandler(dto.keyValueType);
        TypeHandler valueHandler = PERSISTENT_VALUES_HANDLER.getTypeHandler(dto.valueValueType);
        Map map = MAP_FACTORY.make(PERSISTENT_VALUES_HANDLER.generateArchetype(dto.keyValueType),
                PERSISTENT_VALUES_HANDLER.generateArchetype(dto.valueValueType));
        for (int i = 0; i < dto.keySerializedValues.length; i++) {
            map.put(keyHandler.read(dto.keySerializedValues[i]),
                    valueHandler.read(dto.valueSerializedValues[i]));
        }
        return map;
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @Override
    public String write(Map map) {
        if (map == null) {
            throw new IllegalArgumentException("MapHandler.write: map is null");
        }
        String keyValueType = getProperTypeName(map.getFirstArchetype());
        String valueValueType = getProperTypeName(map.getSecondArchetype());
        TypeHandler keyHandler = PERSISTENT_VALUES_HANDLER.getTypeHandler(keyValueType);
        TypeHandler valueHandler = PERSISTENT_VALUES_HANDLER.getTypeHandler(valueValueType);
        MapDTO dto = new MapDTO();
        dto.keyValueType = keyValueType;
        dto.valueValueType = valueValueType;
        dto.keySerializedValues = new String[map.size()];
        dto.valueSerializedValues = new String[map.size()];
        int indexCounter = 0;
        for(Object key : map.keySet()) {
            dto.keySerializedValues[indexCounter] = keyHandler.write(key);
            dto.valueSerializedValues[indexCounter] = valueHandler.write(map.get(key));
            indexCounter++;
        }
        return new Gson().toJson(dto);
    }

    private static class MapDTO {
        String keyValueType;
        String valueValueType;
        String[] keySerializedValues;
        String[] valueSerializedValues;
    }

    @Override
    public String toString() {
        return MapHandler.class.getCanonicalName();
    }

    @Override
    public int hashCode() {
        return MapHandler.class.getCanonicalName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MapHandler && obj.hashCode() == hashCode();
    }

    private static class MapArchetype implements Map {

        @Override
        public List getValuesList() {
            return null;
        }

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
        public Object getFirstArchetype() throws IllegalStateException {
            return 0;
        }

        @Override
        public Object getSecondArchetype() throws IllegalStateException {
            return 0;
        }

        @Override
        public String getInterfaceName() {
            return Map.class.getCanonicalName();
        }
    }
}
