package inaugural.soliloquy.common;

import soliloquy.specs.common.infrastructure.ICollection;
import soliloquy.specs.common.infrastructure.IReadOnlyCollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

class ReadOnlyCollection<V> extends HasOneGenericParam<V> implements IReadOnlyCollection<V> {
    final ArrayList<V> COLLECTION;
    final V ARCHETYPE;

    ReadOnlyCollection(V archetype) {
        COLLECTION = new ArrayList<>();
        ARCHETYPE = archetype;
    }

    ReadOnlyCollection(V[] items, V archetype) {
        COLLECTION = new ArrayList<>(Arrays.asList(items));
        ARCHETYPE = archetype;
    }

    @Override
    public boolean contains(V item) {
        return COLLECTION.contains(item);
    }

    @Override
    public boolean equals(IReadOnlyCollection<V> items) {
        if (items == null) return false;
        if (COLLECTION.size() != items.size()) return false;
        for(V item : COLLECTION) if(!items.contains(item)) return false;
        return true;
    }

    @Override
    public V get(int index) {
        return COLLECTION.get(index);
    }

    @Override
    public boolean isEmpty() {
        return COLLECTION.isEmpty();
    }

    @Override
    public Object[] toArray() {
        return COLLECTION.toArray();
    }

    @Override
    public int size() {
        return COLLECTION.size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public ICollection<V> makeClone() {
        return new Collection<>((V[]) COLLECTION.toArray(), ARCHETYPE);
    }

    @Override
    public V getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        return IReadOnlyCollection.class.getCanonicalName();
    }

    @Override
    public Iterator<V> iterator() {
        return COLLECTION.iterator();
    }
}
