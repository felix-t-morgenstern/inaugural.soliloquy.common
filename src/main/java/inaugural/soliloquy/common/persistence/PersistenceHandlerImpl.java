package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.persistence.PersistenceHandler;
import soliloquy.specs.common.persistence.TypeHandler;

import java.util.List;
import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.collections.Collections.mapOf;

public class PersistenceHandlerImpl implements PersistenceHandler {
    @SuppressWarnings("rawtypes")
    private final Map<String, TypeHandler> TYPE_HANDLERS;

    public PersistenceHandlerImpl() {
        TYPE_HANDLERS = mapOf();
    }

    @Override
    public void addTypeHandler(TypeHandler typeHandler) throws IllegalArgumentException {
        var typeHandled = Check.ifNull(typeHandler, "typeHandler").typeHandled();

        if (TYPE_HANDLERS.containsKey(typeHandled)) {
            throw new IllegalArgumentException(
                    "PersistenceHandler.addTypeHandler: already has handler for " + typeHandled);
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
        //noinspection unchecked
        var handler = (TypeHandler<T>) TYPE_HANDLERS.get(type);
        if (handler == null) {
            throw new IllegalArgumentException(
                    "PersistenceHandlerImpl.getTypeHandler: no handler found for type " + type);
        }
        else {
            return handler;
        }
    }

    @Override
    public List<String> typesHandled() {
        return listOf(TYPE_HANDLERS.keySet());
    }

    @Override
    public int hashCode() {
        return PersistenceHandlerImpl.class.getCanonicalName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PersistenceHandlerImpl && obj.hashCode() == hashCode();
    }

    @Override
    public String toString() {
        return PersistenceHandlerImpl.class.getCanonicalName();
    }
}
