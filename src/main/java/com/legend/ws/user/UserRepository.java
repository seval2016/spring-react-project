package com.legend.ws.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Özel sorgular veya işlemler buraya eklenebilir
}
