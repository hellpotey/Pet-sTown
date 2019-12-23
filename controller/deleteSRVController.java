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

	// ������ ��������
	private STLDAO stlDAO = new STLDAO();
	private SRVDAO srvDAO = new SRVDAO();
	private LoginCustomerDAO loginCustomerDAO = new LoginCustomerDAO();
	private LoginCustomerVO selectCustomer = loginCustomerDAO.getLoginCustomer();
	private ArrayList<SRVVO> SRVList = srvDAO.getSRVSearch("id", selectCustomer.getId());

	ObservableList<SRVVO> data = FXCollections.observableArrayList();
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
			comemySRV();
		});
	}
	private void tabledelete() {
		SRVVO selectSTL=(SRVVO) deleteTb.getSelectionModel().getSelectedItem();
		System.out.println("selectSTL="+ selectSTL);
		///���� �� ����Ʈ SRV �� ���೻���� �����ϰ�
		srvDAO.getSRVDelete(selectSTL.getDate(), selectSTL.getTime());
		
		///salon room list�� �� ����
		stlDAO.setReservation2("���డ��", selectSTL.getDate(), selectSTL.getTime());
		///
		// �����ϸ� �ٽü���
		data.removeAll(data);
		SRVList = srvDAO.getSRVSearch("id",selectCustomer.getId());
		if (SRVList == null) {
			DBUtil.alertDisplay(1, "���", "������", "���µ� ��� �����ؿ�");
			return;
		}
		for (SRVVO srv : SRVList) {
			System.out.println("d =  " + srv);
			data.add(srv);
		}
		deleteTb.setItems(data);
	}
	// ���̺��� �������ش�.
	private void tablesetting() {
		TableColumn colCheckin = new TableColumn("������");
		colCheckin.setMaxWidth(100);
		colCheckin.setStyle("-fx-alignment: CENTER;");
		colCheckin.setCellValueFactory(new PropertyValueFactory("date"));

		TableColumn colCheckout = new TableColumn("����ð�");
		colCheckout.setMaxWidth(100);
		colCheckout.setStyle("-fx-alignment: CENTER;");
		colCheckout.setCellValueFactory(new PropertyValueFactory("time"));

		TableColumn colID = new TableColumn("���� ���̵�");
		colID.setMaxWidth(80);
		colID.setStyle("-fx-alignment: CENTER;");
		colID.setCellValueFactory(new PropertyValueFactory("id"));

		TableColumn colDogname = new TableColumn("������ �̸�");
		colDogname.setMaxWidth(80);
		colDogname.setStyle("-fx-alignment: CENTER;");
		colDogname.setCellValueFactory(new PropertyValueFactory<>("dogname"));
		
		TableColumn colDogCutStyle = new TableColumn("cut Style");
		colDogCutStyle.setMaxWidth(80);
		colDogCutStyle.setStyle("-fx-alignment: CENTER;");
		colDogCutStyle.setCellValueFactory(new PropertyValueFactory<>("cutstyle"));

		// ���̺� ����. �÷����� ��ü�� ���̺�信 ����Ʈ�߰� �� �׸� �߰�
		deleteTb.getColumns().addAll(colCheckin, colCheckout, colID, colDogname, colDogCutStyle);
		
		if (SRVList == null) {
			DBUtil.alertDisplay(1, "���", "������", "���µ� ��� �����ؿ�");
			return;
		}
		for (SRVVO srv : SRVList) {
			System.out.println("1");
			System.out.println("d =  " + srv);
			data.add(srv);
		}
		deleteTb.setItems(data);
	}
	// ����â �ٽ� �ҷ��ö�
	public void comemySRV() {
		Parent addSRVController = null;
		Stage addSRVStage = null;
		try {
			addSRVController = FXMLLoader.load(getClass().getResource("/view/hotelReservation.fxml"));
			Scene scene = new Scene(addSRVController);
			addSRVStage = new Stage();

			addSRVStage.setTitle("�̿� ���� Ȯ��");
			addSRVStage.setScene(scene);
			addSRVStage.setResizable(false);
			addSRVStage.show();
		} catch (IOException e11) {
			System.out.println("hotelReservation View call error " + e11);
		}
	}

}
