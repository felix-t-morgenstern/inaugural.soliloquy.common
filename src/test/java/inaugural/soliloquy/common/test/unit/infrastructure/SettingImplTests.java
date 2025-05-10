package inaugural.soliloquy.common.test.unit.infrastructure;

import inaugural.soliloquy.common.infrastructure.SettingImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.VariableCache;

import java.util.Objects;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SettingImplTests {
    private final String SETTING_ID = randomString();
    private final String SETTING_NAME_1 = randomString();
    private final Integer SETTING_VALUE_1 = randomInt();
    private final Integer SETTING_ARCHETYPE = randomInt();

    @Mock private VariableCache settingControlParams;

    private Setting<Integer> setting;

    @BeforeEach
    public void setUp() {
        setting = new SettingImpl<>(SETTING_ID, SETTING_NAME_1, SETTING_VALUE_1,
                SETTING_ARCHETYPE, settingControlParams);
    }

    @Test
    public void testWithInvalidConstructorParams() {
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
    public void testId() {
        assertEquals(setting.id(), SETTING_ID);
    }

    @Test
    public void testName() {
        assertEquals(setting.getName(), SETTING_NAME_1);
        var settingName2 = randomString();

        setting.setName(settingName2);

        assertEquals(setting.getName(), settingName2);
    }

    @Test
    public void testSetNameWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> setting.setName(null));
        assertThrows(IllegalArgumentException.class, () -> setting.setName(""));
    }

    @Test
    public void testArchetype() {
        assertSame(setting.archetype(), SETTING_ARCHETYPE);
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(Setting.class.getCanonicalName(), setting.getInterfaceName());
    }

    @Test
    public void testGetValue() {
        assertSame(setting.getValue(), SETTING_VALUE_1);
    }

    @Test
    public void testSetValue() {
        Integer settingValue2 = randomInt();

        setting.setValue(settingValue2);

        assertEquals(setting.getValue(), settingValue2);
    }

    @Test
    public void testControlParams() {
        assertSame(setting.controlParams(), settingControlParams);
    }

    @Test
    public void testHashCode() {
        assertEquals(Objects.hash(SETTING_ID, SETTING_NAME_1, SETTING_VALUE_1,
                settingControlParams),
                setting.hashCode());
    }

    @Test
    public void testEquals() {
        var setting2 = new SettingImpl<>(SETTING_ID, SETTING_NAME_1, SETTING_VALUE_1,
                SETTING_ARCHETYPE, settingControlParams);

        assertEquals(setting, setting2);
    }

    @Test
    public void testToString() {
        assertThrows(UnsupportedOperationException.class, setting::toString);
    }
}
