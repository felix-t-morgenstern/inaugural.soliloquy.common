package inaugural.soliloquy.common;

import soliloquy.specs.common.entities.IPersistentValuesHandler;
import soliloquy.specs.common.factories.IGenericParamsSetFactory;
import soliloquy.specs.common.factories.IMapFactory;
import soliloquy.specs.common.valueobjects.IGenericParamsSet;

public class GenericParamsSetFactory implements IGenericParamsSetFactory {
	private IPersistentValuesHandler _persistentValuesHandler;
	private IMapFactory _mapFactory;
	
	public GenericParamsSetFactory(IPersistentValuesHandler persistentValuesHandler, IMapFactory mapFactory) {
		_persistentValuesHandler = persistentValuesHandler;
		_mapFactory = mapFactory;
	}
	
	@Override
	public IGenericParamsSet make() {
		return new GenericParamsSet(_persistentValuesHandler, _mapFactory);
	}

	@Override
	public String getInterfaceName() {
		return IGenericParamsSetFactory.class.getCanonicalName();
	}

}
