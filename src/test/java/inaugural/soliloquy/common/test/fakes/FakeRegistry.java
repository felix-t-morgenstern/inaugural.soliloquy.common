package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.shared.HasId;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class FakeRegistry<T extends HasId> implements Registry<T> {
    private HashMap<String, T> _registry = new HashMap<>();

    private T _archetype;

    public FakeRegistry(T archetype) {
        _archetype = archetype;
    }

    @Override
    public boolean contains(String s) {
        return _registry.containsKey(s);
    }

    @Override
    public boolean contains(T t) throws IllegalArgumentException {
        return false;
    }

    @Override
    public T get(String s) {
        return _registry.get(s);
    }

    @Override
    public void add(T item) throws IllegalArgumentException {
        _registry.put(item.id(), item);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addAll(Object[] items) throws IllegalArgumentException {
        for (Object item : items) {
            add((T) item);
        }
    }

    @Override
    public void addAll(T[] items) throws IllegalArgumentException {
        for (T item : items) {
            add(item);
        }
    }

    @Override
    public void addAll(Collection<T> items) throws IllegalArgumentException {
        items.forEach(this::add);
    }

    @Override
    public void clear() throws UnsupportedOperationException {

    }

    @Override
    public List<T> representation() {
        return null;
    }

    @Override
    public boolean remove(String s) {
        return _registry.remove(s) != null;
    }

    @Override
    public boolean remove(T t) throws IllegalArgumentException {
        return false;
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
}
