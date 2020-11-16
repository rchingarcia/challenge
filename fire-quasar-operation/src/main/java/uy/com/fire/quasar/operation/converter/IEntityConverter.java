package uy.com.fire.quasar.operation.converter;

import java.util.List;

public interface IEntityConverter<T, D> {

	public List<D> mapListEntityToListDto(List<T> listEntidad);

	public List<T> mapListDtoToListEntity(List<D> listEntidadDto);

	public D mapEntityToDto(T entidad);

	public T mapDtoToEntity(D entidadDto);
}
