package co.edu.uco.ucoparking.negocio.assembler.entidad.impl;

import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.negocio.assembler.entidad.EntidadAssembler;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.transversal.UtilObjeto;

public final class PaisEntidadAssembler implements EntidadAssembler<PaisDominio, PaisEntidad> {

    private static volatile PaisEntidadAssembler INSTANCE;

    private PaisEntidadAssembler() {
        super();
    }

    public static PaisEntidadAssembler getInstance() {
        if (UtilObjeto.esNula(INSTANCE)) {
            synchronized (PaisEntidadAssembler.class) {
                if (UtilObjeto.esNula(INSTANCE)) {
                    INSTANCE = new PaisEntidadAssembler();
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public PaisDominio ensamblarDominio(final PaisEntidad entidad) {
        var paisEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new PaisEntidad.Builder().build());

        return new PaisDominio.Builder()
                .id(paisEnsamblar.getId())
                .nombre(paisEnsamblar.getNombre())
                .build();
    }

    @Override
    public PaisEntidad ensamblarEntidad(final PaisDominio dominio) {
        var paisEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new PaisDominio.Builder().build());

        return new PaisEntidad.Builder()
                .id(paisEnsamblar.getId())
                .nombre(paisEnsamblar.getNombre())
                .build();
    }
}