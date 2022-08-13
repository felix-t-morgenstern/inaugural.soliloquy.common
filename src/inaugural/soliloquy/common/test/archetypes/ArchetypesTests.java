package inaugural.soliloquy.common.test.archetypes;

import inaugural.soliloquy.common.archetypes.SettingArchetype;
import inaugural.soliloquy.common.archetypes.SettingsRepoArchetype;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.SettingsRepo;

import static org.junit.jupiter.api.Assertions.assertEquals;

// TODO: Get rid of these and make them anonymous within implementations
class ArchetypesTests {
    @Test
    void testSettingArchetype() {
        assertEquals(Setting.class.getCanonicalName(), new SettingArchetype().getInterfaceName());
    }

    @Test
    void testSettingsRepoArchetype() {
        assertEquals(SettingsRepo.class.getCanonicalName(),
                new SettingsRepoArchetype().getInterfaceName());
    }
}
