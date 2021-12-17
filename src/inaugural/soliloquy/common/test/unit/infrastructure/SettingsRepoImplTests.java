package inaugural.soliloquy.common.test.unit.infrastructure;

import inaugural.soliloquy.common.infrastructure.SettingsRepoImpl;
import inaugural.soliloquy.common.test.fakes.FakeListFactory;
import inaugural.soliloquy.common.test.fakes.FakePairFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.*;
import soliloquy.specs.common.persistence.*;
import soliloquy.specs.common.shared.EntityGroupItem;

import java.util.Objects;
import java.util.TreeMap;

class SettingsRepoImplTests {
    private SettingsRepoImpl _settingsRepo;

    private final ListFactory LIST_FACTORY = new FakeListFactory();

    private final PairFactory PAIR_FACTORY = new FakePairFactory();

    private final PersistentValuesHandler SETTINGS_REPO_PERSISTENT_VALUES_HANDLER =
            new PersistentValuesHandlerStub();

    private final String SETTING_1_ID = "Setting1Id";
    private final String SETTING_2_ID = "Setting2Id";
    private final String SETTING_3_ID = "Setting3Id";

    private final String SETTING_1_NAME = "Setting1Name";
    private final String SETTING_2_NAME = "Setting2Name";
    private final String SETTING_3_NAME = "Setting3Name";

    private final String SETTINGS_REPO_SUBGROUP_1_ID = "SettingsRepoSubgroup1Id";
    private final String SETTINGS_REPO_SUBGROUP_1_1_ID = "SettingsRepoSubgroup1-1Id";

    private final Integer SETTING_1_VALUE = 123;
    private final Double SETTING_2_VALUE = 4.56;
    private final String SETTING_3_VALUE = "Setting3Value";

    private final Integer SETTING_1_VALUE_ALT = 789;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private final Setting SETTING_ARCHETYPE = new SettingStub(SETTING_1_ID, SETTING_1_NAME,
            SETTING_1_VALUE);

