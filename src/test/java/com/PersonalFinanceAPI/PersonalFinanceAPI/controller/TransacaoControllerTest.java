package com.PersonalFinanceAPI.PersonalFinanceAPI.controller;

import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.DadosLancarTransacao;
import com.PersonalFinanceAPI.PersonalFinanceAPI.service.TransacaoService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransacaoControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private TransacaoService transacaoService;

    private String extraiaTokenDoResponse(String responseContent) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseContent);
        return jsonNode.get("token").asText();
    }

    private String obterToken() throws Exception {
        String jsonLogin = "{\"email\":\"aldin9423@uorak.com\", \"senha\":\"123456\"}";

        var result = mvc.perform(post("/login")
                        .content(jsonLogin)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        String responseContent = result.getContentAsString();
        System.out.println(responseContent);
        // Extraia o token do JSON de resposta (use uma biblioteca como Jackson, se necessário)
        return extraiaTokenDoResponse(responseContent);
    }

    @Test
    void deveriaDevolverOCodigo400ParaSolicitacaoDeTransacaoComErros() throws Exception {
        //ARRANGE
        String json = "{ }";

        String token = obterToken();

        //ACT
        var response = mvc.perform(post("/transacao")
                        .header("Authorization", token)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        //ASSERT
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaSalvarATransacaoCorretamenteEDevolverOCodico200OK() throws Exception {
        //ARRANGE
        String json = """
                {
                    "descricao": "99/uber",
                    "valor": 200.00,
                    "data": "2024-10-23T12:00:00",
                    "tipo": "DESPESA",
                    "categoriaId": 1,
                    "quantidadeParcelas": 12,
                    "periodicidade": "MENSAL",
                    "dataVencimento": "2024-12-01"
                }
                """;
        String token = obterToken();

        //ACT
        var response = mvc.perform(post("/transacao")
                        .header("Authorization", token)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        //ASSERT

        Assertions.assertEquals(200, response.getStatus());
    }
}