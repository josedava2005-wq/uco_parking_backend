package co.edu.uco.ucoparking.negocio.assembler.entidad.impl;

import co.edu.uco.ucoparking.entidad.MunicipioEntidad;
import co.edu.uco.ucoparking.negocio.assembler.entidad.EntidadAssembler;
import co.edu.uco.ucoparking.negocio.dominio.MunicipioDominio;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;

public final class MunicipioEntidadAssembler implements EntidadAssembler<MunicipioEntidad, MunicipioDominio> {

	private static EntidadAssembler<MunicipioEntidad, MunicipioDominio> INSTANCE;
	
	private MunicipioEntidadAssembler() {
		super();
	}
	
	public synchronized static final EntidadAssembler<MunicipioEntidad, MunicipioDominio> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new MunicipioEntidadAssembler();
		}
		
		return INSTANCE;
	}
	
	@Override
	public MunicipioDominio ensamblarDominio(final MunicipioEntidad entidad) {
		var entidadAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new MunicipioEntidad.Builder().build());
		return new MunicipioDominio.Builder()
				.id(entidadAEnsamblar.getId())
				.nombre(entidadAEnsamblar.getNombre())
				.departamentoId(entidadAEnsamblar.getDepartamentoId())
				.build();
	}

	@Override
	public MunicipioEntidad ensamblarEntidad(final MunicipioDominio dominio) {
		var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new MunicipioDominio.Builder().build());
		return new MunicipioEntidad.Builder()
				.id(dominioAEnsamblar.getId())
				.nombre(dominioAEnsamblar.getNombre())
				.departamentoId(dominioAEnsamblar.getDepartamentoId())
				.build();
	}
}