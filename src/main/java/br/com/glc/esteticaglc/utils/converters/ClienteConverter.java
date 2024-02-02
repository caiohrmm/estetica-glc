package br.com.glc.esteticaglc.utils.converters;

import br.com.glc.esteticaglc.entities.Cliente;
import br.com.glc.esteticaglc.services.ClienteService;
import io.micrometer.common.util.StringUtils;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FacesConverter(forClass = Cliente.class)
public class ClienteConverter implements Converter<Cliente> {

    @Autowired
    private ClienteService clienteService;

    @Override
    public Cliente getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        Cliente retorno = null;

        if (StringUtils.isNotBlank(value)) {
            retorno = this.clienteService.buscarPorCodigo(Long.parseLong(value));
        }
        return retorno;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Cliente value) {
        if (value != null) {
            Long codigo = ((Cliente) value).getCodigo();
            String retorno = (codigo == null ? null : codigo.toString());

            return retorno;
        }

        return "";
    }
}
