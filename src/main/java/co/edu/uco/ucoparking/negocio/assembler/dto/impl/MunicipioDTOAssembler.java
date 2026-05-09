package co.edu.uco.ucoparking.negocio.assembler.dto.impl;

import co.edu.uco.ucoparking.dto.MunicipioDTO;
import co.edu.uco.ucoparking.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.ucoparking.negocio.dominio.MunicipioDominio;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;

public final class MunicipioDTOAssembler implements DTOAssembler<MunicipioDominio, MunicipioDTO> {

	private static DTOAssembler<MunicipioDominio, MunicipioDTO> INSTANCE;
	
	private MunicipioDTOAssembler() {
		super();
	}
	
	public synchronized static final DTOAssembler<MunicipioDominio, MunicipioDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new MunicipioDTOAssembler();
		}
		
		return INSTANCE;
	}
	
	@Override
	public MunicipioDominio ensamblarDominio(final MunicipioDTO dto) {
		var entidadAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new MunicipioDTO.Builder().build());
		return new MunicipioDominio.Builder()
				.id(entidadAEnsamblar.getId())
				.nombre(entidadAEnsamblar.getNombre())
				.departamentoId(entidadAEnsamblar.getDepartamentoId())
				.build();
	}

	@Override
	public MunicipioDTO ensamblarDTO(final MunicipioDominio dominio) {
		var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new MunicipioDominio.Builder().build());
		return new MunicipioDTO.Builder()
				.id(dominioAEnsamblar.getId())
				.nombre(dominioAEnsamblar.getNombre())
				.departamentoId(dominioAEnsamblar.getDepartamentoId())
				.build();
	}
}