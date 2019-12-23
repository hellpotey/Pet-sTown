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

	// ������ ��������
	private HRVDAO hrvDAO = new HRVDAO();
	private HRLDAO hrlDAO = new HRLDAO();
	private LoginCustomerDAO loginCustomerDAO = new LoginCustomerDAO();
	private LoginCustomerVO selectCustomer = loginCustomerDAO.getLoginCustomer();
	private ArrayList<HRVVO> HRVList = hrvDAO.getHRVSearch("id",selectCustomer.getId());

	ObservableList<HRVVO> data = FXCollections.observableArrayList();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// ���̺��� �������ش�.
		tablesetting();
		
		// ������ư
		btndelete.setOnAction(e1 -> {
		tabledelete();
		});
		// �ڷι�ư
		btnback.setOnAction(e -> {
			((Stage) btnback.getScene().getWindow()).close();
			comemyHRV();
		});
	}
	private void tabledelete() {
		HRVVO selectHRL=(HRVVO) deleteTb.getSelectionModel().getSelectedItem();
		System.out.println("selectHRL="+ selectHRL);
		///���� �� ����Ʈ HRV �� ���೻���� �����ϰ�
		hrvDAO.getHRVDelete(selectHRL.getCheckin(), selectHRL.getCheckout(), selectHRL.getRoom());
		
		///Hotel room list�� �� ����
		hrlDAO.setReservation2("���డ��", selectHRL.getCheckin(), selectHRL.getCheckout(), selectHRL.getRoom());
		///
		// �����ϸ� �ٽü���
		data.removeAll(data);
		HRVList = hrvDAO.getHRVSearch("id",selectCustomer.getId());
		if (HRVList == null) {
			DBUtil.alertDisplay(1, "���", "������", "���µ� ��� �����ؿ�");
			return;
		}
		for (HRVVO hrv : HRVList) {
			System.out.println("d =  " + hrv);
			data.add(hrv);
		}
		deleteTb.setItems(data);
	}
	// ���̺��� �������ش�.
	private void tablesetting() {
		TableColumn coldNo = new TableColumn("no");
		coldNo.setMaxWidth(100);
		coldNo.setStyle("-fx-alignment: CENTER;");
		coldNo.setCellValueFactory(new PropertyValueFactory<>("no"));

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

		TableColumn colID = new TableColumn("���� ���̵�");
		colID.setMaxWidth(80);
		colID.setStyle("-fx-alignment: CENTER;");
		colID.setCellValueFactory(new PropertyValueFactory("id"));

		TableColumn colDogname = new TableColumn("������ �̸�");
		colDogname.setMaxWidth(80);
		colDogname.setStyle("-fx-alignment: CENTER;");
		colDogname.setCellValueFactory(new PropertyValueFactory<>("dogname"));

		// ���̺� ����. �÷����� ��ü�� ���̺�信 ����Ʈ�߰� �� �׸� �߰�
		deleteTb.getColumns().addAll(coldNo, colCheckin, colCheckout, colRoom, colID, colDogname);
		if (HRVList == null) {
			DBUtil.alertDisplay(1, "���", "������", "���µ� ��� �����ؿ�");
			return;
		}
		for (HRVVO hrv : HRVList) {
			System.out.println("d =  " + hrv);
			data.add(hrv);
		}
		deleteTb.setItems(data);
	}
	// ����â �ٽ� �ҷ��ö�
	public void comemyHRV() {
		Parent addHRVController = null;
		Stage addHRVStage = null;
		try {
			addHRVController = FXMLLoader.load(getClass().getResource("/view/hotelReservation.fxml"));
			Scene scene = new Scene(addHRVController);
			addHRVStage = new Stage();

			addHRVStage.setTitle("ȣ�� ���� Ȯ��");
			addHRVStage.setScene(scene);
			addHRVStage.setResizable(false);
			addHRVStage.show();
		} catch (IOException e11) {
			System.out.println("hotelReservation View call error " + e11);
		}
	}

}
