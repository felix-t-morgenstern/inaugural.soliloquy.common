package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.generic.CanGetInterfaceName;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.persistence.TypeWithOneGenericParamHandler;
import soliloquy.specs.common.persistence.TypeWithTwoGenericParamsHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PersistentValuesHandlerImpl extends CanGetInterfaceName
        implements PersistentValuesHandler {
    @SuppressWarnings("rawtypes")
    private final HashMap<String, TypeHandler> TYPE_HANDLERS;

    public PersistentValuesHandlerImpl() {
        TYPE_HANDLERS = new HashMap<>();
    }

    @Override
    public void addTypeHandler(TypeHandler typeHandler) throws IllegalArgumentException {
        String typeHandlerInterfaceName =
                Check.ifNull(typeHandler, "typeHandler").getInterfaceName();
        String typeHandled = typeHandlerInterfaceName.substring(
                typeHandlerInterfaceName.indexOf("<") + 1,
                typeHandlerInterfaceName.length() - 1);

        if (TYPE_HANDLERS.containsKey(typeHandled)) {
            throw new IllegalArgumentException(
                    "PersistentValuesHandler.addTypeHandler: already has handler for "
                            + typeHandled);
        }
        TYPE_HANDLERS.put(typeHandled, typeHandler);
    }

    @Override
    public boolean removeTypeHandler(String type) {
        return TYPE_HANDLERS.remove(Check.ifNullOrEmpty(type, "type")) != null;
    }

    @Override
    public <T> TypeHandler<T> getTypeHandler(String type) throws IllegalArgumentException {
        Check.ifNullOrEmpty(type, "type");
        int caretIndex = type.indexOf("<");
        if (caretIndex > 0) {
            type = type.substring(0, caretIndex);
        }
        //noinspection unchecked
        TypeHandler<T> typeHandler = TYPE_HANDLERS.get(type);
        if (typeHandler == null) {
            throw new IllegalArgumentException("PersistentValuesHandlerImpl.getTypeHandler: " +
                    "no type handler found for type \"" + type + "\"");
        }
        return typeHandler;
    }

    @Override
    public <TArchetype> TArchetype generateArchetype(String type) throws IllegalArgumentException {
        Check.ifNullOrEmpty(type, "type");
        // TODO: Implement!

        if (type.contains("<")) {
            int openingCaretIndex = type.indexOf("<");
            int endingIndex = type.indexOf(">");
            if (endingIndex < 0) {
                throw new IllegalArgumentException(
                        "PersistentValuesHandlerImpl.generateArchetype: type (\"" + type +
                                "\") has opening caret with no closing caret");
            }
            String outerType = type.substring(0, openingCaretIndex);
            if (type.contains(",")) {
                int commaIndex = type.indexOf(",");
                String innerType1 = type.substring(openingCaretIndex + 1, commaIndex);
                String innerType2 = type.substring(commaIndex + 1, type.length() - 1);
                //noinspection rawtypes
                TypeWithTwoGenericParamsHandler typeHandler =
                        (TypeWithTwoGenericParamsHandler) getTypeHandler(outerType);
                //noinspection unchecked
                return (TArchetype) typeHandler.generateArchetype(innerType1, innerType2);
            }
            else {
                String innerType = type.substring(openingCaretIndex + 1, type.length() - 1);
                //noinspection rawtypes
                TypeWithOneGenericParamHandler typeHandler =
                        (TypeWithOneGenericParamHandler) getTypeHandler(outerType);
                //noinspection unchecked
                return (TArchetype) typeHandler.generateArchetype(innerType);
            }
        }
        else {
            TypeHandler<TArchetype> typeHandler = getTypeHandler(type);
            if (typeHandler instanceof TypeWithOneGenericParamHandler) {
                throw new IllegalArgumentException(
                        "PersistentValuesHandlerImpl.generateArchetype: type (\"" + type +
                                "\") has one generic parameter which has not been specified");
            }
            return typeHandler.getArchetype();
        }
    }

    @Override
    public List<String> typesHandled() {
        return new ArrayList<>(TYPE_HANDLERS.keySet());
    }

    @Override
    public String getInterfaceName() {
        return PersistentValuesHandler.class.getCanonicalName();
    }

    @Override
    public int hashCode() {
        return PersistentValuesHandlerImpl.class.getCanonicalName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PersistentValuesHandlerImpl && obj.hashCode() == hashCode();
    }

    @Override
    public String toString() {
        return PersistentValuesHandlerImpl.class.getCanonicalName();
    }
}
