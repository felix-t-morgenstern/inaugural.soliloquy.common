package inaugural.soliloquy.common.persistence;

import com.google.gson.Gson;
import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.PersistentDataStructureWithOneGenericParamHandler;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.persistence.PersistentListHandler;
import soliloquy.specs.common.persistence.PersistentValueTypeHandler;
import soliloquy.specs.common.persistence.PersistentValuesHandler;

@SuppressWarnings("rawtypes")
public class PersistentListHandlerImpl
        extends PersistentDataStructureWithOneGenericParamHandler<List>
        implements PersistentListHandler {
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER;
    private final ListFactory LIST_FACTORY;

    public PersistentListHandlerImpl(PersistentValuesHandler persistentValuesHandler,
                                     ListFactory listFactory) {
        PERSISTENT_VALUES_HANDLER = Check.ifNull(persistentValuesHandler,
                "persistentValuesHandler");
        LIST_FACTORY = Check.ifNull(listFactory, "listFactory");
    }

    @SuppressWarnings({"unchecked", "ConstantConditions", "rawtypes"})
    @Override
    public List read(String valuesString) throws IllegalArgumentException {
        if (valuesString == null) {
            throw new IllegalArgumentException(
                    "PersistentListHandlerImpl.read: valuesString must be non-null");
        }
        if (valuesString.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentListHandlerImpl.read: valuesString must be non-null");
        }
        ListDTO dto = new Gson().fromJson(valuesString, ListDTO.class);
        PersistentValueTypeHandler handler =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(dto.typeName);
        List list =
                LIST_FACTORY.make(PERSISTENT_VALUES_HANDLER.generateArchetype(dto.typeName));
        for (int i = 0; i < dto.serializedValues.length; i++) {
            list.add(handler.read(dto.serializedValues[i]));
        }
        return list;
    }

    @SuppressWarnings({"unchecked", "ConstantConditions", "rawtypes"})
    @Override
    public String write(List list) {
        Check.ifNull(list, "list");
        String internalType = getProperTypeName(list.getArchetype());
        PersistentValueTypeHandler handler =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(internalType);
        ListDTO dto = new ListDTO();
        dto.typeName = internalType;
        String[] serializedValues = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            serializedValues[i] = handler.write(list.get(i));
        }
        dto.serializedValues = serializedValues;
        return new Gson().toJson(dto);
    }

    @SuppressWarnings({"rawtypes", "ConstantConditions"})
    @Override
    public List generateArchetype(String valueType) throws IllegalArgumentException {
        String innerType = getInnerType(valueType, List.class,
                "PersistentListHandlerImpl");

        return LIST_FACTORY.make(PERSISTENT_VALUES_HANDLER.generateArchetype(innerType));
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class ListDTO {
        String typeName;
        String[] serializedValues;
    }

    @Override
    public String toString() {
        return PersistentListHandlerImpl.class.getCanonicalName();
    }

    @Override
    public int hashCode() {
        return PersistentListHandlerImpl.class.getCanonicalName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PersistentListHandlerImpl && obj.hashCode() == hashCode();
    }
}
