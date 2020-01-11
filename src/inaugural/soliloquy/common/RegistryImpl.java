package inaugural.soliloquy.common;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.shared.HasId;

import java.util.HashMap;
import java.util.Iterator;

public class RegistryImpl<T extends HasId> extends HasOneGenericParam<T> implements Registry<T> {
    private T _archetype;
    private HashMap<String,T> _registry;

    @SuppressWarnings("ConstantConditions")
    public RegistryImpl(T archetype) {
        if (archetype == null) {
            throw new IllegalArgumentException("RegistryImpl: archetype must be non-null");
        }
        _archetype = archetype;
        _registry = new HashMap<>();
    }

    @Override
    public boolean contains(String id) {
        return _registry.containsKey(id);
    }

    @Override
    public T get(String id) {
        return _registry.get(id);
    }

    @Override
    public void add(T item) throws IllegalArgumentException {
        if (item == null) {
            throw new IllegalArgumentException("ItemImpl.add: item must be non-null");
        }
        if (item.id() == null) {
            throw new IllegalArgumentException("ItemImpl.add: item's id must be non-null");
        }
        if (item.id().equals("")) {
            throw new IllegalArgumentException("ItemImpl.add: item's id must be non-empty");
        }
        _registry.put(item.id(), item);
    }

    @Override
    public void addAll(Collection<? extends T> collection) throws UnsupportedOperationException {
        // TODO: Test and implement
    }

    @Override
    public void addAll(T[] ts) throws UnsupportedOperationException {
        // TODO: Test and implement
    }

    @Override
    public void clear() throws UnsupportedOperationException {
        // TODO: Test and implement
    }

    @Override
    public boolean remove(T t) throws UnsupportedOperationException {
        // TODO: Test and implement
        return false;
    }

    @Override
    public ReadableCollection<T> representation() {
        return null;
    }

    @Override
    public boolean remove(String id) {
        return _registry.remove(id) != null;
    }

    @Override
    public boolean contains(T t) {
        return false;
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
        return _registry.size();
    }

    @Override
    public T getArchetype() {
        return _archetype;
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        return Registry.class.getCanonicalName();
    }

    @Override
    public Iterator<T> iterator() {
        return _registry.values().iterator();
    }

    @Override
    public Collection<T> makeClone() {
        return null;
    }
}
