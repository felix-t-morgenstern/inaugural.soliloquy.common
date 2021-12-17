package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.persistence.TypeWithOneGenericParamHandler;

@SuppressWarnings("rawtypes")
public class FakeListHandler implements TypeWithOneGenericParamHandler<List> {
    public String GenerateArchetypeInput;
    public List GenerateArchetypeOutput;

    @Override
    public List read(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String write(List list) {
        return null;
    }

    @Override
    public List getArchetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return TypeHandler.class.getCanonicalName() + "<" + List.class.getCanonicalName() + ">";
    }

    @Override
    public List generateArchetype(String valueType) throws IllegalArgumentException {
        GenerateArchetypeInput = valueType;
        return GenerateArchetypeOutput;
    }
}
