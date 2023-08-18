package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeWithTwoGenericParamsHandler;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Pair;

import static inaugural.soliloquy.tools.valueobjects.Pair.pairOf;

@SuppressWarnings("rawtypes")
public class PairHandler
        extends AbstractTypeWithTwoGenericParamsHandler<Pair>
        implements TypeHandler<Pair> {
    private static final Pair<Object, Object> ARCHETYPE = pairOf(0, 0);

    // TODO: Implement null checks here
    public PairHandler(PersistentValuesHandler persistentValuesHandler) {
        super(
                ARCHETYPE,
                persistentValuesHandler,
                archetype1 -> archetype2 -> pairOf(archetype1, archetype2)
        );
    }

    @Override
    public Pair read(String valuesString) throws IllegalArgumentException {
        Check.ifNullOrEmpty(valuesString, "valuesString");
        var dto = JSON.fromJson(valuesString, DTO.class);
        var handler1 = PERSISTENT_VALUES_HANDLER.getTypeHandler(dto.type1);
        var handler2 = PERSISTENT_VALUES_HANDLER.getTypeHandler(dto.type2);
        return pairOf(
                handler1.read(dto.value1),
                handler2.read(dto.value2),
                PERSISTENT_VALUES_HANDLER.generateArchetype(dto.type1),
                PERSISTENT_VALUES_HANDLER.generateArchetype(dto.type2)
        );
    }

    @Override
    public String write(Pair pair) {
        Check.ifNull(pair, "pair");
        var dto = new DTO();
        dto.type1 = getProperTypeName(pair.firstArchetype());
        dto.type2 = getProperTypeName(pair.secondArchetype());
        var handler1 = PERSISTENT_VALUES_HANDLER.getTypeHandler(dto.type1);
        var handler2 = PERSISTENT_VALUES_HANDLER.getTypeHandler(dto.type2);
        dto.value1 = handler1.write(pair.item1());
        dto.value2 = handler2.write(pair.item2());
        return JSON.toJson(dto);
    }

    private static class DTO {
        String type1;
        String value1;
        String type2;
        String value2;
    }
}
