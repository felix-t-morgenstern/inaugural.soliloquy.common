package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.PersistentValuesHandlerImpl;
import inaugural.soliloquy.common.test.fakes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PersistentValuesHandlerImplTests {
    private PersistentValuesHandlerImpl _persistentValuesHandler;

    private PersistentValueTypeHandler<Integer> _persistentIntegerHandler;
    private PersistentValueTypeHandler<String> _persistentStringHandler;

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        _persistentValuesHandler = new PersistentValuesHandlerImpl();

        _persistentIntegerHandler = (PersistentValueTypeHandler<Integer>) mock(PersistentValueTypeHandler.class);
        when(_persistentIntegerHandler.getArchetype()).thenReturn(0);
        String intParam1StrValue = "123";
        Integer intParam1IntValue = 123;
        when(_persistentIntegerHandler.write(intParam1IntValue)).thenReturn(intParam1StrValue);
        when(_persistentIntegerHandler.read(intParam1StrValue)).thenReturn(intParam1IntValue);
        String intParam2StrValue = "456";
        Integer intParam2IntValue = 456;
        when(_persistentIntegerHandler.write(intParam2IntValue)).thenReturn(intParam2StrValue);
        when(_persistentIntegerHandler.read(intParam2StrValue)).thenReturn(intParam2IntValue);
        String intParam3StrValue = "789";
        Integer intParam3IntValue = 789;
        when(_persistentIntegerHandler.write(intParam3IntValue)).thenReturn(intParam3StrValue);
        when(_persistentIntegerHandler.read(intParam3StrValue)).thenReturn(intParam3IntValue);

        _persistentStringHandler = (PersistentValueTypeHandler<String>) mock(PersistentValueTypeHandler.class);
        when(_persistentStringHandler.getArchetype()).thenReturn("");
        String strParam1Value = "String1";
        when(_persistentStringHandler.write(strParam1Value)).thenReturn(strParam1Value);
        when(_persistentStringHandler.read(strParam1Value)).thenReturn(strParam1Value);
        String strParam2Value = "String2";
        when(_persistentStringHandler.write(strParam2Value)).thenReturn(strParam2Value);
        when(_persistentStringHandler.read(strParam2Value)).thenReturn(strParam2Value);
        String strParam3Value = "String3";
        when(_persistentStringHandler.write(strParam3Value)).thenReturn(strParam3Value);
        when(_persistentStringHandler.read(strParam3Value)).thenReturn(strParam3Value);
    }

    @Test
    void testAddAndGetPersistentValueTypeHandler() {
        _persistentValuesHandler.addPersistentValueTypeHandler(_persistentIntegerHandler);
        assertSame(_persistentValuesHandler.<Integer>getPersistentValueTypeHandler(Integer.class.getCanonicalName()), _persistentIntegerHandler);
    }

    @Test
    void testAddPersistentValueTypeHandlerTwiceException() {
        assertThrows(IllegalArgumentException.class, () -> {
            _persistentValuesHandler.addPersistentValueTypeHandler(_persistentIntegerHandler);
            _persistentValuesHandler.addPersistentValueTypeHandler(_persistentIntegerHandler);
        });
    }

    @Test
    void testRemovePersistentValueTypeHandler() {
        assertFalse(_persistentValuesHandler.removePersistentValueTypeHandler(Integer.class.getCanonicalName()));
        _persistentValuesHandler.addPersistentValueTypeHandler(_persistentIntegerHandler);
        assertSame(_persistentValuesHandler.<Integer>getPersistentValueTypeHandler(Integer.class.getCanonicalName()), _persistentIntegerHandler);
        assertTrue(_persistentValuesHandler.removePersistentValueTypeHandler(Integer.class.getCanonicalName()));
    }

    @Test
    void testAddGetAndRemovePersistentValueTypeHandlerWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentValuesHandler.addPersistentValueTypeHandler(null));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentValuesHandler.removePersistentValueTypeHandler(null));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentValuesHandler.removePersistentValueTypeHandler(""));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentValuesHandler.getPersistentValueTypeHandler(null));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentValuesHandler.getPersistentValueTypeHandler(""));
    }

    @Test
    void testPersistentValueTypesHandled() {
        assertTrue(_persistentValuesHandler.persistentValueTypesHandled().isEmpty());
        _persistentValuesHandler.addPersistentValueTypeHandler(_persistentIntegerHandler);
        _persistentValuesHandler.addPersistentValueTypeHandler(_persistentStringHandler);
        assertEquals(2, _persistentValuesHandler.persistentValueTypesHandled().size());
        assertTrue(_persistentValuesHandler.persistentValueTypesHandled().contains(Integer.class.getCanonicalName()));
        assertTrue(_persistentValuesHandler.persistentValueTypesHandled().contains(String.class.getCanonicalName()));
    }

    @Test
    void testGenerateArchetype() {
        _persistentValuesHandler.addPersistentValueTypeHandler(_persistentIntegerHandler);
        assertNotNull(_persistentValuesHandler.generateArchetype(Integer.class.getCanonicalName()));
    }

    @Test
    void testGenerateArchetypeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentValuesHandler.generateArchetype(null));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentValuesHandler.generateArchetype(""));
    }

    @Test
    void testRegisterPersistentCollectionHandler() {
        PersistentCollectionHandler persistentCollectionHandler =
                new FakePersistentCollectionHandler();
        _persistentValuesHandler.registerPersistentCollectionHandler(persistentCollectionHandler);

        assertSame(persistentCollectionHandler,
                _persistentValuesHandler.getPersistentValueTypeHandler(
                        Collection.class.getCanonicalName()));
        assertSame(persistentCollectionHandler,
                _persistentValuesHandler.getPersistentValueTypeHandler(
                        ReadableCollection.class.getCanonicalName()));
    }

    @Test
    void testRegisterPersistentMapHandler() {
        PersistentMapHandler persistentMapHandler = new FakePersistentMapHandler();
        _persistentValuesHandler.registerPersistentMapHandler(persistentMapHandler);

        assertSame(persistentMapHandler,
                _persistentValuesHandler.getPersistentValueTypeHandler(
                        Map.class.getCanonicalName()));
        assertSame(persistentMapHandler,
                _persistentValuesHandler.getPersistentValueTypeHandler(
                        ReadableMap.class.getCanonicalName()));
    }

    @Test
    void testRegisterPersistentPairHandler() {
        PersistentPairHandler persistentPairHandler = new FakePersistentPairHandler();
        _persistentValuesHandler.registerPersistentPairHandler(persistentPairHandler);

        assertSame(persistentPairHandler,
                _persistentValuesHandler.getPersistentValueTypeHandler(
                        Pair.class.getCanonicalName()));
        assertSame(persistentPairHandler,
                _persistentValuesHandler.getPersistentValueTypeHandler(
                        ReadablePair.class.getCanonicalName()));
    }

    @Test
    void testRegisterPersistentRegistryHandler() {
        PersistentRegistryHandler persistentRegistryHandler = new FakePersistentRegistryHandler();
        _persistentValuesHandler.registerPersistentRegistryHandler(persistentRegistryHandler);

        assertSame(persistentRegistryHandler,
                _persistentValuesHandler.getPersistentValueTypeHandler(
                        Registry.class.getCanonicalName()));
    }

    @Test
    void testRegisterInfrastructureHandlersWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentValuesHandler.registerPersistentCollectionHandler(null));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentValuesHandler.registerPersistentMapHandler(null));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentValuesHandler.registerPersistentPairHandler(null));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentValuesHandler.registerPersistentRegistryHandler(null));
    }

    @Test
    void testHashCode() {
        assertEquals(PersistentValuesHandlerImpl.class.getCanonicalName().hashCode(),
                _persistentValuesHandler.hashCode());
    }

    @Test
    void testEquals() {
        PersistentValuesHandler equalPersistentValuesHandler = new PersistentValuesHandlerImpl();
        PersistentValuesHandler unequalPersistentValuesHandler = new FakePersistentValuesHandler();

        assertEquals(_persistentValuesHandler, equalPersistentValuesHandler);
        assertNotEquals(_persistentValuesHandler, unequalPersistentValuesHandler);
        assertNotEquals(null, _persistentValuesHandler);
    }

    @Test
    void testToString() {
        assertEquals(PersistentValuesHandlerImpl.class.getCanonicalName(),
                _persistentValuesHandler.toString());
    }
}
