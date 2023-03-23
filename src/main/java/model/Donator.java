package model;

public class Donator extends User {
    private final String nume, prenume;
    private final GrupaSanguina grupaSanguina;
    private final Integer id;

    public Donator(String username, String sare, String passwordHash, Integer id, String nume, String prenume, GrupaSanguina grupaSanguina) {
        super(username, sare, passwordHash);
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.grupaSanguina = grupaSanguina;
    }

    public Integer getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public GrupaSanguina getGrupaSanguina() {
        return grupaSanguina;
    }

    @Override
    public String toString() {
        return "Donator{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", grupaSanguina=" + grupaSanguina +
                ", username='" + username + '\'' +
                ", sare='" + sare + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                '}';
    }
}
