package inaugural.soliloquy.common;

import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.shared.HasId;

import java.util.HashMap;
import java.util.Iterator;

public class RegistryImpl<T extends HasId> extends HasOneGenericParam<T> implements Registry<T> {
    private T _archetype;
    private HashMap<String,T> _registry;

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
    public void register(T item) throws IllegalArgumentException {
        if (item == null) {
            throw new IllegalArgumentException("ItemImpl.register: item must be non-null");
        }
        if (item.id() == null) {
            throw new IllegalArgumentException("ItemImpl.register: item's id must be non-null");
        }
        if (item.id().equals("")) {
            throw new IllegalArgumentException("ItemImpl.register: item's id must be non-empty");
        }
        _registry.put(item.id(), item);
    }

    @Override
    public boolean remove(String id) {
        return _registry.remove(id) != null;
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
}
