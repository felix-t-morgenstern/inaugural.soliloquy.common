package inaugural.soliloquy.common.infrastructure;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.generic.AbstractHasOneGenericParam;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.shared.HasId;

import java.util.*;

public class RegistryImpl<T extends HasId>
        extends AbstractHasOneGenericParam<T>
        implements Registry<T> {
    private final HashMap<String, T> REGISTRY;

    public RegistryImpl(T archetype) {
        super(archetype);
        REGISTRY = new HashMap<>();
    }

    @Override
    public boolean contains(String id) {
        return REGISTRY.containsKey(Check.ifNullOrEmpty(id, "id"));
    }

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
        return new ArrayList<>(REGISTRY.values());
    }

    @Override
    public boolean remove(String id) {
        return REGISTRY.remove(Check.ifNullOrEmpty(id, "id")) != null;
    }

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
    public Iterator<T> iterator() {
        return REGISTRY.values().iterator();
    }

    @Override
    protected String getUnparameterizedInterfaceName() {
        return Registry.class.getCanonicalName();
    }
}
