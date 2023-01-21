package ch.supsi.backend_api_rest.security.jwt;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository
public class TokenRefreshRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public TokenRefreshRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    public void save(String token, String username, long expiration, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(username, token,expiration, timeUnit);
    }
    public void loginUser(String username)
    {
        redisTemplate.opsForValue().set("logged:"+username, Instant.now().toString());
    }
    public void logoutUser(String username)
    {
        redisTemplate.delete("logged:"+username);
    }
    public boolean checkIfLogged(String username)
    {
        var user=redisTemplate.opsForValue().get("logged:"+username);
        return user != "" && user != null;
    }
    public String find(String username) {
        return redisTemplate.opsForValue().get(username);
    }
    public void delete(String token) {
        redisTemplate.delete(token);
    }
    public void resetSessions(){
        Set<String> keys=redisTemplate.keys("logged:*");
        redisTemplate.delete(keys);
    }
}
