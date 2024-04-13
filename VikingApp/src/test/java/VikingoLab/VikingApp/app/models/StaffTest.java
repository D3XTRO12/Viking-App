package VikingoLab.VikingApp.app.models;

import org.junit.jupiter.api.Test;
import VikingoLab.VikingApp.app.models.Staff.StaffModel;
import VikingoLab.VikingApp.app.models.Job.JobModel;
import VikingoLab.VikingApp.app.models.Staff.StaffBuilder;

import static org.junit.jupiter.api.Assertions.*;

public class StaffTest {

    @Test
    public void testStaffBuilder() {
        // Crear un objeto JobModel con un id inicializado
        JobModel job = new JobModel();
        job.setId(1L); // Asegúrate de que el JobModel tenga un id inicializado

        // Usar StaffBuilder para construir un objeto StaffModel
        StaffModel staff = new StaffBuilder()
                .setId(1L)
                .setName("Juan Perez")
                .setJob(job)
                .setRole("Técnico Senior")
                .setPhoneNumber("+54 11 2222-3333")
                .setEmail("juan.perez@email.com")
                .setAddress("Calle Libertad 123")
                .build();

        // Verificar que los valores se hayan configurado correctamente
        assertEquals(1L, staff.getId());
        assertEquals("Juan Perez", staff.getName());
        assertNotNull(staff.getJob());
        assertEquals(1L, staff.getJob().getId());
        assertEquals("Técnico Senior", staff.getRole());
        assertEquals("+54 11 2222-3333", staff.getPhoneNumber());
        assertEquals("juan.perez@email.com", staff.getEmail());
        assertEquals("Calle Libertad 123", staff.getAddress());
    }
    
    @Test
    public void testStaffModel() {
        // Crear un objeto JobModel con un id inicializado
        JobModel job = new JobModel();
        job.setId(1L); // Asegúrate de que el JobModel tenga un id inicializado
    
        // Crear un objeto StaffModel
        StaffModel staff = new StaffModel(1L, "Juan Perez", job, "Técnico Senior", "+54 11 2222-3333", "juan.perez@email.com", "Calle Libertad 123");
    
        // Verificar valores de los getters
        assertEquals(1L, staff.getId());
        assertEquals("Juan Perez", staff.getName());
        assertNotNull(staff.getId());
        assertNotNull(staff.getJob()); // Check if job is not null
        if (staff.getJob() != null) {
            assertEquals(1L, staff.getJob().getId()); // Ahora debería devolver 1L
        }
        assertEquals("Técnico Senior", staff.getRole());
        assertEquals("+54 11 2222-3333", staff.getPhoneNumber());
        assertEquals("juan.perez@email.com", staff.getEmail());
        assertEquals("Calle Libertad 123", staff.getAddress());
    }
}
