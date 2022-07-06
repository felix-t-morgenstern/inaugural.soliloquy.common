package inaugural.soliloquy.common.persistence;

import com.google.gson.Gson;
import inaugural.soliloquy.tools.persistence.AbstractTypeWithTwoGenericParamsHandler;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.shared.Cloneable;

@SuppressWarnings("rawtypes")
public class PairHandler
        extends AbstractTypeWithTwoGenericParamsHandler<Pair>
        implements TypeHandler<Pair> {
    private final PairFactory PAIR_FACTORY;

    private static final PairArchetype ARCHETYPE = new PairArchetype();

    // TODO: Implement null checks here
    public PairHandler(PersistentValuesHandler persistentValuesHandler,
                       PairFactory pairFactory) {
        super(
                ARCHETYPE,
                persistentValuesHandler,
                archetype1 -> archetype2 -> pairFactory.make(archetype1, archetype2)
        );
        PAIR_FACTORY = pairFactory;
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
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
        PairDTO dto = GSON.fromJson(valuesString, PairDTO.class);
        TypeHandler handler1 = PERSISTENT_VALUES_HANDLER.getTypeHandler(dto.valueType1);
        TypeHandler handler2 = PERSISTENT_VALUES_HANDLER.getTypeHandler(dto.valueType2);
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
            throw new IllegalArgumentException("PairHandler.write: pair cannot be null");
        }
        PairDTO dto = new PairDTO();
        dto.valueType1 = getProperTypeName(pair.getFirstArchetype());
        dto.valueType2 = getProperTypeName(pair.getSecondArchetype());
        TypeHandler handler1 = PERSISTENT_VALUES_HANDLER.getTypeHandler(dto.valueType1);
        TypeHandler handler2 = PERSISTENT_VALUES_HANDLER.getTypeHandler(dto.valueType2);
        dto.serializedValue1 = handler1.write(pair.getItem1());
        dto.serializedValue2 = handler2.write(pair.getItem2());
        return GSON.toJson(dto);
    }

    private static class PairDTO {
        String valueType1;
        String serializedValue1;
        String valueType2;
        String serializedValue2;
    }

    private static class PairArchetype implements Pair {

        @Override
        public Object getItem1() {
            return null;
        }

        @Override
        public Object getItem2() {
            return null;
        }

        @Override
        public void setItem1(Object o) throws IllegalArgumentException {

        }

        @Override
        public void setItem2(Object o) throws IllegalArgumentException {

        }

        @Override
        public Cloneable makeClone() {
            return null;
        }

        @Override
        public Object getFirstArchetype() throws IllegalStateException {
            return 0;
        }

        @Override
        public Object getSecondArchetype() throws IllegalStateException {
            return 0;
        }

        @Override
        public String getInterfaceName() {
            return Pair.class.getCanonicalName();
        }
    }
}
