package com.codeup.blog.repositories;

import com.codeup.blog.models.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AdRepository extends JpaRepository<Ad, Long> {
    Ad findByTitle(String title);
}

