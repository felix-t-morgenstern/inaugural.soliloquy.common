package inaugural.soliloquy.common.persistentvaluetypehandlers;

import soliloquy.common.specs.*;

public class PersistentCollectionHandler extends PersistentTypeHandler<ICollection>
        implements IPersistentValueTypeHandler<ICollection> {
    private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER;
    private final ICollectionFactory COLLECTION_FACTORY;
    private final String DELIMITER_OUTER = "\u0091";
    private final String DELIMITER_INNER = "\u0092";

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
        // NB: A length of 2 implies that the Collection is empty; this is valid.
        if (components.length < 2 || components.length >= 4) {
            throw new IllegalArgumentException(
                    String.format("PersistentCollectionHandler.read: Invalid string (%s)",
                            valuesString));
        }
        try {
            IPersistentValueTypeHandler persistentValueTypeHandler =
                    PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(components[0]);
            Object archetype = persistentValueTypeHandler.read(components[1]);
            ICollection collection = COLLECTION_FACTORY.make(archetype);
            // NB: A length of 2 implies that the Collection is empty; this is valid.
            if (components.length > 2) {
                String[] values = components[2].split(DELIMITER_INNER);
                for (String value : values) {
                    collection.add(persistentValueTypeHandler.read(value));
                }
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
            throw new IllegalArgumentException(
                    "PersistentCollectionHandler.write: collection is null");
        }
        String internalType = getProperTypeName(collection.getArchetype());
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
