package inaugural.soliloquy.common.infrastructure;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.VariableCache;

import java.util.Objects;

public class SettingImpl<T> implements Setting<T> {
    private final String ID;
    private final T ARCHETYPE;
    private final VariableCache CONTROL_PARAMS;

    private String _name;
    private T _value;

    public SettingImpl(String id, String name, T defaultValue, T archetype,
                       VariableCache controlParams) {
        ID = Check.ifNullOrEmpty(id, "id");
        _name = Check.ifNullOrEmpty(name, "name");
        _value = defaultValue;
        ARCHETYPE = Check.ifNull(archetype, "archetype");
        CONTROL_PARAMS = Check.ifNull(controlParams, "controlParams");
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
        _name = Check.ifNullOrEmpty(name, "name");
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
    public int hashCode() {
        return Objects.hash(ID, _name, _value, CONTROL_PARAMS);
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

    @Override
    public String toString() {
        throw new UnsupportedOperationException(
                "SettingImpl.toString: Operation not supported; use " +
                        "PersistentSettingsRepoHandler.write instead");
    }
}
