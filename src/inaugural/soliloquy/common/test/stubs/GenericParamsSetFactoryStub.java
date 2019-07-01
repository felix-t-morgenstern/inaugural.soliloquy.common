package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.factories.IGenericParamsSetFactory;
import soliloquy.specs.common.infrastructure.IGenericParamsSet;

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
