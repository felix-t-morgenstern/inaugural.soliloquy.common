package inaugural.soliloquy.common;

import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.VariableCache;

public class SettingImpl<T> implements Setting<T> {
    private final String ID;
    private final T ARCHETYPE;
    private final VariableCache CONTROL_PARAMS;

    private String _name;
    private T _value;

    public SettingImpl(String id, String name, T defaultValue, T archetype,
                       VariableCache controlParams) {
        ID = Check.ifNullOrEmpty(id, "SettingImpl", null, "id");
        _name = Check.ifNullOrEmpty(name, "SettingImpl", null, "name");
        _value = defaultValue;
        ARCHETYPE = Check.ifNull(archetype, "SettingImpl", null, "archetype");
        CONTROL_PARAMS = Check.ifNull(controlParams, "SettingImpl", null, "controlParams");
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
        _name = Check.ifNullOrEmpty(name, "SettingImpl", "setName", "name");
    }

    @Override
    public T getArchetype() {
        return ARCHETYPE;
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
        return CONTROL_PARAMS;
    }

    @Override
    public String getInterfaceName() {
        return Setting.class.getCanonicalName();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Setting)) {
            return false;
        }
        //noinspection rawtypes
        return ((Setting) o).id().equals(ID);
    }
}
