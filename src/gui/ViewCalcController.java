package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import calculadora.Adicao;
import calculadora.Divisao;
import calculadora.Multiplicacao;
import calculadora.Subtracao;
import gui.util.Alerts;
import gui.util.Constraints;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
	
	private List<Operador> operadores = new ArrayList<>();
	private ObservableList<Operador> obsOperadores;
	double result;
	
	public void loadOperator() {
		Operador opAdicao = new Operador(1, "+");
		Operador opDivisao = new Operador(1, "/");
		Operador opSubtracao = new Operador(1, "-");
		Operador opMultiplicacao = new Operador(1, "*");
		
		operadores.add(opAdicao);
		operadores.add(opDivisao);
		operadores.add(opSubtracao);
		operadores.add(opMultiplicacao);
		
		obsOperadores = FXCollections.observableArrayList(operadores);
		
		cbOperadores.setItems(obsOperadores);
		
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
					result = new Adicao().calcular(value1, value2);
					break;
				case "/":
					result = new Divisao().calcular(value1, value2);
					break;
				case "-":
					result = new Subtracao().calcular(value1, value2);
					break;
				case "*":
					result = new Multiplicacao().calcular(value1, value2);
					break;
			}
			
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
