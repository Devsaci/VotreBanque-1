package org.sid.dao;

import org.sid.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Operationrepository extends JpaRepository<Operation, Long>{

}
