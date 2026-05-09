package co.edu.uco.ucoparking.negocio.assembler.dto.impl;

import co.edu.uco.ucoparking.dto.DepartamentoDTO;
import co.edu.uco.ucoparking.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.ucoparking.negocio.dominio.DepartamentoDominio;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;

public final class DepartamentoDTOAssembler implements DTOAssembler<DepartamentoDominio, DepartamentoDTO> {

	private static DTOAssembler<DepartamentoDominio, DepartamentoDTO> INSTANCE;
	
	private DepartamentoDTOAssembler() {
		super();
	}
	
	public synchronized static final DTOAssembler<DepartamentoDominio, DepartamentoDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new DepartamentoDTOAssembler();
		}
		
		return INSTANCE;
	}
	
	@Override
	public DepartamentoDominio ensamblarDominio(final DepartamentoDTO dto) {
		var entidadAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new DepartamentoDTO.Builder().build());
		return new DepartamentoDominio.Builder()
				.id(entidadAEnsamblar.getId())
				.nombre(entidadAEnsamblar.getNombre())
				.paisId(entidadAEnsamblar.getPaisId())
				.build();
	}

	@Override
	public DepartamentoDTO ensamblarDTO(final DepartamentoDominio dominio) {
		var dominioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new DepartamentoDominio.Builder().build());
		return new DepartamentoDTO.Builder()
				.id(dominioAEnsamblar.getId())
				.nombre(dominioAEnsamblar.getNombre())
				.paisId(dominioAEnsamblar.getPaisId())
				.build();
	}
}