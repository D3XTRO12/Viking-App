package VikingoLab.VikingApp.app.models.Client;

public class ClientBuilder {
    private ClientModel client;

    public ClientBuilder() {
        this.client = new ClientModel();
    }

    public ClientBuilder setId(Long id) {
        client.setId(id);
        return this;
    }

    public ClientBuilder setName(String name) {
        client.setName(name);
        return this;
    }

    public ClientBuilder setPhoneNumber(Long phoneNumber) {
        client.setPhoneNumber(phoneNumber);
        return this;
    }

    public ClientBuilder setAddress(String address) {
        client.setAddress(address);
        return this;
    }

    public ClientBuilder setDni(int i) {
        client.setDni(i);
        return this;
    }

    public ClientModel build() {
        return client;
    }

    public ClientModel getClient() {
        return client;
    }

    public void setClient(ClientModel client) {
        this.client = client;
    }

    
}
