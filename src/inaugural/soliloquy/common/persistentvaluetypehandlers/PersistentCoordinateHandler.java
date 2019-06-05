package inaugural.soliloquy.common.persistentvaluetypehandlers;

import com.google.gson.Gson;
import soliloquy.common.specs.ICoordinate;
import soliloquy.common.specs.ICoordinateFactory;
import soliloquy.common.specs.IPersistentValueTypeHandler;

public class PersistentCoordinateHandler extends PersistentTypeHandler<ICoordinate>
        implements IPersistentValueTypeHandler<ICoordinate> {
    private final ICoordinate ARCHETYPE;
    private final ICoordinateFactory COORDINATE_FACTORY;

    private static final String NULL = "NULL";
    private static final String DELIMITER = "\u0080";

    public PersistentCoordinateHandler(ICoordinateFactory coordinateFactory) {
        ARCHETYPE = coordinateFactory.make(0,0);
        COORDINATE_FACTORY = coordinateFactory;
    }

    @Override
    public ICoordinate getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public ICoordinate read(String valueString) throws IllegalArgumentException {
        if (valueString == null) {
            throw new IllegalArgumentException(
                    "PersistentCoordinateHandler.read: valueString must be non-null");
        }
        if (valueString.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentCoordinateHandler.read: valueString must be non-empty");
        }
        if (valueString.equals(NULL)){
            return null;
        } else {
            CoordinateDTO dto = new Gson().fromJson(valueString, CoordinateDTO.class);
            return COORDINATE_FACTORY.make(dto.x, dto.y);
        }
    }

    @Override
    public String write(ICoordinate coordinate) {
        if (coordinate == null) {
            return NULL;
        } else {
            CoordinateDTO dto = new CoordinateDTO();
            dto.x = coordinate.getX();
            dto.y = coordinate.getY();
            return new Gson().toJson(dto);
        }
    }

    private class CoordinateDTO {
        int x;
        int y;
    }
}
