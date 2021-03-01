package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.infrastructure.SettingsRepo;
import soliloquy.specs.common.persistence.PersistentValueTypeHandler;

public class FakePersistentSettingsRepoHandler implements PersistentValueTypeHandler<SettingsRepo> {
    @Override
    public SettingsRepo read(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String write(SettingsRepo settingsRepo) {
        return null;
    }

    @Override
    public SettingsRepo getArchetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