    @BeforeEach
    void setUp()  {
        _settingsRepo = new SettingsRepoImpl(LIST_FACTORY, PAIR_FACTORY,
                SETTINGS_REPO_PERSISTENT_VALUES_HANDLER, SETTING_ARCHETYPE);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new SettingsRepoImpl(null,
                PAIR_FACTORY, SETTINGS_REPO_PERSISTENT_VALUES_HANDLER, SETTING_ARCHETYPE));
        assertThrows(IllegalArgumentException.class, () -> new SettingsRepoImpl(LIST_FACTORY,
                null, SETTINGS_REPO_PERSISTENT_VALUES_HANDLER, SETTING_ARCHETYPE));
        assertThrows(IllegalArgumentException.class, () -> new SettingsRepoImpl(LIST_FACTORY,
                PAIR_FACTORY, null, SETTING_ARCHETYPE));
        assertThrows(IllegalArgumentException.class, () -> new SettingsRepoImpl(LIST_FACTORY,
                PAIR_FACTORY, SETTINGS_REPO_PERSISTENT_VALUES_HANDLER, null));
    }

    @Test
    void testId() {
        _settingsRepo.newSubgrouping(0, SETTINGS_REPO_SUBGROUP_1_ID, "");

        SettingsRepo settingsRepo = _settingsRepo.getSubgrouping(SETTINGS_REPO_SUBGROUP_1_ID);

        assertEquals(SETTINGS_REPO_SUBGROUP_1_ID, settingsRepo.id());
    }

    @Test
    void testNewSubgroupingWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _settingsRepo.newSubgrouping(-1, SETTINGS_REPO_SUBGROUP_1_ID, null));
        assertThrows(IllegalArgumentException.class,
                () -> _settingsRepo.newSubgrouping(0, null, null));
        assertThrows(IllegalArgumentException.class,
                () -> _settingsRepo.newSubgrouping(0, "", null));
    }

    @Test
    @SuppressWarnings("rawtypes")
    void testAddItemToTopLevel() {
        Setting setting = mock(Setting.class);
        when(setting.id()).thenReturn(SETTING_1_ID);
        when(setting.getValue()).thenReturn(SETTING_1_VALUE);

        _settingsRepo.addEntity(setting, 0, null);

        assertEquals(_settingsRepo.getSetting(SETTING_1_ID).getValue(), SETTING_1_VALUE);
    }

    @Test
    @SuppressWarnings("rawtypes")
    void testAddTwoItemsToTopLevelWithSameId() {
        Setting setting1 = mock(Setting.class);
        when(setting1.id()).thenReturn(SETTING_1_ID);
        when(setting1.getValue()).thenReturn(SETTING_1_VALUE);

        Setting setting2 = mock(Setting.class);
        when(setting2.id()).thenReturn(SETTING_1_ID);
        when(setting2.getValue()).thenReturn(SETTING_2_VALUE);

        _settingsRepo.addEntity(setting1, 0, null);

        assertThrows(IllegalArgumentException.class,
                () -> _settingsRepo.addEntity(setting2, 1, null));
    }

    @Test
    @SuppressWarnings("rawtypes")
    void testAddTwoItemsToTopLevelWithSameOrder() {
        Setting setting1 = mock(Setting.class);
        when(setting1.id()).thenReturn(SETTING_1_ID);
        when(setting1.getValue()).thenReturn(SETTING_1_VALUE);

        Setting setting2 = mock(Setting.class);
        when(setting2.id()).thenReturn(SETTING_2_ID);
        when(setting2.getValue()).thenReturn(SETTING_2_VALUE);

        _settingsRepo.addEntity(setting1, 0, null);

        assertThrows(IllegalArgumentException.class,
                () -> _settingsRepo.addEntity(setting2, 0, null));
    }

    @Test
    @SuppressWarnings("rawtypes")
    void testAddItemToNonexistentSubGrouping() {
        Setting setting1 = mock(Setting.class);
        when(setting1.id()).thenReturn(SETTING_1_ID);
        when(setting1.getValue()).thenReturn(SETTING_1_VALUE);

        assertThrows(IllegalArgumentException.class,
                () -> _settingsRepo.addEntity(setting1, 0, SETTINGS_REPO_SUBGROUP_1_ID));
    }

    @Test
    @SuppressWarnings("rawtypes")
    void testAddItemToExistentSubGrouping() {
        _settingsRepo.newSubgrouping(0, SETTINGS_REPO_SUBGROUP_1_ID, "");

        Setting setting1 = mock(Setting.class);
        when(setting1.id()).thenReturn(SETTING_1_ID);
        when(setting1.getValue()).thenReturn(SETTING_1_VALUE);

        _settingsRepo.addEntity(setting1, 0, SETTINGS_REPO_SUBGROUP_1_ID);

        Setting getResult = _settingsRepo.getSetting(SETTING_1_ID);

        assertEquals(getResult.getValue(), SETTING_1_VALUE);
    }

    @Test
    void testSetNonexistentSetting() {
        assertThrows(IllegalArgumentException.class,
                () -> _settingsRepo.setSetting(SETTING_1_ID, SETTING_1_VALUE));
    }

    @Test
    void testGetSettingWithBlankId() {
        assertThrows(IllegalArgumentException.class, () -> _settingsRepo.getSetting(""));
    }

    @Test
    void testSetSettingWithBlankId() {
        assertThrows(IllegalArgumentException.class,
                () -> _settingsRepo.setSetting("", SETTING_1_VALUE));
    }

    @Test
    void testAddSetAndGetSetting() {
        Setting<Integer> setting =
                new SettingStub<>(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE);

        _settingsRepo.addEntity(setting, 0, null);

        _settingsRepo.setSetting(SETTING_1_ID, SETTING_1_VALUE_ALT);

        assertEquals(_settingsRepo.getSetting(SETTING_1_ID).getValue(), SETTING_1_VALUE_ALT);
    }

    @Test
    void testAddSetAndGetSettingInSubgrouping() {
        Setting<Integer> setting =
                new SettingStub<>(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE);

        _settingsRepo.newSubgrouping(0, SETTINGS_REPO_SUBGROUP_1_ID, null);

        _settingsRepo.newSubgrouping(0, SETTINGS_REPO_SUBGROUP_1_1_ID, SETTINGS_REPO_SUBGROUP_1_ID);

        _settingsRepo.addEntity(setting, 0, SETTINGS_REPO_SUBGROUP_1_1_ID);

        _settingsRepo.setSetting(SETTING_1_ID, SETTING_1_VALUE_ALT);

        assertEquals(_settingsRepo.getSetting(SETTING_1_ID).getValue(), SETTING_1_VALUE_ALT);
    }

    @Test
    void testGetItemByOrder() {
        Setting<Integer> setting =
                new SettingStub<>(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE
                );
        _settingsRepo.addEntity(setting, 123, null);
        assertEquals(_settingsRepo.getItemByOrder(123).entity().getValue(), SETTING_1_VALUE);
    }

    @Test
    void testGetNonexistentItemByOrder() {
        assertThrows(IllegalArgumentException.class, () -> _settingsRepo.getItemByOrder(123));
    }

    @Test
    @SuppressWarnings("rawtypes")
    void testGetAllGroupedRepresentation() {
        Setting<Integer> setting1 =
                new SettingStub<>(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE
                );
        Setting<Double> setting2 =
                new SettingStub<>(SETTING_2_ID, SETTING_2_NAME, SETTING_2_VALUE
                );
        Setting<String> setting3 =
                new SettingStub<>(SETTING_3_ID, SETTING_3_NAME, SETTING_3_VALUE
                );

        _settingsRepo.addEntity(setting1, 0, null);

        _settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_ID, null);

        _settingsRepo.addEntity(setting2, 0, SETTINGS_REPO_SUBGROUP_1_ID);

        _settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_1_ID, SETTINGS_REPO_SUBGROUP_1_ID);

        _settingsRepo.addEntity(setting3, 0, SETTINGS_REPO_SUBGROUP_1_1_ID);

        List<EntityGroupItem<Setting>> topLevelGrouped =
                _settingsRepo.getAllGroupedRepresentation();

        assertEquals(2, topLevelGrouped.size());
        assertEquals(topLevelGrouped.get(0).entity().getValue(), SETTING_1_VALUE);

        List<EntityGroupItem<Setting>> midLevelGrouped =
                topLevelGrouped.get(1).group().getAllGroupedRepresentation();

        assertEquals(2, midLevelGrouped.size());
        assertEquals(midLevelGrouped.get(0).entity().getValue(), SETTING_2_VALUE);

        List<EntityGroupItem<Setting>> bottomLevelGrouped =
                midLevelGrouped.get(1).group().getAllGroupedRepresentation();

        assertEquals(1, bottomLevelGrouped.size());
        assertEquals(bottomLevelGrouped.get(0).entity().getValue(), SETTING_3_VALUE);
    }

    @Test
    @SuppressWarnings("rawtypes")
    void testGetAllUngroupedRepresentation() {
        Setting<Integer> setting1 =
                new SettingStub<>(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE);
        Setting<Double> setting2 =
                new SettingStub<>(SETTING_2_ID, SETTING_2_NAME, SETTING_2_VALUE);
        Setting<String> setting3 =
                new SettingStub<>(SETTING_3_ID, SETTING_3_NAME, SETTING_3_VALUE);

        _settingsRepo.addEntity(setting1, 0, null);

        _settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_ID, null);

        _settingsRepo.addEntity(setting2, 0, SETTINGS_REPO_SUBGROUP_1_ID);

        _settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_1_ID, SETTINGS_REPO_SUBGROUP_1_ID);

        _settingsRepo.addEntity(setting3, 0, SETTINGS_REPO_SUBGROUP_1_1_ID);

        List<Setting> allSettingsUngrouped = _settingsRepo.getAllUngroupedRepresentation();

        assertEquals(3, allSettingsUngrouped.size());

        boolean setting1Used = false;
        boolean setting2Used = false;
        boolean setting3Used = false;
        for (Setting setting : allSettingsUngrouped) {
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
    void testRemoveItem() {
        Setting<Integer> setting1 =
                new SettingStub<>(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE);
        Setting<Double> setting2 =
                new SettingStub<>(SETTING_2_ID, SETTING_2_NAME, SETTING_2_VALUE);
        Setting<String> setting3 =
                new SettingStub<>(SETTING_3_ID, SETTING_3_NAME, SETTING_3_VALUE);

        _settingsRepo.addEntity(setting1, 0, null);

        _settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_ID, null);

        _settingsRepo.addEntity(setting2, 0, SETTINGS_REPO_SUBGROUP_1_ID);

        _settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_1_ID, SETTINGS_REPO_SUBGROUP_1_ID);

        _settingsRepo.addEntity(setting3, 0, SETTINGS_REPO_SUBGROUP_1_1_ID);

        assertNotNull(_settingsRepo.getSetting(SETTING_2_ID));

        _settingsRepo.removeItem(SETTING_2_ID);

        assertNull(_settingsRepo.getSetting(SETTING_2_ID));
    }

    @Test
    void testRemoveItemWithNullOrBlankId() {
        assertThrows(IllegalArgumentException.class, () -> _settingsRepo.removeItem(null));
        assertThrows(IllegalArgumentException.class, () -> _settingsRepo.removeItem(""));
    }

    @Test
    void testGetGroupingIdAndOrder() {
        Setting<Integer> setting1 =
                new SettingStub<>(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE
                );
        Setting<Double> setting2 =
                new SettingStub<>(SETTING_2_ID, SETTING_2_NAME, SETTING_2_VALUE
                );
        Setting<String> setting3 =
                new SettingStub<>(SETTING_3_ID, SETTING_3_NAME, SETTING_3_VALUE
                );

        _settingsRepo.addEntity(setting1, 123, null);

        _settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_ID, null);

        _settingsRepo.addEntity(setting2, 456, SETTINGS_REPO_SUBGROUP_1_ID);

        _settingsRepo.newSubgrouping(1, SETTINGS_REPO_SUBGROUP_1_1_ID,
                SETTINGS_REPO_SUBGROUP_1_ID);

        _settingsRepo.addEntity(setting3, 789, SETTINGS_REPO_SUBGROUP_1_1_ID);

        Pair<String,Integer> setting1GroupingIdAndOrder =
                _settingsRepo.getGroupingIdAndOrder(SETTING_1_ID);
        assertTrue(setting1GroupingIdAndOrder.getItem1() == null ||
                setting1GroupingIdAndOrder.getItem1().equals(""));
        assertEquals(123, (int) setting1GroupingIdAndOrder.getItem2());

        Pair<String,Integer> setting2GroupingIdAndOrder =
                _settingsRepo.getGroupingIdAndOrder(SETTING_2_ID);
        assertEquals(setting2GroupingIdAndOrder.getItem1(), SETTINGS_REPO_SUBGROUP_1_ID);
        assertEquals(456, (int) setting2GroupingIdAndOrder.getItem2());

        Pair<String,Integer> setting3GroupingIdAndOrder =
                _settingsRepo.getGroupingIdAndOrder(SETTING_3_ID);
        assertEquals(setting3GroupingIdAndOrder.getItem1(), SETTINGS_REPO_SUBGROUP_1_1_ID);
        assertEquals(789, (int) setting3GroupingIdAndOrder.getItem2());

        assertThrows(IllegalArgumentException.class,
                () -> _settingsRepo.getGroupingIdAndOrder("ThisIsNotAnId"));
        assertThrows(IllegalArgumentException.class,
                () -> _settingsRepo.getGroupingIdAndOrder(""));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(SettingsRepo.class.getCanonicalName(), _settingsRepo.getInterfaceName());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    void testHashCode() {
        final int order1 = 123123;
        final int order2 = 456456;

        Setting setting1 = new SettingStub(SETTING_1_ID, SETTING_1_NAME, SETTING_1_VALUE);
        Setting setting2 = new SettingStub(SETTING_2_ID, SETTING_2_NAME, SETTING_2_VALUE);

        // NB: TreeMap is chosen to contrast against the implementation, which uses HashMap
        java.util.Map<String,Setting> comparand = new TreeMap<>();
        comparand.put(SETTING_1_ID, setting1);
        comparand.put(SETTING_2_ID, setting2);

        _settingsRepo.newSubgrouping(0, SETTINGS_REPO_SUBGROUP_1_ID, "");
        _settingsRepo.addEntity(setting1, order1, null);
        _settingsRepo.addEntity(setting2, order2, SETTINGS_REPO_SUBGROUP_1_ID);

        assertEquals(comparand.hashCode(), _settingsRepo.hashCode());
    }

    @Test
    void testEquals() {
        SettingsRepo settingsRepo2 = new SettingsRepoImpl(LIST_FACTORY, PAIR_FACTORY,
                SETTINGS_REPO_PERSISTENT_VALUES_HANDLER, SETTING_ARCHETYPE);
        _settingsRepo.newSubgrouping(0, SETTINGS_REPO_SUBGROUP_1_ID, "");
        settingsRepo2.newSubgrouping(0, SETTINGS_REPO_SUBGROUP_1_ID, "");

        SettingsRepo settingsRepoSubgrouping =
                _settingsRepo.getSubgrouping(SETTINGS_REPO_SUBGROUP_1_ID);
        SettingsRepo settingsRepo2Subgrouping =
                settingsRepo2.getSubgrouping(SETTINGS_REPO_SUBGROUP_1_ID);

        assertEquals(settingsRepoSubgrouping, settingsRepo2Subgrouping);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void testToString() {
        assertThrows(UnsupportedOperationException.class, _settingsRepo::toString);
    }
    
    private static class SettingStub<T> implements Setting<T> {
        private final String ID;

        private String _name;
        private T _value;

        SettingStub(String id, String name, T defaultValue) {
            ID = id;
            _name = name;
            _value = defaultValue;
        }

        @Override
        public String id() throws IllegalStateException {
            return ID;
        }

        @Override
        public String getName() {
            return _name;
        }

        @Override
        public void setName(String name) {
            _name = name;
        }

        @Override
        public T getArchetype() {
            return null;
        }

        @Override
        public String getInterfaceName() {
            return null;
        }

        @Override
        public T getValue() {
            return _value;
        }

        @Override
        public void setValue(T value) throws IllegalArgumentException {
            _value = value;
        }

        @Override
        public VariableCache controlParams() {
            return null;
        }

        @Override
        public int hashCode() {
            return Objects.hash(ID, _name, _value);
        }
    }
    
    private static class PersistentValuesHandlerStub implements PersistentValuesHandler {
        @Override
        public void addTypeHandler(TypeHandler<?> typeHandler) throws IllegalArgumentException {

        }

        @Override
        public boolean removeTypeHandler(String s) {
            return false;
        }

        @Override
        public <T> TypeHandler<T> getTypeHandler(String s) throws IllegalArgumentException {
            return null;
        }

        @Override
        public <T> T generateArchetype(String s) throws IllegalArgumentException {
            return null;
        }

        @Override
        public java.util.List<String> typesHandled() {
            return null;
        }

        @Override
        public String getInterfaceName() {
            return null;
        }
    }
}
