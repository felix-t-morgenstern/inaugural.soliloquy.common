package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.persistence.TypeWithTwoGenericParamsHandler;

@SuppressWarnings("rawtypes")
public class FakeMapHandler implements TypeWithTwoGenericParamsHandler<Map> {
    public String GenerateArchetypeInput1;
    public String GenerateArchetypeInput2;
    public Map GenerateArchetypeOutput;

    @Override
    public Map read(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String write(Map Map) {
        return null;
    }

    @Override
    public Map getArchetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return TypeHandler.class.getCanonicalName() + "<" + Map.class.getCanonicalName() + ">";
    }

    @Override
    public Map generateArchetype(String s1, String s2) throws IllegalArgumentException {
        GenerateArchetypeInput1 = s1;
        GenerateArchetypeInput2 = s2;
        return GenerateArchetypeOutput;
    }
}
