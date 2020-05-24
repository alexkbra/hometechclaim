package co.com.hometechclaim.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A Dispositivo.
 */
@Entity
@Table(name = "dispositivo")
public class Dispositivo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idusuario")
    private String idusuario;

    @Column(name = "idcliente")
    private String idcliente;

    @Column(name = "iddealer")
    private String iddealer;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "dispositivo")
    private String dispositivo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public Dispositivo idusuario(String idusuario) {
        this.idusuario = idusuario;
        return this;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public String getIdcliente() {
        return idcliente;
    }

    public Dispositivo idcliente(String idcliente) {
        this.idcliente = idcliente;
        return this;
    }

    public void setIdcliente(String idcliente) {
        this.idcliente = idcliente;
    }

    public String getIddealer() {
        return iddealer;
    }

    public Dispositivo iddealer(String iddealer) {
        this.iddealer = iddealer;
        return this;
    }

    public void setIddealer(String iddealer) {
        this.iddealer = iddealer;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Dispositivo activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public Dispositivo dispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
        return this;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dispositivo)) {
            return false;
        }
        return id != null && id.equals(((Dispositivo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Dispositivo{" +
            "id=" + getId() +
            ", idusuario='" + getIdusuario() + "'" +
            ", idcliente='" + getIdcliente() + "'" +
            ", iddealer='" + getIddealer() + "'" +
            ", activo='" + isActivo() + "'" +
            ", dispositivo='" + getDispositivo() + "'" +
            "}";
    }
}
