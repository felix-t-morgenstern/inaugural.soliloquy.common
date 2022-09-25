package inaugural.soliloquy.common.infrastructure;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.generic.CanGetInterfaceName;
import soliloquy.specs.common.infrastructure.List;

import java.util.Arrays;
import java.util.Collection;

public class ListImpl<V> extends java.util.ArrayList<V> implements List<V> {
    private static final CanGetInterfaceName GET_INTERFACE_NAME = new CanGetInterfaceName();

    private final V _archetype;

    // TODO: Ensure that archetype's child archetypes are tested in constructor
    public ListImpl(V archetype) {
        Check.archetypeAndArchetypesOfArchetypeAreNotNull("archetype", archetype);
        _archetype = Check.ifNull(archetype, "archetype");
    }

    // TODO: Ensure that archetype's child archetypes are tested in constructor
    public ListImpl(V[] items, V archetype) {
        Check.archetypeAndArchetypesOfArchetypeAreNotNull("archetype", archetype);
        _archetype = Check.ifNull(archetype, "archetype");

        this.addAll(Arrays.asList(items));
    }

    // TODO: Ensure that archetype's child archetypes are tested in constructor
    public ListImpl(Collection<V> items, V archetype) {
        super(items);
        Check.archetypeAndArchetypesOfArchetypeAreNotNull("archetype", archetype);
        _archetype = Check.ifNull(archetype, "archetype");
    }

    @Override
    public List<V> makeClone() {
        return new ListImpl<>(this, _archetype);
    }

    @Override
    public V getArchetype() {
        return _archetype;
    }

    @Override
    public String getInterfaceName() {
        return List.class.getCanonicalName() + "<" +
                GET_INTERFACE_NAME.getProperTypeName(_archetype) + ">";
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("PairImpl.toString: Not supported; c.f. " +
                "ListHandler");
    }
}
