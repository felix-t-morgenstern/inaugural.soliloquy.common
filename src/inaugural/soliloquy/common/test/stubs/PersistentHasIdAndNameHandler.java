package inaugural.soliloquy.common.test.stubs;

import com.google.gson.Gson;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;

public class PersistentHasIdAndNameHandler implements PersistentValueTypeHandler<HasIdAndNameStub> {
    public final static String ARCHETYPE_ID = "ArchetypeId";
    public final static String ARCHETYPE_NAME = "ArchetypeName";

    @Override
    public HasIdAndNameStub read(String s) throws IllegalArgumentException {
        HasIdAndNameStubDTO dto = new Gson().fromJson(s, HasIdAndNameStubDTO.class);
        return new HasIdAndNameStub(dto.id, dto.name);
    }

    @Override
    public String write(HasIdAndNameStub hasIdAndNameStub) {
        HasIdAndNameStubDTO dto = new HasIdAndNameStubDTO();
        dto.id = hasIdAndNameStub.id();
        dto.name = hasIdAndNameStub.getName();
        return new Gson().toJson(dto);
    }

    @Override
    public HasIdAndNameStub getArchetype() {
        return new HasIdAndNameStub(ARCHETYPE_ID, ARCHETYPE_NAME);
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    private class HasIdAndNameStubDTO
    {
        String id;
        String name;
    }
}
