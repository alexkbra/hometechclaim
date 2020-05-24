package co.com.hometechclaim.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import co.com.hometechclaim.domain.enumeration.EstadoRequerimiento;

import co.com.hometechclaim.domain.enumeration.TipoRequerimiento;

/**
 * A Requerimiento.
 */
@Entity
@Table(name = "requerimiento")
public class Requerimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Lob
    @Column(name = "detalleproblema", nullable = false)
    private String detalleproblema;

    @NotNull
    @Column(name = "fechacreacion", nullable = false)
    private Instant fechacreacion;

    @Column(name = "fechaactualizacion")
    private Instant fechaactualizacion;

    @Lob
    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "idusuarioatendio")
    private String idusuarioatendio;

    @Column(name = "idusuarioactualizo")
    private String idusuarioactualizo;

    @Column(name = "id_usuario")
    private String idUsuario;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_requerimiento")
    private EstadoRequerimiento estadoRequerimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_requerimiento")
    private TipoRequerimiento tipoRequerimiento;

    @OneToMany(mappedBy = "requerimiento")
    private Set<RequerimientoToSolucion> requerimientoToSolucions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetalleproblema() {
        return detalleproblema;
    }

    public Requerimiento detalleproblema(String detalleproblema) {
        this.detalleproblema = detalleproblema;
        return this;
    }

    public void setDetalleproblema(String detalleproblema) {
        this.detalleproblema = detalleproblema;
    }

    public Instant getFechacreacion() {
        return fechacreacion;
    }

    public Requerimiento fechacreacion(Instant fechacreacion) {
        this.fechacreacion = fechacreacion;
        return this;
    }

    public void setFechacreacion(Instant fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public Instant getFechaactualizacion() {
        return fechaactualizacion;
    }

    public Requerimiento fechaactualizacion(Instant fechaactualizacion) {
        this.fechaactualizacion = fechaactualizacion;
        return this;
    }

    public void setFechaactualizacion(Instant fechaactualizacion) {
        this.fechaactualizacion = fechaactualizacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public Requerimiento observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getIdusuarioatendio() {
        return idusuarioatendio;
    }

    public Requerimiento idusuarioatendio(String idusuarioatendio) {
        this.idusuarioatendio = idusuarioatendio;
        return this;
    }

    public void setIdusuarioatendio(String idusuarioatendio) {
        this.idusuarioatendio = idusuarioatendio;
    }

    public String getIdusuarioactualizo() {
        return idusuarioactualizo;
    }

    public Requerimiento idusuarioactualizo(String idusuarioactualizo) {
        this.idusuarioactualizo = idusuarioactualizo;
        return this;
    }

    public void setIdusuarioactualizo(String idusuarioactualizo) {
        this.idusuarioactualizo = idusuarioactualizo;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public Requerimiento idUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
        return this;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Requerimiento imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Requerimiento imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public EstadoRequerimiento getEstadoRequerimiento() {
        return estadoRequerimiento;
    }

    public Requerimiento estadoRequerimiento(EstadoRequerimiento estadoRequerimiento) {
        this.estadoRequerimiento = estadoRequerimiento;
        return this;
    }

    public void setEstadoRequerimiento(EstadoRequerimiento estadoRequerimiento) {
        this.estadoRequerimiento = estadoRequerimiento;
    }

    public TipoRequerimiento getTipoRequerimiento() {
        return tipoRequerimiento;
    }

    public Requerimiento tipoRequerimiento(TipoRequerimiento tipoRequerimiento) {
        this.tipoRequerimiento = tipoRequerimiento;
        return this;
    }

    public void setTipoRequerimiento(TipoRequerimiento tipoRequerimiento) {
        this.tipoRequerimiento = tipoRequerimiento;
    }

    public Set<RequerimientoToSolucion> getRequerimientoToSolucions() {
        return requerimientoToSolucions;
    }

    public Requerimiento requerimientoToSolucions(Set<RequerimientoToSolucion> requerimientoToSolucions) {
        this.requerimientoToSolucions = requerimientoToSolucions;
        return this;
    }

    public Requerimiento addRequerimientoToSolucion(RequerimientoToSolucion requerimientoToSolucion) {
        this.requerimientoToSolucions.add(requerimientoToSolucion);
        requerimientoToSolucion.setRequerimiento(this);
        return this;
    }

    public Requerimiento removeRequerimientoToSolucion(RequerimientoToSolucion requerimientoToSolucion) {
        this.requerimientoToSolucions.remove(requerimientoToSolucion);
        requerimientoToSolucion.setRequerimiento(null);
        return this;
    }

    public void setRequerimientoToSolucions(Set<RequerimientoToSolucion> requerimientoToSolucions) {
        this.requerimientoToSolucions = requerimientoToSolucions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Requerimiento)) {
            return false;
        }
        return id != null && id.equals(((Requerimiento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Requerimiento{" +
            "id=" + getId() +
            ", detalleproblema='" + getDetalleproblema() + "'" +
            ", fechacreacion='" + getFechacreacion() + "'" +
            ", fechaactualizacion='" + getFechaactualizacion() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", idusuarioatendio='" + getIdusuarioatendio() + "'" +
            ", idusuarioactualizo='" + getIdusuarioactualizo() + "'" +
            ", idUsuario='" + getIdUsuario() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + getImagenContentType() + "'" +
            ", estadoRequerimiento='" + getEstadoRequerimiento() + "'" +
            ", tipoRequerimiento='" + getTipoRequerimiento() + "'" +
            "}";
    }
}
