package br.com.alunoonline.api.service;


import br.com.alunoonline.api.dtos.AtualizarNotasRequest;
import br.com.alunoonline.api.enums.MatriculaAlunosStatusEnum;
import br.com.alunoonline.api.model.MatriculaAluno;
import br.com.alunoonline.api.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class MatriculaAlunoService {

    public static final double MEDIA_PARA_APROVACAO = 7.0;

    @Autowired
    MatriculaAlunoRepository matriculaAlunoRepository;

    //  É AQUI QUE O ALUNO VAI SE MATRICULAR
    public void criarMatricula(MatriculaAluno matriculaAluno) {
        matriculaAluno.setStatus(MatriculaAlunosStatusEnum.MATRICULADO);
        matriculaAlunoRepository.save(matriculaAluno);
    }

    // é aqui que o aluno vai trancar sua matricula em alguma disciplina

    public void trancarMatricula(Long matriculaAlunoId) {
        MatriculaAluno matriculaAluno =
                matriculaAlunoRepository.findById(matriculaAlunoId)
                        .orElseThrow(() ->
                                new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "Matricula Aluno não encontrada!"));

        if (!MatriculaAlunosStatusEnum.MATRICULADO.equals(matriculaAluno.getStatus())) {
            // lançar o erro se o status não for matriculado
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Só é possível trancar uma matricula com o status MATRICULADO");
        }

        matriculaAluno.setStatus(MatriculaAlunosStatusEnum.TRANCADO);
        matriculaAlunoRepository.save(matriculaAluno);
    }

    public void atualizaNotas(Long matriculaAlunoId,
                              AtualizarNotasRequest atualizarNotasRequest) {
        MatriculaAluno matriculaAluno =
                matriculaAlunoRepository.findById(matriculaAlunoId)
                        .orElseThrow(() ->
                                new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "Matricula Aluno não encontrada!"));
        // A PEDIDO DO PROFESSOR, NÃO PERMITIR ALUNO TRANCADO APLICAR NOTA ** NOTA -> 10,00
        if (MatriculaAlunosStatusEnum.TRANCADO.equals(matriculaAluno.getStatus())) {
            // lançar o erro se o status estiver TRANCADO
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Só é possível aplicar nota em Alunos MATRICULADOS");
        }

        //verifica se o front esta mandando nota 1
        // atualizarNotasRequest.getNota1(): Traz a nota que vem do front
        if (atualizarNotasRequest.getNota1() != null) {
            // matriculaAluno.setNota1: Atualiza a nota1 que vem atualmente do BD
            matriculaAluno.setNota1(atualizarNotasRequest.getNota1());
        }
        //verifica se o front esta mandando nota 2
        // atualizarNotasRequest.getNota2(): Traz a nota que vem do front
        if (atualizarNotasRequest.getNota2() != null) {
            // matriculaAluno.setNota2: Atualiza a nota2 que vem atualmente do BD
            matriculaAluno.setNota2(atualizarNotasRequest.getNota2());
        }

        calculaMedia(matriculaAluno);
        matriculaAlunoRepository.save(matriculaAluno);

    }

    private void calculaMedia(MatriculaAluno matriculaAluno) {
        Double nota1 = matriculaAluno.getNota1();
        Double nota2 = matriculaAluno.getNota2();

        if (nota1 != null && nota2 != null) {
            Double media = (nota1 + nota2) / 2;
            matriculaAluno.setStatus(media >= MEDIA_PARA_APROVACAO ? MatriculaAlunosStatusEnum.APROVADO : MatriculaAlunosStatusEnum.REPROVADO);
        }
    }

}
