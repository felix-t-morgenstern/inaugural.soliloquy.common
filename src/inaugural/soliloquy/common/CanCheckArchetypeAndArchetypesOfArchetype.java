package inaugural.soliloquy.common;

import soliloquy.specs.common.shared.IHasOneGenericParam;
import soliloquy.specs.common.shared.IHasTwoGenericParams;

abstract class CanCheckArchetypeAndArchetypesOfArchetype {
    void checkArchetypeAndArchetypesOfArchetype(String methodName, Object archetype) {
        if (archetype == null) {
            throw new IllegalArgumentException(className() + "." + methodName +
                    ": provided archetype is null or has null archetypes");
        }
        if (archetype instanceof IHasOneGenericParam) {
            checkArchetypeAndArchetypesOfArchetype(methodName,
                    ((IHasOneGenericParam) archetype).getArchetype());
        } else if (archetype instanceof IHasTwoGenericParams) {
            checkArchetypeAndArchetypesOfArchetype(methodName,
                    ((IHasTwoGenericParams) archetype).getFirstArchetype());
            checkArchetypeAndArchetypesOfArchetype(methodName,
                    ((IHasTwoGenericParams) archetype).getSecondArchetype());
        }
    }

    protected abstract String className();
}
