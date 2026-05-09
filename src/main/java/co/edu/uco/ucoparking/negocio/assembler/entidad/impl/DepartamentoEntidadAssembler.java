package co.edu.uco.ucoparking.negocio.assembler.entidad.impl;

import co.edu.uco.ucoparking.entidad.DepartamentoEntidad;
import co.edu.uco.ucoparking.negocio.assembler.entidad.EntidadAssembler;
import co.edu.uco.ucoparking.negocio.dominio.DepartamentoDominio;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;

public final class DepartamentoEntidadAssembler implements EntidadAssembler<DepartamentoEntidad, DepartamentoDominio> {

	private static EntidadAssembler<DepartamentoEntidad, DepartamentoDominio> INSTANCE;
	
	private DepartamentoEntidadAssembler() {
		super();
	}
	
	public synchronized static final EntidadAssembler<DepartamentoEntidad, DepartamentoDominio> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new DepartamentoEntidadAssembler();
		}
		
		return INSTANCE;
	}
	
	@Override
	public DepartamentoDominio ensamblarDominio(final DepartamentoEntidad entidad) {
		var entidadAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new DepartamentoEntidad.Builder().build());
		return new DepartamentoDominio.Builder()
				.id(entidadAEnsamblar.getId())
				.nombre(entidadAEnsamblar.getNombre())
				.paisId(entidadAEnsamblar.getPaisId())
				.build();
	}

	@Override
	public DepartamentoEntidad ensamblarEntidad(final DepartamentoDominio dominio) {
		var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new DepartamentoDominio.Builder().build());
		return new DepartamentoEntidad.Builder()
				.id(dominioAEnsamblar.getId())
				.nombre(dominioAEnsamblar.getNombre())
				.paisId(dominioAEnsamblar.getPaisId())
				.build();
	}
}