package inaugural.soliloquy.common.archetypes;

import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Setting;

public class SettingArchetype implements Setting {
    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void setValue(Object o) throws IllegalArgumentException {

    }

    @Override
    public GenericParamsSet controlParams() {
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
