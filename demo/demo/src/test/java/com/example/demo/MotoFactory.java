package com.example.demo;

import com.example.demo.Domain.Category;
import com.example.demo.Domain.Moto;
import com.example.demo.Domain.Status;

public class MotoFactory {

    public static Moto createMoto() {
        Moto moto = new Moto();
        moto.setId(1L);
        moto.setName("Honda CB500X");
        moto.setStatus(Status.NOT_STARTED);
        moto.setCategory(Category.ADVENTURE);
        return moto;
    }
}
