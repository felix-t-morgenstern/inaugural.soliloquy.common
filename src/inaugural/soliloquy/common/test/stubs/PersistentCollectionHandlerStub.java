package inaugural.soliloquy.common.test.stubs;

import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.IPersistentValueTypeHandler;

public class PersistentCollectionHandlerStub implements IPersistentValueTypeHandler<ICollection> {
    @Override
    public ICollection read(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String write(ICollection iCollection) {
        return null;
    }

    @Override
    public ICollection getArchetype() {
        return null;
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
