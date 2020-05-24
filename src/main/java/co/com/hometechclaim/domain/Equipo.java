package co.com.hometechclaim.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A Equipo.
 */
@Entity
@Table(name = "equipo")
public class Equipo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 250)
    @Column(name = "nombre", length = 250, nullable = false)
    private String nombre;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "version", length = 50, nullable = false)
    private String version;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "controlador", length = 100, nullable = false)
    private String controlador;

    @Size(min = 5, max = 100)
    @Column(name = "accountname", length = 100)
    private String accountname;

    @Size(min = 5, max = 100)
    @Column(name = "controllermacaddress", length = 100)
    private String controllermacaddress;

    @Lob
    @Column(name = "imagen_url")
    private byte[] imagenUrl;

    @Column(name = "imagen_url_content_type")
    private String imagenUrlContentType;

    @NotNull
    @Column(name = "valor", precision = 21, scale = 2, nullable = false)
    private BigDecimal valor;

    @OneToMany(mappedBy = "equipo")
    private Set<Equiposinstalados> equiposinstalados = new HashSet<>();

    @OneToMany(mappedBy = "equiposcotizadosToEquipos")
    private Set<EquiposcotizadosToEquipos> equiposcotizadosToEquipos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("equipos")
    private Marca marca;

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

    public Equipo nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getVersion() {
        return version;
    }

    public Equipo version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getControlador() {
        return controlador;
    }

    public Equipo controlador(String controlador) {
        this.controlador = controlador;
        return this;
    }

    public void setControlador(String controlador) {
        this.controlador = controlador;
    }

    public String getAccountname() {
        return accountname;
    }

    public Equipo accountname(String accountname) {
        this.accountname = accountname;
        return this;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getControllermacaddress() {
        return controllermacaddress;
    }

    public Equipo controllermacaddress(String controllermacaddress) {
        this.controllermacaddress = controllermacaddress;
        return this;
    }

    public void setControllermacaddress(String controllermacaddress) {
        this.controllermacaddress = controllermacaddress;
    }

    public byte[] getImagenUrl() {
        return imagenUrl;
    }

    public Equipo imagenUrl(byte[] imagenUrl) {
        this.imagenUrl = imagenUrl;
        return this;
    }

    public void setImagenUrl(byte[] imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getImagenUrlContentType() {
        return imagenUrlContentType;
    }

    public Equipo imagenUrlContentType(String imagenUrlContentType) {
        this.imagenUrlContentType = imagenUrlContentType;
        return this;
    }

    public void setImagenUrlContentType(String imagenUrlContentType) {
        this.imagenUrlContentType = imagenUrlContentType;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Equipo valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Set<Equiposinstalados> getEquiposinstalados() {
        return equiposinstalados;
    }

    public Equipo equiposinstalados(Set<Equiposinstalados> equiposinstalados) {
        this.equiposinstalados = equiposinstalados;
        return this;
    }

    public Equipo addEquiposinstalados(Equiposinstalados equiposinstalados) {
        this.equiposinstalados.add(equiposinstalados);
        equiposinstalados.setEquipo(this);
        return this;
    }

    public Equipo removeEquiposinstalados(Equiposinstalados equiposinstalados) {
        this.equiposinstalados.remove(equiposinstalados);
        equiposinstalados.setEquipo(null);
        return this;
    }

    public void setEquiposinstalados(Set<Equiposinstalados> equiposinstalados) {
        this.equiposinstalados = equiposinstalados;
    }

    public Set<EquiposcotizadosToEquipos> getEquiposcotizadosToEquipos() {
        return equiposcotizadosToEquipos;
    }

    public Equipo equiposcotizadosToEquipos(Set<EquiposcotizadosToEquipos> equiposcotizadosToEquipos) {
        this.equiposcotizadosToEquipos = equiposcotizadosToEquipos;
        return this;
    }

    public Equipo addEquiposcotizadosToEquipos(EquiposcotizadosToEquipos equiposcotizadosToEquipos) {
        this.equiposcotizadosToEquipos.add(equiposcotizadosToEquipos);
        equiposcotizadosToEquipos.setEquiposcotizadosToEquipos(this);
        return this;
    }

    public Equipo removeEquiposcotizadosToEquipos(EquiposcotizadosToEquipos equiposcotizadosToEquipos) {
        this.equiposcotizadosToEquipos.remove(equiposcotizadosToEquipos);
        equiposcotizadosToEquipos.setEquiposcotizadosToEquipos(null);
        return this;
    }

    public void setEquiposcotizadosToEquipos(Set<EquiposcotizadosToEquipos> equiposcotizadosToEquipos) {
        this.equiposcotizadosToEquipos = equiposcotizadosToEquipos;
    }

    public Marca getMarca() {
        return marca;
    }

    public Equipo marca(Marca marca) {
        this.marca = marca;
        return this;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Equipo)) {
            return false;
        }
        return id != null && id.equals(((Equipo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Equipo{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", version='" + getVersion() + "'" +
            ", controlador='" + getControlador() + "'" +
            ", accountname='" + getAccountname() + "'" +
            ", controllermacaddress='" + getControllermacaddress() + "'" +
            ", imagenUrl='" + getImagenUrl() + "'" +
            ", imagenUrlContentType='" + getImagenUrlContentType() + "'" +
            ", valor=" + getValor() +
            "}";
    }
}
