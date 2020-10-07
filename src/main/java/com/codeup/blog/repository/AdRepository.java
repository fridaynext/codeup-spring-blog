package com.codeup.blog.repository;

import com.codeup.blog.models.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Long> {
    Ad findByTitle(String title); // select * from ads where title = ?
    Ad findFirstByTitle(String title); // select * from ads where title = ? limit 1

    // The following method is equivalent to the built in `getOne` method, there's no need to create this example
    @Query("from Ad a where a.id like ?1")
    Ad getAdById(long id);

    // The following method shows you how to use named parameters in a HQL custom query:
    @Query("from Ad a where a.title like %:term%")
    List<Ad> searchByTitleLike(@Param("term") String term);

    // The following method shows you how to use named parameters in a HQL custom query:
    @Query("from Ad a where a.description like %:term%")
    List<Ad> searchByDescriptionLike(@Param("term") String term);
}
