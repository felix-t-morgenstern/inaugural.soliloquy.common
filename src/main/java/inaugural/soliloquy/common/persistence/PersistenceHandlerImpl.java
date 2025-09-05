package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.persistence.PersistenceHandler;
import soliloquy.specs.common.persistence.TypeHandler;

import java.util.Map;
import java.util.Set;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static inaugural.soliloquy.tools.collections.Collections.setOf;

public class PersistenceHandlerImpl implements PersistenceHandler {
    @SuppressWarnings("rawtypes")
    private final Map<String, TypeHandler> TYPE_HANDLERS;
    @SuppressWarnings("rawtypes") private final Set<Class> TYPES_HANDLED;

    public PersistenceHandlerImpl() {
        TYPE_HANDLERS = mapOf();
        TYPES_HANDLED = setOf();
    }

    @Override
    public <T, TImpl extends T> void addTypeHandler(Class<TImpl> aClass, TypeHandler<T> typeHandler)
            throws IllegalArgumentException {
        Check.ifNull(aClass, "aClass");
        Check.ifNull(typeHandler, "typeHandler");

        if (TYPE_HANDLERS.containsKey(aClass.getCanonicalName())) {
            throw new IllegalArgumentException(
                    "PersistenceHandler.addTypeHandler: already has handler for " +
                            aClass.getCanonicalName());
        }
        TYPE_HANDLERS.put(aClass.getCanonicalName(), typeHandler);
        TYPES_HANDLED.add(aClass);
    }

    @Override
    public <T> boolean removeTypeHandler(Class<T> aClass) {
        return TYPE_HANDLERS.remove(Check.ifNull(aClass, "aClass").getCanonicalName()) != null;
    }

    @Override
    public <T> TypeHandler<T> getTypeHandler(String aClass) throws IllegalArgumentException {
        Check.ifNullOrEmpty(aClass, "aClass");
        //noinspection unchecked
        var handler = (TypeHandler<T>) TYPE_HANDLERS.get(aClass);
        if (handler == null) {
            throw new IllegalArgumentException(
                    "PersistenceHandlerImpl.getTypeHandler: no handler found for type " + aClass);
        }
        else {
            return handler;
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Set<Class> typesHandled() {
        return setOf(TYPES_HANDLED);
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
