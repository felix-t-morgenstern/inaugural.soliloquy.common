package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.persistence.TypeWithOneGenericParamHandler;

@SuppressWarnings("rawtypes")
public class FakeRegistryHandler implements TypeWithOneGenericParamHandler<Registry> {
    @Override
    public Registry generateArchetype(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Registry read(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String write(Registry registry) {
        return null;
    }

    @Override
    public Registry getArchetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
