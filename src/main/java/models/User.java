package models;

public class User {
    private int id;
    private String email;
    private String password;
    private String role;
    private Employe employe; // Association avec Employe (peut être null pour un admin)

    // Constructeur
    public User(int id, String email, String password, String role, Employe employe) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.employe = employe;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe employe) { this.employe = employe; }
}
