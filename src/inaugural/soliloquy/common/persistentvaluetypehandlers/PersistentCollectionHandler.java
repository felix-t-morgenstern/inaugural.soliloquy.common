package inaugural.soliloquy.common.persistentvaluetypehandlers;

import com.google.gson.Gson;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.infrastructure.PersistentValuesHandler;

public class PersistentCollectionHandler
        extends PersistentDataStructureWithOneGenericParamHandler<Collection>
        implements soliloquy.specs.common.infrastructure.PersistentCollectionHandler {
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER;
    private final CollectionFactory COLLECTION_FACTORY;

    public PersistentCollectionHandler(PersistentValuesHandler persistentValuesHandler,
                                       CollectionFactory collectionFactory) {
        PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
        COLLECTION_FACTORY = collectionFactory;
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

    @Override
    public Collection generateArchetype(String valueType) throws IllegalArgumentException {
        String innerType = getInnerType(valueType, Collection.class,
                "PersistentCollectionHandler");

        return COLLECTION_FACTORY.make(PERSISTENT_VALUES_HANDLER.generateArchetype(innerType));
    }

    private class CollectionDTO {
        String typeName;
        String[] serializedValues;
    }
}
