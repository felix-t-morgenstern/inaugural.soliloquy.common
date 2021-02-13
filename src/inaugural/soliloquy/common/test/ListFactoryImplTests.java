package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.test.fakes.FakeList;
import inaugural.soliloquy.common.test.fakes.FakeListFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import inaugural.soliloquy.common.ListFactoryImpl;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.List;

import static org.junit.jupiter.api.Assertions.*;

class ListFactoryImplTests {
    private ListFactoryImpl _listFactory;

    @BeforeEach
    void setUp() {
        _listFactory = new ListFactoryImpl();
    }

    @Test
    void testMake() {
        List<String> collection = _listFactory.make("Hello");
        assertNotNull(collection);
        assertNotNull(collection.getArchetype());

        collection.add("Hello");
        assertTrue(collection.contains("Hello"));
        assertEquals("Hello", collection.getArchetype());
    }

    @Test
    void testMakeWithNullArchetype() {
        assertThrows(IllegalArgumentException.class, () -> _listFactory.make(null));
    }

    @Test
    void testMakeFromArray() {
        String string1 = "STRING1";
        String string2 = "STRING2";
        String string3 = "STRING3";
        List<String> collection = _listFactory.make(new String[] {string1, string2, string3}, "");

        assertEquals(3, collection.size());
        assertSame(collection.get(0), string1);
        assertSame(collection.get(1), string2);
        assertSame(collection.get(2), string3);
        assertNotNull(collection.getArchetype());
    }

    @SuppressWarnings("unchecked")
    @Test
    void testArchetypeWithNullArchetype() {
        List<String> archetype = new FakeList<>((String)null);

        assertThrows(IllegalArgumentException.class, () -> _listFactory.make(archetype));
    }

    @Test
    void testHashCode() {
        assertEquals(ListFactoryImpl.class.getCanonicalName().hashCode(),
                _listFactory.hashCode());
    }

    @SuppressWarnings({"SimplifiableJUnitAssertion", "ConstantConditions"})
    @Test
    void testEquals() {
        ListFactory equalCollectionFactory = new ListFactoryImpl();
        ListFactory unequalCollectionFactory = new FakeListFactory();

        assertTrue(_listFactory.equals(equalCollectionFactory));
        assertFalse(_listFactory.equals(unequalCollectionFactory));
        assertFalse(_listFactory.equals(null));
    }

    @Test
    void testToString() {
        assertEquals(ListFactoryImpl.class.getCanonicalName(),
                _listFactory.toString());
    }
}
