package inaugural.soliloquy.common.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.valueobjects.Vertex;

import static soliloquy.specs.common.valueobjects.Vertex.vertexOf;

public class VertexHandler extends AbstractTypeHandler<Vertex> {
    @SuppressWarnings("unchecked")
    @Override
    public Vertex read(String writtenVal) throws IllegalArgumentException {
        Check.ifNullOrEmpty(writtenVal, "writtenVal");

        var dto = JSON.fromJson(writtenVal, DTO.class);

        return vertexOf(dto.x, dto.y);
    }

    @Override
    public String write(Vertex vertex) {
        Check.ifNull(vertex, "vertex");
        var dto = new DTO();
        dto.x = vertex.X;
        dto.y = vertex.Y;
        return JSON.toJson(dto);
    }

    static class DTO {
        float x;
        float y;
    }
}
