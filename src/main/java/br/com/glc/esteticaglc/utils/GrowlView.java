package br.com.glc.esteticaglc.utils;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class GrowlView {

    public static void showInfo(String summary, String message) {
        addMessage(FacesMessage.SEVERITY_INFO, summary, message);
    }

    public static void showWarn(String summary, String message) {
        addMessage(FacesMessage.SEVERITY_WARN, summary, message);
    }

    public static void showError(String summary, String message) {
        addMessage(FacesMessage.SEVERITY_ERROR, summary, message);
    }

    public static void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

}
