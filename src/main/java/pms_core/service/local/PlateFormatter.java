package pms_core.service.local;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlateFormatter {

    public static boolean inPattern(String plate){
        String platePattern = "^[A-Z]\\d{3}[A-Z]{2}$";
        Pattern govermentPlatePattern = Pattern.compile(platePattern);
        Matcher matcher = govermentPlatePattern.matcher(plate);
        return matcher.matches();
    }
    public static String formatPlate(String plate) {
        if (plate == null || plate.length() != 7) {
            throw new IllegalArgumentException("Plate must be exactly 7 characters long.");
        }

        StringBuilder result = new StringBuilder(plate);

        // İlk 2 sembolde 'O' varsa '0' ile değiştir
        for (int i = 0; i < 2; i++) {
            if (result.charAt(i) == 'O') {
                result.setCharAt(i, '0');
            }
        }

        // Ortadaki 2 sembolde '0' varsa 'O' ile değiştir
        for (int i = 2; i < 4; i++) {
            if (result.charAt(i) == '0') {
                result.setCharAt(i, 'O');
            }
        }

        // Son 3 sembolde 'O' varsa '0' ile değiştir
        for (int i = 4; i < 7; i++) {
            if (result.charAt(i) == 'O') {
                result.setCharAt(i, '0');
            }
        }

        return result.toString();
    }
}
