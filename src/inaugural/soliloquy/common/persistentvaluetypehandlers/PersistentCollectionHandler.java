package inaugural.soliloquy.common.persistentvaluetypehandlers;

import com.google.gson.Gson;
import soliloquy.specs.common.entities.IPersistentCollectionHandler;
import soliloquy.specs.common.entities.IPersistentValueTypeHandler;
import soliloquy.specs.common.entities.IPersistentValuesHandler;
import soliloquy.specs.common.factories.ICollectionFactory;
import soliloquy.specs.common.valueobjects.ICollection;

public class PersistentCollectionHandler extends PersistentTypeHandler<ICollection>
        implements IPersistentCollectionHandler {
    private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER;
    private final ICollectionFactory COLLECTION_FACTORY;

    public PersistentCollectionHandler(IPersistentValuesHandler persistentValuesHandler,
                                       ICollectionFactory collectionFactory) {
        PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
        COLLECTION_FACTORY = collectionFactory;
    }

    @Override
    public String getInterfaceName() {
        throw new UnsupportedOperationException();
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
        if (valuesString == null) {
            throw new IllegalArgumentException(
                    "PersistentCollectionHandler.read: valuesString must be non-null");
        }
        if (valuesString.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentCollectionHandler.read: valuesString must be non-null");
        }
        CollectionDTO dto = new Gson().fromJson(valuesString, CollectionDTO.class);
        IPersistentValueTypeHandler handler =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(dto.typeName);
        ICollection collection =
                COLLECTION_FACTORY.make(PERSISTENT_VALUES_HANDLER.generateArchetype(dto.typeName));
        for (int i = 0; i < dto.valueStrings.length; i++) {
            collection.add(handler.read(dto.valueStrings[i]));
        }
        return collection;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String write(ICollection collection) {
        if (collection == null) {
            throw new IllegalArgumentException(
                    "PersistentCollectionHandler.write: collection is null");
        }
        String internalType = getProperTypeName(collection.getArchetype());
        IPersistentValueTypeHandler handler =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(internalType);
        CollectionDTO dto = new CollectionDTO();
        dto.typeName = internalType;
        String[] valueStrings = new String[collection.size()];
        for (int i = 0; i < collection.size(); i++) {
            valueStrings[i] = handler.write(collection.get(i));
        }
        dto.valueStrings = valueStrings;
        return new Gson().toJson(dto);
    }

    @Override
    public ICollection generateArchetype(String valueType) throws IllegalArgumentException {
        if(valueType == null) {
            throw new IllegalArgumentException(
                    "PersistentCollectionHandler.generateArchetype: valueType must be non-null");
        }
        if(valueType.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentCollectionHandler.generateArchetype: valueType must be non-empty");
        }

        int openingCaret = valueType.indexOf("<");
        int closingCaret = valueType.lastIndexOf(">");
        if (!valueType.substring(0, openingCaret).equals(ICollection.class.getCanonicalName())) {
            throw new IllegalArgumentException(
                    "PersistentCollectionHandler.generateArchetype: valueType is not a String representation of a Collection");
        }
        String innerType = valueType.substring(openingCaret + 1, closingCaret);

        return COLLECTION_FACTORY.make(PERSISTENT_VALUES_HANDLER.generateArchetype(innerType));
    }

    private class CollectionDTO {
        String typeName;
        String[] valueStrings;
    }
}
