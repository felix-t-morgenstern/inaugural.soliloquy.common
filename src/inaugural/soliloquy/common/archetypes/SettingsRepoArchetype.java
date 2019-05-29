package inaugural.soliloquy.common.archetypes;

import soliloquy.common.specs.*;

public class SettingsRepoArchetype implements ISettingsRepo {
    @Override
    public <V> ISetting<V> getSetting(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public ISettingsRepo getSubgrouping(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public <V> void setSetting(String s, V v) throws IllegalArgumentException {

    }

    @Override
    public ICollection<IEntityGroupItem<ISetting>> getAllGrouped() {
        return null;
    }

    @Override
    public ICollection<ISetting> getAllUngrouped() {
        return null;
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
        return ISettingsRepo.class.getCanonicalName();
    }
}
