
package com.example.demo.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JwtUtilTest {

    private final JwtUtil jwtUtil = new JwtUtil();

    @Test
    void testGenerateToken() {

        String token = jwtUtil.generateToken(
                "admin@gmail.com", "ADMIN");

        assertNotNull(token);
        assertFalse(token.isEmpty());

        System.out.println("Generated Token: " + token);
    }

    @Test
    void testValidateToken() {

        String token = jwtUtil.generateToken(
                "admin@gmail.com", "ADMIN");

        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    void testGetEmailFromToken() {

        String token = jwtUtil.generateToken(
                "admin@gmail.com", "ADMIN");

        assertEquals(
                "admin@gmail.com",
                jwtUtil.getEmail(token));
    }

    @Test
    void testGetRoleFromToken() {

        String token = jwtUtil.generateToken(
                "admin@gmail.com", "ADMIN");

        assertEquals(
                "ADMIN",
                jwtUtil.getRole(token));
    }
}
