package co.edu.uco.ucoparking.transversal;

import java.util.UUID;

public final class UtilUUID {

	private static final UUID UUID_DEFECTO = new UUID(0L, 0L);

	private UtilUUID() {
		super();
	}

	public static UUID obtenerValorDefecto(final UUID uuid) {
		return UtilObjeto.obtenerValorDefecto(uuid, UUID_DEFECTO);
	}
}
