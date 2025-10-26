package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.entities.Action;

import java.util.function.Function;

@SuppressWarnings("rawtypes")
public class ActionHandler extends AbstractTypeHandler<Action> {
    private final Function<String, Action> GET_ACTION;

    public ActionHandler(Function<String, Action> getAction) {
        GET_ACTION = Check.ifNull(getAction, "getAction");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Action read(String writtenVal) throws IllegalArgumentException {
        Check.ifNullOrEmpty(writtenVal, "writtenVal");
        var dto = JSON.fromJson(writtenVal, Dto.class);
        return GET_ACTION.apply(dto.actionId);
    }

    @Override
    public String write(Action action) {
        Check.ifNull(action, "action");
        var dto = new Dto();
        dto.actionId = action.id();
        return JSON.toJson(dto);
    }

    private static class Dto {
        String actionId;
    }
}
