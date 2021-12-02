import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Main 
{
    static int lineasInvalidas = 0;
    static String cadenaTAIP;
    static ArrayList<TelemetriaExt> cadenaTelemetriaExtendida = new ArrayList<TelemetriaExt>();
    static ArrayList<Telemetria> cadenaTelemetriaBasica = new ArrayList<Telemetria>();

    private static final String ruta = System.getProperties().getProperty("user.dir");

    public static void main(String[] args) throws Exception 
    {
        agregarArchivo();
    }

    private static void agregarArchivo()
    {
        File archivo = new File(ruta + "//taip.txt");

        try 
        {
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);

            while((cadenaTAIP = br.readLine()) != null)
            {
                try 
                {   
                    if(cadenaTAIP.contains(";"))
                    {
                        cadenaTelemetriaExtendida.add(new TelemetriaExt(cadenaTAIP));
                    }
                    else
                    {
                        cadenaTelemetriaBasica.add(new Telemetria(cadenaTAIP));
                    }
                } 
                catch (Exception e) 
                {
                    lineasInvalidas++;
                }
            }
            br.close();

            System.out.println("\nCADENAS CON TELEMETRÍA BASICA" + "\n");
            for (int i = 0; i < cadenaTelemetriaBasica.size(); i++) 
            {
                System.out.println(cadenaTelemetriaBasica.get(i).mostrarContenido());       
            }
            System.out.println("\nCADENAS CON TELEMETRÍA EXTENDIDA" + "\n");
            for (int i = 0; i < cadenaTelemetriaExtendida.size(); i++) 
            {
                System.out.println("                        " + (i+1));
                System.out.println("----------------------------------------------------");
                System.out.println(cadenaTelemetriaExtendida.get(i).mostrarContenidoExtendido());       
            }
            System.out.println("CADENAS INVÁLIDAS: " + lineasInvalidas);
        }
        catch (Exception e) 
        {
            e.getMessage();
        }
    }
}