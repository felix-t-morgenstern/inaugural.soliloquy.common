package inaugural.soliloquy.common;

import soliloquy.specs.common.shared.ISoliloquyClass;

public abstract class CanGetInterfaceName {
    protected String getProperTypeName(Object object) {
        return object instanceof ISoliloquyClass ?
                ((ISoliloquyClass) object).getInterfaceName() :
                object.getClass().getCanonicalName();
    }
}
