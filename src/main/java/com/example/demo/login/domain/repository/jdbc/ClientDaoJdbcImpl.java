package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.ClientDTO;
import com.example.demo.login.domain.repository.ClientDao;

@Repository
public class ClientDaoJdbcImpl implements ClientDao {

	@Autowired
	JdbcTemplate jdbc;

	public int count() {
		int count = jdbc.queryForObject("select count(*)from client", Integer.class);

		return count;
	}

	public int insertOne(ClientDTO clientdto) {
		int rowNumber = jdbc.update("insert into client(id,"
				+ " user_name,"
				+ " mailaddress,"
				+ " address,"
				+ " telephone,"
				+ " registration_date,"
				+ " company)"
				+ " value(?,?,?,?,?,?,?)", clientdto.getId(), clientdto.getUser_name(), clientdto.getMailaddress(),
				clientdto.getAddress(), clientdto.getTelephone(), clientdto.getRegistration_date(),
				clientdto.getCompany());

		return rowNumber;
	}

	public int deleteOne(int id) {
		int rowNumber = jdbc.update("delete from client where id = ?", id);

		return rowNumber;
	}

	public int updateOne(ClientDTO clientdto) {
		int rowNumber = jdbc.update("update client"
				+ " set"
				+ " user_name = ?,"
				+ " telephone = ?,"
				+ " mailaddress = ?"
				+ " where id = ?", clientdto.getUser_name(), clientdto.getTelephone(), clientdto.getMailaddress(),
				clientdto.getId());

		return rowNumber;
	}

	public ClientDTO selectOne(int id) {
		Map<String, Object> map = jdbc.queryForMap("select * from client where id = ?", id);

		ClientDTO clientdto = new ClientDTO();
		clientdto.setId((int) map.get("id"));
		clientdto.setMailaddress((String) map.get("mailaddress"));
		clientdto.setAddress((String) map.get("address"));
		clientdto.setRegistration_date((Date) map.get("registration_date"));
		clientdto.setCompany((String) map.get("company"));
		clientdto.setTelephone((String) map.get("telephone"));
		clientdto.setUser_name((String) map.get("user_name"));

		return clientdto;
	}

	public List<ClientDTO> selectMany() {
		List<Map<String, Object>> clientList = jdbc.queryForList("select * from client");
		List<ClientDTO> list = new ArrayList<>();
		for (Map<String, Object> map : clientList) {
			ClientDTO clientdto = new ClientDTO();
			clientdto.setId((int) map.get("id"));
			clientdto.setMailaddress((String) map.get("mailaddress"));
			clientdto.setAddress((String) map.get("address"));
			clientdto.setRegistration_date((Date) map.get("registration_date"));
			clientdto.setCompany((String) map.get("company"));
			clientdto.setTelephone((String) map.get("telephone"));
			clientdto.setUser_name((String) map.get("user_name"));

			list.add(clientdto);
		}
		return list;
	}

	public List<ClientDTO> search(String company, String address, String user_name, Date registration_dateFrom,
			Date registration_dateTo, String telephone, String mailaddress) {
		StringBuilder sql = new StringBuilder();
		List<Object> list = new ArrayList<Object>();

		sql.append("select * from client where is_deleted = 0");

		if ((company != null) && (!company.isEmpty())) {
			sql.append(" and company like ?");
			list.add("%" + company + "%");
		}

		if ((address != null) && (!address.isEmpty())) {
			sql.append(" and address like ?");
			list.add("%" + address + "%");
		}

		if ((user_name != null) && (!user_name.isEmpty())) {
			sql.append(" and user_name like ?");
			list.add("%" + user_name + "%");
		}

		if ((registration_dateFrom != null) && (registration_dateTo != null)) {
			sql.append(" and registration_date BETWEEN ? AND ?");
			list.add(registration_dateFrom);
			list.add(registration_dateTo);
		} else if ((registration_dateFrom != null) && (registration_dateTo == null)) {
			sql.append(" and registration_date >= ?");
			list.add(registration_dateFrom);
		} else if ((registration_dateFrom == null) && (registration_dateTo != null)) {
			sql.append(" and registration_date <= ?");
			list.add(registration_dateTo);
		}

		if ((telephone != null) && (!telephone.isEmpty())) {
			sql.append(" and telephone like ?");
			list.add("%" + telephone + "%");
		}

		if ((mailaddress != null) && (!mailaddress.isEmpty())) {

			sql.append(" and mailaddress like ?");
			list.add("%" + mailaddress + "%");
		}

		System.out.println("sql   " + sql);
		System.out.println("list  " + list);
		String getSql = sql.toString();
		System.out.println("getSql  " + getSql);
		Object[] getList = list.toArray(new Object[list.size()]);
		System.out.println("getList  " + getList);
		List<Map<String, Object>> searchList = jdbc.queryForList(getSql, getList);
		System.out.println("searchList  " + searchList);
		List<ClientDTO> clientList = new ArrayList<>();
		for (Map<String, Object> map : searchList) {
			ClientDTO clientdto = new ClientDTO();
			clientdto.setId((int) map.get("id"));
			clientdto.setCompany((String) map.get("company"));
			clientdto.setAddress((String) map.get("address"));
			clientdto.setUser_name((String) map.get("user_name"));
			clientdto.setRegistration_date((Date) map.get("registration_date"));
			clientdto.setTelephone((String) map.get("telephone"));
			clientdto.setMailaddress((String) map.get("mailaddress"));

			clientList.add(clientdto);
		}
		return clientList;

	}

	public void clientCsvOut() {
		String sql = "select * from client";
		ClientRowCallbackHandler handler = new ClientRowCallbackHandler();

		jdbc.query(sql, handler);

	}
}
