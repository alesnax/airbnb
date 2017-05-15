package com.epam.apartment.dao.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.epam.apartment.dao.UserDao;
import com.epam.apartment.model.PasswordResetToken;
import com.epam.apartment.model.User;

@Repository
public class JdbcUserDao implements UserDao {

	private static final String INSERT_USER_SQL = "INSERT INTO USERS (U_EMAIL, U_NAME, U_SURNAME, U_PASSWORD, U_BIRTHDAY) VALUES (:p_EMAIL, :p_NAME, :p_SURNAME, :p_PSWD, :p_BIRTHDAY)";
	private static final String SELECT_USER_BY_EMAIL = "SELECT U_ID, U_EMAIL, U_NAME, U_SURNAME, U_BIRTHDAY, U_PASSWORD FROM USERS WHERE U_EMAIL = :p_EMAIL";
	private static final String UPDATE_PASSWORD_BY_ID = "UPDATE USERS SET U_PASSWORD = :p_NEW_PSWD WHERE U_ID = :p_ID";
	private static final String UPDATE_USER_BY_ID = "UPDATE USERS SET U_EMAIL = :email, U_NAME = :name, U_SURNAME = :surname WHERE U_ID = :id";
	private static final String SELECT_USER_BY_ID = "SELECT U_ID, U_EMAIL, U_NAME, U_SURNAME, U_BIRTHDAY, U_PASSWORD FROM USERS WHERE U_ID = :p_ID";
	private static final String UPDATE_PSWD_BY_ID = "UPDATE USERS SET U_PASSWORD = :p_PSWD WHERE U_ID = :p_ID";
	private static final String UPDATE_RESET_PASSWORD_TOKEN = "UPDATE USERS SET U_RESET_PASS_TOKEN = :p_RESET_TOKEN, U_TOKEN_EXPIRE_DATE = :p_EXPIRE_DATE  WHERE U_ID = :p_ID";
	private static final String SELECT_RESET_TOKEN = "SELECT U_ID, U_EMAIL, U_RESET_PASS_TOKEN, U_TOKEN_EXPIRE_DATE FROM USERS WHERE U_RESET_PASS_TOKEN = :p_RESET_TOKEN";

	private static final int EXPECTED_ROW_NUMBER = 1;

	private static final String USER_ID = "U_ID";
	private static final String EMAIL = "U_EMAIL";
	private static final String NAME = "U_NAME";
	private static final String SURNAME = "U_SURNAME";
	private static final String BIRTHDAY = "U_BIRTHDAY";
	private static final String RESET_PASS_TOKEN = "U_RESET_PASS_TOKEN";
	private static final String TOKEN_EXPIRE_DATE = "U_TOKEN_EXPIRE_DATE";
	private static final String PASSWORD = "U_PASSWORD";

	private static final String EMAIL_PARAM = "p_EMAIL";
	private static final String PSWD_PARAM = "p_PSWD";
	private static final String NAME_PARAM = "p_NAME";
	private static final String SURNAME_PARAM = "p_SURNAME";
	private static final String BIRTHDAY_PARAM = "p_BIRTHDAY";
	private static final String ID_PARAM = "p_ID";
	private static final String NEW_PSWD_PARAM = "p_NEW_PSWD";
	private static final String RESET_PASS_PARAM = "p_RESET_TOKEN";
	private static final String EXPIRE_DATE_PARAM = "p_EXPIRE_DATE";

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public void insertNewUser(User user, String password) {
		Date bithday = user.getBirthday() != null ? Date.valueOf(user.getBirthday()) : null;

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue(EMAIL_PARAM, user.getEmail()).addValue(NAME_PARAM, user.getName()).addValue(SURNAME_PARAM, user.getSurname()).addValue(PSWD_PARAM, password).addValue(BIRTHDAY_PARAM,
				bithday);

		this.jdbcTemplate.update(INSERT_USER_SQL, namedParameters);
	}

