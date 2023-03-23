package model;

import java.time.LocalTime;

public class Locatie {
    private final Integer id;
    private final String nume, adresa;
    private final LocalTime oraDeschidere, oraInchidere;
    private final int numarMaximRecoltari;

    public Locatie(Integer id, String nume, String adresa, LocalTime oraDeschidere, LocalTime oraInchidere, int numarMaximRecoltari) {
        this.id = id;
        this.nume = nume;
        this.adresa = adresa;
        this.oraDeschidere = oraDeschidere;
        this.oraInchidere = oraInchidere;
        this.numarMaximRecoltari = numarMaximRecoltari;
    }

    public Integer getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getAdresa() {
        return adresa;
    }

    public LocalTime getOraDeschidere() {
        return oraDeschidere;
    }

    public LocalTime getOraInchidere() {
        return oraInchidere;
    }

    public int getNumarMaximRecoltari() {
        return numarMaximRecoltari;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
