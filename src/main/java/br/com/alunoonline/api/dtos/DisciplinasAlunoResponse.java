package br.com.alunoonline.api.dtos;

import br.com.alunoonline.api.enums.MatriculaAlunosStatusEnum;
import lombok.Data;

@Data
public class DisciplinasAlunoResponse {
    private String nomeDisciplna;
    private String nomeProfessor;
    private Double nota1;
    private Double nota2;
    private Double media;
    private MatriculaAlunosStatusEnum status;

}
