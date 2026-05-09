package co.edu.uco.ucoparking.negocio.dominio;

import java.util.UUID;

import co.edu.uco.ucoparking.transversal.utilitario.UtilTexto;

public class MunicipioDominio {
	
	private UUID id;
	private String nombre;
	private UUID departamentoId;
	
	private MunicipioDominio(final Builder builder) {
		setId(builder.id);
		setNombre(builder.nombre);
		setDepartamentoId(builder.departamentoId);
	}
	
	public UUID getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public UUID getDepartamentoId() {
		return departamentoId;
	}
	
	private void setId(final UUID id) {
		this.id = id;
	}
	
	private void setNombre(final String nombre) {
		this.nombre = UtilTexto.aplicarTrim(nombre);
	}
	
	private void setDepartamentoId(final UUID departamentoId) {
		this.departamentoId = departamentoId;
	}
	
	public static class Builder {
		private UUID id;
		private String nombre;
		private UUID departamentoId;
		
		public Builder id(final UUID id) {
			this.id = id;
			return this;
		}
		
		public Builder nombre(final String nombre) {
			this.nombre = UtilTexto.aplicarTrim(nombre);
			return this;
		}
		
		public Builder departamentoId(final UUID departamentoId) {
			this.departamentoId = departamentoId;
			return this;
		}
		
		public MunicipioDominio build() {
			return new MunicipioDominio(this);
		}
	}
	
}