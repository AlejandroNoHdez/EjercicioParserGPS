import java.text.*;
import java.util.*;

public class Telemetria 
{
      String indice;
      int codigo_evento;
      String clasificacion_evento;
      Date fecha_event;
      String dia_semana;
      Date hora_event;
      double latitud;
      double longitud;
      float velocidad;
      int orientacion;
      String modo_posicion;
      String antiguedad_data;
      String referencia;

      static char valorLat;
      static char valorLng;
      
      public Telemetria(String cadenaTAIP) throws Exception
      {
            referencia = cadenaTAIP;
            asignarIndice(cadenaTAIP.charAt(1));
            asignarCodigoEvento(cadenaTAIP.substring(4, 6));
            asignarFechaEvento(cadenaTAIP.substring(6, 10),cadenaTAIP.substring(10,11),cadenaTAIP.substring(11, 16));
            //asignarHoraEvento(cadenaTAIP.substring(11, 16));
            asignarLatitud(cadenaTAIP.substring(16, 24));
            asignarLongitud(cadenaTAIP.substring(24, 33));
            velocidad = (float) (Integer.parseInt(cadenaTAIP.substring(33, 36)) * 1.60934);
            orientacion = Integer.parseInt(cadenaTAIP.substring(36, 39));
            asignarModoPosicion(cadenaTAIP.charAt(39));
            asignarAntiguedadData(cadenaTAIP.charAt(40));
      }

      private void asignarIndice(char valor) throws Exception
      {
            switch(valor)
            {
                  case 'R':
                              indice = "Response";
                              break;
                  case 'Q':
                              indice = "Query";
                              break;
                  case 'S':
                              indice = "Set";
                              break;
                  default:
                              throw new Exception("Índice Inválido");
            }
      }

      private void asignarCodigoEvento(String cadena)
      {
            codigo_evento = Integer.parseInt(cadena);

            if(codigo_evento == 0)
            {
                  clasificacion_evento = "Localización";
            }
            else if(codigo_evento == 1)
            {
                  clasificacion_evento = "Comunicacion";
            }
            else if(codigo_evento > 1 && codigo_evento <= 5)
            {
                  clasificacion_evento = "Operativo";
            }
            else if(codigo_evento > 5 && codigo_evento <= 7)
            {
                  clasificacion_evento = "Alarma/Dispositivo";
            }
            else if(codigo_evento > 7 && codigo_evento <= 9)
            {
                  clasificacion_evento = "Conducción";
            }
            else if(codigo_evento == 10)
            {
                  clasificacion_evento = "Alarma/Conducción";
            }
            else if(codigo_evento > 10 && codigo_evento <= 12)
            {
                  clasificacion_evento = "Conducción";
            }
            else if(codigo_evento == 13)
            {
                  clasificacion_evento = "Alarma/Conducción";
            }
            else if(codigo_evento == 14)
            {
                  clasificacion_evento = "Alarma";
            }
            else if(codigo_evento > 14 && codigo_evento <= 18)
            {
                  clasificacion_evento = "Alarma/Dispositivo";
            }
            else if(codigo_evento == 19)
            {
                  clasificacion_evento = "Silencioso";
            }
            else if(codigo_evento == 20)
            {
                  clasificacion_evento = "Alarma/Dispositivo";
            }
            else
            {
                  clasificacion_evento = "Genérico";
            }
      }

      private void asignarFechaEvento(String cadena, String dia, String horas) throws ParseException, Exception
      {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
            Date fechaInicio = sdf.parse("06-01-1980");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaInicio);
            calendar.set(Calendar.MINUTE,0);
            calendar.add(Calendar.DAY_OF_YEAR, (Integer.parseInt(cadena) * 7)+(Integer.parseInt(dia)));
            calendar.add(Calendar.SECOND, Integer.parseInt(horas));
            calendar.add(Calendar.HOUR, -6);
            fecha_event = calendar.getTime();
            hora_event = calendar.getTime();
            int dayofweek =  calendar.get(Calendar.DAY_OF_WEEK);
            asignarDiaSemana(dayofweek-1);
      }

      private void asignarDiaSemana(int valor) throws Exception
      {
            switch(valor)
            {
                  case 0:
                              dia_semana = "Domingo";
                              break;
                  case 1:
                              dia_semana = "Lunes";
                              break;
                  case 2:
                              dia_semana = "Martes";
                              break;
                  case 3:
                              dia_semana = "Miércoles";
                              break;
                  case 4:
                              dia_semana = "Jueves";
                              break;
                  case 5:
                              dia_semana = "Viernes";
                              break;
                  case 6:
                              dia_semana = "Sábado";
                              break;
                  default:
                              throw new Exception("Día de la semana inválido");
            }
      }
      
      private String convertirFecha()
      {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fecha = sdf.format(fecha_event);
            return fecha;
      }

      private String convertirHora()
      {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String hora = sdf.format(hora_event);
            return hora;
      }

      private void asignarLatitud(String cadena)
      {
            String lat = cadena.substring(1, 3);
            lat += "." + cadena.substring(3);
            valorLat = cadena.charAt(0);
            latitud = Double.valueOf(lat);
      }

      private void asignarLongitud(String cadena)
      {
            String lng = cadena.substring(1, 4);
            lng += "." + cadena.substring(4);
            valorLng = cadena.charAt(0);
            longitud = Double.valueOf(lng);
      }

      private void asignarModoPosicion(char valor) throws Exception
      {
            switch(valor)
            {
                  case '0':
                              modo_posicion = "2D GPS";
                              break;
                  case '1':
                              modo_posicion = "3D GPS";
                              break;
                  case '2':
                              modo_posicion = "2D DGPS";
                              break;
                  case '3':
                              modo_posicion = "3D DGPS";
                              break;
                  case '9':
                              modo_posicion = "UNKNOWN";
                              break;
                  default:
                              throw new Exception("Modo posición inválido");
            }
      }

      private void asignarAntiguedadData(char valor) throws Exception
      {
            switch(valor)
            {
                  case '0':
                              antiguedad_data = "NOT AVAILABLE";
                              break;
                  case '1':
                              antiguedad_data = "OLDER THAN 10 SECONDS";
                              break;
                  case '2':
                              antiguedad_data = "FRESH, LESS THAN 10 SECONDS";
                              break;
                  case '9':
                              antiguedad_data = "GPS FAILURE";
                              break;
                  default:
                              throw new Exception("Antigüedad de la data inválido");
            }
      }

      public String mostrarContenido()
      {
            return "|| REFERENCIA: " + referencia + "\n"
                  + "|| ÍNDICE DEL MENSAJE: " + indice + "\n"
                  + "|| ÍNDICE DEL EVENTO: " + codigo_evento + "\n"
                  + "|| CLASIFICACIÓN DEL EVENTO: " + clasificacion_evento + "\n"
                  + "|| FECHA DEL EVENTO: " + convertirFecha() + "\n"
                  + "|| DÍA DE LA SEMANA: " + dia_semana + "\n"
                  + "|| HORA DEL REPORTE: " + convertirHora() + "\n"
                  + "|| LATITUD: " + valorLat + latitud + "\n"
                  + "|| LONGITUD: " + valorLng + longitud + "\n"
                  + "|| VELOCIDAD: " + velocidad + " KM/H " + "\n"
                  + "|| ORIENTACIÓN: " + orientacion + "°" + "\n"
                  + "|| MODO DE POSICIÓN: " + modo_posicion + "\n"
                  + "|| ANTIÜEDAD DE LA DATA: " + antiguedad_data;
      }
}