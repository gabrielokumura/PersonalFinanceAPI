package com.PersonalFinanceAPI.PersonalFinanceAPI.repository;

import com.PersonalFinanceAPI.PersonalFinanceAPI.model.MetaFinanceira;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetaFinanceiraRepository extends JpaRepository<MetaFinanceira,Long> {
}
