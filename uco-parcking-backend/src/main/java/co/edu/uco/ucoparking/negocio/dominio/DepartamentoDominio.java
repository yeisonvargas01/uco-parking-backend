package co.edu.uco.ucoparking.negocio.dominio;

import java.util.UUID;

import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;
import co.edu.uco.ucoparking.transversal.utilitario.UtilTexto;
import co.edu.uco.ucoparking.transversal.utilitario.UtilUUID;

public class DepartamentoDominio {

	private UUID id;
	private String nombre;
	private PaisDominio pais;

	private DepartamentoDominio(final Builder builder) {
		setId(builder.id);
		setNombre(builder.nombre);
		setPais(builder.pais);
	}

	public static Builder builder() {
		return new Builder();
	}

	public UUID getId() {
		return id;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	public String getNombre() {
		return nombre;
	}

	private void setNombre(final String nombre) {
		this.nombre = UtilTexto.aplicarTrim(nombre);
	}

	public PaisDominio getPais() {
		return pais;
	}

	private void setPais(final PaisDominio pais) {
		this.pais = UtilObjeto.obtenerValorDefecto(pais, PaisDominio.builder().build());
	}

	public static class Builder {

		private UUID id;
		private String nombre;
		private PaisDominio pais;

		private Builder() {
			super();
		}

		public Builder id(final UUID id) {
			this.id = id;
			return this;
		}

		public Builder nombre(final String nombre) {
			this.nombre = UtilTexto.aplicarTrim(nombre);
			return this;
		}

		public Builder pais(final PaisDominio pais) {
			this.pais = UtilObjeto.obtenerValorDefecto(pais, PaisDominio.builder().build());
			return this;
		}

		public DepartamentoDominio build() {
			return new DepartamentoDominio(this);
		}
	}
}