package com.atman.server.UserModule.Service.Impl;

public interface TokenService {
    String generateAccessToken(String contactNumber);

    String generateRefreshToken(String contactNumber);

    boolean verifyToken(String token);

    String getContactNumberFromToken(String token);
}
