import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class FrmCajaregistradora extends JFrame {


    //Campo de texto donde se ingresa el valor a devolver
    private JTextField txtValor;
    //Tabla de resultados
    private JTable tblResultado;
    //Encabezados de la tabla
    String[] encabezados={"Denominación", "Presentación", "Cantidad usada"};

    //Arreglo con las denominaciones disponibles
    private int[] denominaciones={100000, 50000, 20000, 10000, 5000, 2000 , 1000, 500, 200, 100, 50};
    //Arreglo que indica si es billete o moneda
    private String[] tipo={"Billete", "Billete", "Billete", "Billete", "Billete", "Billete", "Moneda", "Moneda", "Moneda", "Moneda", "Moneda"};
    //Arreglo de almacenamiento de las existencias ingresadas
    private int[] existencias=new int[11];


    //Metodo constructor
    public FrmCajaregistradora(){

        //Configuracion básica de la ventana
        setTitle("Caja Registradora");
        setSize(600,450);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        //Etiqueta para el valor a devolver
        JLabel lblValor =new JLabel("Valor a devolver:");
        lblValor.setBounds(10, 10, 120, 25);
        add(lblValor);

        //Campo donde el usuario escribe el valor
        txtValor=new JTextField();
        txtValor.setBounds(120, 10, 120, 25);
        add(txtValor);

        //Boton para ingresar existencias
        JButton btnExistencias= new JButton("Actualizar existencias");
        btnExistencias.setBounds(10, 45, 200, 25);
        add(btnExistencias);
        
        //Boton para calcular el valor a devolver
        JButton btnCalcular= new JButton("Calcular");
        btnCalcular.setBounds(10, 80, 200, 25);
        add(btnCalcular);


        //Creación de la tabla
        tblResultado= new JTable();
        JScrollPane spTabla =new JScrollPane(tblResultado);
        spTabla.setBounds(10, 115, 550, 200);
        add(spTabla);

        //Inicialización de la tabla vacía
        tblResultado.setModel(new DefaultTableModel(null, encabezados));

        //Eventos de los botones
        btnExistencias.addActionListener(e ->ingresarExistencias());
        btnCalcular.addActionListener(e ->calcularDevuelta());

        



    } 

    //Metodo para ingresar las existencias
    private void ingresarExistencias(){
            //Recorrer todas las denominaciones
            for (int i=0;i<denominaciones .length; i++){
                //Pedir al usuario la cantidad disponible
                String input =JOptionPane.showInputDialog("Ingrese Existencia de $"+ denominaciones[i]);
                //Guardar el valor del arreglo de las existencias
                existencias[i]=Integer.parseInt(input); 
            }
        }

   //Metodo para calcular el valor a devolver
        private void calcularDevuelta(){
            //Crear nuevo modelo para limpiar la tabla
    DefaultTableModel dtm= new DefaultTableModel(null, encabezados);
    //Obtener el valor ingresado por el usuario
    int valor= Integer.parseInt(txtValor.getText());
    //Recorrer cada denominacion
    for(int i=0; i<denominaciones.length;i++){
        //Calcular cuantas unidades se necesitan
        int cantidadNecesaria=valor/denominaciones[i];
        //Si se necesita mas de lo que hay disponible
        //Se usa solo la cantidad disponible
        if (cantidadNecesaria>existencias[i]){
            cantidadNecesaria=existencias[i];
        }


        //Restar al valor total lo utilizado
        valor-= cantidadNecesaria*denominaciones[i];
        //Si se utilizo al menos una unidad, se agrega a la tabla
        if (cantidadNecesaria>0){
            Object[]fila=new Object[3];
            fila[0]="$"+ denominaciones[i];
            fila[1]=tipo[i];
            fila[2]=cantidadNecesaria;

            dtm.addRow(fila);
        }

        
    }

    //Si aun queda dinero sin cubrir, significa que no alcanza
    if (valor>0){
        JOptionPane.showMessageDialog(this, "No hay suficiente dinero para completar la devuelta");
    }
    //Mostrar resultados de la tabla
    tblResultado.setModel(dtm);

   }
   //Metodo principal
   public static void main(String[] args) {
    //Crear y mostrar ventana
    new FrmCajaregistradora().setVisible(true);
    
   }

}