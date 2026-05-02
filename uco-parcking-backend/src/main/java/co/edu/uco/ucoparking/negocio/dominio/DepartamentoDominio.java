package co.edu.uco.ucoparking.negocio.dominio;

import java.util.UUID;


import co.edu.uco.ucoparking.transversal.UtilObjeto;
import co.edu.uco.ucoparking.transversal.UtilTexto;
import co.edu.uco.ucoparking.transversal.UtilUUID;

public class DepartamentoDominio {

	private UUID id;
	private String nombre;
	private PaisDominio pais;

	private DepartamentoDominio(final Builder builder) {
		setId(builder.id);
		setNombre(builder.nombre);
		setPais(builder.pais);
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
		this.pais = UtilObjeto.obtenerValorDefecto(pais, new PaisDominio.Builder().build());
	}

	public static class Builder {

		private UUID id;
		private String nombre;
		private PaisDominio pais;

		public Builder id(final UUID id) {
			this.id = id;
			return this;
		}

		public Builder nombre(final String nombre) {
			this.nombre = UtilTexto.aplicarTrim(nombre);
			return this;
		}

		public Builder pais(final PaisDominio pais) {
			this.pais = UtilObjeto.obtenerValorDefecto(pais, new PaisDominio.Builder().build());
			return this;
		}

		public DepartamentoDominio build() {
			return new DepartamentoDominio(this);
		}
	}
}
