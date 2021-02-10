package inaugural.soliloquy.common;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.generic.HasOneGenericParam;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadableCollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

class ReadableCollectionImpl<V> extends HasOneGenericParam<V> implements ReadableCollection<V> {
    final ArrayList<V> COLLECTION;
    final V ARCHETYPE;

    ReadableCollectionImpl(V archetype) {
        // TODO: Test whether null archetype was fed in
        ARCHETYPE = Check.ifNull(archetype, "archetype");
        COLLECTION = new ArrayList<>();
    }

    ReadableCollectionImpl(V[] items, V archetype) {
        COLLECTION = new ArrayList<>(Arrays.asList(items));
        ARCHETYPE = archetype;
    }

    @Override
    public boolean contains(V item) {
        return COLLECTION.contains(item);
    }

    @Override
    public V get(int index) {
        return COLLECTION.get(Check.ifNonNegative(index, "index"));
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
    public Collection<V> makeClone() {
        return new CollectionImpl<>((V[]) COLLECTION.toArray(), ARCHETYPE);
    }

    @Override
    public V getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        return ReadableCollection.class.getCanonicalName();
    }

    @Override
    public Iterator<V> iterator() {
        return COLLECTION.iterator();
    }

    @Override
    public int hashCode() {
        return Objects.hash(COLLECTION.toArray());
    }

    @Override
    public boolean equals(ReadableCollection<V> items) {
        if (items == null) return false;
        if (COLLECTION.size() != items.size()) return false;
        for(V item : COLLECTION) if(!items.contains(item)) return false;
        return true;
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException(
                "ReadableCollectionImpl.toString: Operation not supported; use " +
                        "PersistentCollectionHandler.write instead");
    }
}
