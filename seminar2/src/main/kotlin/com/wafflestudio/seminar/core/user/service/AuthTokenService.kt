package com.wafflestudio.seminar.core.user.service

import com.wafflestudio.seminar.core.user.database.UserRepository
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import java.util.*

@Service
@EnableConfigurationProperties(AuthProperties::class)
class AuthTokenService(
    private val authProperties: AuthProperties,
    private val userRepository: UserRepository
) {
    private val tokenPrefix = "Bearer "
    private val signingKey = Keys.hmacShaKeyFor(authProperties.jwtSecret.toByteArray())

    /**
     * TODO Jwts.builder() 라이브러리를 통해서, 어떻게 필요한 정보를 토큰에 넣어 발급하고,
     *   검증할지, 또 만료는 어떻게 시킬 수 있을지 고민해보아요.
     */
    fun generateTokenByUsername(username: String): AuthToken {
        val claims: MutableMap<String, Any> = hashMapOf("username" to username)
        val now = Date()
        val expiryDate: Date = Date(now.time + authProperties.jwtExpiration * 1000)
        val resultToken = tokenPrefix + Jwts.builder().setClaims(claims)
            .setIssuer(authProperties.issuer)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(signingKey)
            .compact()
        return AuthToken(resultToken)
    }

    fun generateTokenByEmail(email: String): AuthToken {
        val claims: MutableMap<String, Any> = hashMapOf("email" to email)
        val now = Date()
        val expiryDate: Date = Date(now.time + authProperties.jwtExpiration * 1000)
        val resultToken = tokenPrefix + Jwts.builder().setClaims(claims)
            .setIssuer(authProperties.issuer)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(signingKey)
            .compact()
        return AuthToken(resultToken)
    }


    fun verifyToken(authToken: String?) : Boolean{
        if (authToken.isNullOrEmpty()) {
            throw AuthException("토큰이 주어지지 않았습니다.")
        }
        if (!authToken.startsWith(tokenPrefix)) {
            throw AuthException("토큰이 bearer type이 아닙니다.")
        }
        val jwsClaims : Jws<Claims> = parse(authToken)
        val email = jwsClaims.body.get("email", String::class.java)
        if (email.isNullOrEmpty()) {
            throw AuthException("토큰이 유효하지 않습니다.")
        }
        return true
    }

    fun getCurrentUserId(authToken: String?): Long {
        if (authToken.isNullOrEmpty()) {
            throw AuthException("토큰이 주어지지 않았습니다.")
        }
        val jwsClaims : Jws<Claims> = parse(authToken)
        val email = jwsClaims.body.get("email", String::class.java)
        val userEntity = userRepository.findByEmail(email)
        return userEntity!!.id
    }

    /**
     * TODO Jwts.parserBuilder() 빌더 패턴을 통해 토큰을 읽어올 수도 있습니다.
     *   적절한 인증 처리가 가능하도록 구현해주세요!
     */
    private fun parse(authToken: String): Jws<Claims> {
        val prefixRemoved = authToken.replace(tokenPrefix, "").trim { it <= ' ' }
        return try {
            Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(prefixRemoved)
        } catch (e: SecurityException) {
            throw AuthException("Signature가 유효하지 않습니다.")
        } catch (e: MalformedJwtException) {
            throw AuthException("토큰이 유효하지 않습니다.")
        } catch (e: ExpiredJwtException) {
            throw AuthException("토큰의 유효기간이 지났습니다. 다시 로그인해 주세요.")
        } catch (e: UnsupportedJwtException) {
            throw AuthException("Unsupported 토큰입니다.")
        } catch (e: IllegalArgumentException) {
            throw AuthException("토큰의 claims가 비었습니다.")
        }
    }
}