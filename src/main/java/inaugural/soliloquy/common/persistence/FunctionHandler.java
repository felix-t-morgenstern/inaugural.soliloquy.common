package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.entities.Function;

@SuppressWarnings("rawtypes")
public class FunctionHandler extends AbstractTypeHandler<Function> {
    private final java.util.function.Function<String, Function> GET_FUNCTION;

    public FunctionHandler(java.util.function.Function<String, Function> getFunction) {
        GET_FUNCTION = Check.ifNull(getFunction, "getFunction");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Function read(String writtenVal) throws IllegalArgumentException {
        Check.ifNullOrEmpty(writtenVal, "writtenVal");
        var dto = JSON.fromJson(writtenVal, Dto.class);
        return GET_FUNCTION.apply(dto.functionId);
    }

    @Override
    public String write(Function function) {
        Check.ifNull(function, "function");
        var dto = new Dto();
        dto.functionId = function.id();
        return JSON.toJson(dto);
    }

    private static class Dto {
        String functionId;
    }
}
