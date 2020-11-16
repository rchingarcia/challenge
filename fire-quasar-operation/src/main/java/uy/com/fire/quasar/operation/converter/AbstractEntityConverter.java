package uy.com.fire.quasar.operation.converter;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;

public abstract class AbstractEntityConverter<T, D> implements IEntityConverter<T, D> {

	private ModelMapper modelMapper = new ModelMapper();;

	protected ModelMapper getModelMapper() {
		return modelMapper;
	}

	protected boolean isAmbiguousSupported() {
		return false;
	}

	@PostConstruct
	private void init() {
		modelMapper.getConfiguration().setAmbiguityIgnored(isAmbiguousSupported());
	}

	public List<D> mapListEntityToListDto(List<T> listEntidad) {
		return listEntidad.stream().map(this::mapEntityToDto).collect(Collectors.toList());
	}

	public List<T> mapListDtoToListEntity(List<D> listEntidadDto) {
		return listEntidadDto.stream().map(this::mapDtoToEntity).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	public D mapEntityToDto(T entidad) {
		modelMapper.getConfiguration().setAmbiguityIgnored(isAmbiguousSupported());
		return modelMapper.map(entidad,
				((Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]));

	}

	@SuppressWarnings("unchecked")
	public T mapDtoToEntity(D entidadDto) {
		modelMapper.getConfiguration().setAmbiguityIgnored(isAmbiguousSupported());
		return (T) modelMapper.map(entidadDto,
				((Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]));
	}

}
