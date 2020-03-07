package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.shared.HasId;

import java.util.HashMap;
import java.util.Iterator;

public class FakeRegistry<T extends HasId> implements Registry<T> {
    private HashMap<String,T> _registry = new HashMap<>();

    private T _archetype;

    public FakeRegistry(T archetype)
    {
        _archetype = archetype;
    }

    @Override
    public boolean contains(String s) {
        return _registry.containsKey(s);
    }

    @Override
    public T get(String s) {
        return _registry.get(s);
    }

    @Override
    public void add(T item) throws IllegalArgumentException {
        _registry.put(item.id(), item);
    }

    @Override
    public void addAll(ReadableCollection<? extends T> collection) throws UnsupportedOperationException {

    }

    @Override
    public void addAll(T[] ts) throws UnsupportedOperationException {

    }

    @Override
    public void clear() throws UnsupportedOperationException {

    }

    @Override
    public boolean remove(T t) throws UnsupportedOperationException {
        return false;
    }

    @Override
    public ReadableCollection<T> representation() {
        return null;
    }

    @Override
    public boolean remove(String s) {
        return _registry.remove(s) != null;
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
    public String getInterfaceName() {
        return null;
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
