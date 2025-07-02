package daoimpltest;

import com.pdc.dao.implementacion.OrganizacionVinculadaDAOImpl;
import com.pdc.dao.interfaz.IOrganizacionVinculadaDAO;
import com.pdc.modelo.dto.OrganizacionVinculadaDTO;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

public class OrganizacionVinculadaDAOImplTest {

    private IOrganizacionVinculadaDAO dao;
    private OrganizacionVinculadaDTO organizacionTest;
    private String RFC_TEST;

    @Before
    public void setUp() throws SQLException {
        dao = new OrganizacionVinculadaDAOImpl();
        RFC_TEST = "SAM981231H64";

        organizacionTest = new OrganizacionVinculadaDTO();
        organizacionTest.setRfcMoral(RFC_TEST);
        organizacionTest.setNombreOV("Organización Test");
        organizacionTest.setDireccionOV("Calle Test 123");
        organizacionTest.setTelefonoOV("2288123456");
        organizacionTest.setEstadoOV("ACTIVO");
        organizacionTest.setEstado("Veracruz");
        organizacionTest.setCiudad("Xalapa");
        organizacionTest.setCorreo("test@organizacion.com");
        organizacionTest.setSector("Público");
    }

    @After
    public void tearDown() throws SQLException {
        try {
            dao.eliminarOrganizacionVinculada(RFC_TEST);
        } catch (SQLException e) {
        }
    }

    @Test
    public void testInsertarOrganizacionVinculada() throws SQLException {
        boolean resultado = dao.insertarOrganizacionVinculada(organizacionTest);
        assertTrue("La inserción debería ser exitosa", resultado);

        OrganizacionVinculadaDTO organizacionInsertada = dao.buscarOrganizacionVinculada(RFC_TEST);
        assertNotNull("La organización insertada debería existir", organizacionInsertada);
        assertEquals(RFC_TEST, organizacionInsertada.getRfcMoral());
        assertEquals("Organización Test", organizacionInsertada.getNombreOV());
        assertEquals("Calle Test 123", organizacionInsertada.getDireccionOV());
        assertEquals("2288123456", organizacionInsertada.getTelefonoOV());
        assertEquals("ACTIVO", organizacionInsertada.getEstadoOV());
        assertEquals("Veracruz", organizacionInsertada.getEstado());
        assertEquals("Xalapa", organizacionInsertada.getCiudad());
        assertEquals("test@organizacion.com", organizacionInsertada.getCorreo());
        assertEquals("Público", organizacionInsertada.getSector());
    }

    @Test(expected = SQLIntegrityConstraintViolationException.class)
    public void testInsertarOrganizacionVinculadaRepetida() throws SQLException {
        dao.insertarOrganizacionVinculada(organizacionTest);
        
        OrganizacionVinculadaDTO organizacionDuplicada = new OrganizacionVinculadaDTO();
        organizacionDuplicada.setRfcMoral(RFC_TEST);
        organizacionDuplicada.setNombreOV("Organización Duplicada");
        organizacionDuplicada.setDireccionOV("Otra Dirección 456");
        organizacionDuplicada.setTelefonoOV("2288987654");
        organizacionDuplicada.setEstadoOV("INACTIVO");
        organizacionDuplicada.setEstado("Jalisco");
        organizacionDuplicada.setCiudad("Guadalajara");
        organizacionDuplicada.setCorreo("duplicado@organizacion.com");
        organizacionDuplicada.setSector("Privado");

        dao.insertarOrganizacionVinculada(organizacionDuplicada);
    }

    @Test
    public void testEditarOrganizacionVinculada() throws SQLException {
        dao.insertarOrganizacionVinculada(organizacionTest);

        organizacionTest.setNombreOV("Organización Editada");
        organizacionTest.setDireccionOV("Nueva Dirección 456");
        organizacionTest.setTelefonoOV("2288987654");
        organizacionTest.setEstadoOV("INACTIVO");
        organizacionTest.setEstado("Jalisco");
        organizacionTest.setCiudad("Guadalajara");
        organizacionTest.setCorreo("editado@organizacion.com");
        organizacionTest.setSector("Privado");

        boolean resultado = dao.editarOrganizacionVinculada(organizacionTest);
        assertTrue("La edición debería ser exitosa", resultado);

        OrganizacionVinculadaDTO organizacionEditada = dao.buscarOrganizacionVinculada(RFC_TEST);
        assertNotNull("La organización editada debería existir", organizacionEditada);
        assertEquals("Organización Editada", organizacionEditada.getNombreOV());
        assertEquals("Nueva Dirección 456", organizacionEditada.getDireccionOV());
        assertEquals("2288987654", organizacionEditada.getTelefonoOV());
        assertEquals("INACTIVO", organizacionEditada.getEstadoOV());
        assertEquals("Jalisco", organizacionEditada.getEstado());
        assertEquals("Guadalajara", organizacionEditada.getCiudad());
        assertEquals("editado@organizacion.com", organizacionEditada.getCorreo());
        assertEquals("Privado", organizacionEditada.getSector());
    }

    @Test
    public void testListarOrganizacionesVinculadas() throws SQLException {
        dao.insertarOrganizacionVinculada(organizacionTest);

        List<OrganizacionVinculadaDTO> organizaciones = dao.listarOrganizacionesVinculadas();

        assertNotNull("La lista no debería ser nula", organizaciones);
        assertTrue("La lista debería contener al menos una organización", organizaciones.size() > 0);

        boolean encontrada = false;
        for (OrganizacionVinculadaDTO org : organizaciones) {
            if (RFC_TEST.equals(org.getRfcMoral())) {
                encontrada = true;
                assertEquals("Organización Test", org.getNombreOV());
                assertEquals("Veracruz", org.getEstado());
                assertEquals("Xalapa", org.getCiudad());
                break;
            }
        }
        assertTrue("La organización insertada debería estar en la lista", encontrada);
    }
}