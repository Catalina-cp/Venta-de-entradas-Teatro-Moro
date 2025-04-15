import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ventaEntradasTeatroMoro {

   //Clase interna para almacer la información de cada compra
    static class Compra {
    //Variables de instancia
        int numeroCompra;
        String zona;
        List<String> asientos;
        int cantidad;
        double total;
        boolean descuentoEstudiante;
        boolean descuentoTerceraEdad;
        String descuentoPromocion;
        
        //Constructor para inicializar la informacion de la compra
        Compra(int numeroCompra, String zona, List<String> asientos, int cantidad, double total, boolean descuentoEstudiante, boolean descuentoTerceraEdad, String descuentoPromocion){
            this.numeroCompra = numeroCompra;
            this.zona = zona;
            this.asientos = asientos;
            this.cantidad = cantidad;
            this.total = total;
            this.descuentoEstudiante = descuentoEstudiante;
            this.descuentoTerceraEdad = descuentoTerceraEdad;
            this.descuentoPromocion = descuentoPromocion;
        }
    }
    
    //Variables estaticas
    
    static int asientos = 15;
    static int filas = 5;
    static int numeroCompraActual = 1;
    static boolean[][] zonaVip = new boolean[filas][asientos];
    static boolean[][] zonaPlatea = new boolean[filas][asientos];
    static boolean[][] zonaGeneral = new boolean[filas][asientos];
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        //Variables locales 
        int opcion = 0;
        int tipoZona = 0;
        List<Compra> compras = new ArrayList <>();
        int continuar = 0;
        
        while (true) {
            System.out.println("*** VENTA DE ENTRADAS TEATRO MORO");
            System.out.println("1.- Comprar entradas");
            System.out.println("2.- Ver plano de asientos");
            System.out.println("3.- Ver entradas vendidas");
            System.out.println("4.- Ver promociones");
            System.out.println("5.- Eliminar compra existente");
            System.out.println("6.- Buscar por tipo de descuento");
            System.out.println("7.- Salir");
            opcion = scanner.nextInt();
            
            //Opcion para comprar entradas
            if (opcion == 1){
                double precioBase = 0;
                String zona = "";
                boolean[][] zonaSeleccionada = null; 

                do {
                    System.out.println("Seleccione Zona");
                    System.out.println("1.- Vip $7000");
                    System.out.println("2.- Platea $6000");
                    System.out.println("3.- General $5000");
                    tipoZona = scanner.nextInt();
                    
                    if (tipoZona == 1) {
                        precioBase = 7000;
                        zona = "VIP";
                        zonaSeleccionada = zonaVip;
                    }else if (tipoZona == 2) {
                        precioBase = 6000;
                        zona = "Platea";
                        zonaSeleccionada = zonaPlatea;
                    }else if (tipoZona == 3) {
                        precioBase = 5000;
                        zona = "General";
                        zonaSeleccionada = zonaGeneral;
                    }else {
                        System.out.println("Zona invalida");
                    }
                    
                }while (zonaSeleccionada == null);
                
                //Lista para almacerar los asientos seleccionados
                List<String> asientosSeleccionados = new ArrayList <>();
                int cantidad = 0;
                
                do {
                    //Mostrar el plano de asiento de la zona seleccionada
                    
                    for (int i = 0; i < filas; i++) {
                        System.out.print("Fila" + (i + 1) + ": ");
                        for (int j = 0; j < asientos; j++) {
                            if (zonaSeleccionada[i][j]) {
                                System.out.print("[XX]");
                            } else {
                                System.out.print("[00]");
                            }
                        }
                        System.out.println("");
                    }
                    
                    System.out.println("Seleccione fila (1-5):");
                    int fila = scanner.nextInt();
                    
                    System.out.println("Seleccione asiento (1-15):");
                    int asiento = scanner.nextInt();
                    
                    //validar rango de fila y asiento
                    if (fila < 1 || fila > 5 || asiento < 1 || asiento > 15) {
                        System.out.println("Fila o asiento fuera de rango");
                        continue;
                    }
                    
                    //validar asiento ocupado
                    if (zonaSeleccionada[fila - 1][asiento - 1]) {
                        System.out.println("Asiento ocupado.");
                        continue;
                    }
                    
                    //Marcar el asiento como ocupado
                    zonaSeleccionada[fila - 1][asiento - 1] = true;
                    asientosSeleccionados.add(fila + "-" + asiento); 
                    cantidad++;
                    
                    System.out.println("¿Desea seleccionar otro asiento? (1=Si, 2=No");
                    continuar = scanner.nextInt();
                    
                }while (continuar == 1);
                
                boolean descuentoEstudiante = false;
                boolean descuentoTerceraEdad = false;
                double total = 0;
                
                //Calcular el precio de las entradas
                for (int i = 1; i <= cantidad; i++) {
                    System.out.println("Ingrese edad del comprador de la entrada " + i + ":");
                    int edad = scanner.nextInt();
                    double precio = precioBase;
                    
                    //Aplicar el descuento segun la edad ingresada
                    if (edad <= 18) {
                        precio *= 0.9;   // 10% dscto a estudiantes
                        descuentoEstudiante = true; 
                    } else if (edad >= 65) {
                        precio *= 0.85;  // 15% dscto a tercera edad
                        descuentoTerceraEdad = true;
                    }
                    
                    total += precio;
                }
                
                //Aplicar las promociones segun la cantidad de entradas compradas
                String descuentoPromo ="";
                if (cantidad >= 3 && cantidad <= 5) {
                    total *= 0.95;   //5% dscto por comprar de 3 a 5 entradas
                    descuentoPromo = "5% dscto por comprar 3 o mas entradas";
                } else if (cantidad > 5) {
                    total *= 0.9;   // 10% dscto por comprar mas de 5 ntradas
                    descuentoPromo = "10% dscto por comprar mas de 5 entradas";
                }
                
                //Realizar una nueva compra con todos los datos
                Compra nuevaCompra = new Compra(numeroCompraActual, zona, asientosSeleccionados, cantidad, total, descuentoEstudiante, descuentoTerceraEdad, descuentoPromo);
                compras.add(nuevaCompra);
                System.out.println("Compra realizada. Numero de compra: " + numeroCompraActual);
                
                numeroCompraActual++;
                
            } else if (opcion == 2) {
                System.out.println("Seleccione zona para ver el plano");
                System.out.println("1.- VIP");
                System.out.println("2.- Platea");
                System.out.println("3.- General");
                int zonaVer = scanner.nextInt();
                boolean[][] zonaVista = null;
                
                if (zonaVer == 1) zonaVista = zonaVip;
                else if (zonaVer == 2) zonaVista = zonaPlatea;
                else if (zonaVer == 3) zonaVista = zonaGeneral;
                
                if (zonaVista != null) {
                    for (int i = 0; i < filas; i++) {
                        System.out.print("Fila" + (i + 1) + ": ");
                        for (int j = 0; j < asientos; j++) {
                            if (zonaVista[i][j]) System.out.print("[XX]");
                            else System.out.print("[00]");
                        }
                        System.out.println("");
                    }
                }else {
                    System.out.println("Zona invalida");
                }
            } else if (opcion == 3) {
                if (compras.isEmpty()) {
                    System.out.println("No se han realizado compras.");
                } else {
                    for (Compra c : compras) {
                        mostrarCompra(c);
                    }
                }
                
            } else if (opcion == 4) {
                System.out.println("\n *** PROMOCIONES ***");
                System.out.println("- Descuento 5% por 3 o mas entradas");
                System.out.println("- Descuento 10% por mas de 5 entradas");
                System.out.println("");
                System.out.println("- Descuento 10% para Estudiantes");
                System.out.println("- Descuento 15% para Tercera edad \n");
                
            } else if (opcion == 5) {
                if (compras.isEmpty()) {
                    System.out.println("No hay compras registradas");
                }else {
                    System.out.println("Ingrese numero de compra a eliminar");
                    int eliminar = scanner.nextInt();
                    boolean encontrado = false;
                    
                    for (int i = 0; i < compras.size(); i++) {
                        if (compras.get(i).numeroCompra == eliminar) {
                            Compra compra = compras.remove(i);
                            boolean[][] zonaTarget = switch (compra.zona.toLowerCase()) {
                                case "vip" -> zonaVip;
                                case "Platea" -> zonaPlatea;
                                case "General" -> zonaGeneral;
                                default -> null;
                            };
                            
                            if (zonaTarget != null) {
                                for (String a : compra.asientos) {
                                    String[] partes = a.split("-");
                                    int fila = Integer.parseInt(partes[0]) - 1;
                                    int col = Integer.parseInt(partes[1]) - 1;
                                    zonaTarget[fila][col] = false;
                                }
                            }
                            System.out.println("Compra eliminada correctamente");
                            encontrado = true;
                            break;
                        }
                    }
                    
                    if (!encontrado) System.out.println("Compra no encontrada");
                }
                
            } else if (opcion == 6) {
                if (compras.isEmpty()) {
                    System.out.println("No hay compras para buscar");
                } else {
                    System.out.println("\n Buscar por tipo de descuento: ");
                    System.out.println("1.- Descuento por estudiante");
                    System.out.println("2.- Descuento por Tercera edad");
                    System.out.println("3.- Promocion por 3 o mas entradas (5%)");
                    System.out.println("4.- Promocion por mas de 5 entradas (10%)");
                    System.out.println("5.- Volver");
                    
                    int filtro = scanner.nextInt();
                    for (Compra c : compras) {
                        if ((filtro == 1 && c.descuentoEstudiante) ||
                                (filtro == 2 && c.descuentoTerceraEdad) ||
                                (filtro == 3 && c.descuentoPromocion.contains("5%")) ||
                                (filtro == 4 && c.descuentoPromocion.contains("10%"))) {
                            mostrarCompra(c);
                        }
                    }   
                }
                
            } else if (opcion == 7) {
                System.out.println("Gracias por visitarnos, hasta luego C:"); 
                break;
            } else {
                System.out.println("Opcion invalida");
            }
            
        }
    } 
    
    //Mostrar los detalles de una compra
    static void mostrarCompra(Compra c) {
        System.out.println("\n--- COMPRA #" + c.numeroCompra + " ---");
        System.out.println("Zona + c.zona");
        System.out.println("Asientos : " + c.asientos);
        System.out.println("Cantidad :" + c.cantidad);
        System.out.println("Total pagado: $" + c.total);
        if (c.descuentoEstudiante) System.out.println("Incluye descuento por Estudiante");
        if (c.descuentoTerceraEdad) System.out.println("Incluye descuento por Tercera Edad");
        if (!c.descuentoPromocion.isEmpty()) System.out.println("Promocion aplicada: " + c.descuentoPromocion);
    }
}