package inaugural.soliloquy.common.test.unit.factories;

import inaugural.soliloquy.common.factories.SettingFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.SettingFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.VariableCache;

import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SettingFactoryTests {
    private SettingFactory settingFactory;

    @BeforeEach
    public void setUp() {
        settingFactory = new SettingFactoryImpl();
    }

    @Test
    public void testMake() {
        var settingControlParams = mock(VariableCache.class);
        var settingValue = randomString();
        var settingName = randomString();
        var settingId = randomString();

        var setting = settingFactory.make(settingId, settingName, settingValue, settingControlParams);

        assertEquals(setting.id(), settingId);
        assertEquals(setting.getName(), settingName);
        assertSame(setting.getValue(), settingValue);
        assertSame(setting.controlParams(), settingControlParams);
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(SettingFactory.class.getCanonicalName(), settingFactory.getInterfaceName());
    }

    @Test
    public void testArchetypeWithNullArchetype() {
        //noinspection unchecked
        List<String> archetype = mock(List.class);
        when(archetype.archetype()).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> settingFactory
                .make(randomString(), randomString(), archetype, mock(VariableCache.class)));
    }

    @Test
    public void testHashCode() {
        assertEquals(SettingFactoryImpl.class.getCanonicalName().hashCode(),
                settingFactory.hashCode());
    }

    @Test
    public void testEquals() {
        var equalSettingFactory = new SettingFactoryImpl();
        var unequalSettingFactory = mock(SettingFactory.class);

        assertEquals(equalSettingFactory, settingFactory);
        assertNotEquals(unequalSettingFactory, settingFactory);
        assertNotEquals(null, settingFactory);
    }

    @Test
    public void testToString() {
        assertEquals(SettingFactoryImpl.class.getCanonicalName(),
                settingFactory.toString());
    }
}
