package inaugural.soliloquy.common.test.unit.factories;

import inaugural.soliloquy.common.factories.ListFactoryImpl;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.List;

import static inaugural.soliloquy.tools.collections.Collections.arrayOf;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListFactoryImplTests {
    private ListFactory factory;

    @Before
    public void setUp() {
        factory = new ListFactoryImpl();
    }

    @Test
    public void testMake() {
        var archetype = randomString();
        var value = randomString();

        var list = factory.make(archetype);
        list.add(value);

        assertNotNull(list);
        assertTrue(list.contains(value));
        assertNotNull(list.archetype());
        assertEquals(archetype, list.archetype());
    }

    @Test
    public void testMakeWithNullArchetype() {
        assertThrows(IllegalArgumentException.class, () -> factory.make(null));
    }

    @Test
    public void testMakeFromArray() {
        var string1 = randomString();
        var string2 = randomString();
        var string3 = randomString();
        var list = factory.make(arrayOf(string1, string2, string3), "");

        assertEquals(3, list.size());
        assertSame(list.get(0), string1);
        assertSame(list.get(1), string2);
        assertSame(list.get(2), string3);
        assertNotNull(list.archetype());
    }

    @Test
    public void testArchetypeWithNullArchetype() {
        //noinspection unchecked
        List<String> archetype = mock(List.class);
        when(archetype.archetype()).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> factory.make(archetype));
    }

    @Test
    public void testHashCode() {
        assertEquals(ListFactoryImpl.class.getCanonicalName().hashCode(),
                factory.hashCode());
    }

    @SuppressWarnings({"SimplifiableJUnitAssertion", "ConstantConditions"})
    @Test
    public void testEquals() {
        var equalFactory = new ListFactoryImpl();
        var unequalFactory = mock(ListFactory.class);

        assertTrue(factory.equals(equalFactory));
        assertFalse(factory.equals(unequalFactory));
        assertFalse(factory.equals(null));
    }

    @Test
    public void testToString() {
        assertEquals(ListFactoryImpl.class.getCanonicalName(),
                factory.toString());
    }
}
