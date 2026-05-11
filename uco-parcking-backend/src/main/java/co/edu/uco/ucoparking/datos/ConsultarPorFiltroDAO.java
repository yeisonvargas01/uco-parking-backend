package co.edu.uco.ucoparking.datos;

import java.util.List;

public interface ConsultarPorFiltroDAO<E> {

	List<E> consultar(E filtro);
}
