package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.RegistryHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.shared.HasId;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegistryHandlerTests {
    private final String VALUE_1 = randomString();
    private final String VALUE_2 = randomString();
    private final String VALUE_3 = randomString();
    private final String INTERNAL_TYPE = randomString();

    @Mock private HasId mockHasId1;
    @Mock private HasId mockHasId2;
    @Mock private HasId mockHasId3;
    @Mock private HasId mockGeneratedArchetype;
    @Mock @SuppressWarnings({"rawtypes"}) private TypeHandler mockHasIdHandler;
    @Mock private PersistentValuesHandler mockPersistentValuesHandler;
    @Mock @SuppressWarnings({"rawtypes"}) private Registry mockRegistry;
    @Mock private RegistryFactory mockRegistryFactory;

    private RegistryHandler registryHandler;

    private final String DATA_STRING =
            String.format("{\"type\":\"%s\",\"values\":[\"%s\",\"%s\",\"%s\"]}",
                    INTERNAL_TYPE, VALUE_1, VALUE_2, VALUE_3);

    @Before
    public void setup() {
        var archetype = mock(HasId.class);
        when(archetype.getInterfaceName()).thenReturn(INTERNAL_TYPE);
        when(mockRegistry.archetype()).thenReturn(archetype);
        when(mockRegistry.iterator()).thenReturn(listOf(
            mockHasId1 = mock(HasId.class),
            mockHasId2 = mock(HasId.class),
            mockHasId3 = mock(HasId.class)
        ).iterator());
        when(mockRegistry.size()).thenReturn(3);

        //noinspection unchecked
        when(mockRegistryFactory.make(any())).thenReturn(mockRegistry);

        //noinspection unchecked
        when(mockHasIdHandler.write(mockHasId1)).thenReturn(VALUE_1);
        //noinspection unchecked
        when(mockHasIdHandler.write(mockHasId2)).thenReturn(VALUE_2);
        //noinspection unchecked
        when(mockHasIdHandler.write(mockHasId3)).thenReturn(VALUE_3);
        when(mockHasIdHandler.read(VALUE_1)).thenReturn(mockHasId1);
        when(mockHasIdHandler.read(VALUE_2)).thenReturn(mockHasId2);
        when(mockHasIdHandler.read(VALUE_3)).thenReturn(mockHasId3);

        //noinspection unchecked
        when(mockPersistentValuesHandler.getTypeHandler(INTERNAL_TYPE))
                .thenReturn(mockHasIdHandler);
        when(mockPersistentValuesHandler.generateArchetype(INTERNAL_TYPE)).thenReturn(
                mockGeneratedArchetype);

        registryHandler = new RegistryHandler(mockPersistentValuesHandler, mockRegistryFactory);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new RegistryHandler(null, mockRegistryFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new RegistryHandler(mockPersistentValuesHandler, null));
    }

    @Test
    public void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> registryHandler.write(null));
    }

    @Test
    public void testWrite() {
        var output = registryHandler.write(mockRegistry);

        assertEquals(DATA_STRING, output);
        verify(mockPersistentValuesHandler, times(1)).getTypeHandler(INTERNAL_TYPE);
        //noinspection unchecked
        verify(mockHasIdHandler, times(1)).write(mockHasId1);
        //noinspection unchecked
        verify(mockHasIdHandler, times(1)).write(mockHasId2);
        //noinspection unchecked
        verify(mockHasIdHandler, times(1)).write(mockHasId3);
    }

    @Test
    public void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> registryHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> registryHandler.read(""));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testRead() {
        Registry<HasId> readValue = registryHandler.read(DATA_STRING);

        assertNotNull(readValue);
        assertSame(mockRegistry, readValue);
        verify(mockPersistentValuesHandler, times(1)).getTypeHandler(INTERNAL_TYPE);
        verify(mockPersistentValuesHandler, times(1)).generateArchetype(INTERNAL_TYPE);
        verify(mockRegistryFactory, times(1)).make(mockGeneratedArchetype);
        verify(mockHasIdHandler, times(1)).read(VALUE_1);
        verify(mockHasIdHandler, times(1)).read(VALUE_2);
        verify(mockHasIdHandler, times(1)).read(VALUE_3);
        verify(mockRegistry, times(1)).add(mockHasId1);
        verify(mockRegistry, times(1)).add(mockHasId2);
        verify(mockRegistry, times(1)).add(mockHasId3);
    }

    @Test
    public void testGenerateArchetypeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                registryHandler.generateArchetype(null));
        assertThrows(IllegalArgumentException.class, () ->
                registryHandler.generateArchetype(""));
    }

    @Test
    public void testGenerateArchetype() {
        //noinspection unchecked
        Registry<HasId> generatedArchetype = registryHandler.generateArchetype(INTERNAL_TYPE);

        assertNotNull(generatedArchetype);
        assertSame(mockRegistry, generatedArchetype);
        verify(mockPersistentValuesHandler, times(1)).generateArchetype(INTERNAL_TYPE);
        verify(mockRegistryFactory, times(1)).make(mockGeneratedArchetype);
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Registry.class.getCanonicalName() + ">",
                registryHandler.getInterfaceName());
    }

    @Test
    public void testArchetype() {
        assertNotNull(registryHandler.archetype());
        assertEquals(Registry.class.getCanonicalName(),
                registryHandler.archetype().getInterfaceName());
    }

    @Test
    public void testHashCode() {
        assertEquals((TypeHandler.class.getCanonicalName() + "<" +
                        Registry.class.getCanonicalName() + ">").hashCode(),
                registryHandler.hashCode());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    public void testEquals() {
        var equalHandler = new RegistryHandler(mockPersistentValuesHandler, mockRegistryFactory);
        TypeHandler<Registry> unequalHandler = mock(TypeHandler.class);

        assertEquals(registryHandler, equalHandler);
        assertNotEquals(registryHandler, unequalHandler);
        assertNotEquals(null, registryHandler);
    }

    @Test
    public void testToString() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Registry.class.getCanonicalName() + ">",
                registryHandler.toString());
    }
}
