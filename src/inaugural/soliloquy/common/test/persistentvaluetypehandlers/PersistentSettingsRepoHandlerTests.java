package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentSettingsRepoHandler;
import inaugural.soliloquy.common.test.stubs.PersistentValuesHandlerStub;
import inaugural.soliloquy.common.test.stubs.SettingsRepoStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PersistentSettingsRepoHandlerTests {
    private final ISettingsRepo SETTINGS_REPO = new SettingsRepoStub();
    private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER =
            new PersistentValuesHandlerStub();

    private final String VALUES_STRING =
            "[{\"name\":\"setting1Name\",\"valueString\":\"setting1Value\"}," +
                    "{\"name\":\"setting2Name\",\"valueString\":\"123123\"}]";

    private IPersistentValueTypeHandler<ISettingsRepo> _persistentSettingsRepoHandler;

    @BeforeEach
    void setUp() {
        _persistentSettingsRepoHandler = new PersistentSettingsRepoHandler(SETTINGS_REPO,
                PERSISTENT_VALUES_HANDLER);
    }

    @Test
    void testGetInterface() {
        assertEquals(IPersistentValueTypeHandler.class.getCanonicalName() + "<" +
                ISettingsRepo.class.getCanonicalName() + ">",
                _persistentSettingsRepoHandler.getInterfaceName());
    }

    @Test
    void testWrite() {
        assertEquals(VALUES_STRING, _persistentSettingsRepoHandler.write(SETTINGS_REPO));
    }

    @Test
    void testWriteWithInvalidParameters() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentSettingsRepoHandler.write(null));
    }

    @Test
    void testRead() {
        ISettingsRepo settingsRepo = _persistentSettingsRepoHandler.read(VALUES_STRING);

        assertSame(SETTINGS_REPO, settingsRepo);

        ICollection<ISetting> settings = SETTINGS_REPO.getAllUngrouped();
        assertEquals(2, settings.size());
        for(ISetting setting : settings) {
            switch(setting.getName()) {
                case SettingsRepoStub.SETTING_1_NAME:
                    assertEquals(SettingsRepoStub.SETTING_1_VALUE, setting.getValue());
                    break;
                case SettingsRepoStub.SETTING_2_NAME:
                    assertEquals(SettingsRepoStub.SETTING_2_VALUE, setting.getValue());
                    break;
                default:
                    fail("Invalid setting name");
            }
        }
    }

    @Test
    void testReadForNonexistentSetting() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentSettingsRepoHandler.read(
                        "[{\"name\":\"InvalidName\",\"valueString\":\"setting1Value\"}," +
                        "{\"name\":\"setting2Name\",\"valueString\":\"123123\"}]"));
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentSettingsRepoHandler.read(null));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentSettingsRepoHandler.read(""));
    }
}
