package controller;

import java.util.ArrayList;

import model.HRVVO;
import model.SRVVO;

public class ValueGET {
	public int firstfloor() throws Exception {
		ArrayList<HRVVO> list1 = new ArrayList<HRVVO>();
		ArrayList<HRVVO> list2 = new ArrayList<HRVVO>();
		ArrayList<HRVVO> list3 = new ArrayList<HRVVO>();
		HRVDAO hrvDAO = new HRVDAO();
		list1 = hrvDAO.getHRVSearch("room", "101");
		list2 = hrvDAO.getHRVSearch("room", "102");
		list3 = hrvDAO.getHRVSearch("room", "103");
		int room = list1.size() + list2.size() + list3.size();
		int value = room * 10000;
		return value;
	}

	public int secondfloor() throws Exception {
		ArrayList<HRVVO> list1 = new ArrayList<HRVVO>();
		ArrayList<HRVVO> list2 = new ArrayList<HRVVO>();
		ArrayList<HRVVO> list3 = new ArrayList<HRVVO>();
		HRVDAO hrvDAO = new HRVDAO();
		list1 = hrvDAO.getHRVSearch("room", "201");
		list2 = hrvDAO.getHRVSearch("room", "202");
		list3 = hrvDAO.getHRVSearch("room", "203");
		int room = list1.size() + list2.size() + list3.size();
		int value = room * 15000;
		return value;
	}

	public int thirdfloor() throws Exception {
		ArrayList<HRVVO> list1 = new ArrayList<HRVVO>();
		ArrayList<HRVVO> list2 = new ArrayList<HRVVO>();
		ArrayList<HRVVO> list3 = new ArrayList<HRVVO>();
		HRVDAO hrvDAO = new HRVDAO();
		list1 = hrvDAO.getHRVSearch("room", "301");
		list2 = hrvDAO.getHRVSearch("room", "302");
		list3 = hrvDAO.getHRVSearch("room", "303");
		int room = list1.size() + list2.size() + list3.size();
		int value = room * 20000;
		return value;
	}
	public int cutstlye1() throws Exception {
		ArrayList<SRVVO> list1 = new ArrayList<SRVVO>();
		ArrayList<SRVVO> list2 = new ArrayList<SRVVO>();
		ArrayList<SRVVO> list3 = new ArrayList<SRVVO>();
		SRVDAO stvDAO = new SRVDAO();
		list1 = stvDAO.getSTVSearch("cutstyle", "목욕");
		int room = list1.size();
		int value = room * 15000;
		return value;
	}
	public int cutstlye2() throws Exception {
		ArrayList<SRVVO> list1 = new ArrayList<SRVVO>();
		ArrayList<SRVVO> list2 = new ArrayList<SRVVO>();
		ArrayList<SRVVO> list3 = new ArrayList<SRVVO>();
		SRVDAO stvDAO = new SRVDAO();
		list1 = stvDAO.getSTVSearch("cutstyle", "클리핑");
		int room = list1.size();
		int value = room * 20000;
		return value;
	}
	public int cutstlye3() throws Exception {
		ArrayList<SRVVO> list1 = new ArrayList<SRVVO>();
		ArrayList<SRVVO> list2 = new ArrayList<SRVVO>();
		ArrayList<SRVVO> list3 = new ArrayList<SRVVO>();
		SRVDAO stvDAO = new SRVDAO();
		list1 = stvDAO.getSTVSearch("cutstyle", "스포팅");
		int room = list1.size();
		int value = room * 30000;
		return value;
	}
	public int cutstlye4() throws Exception {
		ArrayList<SRVVO> list1 = new ArrayList<SRVVO>();
		ArrayList<SRVVO> list2 = new ArrayList<SRVVO>();
		ArrayList<SRVVO> list3 = new ArrayList<SRVVO>();
		SRVDAO stvDAO = new SRVDAO();
		list1 = stvDAO.getSTVSearch("cutstyle", "가위컷");
		int room = list1.size();
		int value = room * 45000;
		return value;
	}
	
	
	
	
	
	
}
