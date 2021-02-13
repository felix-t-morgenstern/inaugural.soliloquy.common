package inaugural.soliloquy.common;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.generic.HasOneGenericParam;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.shared.HasId;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class RegistryImpl<T extends HasId> extends HasOneGenericParam<T> implements Registry<T> {
    private final T ARCHETYPE;
    private final ListFactory LIST_FACTORY;
    private final HashMap<String,T> REGISTRY;

    public RegistryImpl(T archetype, ListFactory listFactory) {
        ARCHETYPE = Check.ifNull(archetype, "archetype");
        LIST_FACTORY = Check.ifNull(listFactory, "listFactory");
        REGISTRY = new HashMap<>();
    }

    @Override
    public boolean contains(String id) {
        return REGISTRY.containsKey(Check.ifNullOrEmpty(id, "id"));
    }

    // TODO: Test and implement
    @Override
    public boolean contains(T item) throws IllegalArgumentException {
        Check.ifNull(item, "item");
        return REGISTRY.containsKey(Check.ifNullOrEmpty(item.id(), "item.id()"));
    }

    @Override
    public T get(String id) {
        return REGISTRY.get(id);
    }

    @Override
    public void add(T item) throws IllegalArgumentException {
        Check.ifNull(item, "item");
        REGISTRY.put(Check.ifNullOrEmpty(Check.ifNull(item, "item").id(), "item.id"), item);
    }

    // TODO: Test and implement
    @SuppressWarnings("unchecked")
    @Override
    public void addAll(Object[] items) throws IllegalArgumentException {
        Check.ifNull(items, "items");
        for (Object item : items) {
            if (item == null) {
                throw new IllegalArgumentException("RegistryImpl.addAll: object in untyped " +
                        "array cannot be null");
            }
            try {
                add((T) item);
            }
            catch (ClassCastException e) {
                throw new IllegalArgumentException("RegistryImpl.addAll: object in untyped " +
                        "array was not of required type");
            }
        }
    }

    // TODO: Test and implement
    @Override
    public void addAll(T[] items) throws IllegalArgumentException {
        Check.ifNull(items, "items");
        for (T item : items) {
            if (item == null) {
                throw new IllegalArgumentException("RegistryImpl.addAll: object in untyped " +
                        "array cannot be null");
            }
            add(item);
        }
    }

    @Override
    public void addAll(Collection<T> collection) throws IllegalArgumentException {
        Check.ifNull(collection, "collection").forEach(this::add);
    }

    @Override
    public void clear() throws UnsupportedOperationException {
        REGISTRY.clear();
    }

    @Override
    public List<T> representation() {
        return LIST_FACTORY.make(REGISTRY.values(), ARCHETYPE);
    }

    @Override
    public boolean remove(String id) {
        return REGISTRY.remove(Check.ifNullOrEmpty(id, "id")) != null;
    }

    // TODO: Test to ensure no null input
    @Override
    public boolean remove(T item) throws IllegalArgumentException {
        Check.ifNull(item, "item");
        return REGISTRY.remove(Check.ifNullOrEmpty(item.id(), "item.id()"), item);
    }

    @Override
    public int size() {
        return REGISTRY.size();
    }

    @Override
    public T getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        return Registry.class.getCanonicalName();
    }

    @Override
    public Iterator<T> iterator() {
        return REGISTRY.values().iterator();
    }
}
