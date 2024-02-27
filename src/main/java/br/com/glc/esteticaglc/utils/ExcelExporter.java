package br.com.glc.esteticaglc.utils;

import br.com.glc.esteticaglc.entities.Cliente;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ExcelExporter {

    public ByteArrayOutputStream exportToExcel(List<Cliente> clientes) throws IOException {
        // Cria um novo ByteArrayOutputStream para armazenar os dados do arquivo Excel
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Cria um novo workbook Excel
        try (Workbook workbook = new HSSFWorkbook()) {
            // Cria uma nova planilha
            Sheet sheet = workbook.createSheet("Clientes");

            // Cria a primeira linha para os cabeçalhos
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Código", "Nome", "CPF", "RG", "Telefone", "Endereço"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Preenche as linhas com os dados dos clientes
            int rowNum = 1;
            for (Cliente cliente : clientes) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(cliente.getCodigo());
                row.createCell(1).setCellValue(cliente.getNome());
                row.createCell(2).setCellValue(cliente.getCpf());
                row.createCell(3).setCellValue(cliente.getRg());
                row.createCell(4).setCellValue(cliente.getTelefone());
                row.createCell(5).setCellValue(cliente.enderecoCompleto());
            }

            // Escreve o workbook no ByteArrayOutputStream
            workbook.write(outputStream);
        }

        return outputStream;
    }

}
