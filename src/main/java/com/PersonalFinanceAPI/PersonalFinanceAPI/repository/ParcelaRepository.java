package com.PersonalFinanceAPI.PersonalFinanceAPI.repository;

import com.PersonalFinanceAPI.PersonalFinanceAPI.model.Parcela;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelaRepository extends JpaRepository<Parcela,Long> {
}
