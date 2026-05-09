package co.edu.uco.ucoparking.negocio.dominio;

import java.util.UUID;

import co.edu.uco.ucoparking.transversal.utilitario.UtilTexto;

public class DepartamentoDominio {
	
	private UUID id;
	private String nombre;
	private UUID paisId;
	
	private DepartamentoDominio(final Builder builder) {
		setId(builder.id);
		setNombre(builder.nombre);
		setPaisId(builder.paisId);
	}
	
	public UUID getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public UUID getPaisId() {
		return paisId;
	}
	
	private void setId(final UUID id) {
		this.id = id;
	}
	
	private void setNombre(final String nombre) {
		this.nombre = UtilTexto.aplicarTrim(nombre);
	}
	
	private void setPaisId(final UUID paisId) {
		this.paisId = paisId;
	}
	
	public static class Builder {
		private UUID id;
		private String nombre;
		private UUID paisId;
		
		public Builder id(final UUID id) {
			this.id = id;
			return this;
		}
		
		public Builder nombre(final String nombre) {
			this.nombre = UtilTexto.aplicarTrim(nombre);
			return this;
		}
		
		public Builder paisId(final UUID paisId) {
			this.paisId = paisId;
			return this;
		}
		
		public DepartamentoDominio build() {
			return new DepartamentoDominio(this);
		}
	}
	
}