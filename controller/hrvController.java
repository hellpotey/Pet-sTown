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
	
	//데이터 넣는용
	ObservableList data = FXCollections.observableArrayList();
	
	//테이블 뷰에 데이터 갯수 구하기 위함
	ArrayList<STLVO> checkSalonList = new ArrayList<STLVO>();
	ArrayList<HRLVO> checkHotelList = new ArrayList<HRLVO>();
	
	private HRVDAO hrvDAO = new HRVDAO();
	private HRLDAO hrlDAO = new HRLDAO();
	private SRVDAO srvDAO = new SRVDAO();
	private STLDAO stlDAO = new STLDAO();
	// 로그인한 고객정보 , 강아지리스트 가져오기 위함
	private DogDAO dogDAO = new DogDAO();
	private LoginCustomerDAO loginCustomerDAO = new LoginCustomerDAO();
	private LoginCustomerVO selectCustomer = loginCustomerDAO.getLoginCustomer();
	private ArrayList<DogVO> dogList = dogDAO.getLoginCustomerDogList(selectCustomer.getId());
	//
	// 로그인한 개 정보
	private loginDogDAO loginDogDAO = new loginDogDAO();
	private DogVO loginDog = loginDogDAO.getLoginDog();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		////////////////////////// 호텔/////////////////////////////////////////////
		// 호텔 예약가능 리스트 가져온다
		hrltotalListLoder();
		// 호텔 예약가능 리스트 셋팅한다.
		hrltotalListSetting();
		// 호텔 체크인검색
		hdpcheckin.setOnAction(event -> {
			handlerSearchAction(event, "checkin", hdpcheckin.getValue().toString());
		});
		// '' 아웃
		hdpcheckout.setOnAction(event -> {
			handlerSearchAction(event, "checkout", hdpcheckout.getValue().toString());
		});
		// '' 통합
		hbtndateserch.setOnAction(event -> {
			handlerbtndateserchAction(event, "checkin", "checkout", hdpcheckin.getValue().toString(),
					hdpcheckout.getValue().toString());
		});
		
		// 호텔 취소 버튼
		hbtncancel.setOnAction(event -> {
			((Stage) hbtncancel.getScene().getWindow()).close();
			callmydog();
		});

		// 호텔 예약버튼
		hbtnok.setOnAction(event -> {
			handlerhbtnokAction(event);
		});
		// 호텔 예약관리버튼
		hbtnconfirm.setOnAction(event -> {
			handlerhbtnconfirmAction(event);
		});
		// 호텔 전체 리스트 버튼
		hbtntotal.setOnAction(event -> {
			hrltotalListSetting();
		});
////////////////////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////// 미용/////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 미용 예약가능 리스트 가져온다
		stltotalListLoder();
		// 미용 예약가능 리스트 셋팅한다.
		stltotalListSetting();
		// 미용 데이트 피커 검색
		sdpdate.setOnAction(event -> {
			 System.out.println("sdpdate.getValue().toString()=             "+sdpdate.getValue().toString());
			 handlerSalonSearchAction(event, "date", sdpdate.getValue().toString());
		});
		// 컷 스타일 체크박스 셋팅
		cbstyleSetting();
		// 미용 예약버튼
		sbtnok.setOnAction(event -> {
			handlersbtnokAction(event);
		});
		// 미용 예약확인버튼
		sbtnconfirm.setOnAction(event -> {
			handlersbtnconfirmAction(event);
		});
		// 미용 전체 리스트 버튼
		sbtntotal.setOnAction(event -> {
			stltotalListSetting();
		});
		// 호텔 취소 버튼
		sbtncancel.setOnAction(event -> {
			((Stage) sbtncancel.getScene().getWindow()).close();
			callmydog();
		});
	}



