package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.persistence.PersistenceHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Pair;

import static soliloquy.specs.common.valueobjects.Pair.pairOf;

@SuppressWarnings("rawtypes")
public class PairHandler extends AbstractTypeHandler<Pair> implements TypeHandler<Pair> {
    private final PersistenceHandler PERSISTENCE_HANDLER;

    public PairHandler(PersistenceHandler persistenceHandler) {
        PERSISTENCE_HANDLER = Check.ifNull(persistenceHandler, "persistenceHandler");
    }

    @Override
    public String typeHandled() {
        return Pair.class.getCanonicalName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Pair read(String valuesString) throws IllegalArgumentException {
        Check.ifNullOrEmpty(valuesString, "valuesString");
        var dto = JSON.fromJson(valuesString, DTO.class);
        Object first = null;
        if (dto.type1 != null) {
            var handler1 = PERSISTENCE_HANDLER.getTypeHandler(dto.type1);
            first = handler1.read(dto.value1);
        }
        Object second = null;
        if (dto.type2 != null) {
            var handler2 = PERSISTENCE_HANDLER.getTypeHandler(dto.type2);
            second = handler2.read(dto.value2);
        }
        return pairOf(first, second);
    }

    @Override
    public String write(Pair pair) {
        Check.ifNull(pair, "pair");
        var dto = new DTO();
        if (pair.FIRST != null) {
            dto.type1 = pair.FIRST.getClass().getCanonicalName();
            var handler1 = PERSISTENCE_HANDLER.getTypeHandler(dto.type1);
            dto.value1 = handler1.write(pair.FIRST);
        }
        if (pair.SECOND != null) {
            dto.type2 = pair.SECOND.getClass().getCanonicalName();
            var handler2 = PERSISTENCE_HANDLER.getTypeHandler(dto.type2);
            dto.value2 = handler2.write(pair.SECOND);
        }
        return JSON.toJson(dto);
    }

    private static class DTO {
        String type1;
        String value1;
        String type2;
        String value2;
    }
}
