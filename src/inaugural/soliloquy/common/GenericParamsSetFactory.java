package inaugural.soliloquy.common;

import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.IGenericParamsSetFactory;
import soliloquy.common.specs.IMapFactory;
import soliloquy.common.specs.IPersistentValuesHandler;

public class GenericParamsSetFactory implements IGenericParamsSetFactory {
	private IPersistentValuesHandler _persistentValuesHandler;
	private IMapFactory _mapFactory;
	
	public GenericParamsSetFactory(IPersistentValuesHandler persistentValuesHandler, IMapFactory mapFactory)
	{
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
