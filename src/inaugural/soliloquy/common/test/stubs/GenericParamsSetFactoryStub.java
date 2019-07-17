package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.common.infrastructure.GenericParamsSet;

public class GenericParamsSetFactoryStub implements GenericParamsSetFactory {
    @Override
    public GenericParamsSet make() {
        return new GenericParamsSetStub();
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
