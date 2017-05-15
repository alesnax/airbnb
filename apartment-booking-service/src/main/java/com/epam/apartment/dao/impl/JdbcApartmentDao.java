package com.epam.apartment.dao.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.epam.apartment.dao.ApartmentDao;
import com.epam.apartment.model.Apartment;
import com.epam.apartment.model.ApartmentCriteria;
import com.epam.apartment.model.ApartmentType;
import com.epam.apartment.model.Location;

@Repository
public class JdbcApartmentDao implements ApartmentDao {

	private static final String SELECT_AVAILABLE_APARTMENTS = "SELECT AP_ID, AP_NAME, AP_PRICE, AP_MAX_GUEST_NUMBER, LO_ID, LO_COUNTRY, LO_CITY, LO_STREET, LO_BUILDING_NO, TY_TYPE, TY_DESCRIPTION "
			+ "FROM APARTMENTS JOIN LOCATIONS ON LOCATIONS.LO_ID = AP_LOCATION_ID JOIN TYPE_APARTMENTS ON TYPE_APARTMENTS.TY_ID = APARTMENTS.AP_TYPE_ID "
			+ "WHERE AP_ID NOT IN (SELECT BO_APARTMENT FROM BOOKINGS WHERE :p_ARRIVAL_DATE < BO_END AND :p_LEAVING_DATE > BO_START)";

	private static final String SELECT_APARTMETNS_BY_NAME = "SELECT AP_ID, AP_NAME, AP_PRICE, AP_MAX_GUEST_NUMBER, LO_ID, LO_COUNTRY, LO_CITY, LO_STREET, LO_BUILDING_NO, TY_TYPE, TY_DESCRIPTION "
			+ "FROM APARTMENTS JOIN LOCATIONS ON LOCATIONS.LO_ID=AP_LOCATION_ID JOIN TYPE_APARTMENTS ON TYPE_APARTMENTS.TY_ID = APARTMENTS.AP_TYPE_ID WHERE AP_NAME LIKE :p_NAME";

	private static final String SELECT_APARTMENTS_BY_LOCATION = "SELECT AP_ID, AP_NAME, AP_PRICE, AP_MAX_GUEST_NUMBER, LO_ID, LO_COUNTRY, LO_CITY, LO_STREET, LO_BUILDING_NO, TY_TYPE, TY_DESCRIPTION "
			+ "FROM APARTMENTS JOIN LOCATIONS ON LOCATIONS.LO_ID=AP_LOCATION_ID JOIN TYPE_APARTMENTS ON TYPE_APARTMENTS.TY_ID = APARTMENTS.AP_TYPE_ID WHERE LO_CITY = :p_CITY";

	private static final String SELECT_APARTMENTS_BY_CRITERIA = "SELECT AP_ID, AP_NAME, AP_PRICE, AP_MAX_GUEST_NUMBER, LO_ID, LO_COUNTRY, LO_CITY, LO_STREET, LO_BUILDING_NO, TY_TYPE, TY_DESCRIPTION "
			+ "FROM APARTMENTS JOIN LOCATIONS ON LOCATIONS.LO_ID = AP_LOCATION_ID JOIN TYPE_APARTMENTS ON TYPE_APARTMENTS.TY_ID = APARTMENTS.AP_TYPE_ID "
			+ "WHERE AP_ID NOT IN (SELECT BO_APARTMENT FROM BOOKINGS WHERE (:p_ARRIVAL_DATE IS NULL OR :p_ARRIVAL_DATE < BO_END) AND (:p_LEAVING_DATE IS NULL OR :p_LEAVING_DATE > BO_START)) "
			+ "AND (:p_COUNTRY IS NULL OR :p_COUNTRY = LO_COUNTRY) AND (:p_CITY IS NULL OR :p_CITY = LO_CITY) AND (:p_GUEST_NUMBER IS NULL OR :p_GUEST_NUMBER = AP_MAX_GUEST_NUMBER) AND (:p_MAX_PRICE IS NULL OR :p_MAX_PRICE <= AP_PRICE)";

	private static final String SELECT_APARTMENT_BY_ID = "SELECT AP_ID, AP_NAME, AP_PRICE, AP_MAX_GUEST_NUMBER, LO_ID, LO_COUNTRY, LO_CITY, LO_STREET, LO_BUILDING_NO, TY_TYPE, TY_DESCRIPTION "
			+ "FROM APARTMENTS JOIN LOCATIONS ON LOCATIONS.LO_ID=AP_LOCATION_ID JOIN TYPE_APARTMENTS ON TYPE_APARTMENTS.TY_ID = APARTMENTS.AP_TYPE_ID WHERE :p_ID = AP_ID";

