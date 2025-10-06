package com.cs.BatchAutoGenerateParameter.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cs.BatchAutoGenerateParameter.entities.Terminal;


@Repository
public interface ITerminalJPARepository extends JpaRepository<Terminal, Integer> {

	Terminal findByTerminalId(String terminalId);
}
