package co.com.hometechclaim.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import co.com.hometechclaim.domain.enumeration.Areas;

/**
 * A Notificaciones.
 */
@Entity
@Table(name = "notificaciones")
public class Notificaciones implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 255)
    @Column(name = "correo", length = 255, nullable = false)
    private String correo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "area", nullable = false)
    private Areas area;

    @NotNull
    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Column(name = "idusuario")
    private String idusuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public Notificaciones correo(String correo) {
        this.correo = correo;
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Areas getArea() {
        return area;
    }

    public Notificaciones area(Areas area) {
        this.area = area;
        return this;
    }

    public void setArea(Areas area) {
        this.area = area;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Notificaciones activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public Notificaciones idusuario(String idusuario) {
        this.idusuario = idusuario;
        return this;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notificaciones)) {
            return false;
        }
        return id != null && id.equals(((Notificaciones) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Notificaciones{" +
            "id=" + getId() +
            ", correo='" + getCorreo() + "'" +
            ", area='" + getArea() + "'" +
            ", activo='" + isActivo() + "'" +
            ", idusuario='" + getIdusuario() + "'" +
            "}";
    }
}
