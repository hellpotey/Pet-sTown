package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.DogVO;
import model.HRLVO;
import model.HRVVO;
import model.LoginCustomerVO;
import model.SRVVO;
import model.STLVO;

public class hrvController implements Initializable {
	@FXML
	private Button hbtnok;
	@FXML
	private Button sbtncancel;
	@FXML
	private Button hbtncancel;
	@FXML
	private Button sbtnok;
	@FXML
	private Button hbtnconfirm;
	@FXML
	private Button sbtnconfirm;
	@FXML
	private DatePicker hdpcheckin;
	@FXML
	private DatePicker hdpcheckout;
	@FXML
	private DatePicker sdpdate;
	@FXML
	private TableView htv;
	@FXML
	private TableView stv;
	@FXML
	private ComboBox scbstyle;
	@FXML
	private Button hbtntotal;
	@FXML
	private Button sbtntotal;
	@FXML
	private Button hbtndateserch;

	private ObservableList<HRLVO> hoteldata = FXCollections.observableArrayList();
	private ObservableList<HRLVO> totalHoteldata = FXCollections.observableArrayList();
	private ObservableList<STLVO> salondata = FXCollections.observableArrayList();
	private ObservableList<STLVO> totalSalondata = FXCollections.observableArrayList();
	
	//������ �ִ¿�
	ObservableList data = FXCollections.observableArrayList();
	
	//���̺� �信 ������ ���� ���ϱ� ����
	ArrayList<STLVO> checkSalonList = new ArrayList<STLVO>();
	ArrayList<HRLVO> checkHotelList = new ArrayList<HRLVO>();
	
	private HRVDAO hrvDAO = new HRVDAO();
	private HRLDAO hrlDAO = new HRLDAO();
	private SRVDAO srvDAO = new SRVDAO();
	private STLDAO stlDAO = new STLDAO();
	// �α����� ������ , ����������Ʈ �������� ����
	private DogDAO dogDAO = new DogDAO();
	private LoginCustomerDAO loginCustomerDAO = new LoginCustomerDAO();
	private LoginCustomerVO selectCustomer = loginCustomerDAO.getLoginCustomer();
	private ArrayList<DogVO> dogList = dogDAO.getLoginCustomerDogList(selectCustomer.getId());
	//
	// �α����� �� ����
	private loginDogDAO loginDogDAO = new loginDogDAO();
	private DogVO loginDog = loginDogDAO.getLoginDog();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		////////////////////////// ȣ��/////////////////////////////////////////////
		// ȣ�� ���డ�� ����Ʈ �����´�
		hrltotalListLoder();
		// ȣ�� ���డ�� ����Ʈ �����Ѵ�.
		hrltotalListSetting();
		// ȣ�� üũ�ΰ˻�
		hdpcheckin.setOnAction(event -> {
			handlerSearchAction(event, "checkin", hdpcheckin.getValue().toString());
		});
		// '' �ƿ�
		hdpcheckout.setOnAction(event -> {
			handlerSearchAction(event, "checkout", hdpcheckout.getValue().toString());
		});
		// '' ����
		hbtndateserch.setOnAction(event -> {
			handlerbtndateserchAction(event, "checkin", "checkout", hdpcheckin.getValue().toString(),
					hdpcheckout.getValue().toString());
		});
		
		// ȣ�� ��� ��ư
		hbtncancel.setOnAction(event -> {
			((Stage) hbtncancel.getScene().getWindow()).close();
			callmydog();
		});

		// ȣ�� �����ư
		hbtnok.setOnAction(event -> {
			handlerhbtnokAction(event);
		});
		// ȣ�� ���������ư
		hbtnconfirm.setOnAction(event -> {
			handlerhbtnconfirmAction(event);
		});
		// ȣ�� ��ü ����Ʈ ��ư
		hbtntotal.setOnAction(event -> {
			hrltotalListSetting();
		});
////////////////////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////// �̿�/////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////
		// �̿� ���డ�� ����Ʈ �����´�
		stltotalListLoder();
		// �̿� ���డ�� ����Ʈ �����Ѵ�.
		stltotalListSetting();
		// �̿� ����Ʈ ��Ŀ �˻�
		sdpdate.setOnAction(event -> {
			 System.out.println("sdpdate.getValue().toString()=             "+sdpdate.getValue().toString());
			 handlerSalonSearchAction(event, "date", sdpdate.getValue().toString());
		});
		// �� ��Ÿ�� üũ�ڽ� ����
		cbstyleSetting();
		// �̿� �����ư
		sbtnok.setOnAction(event -> {
			handlersbtnokAction(event);
		});
		// �̿� ����Ȯ�ι�ư
		sbtnconfirm.setOnAction(event -> {
			handlersbtnconfirmAction(event);
		});
		// �̿� ��ü ����Ʈ ��ư
		sbtntotal.setOnAction(event -> {
			stltotalListSetting();
		});
		// ȣ�� ��� ��ư
		sbtncancel.setOnAction(event -> {
			((Stage) sbtncancel.getScene().getWindow()).close();
			callmydog();
		});
	}



