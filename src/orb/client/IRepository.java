package orb.client;

interface IRepository {

    // Se asocia un valor entero a una cadena
    public void asociar(String key, int d);

    // Se obtiene el valor entero asociado previamente a la cadena especificada.
    public int obtener(String key);
}