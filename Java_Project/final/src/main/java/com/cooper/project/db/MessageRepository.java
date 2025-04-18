package com.cooper.project.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooper.project.*;
import com.cooper.project.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{

}