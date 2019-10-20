package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.shared.HasId;

import java.util.HashMap;
import java.util.Iterator;

public class RegistryStub<T extends HasId> implements Registry<T> {
    HashMap<String,T> _registry = new HashMap<>();

    private T _archetype;

    public RegistryStub(T archetype)
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
    public void register(T item) throws IllegalArgumentException {
        _registry.put(item.id(), item);
    }

    @Override
    public boolean remove(String s) {
        return _registry.remove(s) != null;
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
