package inaugural.soliloquy.common.persistentvaluetypehandlers;

import com.google.gson.Gson;
import soliloquy.specs.common.factories.IMapFactory;
import soliloquy.specs.common.infrastructure.*;

public class PersistentMapHandler extends PersistentHandlerWithTwoGenerics<IMap>
        implements IPersistentMapHandler {
    private final IMapFactory MAP_FACTORY;

    public PersistentMapHandler(IPersistentValuesHandler persistentValuesHandler,
                                IMapFactory mapFactory) {
        super(persistentValuesHandler);
        MAP_FACTORY = mapFactory;
    }

    @Override
    public String getInterfaceName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public IMap getArchetype() {
        // NB: PersistentMapHandler should be selected by the PersistentValuesHandler through
        // specific, manually-defined String pattern recognition, rather than via getArchetype.
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public IMap read(String valuesString) throws IllegalArgumentException {
        MapDTO dto = new Gson().fromJson(valuesString, MapDTO.class);
        IPersistentValueTypeHandler keyHandler =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(dto.keyValueType);
        IPersistentValueTypeHandler valueHandler =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(dto.valueValueType);
        IMap map = MAP_FACTORY.make(PERSISTENT_VALUES_HANDLER.generateArchetype(dto.keyValueType),
                PERSISTENT_VALUES_HANDLER.generateArchetype(dto.valueValueType));
        for (int i = 0; i < dto.keySerializedValues.length; i++) {
            map.put(keyHandler.read(dto.keySerializedValues[i]),
                    valueHandler.read(dto.valueSerializedValues[i]));
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String write(IMap map) {
        if (map == null) {
            throw new IllegalArgumentException("PersistentMapHandler.write: map is null");
        }
        String keyValueType = getProperTypeName(map.getFirstArchetype());
        String valueValueType = getProperTypeName(map.getSecondArchetype());
        IPersistentValueTypeHandler keyHandler =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(keyValueType);
        IPersistentValueTypeHandler valueHandler =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(valueValueType);
        MapDTO dto = new MapDTO();
        dto.keyValueType = keyValueType;
        dto.valueValueType = valueValueType;
        dto.keySerializedValues = new String[map.size()];
        dto.valueSerializedValues = new String[map.size()];
        int indexCounter = 0;
        for(Object entry : map) {
            IPair pair = (IPair) entry;
            dto.keySerializedValues[indexCounter] = keyHandler.write(pair.getItem1());
            dto.valueSerializedValues[indexCounter] = valueHandler.write(pair.getItem2());
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
}
