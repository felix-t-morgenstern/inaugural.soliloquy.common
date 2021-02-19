package inaugural.soliloquy.common.persistence;

import com.google.gson.Gson;
import inaugural.soliloquy.tools.persistence.PersistentTypeHandlerWithTwoGenerics;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.persistence.PersistentPairHandler;
import soliloquy.specs.common.persistence.PersistentValueTypeHandler;
import soliloquy.specs.common.persistence.PersistentValuesHandler;

public class PersistentPairHandlerImpl extends PersistentTypeHandlerWithTwoGenerics<Pair>
        implements PersistentPairHandler {

    private final PairFactory PAIR_FACTORY;

    public PersistentPairHandlerImpl(PersistentValuesHandler persistentValuesHandler,
                                     PairFactory pairFactory) {
        super(persistentValuesHandler);
        PAIR_FACTORY = pairFactory;
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @Override
    public Pair read(String valuesString) throws IllegalArgumentException {
        if (valuesString == null) {
            throw new IllegalArgumentException(
                    "PersistentPairHandlerImpl.read: valuesString cannot be null");
        }
        if (valuesString.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentPairHandlerImpl.read: valuesString cannot be empty");
        }
        PairDTO dto = new Gson().fromJson(valuesString, PairDTO.class);
        PersistentValueTypeHandler handler1 =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(dto.valueType1);
        PersistentValueTypeHandler handler2 =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(dto.valueType2);
        Pair pair = PAIR_FACTORY.make(PERSISTENT_VALUES_HANDLER.generateArchetype(dto.valueType1),
                PERSISTENT_VALUES_HANDLER.generateArchetype(dto.valueType2));
        pair.setItem1(handler1.read(dto.serializedValue1));
        pair.setItem2(handler2.read(dto.serializedValue2));
        return pair;
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @Override
    public String write(Pair pair) {
        if(pair == null) {
            throw new IllegalArgumentException("PersistentPairHandlerImpl.write: pair cannot be null");
        }
        PairDTO dto = new PairDTO();
        dto.valueType1 = getProperTypeName(pair.getFirstArchetype());
        dto.valueType2 = getProperTypeName(pair.getSecondArchetype());
        PersistentValueTypeHandler handler1 =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(dto.valueType1);
        PersistentValueTypeHandler handler2 =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(dto.valueType2);
        dto.serializedValue1 = handler1.write(pair.getItem1());
        dto.serializedValue2 = handler2.write(pair.getItem2());
        return new Gson().toJson(dto);
    }

    @Override
    public Pair getArchetype() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getInterfaceName() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Object generateTypeFromFactory(Object archetype1, Object archetype2) {
        return PAIR_FACTORY.make(null, null, archetype1, archetype2);
    }

    private class PairDTO {
        String valueType1;
        String serializedValue1;
        String valueType2;
        String serializedValue2;
    }
}
