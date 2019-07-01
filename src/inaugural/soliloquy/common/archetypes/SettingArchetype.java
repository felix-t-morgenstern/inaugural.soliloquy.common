package inaugural.soliloquy.common.archetypes;

import soliloquy.specs.common.infrastructure.IGenericParamsSet;
import soliloquy.specs.common.infrastructure.ISetting;

public class SettingArchetype implements ISetting {
    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void setValue(Object o) throws IllegalArgumentException {

    }

    @Override
    public IGenericParamsSet controlParams() {
        return null;
    }

    @Override
    public String getUnparameterizedInterfaceName() {
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
        return ISetting.class.getCanonicalName();
    }
}
