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
    	Collection<String> strings = _collectionFactory.make("Hello");
		assertNotNull(strings);
    	
    	strings.add("Hello");
    	assertTrue(strings.contains("Hello"));
		assertEquals("Hello", strings.getArchetype());
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
		Collection<String> newCollection = _collectionFactory.make(new String[] {string1, string2, string3}, "");

		assertEquals(3, newCollection.size());
		assertSame(newCollection.get(0), string1);
		assertSame(newCollection.get(1), string2);
		assertSame(newCollection.get(2), string3);
    }

    @SuppressWarnings("unchecked")
	@Test
	void testArchetypeWithNullArchetype() {
		Collection archetype = new CollectionStub(null);

		assertThrows(IllegalArgumentException.class, () -> _collectionFactory.make(archetype));
	}
}
