/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.hometechclaim.service.dto.requerimiento;

import java.util.List;

/**
 *
 * @author 57304
 */
public class RequerimientoToSolucion {
    
    private List<String> idSoluciones;
    private String observacion;
    private String idToken;
    private String idUser;

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
    

    public List<String> getIdSoluciones() {
        return idSoluciones;
    }

    public void setIdSoluciones(List<String> idSoluciones) {
        this.idSoluciones = idSoluciones;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    
    
    
}
