package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.persistence.AbstractTypeWithTwoGenericParamsHandler;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;

@SuppressWarnings("rawtypes")
public class PairHandler
        extends AbstractTypeWithTwoGenericParamsHandler<Pair>
        implements TypeHandler<Pair> {
    private static final Pair<Object, Object> ARCHETYPE = new Pair<>(0, 0);

    // TODO: Implement null checks here
    public PairHandler(PersistentValuesHandler persistentValuesHandler) {
        super(
                ARCHETYPE,
                persistentValuesHandler,
                archetype1 -> archetype2 -> new Pair<>(archetype1, archetype2)
        );
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public Pair read(String valuesString) throws IllegalArgumentException {
        if (valuesString == null) {
            throw new IllegalArgumentException(
                    "PairHandler.read: valuesString cannot be null");
        }
        if (valuesString.equals("")) {
            throw new IllegalArgumentException(
                    "PairHandler.read: valuesString cannot be empty");
        }
        PairDTO dto = JSON.fromJson(valuesString, PairDTO.class);
        TypeHandler handler1 = PERSISTENT_VALUES_HANDLER.getTypeHandler(dto.valueType1);
        TypeHandler handler2 = PERSISTENT_VALUES_HANDLER.getTypeHandler(dto.valueType2);
        return new Pair<>(
                handler1.read(dto.serializedValue1),
                handler2.read(dto.serializedValue2),
                PERSISTENT_VALUES_HANDLER.generateArchetype(dto.valueType1),
                PERSISTENT_VALUES_HANDLER.generateArchetype(dto.valueType2)
        );
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @Override
    public String write(Pair pair) {
        if (pair == null) {
            throw new IllegalArgumentException("PairHandler.write: pair cannot be null");
        }
        PairDTO dto = new PairDTO();
        dto.valueType1 = getProperTypeName(pair.getFirstArchetype());
        dto.valueType2 = getProperTypeName(pair.getSecondArchetype());
        TypeHandler handler1 = PERSISTENT_VALUES_HANDLER.getTypeHandler(dto.valueType1);
        TypeHandler handler2 = PERSISTENT_VALUES_HANDLER.getTypeHandler(dto.valueType2);
        dto.serializedValue1 = handler1.write(pair.getItem1());
        dto.serializedValue2 = handler2.write(pair.getItem2());
        return JSON.toJson(dto);
    }

    private static class PairDTO {
        String valueType1;
        String serializedValue1;
        String valueType2;
        String serializedValue2;
    }
}
