package inaugural.soliloquy.common.test.stubs;

import soliloquy.common.specs.*;

import java.util.HashMap;

public class SettingsRepoStub implements ISettingsRepo {
    public static final HashMap<String,Object> SETTINGS = new HashMap<>();

    public static final String SETTING_1_NAME = "setting1Name";
    public static final String SETTING_1_VALUE = "setting1Value";

    public static final String SETTING_2_NAME = "setting2Name";
    public static final Integer SETTING_2_VALUE = 123123;

    public static final ISetting SETTING_1 = new SettingStub(SETTING_1_NAME, SETTING_1_VALUE);
    public static final ISetting SETTING_2 = new SettingStub(SETTING_2_NAME, SETTING_2_VALUE);

    @Override
    public <V> ISetting<V> getSetting(String name) throws IllegalArgumentException {
        switch (name) {
            case SETTING_1_NAME:
                return SETTING_1;
            case SETTING_2_NAME:
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
    public <V> void setSetting(String name, V value) throws IllegalArgumentException {
        SETTINGS.put(name, value);
    }

    @Override
    public ICollection<IEntityGroupItem<ISetting>> getAllGrouped() {
        return null;
    }

    @SuppressWarnings("unchecked")
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
