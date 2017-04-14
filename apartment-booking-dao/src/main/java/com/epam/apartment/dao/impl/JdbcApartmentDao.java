package com.epam.apartment.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import com.epam.apartment.dao.ApartmentDao;
import com.epam.apartment.domain.Apartment;
import com.epam.apartment.domain.ApartmentCriteria;
import com.epam.apartment.domain.ApartmentType;
import com.epam.apartment.domain.Location;

@Repository
@Scope(value = "singleton")
public class JdbcApartmentDao implements ApartmentDao {

	private static final String SELECT_AVAILABLE_APARTMENTS = "SELECT AP_ID, AP_NAME, AP_PRICE, AP_MAX_GUEST_NUMBER, LO_COUNTRY, LO_CITY, LO_STREET, LO_BUILDING_NO, TY_TYPE, TY_DESCRIPTION "
			+ "FROM APARTMENTS JOIN LOCATIONS ON LOCATIONS.LO_ID = AP_LOCATION_ID JOIN TYPE_APARTMENTS ON TYPE_APARTMENTS.TY_ID = APARTMENTS.AP_TYPE_ID "
			+ "WHERE AP_ID NOT IN (SELECT BO_APARTMENT FROM BOOKINGS WHERE :p_ARRIVAL_DATE < BO_END AND :p_LEAVING_DATE > BO_START)";

	private static final String SELECT_APARTMETNS_BY_NAME = "SELECT AP_ID, AP_NAME, AP_PRICE, AP_MAX_GUEST_NUMBER, LO_COUNTRY, LO_CITY, LO_STREET, LO_BUILDING_NO, TY_TYPE, TY_DESCRIPTION "
			+ "FROM APARTMENTS JOIN LOCATIONS ON LOCATIONS.LO_ID=AP_LOCATION_ID JOIN TYPE_APARTMENTS ON TYPE_APARTMENTS.TY_ID = APARTMENTS.AP_TYPE_ID WHERE AP_NAME LIKE :p_NAME";

	private static final String SELECT_APARTMENTS_BY_CRITERIA = "SELECT AP_ID, AP_NAME, AP_PRICE, AP_MAX_GUEST_NUMBER, LO_COUNTRY, LO_CITY, LO_STREET, LO_BUILDING_NO, TY_TYPE, TY_DESCRIPTION "
			+ "FROM APARTMENTS JOIN LOCATIONS ON LOCATIONS.LO_ID = AP_LOCATION_ID JOIN TYPE_APARTMENTS ON TYPE_APARTMENTS.TY_ID = APARTMENTS.AP_TYPE_ID "
			+ "WHERE AP_ID NOT IN (SELECT BO_APARTMENT FROM BOOKINGS WHERE (:arrivalDate IS NULL OR :arrivalDate < BO_END) AND (:leavingDate IS NULL OR :leavingDate > BO_START)) "
			+ "AND (:country IS NULL OR :country = LO_COUNTRY) AND (:city IS NULL OR :city = LO_CITY) AND (:guestNumber IS NULL OR :guestNumber = AP_MAX_GUEST_NUMBER) AND (:maxPrice IS NULL OR :maxPrice <= AP_PRICE)";

	private static final String COUNTRY = "LO_COUNTRY";
	private static final String CITY = "LO_CITY";
	private static final String STREET = "LO_STREET";
	private static final String BUILDING = "LO_BUILDING_NO";
	private static final String APARTMENT_TYPE = "TY_TYPE";
	private static final String TYPE_DESCRIPTION = "TY_DESCRIPTION";
	private static final String AP_ID = "AP_ID";
	private static final String NAME = "AP_NAME";
	private static final String PRICE = "AP_PRICE";
	private static final String MAX_GUEST_NUMBER = "AP_MAX_GUEST_NUMBER";

	private static final String ARRIVAL_PARAM = "p_ARRIVAL_DATE";
	private static final String LEAVING_PARAM = "p_LEAVING_DATE";
	private static final String NAME_PARAM = "p_NAME";

	private static final String PERCENT = "%";

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<Apartment> findAvailableApartments(LocalDate arrivalDate, LocalDate leavingDate) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(ARRIVAL_PARAM, arrivalDate)
				.addValue(LEAVING_PARAM, leavingDate);
		return this.jdbcTemplate.query(SELECT_AVAILABLE_APARTMENTS, namedParameters, new ApatrmentMapper());
	}

	public List<Apartment> findConcreteApartment(String name) {
		String namePattern = PERCENT.concat(name).concat(PERCENT);
		SqlParameterSource namedParameters = new MapSqlParameterSource(NAME_PARAM, namePattern);
		return this.jdbcTemplate.query(SELECT_APARTMETNS_BY_NAME, namedParameters, new ApatrmentMapper());
	}

	public List<Apartment> findAvailableApartmentByCriteria(ApartmentCriteria criteria) {
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(criteria);
		return this.jdbcTemplate.query(SELECT_APARTMENTS_BY_CRITERIA, namedParameters, new ApatrmentMapper());
	}

	private static class ApatrmentMapper implements RowMapper<Apartment> {
		public Apartment mapRow(ResultSet rs, int rowNum) throws SQLException {
			Apartment apartment = new Apartment();
			Location location = new Location();
			location.setCountry(rs.getString(COUNTRY));
			location.setCity(rs.getString(CITY));
			location.setStreet(rs.getString(STREET));
			location.setBuildingNo(rs.getString(BUILDING));
			apartment.setLocation(location);
			ApartmentType type = new ApartmentType();
			type.setType(rs.getString(APARTMENT_TYPE));
			type.setDescription(rs.getString(TYPE_DESCRIPTION));
			apartment.setType(type);
			apartment.setId(rs.getInt(AP_ID));
			apartment.setName(rs.getString(NAME));
			apartment.setPrice(rs.getBigDecimal(PRICE));
			apartment.setMaxGuestNumber(rs.getInt(MAX_GUEST_NUMBER));
			return apartment;
		}
	}
}
