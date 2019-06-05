package inaugural.soliloquy.common.persistentvaluetypehandlers;

import com.google.gson.Gson;
import soliloquy.common.specs.*;

public class PersistentPairHandler extends PersistentHandlerWithTwoGenerics<IPair>
        implements IPersistentPairHandler {

    private final IPairFactory PAIR_FACTORY;

    public PersistentPairHandler(IPersistentValuesHandler persistentValuesHandler,
                                 IPairFactory pairFactory) {
        super(persistentValuesHandler);
        PAIR_FACTORY = pairFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public IPair read(String valuesString) throws IllegalArgumentException {
        if (valuesString == null) {
            throw new IllegalArgumentException(
                    "PersistentPairHandler.read: valuesString cannot be null");
        }
        if (valuesString.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentPairHandler.read: valuesString cannot be empty");
        }
        PairDTO dto = new Gson().fromJson(valuesString, PairDTO.class);
        IPersistentValueTypeHandler handler1 =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(dto.valueType1);
        IPersistentValueTypeHandler handler2 =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(dto.valueType2);
        IPair pair = PAIR_FACTORY.make(PERSISTENT_VALUES_HANDLER.generateArchetype(dto.valueType1),
                PERSISTENT_VALUES_HANDLER.generateArchetype(dto.valueType2));
        pair.setItem1(handler1.read(dto.valueString1));
        pair.setItem2(handler2.read(dto.valueString2));
        return pair;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String write(IPair pair) {
        if(pair == null) {
            throw new IllegalArgumentException("PersistentPairHandler.write: pair cannot be null");
        }
        PairDTO dto = new PairDTO();
        dto.valueType1 = getProperTypeName(pair.getFirstArchetype());
        dto.valueType2 = getProperTypeName(pair.getSecondArchetype());
        IPersistentValueTypeHandler handler1 =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(dto.valueType1);
        IPersistentValueTypeHandler handler2 =
                PERSISTENT_VALUES_HANDLER.getPersistentValueTypeHandler(dto.valueType2);
        dto.valueString1 = handler1.write(pair.getItem1());
        dto.valueString2 = handler2.write(pair.getItem2());
        return new Gson().toJson(dto);
    }

    @Override
    public IPair getArchetype() {
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
    public IPair generateArchetype(String valueType) throws IllegalArgumentException {
        int openingCaret = valueType.indexOf("<");
        int closingCaret = valueType.lastIndexOf(">");

        return (IPair) generateTypeFromGenericParameterNames(valueType
                .substring(openingCaret + 1, closingCaret + 1));
    }

    @Override
    protected Object generateTypeFromFactory(Object archetype1, Object archetype2) {
        return PAIR_FACTORY.make(null, null, archetype1, archetype2);
    }

    private class PairDTO {
        String valueType1;
        String valueString1;
        String valueType2;
        String valueString2;
    }
}
