package inaugural.soliloquy.common.persistentvaluetypehandlers;

import com.google.gson.Gson;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.infrastructure.PersistentValuesHandler;

public class PersistentCollectionHandler extends PersistentTypeHandler<Collection>
        implements soliloquy.specs.common.infrastructure.PersistentCollectionHandler {
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER;
    private final CollectionFactory COLLECTION_FACTORY;

    public PersistentCollectionHandler(PersistentValuesHandler persistentValuesHandler,
                                       CollectionFactory collectionFactory) {
        PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
        COLLECTION_FACTORY = collectionFactory;
    }

    @Override
    public String getInterfaceName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection getArchetype() {
        // NB: PersistentCollectionHandler should be selected by the PersistentValuesHandler
        // through specific, manually-defined String pattern recognition, rather than via
        // getArchetype.
        return null;
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @Override
    public Collection read(String valuesString) throws IllegalArgumentException {
        if (valuesString == null) {
            throw new IllegalArgumentException(
                    "PersistentCollectionHandler.read: valuesString must be non-null");
        }
        if (valuesString.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentCollectionHandler.read: valuesString must be non-null");
        }
        CollectionDTO dto = new Gson().fromJson(valuesString, CollectionDTO.class);
        PersistentValueTypeHandler handler =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(dto.typeName);
        Collection collection =
                COLLECTION_FACTORY.make(PERSISTENT_VALUES_HANDLER.generateArchetype(dto.typeName));
        for (int i = 0; i < dto.serializedValues.length; i++) {
            collection.add(handler.read(dto.serializedValues[i]));
        }
        return collection;
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @Override
    public String write(Collection collection) {
        if (collection == null) {
            throw new IllegalArgumentException(
                    "PersistentCollectionHandler.write: collection is null");
        }
        String internalType = getProperTypeName(collection.getArchetype());
        PersistentValueTypeHandler handler =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(internalType);
        CollectionDTO dto = new CollectionDTO();
        dto.typeName = internalType;
        String[] serializedValues = new String[collection.size()];
        for (int i = 0; i < collection.size(); i++) {
            serializedValues[i] = handler.write(collection.get(i));
        }
        dto.serializedValues = serializedValues;
        return new Gson().toJson(dto);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public Collection generateArchetype(String valueType) throws IllegalArgumentException {
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
        if (!valueType.substring(0, openingCaret).equals(Collection.class.getCanonicalName())) {
            throw new IllegalArgumentException(
                    "PersistentCollectionHandler.generateArchetype: valueType is not a String representation of a Collection");
        }
        String innerType = valueType.substring(openingCaret + 1, closingCaret);

        return COLLECTION_FACTORY.make(PERSISTENT_VALUES_HANDLER.generateArchetype(innerType));
    }

    private class CollectionDTO {
        String typeName;
        String[] serializedValues;
    }
}
