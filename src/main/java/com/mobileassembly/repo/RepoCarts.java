package com.mobileassembly.repo;

import com.mobileassembly.models.Carts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoCarts extends JpaRepository<Carts, Long> {
}
