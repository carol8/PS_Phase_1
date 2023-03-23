package model;

import java.time.LocalDate;

public class Programare {
    private final Integer id;
    private final Donator donator;
    private final Locatie locatie;
    private final LocalDate dataProgramarii;
    private final boolean confirmare;

    public Programare(Integer id, Donator donator, Locatie locatie, LocalDate dataProgramarii, boolean confirmare) {
        this.id = id;
        this.donator = donator;
        this.locatie = locatie;
        this.dataProgramarii = dataProgramarii;
        this.confirmare = confirmare;
    }

    public Integer getId() {
        return id;
    }

    public Donator getDonator() {
        return donator;
    }

    public Locatie getLocatie() {
        return locatie;
    }

    public LocalDate getDataProgramarii() {
        return dataProgramarii;
    }

    public boolean isConfirmare() {
        return confirmare;
    }

    @Override
    public String toString() {
        return "Programare{" +
                "id=" + id +
                ", donator=" + donator +
                ", locatie=" + locatie +
                ", dataProgramarii=" + dataProgramarii +
                ", confirmare=" + confirmare +
                '}';
    }
}
