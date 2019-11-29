package inaugural.soliloquy.common.test.stubs;
import soliloquy.specs.common.entities.Function;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.game.Game;
import soliloquy.specs.logger.Logger;

public class MapValidatorStub implements Function<Pair<String,String>,String> {
    @Override
    public String id() {
        // Ignore; stub class
        return null;
    }

    @Override
    public String run(Pair<String, String> input) throws IllegalArgumentException {
        if (input.getItem1().equals("Key1")) return null;
        return "Input key (" + input.getItem1() + ") not equal to Key1";
    }

    @Override
    public String getInterfaceName() {
        // Ignore; stub class
        return null;
    }

    @Override
    public Pair<String, String> getFirstArchetype() {
        // Ignore; stub class
        return null;
    }

    @Override
    public String getSecondArchetype() {
        // Ignore; stub class
        return null;
    }

    @Override
    public Game game() {
        // Ignore; stub class
        return null;
    }

    @Override
    public Logger logger() {
        // Ignore; stub class
        return null;
    }
}
