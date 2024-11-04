package com.PersonalFinanceAPI.PersonalFinanceAPI.service;

import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosCadastroCategoria;
import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosLancarTransacao;
import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.TipoTransacao;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Categoria;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Parcela;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Transacao;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Usuario;
import com.PersonalFinanceAPI.PersonalFinanceAPI.repository.ParcelaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class ParcelaServiceTest {

    @Mock
    private ParcelaRepository parcelaRepository;

    @InjectMocks
    private ParcelaService parcelaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void cenario01(){
        //Transação com parcela única valor 1000

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

        BigDecimal valorEsperado = new BigDecimal("1000.00");
        LocalDate dataVencimentoEsperada =  LocalDate.of(2024, 12, 5);

        when(parcelaRepository.save(any(Parcela.class))).thenReturn(null);

        // Chama o método a ser testado
        List<Parcela> parcelas = parcelaService.gerarParcelas(transacao);


        // Valida se a lista foi criada com o tamanho correto
        assertNotNull(parcelas, "A lista de parcelas não deve ser nula");
        assertEquals(1, parcelas.size(), "O número de parcelas deve ser igual a 1");

        // Valida os elementos da lista
        for (int i = 0; i < parcelas.size(); i++) {
            Parcela parcela = parcelas.get(i);
            assertEquals(dataVencimentoEsperada, parcela.getDataVencimento(), "A data de vencimento está incorreta");
            assertEquals(valorEsperado, parcela.getValorParcela(), "O valor da parcela está incorreto");
            assertEquals(i + 1, parcela.getNumeroParcela(), "O número da parcela está incorreto");
            assertNotNull(parcela.getValorParcela(), "O valor da parcela não deve ser nulo");
            assertNotNull(parcela.getDataVencimento(), "A data de vencimento não deve ser nula");
        }
    }
    @Test
    void cenario02(){
        //Transação com 2 parcelas valorTotal 1000

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
                2), categoria);

        LocalDate dataVencimentoEsperada =  LocalDate.of(2024, 12, 5);
        when(parcelaRepository.save(any(Parcela.class))).thenReturn(null);

        // Chama o método a ser testado
        List<Parcela> parcelas = parcelaService.gerarParcelas(transacao);


        // Valida se a lista foi criada com o tamanho correto
        assertNotNull(parcelas, "A lista de parcelas não deve ser nula");
        assertEquals(2, parcelas.size(), "O número de parcelas deve ser igual a 2");

        // Valida os elementos da lista
        for (int i = 0; i < parcelas.size(); i++) {
            Parcela parcela = parcelas.get(i);
            assertEquals(dataVencimentoEsperada.plusMonths(i), parcela.getDataVencimento(), "A data de vencimento está incorreta");
            BigDecimal somaParcelas = parcelas.stream()
                    .map(Parcela::getValorParcela)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            assertEquals(transacao.getValor(), somaParcelas, "A soma das parcelas deve ser igual ao valor total da transação.");
            assertEquals(i + 1, parcela.getNumeroParcela(), "O número da parcela está incorreto");
            assertNotNull(parcela.getValorParcela(), "O valor da parcela não deve ser nulo");
            assertNotNull(parcela.getDataVencimento(), "A data de vencimento não deve ser nula");
        }
    }

    @Test
    void cenario03(){
        //Transação com 2 semamais parcelas valorTotal 1000

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
                "SEMANAL",
                LocalDate.of(2024, 12, 5),
                2), categoria);

        LocalDate dataVencimentoEsperada =  LocalDate.of(2024, 12, 5);
        when(parcelaRepository.save(any(Parcela.class))).thenReturn(null);

        // Chama o método a ser testado
        List<Parcela> parcelas = parcelaService.gerarParcelas(transacao);


        // Valida se a lista foi criada com o tamanho correto
        assertNotNull(parcelas, "A lista de parcelas não deve ser nula");
        assertEquals(2, parcelas.size(), "O número de parcelas deve ser igual a 2");

        // Valida os elementos da lista
        for (int i = 0; i < parcelas.size(); i++) {
            Parcela parcela = parcelas.get(i);
            assertEquals(dataVencimentoEsperada.plusWeeks(i), parcela.getDataVencimento(), "A data de vencimento está incorreta");
            BigDecimal somaParcelas = parcelas.stream()
                    .map(Parcela::getValorParcela)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            assertEquals(transacao.getValor(), somaParcelas, "A soma das parcelas deve ser igual ao valor total da transação.");
            assertEquals(i + 1, parcela.getNumeroParcela(), "O número da parcela está incorreto");
            assertNotNull(parcela.getValorParcela(), "O valor da parcela não deve ser nulo");
            assertNotNull(parcela.getDataVencimento(), "A data de vencimento não deve ser nula");
        }
    }

    @Test
    void cenario04(){
        //Transação com 3 parcelas semamais  valorTotal 1001, valor quebrado para testar o arredondamento

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
                "SEMANAL",
                LocalDate.of(2024, 12, 5),
                3), categoria);

        LocalDate dataVencimentoEsperada =  LocalDate.of(2024, 12, 5);
        when(parcelaRepository.save(any(Parcela.class))).thenReturn(null);

        // Chama o método a ser testado
        List<Parcela> parcelas = parcelaService.gerarParcelas(transacao);


        // Valida se a lista foi criada com o tamanho correto
        assertNotNull(parcelas, "A lista de parcelas não deve ser nula");
        assertEquals(3, parcelas.size(), "O número de parcelas deve ser igual a 3");

        // Valida os elementos da lista
        for (int i = 0; i < parcelas.size(); i++) {
            Parcela parcela = parcelas.get(i);
            assertEquals(dataVencimentoEsperada.plusWeeks(i), parcela.getDataVencimento(), "A data de vencimento está incorreta");

            BigDecimal somaParcelas = parcelas.stream()
                    .map(Parcela::getValorParcela)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            assertEquals(transacao.getValor(), somaParcelas, "A soma das parcelas deve ser igual ao valor total da transação.");
            assertEquals(i + 1, parcela.getNumeroParcela(), "O número da parcela está incorreto");
            assertNotNull(parcela.getValorParcela(), "O valor da parcela não deve ser nulo");
            assertNotNull(parcela.getDataVencimento(), "A data de vencimento não deve ser nula");
        }
    }
    @Test
    void cenario05(){
        //Transação com quantidade de parcelas muito alta

        Usuario usuario = new Usuario("João Silva",
                "joao.silva@example.com",
                "senhaSegura123");

        Categoria categoria = new Categoria(new DadosCadastroCategoria(
                "Educação",
                "Gastos relacionados a cursos e materiais educacionais",
                new BigDecimal("10000.00")),usuario);

        Transacao transacao = new Transacao( new DadosLancarTransacao(
                "Pagamento de curso online",
                new BigDecimal("1000000.00"),
                LocalDateTime.of(2024, 11, 2, 10, 30),
                TipoTransacao.DESPESA,
                1L,
                "SEMANAL",
                LocalDate.of(2024, 12, 5),
                1000), categoria);

        LocalDate dataVencimentoEsperada =  LocalDate.of(2024, 12, 5);
        when(parcelaRepository.save(any(Parcela.class))).thenReturn(null);

        // Chama o método a ser testado
        List<Parcela> parcelas = parcelaService.gerarParcelas(transacao);


        // Valida se a lista foi criada com o tamanho correto
        assertNotNull(parcelas, "A lista de parcelas não deve ser nula");
        assertEquals(1000, parcelas.size(), "O número de parcelas deve ser igual a 10000");

        // Valida os elementos da lista
        for (int i = 0; i < parcelas.size(); i++) {
            Parcela parcela = parcelas.get(i);
            assertEquals(dataVencimentoEsperada.plusWeeks(i), parcela.getDataVencimento(), "A data de vencimento está incorreta");
            BigDecimal somaParcelas = parcelas.stream()
                    .map(Parcela::getValorParcela)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            assertEquals(transacao.getValor(), somaParcelas, "A soma das parcelas deve ser igual ao valor total da transação.");
            assertEquals(i + 1, parcela.getNumeroParcela(), "O número da parcela está incorreto");
            assertNotNull(parcela.getValorParcela(), "O valor da parcela não deve ser nulo");
            assertNotNull(parcela.getDataVencimento(), "A data de vencimento não deve ser nula");
        }
    }

}