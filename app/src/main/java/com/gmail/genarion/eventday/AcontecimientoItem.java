package com.gmail.genarion.eventday;

/**
 * Created by Mario on 18/10/2016.
 */

public class AcontecimientoItem {
    private String id;
    private String nombre;

    public AcontecimientoItem(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
