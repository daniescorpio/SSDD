package orb.client;

public class ClienteAgenda {

    public static void main(String[] args) {
        Agenda agendaTelefonica = new Agenda();
        agendaTelefonica.asociar("Juan", 66756677);
        System.out.println("Telefono Juan = " + agendaTelefonica.obtener("Juan"));
    }
}
