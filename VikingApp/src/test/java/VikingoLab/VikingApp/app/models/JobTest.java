package VikingoLab.VikingApp.app.models;

import org.junit.jupiter.api.Test;
import VikingoLab.VikingApp.app.models.Job.JobBuilder;
import VikingoLab.VikingApp.app.models.Job.JobModel;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class JobTest {

    @Test
    public void testJobBuilder() {
        JobBuilder jobBuilder = new JobBuilder();
        jobBuilder.setId(1L)
                .setCustomerId(2L)
                .setDeviceType("Laptop")
                .setDeviceBrand("Dell")
                .setDeviceModel("XPS 13")
                .setSerialNumber("1234567890")
                .setIssueDescription("La pantalla no enciende")
                .setRepairStatus("En espera");

        JobModel job = jobBuilder.build();

        assertEquals(1L, job.getId());
        assertEquals(2L, job.getCustomerId());
        assertEquals("Laptop", job.getDeviceType()); // Corregido para obtener el tipo de dispositivo
        assertEquals("Dell", job.getDeviceBrand()); // Corregido para obtener la marca del dispositivo
        assertEquals("XPS 13", job.getDeviceModel());
        assertEquals("1234567890", job.getSerialNumber());
        assertEquals("La pantalla no enciende", job.getIssueDescription());
        assertEquals("En espera", job.getRepairStatus());
    }


    @Test
    public void testJobModel() {
        JobModel job = new JobModel();
        job.setId(1L);
        job.setCustomerId(2L);
        job.setDeviceType("Laptop");
        job.setDeviceBrand("Dell");
        job.setDeviceModel("XPS 13");
        job.setSerialNumber("1234567890");
        job.setIssueDescription("La pantalla no enciende");
        job.setRepairStatus("En espera");

        assertEquals(1L, job.getId());
        assertEquals(2L, job.getCustomerId());
        assertEquals("Laptop", job.getDeviceType());
        assertEquals("Dell", job.getDeviceBrand());
        assertEquals("XPS 13", job.getDeviceModel());
        assertEquals("1234567890", job.getSerialNumber());
        assertEquals("La pantalla no enciende", job.getIssueDescription());
        assertEquals("En espera", job.getRepairStatus());
    }
}
