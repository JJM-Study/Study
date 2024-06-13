package kr.co.hanbit.product.management.infrastructure;

import kr.co.hanbit.product.management.application.EntityNotFoundException;
import kr.co.hanbit.product.management.domain.Product;
import kr.co.hanbit.product.management.domain.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterDisposer;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("prod")
public class DatabaseProductRepository implements ProductRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public DatabaseProductRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    public Product add(Product product) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(product);

        namedParameterJdbcTemplate.update("INSERT INTO products(name, price, amount) VALUES ( :name, :price, :amount)", namedParameter, keyHolder);

        Long generated = keyHolder.getKey().longValue();
        product.setId(generated);

        return product;
    }

    public Product findById(Long id) {
        SqlParameterSource namedParamter = new MapSqlParameterSource("id", id);

        Product product = null;

        try {
            product = namedParameterJdbcTemplate.queryForObject("SELECT * FROM products WHERE id = :id", namedParamter, new BeanPropertyRowMapper<>(Product.class));
        } catch (EmptyResultDataAccessException exception) {
            throw new EntityNotFoundException("Product를 찾지 못했습니다.");
        }
        return product;
    }

    public List<Product> findAll() {
        List<Product> products = namedParameterJdbcTemplate.query("SELECT * FROM products",  new BeanPropertyRowMapper<>(Product.class));

        return products;
    }

    public List<Product> findByNameContaining(String name) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("name", "%" + name + "%");

        List<Product> products = namedParameterJdbcTemplate.query("SELECT * FROM products WHERE name Like :name", namedParameter, new BeanPropertyRowMapper<>(Product.class));

        return products;
    }

    public Product update(Product product) {
        SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(product);

        namedParameterJdbcTemplate.update("UPDATE products SET name = :name, price = :price, amount = :amount WHERE id = :id ", namedParameter);

        return product;
    }

    public void delete(Long id) {
        SqlParameterSource namedParater = new MapSqlParameterSource("id", id);

        namedParameterJdbcTemplate.update("DELETE FROM products WHERE id = :id", namedParater);
    }

}
