package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.SettingsRepo;
import soliloquy.specs.common.shared.EntityGroupItem;

import java.util.HashMap;

public class SettingsRepoStub implements SettingsRepo {
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final HashMap<String,Object> SETTINGS = new HashMap<>();

    private static final String SETTING_1_ID = "setting1Id";
    public static final String SETTING_1_NAME = "setting1Name";
    public static final String SETTING_1_VALUE = "setting1Value";

    private static final String SETTING_2_ID = "setting2Id";
    public static final String SETTING_2_NAME = "setting2Name";
    public static final Integer SETTING_2_VALUE = 123123;

    @SuppressWarnings("unchecked")
    private static final Setting SETTING_1 = new SettingStub(SETTING_1_ID, SETTING_1_NAME,
            SETTING_1_VALUE);
    @SuppressWarnings("unchecked")
    private static final Setting SETTING_2 = new SettingStub(SETTING_2_ID, SETTING_2_NAME,
            SETTING_2_VALUE);

    @SuppressWarnings("unchecked")
    @Override
    public <V> Setting<V> getSetting(String name) throws IllegalArgumentException {
        switch (name) {
            case SETTING_1_ID:
                return SETTING_1;
            case SETTING_2_ID:
                return SETTING_2;
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

    @Override
    public Collection<EntityGroupItem<Setting>> getAllGrouped() {
        return null;
    }

    @Override
    public Collection<Setting> getAllUngrouped() {
        Collection<Setting> settings = new CollectionStub<>();
        settings.add(SETTING_1);
        settings.add(SETTING_2);
        return settings;
    }

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
