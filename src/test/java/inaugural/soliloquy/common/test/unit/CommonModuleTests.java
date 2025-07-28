package inaugural.soliloquy.common.test.unit;

import inaugural.soliloquy.common.CommonModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.persistence.PersistenceHandler;

import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;

@ExtendWith(MockitoExtension.class)
public class CommonModuleTests {
    // (NB: Taken from MapHandlerTests to increase surety that instance works properly)
    private final String KEY = randomString();
    private final Integer VALUE = randomInt();
    private final String MAP_STRING = String.format(
            "{\"keyType\":\"%s\",\"valueType\":\"%s\",\"keys\":[\"%s\"],\"values\":[\"%d\"]}",
            String.class.getCanonicalName(), Integer.class.getCanonicalName(),
            KEY, VALUE);

    private CommonModule module;

    @BeforeEach
    public void setUp() {
        module = new CommonModule();
    }

    @Test
    public void testProvideClass() {
        var persistenceHandler = module.provide(PersistenceHandler.class);
        var mapHandler = persistenceHandler.getTypeHandler(Map.class.getCanonicalName());
        var map = mapOf(pairOf(KEY, VALUE));
        var mapHandlerOutput = mapHandler.write(map);

        assertNotNull(persistenceHandler);
        assertEquals(MAP_STRING, mapHandlerOutput);
        assertEquals(map, mapHandler.read(MAP_STRING));
    }

    @Test
    public void testProvideInstance() {
        assertThrows(IllegalArgumentException.class, () -> module.provide(randomString()));
    }
}