/////////////////////////////////////////////////////////////////////
/////////////////////// �̿�////////////////////////////
/////////////////////////////////////////////////////////////////////
	
	// �̿뿹��Ȯ�� ���d
	private void handlersbtnconfirmAction(ActionEvent event) {
		Parent parent = null;
		Stage stage = null;
		try {
			parent = FXMLLoader.load(getClass().getResource("/view/deleteSRV.fxml"));
			Scene scene = new Scene(parent);
			stage = new Stage();

			stage.setTitle("deleteSRV Window");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (IOException e11) {
			System.out.println("deleteSRV View call error " + e11);
		}	
	}
	
	// ��Ÿ�� üũ�ڽ� ����
	private void cbstyleSetting() {
		scbstyle.setItems(FXCollections.observableArrayList("���", "Ŭ����", "������", "������"));
	}
	
	// ��ü �̿� ����Ʈ ����
	private void stltotalListSetting() {
		//
		ArrayList<STLVO> list = null;
		STLDAO stlDAO = new STLDAO();
		STLVO stlVO = null;
		totalSalondata.removeAll(totalSalondata);
		list = stlDAO.getSTLTotal();
		if (list == null) {
			DBUtil.alertDisplay(1, "���", "DB�������� ����", "���� ���");
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			stlVO = list.get(i);
			totalSalondata.add(stlVO);
		}
		stv.setItems(totalSalondata);
	}

	// ��� �ٿ뵵 �˻����
	public void handlerSalonSearchAction(Event event, String corumns, String search) {
		try {
			ArrayList<STLVO> list = new ArrayList<STLVO>();
			STLDAO stlDAO = new STLDAO();
			list = stlDAO.getSTLSearch(corumns, search);
			System.out.println("list.size=" + list.size());
			if (list == null) {
				throw new Exception("�˻�����");
			}
			salondata.removeAll(salondata);
			for (STLVO stl : list) {
				salondata.add(stl);
			}
		} catch (Exception e1) {
			DBUtil.alertDisplay(1, "�˻����", "�̸��˻�����", e1.toString());
		}
		stv.setItems(salondata);
	}
	
	// �̿� Ȯ�ι�ư
	private void handlersbtnokAction(ActionEvent event) {
		STLVO selectSTL = (STLVO) stv.getSelectionModel().getSelectedItem();
		System.out.println(selectSTL);
		if (selectSTL.getReservation().equals("����Ұ�")) {
			DBUtil.alertDisplay(1, "�� . �� . �� . ��", "������ �̹� �ֽ��ϴ�.", "���� ������ ��¥�� �ð��� �����ϼ���");
		} else {
			SRVVO hv = new SRVVO(selectSTL.getDate(), selectSTL.getTime(), loginDog.getId(),
					loginDog.getDogname(), loginDog.getBreed(), loginDog.getGender(), loginDog.getAge(),
					loginDog.getNeuter(), loginDog.getSpecial(), (String) scbstyle.getSelectionModel().getSelectedItem());
			if((String) scbstyle.getSelectionModel().getSelectedItem()==null) {
				DBUtil.alertDisplay(1, "���� �Ұ�", "�� ��Ÿ�� �̼���", "���ϴ� �� ��Ÿ���� �������ּ���");
			}else {
				
				srvDAO.getSRVregiste(hv);
				System.out.println("selectSTL.getNo() = " + selectSTL.getNo());
				stlDAO.setReservation(selectSTL.getNo(), "����Ұ�");
				stltotalListSetting();
			}
		}

	}

	private void stltotalListLoder() {
		TableColumn colNo = new TableColumn("No");
		colNo.setMaxWidth(30);
		colNo.setStyle("-fx-alignment: CENTER;");
		colNo.setCellValueFactory(new PropertyValueFactory("no"));

		TableColumn colCheckin = new TableColumn("��¥");
		colCheckin.setMaxWidth(100);
		colCheckin.setStyle("-fx-alignment: CENTER;");
		colCheckin.setCellValueFactory(new PropertyValueFactory("date"));

		TableColumn coltime = new TableColumn("�ð�");
		coltime.setMaxWidth(100);
		coltime.setStyle("-fx-alignment: CENTER;");
		coltime.setCellValueFactory(new PropertyValueFactory("time"));

		TableColumn colReservation = new TableColumn("���࿩��");
		colReservation.setMaxWidth(80);
		colReservation.setStyle("-fx-alignment: CENTER;");
		colReservation.setCellValueFactory(new PropertyValueFactory("reservation"));

		// ���̺� ����. �÷����� ��ü�� ���̺�信 ����Ʈ�߰� �� �׸� �߰�
		stv.getColumns().addAll(colNo, colCheckin, coltime, colReservation);

	}

	
	
	
	
	
	
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
/////////////////////////////////////ȣ��////////////////////////////////////////////////////////////////	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
///////////////////////// ȣ�� ���೻�� Ȯ�ι�ư/////////////////////////////////////////////////////////////////////

	private void handlerhbtnconfirmAction(ActionEvent event) {
		Parent parent = null;
		Stage stage = null;
		try {
			parent = FXMLLoader.load(getClass().getResource("/view/deleteHRV.fxml"));
			Scene scene = new Scene(parent);
			stage = new Stage();

			stage.setTitle("ȣ�� ���� Ȯ��");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (IOException e11) {
			System.out.println("deleteHRV View call error " + e11);
		}
	}

	// ��ü ȣ�� ����Ʈ ����
	private void hrltotalListSetting() {
		ArrayList<HRLVO> list = null;
		HRLDAO hrlDAO = new HRLDAO();
		HRLVO hrlVO = null;
		totalHoteldata.removeAll(totalHoteldata);
		System.out.println("htv.getItems().size()=     "+htv.getItems().size());
		list = hrlDAO.getHRLTotal();
		if (list == null) {
			DBUtil.alertDisplay(1, "���", "DB�������� ����", "���� ���");
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			hrlVO = list.get(i);
			totalHoteldata.add(hrlVO);
		}
		
		htv.setItems(totalHoteldata);
	}

	// ȣ�� Ȯ�ι�ư
	private void handlerhbtnokAction(ActionEvent event) {
		HRLVO selectHRL = (HRLVO) htv.getSelectionModel().getSelectedItem();
		System.out.println(selectHRL);

		HRVVO hv = new HRVVO(selectHRL.getCheckin(), selectHRL.getCheckout(), selectHRL.getRoom(), loginDog.getId(),
				loginDog.getDogname(), loginDog.getBreed(), loginDog.getGender(), loginDog.getAge(),
				loginDog.getNeuter(), loginDog.getSpecial());

		if (selectHRL.getReservation().equals("����Ұ�")) {
			DBUtil.alertDisplay(1, "�� . �� . �� . ��", "������ �̹� �ֽ��ϴ�.", "���� ������ ȣ���̳� ��¥�� �����ϼ���");
		} else {
			try {
				hrvDAO.getHRVregiste(hv);
				System.out.println("selectHRL.getNo() = " + selectHRL.getNo());
				hrlDAO.setReservation(selectHRL.getNo(), "����Ұ�");
				handlerhbtnconfirmAction(event);
				hrltotalListSetting();
			} catch (Exception e) {
				System.out.println(e);
			}
			
		}

	}

	// 2. �ٿ뵵 �˻����
	public void handlerbtndateserchAction(Event event, String corumns1, String corumns2, String search1,
			String search2) {
		String resultString1 = search1.substring(search1.length() - 2, search1.length());
		System.out.println("resultString1=  " + resultString1);
		checkHotelList.removeAll(checkHotelList);
		try {
			HRLDAO hrlDAO = new HRLDAO();
			checkHotelList = hrlDAO.getHRLSearch2(corumns1, corumns2, search1, search2);
			System.out.println("list.size=" + checkHotelList.size());
			if (checkHotelList == null) {
				throw new Exception("�˻�����");
			}
			hoteldata.removeAll(hoteldata);
			for (HRLVO hvo : checkHotelList) {
				hoteldata.add(hvo);
			}
		} catch (Exception e1) {
			DBUtil.alertDisplay(1, "�˻����", "�̸��˻�����", e1.toString());
		}
		htv.setItems(hoteldata);
	}

	// 3. �ٿ뵵 �˻����
	public void handlerSearchAction(Event event, String corumns, String search) {
		checkHotelList.removeAll(checkHotelList);
		try {
			HRLDAO hrlDAO = new HRLDAO();
			checkHotelList = hrlDAO.getHRLSearch(corumns, search);
			System.out.println("list.size=" + checkHotelList.size());
			if (checkHotelList == null) {
				throw new Exception("�˻�����");
			}
			hoteldata.removeAll(hoteldata);
			for (HRLVO hvo : checkHotelList) {
				hoteldata.add(hvo);
			}
		} catch (Exception e1) {
			DBUtil.alertDisplay(1, "�˻����", "�̸��˻�����", e1.toString());
		}
		htv.setItems(hoteldata);
	}
	
	//���̺� �� �÷�����
	private void hrltotalListLoder() {
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

		TableColumn colReservation = new TableColumn("���࿩��");
		colReservation.setMaxWidth(80);
		colReservation.setStyle("-fx-alignment: CENTER;");
		colReservation.setCellValueFactory(new PropertyValueFactory("reservation"));

		// ���̺� ����. �÷����� ��ü�� ���̺�信 �߰�
		htv.getColumns().addAll(colNo, colCheckin, colCheckout, colRoom, colReservation);

	}
	////////��ҽ� mydog�ҷ�����
	private void callmydog (){
		Parent parent = null;
		Stage stage = null;
		try {
			parent = FXMLLoader.load(getClass().getResource("/view/myDog.fxml"));
			Scene scene = new Scene(parent);
			stage = new Stage();

			stage.setTitle(selectCustomer.getName()+"���� ������");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (IOException e11) {
			System.out.println("myDog View call error " + e11);
		}
	}
	
}
	
	
	
	
	
	
	
	
	
	
	

