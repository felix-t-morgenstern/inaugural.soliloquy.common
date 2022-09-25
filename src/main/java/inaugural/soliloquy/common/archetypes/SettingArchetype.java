package inaugural.soliloquy.common.archetypes;

import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.VariableCache;

public class SettingArchetype implements Setting {
    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void setValue(Object o) throws IllegalArgumentException {

    }

    @Override
    public VariableCache controlParams() {
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

    }

    @Override
    public Object getArchetype() {
        return false;
    }

    @Override
    public String getInterfaceName() {
        return Setting.class.getCanonicalName();
    }
}
