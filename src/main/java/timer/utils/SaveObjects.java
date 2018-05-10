package timer.utils;

import timer.business.Settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Computer on 11-08-2017.
 */
public class SaveObjects {

    public static void serializeSettings(Settings settings){
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;

        try{
            if (!(new File("c:\\ageGadget\\ageSettings.ser")).exists()) {
                new File("c:\\ageGadget").mkdir();
                new File("c:\\ageGadget\\ageSettings.ser").createNewFile();
            }
            fout = new FileOutputStream("c:\\ageGadget\\ageSettings.ser");
            oos = new ObjectOutputStream(fout);
            oos.writeObject(settings);
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            if(fout!=null){
                try {
                    fout.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static Settings deserializeSettings(){
        Settings settings = null;

            try{
                if ((new File("c:\\ageGadget\\ageSettings.ser")).exists()) {
                    FileInputStream fileIn = new FileInputStream("c:\\ageGadget\\ageSettings.ser");
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    settings = (Settings) in.readObject();
                }
            } catch (Exception ex){
                ex.printStackTrace();
                return null;
            }
            return settings;
    }
}
