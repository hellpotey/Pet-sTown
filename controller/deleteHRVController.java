package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.HRLVO;
import model.HRVVO;
import model.LoginCustomerVO;

public class deleteHRVController implements Initializable {
	@FXML
	private TableView deleteTb;
	@FXML
	private Button btndelete;
	@FXML
	private Button btnback;

	// 고객정보 가져오기
	private HRVDAO hrvDAO = new HRVDAO();
	private HRLDAO hrlDAO = new HRLDAO();
	private LoginCustomerDAO loginCustomerDAO = new LoginCustomerDAO();
	private LoginCustomerVO selectCustomer = loginCustomerDAO.getLoginCustomer();
	private ArrayList<HRVVO> HRVList = hrvDAO.getHRVSearch("id",selectCustomer.getId());

	ObservableList<HRVVO> data = FXCollections.observableArrayList();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// 테이블을 셋팅해준다.
		tablesetting();
		
		// 삭제버튼
		btndelete.setOnAction(e1 -> {
		tabledelete();
		});
		// 뒤로버튼
		btnback.setOnAction(e -> {
			((Stage) btnback.getScene().getWindow()).close();
			comemyHRV();
		});
	}
	private void tabledelete() {
		HRVVO selectHRL=(HRVVO) deleteTb.getSelectionModel().getSelectedItem();
		System.out.println("selectHRL="+ selectHRL);
		///예약 된 리스트 HRV 의 예약내역을 삭제하고
		hrvDAO.getHRVDelete(selectHRL.getCheckin(), selectHRL.getCheckout(), selectHRL.getRoom());
		
		///Hotel room list를 재 셋팅
		hrlDAO.setReservation2("예약가능", selectHRL.getCheckin(), selectHRL.getCheckout(), selectHRL.getRoom());
		///
		// 삭제하면 다시셋팅
		data.removeAll(data);
		HRVList = hrvDAO.getHRVSearch("id",selectCustomer.getId());
		if (HRVList == null) {
			DBUtil.alertDisplay(1, "경고", "예약이", "없는데 어떻게 삭제해요");
			return;
		}
		for (HRVVO hrv : HRVList) {
			System.out.println("d =  " + hrv);
			data.add(hrv);
		}
		deleteTb.setItems(data);
	}
	// 테이블을 셋팅해준다.
	private void tablesetting() {
		TableColumn coldNo = new TableColumn("no");
		coldNo.setMaxWidth(100);
		coldNo.setStyle("-fx-alignment: CENTER;");
		coldNo.setCellValueFactory(new PropertyValueFactory<>("no"));

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

		TableColumn colID = new TableColumn("견주 아이디");
		colID.setMaxWidth(80);
		colID.setStyle("-fx-alignment: CENTER;");
		colID.setCellValueFactory(new PropertyValueFactory("id"));

		TableColumn colDogname = new TableColumn("강아지 이름");
		colDogname.setMaxWidth(80);
		colDogname.setStyle("-fx-alignment: CENTER;");
		colDogname.setCellValueFactory(new PropertyValueFactory<>("dogname"));

		// 테이블 설정. 컬럼들의 객체를 테이블뷰에 리스트추가 및 항목 추가
		deleteTb.getColumns().addAll(coldNo, colCheckin, colCheckout, colRoom, colID, colDogname);
		if (HRVList == null) {
			DBUtil.alertDisplay(1, "경고", "예약이", "없는데 어떻게 삭제해요");
			return;
		}
		for (HRVVO hrv : HRVList) {
			System.out.println("d =  " + hrv);
			data.add(hrv);
		}
		deleteTb.setItems(data);
	}
	// 예약창 다시 불러올때
	public void comemyHRV() {
		Parent addHRVController = null;
		Stage addHRVStage = null;
		try {
			addHRVController = FXMLLoader.load(getClass().getResource("/view/hotelReservation.fxml"));
			Scene scene = new Scene(addHRVController);
			addHRVStage = new Stage();

			addHRVStage.setTitle("호텔 예약 확인");
			addHRVStage.setScene(scene);
			addHRVStage.setResizable(false);
			addHRVStage.show();
		} catch (IOException e11) {
			System.out.println("hotelReservation View call error " + e11);
		}
	}

}
