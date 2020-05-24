package co.com.hometechclaim.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Intereses.
 */
@Entity
@Table(name = "intereses")
public class Intereses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 45)
    @Column(name = "nombre", length = 45, nullable = false)
    private String nombre;

    @NotNull
    @Size(min = 5, max = 45)
    @Column(name = "descripcion", length = 45, nullable = false)
    private String descripcion;

    @NotNull
    @Size(min = 5, max = 45)
    @Column(name = "imagen", length = 45, nullable = false)
    private String imagen;

    @NotNull
    @Size(min = 2, max = 10)
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @OneToMany(mappedBy = "intereses")
    private Set<InteresesToCliente> interesesToClientes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Intereses nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Intereses descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public Intereses imagen(String imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCode() {
        return code;
    }

    public Intereses code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<InteresesToCliente> getInteresesToClientes() {
        return interesesToClientes;
    }

    public Intereses interesesToClientes(Set<InteresesToCliente> interesesToClientes) {
        this.interesesToClientes = interesesToClientes;
        return this;
    }

    public Intereses addInteresesToCliente(InteresesToCliente interesesToCliente) {
        this.interesesToClientes.add(interesesToCliente);
        interesesToCliente.setIntereses(this);
        return this;
    }

    public Intereses removeInteresesToCliente(InteresesToCliente interesesToCliente) {
        this.interesesToClientes.remove(interesesToCliente);
        interesesToCliente.setIntereses(null);
        return this;
    }

    public void setInteresesToClientes(Set<InteresesToCliente> interesesToClientes) {
        this.interesesToClientes = interesesToClientes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Intereses)) {
            return false;
        }
        return id != null && id.equals(((Intereses) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Intereses{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
