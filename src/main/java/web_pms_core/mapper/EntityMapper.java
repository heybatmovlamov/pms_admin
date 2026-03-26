package web_pms_core.mapper;

import java.util.List;

public interface EntityMapper<R, V, E> {

    E toEntity(R request);

    List<E> toEntity(List<R> dtoList);

    V toResponse(E entity);

    List<V> toResponse(List<E> entityList);
}