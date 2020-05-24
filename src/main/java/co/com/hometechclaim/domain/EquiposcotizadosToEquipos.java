package co.com.hometechclaim.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A EquiposcotizadosToEquipos.
 */
@Entity
@Table(name = "equiposcotizados_to_equipos")
public class EquiposcotizadosToEquipos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "valor_unidad", precision = 21, scale = 2, nullable = false)
    private BigDecimal valorUnidad;

    @NotNull
    @Column(name = "valor_util_unidad", precision = 21, scale = 2, nullable = false)
    private BigDecimal valorUtilUnidad;

    @NotNull
    @Column(name = "descuento_unidad", precision = 21, scale = 2, nullable = false)
    private BigDecimal descuentoUnidad;

    @NotNull
    @Column(name = "fechacotizacion", nullable = false)
    private Instant fechacotizacion;

    @NotNull
    @Column(name = "cantidad_equipos", precision = 21, scale = 2, nullable = false)
    private BigDecimal cantidadEquipos;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("equiposcotizadosToEquipos")
    private Cotizacion cotizacion;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("equiposcotizadosToEquipos")
    private Equipo equiposcotizadosToEquipos;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValorUnidad() {
        return valorUnidad;
    }

    public EquiposcotizadosToEquipos valorUnidad(BigDecimal valorUnidad) {
        this.valorUnidad = valorUnidad;
        return this;
    }

    public void setValorUnidad(BigDecimal valorUnidad) {
        this.valorUnidad = valorUnidad;
    }

    public BigDecimal getValorUtilUnidad() {
        return valorUtilUnidad;
    }

    public EquiposcotizadosToEquipos valorUtilUnidad(BigDecimal valorUtilUnidad) {
        this.valorUtilUnidad = valorUtilUnidad;
        return this;
    }

    public void setValorUtilUnidad(BigDecimal valorUtilUnidad) {
        this.valorUtilUnidad = valorUtilUnidad;
    }

    public BigDecimal getDescuentoUnidad() {
        return descuentoUnidad;
    }

    public EquiposcotizadosToEquipos descuentoUnidad(BigDecimal descuentoUnidad) {
        this.descuentoUnidad = descuentoUnidad;
        return this;
    }

    public void setDescuentoUnidad(BigDecimal descuentoUnidad) {
        this.descuentoUnidad = descuentoUnidad;
    }

    public Instant getFechacotizacion() {
        return fechacotizacion;
    }

    public EquiposcotizadosToEquipos fechacotizacion(Instant fechacotizacion) {
        this.fechacotizacion = fechacotizacion;
        return this;
    }

    public void setFechacotizacion(Instant fechacotizacion) {
        this.fechacotizacion = fechacotizacion;
    }

    public BigDecimal getCantidadEquipos() {
        return cantidadEquipos;
    }

    public EquiposcotizadosToEquipos cantidadEquipos(BigDecimal cantidadEquipos) {
        this.cantidadEquipos = cantidadEquipos;
        return this;
    }

    public void setCantidadEquipos(BigDecimal cantidadEquipos) {
        this.cantidadEquipos = cantidadEquipos;
    }

    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    public EquiposcotizadosToEquipos cotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
        return this;
    }

    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
    }

    public Equipo getEquiposcotizadosToEquipos() {
        return equiposcotizadosToEquipos;
    }

    public EquiposcotizadosToEquipos equiposcotizadosToEquipos(Equipo equipo) {
        this.equiposcotizadosToEquipos = equipo;
        return this;
    }

    public void setEquiposcotizadosToEquipos(Equipo equipo) {
        this.equiposcotizadosToEquipos = equipo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EquiposcotizadosToEquipos)) {
            return false;
        }
        return id != null && id.equals(((EquiposcotizadosToEquipos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EquiposcotizadosToEquipos{" +
            "id=" + getId() +
            ", valorUnidad=" + getValorUnidad() +
            ", valorUtilUnidad=" + getValorUtilUnidad() +
            ", descuentoUnidad=" + getDescuentoUnidad() +
            ", fechacotizacion='" + getFechacotizacion() + "'" +
            ", cantidadEquipos=" + getCantidadEquipos() +
            "}";
    }
}
