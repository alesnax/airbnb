package com.epam.apartment.dao.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
import com.epam.apartment.dto.EditedUserDto;
import com.epam.apartment.dto.UserDto;
import com.epam.apartment.model.User;

@Repository("userDao")
@Scope(value = "singleton")
public class JdbcUserDao implements UserDao {

	private static final String INSERT_USER_SQL = "INSERT INTO USERS (U_EMAIL, U_NAME, U_SURNAME, U_PASSWORD, U_BIRTHDAY) VALUES (:p_EMAIL, :p_NAME, :p_SURNAME, :p_PSWD, :p_BIRTHDAY)";
	private static final String SELECT_USER_BY_PSWD = "SELECT U_ID, U_EMAIL, U_NAME, U_SURNAME, U_BIRTHDAY FROM USERS WHERE U_EMAIL = :p_EMAIL AND U_PASSWORD = :p_PSWD";
	private static final String SELECT_USER_BY_EMAIL = "SELECT U_ID, U_EMAIL, U_NAME, U_SURNAME, U_BIRTHDAY FROM USERS WHERE U_EMAIL = :p_EMAIL";
	private static final String UPDATE_PASSWORD_BY_ID = "UPDATE USERS SET U_PASSWORD = :p_NEW_PSWD WHERE U_ID = :p_ID AND U_PASSWORD = :p_OLD_PSWD";
	private static final String UPDATE_USER_BY_ID = "UPDATE USERS SET U_EMAIL = :email, U_NAME = :name, U_SURNAME = :surname WHERE U_ID = :id";
	private static final String SELECT_USER_BY_ID = "SELECT U_ID, U_EMAIL, U_NAME, U_SURNAME, U_BIRTHDAY FROM USERS WHERE U_ID = :p_ID";
	private static final String UPDATE_PSWD_BY_EMAIL = "UPDATE USERS SET U_PASSWORD = :p_EMAIL WHERE U_EMAIL = :p_EMAIL";

	private static final int EXPECTED_ROW_NUMBER = 1;

	private static final String USER_ID = "U_ID";
	private static final String EMAIL = "U_EMAIL";
	private static final String NAME = "U_NAME";
	private static final String SURNAME = "U_SURNAME";
	private static final String BIRTHDAY = "U_BIRTHDAY";

	private static final String EMAIL_PARAM = "p_EMAIL";
	private static final String PSWD_PARAM = "p_PSWD";
	private static final String NAME_PARAM = "p_NAME";
	private static final String SURNAME_PARAM = "p_SURNAME";
	private static final String BIRTHDAY_PARAM = "p_BIRTHDAY";
	private static final String ID_PARAM = "p_ID";
	private static final String OLD_PSWD_PARAM = "p_OLD_PSWD";
	private static final String NEW_PSWD_PARAM = "p_NEW_PSWD";

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public User authoriseUser(String email, String pswd) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource().addValue(EMAIL_PARAM, email).addValue(PSWD_PARAM, pswd);
		User user = null;
		try {
			user = this.jdbcTemplate.queryForObject(SELECT_USER_BY_PSWD, namedParameters, new UserMapper());
		} catch (EmptyResultDataAccessException e) {
			user = null;
		}
		return user;
	}

	public User registerNewUser(UserDto accountDto) {
		Date bithday = accountDto.getBirthday() != null ? Date.valueOf(accountDto.getBirthday()) : null;

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue(EMAIL_PARAM, accountDto.getEmail()).addValue(NAME_PARAM, accountDto.getName()).addValue(SURNAME_PARAM, accountDto.getSurname())
				.addValue(PSWD_PARAM, accountDto.getPassword()).addValue(BIRTHDAY_PARAM, bithday);

		this.jdbcTemplate.update(INSERT_USER_SQL, namedParameters);
		return authoriseUser(accountDto.getEmail(), accountDto.getPassword());

	}

	public boolean changePswd(int id, String oldPswd, String newPswd) {
		boolean updated = false;
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(ID_PARAM, id).addValue(OLD_PSWD_PARAM, oldPswd).addValue(NEW_PSWD_PARAM, newPswd);

		int rowsAffected = this.jdbcTemplate.update(UPDATE_PASSWORD_BY_ID, namedParameters);
		if (rowsAffected == EXPECTED_ROW_NUMBER) {
			updated = true;
		}
		return updated;
	}

	public boolean restorePswd(String email, String newPswd) {
		boolean updated = false;
		MapSqlParameterSource namedParameters = new MapSqlParameterSource().addValue(EMAIL_PARAM, email).addValue(PSWD_PARAM, newPswd);
		int rowsAffected = this.jdbcTemplate.update(UPDATE_PSWD_BY_EMAIL, namedParameters);
		if (rowsAffected == EXPECTED_ROW_NUMBER) {
			updated = true;
		}
		return updated;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public User editProfile(EditedUserDto editedUser) {
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(editedUser);
		MapSqlParameterSource idNamedParameters = new MapSqlParameterSource(ID_PARAM, editedUser.getId());
		this.jdbcTemplate.update(UPDATE_USER_BY_ID, namedParameters);

		return this.jdbcTemplate.queryForObject(SELECT_USER_BY_ID, idNamedParameters, new UserMapper());
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

	@Override
	public User findByEmail(String email) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource().addValue(EMAIL_PARAM, email);
		User user = null;
		try {
			user = this.jdbcTemplate.queryForObject(SELECT_USER_BY_EMAIL, namedParameters, new UserMapper());
		} catch (EmptyResultDataAccessException e) {
			user = null;
		}
		return user;
	}

}
