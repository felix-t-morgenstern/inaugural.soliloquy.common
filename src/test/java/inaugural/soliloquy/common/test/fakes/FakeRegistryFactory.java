package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.shared.HasId;

public class FakeRegistryFactory implements RegistryFactory {
    @Override
    public <T extends HasId> Registry<T> make(T t) {
        return new FakeRegistry<>(t);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
