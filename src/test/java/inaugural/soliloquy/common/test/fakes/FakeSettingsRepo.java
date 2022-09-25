package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.SettingsRepo;
import soliloquy.specs.common.shared.EntityGroupItem;
import soliloquy.specs.common.valueobjects.Pair;

import java.util.HashMap;

public class FakeSettingsRepo implements SettingsRepo {
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final HashMap<String, Object> SETTINGS = new HashMap<>();

    private static final String SETTING_1_ID = "setting1Id";
    public static final String SETTING_1_NAME = "setting1Name";
    public static final String SETTING_1_VALUE = "setting1Value";

    private static final String SETTING_2_ID = "setting2Id";
    public static final String SETTING_2_NAME = "setting2Name";
    public static final Integer SETTING_2_VALUE = 123123;

    private static final Setting<String> SETTING_1 = new FakeSetting<>(SETTING_1_ID, SETTING_1_NAME,
            SETTING_1_VALUE);
    private static final Setting<Integer> SETTING_2 =
            new FakeSetting<>(SETTING_2_ID, SETTING_2_NAME,
                    SETTING_2_VALUE);

    @SuppressWarnings("unchecked")
    @Override
    public <V> Setting<V> getSetting(String name) throws IllegalArgumentException {
        switch (name) {
            case SETTING_1_ID:
                return (Setting<V>) SETTING_1;
            case SETTING_2_ID:
                return (Setting<V>) SETTING_2;
            default:
                return null;
        }
    }

    @Override
    public SettingsRepo getSubgrouping(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public <V> void setSetting(String id, V value) throws IllegalArgumentException {
        SETTINGS.put(id, value);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<EntityGroupItem<Setting>> getAllGroupedRepresentation() {
        return null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<Setting> getAllUngroupedRepresentation() {
        List<Setting> settings = new FakeList<>();
        settings.add(SETTING_1);
        settings.add(SETTING_2);
        return settings;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public EntityGroupItem<Setting> getItemByOrder(int i) throws IllegalArgumentException {
        return null;
    }

    @Override
    public void addEntity(Setting Setting, int i, String s) throws IllegalArgumentException {

    }

    @Override
    public void newSubgrouping(int i, String s, String s1) throws IllegalArgumentException {

    }

    @Override
    public boolean removeItem(String s) throws IllegalArgumentException {
        return false;
    }

    @Override
    public Pair<String, Integer> getGroupingIdAndOrder(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
