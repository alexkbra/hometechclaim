package co.com.hometechclaim.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import co.com.hometechclaim.domain.enumeration.TipoContenido;

/**
 * A Contenido.
 */
@Entity
@Table(name = "contenido")
public class Contenido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Lob
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "video_url")
    private String videoUrl;

    @Lob
    @Column(name = "imagen_u_rl")
    private byte[] imagenURl;

    @Column(name = "imagen_u_rl_content_type")
    private String imagenURlContentType;

    @Column(name = "audio")
    private String audio;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_contenido")
    private TipoContenido tipoContenido;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "fechacreacion")
    private Instant fechacreacion;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("contenidos")
    private Novedad novedad;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Contenido descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public Contenido videoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public byte[] getImagenURl() {
        return imagenURl;
    }

    public Contenido imagenURl(byte[] imagenURl) {
        this.imagenURl = imagenURl;
        return this;
    }

    public void setImagenURl(byte[] imagenURl) {
        this.imagenURl = imagenURl;
    }

    public String getImagenURlContentType() {
        return imagenURlContentType;
    }

    public Contenido imagenURlContentType(String imagenURlContentType) {
        this.imagenURlContentType = imagenURlContentType;
        return this;
    }

    public void setImagenURlContentType(String imagenURlContentType) {
        this.imagenURlContentType = imagenURlContentType;
    }

    public String getAudio() {
        return audio;
    }

    public Contenido audio(String audio) {
        this.audio = audio;
        return this;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public TipoContenido getTipoContenido() {
        return tipoContenido;
    }

    public Contenido tipoContenido(TipoContenido tipoContenido) {
        this.tipoContenido = tipoContenido;
        return this;
    }

    public void setTipoContenido(TipoContenido tipoContenido) {
        this.tipoContenido = tipoContenido;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Contenido activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Instant getFechacreacion() {
        return fechacreacion;
    }

    public Contenido fechacreacion(Instant fechacreacion) {
        this.fechacreacion = fechacreacion;
        return this;
    }

    public void setFechacreacion(Instant fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public Novedad getNovedad() {
        return novedad;
    }

    public Contenido novedad(Novedad novedad) {
        this.novedad = novedad;
        return this;
    }

    public void setNovedad(Novedad novedad) {
        this.novedad = novedad;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contenido)) {
            return false;
        }
        return id != null && id.equals(((Contenido) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Contenido{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", videoUrl='" + getVideoUrl() + "'" +
            ", imagenURl='" + getImagenURl() + "'" +
            ", imagenURlContentType='" + getImagenURlContentType() + "'" +
            ", audio='" + getAudio() + "'" +
            ", tipoContenido='" + getTipoContenido() + "'" +
            ", activo='" + isActivo() + "'" +
            ", fechacreacion='" + getFechacreacion() + "'" +
            "}";
    }
}
