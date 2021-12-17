package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.infrastructure.*;
import soliloquy.specs.common.persistence.*;

public class FakePersistentValuesHandler implements PersistentValuesHandler {

    @Override
    public void addTypeHandler(TypeHandler<?> typeHandler) throws IllegalArgumentException {

    }

    @Override
    public boolean removeTypeHandler(String s) {
        return false;
    }

    @Override
    public <T> TypeHandler<T> getTypeHandler(String type)
            throws UnsupportedOperationException {
        if (type.equals(List.class.getName())) {
            //noinspection unchecked
            return (TypeHandler<T>) new FakeListHandler();
        } else if (type.equals(Integer.class.getCanonicalName())) {
            //noinspection unchecked
            return (TypeHandler<T>) new FakeIntegerHandler();
        } else if (type.equals(String.class.getCanonicalName())) {
            //noinspection unchecked
            return (TypeHandler<T>) new FakeStringHandler();
        } else if (type.equals(FakeHasIdAndName.class.getCanonicalName())) {
            //noinspection unchecked
            return (TypeHandler<T>) new PersistentHasIdAndNameHandler();
        } else {
            return null;
        }
    }

    @Override
    public <T> T generateArchetype(String valueType) throws IllegalArgumentException {
        //noinspection unchecked
        return (T)getTypeHandler(valueType).getArchetype();
    }

    @Override
    public java.util.List<String> typesHandled() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
