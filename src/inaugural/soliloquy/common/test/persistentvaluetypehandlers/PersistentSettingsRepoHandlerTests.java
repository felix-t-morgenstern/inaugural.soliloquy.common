package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentSettingsRepoHandler;
import inaugural.soliloquy.common.test.stubs.PersistentValuesHandlerStub;
import inaugural.soliloquy.common.test.stubs.SettingsRepoStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.IPersistentValueTypeHandler;
import soliloquy.common.specs.IPersistentValuesHandler;
import soliloquy.common.specs.ISettingsRepo;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PersistentSettingsRepoHandlerTests {
    private final ISettingsRepo SETTINGS_REPO = new SettingsRepoStub();
    private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER =
            new PersistentValuesHandlerStub();

    private final String VALUES_STRING = "setting1Name\u0098setting1Value\u009B" +
            "setting2Name\u0098123123";

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

        HashMap<String,Object> settings = SettingsRepoStub.SETTINGS;
        assertEquals(2, settings.size());
        assertEquals(SettingsRepoStub.SETTING_1_VALUE,
                settings.get(SettingsRepoStub.SETTING_1_NAME));
        assertEquals(SettingsRepoStub.SETTING_2_VALUE,
                settings.get(SettingsRepoStub.SETTING_2_NAME));
    }

    @Test
    void testReadForNonexistentSetting() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentSettingsRepoHandler.read("setting1Name\u0098setting1Value" +
                        "\u009BINVALID_NAME\u0098123123"));
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentSettingsRepoHandler.read("setting1Name\u0098setting1Value" +
                        "\u009Bsetting2Name\u0098123123\u0098invalid_field"));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentSettingsRepoHandler.read("setting1Name\u0098setting1Value" +
                        "\u009Bsetting2Name"));
    }
}
