/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package outro;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author 04783714118
 */
public class Utilities {

    public static String getDataAtualString() {
        Date dataHoje = new Date();
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        String data = formataData.format(dataHoje);
        return data + " 01:01:01";
    }

    public static String getDataAtualSemHoraString() {
        Date dataHoje = new Date();
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        String data = formataData.format(dataHoje);
        return data;
    }

    public static Date StringToDate(String str) {
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Date dataEntrada = null;
        try {
            dataEntrada = formataData.parse(str);
        } catch (ParseException ex) {

        }
        return dataEntrada;
    }

    public static String DateToString(Date dt) {
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        String data = formataData.format(dt);
        return data;
    }

    public static String formataData(String data) {

        Date dt = StringToDate(data);

        return DateToString(dt);

    }

    public static Calendar DateToCalendar(Date date) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(date);
        return calendario;
    }

    public static ArrayList<?> embaralhaArrayList(ArrayList<?> al) {
        long seed = System.nanoTime();
        Random rand = new Random(seed);

        Collections.shuffle(al, rand);

        return al;
    }

}
