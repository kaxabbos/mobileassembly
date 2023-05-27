package com.mobileassembly.repo;

import com.mobileassembly.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoProducts extends JpaRepository<Products, Long> {
}
