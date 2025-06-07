package br.com.presenca.controle.domain.entity;

import br.com.presenca.controle.domain.exception.AtividadeFechadaException;
import br.com.presenca.controle.domain.exception.AtividadeJaConcluidaException;
import br.com.presenca.controle.domain.exception.TempoDeToleranciaNaoAtingidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AtividadeTest {

    private Atividade atividade;
    private LocalDateTime horarioBase;
    private String usuarioId = "usuario-1";
    private String atividadeId = "atividade-1";

    @BeforeEach
    void setUp() {
        atividade = Atividade.create("Atividade Teste", 30); // 30 minutos de tempo de conclusão
        horarioBase = LocalDateTime.of(2024, 1, 1, 10, 0);
    }

    @Test
    @DisplayName("Deve criar uma atividade com sucesso")
    void deveCriarAtividadeComSucesso() {
        // given
        String descricao = "Atividade Teste";
        int tempoDeConclusao = 30;
        int presencasNecessarias = 3;

        // when
        Atividade novaAtividade = Atividade.create(descricao, tempoDeConclusao, presencasNecessarias);

        // then
        assertNotNull(novaAtividade);
        assertNotNull(novaAtividade.getId());
        assertEquals(descricao, novaAtividade.getDescricao());
        assertEquals(tempoDeConclusao, novaAtividade.getTempoDeConclusao());
        assertEquals(presencasNecessarias, novaAtividade.getPresencasNecessarias());
        assertTrue(novaAtividade.getPresencas().isEmpty());
        assertTrue(novaAtividade.isOpen());
    }

    @Test
    @DisplayName("Deve registrar primeira presença com sucesso")
    void deveRegistrarPrimeiraPresencaComSucesso() {
        // when
        Presenca presenca = atividade.marcarPresenca(usuarioId, horarioBase);

        // then
        assertNotNull(presenca);
        assertEquals(usuarioId, presenca.getUsuarioId());
        assertEquals(atividade.getId(), presenca.getAtividadeId());
        assertEquals(1, atividade.getPresencas().size());
    }

    @Test
    @DisplayName("Deve registrar segunda presença quando tempo mínimo for atingido")
    void deveRegistrarSegundaPresencaQuandoTempoMinimoForAtingido() {
        // given
        Presenca primeiraPresenca = Presenca.registra(usuarioId, atividade.getId(), horarioBase);
        atividade.getPresencas().add(primeiraPresenca);

        // when
        Presenca segundaPresenca = atividade.marcarPresenca(usuarioId, horarioBase.plusMinutes(31));

        // then
        assertNotNull(segundaPresenca);
        assertEquals(2, atividade.getPresencas().size());
        assertTrue(segundaPresenca.getHorario().isAfter(primeiraPresenca.getHorario()));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar marcar segunda presença antes do tempo mínimo")
    void deveLancarExcecaoAoMarcarSegundaPresencaAntesDoTempoMinimo() {
        // given
        Presenca primeiraPresenca = Presenca.registra(usuarioId, atividadeId, horarioBase);
        atividade.getPresencas().add(primeiraPresenca);

        // when & then
        assertThrows(TempoDeToleranciaNaoAtingidoException.class, () -> {
            atividade.marcarPresenca(usuarioId, horarioBase.plusSeconds(1));
        });
    }

    @Test
    @DisplayName("Deve verificar se contém presença corretamente")
    void deveVerificarSeContemPresenca() {
        // given
        Presenca presenca = Presenca.registra(usuarioId, atividadeId);
        atividade.getPresencas().add(presenca);

        // when & then
        assertTrue(atividade.contem(presenca));
        assertFalse(atividade.contem(Presenca.registra("outro-usuario-id", atividadeId)));
    }

    @Test
    @DisplayName("Deve permitir múltiplas presenças de diferentes usuários")
    void devePermitirMultiplasPresencasDeDiferentesUsuarios() {
        // when
        Presenca presenca1 = atividade.marcarPresenca(usuarioId, horarioBase);
        Presenca presenca2 = atividade.marcarPresenca("usuario-2", horarioBase);

        // then
        assertEquals(2, atividade.getPresencas().size());
        assertNotEquals(presenca1.getUsuarioId(), presenca2.getUsuarioId());
    }

    @Test
    @DisplayName("Deve permitir que usuário participante continue participando mesmo com atividade fechada")
    void devePermitirUsuarioParticipanteContinuarParticipandoComAtividadeFechada() {
        // given
        Presenca primeiraPresenca = Presenca.registra(usuarioId, atividadeId, horarioBase);
        atividade.getPresencas().add(primeiraPresenca);
        atividade.fechar();

        // when
        Presenca segundaPresenca = atividade.marcarPresenca(usuarioId, horarioBase.plusMinutes(31));

        // then
        assertNotNull(segundaPresenca);
        assertEquals(2, atividade.getPresencas().size());
        assertTrue(segundaPresenca.getHorario().isAfter(primeiraPresenca.getHorario()));
    }

    @Test
    @DisplayName("Deve impedir que novo usuário participe de atividade fechada")
    void deveImpedirNovoUsuarioParticiparDeAtividadeFechada() {
        // given
        atividade.fechar();
        Usuario novoUsuario = new Usuario("novo-usuario");

        // when & then
        AtividadeFechadaException exception = assertThrows(AtividadeFechadaException.class, () -> {
            atividade.marcarPresenca(novoUsuario);
        });
        assertEquals(atividade, exception.getAtividade());
    }

    @Test
    @DisplayName("Deve impedir nova presença após usuário concluir atividade")
    void deveImpedirNovaPresencaAposUsuarioConcluirAtividade() {
        // given
        atividade = Atividade.create("Atividade Teste", 30, 2);
        
        // Primeira presença
        atividade.marcarPresenca(usuarioId, horarioBase);
        
        // Segunda presença (completa a atividade)
        atividade.marcarPresenca(usuarioId, horarioBase.plusMinutes(31));

        // when & then
        assertThrows(AtividadeJaConcluidaException.class, () -> {
            atividade.marcarPresenca(usuarioId, horarioBase.plusMinutes(62));
        });
    }

    @Test
    @DisplayName("Deve permitir presenças até atingir quantidade necessária")
    void devePermitirPresencasAteAtingirQuantidadeNecessaria() {
        // given
        atividade = Atividade.create("Atividade Teste", 30, 3);
        
        // when
        atividade.marcarPresenca(usuarioId, horarioBase);
        atividade.marcarPresenca(usuarioId, horarioBase.plusMinutes(31));
        atividade.marcarPresenca(usuarioId, horarioBase.plusMinutes(62));

        // then
        assertEquals(3, atividade.getPresencas().size());
        assertThrows(AtividadeJaConcluidaException.class, () -> {
            atividade.marcarPresenca(usuarioId, horarioBase.plusMinutes(93));
        });
    }

    @Test
    @DisplayName("Deve permitir que diferentes usuários completem a atividade independentemente")
    void devePermitirDiferentesUsuariosCompletaremAtividadeIndependentemente() {
        // given
        atividade = Atividade.create("Atividade Teste", 30, 2);
        
        String outroUsuarioId = "usuario-2";
        
        // when
        // Usuário 1 completa a atividade
        atividade.marcarPresenca(usuarioId, horarioBase);
        atividade.marcarPresenca(usuarioId, horarioBase.plusMinutes(31));
        
        // Usuário 2 ainda pode completar a atividade
        atividade.marcarPresenca(outroUsuarioId, horarioBase.plusMinutes(31));
        atividade.marcarPresenca(outroUsuarioId, horarioBase.plusMinutes(62));

        // then
        assertEquals(4, atividade.getPresencas().size());
        assertThrows(AtividadeJaConcluidaException.class, () -> {
            atividade.marcarPresenca(usuarioId, horarioBase.plusMinutes(93));
        });
        assertThrows(AtividadeJaConcluidaException.class, () -> {
            atividade.marcarPresenca(outroUsuarioId, horarioBase.plusMinutes(93));
        });
    }
} 