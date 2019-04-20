package inaugural.soliloquy.common.persistentvaluetypehandlers;

import soliloquy.common.specs.*;

public class PersistentCollectionHandler extends PersistentTypeHandler<ICollection>
        implements IPersistentValueTypeHandler<ICollection> {
    private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER;
    private final ICollectionFactory COLLECTION_FACTORY;
    private final String DELIMITER_OUTER = "\u001d";
    private final String DELIMITER_INNER = "\u001e";

    public PersistentCollectionHandler(IPersistentValuesHandler persistentValuesHandler,
                                       ICollectionFactory collectionFactory) {
        PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
        COLLECTION_FACTORY = collectionFactory;
    }

    @Override
    public ICollection getArchetype() {
        // NB: PersistentCollectionHandler should be selected by the PersistentValuesHandler
        // through specific, manually-defined String pattern recognition, rather than via
        // getArchetype.
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ICollection read(String valuesString) throws IllegalArgumentException {
        String[] components = valuesString.split(DELIMITER_OUTER);
        if (components.length != 3) {
            throw new IllegalArgumentException(
                    String.format("PersistentCollectionHandler.read: Invalid string (%s)",
                            valuesString));
        }
        try {
            IPersistentValueTypeHandler persistentValueTypeHandler =
                    PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(components[0]);
            Object archetype = persistentValueTypeHandler.read(components[1]);
            ICollection collection = COLLECTION_FACTORY.make(archetype);
            String[] values = components[2].split(DELIMITER_INNER);
            for (String value : values) {
                collection.add(persistentValueTypeHandler.read(value));
            }
            return collection;
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    String.format("PersistentCollectionHandler.read: Invalid string (%s)",
                            valuesString));
        }
    }

    @Override
    public String write(ICollection collection) {
        if (collection == null) {
            throw new IllegalArgumentException("PersistentCollectionHandler: collection is null");
        }
        String internalType = collection.getArchetype() instanceof ISoliloquyClass ?
                ((ISoliloquyClass) collection.getArchetype()).getInterfaceName() :
                collection.getArchetype().getClass().getCanonicalName();
        IPersistentValueTypeHandler persistentValueTypeHandler =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(internalType);
        StringBuilder writtenValue = new StringBuilder();
        writtenValue.append(internalType);
        writtenValue.append(DELIMITER_OUTER);
        writtenValue.append(persistentValueTypeHandler.write(collection.getArchetype()));
        writtenValue.append(DELIMITER_OUTER);
        boolean firstValue = true;
        for (Object item : collection) {
            if (firstValue) {
                firstValue = false;
            } else {
                writtenValue.append(DELIMITER_INNER);
            }
            writtenValue.append(persistentValueTypeHandler.write(item));
        }
        return writtenValue.toString();
    }
}
