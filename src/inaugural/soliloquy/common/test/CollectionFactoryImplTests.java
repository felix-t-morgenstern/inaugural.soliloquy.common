package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.test.stubs.CollectionStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import inaugural.soliloquy.common.CollectionFactoryImpl;
import soliloquy.specs.common.infrastructure.Collection;

import static org.junit.jupiter.api.Assertions.*;

class CollectionFactoryImplTests {
    private CollectionFactoryImpl _collectionFactory;

    @BeforeEach
    void setUp() {
        _collectionFactory = new CollectionFactoryImpl();
    }

    @Test
    void testMake() {
        Collection<String> collection = _collectionFactory.make("Hello");
        assertNotNull(collection);
        assertNotNull(collection.getArchetype());

        collection.add("Hello");
        assertTrue(collection.contains("Hello"));
        assertEquals("Hello", collection.getArchetype());
    }

    @Test
    void testMakeWithNullArchetype() {
        assertThrows(IllegalArgumentException.class, () -> _collectionFactory.make(null));
    }

    @Test
    void testMakeFromArray() {
        String string1 = "STRING1";
        String string2 = "STRING2";
        String string3 = "STRING3";
        Collection<String> collection = _collectionFactory.make(new String[] {string1, string2, string3}, "");

        assertEquals(3, collection.size());
        assertSame(collection.get(0), string1);
        assertSame(collection.get(1), string2);
        assertSame(collection.get(2), string3);
        assertNotNull(collection.getArchetype());
    }

    @SuppressWarnings("unchecked")
    @Test
    void testArchetypeWithNullArchetype() {
        Collection archetype = new CollectionStub(null);

        assertThrows(IllegalArgumentException.class, () -> _collectionFactory.make(archetype));
    }
}
