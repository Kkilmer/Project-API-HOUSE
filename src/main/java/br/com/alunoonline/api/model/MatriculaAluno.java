package br.com.alunoonline.api.model;

import br.com.alunoonline.api.enums.MatriculaAlunosStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.security.PrivilegedAction;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class MatriculaAluno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "/aluno_id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "/disciplina_id")
    private Disciplina disciplina;

    private Double nota1;

    private Double nota2;

    @Enumerated(EnumType.STRING)
    private MatriculaAlunosStatusEnum status;


}
