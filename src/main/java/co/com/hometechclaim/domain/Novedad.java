package co.com.hometechclaim.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Novedad.
 */
@Entity
@Table(name = "novedad")
public class Novedad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    @NotNull
    @Size(min = 5, max = 250)
    @Column(name = "subtitulo", length = 250, nullable = false)
    private String subtitulo;

    @NotNull
    @Size(min = 5, max = 500)
    @Column(name = "descripcion", length = 500, nullable = false)
    private String descripcion;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @OneToMany(mappedBy = "novedad")
    private Set<NovedadToCliente> novedadToClientes = new HashSet<>();

    @OneToMany(mappedBy = "novedad")
    private Set<Contenido> contenidos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Novedad titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public Novedad subtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
        return this;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Novedad descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Novedad imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Novedad imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public Set<NovedadToCliente> getNovedadToClientes() {
        return novedadToClientes;
    }

    public Novedad novedadToClientes(Set<NovedadToCliente> novedadToClientes) {
        this.novedadToClientes = novedadToClientes;
        return this;
    }

    public Novedad addNovedadToCliente(NovedadToCliente novedadToCliente) {
        this.novedadToClientes.add(novedadToCliente);
        novedadToCliente.setNovedad(this);
        return this;
    }

    public Novedad removeNovedadToCliente(NovedadToCliente novedadToCliente) {
        this.novedadToClientes.remove(novedadToCliente);
        novedadToCliente.setNovedad(null);
        return this;
    }

    public void setNovedadToClientes(Set<NovedadToCliente> novedadToClientes) {
        this.novedadToClientes = novedadToClientes;
    }

    public Set<Contenido> getContenidos() {
        return contenidos;
    }

    public Novedad contenidos(Set<Contenido> contenidos) {
        this.contenidos = contenidos;
        return this;
    }

    public Novedad addContenido(Contenido contenido) {
        this.contenidos.add(contenido);
        contenido.setNovedad(this);
        return this;
    }

    public Novedad removeContenido(Contenido contenido) {
        this.contenidos.remove(contenido);
        contenido.setNovedad(null);
        return this;
    }

    public void setContenidos(Set<Contenido> contenidos) {
        this.contenidos = contenidos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Novedad)) {
            return false;
        }
        return id != null && id.equals(((Novedad) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Novedad{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", subtitulo='" + getSubtitulo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + getImagenContentType() + "'" +
            "}";
    }
}
