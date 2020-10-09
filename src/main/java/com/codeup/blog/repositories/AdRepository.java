package com.codeup.blog.repositories;

import com.codeup.blog.models.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepository extends JpaRepository<Ad, Long> {
}

