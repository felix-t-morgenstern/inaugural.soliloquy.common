package inaugural.soliloquy.common.persistentvaluetypehandlers;

import soliloquy.common.specs.IPair;
import soliloquy.common.specs.IPairFactory;
import soliloquy.common.specs.IPersistentValueTypeHandler;
import soliloquy.common.specs.IPersistentValuesHandler;

public class PersistentPairHandler extends PersistentTypeHandler<IPair>
        implements IPersistentValueTypeHandler<IPair> {
    private final String DELIMITER_OUTER = "\u008b";
    private final String DELIMITER_INNER = "\u008c";

    private final IPairFactory PAIR_FACTORY;
    private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER;

    public PersistentPairHandler(IPairFactory pairFactory,
                                 IPersistentValuesHandler persistentValuesHandler) {
        PAIR_FACTORY = pairFactory;
        PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
    }

    @Override
    public IPair read(String valuesString) throws IllegalArgumentException {
        if (valuesString == null) {
            throw new IllegalArgumentException(
                    "PersistentPairHandler.read: valuesString cannot be null");
        }
        if (valuesString.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentPairHandler.read: valuesString cannot be empty");
        }

        String[] components = valuesString.split(DELIMITER_OUTER);
        if (components.length != 2) {
            throw new IllegalArgumentException(
                    "PersistentPairHandler.read: valuesString must have one and only one outer delimiter");
        }

        String[] item1Components = components[0].split(DELIMITER_INNER);
        if (item1Components.length != 2) {
            throw new IllegalArgumentException(
                    "PersistentPairHandler.read: valuesString must have two and only two inner-delimited items for item1");
        }
        IPersistentValueTypeHandler firstPersistentValueTypeHandler =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(item1Components[0]);
        String item1ValueString = item1Components[1];

        String[] item2Components = components[1].split(DELIMITER_INNER);
        if (item2Components.length != 2) {
            throw new IllegalArgumentException(
                    "PersistentPairHandler.read: valuesString must have two and only two inner-delimited items for item2");
        }
        IPersistentValueTypeHandler secondPersistentValueTypeHandler =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(item2Components[0]);
        String item2ValueString = item2Components[1];

        return PAIR_FACTORY.make(firstPersistentValueTypeHandler.read(item1ValueString),
                secondPersistentValueTypeHandler.read(item2ValueString));
    }

    @SuppressWarnings("unchecked")
    @Override
    public String write(IPair pair) {
        if(pair == null) {
            throw new IllegalArgumentException("PersistentPairHandler.write: pair cannot be null");
        }

        String firstInternalType = getProperTypeName(pair.getFirstArchetype());
        IPersistentValueTypeHandler firstPersistentValueHandler =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(firstInternalType);

        String secondInternalType = getProperTypeName(pair.getSecondArchetype());
        IPersistentValueTypeHandler secondPersistentValueHandler =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(secondInternalType);

        StringBuilder writtenValue = new StringBuilder();
        writtenValue.append(firstInternalType);
        writtenValue.append(DELIMITER_INNER);
        writtenValue.append(firstPersistentValueHandler.write(pair.getItem1()));
        writtenValue.append(DELIMITER_OUTER);
        writtenValue.append(secondInternalType);
        writtenValue.append(DELIMITER_INNER);
        writtenValue.append(secondPersistentValueHandler.write(pair.getItem2()));

        return writtenValue.toString();
    }

    @Override
    public IPair getArchetype() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getInterfaceName() {
        throw new UnsupportedOperationException();
    }
}
