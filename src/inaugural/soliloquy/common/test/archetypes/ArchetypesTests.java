package inaugural.soliloquy.common.test.archetypes;

import inaugural.soliloquy.common.archetypes.*;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Function;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.SettingsRepo;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArchetypesTests {
    @Test
    void testPairArchetype() {
        assertEquals(Pair.class.getCanonicalName() + "<" + String.class.getCanonicalName() + "," +
                Integer.class.getCanonicalName() + ">",
                new PairArchetype<>("",0).getInterfaceName());
    }

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
