package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.common.shared.HasName;

public class HasIdAndNameStub implements HasId, HasName {
    private String _id;
    private String _name;

    public HasIdAndNameStub(String id, String name) {
        _id = id;
        _name = name;
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
    public String getInterfaceName() {
        return HasIdAndNameStub.class.getCanonicalName();
    }
}
