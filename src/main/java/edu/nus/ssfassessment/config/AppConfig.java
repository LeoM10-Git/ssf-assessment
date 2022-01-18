package edu.nus.ssfassessment.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Optional;
import java.util.logging.Logger;

import static edu.nus.ssfassessment.Constants.*;

@Configuration
@EnableRedisRepositories
public class AppConfig {
    private final Logger logger = Logger.getLogger(AppConfig.class.getName());

    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.port}")
    private Optional<Integer> redisPort;
    @Value("${spring.redis.database}")
    private int redisDatabase;

    private final String redisPassword;

    public AppConfig() {
        redisPassword = System.getenv(ENV_REDIS_PASSWORD);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {

        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setDatabase(redisDatabase);
        config.setHostName(redisHost);
        config.setPort(redisPort.get());
        if (null != redisPassword) {
            config.setPassword(redisPassword);
            logger.info("Set Redis password");
        }

        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisFac);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer<Object> jrs = new Jackson2JsonRedisSerializer<>(Object.class);
        redisTemplate.setValueSerializer(jrs);

        return redisTemplate;
    }
}