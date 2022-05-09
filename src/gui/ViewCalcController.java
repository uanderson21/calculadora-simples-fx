package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import calculadora.Adicao;
import calculadora.Divisao;
import calculadora.Multiplicacao;
import calculadora.OperacaoBinaria;
import calculadora.Subtracao;
import gui.util.Alerts;
import gui.util.Constraints;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.Operador;
import javafx.scene.control.Alert.AlertType;

public class ViewCalcController implements Initializable {
			
	@FXML
	public TextField txtValue1;
	@FXML 
	public TextField txtValue2;
	@FXML
	public TextField txtResult;
	@FXML
	public ComboBox<Operador> cbOperadores;
	@FXML
	public Button btnCalc;

	private ObservableList<Operador> obsOperadores;
	OperacaoBinaria opBinario;
	
	public void loadOperator() {
		List<Operador> list = new ArrayList<>();
		list.add(new Operador(1, "+"));
		list.add(new Operador(1, "/"));
		list.add(new Operador(1, "-"));
		list.add(new Operador(1, "*"));
			
		obsOperadores = FXCollections.observableArrayList(list);		
		cbOperadores.setItems(obsOperadores);	
		
		Callback<ListView<Operador>, ListCell<Operador>> factory = lv -> new ListCell<Operador>() {
			@Override
			protected void updateItem(Operador item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNome());
			}
		};
		cbOperadores.setCellFactory(factory);
		cbOperadores.setButtonCell(factory.call(null));		
	}
	
	@FXML
	public void onBtnCalcAction() {
		//valida		
		if ((txtValue1.getText().equals("")) || (txtValue2.getText().equals(""))) {
			Alerts.showAlert("Calculadora", null, "Necessário informar todos os campos", AlertType.INFORMATION);
			return;
		}
		
		if (cbOperadores.getValue() == null) {
			Alerts.showAlert("Calculadora", null, "Selecione uma operação para efetuar o calculo", AlertType.INFORMATION);
			return;
		}
		
		try {
			String op = cbOperadores.getValue().getNome();
			
			double value1 = Double.parseDouble(txtValue1.getText());
			double value2 = Double.parseDouble(txtValue2.getText());
			
			switch(op) {
				case "+":
					opBinario = new Adicao();
					break;
				case "/":
					opBinario = new Divisao();
					break;
				case "-":
					opBinario = new Subtracao();
					break;
				case "*":
					opBinario = new Multiplicacao();
					break;
			}
			double result = opBinario.calcular(value1, value2);
			
			txtResult.setText(String.valueOf(result));
			
		}catch(NumberFormatException e) {
			Alerts.showAlert("Calculadora", null, e.getMessage(), AlertType.ERROR);
		}
		
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		loadOperator();		
		
		Constraints.setTextFieldDouble(txtValue1);
		Constraints.setTextFieldDouble(txtValue2);
		
		Constraints.setTextFieldMaxLength(txtValue1, 12);
		Constraints.setTextFieldMaxLength(txtValue2, 12);
	}
	
	


}
