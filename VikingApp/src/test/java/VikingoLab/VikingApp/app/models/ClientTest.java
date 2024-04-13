package VikingoLab.VikingApp.app.models;

import VikingoLab.VikingApp.app.models.Client.ClientBuilder;
import VikingoLab.VikingApp.app.models.Client.ClientModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    @Test
    public void testClientModel() {
        ClientModel client = new ClientModel();

        client.setId(1L);
        client.setName("Juan Perez");
        client.setPhoneNumber(1234567890L);
        client.setAddress("Calle Libertad 123");
        client.setDni(12345678);

        assertEquals(1L, client.getId());
        assertEquals("Juan Perez", client.getName());
        assertEquals(1234567890L, client.getPhoneNumber());
        assertEquals("Calle Libertad 123", client.getAddress());
        assertEquals(12345678, client.getDni());
    }

    @Test
    public void testClientBuilder() {
        ClientModel client = new ClientBuilder()
                .setId(1L)
                .setName("Maria Rodriguez")
                .setPhoneNumber(9876543210L)
                .setAddress("Avenida Siempreviva 742")
                .setDni(87654321)
                .build();

        assertEquals(1L, client.getId());
        assertEquals("Maria Rodriguez", client.getName());
        assertEquals(9876543210L, client.getPhoneNumber());
        assertEquals("Avenida Siempreviva 742", client.getAddress());
        assertEquals(87654321, client.getDni());
    }
}
