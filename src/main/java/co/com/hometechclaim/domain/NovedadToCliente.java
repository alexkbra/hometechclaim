package co.com.hometechclaim.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A NovedadToCliente.
 */
@Entity
@Table(name = "novedad_to_cliente")
public class NovedadToCliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fechacreacion")
    private Instant fechacreacion;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("novedadToClientes")
    private Novedad novedad;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("novedadToClientes")
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechacreacion() {
        return fechacreacion;
    }

    public NovedadToCliente fechacreacion(Instant fechacreacion) {
        this.fechacreacion = fechacreacion;
        return this;
    }

    public void setFechacreacion(Instant fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public Novedad getNovedad() {
        return novedad;
    }

    public NovedadToCliente novedad(Novedad novedad) {
        this.novedad = novedad;
        return this;
    }

    public void setNovedad(Novedad novedad) {
        this.novedad = novedad;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public NovedadToCliente cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NovedadToCliente)) {
            return false;
        }
        return id != null && id.equals(((NovedadToCliente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "NovedadToCliente{" +
            "id=" + getId() +
            ", fechacreacion='" + getFechacreacion() + "'" +
            "}";
    }
}
