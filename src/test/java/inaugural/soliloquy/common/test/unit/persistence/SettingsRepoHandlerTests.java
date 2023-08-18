package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.SettingsRepoHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.SettingsRepo;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Pair;

import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.arrayOf;
import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.testing.Mock.generateMockPersistentValuesHandlerWithSimpleHandlers;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SettingsRepoHandlerTests {
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
                    arrayOf(SETTING_1_VALUE),
                    arrayOf(SETTING_2_VALUE));
    private final PersistentValuesHandler MOCK_PERSISTENT_VALUES_HANDLER =
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS.item1();
    @SuppressWarnings("rawtypes") private final TypeHandler MOCK_STRING_HANDLER =
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS.item2()
                    .get(String.class.getCanonicalName());
    @SuppressWarnings("rawtypes") private final TypeHandler MOCK_INTEGER_HANDLER =
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS.item2()
                    .get(Integer.class.getCanonicalName());

    @SuppressWarnings("rawtypes") @Mock private Setting mockSetting1;
    @SuppressWarnings("rawtypes") @Mock private Setting mockSetting2;
    @Mock private SettingsRepo mockSettingsRepo;

    private final String VALUES_STRING = String.format(
            "[{\"id\":\"%s\",\"value\":\"%s\"},{\"id\":\"%s\",\"value\":\"%d\"}]",
            SETTING_1_ID, SETTING_1_VALUE, SETTING_2_ID, SETTING_2_VALUE);

    private TypeHandler<SettingsRepo> settingsRepoHandler;

    @Before
    public void setUp() {
        when(mockSetting1.id()).thenReturn(SETTING_1_ID);
        when(mockSetting1.getValue()).thenReturn(SETTING_1_VALUE);
        when(mockSetting1.archetype()).thenReturn(randomString());

        when(mockSetting2.id()).thenReturn(SETTING_2_ID);
        when(mockSetting2.getValue()).thenReturn(SETTING_2_VALUE);
        when(mockSetting2.archetype()).thenReturn(randomInt());

        mockSettingsRepo = mock(SettingsRepo.class);
        when(mockSettingsRepo.getAllUngroupedRepresentation()).thenReturn(
                listOf(mockSetting1, mockSetting2));
        //noinspection unchecked
        when(mockSettingsRepo.getSetting(SETTING_1_ID)).thenReturn(mockSetting1);
        //noinspection unchecked
        when(mockSettingsRepo.getSetting(SETTING_2_ID)).thenReturn(mockSetting2);
        when(mockSettingsRepo.getInterfaceName()).thenReturn(SETTINGS_REPO_INTERFACE_NAME);

        settingsRepoHandler =
                new SettingsRepoHandler(MOCK_PERSISTENT_VALUES_HANDLER, mockSettingsRepo);
    }

    @Test
    public void testGetInterface() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        SETTINGS_REPO_INTERFACE_NAME + ">",
                settingsRepoHandler.getInterfaceName());
    }

    @Test
    public void testWrite() {
        var output = settingsRepoHandler.write(mockSettingsRepo);

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
    public void testWriteWithInvalidParameters() {
        assertThrows(IllegalArgumentException.class,
                () -> settingsRepoHandler.write(null));
    }

    @Test
    public void testRead() {
        var settingsRepo = settingsRepoHandler.read(VALUES_STRING);

        assertSame(mockSettingsRepo, settingsRepo);
        verify(MOCK_STRING_HANDLER, times(1)).read(SETTING_1_VALUE);
        verify(MOCK_INTEGER_HANDLER, times(1)).read(SETTING_2_VALUE.toString());
        //noinspection unchecked
        verify(mockSetting1, times(1)).setValue(SETTING_1_VALUE);
        //noinspection unchecked
        verify(mockSetting2, times(1)).setValue(SETTING_2_VALUE);
    }

    @Test
    public void testReadForNonexistentSetting() {
        assertThrows(IllegalArgumentException.class,
                () -> settingsRepoHandler.read(
                        "[{\"id\":\"InvalidName\",\"valueString\":\"setting1Value\"}," +
                                "{\"id\":\"setting2Name\",\"valueString\":\"123123\"}]"));
    }

    @Test
    public void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> settingsRepoHandler.read(null));
        assertThrows(IllegalArgumentException.class,
                () -> settingsRepoHandler.read(""));
    }

    @Test
    public void testHashCode() {
        assertEquals((TypeHandler.class.getCanonicalName() + "<" +
                        SETTINGS_REPO_INTERFACE_NAME + ">").hashCode(),
                settingsRepoHandler.hashCode());
    }

    @Test
    public void testEquals() {
        var equalHandler =
                new SettingsRepoHandler(MOCK_PERSISTENT_VALUES_HANDLER, mockSettingsRepo);
        //noinspection unchecked
        TypeHandler<SettingsRepo> unequalHandler = mock(TypeHandler.class);

        assertEquals(settingsRepoHandler, equalHandler);
        assertNotEquals(settingsRepoHandler, unequalHandler);
        assertNotEquals(null, settingsRepoHandler);
    }

    @Test
    public void testToString() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        SETTINGS_REPO_INTERFACE_NAME + ">",
                settingsRepoHandler.toString());
    }
}
