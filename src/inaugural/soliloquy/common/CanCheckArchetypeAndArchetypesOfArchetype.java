package inaugural.soliloquy.common;

import soliloquy.specs.common.shared.HasOneGenericParam;
import soliloquy.specs.common.shared.HasTwoGenericParams;

abstract class CanCheckArchetypeAndArchetypesOfArchetype {
    void checkArchetypeAndArchetypesOfArchetype(String methodName, Object archetype) {
        if (archetype == null) {
            throw new IllegalArgumentException(className() + "." + methodName +
                    ": provided archetype is null or has null archetypes");
        }
        if (archetype instanceof HasOneGenericParam) {
            checkArchetypeAndArchetypesOfArchetype(methodName,
                    ((HasOneGenericParam) archetype).getArchetype());
        } else if (archetype instanceof HasTwoGenericParams) {
            checkArchetypeAndArchetypesOfArchetype(methodName,
                    ((HasTwoGenericParams) archetype).getFirstArchetype());
            checkArchetypeAndArchetypesOfArchetype(methodName,
                    ((HasTwoGenericParams) archetype).getSecondArchetype());
        }
    }

    protected abstract String className();
}
