package mx.primis.auth_service.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "permissions")
public class PermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id", columnDefinition = "INT")
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    public PermissionEntity() {}

    public PermissionEntity(String name) {
        this.name = name;
    }

    public PermissionEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PermissionEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
