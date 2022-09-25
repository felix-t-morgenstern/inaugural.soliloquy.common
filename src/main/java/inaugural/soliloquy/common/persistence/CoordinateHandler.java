package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.valueobjects.Coordinate;

public class CoordinateHandler extends AbstractTypeHandler<Coordinate> {
    private final CoordinateFactory COORDINATE_FACTORY;

    private static final String NULL = "NULL";

    public CoordinateHandler(CoordinateFactory coordinateFactory) {
        super(coordinateFactory.make(0, 0));
        COORDINATE_FACTORY = coordinateFactory;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public Coordinate read(String serializedValue) throws IllegalArgumentException {
        if (serializedValue == null) {
            throw new IllegalArgumentException(
                    "CoordinateHandler.read: serializedValue must be non-null");
        }
        if (serializedValue.equals("")) {
            throw new IllegalArgumentException(
                    "CoordinateHandler.read: serializedValue must be non-empty");
        }
        if (serializedValue.equals(NULL)) {
            return null;
        }
        else {
            CoordinateDTO dto = JSON.fromJson(serializedValue, CoordinateDTO.class);
            return COORDINATE_FACTORY.make(dto.x, dto.y);
        }
    }

    @Override
    public String write(Coordinate coordinate) {
        if (coordinate == null) {
            return NULL;
        }
        else {
            CoordinateDTO dto = new CoordinateDTO();
            dto.x = coordinate.getX();
            dto.y = coordinate.getY();
            return JSON.toJson(dto);
        }
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class CoordinateDTO {
        int x;
        int y;
    }
}
