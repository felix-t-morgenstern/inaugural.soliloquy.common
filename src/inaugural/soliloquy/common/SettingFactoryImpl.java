package inaugural.soliloquy.common;

import soliloquy.specs.common.factories.SettingFactory;
import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.VariableCache;

public class SettingFactoryImpl extends CanCheckArchetypeAndArchetypesOfArchetype
        implements SettingFactory {
    @Override
    public <T> Setting<T> make(String id, String name, T defaultValue,
                               VariableCache controlParams) {
        checkArchetypeAndArchetypesOfArchetype("make", defaultValue);
        return new SettingImpl<>(id, name, defaultValue, defaultValue, controlParams);
    }

    @Override
    public String getInterfaceName() {
        return SettingFactory.class.getCanonicalName();
    }

    @Override
    protected String className() {
        return "SettingFactory";
    }

    @Override
    public int hashCode() {
        return SettingFactoryImpl.class.getCanonicalName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SettingFactoryImpl && obj.hashCode() == hashCode();
    }

    @Override
    public String toString() {
        return SettingFactoryImpl.class.getCanonicalName();
    }
}
