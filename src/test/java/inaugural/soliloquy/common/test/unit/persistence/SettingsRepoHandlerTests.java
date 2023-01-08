package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.SettingsRepoHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.SettingsRepo;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Pair;

import java.util.ArrayList;
import java.util.Map;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.testing.Mock.generateMockPersistentValuesHandlerWithSimpleHandlers;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SettingsRepoHandlerTests {
    private final String SETTING_1_ID = randomString();
    private final String SETTING_1_NAME = randomString();
    private final String SETTING_1_VALUE = randomString();

    private final String SETTING_2_ID = randomString();
    private final String SETTING_2_NAME = randomString();
    private final Integer SETTING_2_VALUE = randomInt();

    private final String SETTINGS_REPO_INTERFACE_NAME = randomString();

    @SuppressWarnings("rawtypes")
    private final Pair<PersistentValuesHandler, Map<String, TypeHandler>>
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS =
            generateMockPersistentValuesHandlerWithSimpleHandlers(
                    new String[]{SETTING_1_VALUE},
                    new Integer[]{SETTING_2_VALUE});
    private final PersistentValuesHandler MOCK_PERSISTENT_VALUES_HANDLER =
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS.getItem1();
    @SuppressWarnings("rawtypes") private final TypeHandler MOCK_STRING_HANDLER =
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS.getItem2()
                    .get(String.class.getCanonicalName());
    @SuppressWarnings("rawtypes") private final TypeHandler MOCK_INTEGER_HANDLER =
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS.getItem2()
                    .get(Integer.class.getCanonicalName());

    @SuppressWarnings("rawtypes") @Mock private Setting mockSetting1;
    @SuppressWarnings("rawtypes") @Mock private Setting mockSetting2;
    @Mock private SettingsRepo mockSettingsRepo;

    private final String VALUES_STRING = String.format(
            "[{\"id\":\"%s\",\"serializedValue\":\"%s\"},{\"id\":\"%s\"," +
                    "\"serializedValue\":\"%d\"}]",
            SETTING_1_ID, SETTING_1_VALUE, SETTING_2_ID, SETTING_2_VALUE);

    private TypeHandler<SettingsRepo> settingsRepoHandler;

    @BeforeEach
    void setUp() {
        mockSetting1 = mock(Setting.class);
        when(mockSetting1.id()).thenReturn(SETTING_1_ID);
        when(mockSetting1.getName()).thenReturn(SETTING_1_NAME);
        when(mockSetting1.getValue()).thenReturn(SETTING_1_VALUE);
        when(mockSetting1.getArchetype()).thenReturn(randomString());

        mockSetting2 = mock(Setting.class);
        when(mockSetting2.id()).thenReturn(SETTING_2_ID);
        when(mockSetting2.getName()).thenReturn(SETTING_2_NAME);
        when(mockSetting2.getValue()).thenReturn(SETTING_2_VALUE);
        when(mockSetting2.getArchetype()).thenReturn(randomInt());

        mockSettingsRepo = mock(SettingsRepo.class);
        when(mockSettingsRepo.getAllUngroupedRepresentation()).thenReturn(new ArrayList<>() {{
            add(mockSetting1);
            add(mockSetting2);
        }});
        //noinspection unchecked
        when(mockSettingsRepo.getSetting(SETTING_1_ID)).thenReturn(mockSetting1);
        //noinspection unchecked
        when(mockSettingsRepo.getSetting(SETTING_2_ID)).thenReturn(mockSetting2);
        when(mockSettingsRepo.getInterfaceName()).thenReturn(SETTINGS_REPO_INTERFACE_NAME);

        settingsRepoHandler =
                new SettingsRepoHandler(MOCK_PERSISTENT_VALUES_HANDLER, mockSettingsRepo);
    }

    @Test
    void testGetInterface() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        SETTINGS_REPO_INTERFACE_NAME + ">",
                settingsRepoHandler.getInterfaceName());
    }

    @Test
    void testWrite() {
        String output = settingsRepoHandler.write(mockSettingsRepo);

        assertEquals(VALUES_STRING, output);
        verify(MOCK_PERSISTENT_VALUES_HANDLER, times(1))
                .getTypeHandler(String.class.getCanonicalName());
        verify(MOCK_PERSISTENT_VALUES_HANDLER, times(1))
                .getTypeHandler(Integer.class.getCanonicalName());
        //noinspection unchecked
        verify(MOCK_STRING_HANDLER, times(1)).write(SETTING_1_VALUE);
        //noinspection unchecked
        verify(MOCK_INTEGER_HANDLER, times(1)).write(SETTING_2_VALUE);
    }

    @Test
    void testWriteWithInvalidParameters() {
        assertThrows(IllegalArgumentException.class,
                () -> settingsRepoHandler.write(null));
    }

    @Test
    void testRead() {
        SettingsRepo settingsRepo = settingsRepoHandler.read(VALUES_STRING);

        assertSame(mockSettingsRepo, settingsRepo);
        verify(MOCK_STRING_HANDLER, times(1)).read(SETTING_1_VALUE);
        verify(MOCK_INTEGER_HANDLER, times(1)).read(SETTING_2_VALUE.toString());
        //noinspection unchecked
        verify(mockSetting1, times(1)).setValue(SETTING_1_VALUE);
        //noinspection unchecked
        verify(mockSetting2, times(1)).setValue(SETTING_2_VALUE);
    }

    @Test
    void testReadForNonexistentSetting() {
        assertThrows(IllegalArgumentException.class,
                () -> settingsRepoHandler.read(
                        "[{\"id\":\"InvalidName\",\"valueString\":\"setting1Value\"}," +
                                "{\"id\":\"setting2Name\",\"valueString\":\"123123\"}]"));
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> settingsRepoHandler.read(null));
        assertThrows(IllegalArgumentException.class,
                () -> settingsRepoHandler.read(""));
    }

    @Test
    void testHashCode() {
        assertEquals((TypeHandler.class.getCanonicalName() + "<" +
                        SETTINGS_REPO_INTERFACE_NAME + ">").hashCode(),
                settingsRepoHandler.hashCode());
    }

    @Test
    void testEquals() {
        TypeHandler<SettingsRepo> equalHandler =
                new SettingsRepoHandler(MOCK_PERSISTENT_VALUES_HANDLER, mockSettingsRepo);
        //noinspection unchecked
        TypeHandler<SettingsRepo> unequalHandler = mock(TypeHandler.class);

        assertEquals(settingsRepoHandler, equalHandler);
        assertNotEquals(settingsRepoHandler, unequalHandler);
        assertNotEquals(null, settingsRepoHandler);
    }

    @Test
    void testToString() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        SETTINGS_REPO_INTERFACE_NAME + ">",
                settingsRepoHandler.toString());
    }
}