	private static final String COUNTRY = "LO_COUNTRY";
	private static final String CITY = "LO_CITY";
	private static final String STREET = "LO_STREET";
	private static final String BUILDING = "LO_BUILDING_NO";
	private static final String APARTMENT_TYPE = "TY_TYPE";
	private static final String TYPE_DESCRIPTION = "TY_DESCRIPTION";
	private static final String AP_ID = "AP_ID";
	private static final String LOCATION_ID = "LO_ID";
	private static final String NAME = "AP_NAME";
	private static final String PRICE = "AP_PRICE";
	private static final String MAX_GUEST_NUMBER = "AP_MAX_GUEST_NUMBER";

	private static final String ID_PARAM = "p_ID";
	private static final String LOCATION_ID_PARAM = "p_LO_ID";
	private static final String ARRIVAL_PARAM = "p_ARRIVAL_DATE";
	private static final String LEAVING_PARAM = "p_LEAVING_DATE";
	private static final String NAME_PARAM = "p_NAME";
	private static final String COUNTRY_PARAM = "p_COUNTRY";
	private static final String CITY_PARAM = "p_CITY";
	private static final String GUEST_NUMBER_PARAM = "p_GUEST_NUMBER";
	private static final String MAX_PRICE_PARAM = "p_MAX_PRICE";

	private static final String PERCENT = "%";

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public List<Apartment> findAvailableApartments(LocalDate arrivalDate, LocalDate leavingDate) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(ARRIVAL_PARAM, Date.valueOf(arrivalDate)).addValue(LEAVING_PARAM, Date.valueOf(leavingDate));
		return this.jdbcTemplate.query(SELECT_AVAILABLE_APARTMENTS, namedParameters, new ApatrmentMapper());
	}

	@Override
	public List<Apartment> findAvailableApartmentByLocationId(String city) {
		SqlParameterSource namedParameters = new MapSqlParameterSource(CITY_PARAM, city);
		return this.jdbcTemplate.query(SELECT_APARTMENTS_BY_LOCATION, namedParameters, new ApatrmentMapper());
	}

	@Override
	public List<Apartment> findConcreteApartment(String name) {
		String namePattern = PERCENT.concat(name).concat(PERCENT);
		SqlParameterSource namedParameters = new MapSqlParameterSource(NAME_PARAM, namePattern);
		return this.jdbcTemplate.query(SELECT_APARTMETNS_BY_NAME, namedParameters, new ApatrmentMapper());
	}

	@Override
	public List<Apartment> findAvailableApartmentByCriteria(ApartmentCriteria criteria) {
		Date arrivalDate = criteria.getArrivalDate() != null ? Date.valueOf(criteria.getArrivalDate()) : null;
		Date leavingDate = criteria.getLeavingDate() != null ? Date.valueOf(criteria.getLeavingDate()) : null;
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(COUNTRY_PARAM, criteria.getCountry()).addValue(CITY_PARAM, criteria.getCity())
				.addValue(GUEST_NUMBER_PARAM, criteria.getGuestNumber()).addValue(MAX_PRICE_PARAM, criteria.getMaxPrice()).addValue(ARRIVAL_PARAM, arrivalDate).addValue(LEAVING_PARAM, leavingDate);

		return this.jdbcTemplate.query(SELECT_APARTMENTS_BY_CRITERIA, namedParameters, new ApatrmentMapper());
	}

	@Override
	public Apartment findApartmentById(int id) {
		SqlParameterSource namedParameters = new MapSqlParameterSource(ID_PARAM, new Integer(id));
		Apartment apartment = null;
		List<Apartment> apartments = this.jdbcTemplate.query(SELECT_APARTMENT_BY_ID, namedParameters, new ApatrmentMapper());
		if (!apartments.isEmpty()) {
			apartment = apartments.get(0);
		}
		return apartment;
	}

	private static class ApatrmentMapper implements RowMapper<Apartment> {
		public Apartment mapRow(ResultSet rs, int rowNum) throws SQLException {
			Apartment apartment = new Apartment();
			Location location = new Location();
			location.setId(rs.getInt(LOCATION_ID));
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
