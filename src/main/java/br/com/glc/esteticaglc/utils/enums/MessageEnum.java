package br.com.glc.esteticaglc.utils.enums;

import lombok.Getter;

@Getter
public enum MessageEnum {

    MSG_SUCESSO("Sucesso."),
    MSG_SALVO_SUCESSO("Registro salvo com sucesso."),
    MSG_AVISO("Aviso."),
    MSG_ERRO("Erro."),
    MSG_ERRO_SALVAR("Ocorreu um erro ao salvar."),
    MSG_IMPORT_SUCESSO("Importado com sucesso."),
    MSG_EXCLUIDO_SUCESSO("Excluido com sucesso."),
    MSG_OPERACAO_CONCLUIDA_SUCESSO("Operação concluída com sucesso."),
    MSG_NENHUM_REGISTRO("Nenhum registro encontrado."),
    MSG_REGISTRO_EXISTENTE("Registro já existente.");

    private String msg;

    private MessageEnum(String msg) {
        this.msg = msg;
    }

}
