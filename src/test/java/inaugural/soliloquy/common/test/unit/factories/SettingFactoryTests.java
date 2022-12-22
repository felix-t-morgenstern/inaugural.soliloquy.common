package inaugural.soliloquy.common.test.unit.factories;

import inaugural.soliloquy.common.factories.SettingFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.SettingFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.VariableCache;

import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SettingFactoryTests {
    private SettingFactoryImpl settingFactory;

    @BeforeEach
    void setUp() {
        settingFactory = new SettingFactoryImpl();
    }

    @Test
    void testMake() {
        VariableCache settingControlParams = mock(VariableCache.class);
        Integer settingValue = 123;
        String settingName = "SettingName";
        String settingId = "SettingId";
        Setting<Integer> setting = settingFactory.make(settingId, settingName, settingValue,
                settingControlParams);
        assertEquals(setting.id(), settingId);
        assertEquals(setting.getName(), settingName);
        assertSame(setting.getValue(), settingValue);
        assertSame(setting.controlParams(), settingControlParams);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(SettingFactory.class.getCanonicalName(), settingFactory.getInterfaceName());
    }

    @Test
    void testArchetypeWithNullArchetype() {
        //noinspection unchecked
        List<String> archetype = mock(List.class);
        when(archetype.getArchetype()).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> settingFactory
                .make(randomString(), randomString(), archetype, mock(VariableCache.class)));
    }

    @Test
    void testHashCode() {
        assertEquals(SettingFactoryImpl.class.getCanonicalName().hashCode(),
                settingFactory.hashCode());
    }

    @Test
    void testEquals() {
        SettingFactory equalSettingFactory = new SettingFactoryImpl();
        SettingFactory unequalSettingFactory = mock(SettingFactory.class);

        assertEquals(equalSettingFactory, settingFactory);
        assertNotEquals(unequalSettingFactory, settingFactory);
        assertNotEquals(null, settingFactory);
    }

    @Test
    void testToString() {
        assertEquals(SettingFactoryImpl.class.getCanonicalName(),
                settingFactory.toString());
    }
}
