package orb.client;

public class ClienteAgendas {
    public static void main(String[] args) {
        Agenda agendaTelefonica = new Agenda();
        Agenda guiaClaves = new Agenda();

        agendaTelefonica.asociar("Juan", 66756677);
        guiaClaves.asociar("Moodle", 23323);
        agendaTelefonica.asociar("Pepe", 644454456);

//        System.out.println("Telefono Juan = " + agendaTelefonica.obtener("Juan"));
//        System.out.println("Clave Moodle = " + guiaClaves.obtener("Moodle"));
    }
}
