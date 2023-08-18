package inaugural.soliloquy.common.test.unit.infrastructure;

import inaugural.soliloquy.common.infrastructure.SettingsRepoImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.SettingsRepo;
import soliloquy.specs.common.persistence.PersistentValuesHandler;

import java.util.TreeMap;

import static inaugural.soliloquy.tools.random.Random.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SettingsRepoImplTests {
    private final String SETTING_1_ID = randomString();
    private final String SETTING_2_ID = randomString();
    private final String SETTING_3_ID = randomString();

    private final String SETTING_1_NAME = randomString();
    private final String SETTING_2_NAME = randomString();
    private final String SETTING_3_NAME = randomString();

    private final int SETTING_1_ORDER = randomInt();
    private final int SETTING_2_ORDER = randomInt();
    private final int SETTING_3_ORDER = randomInt();

    private final String SETTINGS_REPO_SUBGROUP_1_ID = randomString();
    private final String SETTINGS_REPO_SUBGROUP_1_1_ID = randomString();

    private final Integer SETTING_1_VALUE = randomInt();
    private final Double SETTING_2_VALUE = randomDouble();
    private final String SETTING_3_VALUE = randomString();

    private final Integer SETTING_1_VALUE_ALT = randomInt();

    @Mock private PersistentValuesHandler mockPersistentValuesHandler;

    private SettingsRepoImpl settingsRepo;

    @Before
    public void setUp() {
        mockPersistentValuesHandler = mock(PersistentValuesHandler.class);

        settingsRepo = new SettingsRepoImpl(mockPersistentValuesHandler);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new SettingsRepoImpl(null));
    }

    @Test
    public void testId() {
        settingsRepo.newSubgrouping(0, SETTINGS_REPO_SUBGROUP_1_ID, "");

        var settingsRepo = this.settingsRepo.getSubgrouping(SETTINGS_REPO_SUBGROUP_1_ID);

        assertEquals(SETTINGS_REPO_SUBGROUP_1_ID, settingsRepo.id());
    }

    @Test
    public void testNewSubgroupingWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> settingsRepo.newSubgrouping(-1, SETTINGS_REPO_SUBGROUP_1_ID, null));
        assertThrows(IllegalArgumentException.class,
                () -> settingsRepo.newSubgrouping(0, null, null));
        assertThrows(IllegalArgumentException.class,
                () -> settingsRepo.newSubgrouping(0, "", null));
    }

    @Test
    public void testAddItemToTopLevel() {
        var setting = mock(Setting.class);
        when(setting.id()).thenReturn(SETTING_1_ID);
        when(setting.getValue()).thenReturn(SETTING_1_VALUE);

        settingsRepo.addEntity(setting, 0, null);

        assertEquals(settingsRepo.getSetting(SETTING_1_ID).getValue(), SETTING_1_VALUE);
    }

    @Test
    public void testAddTwoItemsToTopLevelWithSameId() {
        var setting1 = mock(Setting.class);
        when(setting1.id()).thenReturn(SETTING_1_ID);
        when(setting1.getValue()).thenReturn(SETTING_1_VALUE);

        var setting2 = mock(Setting.class);
        when(setting2.id()).thenReturn(SETTING_1_ID);
        when(setting2.getValue()).thenReturn(SETTING_2_VALUE);

        settingsRepo.addEntity(setting1, 0, null);

        assertThrows(IllegalArgumentException.class,
                () -> settingsRepo.addEntity(setting2, 1, null));
    }

    @Test
    public void testAddTwoItemsToTopLevelWithSameOrder() {
        var setting1 = mock(Setting.class);
        when(setting1.id()).thenReturn(SETTING_1_ID);
        when(setting1.getValue()).thenReturn(SETTING_1_VALUE);

        var setting2 = mock(Setting.class);
        when(setting2.id()).thenReturn(SETTING_2_ID);
        when(setting2.getValue()).thenReturn(SETTING_2_VALUE);

        settingsRepo.addEntity(setting1, 0, null);

        assertThrows(IllegalArgumentException.class,
                () -> settingsRepo.addEntity(setting2, 0, null));
    }

    @Test
    public void testAddItemToNonexistentSubGrouping() {
        var setting1 = mock(Setting.class);
        when(setting1.id()).thenReturn(SETTING_1_ID);
        when(setting1.getValue()).thenReturn(SETTING_1_VALUE);

        assertThrows(IllegalArgumentException.class,
                () -> settingsRepo.addEntity(setting1, 0, SETTINGS_REPO_SUBGROUP_1_ID));
    }

    @Test
    public void testAddItemToExistentSubGrouping() {
        settingsRepo.newSubgrouping(0, SETTINGS_REPO_SUBGROUP_1_ID, "");

        var setting1 = mock(Setting.class);
        when(setting1.id()).thenReturn(SETTING_1_ID);
        when(setting1.getValue()).thenReturn(SETTING_1_VALUE);

        settingsRepo.addEntity(setting1, 0, SETTINGS_REPO_SUBGROUP_1_ID);

        var getResult = settingsRepo.getSetting(SETTING_1_ID);

        assertEquals(getResult.getValue(), SETTING_1_VALUE);
    }

    @Test
    public void testSetNonexistentSetting() {
        assertThrows(IllegalArgumentException.class,
                () -> settingsRepo.setSetting(SETTING_1_ID, SETTING_1_VALUE));
    }

    @Test
    public void testGetSettingWithBlankId() {
        assertThrows(IllegalArgumentException.class, () -> settingsRepo.getSetting(""));
    }

    @Test
    public void testSetSettingWithBlankId() {
        assertThrows(IllegalArgumentException.class,
                () -> settingsRepo.setSetting("", SETTING_1_VALUE));
    }

    @Test
    public void testAddSetAndGetSetting() {
        var setting = generateMockSetting(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE);
        settingsRepo.addEntity(setting, 0, null);

        settingsRepo.setSetting(SETTING_1_ID, SETTING_1_VALUE_ALT);

        verify(setting, times(1)).setValue(SETTING_1_VALUE_ALT);
    }

    @Test
    public void testAddSetAndGetSettingInSubgrouping() {
        var setting = generateMockSetting(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE);
        settingsRepo.newSubgrouping(0, SETTINGS_REPO_SUBGROUP_1_ID, null);
        settingsRepo.newSubgrouping(0, SETTINGS_REPO_SUBGROUP_1_1_ID, SETTINGS_REPO_SUBGROUP_1_ID);

        settingsRepo.addEntity(setting, 0, SETTINGS_REPO_SUBGROUP_1_1_ID);
        settingsRepo.setSetting(SETTING_1_ID, SETTING_1_VALUE_ALT);
        settingsRepo.getSetting(SETTING_1_ID).getValue();

        verify(setting, times(1)).setValue(SETTING_1_VALUE_ALT);
        verify(setting, times(1)).getValue();
    }

    @Test
    public void testGetItemByOrder() {
        var setting = generateMockSetting(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE);
        settingsRepo.addEntity(setting, 123, null);
        assertEquals(settingsRepo.getItemByOrder(123).entity().getValue(), SETTING_1_VALUE);
    }

    @Test
    public void testGetNonexistentItemByOrder() {
        assertThrows(IllegalArgumentException.class, () -> settingsRepo.getItemByOrder(123));
    }

    @Test
    public void testGetAllGroupedRepresentation() {
        var setting1 = generateMockSetting(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE);
        var setting2 = generateMockSetting(SETTING_2_ID, SETTING_2_NAME, SETTING_2_VALUE);
        var setting3 = generateMockSetting(SETTING_3_ID, SETTING_3_NAME, SETTING_3_VALUE);

        settingsRepo.addEntity(setting1, 0, null);

        settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_ID, null);

        settingsRepo.addEntity(setting2, 0, SETTINGS_REPO_SUBGROUP_1_ID);

        settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_1_ID, SETTINGS_REPO_SUBGROUP_1_ID);

        settingsRepo.addEntity(setting3, 0, SETTINGS_REPO_SUBGROUP_1_1_ID);

        var topLevelGrouped = settingsRepo.getAllGroupedRepresentation();

        assertEquals(2, topLevelGrouped.size());
        assertEquals(topLevelGrouped.get(0).entity().getValue(), SETTING_1_VALUE);

        var midLevelGrouped = topLevelGrouped.get(1).group().getAllGroupedRepresentation();

        assertEquals(2, midLevelGrouped.size());
        assertEquals(midLevelGrouped.get(0).entity().getValue(), SETTING_2_VALUE);

        var bottomLevelGrouped = midLevelGrouped.get(1).group().getAllGroupedRepresentation();

        assertEquals(1, bottomLevelGrouped.size());
        assertEquals(bottomLevelGrouped.get(0).entity().getValue(), SETTING_3_VALUE);
    }

    @Test
    public void testGetAllUngroupedRepresentation() {
        var setting1 = generateMockSetting(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE);
        var setting2 = generateMockSetting(SETTING_2_ID, SETTING_2_NAME, SETTING_2_VALUE);
        var setting3 = generateMockSetting(SETTING_3_ID, SETTING_3_NAME, SETTING_3_VALUE);

        settingsRepo.addEntity(setting1, 0, null);

        settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_ID, null);

        settingsRepo.addEntity(setting2, 0, SETTINGS_REPO_SUBGROUP_1_ID);

        settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_1_ID, SETTINGS_REPO_SUBGROUP_1_ID);

        settingsRepo.addEntity(setting3, 0, SETTINGS_REPO_SUBGROUP_1_1_ID);

        var allSettingsUngrouped = settingsRepo.getAllUngroupedRepresentation();

        assertEquals(3, allSettingsUngrouped.size());

        var setting1Used = false;
        var setting2Used = false;
        var setting3Used = false;
        for (var setting : allSettingsUngrouped) {
            if (setting.id().equals(setting1.id())
                    && setting.getName().equals(setting1.getName())
                    && setting.getValue().equals(setting1.getValue())) {
                setting1Used = true;
            }
            if (setting.id().equals(setting2.id())
                    && setting.getName().equals(setting2.getName())
                    && setting.getValue().equals(setting2.getValue())) {
                setting2Used = true;
            }
            if (setting.id().equals(setting3.id())
                    && setting.getName().equals(setting3.getName())
                    && setting.getValue().equals(setting3.getValue())) {
                setting3Used = true;
            }
        }
        assertTrue(setting1Used);
        assertTrue(setting2Used);
        assertTrue(setting3Used);
    }

    @Test
    public void testRemoveItem() {
        var setting1 = generateMockSetting(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE);
        var setting2 = generateMockSetting(SETTING_2_ID, SETTING_2_NAME, SETTING_2_VALUE);
        var setting3 = generateMockSetting(SETTING_3_ID, SETTING_3_NAME, SETTING_3_VALUE);

        settingsRepo.addEntity(setting1, 0, null);

        settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_ID, null);

        settingsRepo.addEntity(setting2, 0, SETTINGS_REPO_SUBGROUP_1_ID);

        settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_1_ID, SETTINGS_REPO_SUBGROUP_1_ID);

        settingsRepo.addEntity(setting3, 0, SETTINGS_REPO_SUBGROUP_1_1_ID);

        assertNotNull(settingsRepo.getSetting(SETTING_2_ID));

        settingsRepo.removeItem(SETTING_2_ID);

        assertNull(settingsRepo.getSetting(SETTING_2_ID));
    }

    @Test
    public void testRemoveItemWithNullOrBlankId() {
        assertThrows(IllegalArgumentException.class, () -> settingsRepo.removeItem(null));
        assertThrows(IllegalArgumentException.class, () -> settingsRepo.removeItem(""));
    }

    @Test
    public void testGetGroupingIdAndOrder() {
        var setting1 = generateMockSetting(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE);
        var setting2 = generateMockSetting(SETTING_2_ID, SETTING_2_NAME, SETTING_2_VALUE);
        var setting3 = generateMockSetting(SETTING_3_ID, SETTING_3_NAME, SETTING_3_VALUE);

        settingsRepo.addEntity(setting1, SETTING_1_ORDER, null);

        settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_ID, null);

        settingsRepo.addEntity(setting2, SETTING_2_ORDER, SETTINGS_REPO_SUBGROUP_1_ID);

        settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_1_ID,
                SETTINGS_REPO_SUBGROUP_1_ID);

        settingsRepo.addEntity(setting3, SETTING_3_ORDER, SETTINGS_REPO_SUBGROUP_1_1_ID);

        var setting1GroupingIdAndOrder = settingsRepo.getGroupingIdAndOrder(SETTING_1_ID);
        assertTrue(setting1GroupingIdAndOrder.item1() == null ||
                setting1GroupingIdAndOrder.item1().equals(""));
        assertEquals(SETTING_1_ORDER, (int) setting1GroupingIdAndOrder.item2());

        var setting2GroupingIdAndOrder = settingsRepo.getGroupingIdAndOrder(SETTING_2_ID);
        assertEquals(setting2GroupingIdAndOrder.item1(), SETTINGS_REPO_SUBGROUP_1_ID);
        assertEquals(SETTING_2_ORDER, (int) setting2GroupingIdAndOrder.item2());

        var setting3GroupingIdAndOrder = settingsRepo.getGroupingIdAndOrder(SETTING_3_ID);
        assertEquals(setting3GroupingIdAndOrder.item1(), SETTINGS_REPO_SUBGROUP_1_1_ID);
        assertEquals(SETTING_3_ORDER, (int) setting3GroupingIdAndOrder.item2());

        assertThrows(IllegalArgumentException.class,
                () -> settingsRepo.getGroupingIdAndOrder("ThisIsNotAnId"));
        assertThrows(IllegalArgumentException.class,
                () -> settingsRepo.getGroupingIdAndOrder(""));
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(SettingsRepo.class.getCanonicalName(), settingsRepo.getInterfaceName());
    }

    @Test
    public void testHashCode() {
        var setting1 = generateMockSetting(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE);
        var setting2 = generateMockSetting(SETTING_2_ID, SETTING_2_NAME, SETTING_2_VALUE);

        // NB: TreeMap is chosen to contrast against the implementation, which uses HashMap
        //noinspection rawtypes
        java.util.Map<String, Setting> comparand = new TreeMap<>();
        comparand.put(SETTING_1_ID, setting1);
        comparand.put(SETTING_2_ID, setting2);

        settingsRepo.newSubgrouping(0, SETTINGS_REPO_SUBGROUP_1_ID, "");
        settingsRepo.addEntity(setting1, SETTING_1_ORDER, null);
        settingsRepo.addEntity(setting2, SETTING_2_ORDER, SETTINGS_REPO_SUBGROUP_1_ID);

        assertEquals(comparand.hashCode(), settingsRepo.hashCode());
    }

    @Test
    public void testEquals() {
        var settingsRepo2 = new SettingsRepoImpl(mockPersistentValuesHandler);
        settingsRepo.newSubgrouping(0, SETTINGS_REPO_SUBGROUP_1_ID, "");
        settingsRepo2.newSubgrouping(0, SETTINGS_REPO_SUBGROUP_1_ID, "");

        var settingsRepoSubgrouping =
                settingsRepo.getSubgrouping(SETTINGS_REPO_SUBGROUP_1_ID);
        var settingsRepo2Subgrouping =
                settingsRepo2.getSubgrouping(SETTINGS_REPO_SUBGROUP_1_ID);

        assertEquals(settingsRepoSubgrouping, settingsRepo2Subgrouping);
    }

    @Test
    public void testToString() {
        assertThrows(UnsupportedOperationException.class, settingsRepo::toString);
    }

    private <T> Setting<T> generateMockSetting(String id, String name, T value) {
        //noinspection unchecked
        var mockSetting = (Setting<T>) mock(Setting.class);

        when(mockSetting.id()).thenReturn(id);
        when(mockSetting.getName()).thenReturn(name);
        when(mockSetting.getValue()).thenReturn(value);

        return mockSetting;
    }
}
