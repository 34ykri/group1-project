package dmacc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dmacc.beans.SavedEntity;

@Repository
public interface SavedRepository extends JpaRepository<SavedEntity, Integer>{

}
