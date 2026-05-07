package com.grupocordillera.ms_inventory.factory;

import com.grupocordillera.ms_inventory.model.Inventory;

import java.util.HashMap;

public class InventoryFactory {

    public static Inventory crearInventario(Inventory data) {

        Inventory i = new Inventory();

        i.setCodigo(data.getCodigo());
        i.setNombre(data.getNombre());
        i.setMarca(data.getMarca());
        i.setPrecio(data.getPrecio());
        i.setCantidad(data.getCantidad());
        i.setCategoria(data.getCategoria());


        if (data.getAtributos() == null) {
            i.setAtributos(new HashMap<>());
        } else {
            i.setAtributos(new HashMap<>(data.getAtributos()));
        }

        if ("tecnologia".equalsIgnoreCase(data.getCategoria())) {
            i.getAtributos().put("garantia", "12 meses");
        }

        if (i.getCantidad() == 0) {
            i.getAtributos().put("estado", "sin stock");
        }

        return i;
    }
}