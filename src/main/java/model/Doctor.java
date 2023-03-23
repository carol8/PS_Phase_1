package model;

public class Doctor extends User {
    private final String nume, prenume, email, cnp;
    private final Locatie locatie;
    private final Integer id;

    public Doctor(String username, String sare, String passwordHash, Integer id, String nume, String prenume, String email, String cnp, Locatie locatie) {
        super(username, sare, passwordHash);
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.cnp = cnp;
        this.locatie = locatie;
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

    public String getEmail() {
        return email;
    }

    public String getCnp() {
        return cnp;
    }

    public Locatie getLocatie() {
        return locatie;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", email='" + email + '\'' +
                ", cnp='" + cnp + '\'' +
                ", locatie=" + locatie +
                ", username='" + username + '\'' +
                ", sare='" + sare + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                '}';
    }
}
