package inaugural.soliloquy.common;

import inaugural.soliloquy.tools.Check;
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

    public RegistryImpl(T archetype, CollectionFactory collectionFactory) {
        ARCHETYPE = Check.ifNull(archetype, "archetype");
        COLLECTION_FACTORY = Check.ifNull(collectionFactory, "collectionFactory");
        REGISTRY = new HashMap<>();
    }

    @Override
    public boolean contains(String id) {
        return REGISTRY.containsKey(Check.ifNullOrEmpty(id, "id"));
    }

    @Override
    public T get(String id) {
        return REGISTRY.get(id);
    }

    @Override
    public void add(T item) throws IllegalArgumentException {
        REGISTRY.put(Check.ifNullOrEmpty(Check.ifNull(item, "item").id(), "item.id"), item);
    }

    @Override
    public void addAll(ReadableCollection<? extends T> collection)
            throws UnsupportedOperationException {
        Check.ifNull(collection, "collection").forEach(this::add);
    }

    @Override
    public void addAll(T[] array) throws UnsupportedOperationException {
        for (T t : Check.ifNull(array, "array")) {
            this.add(t);
        }
    }

    @Override
    public void clear() throws UnsupportedOperationException {
        REGISTRY.clear();
    }

    @Override
    public boolean remove(T item) throws UnsupportedOperationException {
        return REGISTRY.remove(Check.ifNullOrEmpty(Check.ifNull(item, "item").id(), "item.id"))
                != null;
    }

    @Override
    public ReadableCollection<T> representation() {
        Collection<T> collection = COLLECTION_FACTORY.make(ARCHETYPE);
        REGISTRY.values().forEach(collection::add);
        return collection.representation();
    }

    @Override
    public boolean remove(String id) {
        return REGISTRY.remove(Check.ifNullOrEmpty(id, "id")) != null;
    }

    @Override
    public boolean contains(T item) {
        return REGISTRY.containsValue(Check.ifNull(item, "item"));
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
