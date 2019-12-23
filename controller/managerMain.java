package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.CustomerVO;
import controller.CustomerDAO;
import model.DogVO;
import controller.DogDAO;
import model.HRVVO;
import controller.HRVDAO;
import model.SRVVO;
import controller.SRVDAO;

public class managerMain implements Initializable {
	@FXML
	private TableView<CustomerVO> customerView;
	@FXML
	private TableView<HRVVO> htrvtv;
	@FXML
	private TableView<SRVVO> slrvtv;
	@FXML
	private TableView<DogVO> dogView;
	@FXML
	private TextField txtCustomerSearch;
	@FXML
	private TextField txtCustomerSearch1;
	@FXML
	private TextField txtCustomerSearch2;
	@FXML
	private TextField txtCustomerSearch3;
	@FXML
	private TextField txtDogSearch;
	@FXML
	private TextField txtDogSearch1;
	@FXML
	private TextField txtDogSearch3;
	@FXML
	private DatePicker datCheckIn;
	@FXML
	private DatePicker datCheckIn1;
	@FXML
	private DatePicker datCheckOut;
	@FXML
	private ComboBox comRoomList;
	@FXML
	private ComboBox comTimeList;
	@FXML
	private ComboBox comAgeList;
	@FXML
	private Button btnCustomerSearch;
	@FXML
	private Button btnCustomerSearch1;
	@FXML
	private Button btnCustomerSearch2;
	@FXML
	private Button btnCustomerSearch3;
	@FXML
	private Button btnDogSearch;
	@FXML
	private Button btnDogSearch1;
	@FXML
	private Button btnDogSearch3;
	@FXML
	private Button btnRoomSearch;
	@FXML
	private Button btnAgeSearch;
	@FXML
	private Button btnCheckIn;
	@FXML
	private Button btnCheckOut;
	@FXML
	private Button btnTimeSearch;
	@FXML
	private Button btnExit1;
	@FXML
	private Button btnExit2;
	@FXML
	private Button btnExit3;
	@FXML
	private Button btnExit4;
	@FXML
	private Button btnExit5;
	@FXML
	private Button btnTotal1;
	@FXML
	private Button btnTotal2;
	@FXML
	private Button btnTotal3;
	@FXML
	private Button btnTotal4;
	@FXML
	private Button btndelete1;
	@FXML
	private Button btndelete2;
	@FXML
	private Button btndelete3;
	@FXML
	private Button btndelete4;
	ObservableList<HRVVO> data;
	ObservableList<SRVVO> data2;
	ObservableList<CustomerVO> data3;
	ObservableList<DogVO> data4;
	private ObservableList<HRVVO> selectHRVVO;
	private ObservableList<CustomerVO> selectCV;
	private ObservableList<SRVVO> selectSTVVO;
	private ObservableList<DogVO> selectDog;
	@FXML
	private Button btnTotalChart;
	@FXML
	private Button btnnHotelTotal;
	@FXML
	private Button btnSalonTotal;
	@FXML
	private PieChart pieChartView;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// ��ü ����Ʈ �����´�
		MakeHotelList();
		MakeSalonList();
		MakecustomerList();
		MakeDogList();
		// �޺��ڽ��� ����Ʈ ����
		hotelComListsetting("room", comRoomList);
		hotelComListsetting("age", comAgeList);
		salonComListsetting1("time", comTimeList);
		// �� �˻�
		btnCustomerSearch.setOnAction(event -> {
			handlerSearchAction(event, "id", txtCustomerSearch.getText());
		});
		btnCustomerSearch1.setOnAction(event -> {
			handlerSearchAction1(event, "id", txtCustomerSearch1.getText());
		});
		btnCustomerSearch2.setOnAction(event -> {
			handlerSearchAction2(event, "id", txtCustomerSearch2.getText());
		});
		btnCustomerSearch3.setOnAction(event -> {
			handlerSearchAction3(event, "id", txtCustomerSearch3.getText());
		});
		// �� �˻�
		btnDogSearch.setOnAction(event -> {
			handlerSearchAction(event, "dogname", txtDogSearch.getText());
		});
		btnDogSearch1.setOnAction(event -> {
			handlerSearchAction1(event, "dogname", txtDogSearch1.getText());
		});
		btnDogSearch3.setOnAction(event -> {
			handlerSearchAction3(event, "dogname", txtDogSearch3.getText());
		});
		// üũ�� �˻�
		datCheckIn.setOnAction(event -> {
			handlerSearchAction(event, "checkin", datCheckIn.getValue().toString());
		});
		datCheckIn1.setOnAction(event -> {
			handlerSearchAction1(event, "date", datCheckIn1.getValue().toString());
		});
		// üũ�ƿ� �˻�
		datCheckOut.setOnAction(event -> {
			handlerSearchAction(event, "checkout", datCheckOut.getValue().toString());
		});
		btnTimeSearch.setOnAction(event -> {
			handlerSearchAction1(event, "time", (String) comTimeList.getSelectionModel().getSelectedItem());
		});
		// �޺��ڽ��� ����Ʈ �˻�
		btnRoomSearch.setOnAction(event -> {
			handlerSearchAction(event, "room", (String) comRoomList.getSelectionModel().getSelectedItem());
		});
		btnAgeSearch.setOnAction(event -> {
			handlerSearchAction(event, "age", (String) comAgeList.getSelectionModel().getSelectedItem());
		});
		// �����ư �̺�Ʈ
		btnExit1.setOnAction(event -> {
			Exit();
		});
		btnExit2.setOnAction(event -> {
			Exit();
		});
		btnExit3.setOnAction(event -> {
			Exit();
		});
		btnExit4.setOnAction(event -> {
			Exit();
		});
		btnExit5.setOnAction(event -> {
			Exit();
		});
		// ��ü ����Ʈ ��ư (��ü����Ʈ�� �ʱ�ȭ)
		btnTotal1.setOnAction(event -> {
			data.removeAll(data);
			totalHotelList();
		});
		btnTotal2.setOnAction(event -> {
			data2.removeAll(data2);
			totalSalonList();
		});
		btnTotal3.setOnAction(event -> {
			data3.removeAll(data3);
			totalCustomerList();
		});
		btnTotal4.setOnAction(event -> {
			data4.removeAll(data4);
			totalDogList();
		});
		// ���̺�� ���� �̺�Ʈ
		btndelete1.setOnAction(event -> {
			handlerBtnDeleteAction();
		});
		btndelete2.setOnAction(event -> {
			handlerBtnDelete2Action();
		});
		btndelete3.setOnAction(event -> {
			handlerBtnDelete3Action();
			data3.removeAll(data3);
			totalCustomerList();
		});
		btndelete4.setOnAction(event -> {
			handlerBtnDelete4Action();
			data4.removeAll(data4);
			totalDogList();
		});
		btnTotalChart.setOnAction(event -> {
			getTotalPiechart();
		});
		btnnHotelTotal.setOnAction(event -> {
			getHotelPiechart();
		});
		btnSalonTotal.setOnAction(event->{
			getSalonPiechart();
		});
	}

	

	// ���̺�� ���� �̺�Ʈ hotel
	public void handlerBtnDeleteAction() {
		selectHRVVO = htrvtv.getSelectionModel().getSelectedItems();
		try {
			HRVDAO hrvDAO = new HRVDAO();
			int hrvDAONO = selectHRVVO.get(0).getNo();
			hrvDAO.getHRVDelete(hrvDAONO);
			data.removeAll(data);
			totalHotelList();
		} catch (Exception e) {
			DBUtil.alertDisplay(1, "���� ����", "��������", e.toString());
		}
	}

	// ���̺�� ���� �̺�Ʈ salon
	public void handlerBtnDelete2Action() {
		selectSTVVO = slrvtv.getSelectionModel().getSelectedItems();
		try {
			SRVDAO stvDAO = new SRVDAO();
			stvDAO.getSTRDelete(selectSTVVO.get(0).getNo());
			data2.removeAll(data2);
			totalSalonList();
		} catch (Exception e) {
			DBUtil.alertDisplay(1, "���� ����", "��������", e.toString());
		}
	}

	// ���̺�� ���� �̺�Ʈ customer
	public void handlerBtnDelete3Action() {
		selectCV = customerView.getSelectionModel().getSelectedItems();
		try {
			CustomerDAO cdao = new CustomerDAO();
			cdao.getCVODelete(selectCV.get(0).getId());
			data3.removeAll(data3);
			totalHotelList();
		} catch (Exception e) {
			DBUtil.alertDisplay(1, "���� ����", "��������", e.toString());
		}
	}

	// ���̺�� ���� �̺�Ʈ dog
	public void handlerBtnDelete4Action() {
		selectDog = dogView.getSelectionModel().getSelectedItems();
		try {
			DogDAO ddao = new DogDAO();
			selectDog = dogView.getSelectionModel().getSelectedItems();
			String selectDogId = selectDog.get(0).getId();
			String selectDogdogname = selectDog.get(0).getDogname();
			ddao.getDVODelete(selectDogId, selectDogdogname);
			data4.removeAll(data4);
			totalDogList();
		} catch (Exception e) {
			DBUtil.alertDisplay(1, "���� ����", "��������", e.toString());
		}
	}

	// 3. �ٿ뵵 �޺��ڽ� ���� ��� hotel
	public void hotelComListsetting(String coloumns, ComboBox comboBox) {
		HRVDAO hrvDAO = new HRVDAO();
		HashSet<String> hashSet = hrvDAO.getStringHashSet(coloumns);
		// ����Ʈ�� ��ȯ������ ������������ ����
		ArrayList<Integer> integerList = new ArrayList<Integer>();
		ArrayList<String> stringList = new ArrayList<String>(hashSet);
		// �������� ����
		Collections.sort(integerList);
		Collections.sort(stringList);
		// �޺� �ڽ��� ����
		comboBox.setItems(FXCollections.observableArrayList(stringList));
		
	}

	// salon
	public void salonComListsetting1(String coloumns, ComboBox comboBox1) {
		SRVDAO stvDAO = new SRVDAO();
		HashSet<String> hashSet = stvDAO.getStringHashSet(coloumns);
		// ����Ʈ�� ��ȯ������ ������������ ����
		ArrayList<Integer> integerList = new ArrayList<Integer>();
		ArrayList<String> stringList = new ArrayList<String>(hashSet);
		// �������� ����
		Collections.sort(integerList);
		Collections.sort(stringList);
		
		// �޺� �ڽ��� ����
		comboBox1.setItems(FXCollections.observableArrayList(stringList));
	}

	// 2. �ٿ뵵 �˻����HRVVO
	public void handlerSearchAction(Event event, String corumns, String search) {
		try {
			ArrayList<HRVVO> list = new ArrayList<HRVVO>();
			HRVDAO hrvDAO = new HRVDAO();
			list = hrvDAO.getHRVSearch(corumns, search);
			System.out.println("list.size=" + list.size());
			if (list == null) {
				throw new Exception("�˻�����");
			}
			data.removeAll(data);
			for (HRVVO hvo : list) {
				data.add(hvo);
			}
		} catch (Exception e1) {
			DBUtil.alertDisplay(1, "�˻����", "�̸��˻�����", e1.toString());
		}
	}

	// STVVO
	public void handlerSearchAction1(ActionEvent event, String corumns, String search) {
		try {
			ArrayList<SRVVO> list = new ArrayList<SRVVO>();
			SRVDAO stvDAO = new SRVDAO();
			list = stvDAO.getSTVSearch(corumns, search);
			System.out.println("list.size=" + list.size());
			if (list == null) {
				throw new Exception("�˻�����");
			}
			data2.removeAll(data2);
			for (SRVVO svo : list) {
				data2.add(svo);
			}
		} catch (Exception e1) {
			DBUtil.alertDisplay(1, "�˻����", "�̸��˻�����", e1.toString());
		}
	}

	// CustomerVO
	public void handlerSearchAction2(ActionEvent event, String corumns, String search) {
		try {
			ArrayList<CustomerVO> list = new ArrayList<CustomerVO>();
			CustomerDAO cdao = new CustomerDAO();
			list = cdao.getCustomerSearch(corumns, search);
			System.out.println("list.size=" + list.size());
			if (list == null) {
				throw new Exception("�˻�����");
			}
			data3.removeAll(data3);
			for (CustomerVO cvo : list) {
				data3.add(cvo);
			}
		} catch (Exception e1) {
			DBUtil.alertDisplay(1, "�˻����", "�̸��˻�����", e1.toString());
		}
	}

	// DogVO
	public void handlerSearchAction3(ActionEvent event, String corumns, String search) {
		try {
			ArrayList<DogVO> list = new ArrayList<DogVO>();
			DogDAO ddao = new DogDAO();
			list = ddao.getDogSearch(corumns, search);
			System.out.println("list.size=" + list.size());
			if (list == null) {
				throw new Exception("�˻�����");
			}
			data4.removeAll(data4);
			for (DogVO dvo : list) {
				data4.add(dvo);
			}
		} catch (Exception e1) {
			DBUtil.alertDisplay(1, "�˻����", "�̸��˻�����", e1.toString());
		}
	}

	// 1. (������ �α����ϴ� ����) ���̺���� �����Ͱ��� �о ���̺� �信 �����´�.(ȣ��)
	public void MakeHotelList() {

		ArrayList<HRVVO> list = null;
		HRVDAO hrvDAO = new HRVDAO();
		HRVVO hrvVO = null;
		data = FXCollections.observableArrayList();
		TableColumn colNo = new TableColumn("No");
		colNo.setMaxWidth(30);
		colNo.setStyle("-fx-alignment: CENTER;");
		colNo.setCellValueFactory(new PropertyValueFactory("no"));

		TableColumn colCheckin = new TableColumn("üũ��");
		colCheckin.setMaxWidth(100);
		colCheckin.setStyle("-fx-alignment: CENTER;");
		colCheckin.setCellValueFactory(new PropertyValueFactory("checkin"));

		TableColumn colCheckout = new TableColumn("üũ�ƿ�");
		colCheckout.setMaxWidth(100);
		colCheckout.setStyle("-fx-alignment: CENTER;");
		colCheckout.setCellValueFactory(new PropertyValueFactory("checkout"));

		TableColumn colRoom = new TableColumn("����");
		colRoom.setMaxWidth(50);
		colRoom.setStyle("-fx-alignment: CENTER;");
		colRoom.setCellValueFactory(new PropertyValueFactory("room"));

		TableColumn colName = new TableColumn("���� ���̵�");
		colName.setMaxWidth(80);
		colName.setStyle("-fx-alignment: CENTER;");
		colName.setCellValueFactory(new PropertyValueFactory("id"));

		TableColumn colDogname = new TableColumn("������ �̸�");
		colDogname.setMaxWidth(80);
		colDogname.setStyle("-fx-alignment: CENTER;");
		colDogname.setCellValueFactory(new PropertyValueFactory<>("dogname"));

		TableColumn colgender = new TableColumn("����");
		colgender.setMaxWidth(50);
		colgender.setStyle("-fx-alignment: CENTER;");
		colgender.setCellValueFactory(new PropertyValueFactory<>("gender"));

		TableColumn colAge = new TableColumn("����");
		colAge.setMaxWidth(50);
		colAge.setStyle("-fx-alignment: CENTER;");
		colAge.setCellValueFactory(new PropertyValueFactory<>("age"));

		TableColumn colNeuter = new TableColumn("�߼�ȭ");
		colNeuter.setMaxWidth(50);
		colNeuter.setStyle("-fx-alignment: CENTER;");
		colNeuter.setCellValueFactory(new PropertyValueFactory<>("neuter"));

		TableColumn colSpecial = new TableColumn("Ư�̻���");
		colSpecial.setMaxWidth(150);
		colSpecial.setStyle("-fx-alignment: CENTER;");
		colSpecial.setCellValueFactory(new PropertyValueFactory<>("special"));

		TableColumn colPrice = new TableColumn("����");
		colPrice.setMaxWidth(200);
		colPrice.setStyle("-fx-alignment: CENTER;");
		colPrice.setCellValueFactory(new PropertyValueFactory("price"));

		// ���̺� ����. �÷����� ��ü�� ���̺�信 ����Ʈ�߰� �� �׸� �߰�
		

		htrvtv.getColumns().addAll(colNo, colCheckin, colCheckout, colRoom, colName, colDogname, colgender, colAge,
				colNeuter, colSpecial);

		list = hrvDAO.getHRVTotal();
		if (list == null) {
			DBUtil.alertDisplay(1, "���", "DB�������� ����", "���� ���");
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			hrvVO = list.get(i);
			data.add(hrvVO);
		}
		htrvtv.setItems(data);
	}

	// (������ �α����ϴ� ����) ���̺���� �����Ͱ��� �о ���̺� �信 �����´�.(�̿�)
	public void MakeSalonList() {
		try {

			ArrayList<SRVVO> list = null;
			SRVDAO stvDAO = new SRVDAO();
			SRVVO stvVO = null;
			data2 = FXCollections.observableArrayList();

			TableColumn colNo = new TableColumn("No");
			colNo.setMaxWidth(30);
			colNo.setStyle("-fx-alignment: CENTER;");
			colNo.setCellValueFactory(new PropertyValueFactory("no"));

			TableColumn colCheckin = new TableColumn("��¥");
			colCheckin.setMaxWidth(100);
			colCheckin.setStyle("-fx-alignment: CENTER;");
			colCheckin.setCellValueFactory(new PropertyValueFactory("date"));

			TableColumn colTime = new TableColumn("�ð�");
			colTime.setMaxWidth(100);
			colTime.setStyle("-fx-alignment: CENTER;");
			colTime.setCellValueFactory(new PropertyValueFactory("time"));

			TableColumn colName = new TableColumn("���� ���̵�");
			colName.setMaxWidth(80);
			colName.setStyle("-fx-alignment: CENTER;");
			colName.setCellValueFactory(new PropertyValueFactory("id"));

			TableColumn colDogname = new TableColumn("������ �̸�");
			colDogname.setMaxWidth(80);
			colDogname.setStyle("-fx-alignment: CENTER;");
			colDogname.setCellValueFactory(new PropertyValueFactory<>("dogname"));

			TableColumn colBreed = new TableColumn("����");
			colBreed.setMaxWidth(100);
			colBreed.setStyle("-fx-alignment: CENTER;");
			colBreed.setCellValueFactory(new PropertyValueFactory<>("dogbreed"));

			TableColumn colgender = new TableColumn("����");
			colgender.setMaxWidth(50);
			colgender.setStyle("-fx-alignment: CENTER;");
			colgender.setCellValueFactory(new PropertyValueFactory<>("gender"));

			TableColumn colAge = new TableColumn("����");
			colAge.setMaxWidth(50);
			colAge.setStyle("-fx-alignment: CENTER;");
			colAge.setCellValueFactory(new PropertyValueFactory<>("age"));

			TableColumn colNeuter = new TableColumn("�߼�ȭ");
			colNeuter.setMaxWidth(50);
			colNeuter.setStyle("-fx-alignment: CENTER;");
			colNeuter.setCellValueFactory(new PropertyValueFactory<>("neuter"));

			TableColumn colSpecial = new TableColumn("Ư�̻���");
			colSpecial.setMaxWidth(200);
			colSpecial.setStyle("-fx-alignment: CENTER;");
			colSpecial.setCellValueFactory(new PropertyValueFactory<>("special"));

			TableColumn colCutStyle = new TableColumn("�ƽ�Ÿ��");
			colCutStyle.setMaxWidth(200);
			colCutStyle.setStyle("-fx-alignment: CENTER;");
			colCutStyle.setCellValueFactory(new PropertyValueFactory("cutstyle"));

			TableColumn colPrice = new TableColumn("����");
			colPrice.setMaxWidth(200);
			colPrice.setStyle("-fx-alignment: CENTER;");
			colPrice.setCellValueFactory(new PropertyValueFactory("price"));

			// ���̺� ����. �÷����� ��ü�� ���̺�信 ����Ʈ�߰� �� �׸� �߰�
			slrvtv.setItems(data2);

			slrvtv.getColumns().addAll(colNo, colCheckin, colTime, colName, colDogname, colBreed, colgender, colAge,
					colNeuter, colSpecial, colCutStyle);

			list = stvDAO.getSTVTotal();
			if (list == null) {
				DBUtil.alertDisplay(1, "���", "DB�������� ����", "���� ���");
				return;
			}
			for (int i = 0; i < list.size(); i++) {
				stvVO = list.get(i);
				data2.add(stvVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void MakecustomerList() {
		try {

			ArrayList<CustomerVO> list = null;
			CustomerDAO cdao = new CustomerDAO();
			CustomerVO cvo = null;
			data3 = FXCollections.observableArrayList();

			TableColumn colId = new TableColumn("���̵�");
			colId.setMaxWidth(100);
			colId.setStyle("-fx-alignment: CENTER;");
			colId.setCellValueFactory(new PropertyValueFactory("id"));

			TableColumn colPassword = new TableColumn("�н�����");
			colPassword.setMaxWidth(100);
			colPassword.setStyle("-fx-alignment: CENTER;");
			colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

			TableColumn colname = new TableColumn("�̸�");
			colname.setMaxWidth(50);
			colname.setStyle("-fx-alignment: CENTER;");
			colname.setCellValueFactory(new PropertyValueFactory<>("name"));

			TableColumn colPhone = new TableColumn("��ȭ��ȣ");
			colPhone.setMaxWidth(150);
			colPhone.setStyle("-fx-alignment: CENTER;");
			colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

			TableColumn colEmail = new TableColumn("�̸���");
			colEmail.setMaxWidth(250);
			colEmail.setStyle("-fx-alignment: CENTER;");
			colEmail.setCellValueFactory(new PropertyValueFactory("email"));

			// ���̺� ����. �÷����� ��ü�� ���̺�信 ����Ʈ�߰� �� �׸� �߰�
			customerView.setItems(data3);

			customerView.getColumns().addAll(colId, colPassword, colname, colPhone, colEmail);

			list = cdao.getCustomerTotal();
			if (list == null) {
				DBUtil.alertDisplay(1, "���", "DB�������� ����", "���� ���");
				return;
			}
			for (int i = 0; i < list.size(); i++) {
				cvo = list.get(i);
				data3.add(cvo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void MakeDogList() {
		ArrayList<DogVO> list = null;
		DogDAO dogDAO = new DogDAO();
		DogVO dvo = null;
		data4 = FXCollections.observableArrayList();

		TableColumn colNo = new TableColumn("No");
		colNo.setMaxWidth(30);
		colNo.setStyle("-fx-alignment: CENTER;");
		colNo.setCellValueFactory(new PropertyValueFactory("no"));

		TableColumn colId = new TableColumn("���̵�");
		colId.setMaxWidth(100);
		colId.setStyle("-fx-alignment: CENTER;");
		colId.setCellValueFactory(new PropertyValueFactory("id"));

		TableColumn colDogname = new TableColumn("�������̸�");
		colDogname.setMaxWidth(100);
		colDogname.setStyle("-fx-alignment: CENTER;");
		colDogname.setCellValueFactory(new PropertyValueFactory("dogname"));

		TableColumn colBreed = new TableColumn("����");
		colBreed.setMaxWidth(150);
		colBreed.setStyle("-fx-alignment: CENTER;");
		colBreed.setCellValueFactory(new PropertyValueFactory("breed"));

		TableColumn colGender = new TableColumn("����");
		colGender.setMaxWidth(50);
		colGender.setStyle("-fx-alignment: CENTER;");
		colGender.setCellValueFactory(new PropertyValueFactory("gender"));

		TableColumn colAge = new TableColumn("����");
		colAge.setMaxWidth(50);
		colAge.setStyle("-fx-alignment: CENTER;");
		colAge.setCellValueFactory(new PropertyValueFactory("age"));

		TableColumn colNeuter = new TableColumn("�߼�ȭ");
		colNeuter.setMaxWidth(50);
		colNeuter.setStyle("-fx-alignment: CENTER;");
		colNeuter.setCellValueFactory(new PropertyValueFactory("neuter"));

		TableColumn colSpecial = new TableColumn("Ư�̻���");
		colSpecial.setMaxWidth(200);
		colSpecial.setStyle("-fx-alignment: CENTER;");
		colSpecial.setCellValueFactory(new PropertyValueFactory("special"));

		TableColumn colLocalurl = new TableColumn("�̹���");
		colLocalurl.setMaxWidth(200);
		colLocalurl.setStyle("-fx-alignment: CENTER;");
		colLocalurl.setCellValueFactory(new PropertyValueFactory("localurl"));

		// ���̺� ����. �÷����� ��ü�� ���̺�信 ����Ʈ�߰� �� �׸� �߰�
		dogView.setItems(data4);

		dogView.getColumns().addAll(colNo, colId, colDogname, colBreed, colGender, colAge, colNeuter, colSpecial,
				colLocalurl);

		list = dogDAO.getDogTotal();
		if (list == null) {
			DBUtil.alertDisplay(1, "���", "DB�������� ����", "���� ���");
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			dvo = list.get(i);
			data4.add(dvo);
		}
	}

	// ����Ʈ �ҷ����� �Լ�
	public void totalHotelList() {

		ArrayList<HRVVO> list = null;
		HRVDAO hrvDAO = new HRVDAO();
		HRVVO hrvVO = null;
		list = hrvDAO.getHRVTotal();
		if (list == null) {
			DBUtil.alertDisplay(1, "���", "Database ���� ����", "���� �ٶ�");
		}
		for (int i = 0; i < list.size(); i++) {
			hrvVO = list.get(i);
			data.add(hrvVO);

		}
	}

	public void totalSalonList() {
		ArrayList<SRVVO> list = null;
		SRVDAO stvDAO = new SRVDAO();
		SRVVO stvVO = null;
		list = stvDAO.getSTVTotal();
		if (list == null) {
			DBUtil.alertDisplay(1, "���", "Database ���� ����", "���� �ٶ�");
		}
		for (int i = 0; i < list.size(); i++) {
			stvVO = list.get(i);
			data2.add(stvVO);
		}

	}

	public void totalCustomerList() {
		ArrayList<CustomerVO> list = null;
		CustomerDAO cdao = new CustomerDAO();
		CustomerVO cvo = null;
		list = cdao.getCustomerTotal();
		if (list == null) {
			DBUtil.alertDisplay(1, "���", "Database ���� ����", "���� �ٶ�");
		}
		for (int i = 0; i < list.size(); i++) {
			cvo = list.get(i);
			data3.add(cvo);
		}

	}

	public void totalDogList() {
		ArrayList<DogVO> list = null;
		DogDAO ddao = new DogDAO();
		DogVO dvo = null;
		list = ddao.getDogTotal();
		if (list == null) {
			DBUtil.alertDisplay(1, "���", "Database ���� ����", "���� �ٶ�");
		}
		for (int i = 0; i < list.size(); i++) {
			dvo = list.get(i);
			data4.add(dvo);
		}

	}

	public void handlerSearroomchAction(String corumns, String search) {
		try {
			ArrayList<HRVVO> list = new ArrayList<HRVVO>();
			HRVDAO hrvDAO = new HRVDAO();
			list = hrvDAO.getHRVSearch(corumns, search);
			System.out.println("list.size=" + list.size());
			if (list == null) {
				throw new Exception("�˻�����");
			}
			data.removeAll(data);
			for (HRVVO hvo : list) {
				data.add(hvo);
			}
		} catch (Exception e1) {
			DBUtil.alertDisplay(1, "�˻����", "�̸��˻�����", e1.toString());
		}
	}

	// �����̺�Ʈ
	public void Exit() {
		Platform.exit();
	}
	//���� ���� ��Ʈ
	public void getTotalPiechart() {
		ValueGET values = new ValueGET();
		int value1;
		int value2;
		int value3;
		int value4;
		int value5;
		int value6;
		int value7;
		int value8;
		int value9;
		try {
			value1 = values.firstfloor();
			value2 = values.secondfloor();
			value3 = values.thirdfloor();
			value4 = value1 + value2 + value3;
			value5 = values.cutstlye1();
			value6 = values.cutstlye2();
			value7 = values.cutstlye3();
			value8 = values.cutstlye4();
			value9 = value5 + value6 + value7 + value8;
			pieChartView.setData(FXCollections.observableArrayList(new PieChart.Data("hotel Total = ["+value4+"]", (double) (value4)),
					new PieChart.Data("Salon Total = ["+value9+"]", (double) (value9))));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//ȣ�� ����
	public void getHotelPiechart() {
		ValueGET values = new ValueGET();
		int value1;
		int value2;
		int value3;
		try {
			value1 = values.firstfloor();
			value2 = values.secondfloor();
			value3 = values.thirdfloor();
			pieChartView.setData(FXCollections.observableArrayList(new PieChart.Data("Hotel 1��=["+value1+"]",(double)(value1)),
					new PieChart.Data("Hotel 2�� = ["+value2+"]",(double)(value2)),
					new PieChart.Data("Hotel 3�� = ["+value3+"]", (double)(value3))));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//�̿� ��Ÿ�Ϻ�
	public void getSalonPiechart() {
		ValueGET values = new ValueGET();
		int value1;
		int value2;
		int value3;
		int value4;
		try {
			value1 = values.cutstlye1();
			value2 = values.cutstlye2();
			value3 = values.cutstlye3();
			value4 = values.cutstlye4();
			pieChartView.setData(FXCollections.observableArrayList(new PieChart.Data("��� =["+value1+"]", (double) (value1)),
					new PieChart.Data("Ŭ���� = ["+value2+"]", (double)(value2)),
					new PieChart.Data("������ = ["+value3+"]", (double)(value3)),
					new PieChart.Data("������ = ["+value4+"]", (double)(value4))));
		} catch (Exception e) {
		e.printStackTrace();
		}
	}
}