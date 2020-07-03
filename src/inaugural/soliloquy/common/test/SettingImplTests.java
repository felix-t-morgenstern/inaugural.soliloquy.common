package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.test.fakes.FakeVariableCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.SettingImpl;
import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.VariableCache;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class SettingImplTests {
    private final String SETTING_ID = "SettingId";
    private final String SETTING_NAME_1 = "SettingName1";
    private final Integer SETTING_VALUE_1 = 123;
    private final Integer SETTING_ARCHETYPE = 789;
    private final VariableCache _settingControlParams = new FakeVariableCache();

    private SettingImpl<Integer> _setting;

    @BeforeEach
    void setUp() {
        _setting = new SettingImpl<>(SETTING_ID, SETTING_NAME_1, SETTING_VALUE_1,
                SETTING_ARCHETYPE, _settingControlParams);
    }

    @Test
    void testWithInvalidConstructorParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new SettingImpl<>(null,
                        SETTING_NAME_1,
                        SETTING_VALUE_1,
                        SETTING_ARCHETYPE,
                        _settingControlParams));
        assertThrows(IllegalArgumentException.class,
                () -> new SettingImpl<>("",
                        SETTING_NAME_1,
                        SETTING_VALUE_1,
                        SETTING_ARCHETYPE,
                        _settingControlParams));
        assertThrows(IllegalArgumentException.class,
                () -> new SettingImpl<>(SETTING_ID,
                        null,
                        SETTING_VALUE_1,
                        SETTING_ARCHETYPE,
                        _settingControlParams));
        assertThrows(IllegalArgumentException.class,
                () -> new SettingImpl<>(SETTING_ID,
                        "",
                        SETTING_VALUE_1,
                        SETTING_ARCHETYPE,
                        _settingControlParams));
        assertThrows(IllegalArgumentException.class,
                () -> new SettingImpl<>(SETTING_ID,
                        SETTING_NAME_1,
                        SETTING_VALUE_1,
                        null,
                        _settingControlParams));
        assertThrows(IllegalArgumentException.class,
                () -> new SettingImpl<>(SETTING_ID,
                        SETTING_NAME_1,
                        SETTING_VALUE_1,
                        SETTING_ARCHETYPE,
                        null));
    }
    
    @Test
    void testId() {
        assertEquals(_setting.id(), SETTING_ID);
    }

    @Test
    void testName() {
        assertEquals(_setting.getName(), SETTING_NAME_1);
        String settingName2 = "settingName2";
        _setting.setName(settingName2);
        assertEquals(_setting.getName(), settingName2);
    }

    @Test
    void testSetNameWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _setting.setName(null));
        assertThrows(IllegalArgumentException.class, () -> _setting.setName(""));
    }

    @Test
    void testGetArchetype() {
        assertSame(_setting.getArchetype(), SETTING_ARCHETYPE);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(Setting.class.getCanonicalName(), _setting.getInterfaceName());
    }

    @Test
    void testGetValue() {
        assertSame(_setting.getValue(), SETTING_VALUE_1);
    }

    @Test
    void testSetValue() {
        Integer settingValue2 = 456;
        _setting.setValue(settingValue2);
        assertSame(_setting.getValue(), settingValue2);
    }

    @Test
    void testControlParams() {
        assertSame(_setting.controlParams(), _settingControlParams);
    }

    @Test
    void testHashCode() {
        assertEquals(Objects.hash(SETTING_ID, SETTING_NAME_1, SETTING_VALUE_1,
                _settingControlParams),
                _setting.hashCode());
    }

    @SuppressWarnings("rawtypes")
    @Test
    void testEquals() {
        Setting setting2 = new SettingImpl<>(SETTING_ID, SETTING_NAME_1, SETTING_VALUE_1,
                SETTING_ARCHETYPE, _settingControlParams);

        assertEquals(_setting, setting2);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void testToString() {
        assertThrows(UnsupportedOperationException.class, _setting::toString);
    }
}
