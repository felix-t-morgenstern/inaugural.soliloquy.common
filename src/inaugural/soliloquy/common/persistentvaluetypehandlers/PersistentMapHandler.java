package inaugural.soliloquy.common.persistentvaluetypehandlers;

import soliloquy.common.specs.*;

public class PersistentMapHandler extends PersistentTypeHandler<IMap>
        implements IPersistentValueTypeHandler<IMap> {
    private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER;
    private final IMapFactory MAP_FACTORY;
    private final String DELIMITER_OUTER = "\u001d";
    private final String DELIMITER_PAIR = "\u001e";
    private final String DELIMITER_ITEM = "\u001f";

    public PersistentMapHandler(IPersistentValuesHandler persistentValuesHandler,
                                IMapFactory mapFactory) {
        PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
        MAP_FACTORY = mapFactory;
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
        String[] components = valuesString.split(DELIMITER_OUTER);

        String[] types = components[0].split(DELIMITER_ITEM);
        IPersistentValueTypeHandler keyPersistentValueTypeHandler =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(types[0]);
        IPersistentValueTypeHandler valuePersistentValueTypeHandler =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(types[1]);

        String[] archetypeValueStrings = components[1].split(DELIMITER_ITEM);
        Object keyArchetype = keyPersistentValueTypeHandler.read(archetypeValueStrings[0]);
        Object valueArchetype = valuePersistentValueTypeHandler.read(archetypeValueStrings[1]);

        IMap map = MAP_FACTORY.make(keyArchetype, valueArchetype);

        String[] pairsValueStrings = components[2].split(DELIMITER_PAIR);
        for (String pairValueString : pairsValueStrings) {
            String[] keyAndValueStrings = pairValueString.split(DELIMITER_ITEM);
            String keyValueString = keyAndValueStrings[0];
            String valueValueString = keyAndValueStrings[1];
            map.put(keyPersistentValueTypeHandler.read(keyValueString),
                    valuePersistentValueTypeHandler.read(valueValueString));
        }

        return map;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String write(IMap map) {
        if (map == null) {
            throw new IllegalArgumentException("PersistentMapHandler.write: map is null");
        }

        String keyInternalType = getProperTypeName(map.getFirstArchetype());
        IPersistentValueTypeHandler keyPersistentValueHandler =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(keyInternalType);

        String valueInternalType = getProperTypeName(map.getSecondArchetype());
        IPersistentValueTypeHandler valuePersistentValueHandler =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(valueInternalType);

        StringBuilder writtenValue = new StringBuilder();
        writtenValue.append(keyInternalType);
        writtenValue.append(DELIMITER_ITEM);
        writtenValue.append(valueInternalType);
        writtenValue.append(DELIMITER_OUTER);
        writtenValue.append(keyPersistentValueHandler.write(map.getFirstArchetype()));
        writtenValue.append(DELIMITER_ITEM);
        writtenValue.append(valuePersistentValueHandler.write(map.getSecondArchetype()));
        writtenValue.append(DELIMITER_OUTER);

        boolean firstValue = true;
        for(Object uncastEntry : map){
            if (firstValue) {
                firstValue = false;
            } else {
                writtenValue.append(DELIMITER_PAIR);
            }

            IPair<Object,Object> entry = (IPair<Object,Object>) uncastEntry;
            writtenValue.append(keyPersistentValueHandler.write(entry.getItem1()));
            writtenValue.append(DELIMITER_ITEM);
            writtenValue.append(valuePersistentValueHandler.write(entry.getItem2()));
        }

        return writtenValue.toString();
    }
}