/////////////////////////////////////////////////////////////////////
/////////////////////// 미용////////////////////////////
/////////////////////////////////////////////////////////////////////
	
	// 미용예약확인 벝늕
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
	
	// 스타일 체크박스 셋팅
	private void cbstyleSetting() {
		scbstyle.setItems(FXCollections.observableArrayList("목욕", "클리핑", "스포팅", "가위컷"));
	}
	
	// 전체 미용 리스트 셋팅
	private void stltotalListSetting() {
		//
		ArrayList<STLVO> list = null;
		STLDAO stlDAO = new STLDAO();
		STLVO stlVO = null;
		totalSalondata.removeAll(totalSalondata);
		list = stlDAO.getSTLTotal();
		if (list == null) {
			DBUtil.alertDisplay(1, "경고", "DB가져오기 오류", "점검 요망");
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			stlVO = list.get(i);
			totalSalondata.add(stlVO);
		}
		stv.setItems(totalSalondata);
	}

	// 살롱 다용도 검색기능
	public void handlerSalonSearchAction(Event event, String corumns, String search) {
		try {
			ArrayList<STLVO> list = new ArrayList<STLVO>();
			STLDAO stlDAO = new STLDAO();
			list = stlDAO.getSTLSearch(corumns, search);
			System.out.println("list.size=" + list.size());
			if (list == null) {
				throw new Exception("검색오류");
			}
			salondata.removeAll(salondata);
			for (STLVO stl : list) {
				salondata.add(stl);
			}
		} catch (Exception e1) {
			DBUtil.alertDisplay(1, "검색결과", "이름검색오류", e1.toString());
		}
		stv.setItems(salondata);
	}
	
	// 미용 확인버튼
	private void handlersbtnokAction(ActionEvent event) {
		STLVO selectSTL = (STLVO) stv.getSelectionModel().getSelectedItem();
		System.out.println(selectSTL);
		if (selectSTL.getReservation().equals("예약불가")) {
			DBUtil.alertDisplay(1, "예 . 약 . 불 . 가", "예약이 이미 있습니다.", "예약 가능한 날짜나 시간을 선택하세요");
		} else {
			SRVVO hv = new SRVVO(selectSTL.getDate(), selectSTL.getTime(), loginDog.getId(),
					loginDog.getDogname(), loginDog.getBreed(), loginDog.getGender(), loginDog.getAge(),
					loginDog.getNeuter(), loginDog.getSpecial(), (String) scbstyle.getSelectionModel().getSelectedItem());
			if((String) scbstyle.getSelectionModel().getSelectedItem()==null) {
				DBUtil.alertDisplay(1, "예약 불가", "컷 스타일 미선택", "원하는 컷 스타일을 선택해주세요");
			}else {
				
				srvDAO.getSRVregiste(hv);
				System.out.println("selectSTL.getNo() = " + selectSTL.getNo());
				stlDAO.setReservation(selectSTL.getNo(), "예약불가");
				stltotalListSetting();
			}
		}

	}

	private void stltotalListLoder() {
		TableColumn colNo = new TableColumn("No");
		colNo.setMaxWidth(30);
		colNo.setStyle("-fx-alignment: CENTER;");
		colNo.setCellValueFactory(new PropertyValueFactory("no"));

		TableColumn colCheckin = new TableColumn("날짜");
		colCheckin.setMaxWidth(100);
		colCheckin.setStyle("-fx-alignment: CENTER;");
		colCheckin.setCellValueFactory(new PropertyValueFactory("date"));

		TableColumn coltime = new TableColumn("시간");
		coltime.setMaxWidth(100);
		coltime.setStyle("-fx-alignment: CENTER;");
		coltime.setCellValueFactory(new PropertyValueFactory("time"));

		TableColumn colReservation = new TableColumn("예약여부");
		colReservation.setMaxWidth(80);
		colReservation.setStyle("-fx-alignment: CENTER;");
		colReservation.setCellValueFactory(new PropertyValueFactory("reservation"));

		// 테이블 설정. 컬럼들의 객체를 테이블뷰에 리스트추가 및 항목 추가
		stv.getColumns().addAll(colNo, colCheckin, coltime, colReservation);

	}

	
	
	
	
	
	
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
/////////////////////////////////////호텔////////////////////////////////////////////////////////////////	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
///////////////////////// 호텔 예약내역 확인버튼/////////////////////////////////////////////////////////////////////

	private void handlerhbtnconfirmAction(ActionEvent event) {
		Parent parent = null;
		Stage stage = null;
		try {
			parent = FXMLLoader.load(getClass().getResource("/view/deleteHRV.fxml"));
			Scene scene = new Scene(parent);
			stage = new Stage();

			stage.setTitle("호텔 예약 확인");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (IOException e11) {
			System.out.println("deleteHRV View call error " + e11);
		}
	}

	// 전체 호텔 리스트 셋팅
	private void hrltotalListSetting() {
		ArrayList<HRLVO> list = null;
		HRLDAO hrlDAO = new HRLDAO();
		HRLVO hrlVO = null;
		totalHoteldata.removeAll(totalHoteldata);
		System.out.println("htv.getItems().size()=     "+htv.getItems().size());
		list = hrlDAO.getHRLTotal();
		if (list == null) {
			DBUtil.alertDisplay(1, "경고", "DB가져오기 오류", "점검 요망");
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			hrlVO = list.get(i);
			totalHoteldata.add(hrlVO);
		}
		
		htv.setItems(totalHoteldata);
	}

	// 호텔 확인버튼
	private void handlerhbtnokAction(ActionEvent event) {
		HRLVO selectHRL = (HRLVO) htv.getSelectionModel().getSelectedItem();
		System.out.println(selectHRL);

		HRVVO hv = new HRVVO(selectHRL.getCheckin(), selectHRL.getCheckout(), selectHRL.getRoom(), loginDog.getId(),
				loginDog.getDogname(), loginDog.getBreed(), loginDog.getGender(), loginDog.getAge(),
				loginDog.getNeuter(), loginDog.getSpecial());

		if (selectHRL.getReservation().equals("예약불가")) {
			DBUtil.alertDisplay(1, "예 . 약 . 불 . 가", "예약이 이미 있습니다.", "예약 가능한 호실이나 날짜를 선택하세요");
		} else {
			try {
				hrvDAO.getHRVregiste(hv);
				System.out.println("selectHRL.getNo() = " + selectHRL.getNo());
				hrlDAO.setReservation(selectHRL.getNo(), "예약불가");
				handlerhbtnconfirmAction(event);
				hrltotalListSetting();
			} catch (Exception e) {
				System.out.println(e);
			}
			
		}

	}

	// 2. 다용도 검색기능
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
				throw new Exception("검색오류");
			}
			hoteldata.removeAll(hoteldata);
			for (HRLVO hvo : checkHotelList) {
				hoteldata.add(hvo);
			}
		} catch (Exception e1) {
			DBUtil.alertDisplay(1, "검색결과", "이름검색오류", e1.toString());
		}
		htv.setItems(hoteldata);
	}

	// 3. 다용도 검색기능
	public void handlerSearchAction(Event event, String corumns, String search) {
		checkHotelList.removeAll(checkHotelList);
		try {
			HRLDAO hrlDAO = new HRLDAO();
			checkHotelList = hrlDAO.getHRLSearch(corumns, search);
			System.out.println("list.size=" + checkHotelList.size());
			if (checkHotelList == null) {
				throw new Exception("검색오류");
			}
			hoteldata.removeAll(hoteldata);
			for (HRLVO hvo : checkHotelList) {
				hoteldata.add(hvo);
			}
		} catch (Exception e1) {
			DBUtil.alertDisplay(1, "검색결과", "이름검색오류", e1.toString());
		}
		htv.setItems(hoteldata);
	}
	
	//테이블 뷰 컬럼셋팅
	private void hrltotalListLoder() {
		TableColumn colNo = new TableColumn("No");
		colNo.setMaxWidth(30);
		colNo.setStyle("-fx-alignment: CENTER;");
		colNo.setCellValueFactory(new PropertyValueFactory("no"));

		TableColumn colCheckin = new TableColumn("체크인");
		colCheckin.setMaxWidth(100);
		colCheckin.setStyle("-fx-alignment: CENTER;");
		colCheckin.setCellValueFactory(new PropertyValueFactory("checkin"));

		TableColumn colCheckout = new TableColumn("체크아웃");
		colCheckout.setMaxWidth(100);
		colCheckout.setStyle("-fx-alignment: CENTER;");
		colCheckout.setCellValueFactory(new PropertyValueFactory("checkout"));

		TableColumn colRoom = new TableColumn("객실");
		colRoom.setMaxWidth(50);
		colRoom.setStyle("-fx-alignment: CENTER;");
		colRoom.setCellValueFactory(new PropertyValueFactory("room"));

		TableColumn colReservation = new TableColumn("예약여부");
		colReservation.setMaxWidth(80);
		colReservation.setStyle("-fx-alignment: CENTER;");
		colReservation.setCellValueFactory(new PropertyValueFactory("reservation"));

		// 테이블 설정. 컬럼들의 객체를 테이블뷰에 추가
		htv.getColumns().addAll(colNo, colCheckin, colCheckout, colRoom, colReservation);

	}
	////////취소시 mydog불러오기
	private void callmydog (){
		Parent parent = null;
		Stage stage = null;
		try {
			parent = FXMLLoader.load(getClass().getResource("/view/myDog.fxml"));
			Scene scene = new Scene(parent);
			stage = new Stage();

			stage.setTitle(selectCustomer.getName()+"님의 강아지");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (IOException e11) {
			System.out.println("myDog View call error " + e11);
		}
	}
	
}
	
	
	
	
	
	
	
	
	
	
	

