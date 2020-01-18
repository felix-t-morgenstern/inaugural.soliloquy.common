package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.VariableCache;

public class SettingStub<T> implements Setting<T> {
    private String _id;
    private String _name;
    private T _value;

    public SettingStub(String id, String name, T value) {
        _id = id;
        _name = name;
        _value = value;
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
        return new VariableCacheStub();
    }

    @Override
    public String id() throws IllegalStateException {
        return _id;
    }

    @Override
    public String getName() {
        return _name;
    }

    @Override
    public void setName(String name) {
        _name = name;
    }

    @Override
    public T getArchetype() {
        return _value;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
