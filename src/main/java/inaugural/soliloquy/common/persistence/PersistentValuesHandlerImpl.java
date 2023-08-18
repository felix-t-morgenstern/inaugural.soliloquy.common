package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.generic.CanGetInterfaceName;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.persistence.TypeWithOneGenericParamHandler;
import soliloquy.specs.common.persistence.TypeWithTwoGenericParamsHandler;

import java.util.List;
import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.collections.Collections.mapOf;

public class PersistentValuesHandlerImpl extends CanGetInterfaceName
        implements PersistentValuesHandler {
    @SuppressWarnings("rawtypes")
    private final Map<String, TypeHandler> TYPE_HANDLERS;

    public PersistentValuesHandlerImpl() {
        TYPE_HANDLERS = mapOf();
    }

    @Override
    public void addTypeHandler(TypeHandler typeHandler) throws IllegalArgumentException {
        var typeHandlerInterfaceName = Check.ifNull(typeHandler, "typeHandler").getInterfaceName();
        var typeHandled = typeHandlerInterfaceName.substring(
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
        var caretIndex = type.indexOf("<");
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

        if (type.contains("<")) {
            var openingCaretIndex = type.indexOf("<");
            var endingIndex = type.indexOf(">");
            if (endingIndex < 0) {
                throw new IllegalArgumentException(
                        "PersistentValuesHandlerImpl.generateArchetype: type (\"" + type +
                                "\") has opening caret with no closing caret");
            }
            var outerType = type.substring(0, openingCaretIndex);
            if (type.contains(",")) {
                var commaIndex = type.indexOf(",");
                var innerType1 = type.substring(openingCaretIndex + 1, commaIndex);
                var innerType2 = type.substring(commaIndex + 1, type.length() - 1);
                //noinspection rawtypes
                var handler = (TypeWithTwoGenericParamsHandler) getTypeHandler(outerType);
                //noinspection unchecked
                return (TArchetype) handler.generateArchetype(innerType1, innerType2);
            }
            else {
                var innerType = type.substring(openingCaretIndex + 1, type.length() - 1);
                //noinspection rawtypes
                var handler = (TypeWithOneGenericParamHandler) getTypeHandler(outerType);
                //noinspection unchecked
                return (TArchetype) handler.generateArchetype(innerType);
            }
        }
        else {
            TypeHandler<TArchetype> handler = getTypeHandler(type);
            if (handler instanceof TypeWithOneGenericParamHandler) {
                throw new IllegalArgumentException(
                        "PersistentValuesHandlerImpl.generateArchetype: type (\"" + type +
                                "\") has one generic parameter which has not been specified");
            }
            return handler.archetype();
        }
    }

    @Override
    public List<String> typesHandled() {
        return listOf(TYPE_HANDLERS.keySet());
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
