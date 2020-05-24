package co.com.hometechclaim.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Colaboradores.
 */
@Entity
@Table(name = "colaboradores")
public class Colaboradores implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Size(min = 5, max = 255)
    @Column(name = "correo", length = 255)
    private String correo;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "idusuario")
    private String idusuario;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("colaboradores")
    private Dealer dealer;

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

    public Colaboradores nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public Colaboradores correo(String correo) {
        this.correo = correo;
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Colaboradores activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public Colaboradores idusuario(String idusuario) {
        this.idusuario = idusuario;
        return this;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public Colaboradores dealer(Dealer dealer) {
        this.dealer = dealer;
        return this;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Colaboradores)) {
            return false;
        }
        return id != null && id.equals(((Colaboradores) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Colaboradores{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", activo='" + isActivo() + "'" +
            ", idusuario='" + getIdusuario() + "'" +
            "}";
    }
}
