import constants.SareConstants;
import model.*;
import org.javatuples.Pair;
import persistence.AdminPersistence;
import persistence.DoctorPersistence;
import persistence.DonatorPersistence;
import persistence.LocatiePersistence;

import javax.crypto.SecretKeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountCreator {
    public static void main(String[] args) throws NoSuchAlgorithmException, SQLException {
        PasswordEncryptor passwordEncryptor = new PasswordEncryptor(
                SareConstants.SARE_LENGTH,
                SareConstants.SARE_ACCEPTED_CHARS,
                SecureRandom.getInstanceStrong(),
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        );
        boolean loop = true;
        Scanner input = new Scanner(System.in);
        String options =
                """
                        Selecteaza optiunea dorita:
                        1. Adauga admin
                        2. Afiseaza admini
                        3. Sterge admin
                        4. Adauga donator
                        5. Afiseaza donatori
                        6. Sterge donator
                        7. Adauga doctor
                        8. Afiseaza doctori
                        9. Sterge doctor
                        10. Afiseaza locatii
                        0. Iesire""";
        do {
            System.out.println(options);
            switch (input.nextInt()) {
                case 1 -> {
                    System.out.println("Username:");
                    String usernameAdmin = input.next();
                    System.out.println("Parola:");
                    String parolaAdmin = input.next();
                    Pair<String, String> saltHashAdmin = passwordEncryptor.encryptPassword(parolaAdmin);
                    AdminPersistence.insertAdmin(new Admin(
                            usernameAdmin,
                            saltHashAdmin.getValue0(),
                            saltHashAdmin.getValue1()
                    ));
                }
                case 2 -> System.out.println(AdminPersistence.readAdmins(""));
                case 3 -> {
                    System.out.println(AdminPersistence.readAdmins(""));
                    System.out.println("Username-ul adminului care va fi sters:");
                    AdminPersistence.deleteAdmin(input.next());
                }
                case 4 -> {
                    System.out.println("Username:");
                    String usernameDonator = input.next();
                    System.out.println("Parola:");
                    String parolaDonator = input.next();
                    System.out.println("Nume:");
                    String numeDonator = input.next();
                    System.out.println("Prenume:");
                    String prenumeDonator = input.next();
                    System.out.println("Grupa sanguina:");
                    GrupaSanguina grupaSanguinaDonator = GrupaSanguina.fromInteger(input.nextInt());
                    Pair<String, String> saltHashDonator = passwordEncryptor.encryptPassword(parolaDonator);
                    DonatorPersistence.insertDonator(new Donator(
                            usernameDonator,
                            saltHashDonator.getValue0(),
                            saltHashDonator.getValue1(),
                            null,
                            numeDonator,
                            prenumeDonator,
                            grupaSanguinaDonator
                    ));
                }
                case 5 -> System.out.println(DonatorPersistence.readDonatori(""));
                case 6 -> {
                    System.out.println(DonatorPersistence.readDonatori(""));
                    System.out.println("Id-ul donatorului care va fi sters:");
                    DonatorPersistence.deleteDonator(input.nextInt());
                }
                case 7 -> {
                    System.out.println("Username:");
                    String usernameDoctor = input.next();
                    System.out.println("Parola:");
                    String parolaDoctor = input.next();
                    System.out.println("Nume:");
                    String numeDoctor = input.next();
                    System.out.println("Prenume:");
                    String prenumeDoctor = input.next();
                    System.out.println("Email:");
                    String emailDoctor = input.next();
                    System.out.println("Cnp:");
                    String cnpDoctor = input.next();
                    System.out.println(LocatiePersistence.readLocatii(""));
                    System.out.println("Id locatie:");
                    int idLocatieDoctor = input.nextInt();
                    Pair<String, String> saltHashDoctor = passwordEncryptor.encryptPassword(parolaDoctor);
                    DoctorPersistence.insertDoctor(new Doctor(
                            usernameDoctor,
                            saltHashDoctor.getValue0(),
                            saltHashDoctor.getValue1(),
                            null,
                            numeDoctor,
                            prenumeDoctor,
                            emailDoctor,
                            cnpDoctor,
                            new Locatie(idLocatieDoctor,
                                    null,
                                    null,
                                    null,
                                    null,
                                    0)));
                }
                case 8 -> System.out.println(DoctorPersistence.readDoctori(""));
                case 9 -> {
                    System.out.println(DoctorPersistence.readDoctori(""));
                    System.out.println("Id-ul doctorului care va fi sters:");
                    DoctorPersistence.deleteDoctor(input.nextInt());
                }
                case 10 -> System.out.println(LocatiePersistence.readLocatii(""));
                case 0 -> loop = false;
            }
        } while (loop);
    }
}
