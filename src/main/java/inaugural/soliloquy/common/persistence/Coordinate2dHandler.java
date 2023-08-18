package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.valueobjects.Coordinate2d;

import static inaugural.soliloquy.tools.generic.Archetypes.generateSimpleArchetype;

public class Coordinate2dHandler extends AbstractTypeHandler<Coordinate2d> {
    public Coordinate2dHandler() {
        super(generateSimpleArchetype(Coordinate2d.class));
    }

    @Override
    public Coordinate2d read(String serializedValue) throws IllegalArgumentException {
        Check.ifNullOrEmpty(serializedValue, "serializedValue");
        var dto = JSON.fromJson(serializedValue, DTO.class);
        return Coordinate2d.of(dto.x, dto.y);
    }

    @Override
    public String write(Coordinate2d coordinate) {
        Check.ifNull(coordinate, "coordinate");
        var dto = new DTO();
        dto.x = coordinate.X;
        dto.y = coordinate.Y;
        return JSON.toJson(dto);
    }

    private static class DTO {
        int x;
        int y;
    }
}
