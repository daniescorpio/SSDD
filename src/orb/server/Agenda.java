package orb.server;

import java.util.Hashtable;

public class Agenda implements IRepository {

    private Hashtable<String, Integer> ht = new Hashtable<String, Integer>();

    private int objId;

    public Agenda(int objId) {
        this.objId = objId;
    }

    //Inserta una nueva tupla en la tabla hash
    public void asociar(String s, int v) {
        ht.put(s, new Integer(v));
    }

    //Obtiene el valor asociado a la clave s
    public int obtener(String s) {
        return ((Integer) ht.get(s)).intValue();
    }

    public int getObjId() {
        return objId;
    }
}