	@Override
	public boolean changePswd(int id, String newPswd) {
		boolean updated = false;
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(ID_PARAM, id).addValue(NEW_PSWD_PARAM, newPswd);

		int rowsAffected = this.jdbcTemplate.update(UPDATE_PASSWORD_BY_ID, namedParameters);
		if (rowsAffected == EXPECTED_ROW_NUMBER) {
			updated = true;
		}
		return updated;
	}

	@Override
	public void restorePswd(long id, String newPswd) {
		// boolean updated = false;
		MapSqlParameterSource namedParameters = new MapSqlParameterSource().addValue(ID_PARAM, id).addValue(PSWD_PARAM, newPswd);
		this.jdbcTemplate.update(UPDATE_PSWD_BY_ID, namedParameters);

		/*
		 * if (rowsAffected == EXPECTED_ROW_NUMBER) { updated = true; } return
		 * updated;
		 */
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public User editProfile(User user) {
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(user);
		MapSqlParameterSource idNamedParameters = new MapSqlParameterSource(ID_PARAM, user.getId());
		this.jdbcTemplate.update(UPDATE_USER_BY_ID, namedParameters);

		return this.jdbcTemplate.queryForObject(SELECT_USER_BY_ID, idNamedParameters, new UserMapper());
	}

	@Override
	public void savePaswordResetToken(PasswordResetToken resetPassToken) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource().addValue(ID_PARAM, resetPassToken.getUser().getId()).addValue(RESET_PASS_PARAM, resetPassToken.getToken())
				.addValue(EXPIRE_DATE_PARAM, resetPassToken.getExpiryDate());
		this.jdbcTemplate.update(UPDATE_RESET_PASSWORD_TOKEN, namedParameters);

	}

	@Override
	public PasswordResetToken findPasswordResetToken(String token) {
		PasswordResetToken passwordResetToken = null;
		MapSqlParameterSource namedParameters = new MapSqlParameterSource(RESET_PASS_PARAM, token);

		try {
			passwordResetToken = this.jdbcTemplate.queryForObject(SELECT_RESET_TOKEN, namedParameters, new RowMapper<PasswordResetToken>() {

				@Override
				public PasswordResetToken mapRow(ResultSet rs, int rowNum) throws SQLException {
					PasswordResetToken token = new PasswordResetToken();
					User user = new User();
					user.setId(rs.getInt(USER_ID));
					user.setEmail(rs.getString(EMAIL));

					token.setUser(user);
					token.setToken(rs.getString(RESET_PASS_TOKEN));
					token.setExpiryDate(rs.getDate(TOKEN_EXPIRE_DATE));
					return token;

				}
			});
		} catch (EmptyResultDataAccessException e) {
			passwordResetToken = null;
		}
		return passwordResetToken;
	}

	@Override
	public User findByEmail(String email) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource().addValue(EMAIL_PARAM, email);
		User user = null;
		try {
			user = this.jdbcTemplate.queryForObject(SELECT_USER_BY_EMAIL, namedParameters, new UserPassMapper());
		} catch (EmptyResultDataAccessException e) {
			user = null;
		}
		return user;
	}

	@Override
	public User findById(long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource().addValue(ID_PARAM, id);
		User user = null;
		try {
			user = this.jdbcTemplate.queryForObject(SELECT_USER_BY_ID, namedParameters, new UserPassMapper());
		} catch (EmptyResultDataAccessException e) {
			user = null;
		}
		return user;
	}

	private static class UserMapper implements RowMapper<User> {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getInt(USER_ID));
			user.setEmail(rs.getString(EMAIL));
			user.setName(rs.getString(NAME));
			user.setSurname(rs.getString(SURNAME));
			Date birthday = rs.getDate(BIRTHDAY);
			if (birthday != null) {
				user.setBirthday(birthday.toLocalDate());
			}

			return user;
		}
	}

	private static class UserPassMapper implements RowMapper<User> {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getInt(USER_ID));
			user.setEmail(rs.getString(EMAIL));
			user.setName(rs.getString(NAME));
			user.setSurname(rs.getString(SURNAME));
			Date birthday = rs.getDate(BIRTHDAY);
			if (birthday != null) {
				user.setBirthday(birthday.toLocalDate());
			}
			user.setPassword(rs.getString(PASSWORD));

			return user;
		}
	}

}
