package com.PersonalFinanceAPI.PersonalFinanceAPI.service;

import com.PersonalFinanceAPI.PersonalFinanceAPI.dto.MetaFinanceiraDTO;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Categoria;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.MetaFinanceira;
import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Usuario;
import com.PersonalFinanceAPI.PersonalFinanceAPI.service.repository.MetaFinanceiraRepository;
import com.PersonalFinanceAPI.PersonalFinanceAPI.service.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MetaFinanceiraService {
    MetaFinanceiraRepository metaFinanceiraRepository;

    UsuarioRepository usuarioRepository;

    @Autowired
    public MetaFinanceiraService (MetaFinanceiraRepository metaFinanceiraRepository, UsuarioRepository usuarioRepository){
        this.metaFinanceiraRepository = metaFinanceiraRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public MetaFinanceiraDTO cadastrarMetaFinanceira(MetaFinanceiraDTO dados, Long usuarioId) {

        Usuario usuario = usuarioRepository.getReferenceById(usuarioId);

        MetaFinanceira metaFinanceira = new MetaFinanceira(dados, usuario);

        return metaFinanceiraRepository.save(dados);









    }
}
