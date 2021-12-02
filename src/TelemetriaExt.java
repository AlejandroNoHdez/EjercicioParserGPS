public class TelemetriaExt extends Telemetria
{
      int ignicion;
      int ext_pw;
      int aceleracion;
      int idle;
      int vo;
      String IMEI;

      public TelemetriaExt(String cadenaTAIP) throws Exception {
            super(cadenaTAIP);
            String[] cadenaExt = parteExtendida(cadenaTAIP.substring(41));

            for (String cadenaExt1 : cadenaExt) 
            {
                  generarDatos(cadenaExt1);      
            }
      }
      
      private String[] parteExtendida(String cadena)
      {
            String [] datos = cadena.split(";");

            return datos;
      }

      private void generarDatos(String cadena) throws Exception
      {
            if(cadena.contains("IO"))
            {
                  switch(cadena.charAt(3))
                  {
                        case '0':
                                    ignicion = 0;
                                    ext_pw = 0;
                                    break;
                        case '1':
                                    ignicion = 1;
                                    ext_pw = 0;
                                    break;
                        case '2':
                                    ignicion = 0;
                                    ext_pw = 1;
                                    break;
                        case '3':
                                    ignicion = 1;
                                    ext_pw = 1;
                                    break;
                  }
            }
            else if(cadena.contains("AC"))
            {
                  aceleracion = Integer.parseInt(cadena.substring(3));
            }
            else if(cadena.contains("CL"))
            {
                  idle = Integer.parseInt(cadena.substring(3));
            }
            else if(cadena.contains("VO"))
            {
                  vo = Integer.parseInt(cadena.substring(3));
            }
            else if(cadena.contains("ID"))
            {
                  IMEI = cadena.substring(3, cadena.length()-1);
            }
      }

      public String mostrarContenidoExtendido()
      {
            return mostrarContenido() + "\n"
                  + "|| IGNICIÓN: " + ignicion + "\n"
                  + "|| FUENTE DE ALIMENTACIÓN: " + ext_pw + "\n"
                  + "|| ACELERACIÓN: " + aceleracion + "\n"
                  + "|| CONTADOR DE IDLE: " + idle + "\n"
                  + "|| ODÓMETRO VIRTUAL: " + vo + "\n"
                  + "|| ID: " + IMEI+ "\n";
      }
}
