package com.PersonalFinanceAPI.PersonalFinanceAPI.service;

import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosCadastroCategoria;
import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosLancarTransacao;
import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.TipoTransacao;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Categoria;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Transacao;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Usuario;
import com.PersonalFinanceAPI.PersonalFinanceAPI.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Habilita o Mockito
class TransacaoServiceTest {
    @Mock
    private EmailService emailService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private TransacaoService transacaoService;

    @BeforeEach
    public void setUp() {
        // Não é necessário chamar MockitoAnnotations.openMocks(this) aqui
    }
    @Test
    void deveAtualizarSaldoParaTransacaoDeReceita(){
    //transação de receita
        Usuario usuario = new Usuario("João Silva",
                "joao.silva@example.com",
                "senhaSegura123");
        Categoria categoria = new Categoria(new DadosCadastroCategoria(
                "Educação",
                "Gastos relacionados a cursos e materiais educacionais",
                new BigDecimal("1000.00")),usuario);

        Transacao transacao = new Transacao( new DadosLancarTransacao(
                "Pagamento de curso online",
                new BigDecimal("1000.00"),
                LocalDateTime.of(2024, 11, 2, 10, 30),
                TipoTransacao.RECEITA,
                1L,
                "MENSAL",
                LocalDate.of(2024, 12, 5),
                1), categoria);

        // Chama o método a ser testado
        transacaoService.atualizarSaldoUsuario(transacao);


        assertEquals(new BigDecimal("1000.00"), usuario.getSaldo(), "A transação não acrescentou o valor correto ao usuário");
        // Verifica se o método save foi chamado com o usuário correto
        verify(usuarioRepository, times(1)).save(usuario);
        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository, times(1)).save(captor.capture());
        Usuario usuarioSalvo = captor.getValue();
        assertEquals(new BigDecimal("1000.00"), usuarioSalvo.getSaldo(), "O saldo do usuário salvo não está correto");
    }

    @Test
    void deveAtualizarSaldoParaTransacaoDeDespesa(){
        //transação de Despesa
        Usuario usuario = new Usuario("João Silva",
                "joao.silva@example.com",
                "senhaSegura123");
        Categoria categoria = new Categoria(new DadosCadastroCategoria(
                "Educação",
                "Gastos relacionados a cursos e materiais educacionais",
                new BigDecimal("1000.00")),usuario);

        Transacao transacao = new Transacao( new DadosLancarTransacao(
                "Pagamento de curso online",
                new BigDecimal("1000.00"),
                LocalDateTime.of(2024, 11, 2, 10, 30),
                TipoTransacao.DESPESA,
                1L,
                "MENSAL",
                LocalDate.of(2024, 12, 5),
                1), categoria);

        // Chama o método a ser testado
        transacaoService.atualizarSaldoUsuario(transacao);

        assertEquals(new BigDecimal("-1000.00"), usuario.getSaldo(), "A transação não acrescentou o valor correto ao usuário");
        // Verifica se o método save foi chamado com o usuário correto
        verify(usuarioRepository, times(1)).save(usuario);
        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository, times(1)).save(captor.capture());
        Usuario usuarioSalvo = captor.getValue();
        assertEquals(new BigDecimal("-1000.00"), usuarioSalvo.getSaldo(), "O saldo do usuário salvo não está correto");
    }
    @Test
    void envioDoEmail(){
        // Transação de Despesa
        Usuario usuario = new Usuario("João Silva",
                "joao.silva@example.com",
                "senhaSegura123");
        Categoria categoria = new Categoria(new DadosCadastroCategoria(
                "Educação",
                "Gastos relacionados a cursos e materiais educacionais",
                new BigDecimal("-10.00")), usuario);

        Transacao transacao = new Transacao(new DadosLancarTransacao(
                "Pagamento de curso online",
                new BigDecimal("1000.00"),
                LocalDateTime.of(2024, 11, 2, 10, 30),
                TipoTransacao.DESPESA,
                1L,
                "MENSAL",
                LocalDate.of(2024, 12, 5),
                1), categoria);

        // Chama o método a ser testado
        transacaoService.atualizarSaldoUsuario(transacao);

        // Verifica se o email foi enviado
        verify(emailService).enviarEmail(
                "joao.silva@example.com",
                "Orçamento Atingido",
                "com sua ultima transação da categoria Educação Você atingiu o orçamento predeterminado"
        );

        // Verifica se o saldo foi atualizado
        assertEquals(new BigDecimal("-1000.00"), usuario.getSaldo(), "A transação não acrescentou o valor correto ao usuário");

        // Verifica se o método save foi chamado com o usuário correto
        verify(usuarioRepository, times(1)).save(usuario);
        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository, times(1)).save(captor.capture());
        Usuario usuarioSalvo = captor.getValue();
        assertEquals(new BigDecimal("-1000.00"), usuarioSalvo.getSaldo(), "O saldo do usuário salvo não está correto");
    }
}
