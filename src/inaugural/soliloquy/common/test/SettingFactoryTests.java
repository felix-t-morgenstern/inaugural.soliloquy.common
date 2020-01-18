package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.SettingFactoryImpl;
import inaugural.soliloquy.common.test.stubs.CollectionStub;
import inaugural.soliloquy.common.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.SettingFactory;
import soliloquy.specs.common.infrastructure.Collection;
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
        VariableCache settingControlParams = new VariableCacheStub();
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

    @SuppressWarnings("unchecked")
    @Test
    void testArchetypeWithNullArchetype() {
        Collection archetype = new CollectionStub(null);

        assertThrows(IllegalArgumentException.class, () -> _settingFactory.make("settingId",
                "settingName", archetype, new VariableCacheStub()));
    }
}
