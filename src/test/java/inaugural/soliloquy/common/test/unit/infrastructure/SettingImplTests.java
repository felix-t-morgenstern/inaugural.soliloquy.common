package inaugural.soliloquy.common.test.unit.infrastructure;

import inaugural.soliloquy.common.infrastructure.SettingImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.VariableCache;

import java.util.Objects;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SettingImplTests {
    private final String SETTING_ID = randomString();
    private final String SETTING_NAME_1 = randomString();
    private final Integer SETTING_VALUE_1 = randomInt();
    private final Integer SETTING_ARCHETYPE = randomInt();

    @Mock private VariableCache settingControlParams;

    private SettingImpl<Integer> setting;

    @BeforeEach
    void setUp() {
        settingControlParams = mock(VariableCache.class);

        setting = new SettingImpl<>(SETTING_ID, SETTING_NAME_1, SETTING_VALUE_1,
                SETTING_ARCHETYPE, settingControlParams);
    }

    @Test
    void testWithInvalidConstructorParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new SettingImpl<>(null,
                        SETTING_NAME_1,
                        SETTING_VALUE_1,
                        SETTING_ARCHETYPE,
                        settingControlParams));
        assertThrows(IllegalArgumentException.class,
                () -> new SettingImpl<>("",
                        SETTING_NAME_1,
                        SETTING_VALUE_1,
                        SETTING_ARCHETYPE,
                        settingControlParams));
        assertThrows(IllegalArgumentException.class,
                () -> new SettingImpl<>(SETTING_ID,
                        null,
                        SETTING_VALUE_1,
                        SETTING_ARCHETYPE,
                        settingControlParams));
        assertThrows(IllegalArgumentException.class,
                () -> new SettingImpl<>(SETTING_ID,
                        "",
                        SETTING_VALUE_1,
                        SETTING_ARCHETYPE,
                        settingControlParams));
        assertThrows(IllegalArgumentException.class,
                () -> new SettingImpl<>(SETTING_ID,
                        SETTING_NAME_1,
                        SETTING_VALUE_1,
                        null,
                        settingControlParams));
        assertThrows(IllegalArgumentException.class,
                () -> new SettingImpl<>(SETTING_ID,
                        SETTING_NAME_1,
                        SETTING_VALUE_1,
                        SETTING_ARCHETYPE,
                        null));
    }

    @Test
    void testId() {
        assertEquals(setting.id(), SETTING_ID);
    }

    @Test
    void testName() {
        assertEquals(setting.getName(), SETTING_NAME_1);
        String settingName2 = "settingName2";
        setting.setName(settingName2);
        assertEquals(setting.getName(), settingName2);
    }

    @Test
    void testSetNameWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> setting.setName(null));
        assertThrows(IllegalArgumentException.class, () -> setting.setName(""));
    }

    @Test
    void testGetArchetype() {
        assertSame(setting.getArchetype(), SETTING_ARCHETYPE);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(Setting.class.getCanonicalName(), setting.getInterfaceName());
    }

    @Test
    void testGetValue() {
        assertSame(setting.getValue(), SETTING_VALUE_1);
    }

    @Test
    void testSetValue() {
        Integer settingValue2 = 456;
        setting.setValue(settingValue2);
        assertSame(setting.getValue(), settingValue2);
    }

    @Test
    void testControlParams() {
        assertSame(setting.controlParams(), settingControlParams);
    }

    @Test
    void testHashCode() {
        assertEquals(Objects.hash(SETTING_ID, SETTING_NAME_1, SETTING_VALUE_1,
                settingControlParams),
                setting.hashCode());
    }

    @SuppressWarnings("rawtypes")
    @Test
    void testEquals() {
        Setting setting2 = new SettingImpl<>(SETTING_ID, SETTING_NAME_1, SETTING_VALUE_1,
                SETTING_ARCHETYPE, settingControlParams);

        assertEquals(setting, setting2);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void testToString() {
        assertThrows(UnsupportedOperationException.class, setting::toString);
    }
}
