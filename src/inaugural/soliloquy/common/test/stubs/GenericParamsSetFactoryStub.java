package inaugural.soliloquy.common.test.stubs;

import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.IGenericParamsSetFactory;

public class GenericParamsSetFactoryStub implements IGenericParamsSetFactory {
    @Override
    public IGenericParamsSet make() {
        return new GenericParamsSetStub();
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
