package co.com.hometechclaim.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TipoEquipo.
 */
@Entity
@Table(name = "tipo_equipo")
public class TipoEquipo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @Lob
    @Column(name = "imagen_url")
    private byte[] imagenUrl;

    @Column(name = "imagen_url_content_type")
    private String imagenUrlContentType;

    @NotNull
    @Size(min = 2, max = 10)
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @OneToMany(mappedBy = "tipoEquipo")
    private Set<Marca> marcas = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("tipoEquipos")
    private Solucion solucion;

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

    public TipoEquipo nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TipoEquipo descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getImagenUrl() {
        return imagenUrl;
    }

    public TipoEquipo imagenUrl(byte[] imagenUrl) {
        this.imagenUrl = imagenUrl;
        return this;
    }

    public void setImagenUrl(byte[] imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getImagenUrlContentType() {
        return imagenUrlContentType;
    }

    public TipoEquipo imagenUrlContentType(String imagenUrlContentType) {
        this.imagenUrlContentType = imagenUrlContentType;
        return this;
    }

    public void setImagenUrlContentType(String imagenUrlContentType) {
        this.imagenUrlContentType = imagenUrlContentType;
    }

    public String getCode() {
        return code;
    }

    public TipoEquipo code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Marca> getMarcas() {
        return marcas;
    }

    public TipoEquipo marcas(Set<Marca> marcas) {
        this.marcas = marcas;
        return this;
    }

    public TipoEquipo addMarca(Marca marca) {
        this.marcas.add(marca);
        marca.setTipoEquipo(this);
        return this;
    }

    public TipoEquipo removeMarca(Marca marca) {
        this.marcas.remove(marca);
        marca.setTipoEquipo(null);
        return this;
    }

    public void setMarcas(Set<Marca> marcas) {
        this.marcas = marcas;
    }

    public Solucion getSolucion() {
        return solucion;
    }

    public TipoEquipo solucion(Solucion solucion) {
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
        if (!(o instanceof TipoEquipo)) {
            return false;
        }
        return id != null && id.equals(((TipoEquipo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TipoEquipo{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", imagenUrl='" + getImagenUrl() + "'" +
            ", imagenUrlContentType='" + getImagenUrlContentType() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
