package inaugural.soliloquy.common.test.unit.factories;

import inaugural.soliloquy.common.factories.SettingFactoryImpl;
import inaugural.soliloquy.common.test.fakes.FakeList;
import inaugural.soliloquy.common.test.fakes.FakeSettingFactory;
import inaugural.soliloquy.common.test.fakes.FakeVariableCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.SettingFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.VariableCache;

import static org.junit.jupiter.api.Assertions.*;

class SettingFactoryTests {
    private SettingFactoryImpl _settingFactory;

    @BeforeEach
    void setUp() {
        _settingFactory = new SettingFactoryImpl();
    }

    @Test
    void testMake() {
        VariableCache settingControlParams = new FakeVariableCache();
        Integer settingValue = 123;
        String settingName = "SettingName";
        String settingId = "SettingId";
        Setting<Integer> setting = _settingFactory.make(settingId, settingName, settingValue,
                settingControlParams);
        assertEquals(setting.id(), settingId);
        assertEquals(setting.getName(), settingName);
        assertSame(setting.getValue(), settingValue);
        assertSame(setting.controlParams(), settingControlParams);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(SettingFactory.class.getCanonicalName(), _settingFactory.getInterfaceName());
    }

    @Test
    void testArchetypeWithNullArchetype() {
        List<String> archetype = new FakeList<>((String) null);

        assertThrows(IllegalArgumentException.class, () -> _settingFactory.make("settingId",
                "settingName", archetype, new FakeVariableCache()));
    }

    @Test
    void testHashCode() {
        assertEquals(SettingFactoryImpl.class.getCanonicalName().hashCode(),
                _settingFactory.hashCode());
    }

    @Test
    void testEquals() {
        SettingFactory equalSettingFactory = new SettingFactoryImpl();
        SettingFactory unequalSettingFactory = new FakeSettingFactory();

        assertEquals(equalSettingFactory, _settingFactory);
        assertNotEquals(unequalSettingFactory, _settingFactory);
        assertNotEquals(null, _settingFactory);
    }

    @Test
    void testToString() {
        assertEquals(SettingFactoryImpl.class.getCanonicalName(),
                _settingFactory.toString());
    }
}
