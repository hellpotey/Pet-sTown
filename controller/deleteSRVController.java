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
import model.LoginCustomerVO;
import model.SRVVO;

public class deleteSRVController implements Initializable {
	@FXML
	private TableView deleteTb;
	@FXML
	private Button btndelete;
	@FXML
	private Button btnback;

	// 고객정보 가져오기
	private STLDAO stlDAO = new STLDAO();
	private SRVDAO srvDAO = new SRVDAO();
	private LoginCustomerDAO loginCustomerDAO = new LoginCustomerDAO();
	private LoginCustomerVO selectCustomer = loginCustomerDAO.getLoginCustomer();
	private ArrayList<SRVVO> SRVList = srvDAO.getSRVSearch("id", selectCustomer.getId());

	ObservableList<SRVVO> data = FXCollections.observableArrayList();
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
			comemySRV();
		});
	}
	private void tabledelete() {
		SRVVO selectSTL=(SRVVO) deleteTb.getSelectionModel().getSelectedItem();
		System.out.println("selectSTL="+ selectSTL);
		///예약 된 리스트 SRV 의 예약내역을 삭제하고
		srvDAO.getSRVDelete(selectSTL.getDate(), selectSTL.getTime());
		
		///salon room list를 재 셋팅
		stlDAO.setReservation2("예약가능", selectSTL.getDate(), selectSTL.getTime());
		///
		// 삭제하면 다시셋팅
		data.removeAll(data);
		SRVList = srvDAO.getSRVSearch("id",selectCustomer.getId());
		if (SRVList == null) {
			DBUtil.alertDisplay(1, "경고", "예약이", "없는데 어떻게 삭제해요");
			return;
		}
		for (SRVVO srv : SRVList) {
			System.out.println("d =  " + srv);
			data.add(srv);
		}
		deleteTb.setItems(data);
	}
	// 테이블을 셋팅해준다.
	private void tablesetting() {
		TableColumn colCheckin = new TableColumn("예약일");
		colCheckin.setMaxWidth(100);
		colCheckin.setStyle("-fx-alignment: CENTER;");
		colCheckin.setCellValueFactory(new PropertyValueFactory("date"));

		TableColumn colCheckout = new TableColumn("예약시간");
		colCheckout.setMaxWidth(100);
		colCheckout.setStyle("-fx-alignment: CENTER;");
		colCheckout.setCellValueFactory(new PropertyValueFactory("time"));

		TableColumn colID = new TableColumn("견주 아이디");
		colID.setMaxWidth(80);
		colID.setStyle("-fx-alignment: CENTER;");
		colID.setCellValueFactory(new PropertyValueFactory("id"));

		TableColumn colDogname = new TableColumn("강아지 이름");
		colDogname.setMaxWidth(80);
		colDogname.setStyle("-fx-alignment: CENTER;");
		colDogname.setCellValueFactory(new PropertyValueFactory<>("dogname"));
		
		TableColumn colDogCutStyle = new TableColumn("cut Style");
		colDogCutStyle.setMaxWidth(80);
		colDogCutStyle.setStyle("-fx-alignment: CENTER;");
		colDogCutStyle.setCellValueFactory(new PropertyValueFactory<>("cutstyle"));

		// 테이블 설정. 컬럼들의 객체를 테이블뷰에 리스트추가 및 항목 추가
		deleteTb.getColumns().addAll(colCheckin, colCheckout, colID, colDogname, colDogCutStyle);
		
		if (SRVList == null) {
			DBUtil.alertDisplay(1, "경고", "예약이", "없는데 어떻게 삭제해요");
			return;
		}
		for (SRVVO srv : SRVList) {
			System.out.println("1");
			System.out.println("d =  " + srv);
			data.add(srv);
		}
		deleteTb.setItems(data);
	}
	// 예약창 다시 불러올때
	public void comemySRV() {
		Parent addSRVController = null;
		Stage addSRVStage = null;
		try {
			addSRVController = FXMLLoader.load(getClass().getResource("/view/hotelReservation.fxml"));
			Scene scene = new Scene(addSRVController);
			addSRVStage = new Stage();

			addSRVStage.setTitle("미용 예약 확인");
			addSRVStage.setScene(scene);
			addSRVStage.setResizable(false);
			addSRVStage.show();
		} catch (IOException e11) {
			System.out.println("hotelReservation View call error " + e11);
		}
	}

}
