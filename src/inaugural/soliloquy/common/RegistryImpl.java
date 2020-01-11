package inaugural.soliloquy.common;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.shared.HasId;

import java.util.HashMap;
import java.util.Iterator;

public class RegistryImpl<T extends HasId> extends HasOneGenericParam<T> implements Registry<T> {
    private final T ARCHETYPE;
    private final CollectionFactory COLLECTION_FACTORY;
    private final HashMap<String,T> REGISTRY;

    @SuppressWarnings("ConstantConditions")
    public RegistryImpl(T archetype, CollectionFactory collectionFactory) {
        if (archetype == null) {
            throw new IllegalArgumentException("RegistryImpl: archetype must be non-null");
        }
        ARCHETYPE = archetype;
        if (collectionFactory == null) {
            throw new IllegalArgumentException("RegistryImpl: collectionFactory must be non-null");
        }
        COLLECTION_FACTORY = collectionFactory;
        REGISTRY = new HashMap<>();
    }

    @Override
    public boolean contains(String id) {
        return REGISTRY.containsKey(id);
    }

    @Override
    public T get(String id) {
        return REGISTRY.get(id);
    }

    @Override
    public void add(T item) throws IllegalArgumentException {
        if (item == null) {
            throw new IllegalArgumentException("RegistryImpl.add: item must be non-null");
        }
        if (item.id() == null) {
            throw new IllegalArgumentException("RegistryImpl.add: item's id must be non-null");
        }
        if (item.id().equals("")) {
            throw new IllegalArgumentException("RegistryImpl.add: item's id must be non-empty");
        }
        REGISTRY.put(item.id(), item);
    }

    @Override
    public void addAll(ReadableCollection<? extends T> collection)
            throws UnsupportedOperationException {
        if (collection == null) {
            throw new IllegalArgumentException("RegistryImpl.addAll: collection cannot be null");
        }
        collection.forEach(this::add);
    }

    @Override
    public void addAll(T[] array) throws UnsupportedOperationException {
        if (array == null) {
            throw new IllegalArgumentException("RegistryImpl.addAll: array cannot be null");
        }
        for (T t : array) {
            this.add(t);
        }
    }

    @Override
    public void clear() throws UnsupportedOperationException {
        REGISTRY.clear();
    }

    @Override
    public boolean remove(T item) throws UnsupportedOperationException {
        if (item == null) {
            throw new IllegalArgumentException("RegistryImpl.remove: item cannot be null");
        }
        if (item.id() == null) {
            throw new IllegalArgumentException("RegistryImpl.remove: item cannot have null id");
        }
        if (item.id().equals("")) {
            throw new IllegalArgumentException("RegistryImpl.remove: item cannot have null id");
        }
        return REGISTRY.remove(item.id()) != null;
    }

    @Override
    public ReadableCollection<T> representation() {
        Collection<T> collection = COLLECTION_FACTORY.make(ARCHETYPE);
        REGISTRY.values().forEach(collection::add);
        return collection.representation();
    }

    @Override
    public boolean remove(String id) {
        return REGISTRY.remove(id) != null;
    }

    @Override
    public boolean contains(T item) {
        return REGISTRY.containsValue(item);
    }

    @Override
    public boolean equals(ReadableCollection<T> readableCollection) {
        return false;
    }

    @Override
    public T get(int i) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
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

    @Override
    public Collection<T> makeClone() {
        return null;
    }
}
