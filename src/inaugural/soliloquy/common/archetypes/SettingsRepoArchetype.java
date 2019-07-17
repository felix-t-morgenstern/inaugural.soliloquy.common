package inaugural.soliloquy.common.archetypes;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.SettingsRepo;
import soliloquy.specs.common.shared.EntityGroupItem;

public class SettingsRepoArchetype implements SettingsRepo {
    @Override
    public <V> Setting<V> getSetting(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public SettingsRepo getSubgrouping(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public <V> void setSetting(String s, V v) throws IllegalArgumentException {

    }

    @Override
    public Collection<EntityGroupItem<Setting>> getAllGrouped() {
        return null;
    }

    @Override
    public Collection<Setting> getAllUngrouped() {
        return null;
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
        return SettingsRepo.class.getCanonicalName();
    }
}
