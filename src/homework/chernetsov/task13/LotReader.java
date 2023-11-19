package homework.chernetsov.task13;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

public class LotReader {
    private final String DEFAULT_FILE_NAME = "lot.txt";
    private final String fileName;
    private final BufferedReader reader;
    private String lotName;
    private BigDecimal lotValue;

    public LotReader(String fileName) throws FileNotFoundException {
        this.reader = new BufferedReader(new FileReader(fileName));
        this.fileName = (fileName == null || fileName.isEmpty())? DEFAULT_FILE_NAME : fileName;
    }


    public boolean readNextLot() {
        try {
            this.lotName = readNotEmptyLine();
            this.lotValue = new BigDecimal(readNotEmptyLine());
        }catch (IOException ex){
            //todo throw custom exception? IncorrectLotFile
        }catch (NumberFormatException e){
            //todo throw custom exception? IncorrectValueFormat ?
        }catch (Exception exception){ //todo my custom exception - недостаточно параметров для лота, хз кидать его или нет
            return false;
            // поймали кастомное исключение из своего метода
        }

        return true;
    }

    private String readNotEmptyLine() throws IOException {
        String line = "";
        while (line.isEmpty()){
            line = reader.readLine();
            if (line == null) {
                //todo throw custom exception with message - недостаточно параметров для лота
                throw new RuntimeException();
            }
        }
        return line;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/homework/chernetsov/task13/lot.txt"));
        System.out.println(reader.readLine());
        System.out.println(reader.readLine());
        System.out.println(reader.readLine());
        BigDecimal bigDecimal = new BigDecimal("a");
        System.out.println(bigDecimal);
    }
}
