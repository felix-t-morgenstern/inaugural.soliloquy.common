package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.PersistentSettingsRepoHandler;
import inaugural.soliloquy.common.test.fakes.FakePersistentSettingsRepoHandler;
import inaugural.soliloquy.common.test.fakes.FakePersistentValuesHandler;
import inaugural.soliloquy.common.test.fakes.FakeSettingsRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.SettingsRepo;
import soliloquy.specs.common.persistence.PersistentValueTypeHandler;
import soliloquy.specs.common.persistence.PersistentValuesHandler;

import static org.junit.jupiter.api.Assertions.*;

class PersistentSettingsRepoHandlerTests {
    private final SettingsRepo SETTINGS_REPO = new FakeSettingsRepo();
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER =
            new FakePersistentValuesHandler();

    private final String VALUES_STRING =
            "[{\"id\":\"setting1Id\",\"serializedValue\":\"setting1Value\"}," +
                    "{\"id\":\"setting2Id\",\"serializedValue\":\"123123\"}]";

    private PersistentValueTypeHandler<SettingsRepo> _persistentSettingsRepoHandler;

    @BeforeEach
    void setUp() {
        _persistentSettingsRepoHandler =
                new PersistentSettingsRepoHandler(PERSISTENT_VALUES_HANDLER, SETTINGS_REPO);
    }

    @Test
    void testGetInterface() {
        assertEquals(PersistentValueTypeHandler.class.getCanonicalName() + "<" +
                SettingsRepo.class.getCanonicalName() + ">",
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

    @SuppressWarnings("rawtypes")
    @Test
    void testRead() {
        SettingsRepo settingsRepo = _persistentSettingsRepoHandler.read(VALUES_STRING);

        assertSame(SETTINGS_REPO, settingsRepo);

        List<Setting> settings = SETTINGS_REPO.getAllUngroupedRepresentation();
        assertEquals(2, settings.size());
        for(Setting setting : settings) {
            switch(setting.getName()) {
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
                () -> _persistentSettingsRepoHandler.read(
                        "[{\"id\":\"InvalidName\",\"valueString\":\"setting1Value\"}," +
                        "{\"id\":\"setting2Name\",\"valueString\":\"123123\"}]"));
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentSettingsRepoHandler.read(null));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentSettingsRepoHandler.read(""));
    }

    @Test
    void testHashCode() {
        assertEquals(PersistentSettingsRepoHandler.class.getCanonicalName().hashCode(),
                _persistentSettingsRepoHandler.hashCode());
    }

    @Test
    void testEquals() {
        PersistentValueTypeHandler<SettingsRepo> equalHandler =
                new PersistentSettingsRepoHandler(PERSISTENT_VALUES_HANDLER, SETTINGS_REPO);
        PersistentValueTypeHandler<SettingsRepo> unequalHandler =
                new FakePersistentSettingsRepoHandler();

        assertEquals(_persistentSettingsRepoHandler, equalHandler);
        assertNotEquals(_persistentSettingsRepoHandler, unequalHandler);
        assertNotEquals(null, _persistentSettingsRepoHandler);
    }

    @Test
    void testToString() {
        assertEquals(PersistentSettingsRepoHandler.class.getCanonicalName(),
                _persistentSettingsRepoHandler.toString());
    }
}
