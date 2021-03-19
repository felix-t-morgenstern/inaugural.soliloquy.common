package inaugural.soliloquy.common.persistence;

import com.google.gson.Gson;
import inaugural.soliloquy.tools.persistence.PersistentDataStructureWithTwoGenericParamsHandler;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.persistence.PersistentMapHandler;
import soliloquy.specs.common.persistence.PersistentValueTypeHandler;
import soliloquy.specs.common.persistence.PersistentValuesHandler;

@SuppressWarnings("rawtypes")
public class PersistentMapHandlerImpl
        extends PersistentDataStructureWithTwoGenericParamsHandler<Map>
        implements PersistentMapHandler {
    private final MapFactory MAP_FACTORY;

    public PersistentMapHandlerImpl(PersistentValuesHandler persistentValuesHandler,
                                    MapFactory mapFactory) {
        super(persistentValuesHandler);
        MAP_FACTORY = mapFactory;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public Map read(String valuesString) throws IllegalArgumentException {
        MapDTO dto = new Gson().fromJson(valuesString, MapDTO.class);
        PersistentValueTypeHandler keyHandler =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(dto.keyValueType);
        PersistentValueTypeHandler valueHandler =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(dto.valueValueType);
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
            throw new IllegalArgumentException("PersistentMapHandlerImpl.write: map is null");
        }
        String keyValueType = getProperTypeName(map.getFirstArchetype());
        String valueValueType = getProperTypeName(map.getSecondArchetype());
        PersistentValueTypeHandler keyHandler =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(keyValueType);
        PersistentValueTypeHandler valueHandler =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(valueValueType);
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

    @Override
    protected Object generateTypeFromFactory(Object archetype1, Object archetype2) {
        return MAP_FACTORY.make(archetype1,archetype2);
    }

    private class MapDTO {
        String keyValueType;
        String valueValueType;
        String[] keySerializedValues;
        String[] valueSerializedValues;
    }

    @Override
    public String toString() {
        return PersistentMapHandlerImpl.class.getCanonicalName();
    }

    @Override
    public int hashCode() {
        return PersistentMapHandlerImpl.class.getCanonicalName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PersistentMapHandlerImpl && obj.hashCode() == hashCode();
    }
}
