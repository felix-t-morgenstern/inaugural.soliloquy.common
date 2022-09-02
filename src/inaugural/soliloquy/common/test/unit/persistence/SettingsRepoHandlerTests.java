package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.SettingsRepoHandler;
import inaugural.soliloquy.common.test.fakes.FakePersistentValuesHandler;
import inaugural.soliloquy.common.test.fakes.FakeSettingsRepo;
import inaugural.soliloquy.common.test.fakes.FakeSettingsRepoHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.SettingsRepo;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SettingsRepoHandlerTests {
    private final SettingsRepo SETTINGS_REPO = new FakeSettingsRepo();
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER =
            new FakePersistentValuesHandler();

    private final String VALUES_STRING =
            "[{\"id\":\"setting1Id\",\"serializedValue\":\"setting1Value\"}," +
                    "{\"id\":\"setting2Id\",\"serializedValue\":\"123123\"}]";

    private TypeHandler<SettingsRepo> _settingsRepoHandler;

    @BeforeEach
    void setUp() {
        _settingsRepoHandler =
                new SettingsRepoHandler(PERSISTENT_VALUES_HANDLER, SETTINGS_REPO);
    }

    @Test
    void testGetInterface() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        SettingsRepo.class.getCanonicalName() + ">",
                _settingsRepoHandler.getInterfaceName());
    }

    @Test
    void testWrite() {
        assertEquals(VALUES_STRING, _settingsRepoHandler.write(SETTINGS_REPO));
    }

    @Test
    void testWriteWithInvalidParameters() {
        assertThrows(IllegalArgumentException.class,
                () -> _settingsRepoHandler.write(null));
    }

    @SuppressWarnings("rawtypes")
    @Test
    void testRead() {
        SettingsRepo settingsRepo = _settingsRepoHandler.read(VALUES_STRING);

        assertSame(SETTINGS_REPO, settingsRepo);

        List<Setting> settings = SETTINGS_REPO.getAllUngroupedRepresentation();
        assertEquals(2, settings.size());
        for (Setting setting : settings) {
            switch (setting.getName()) {
                case FakeSettingsRepo.SETTING_1_NAME:
                    assertEquals(FakeSettingsRepo.SETTING_1_VALUE, setting.getValue());
                    break;
                case FakeSettingsRepo.SETTING_2_NAME:
                    assertEquals(FakeSettingsRepo.SETTING_2_VALUE, setting.getValue());
                    break;
                default:
                    fail("Invalid setting name");
            }
        }
    }

    @Test
    void testReadForNonexistentSetting() {
        assertThrows(IllegalArgumentException.class,
                () -> _settingsRepoHandler.read(
                        "[{\"id\":\"InvalidName\",\"valueString\":\"setting1Value\"}," +
                                "{\"id\":\"setting2Name\",\"valueString\":\"123123\"}]"));
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _settingsRepoHandler.read(null));
        assertThrows(IllegalArgumentException.class,
                () -> _settingsRepoHandler.read(""));
    }

    @Test
    void testHashCode() {
        assertEquals((TypeHandler.class.getCanonicalName() + "<" +
                        SettingsRepo.class.getCanonicalName() + ">").hashCode(),
                _settingsRepoHandler.hashCode());
    }

    @Test
    void testEquals() {
        TypeHandler<SettingsRepo> equalHandler =
                new SettingsRepoHandler(PERSISTENT_VALUES_HANDLER, SETTINGS_REPO);
        TypeHandler<SettingsRepo> unequalHandler = new FakeSettingsRepoHandler();

        assertEquals(_settingsRepoHandler, equalHandler);
        assertNotEquals(_settingsRepoHandler, unequalHandler);
        assertNotEquals(null, _settingsRepoHandler);
    }

    @Test
    void testToString() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        SettingsRepo.class.getCanonicalName() + ">",
                _settingsRepoHandler.toString());
    }
}
