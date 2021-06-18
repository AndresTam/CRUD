package components;

import javafx.scene.control.*;
import models.ProductosDAO;
import sample.frmProductos;
import java.util.Optional;

public class BtnCustomCell extends TableCell<ProductosDAO, String> {
    private ProductosDAO objDAO;
    private Button btnCelda;

    public BtnCustomCell(int opc){
        if(opc == 1){
            btnCelda = new Button("Editar");
            btnCelda.setOnAction(event -> {
                objDAO = BtnCustomCell.this.getTableView().getItems().get(BtnCustomCell.this.getIndex());
                new frmProductos(BtnCustomCell.this.getTableView(), objDAO);
            });
        } else if(opc == 2){
            btnCelda = new Button("Eliminar");
            btnCelda.setOnAction(event -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Mensaje del Sistema");
                alerta.setHeaderText("Confirmación de la acción");
                alerta.setContentText("¿Realmente desea borrar el registro?");
                Optional<ButtonType> result = alerta.showAndWait();
                if(result.get() == ButtonType.OK){
                    objDAO = BtnCustomCell.this.getTableView().getItems().get(BtnCustomCell.this.getIndex());
                    objDAO.DELETE();
                    BtnCustomCell.this.getTableView().setItems(objDAO.SELECT());
                    BtnCustomCell.this.getTableView().refresh();
                }
            });
        } else if(opc == 3){
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if(!empty){
            setGraphic(btnCelda);
        }
    }
}
