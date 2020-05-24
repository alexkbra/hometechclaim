package co.com.hometechclaim.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A RequerimientoToSolucion.
 */
@Entity
@Table(name = "requerimiento_to_solucion")
public class RequerimientoToSolucion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fechacreacion", nullable = false)
    private Instant fechacreacion;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("requerimientoToSolucions")
    private Requerimiento requerimiento;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("requerimientoToSolucions")
    private Solucion solucion;

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

    public RequerimientoToSolucion fechacreacion(Instant fechacreacion) {
        this.fechacreacion = fechacreacion;
        return this;
    }

    public void setFechacreacion(Instant fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public Requerimiento getRequerimiento() {
        return requerimiento;
    }

    public RequerimientoToSolucion requerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
        return this;
    }

    public void setRequerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
    }

    public Solucion getSolucion() {
        return solucion;
    }

    public RequerimientoToSolucion solucion(Solucion solucion) {
        this.solucion = solucion;
        return this;
    }

    public void setSolucion(Solucion solucion) {
        this.solucion = solucion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequerimientoToSolucion)) {
            return false;
        }
        return id != null && id.equals(((RequerimientoToSolucion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RequerimientoToSolucion{" +
            "id=" + getId() +
            ", fechacreacion='" + getFechacreacion() + "'" +
            "}";
    }
}
