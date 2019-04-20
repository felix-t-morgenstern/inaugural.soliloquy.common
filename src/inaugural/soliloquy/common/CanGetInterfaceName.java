package inaugural.soliloquy.common;

import soliloquy.common.specs.ISoliloquyClass;

public abstract class CanGetInterfaceName {
    protected String getProperTypeName(Object object) {
        return object instanceof ISoliloquyClass ?
                ((ISoliloquyClass) object).getInterfaceName() :
                object.getClass().getCanonicalName();
    }
}
