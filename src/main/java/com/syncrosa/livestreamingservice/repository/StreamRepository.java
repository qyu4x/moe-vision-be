package com.syncrosa.livestreamingservice.repository;

import com.syncrosa.livestreamingservice.entity.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StreamRepository extends JpaRepository<Stream, String> {

    Optional<Stream> findFirstByToken(String token);

    List<Stream> findAllByIsStreamActive(Boolean status);

}
