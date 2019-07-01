package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.infrastructure.ICollection;
import soliloquy.specs.common.infrastructure.IPair;
import soliloquy.specs.common.infrastructure.ISetting;
import soliloquy.specs.common.infrastructure.ISettingsRepo;
import soliloquy.specs.common.shared.IEntityGroupItem;

import java.util.HashMap;

public class SettingsRepoStub implements ISettingsRepo {
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final HashMap<String,Object> SETTINGS = new HashMap<>();

    private static final String SETTING_1_ID = "setting1Id";
    public static final String SETTING_1_NAME = "setting1Name";
    public static final String SETTING_1_VALUE = "setting1Value";

    private static final String SETTING_2_ID = "setting2Id";
    public static final String SETTING_2_NAME = "setting2Name";
    public static final Integer SETTING_2_VALUE = 123123;

    @SuppressWarnings("unchecked")
    private static final ISetting SETTING_1 = new SettingStub(SETTING_1_ID, SETTING_1_NAME,
            SETTING_1_VALUE);
    @SuppressWarnings("unchecked")
    private static final ISetting SETTING_2 = new SettingStub(SETTING_2_ID, SETTING_2_NAME,
            SETTING_2_VALUE);

    @SuppressWarnings("unchecked")
    @Override
    public <V> ISetting<V> getSetting(String name) throws IllegalArgumentException {
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
    public ISettingsRepo getSubgrouping(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public <V> void setSetting(String id, V value) throws IllegalArgumentException {
        SETTINGS.put(id, value);
    }

    @Override
    public ICollection<IEntityGroupItem<ISetting>> getAllGrouped() {
        return null;
    }

    @Override
    public ICollection<ISetting> getAllUngrouped() {
        ICollection<ISetting> settings = new CollectionStub<>();
        settings.add(SETTING_1);
        settings.add(SETTING_2);
        return settings;
    }

    @Override
    public IEntityGroupItem<ISetting> getItemByOrder(int i) throws IllegalArgumentException {
        return null;
    }

    @Override
    public void addEntity(ISetting iSetting, int i, String s) throws IllegalArgumentException {

    }

    @Override
    public void newSubgrouping(int i, String s, String s1) throws IllegalArgumentException {

    }

    @Override
    public boolean removeItem(String s) throws IllegalArgumentException {
        return false;
    }

    @Override
    public IPair<String, Integer> getGroupingIdAndOrder(String s) throws IllegalArgumentException {
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
