package ru.geekbrains;

public class Main {

    public static String InputString =  "10 3 1 2\n2 3 2 2\n5 6 7 1\n300 3 1 0";
    public static int perfectSizeArray = 4;
    public static String[][] strArray;

    public static void main(String[] args) {

        try {
            strArray = string_toArray(InputString, perfectSizeArray);
            try {
                printArraySqr(strArray, "Исходный массив:\n");
                System.out.println("Cумма элементов массива деленная на 2 = " + getSumArray(strArray));
            }
            catch (MyException e){
                e.printStackTrace();
            }
        }
        catch (MyException e){
            e.printStackTrace();
        }
    }

    // метод возвращает количество вхожнений в строке str, подстроки subString
    public static int count(String str, String subString) {
        return (str.length() - str.replace(subString, "").length()) / subString.length();
    }

    public static float getSumArray(String[][] strArray) throws MyException {
        float result = 0;

        for (int i = 0; i < strArray.length; i++) {
            for (int j = 0; j < strArray[i].length; j++) {

                try {
                    result += Float.parseFloat(strArray[i][j]);
                } catch (NumberFormatException e){
                 throw new MyException("Ошибка преобразования к числу");}
            }
        }

        return result / 2;
    }

    public static String[][] string_toArray(String str, int SizeArray) throws MyException {

        String[][] result = new String[SizeArray][SizeArray];
        String lineSeparator = "\n";
        String elementSeparator = " ";
        // перед преобразованием проверим что массив получится соответствующий требованиям по размеру SizeArray
        // если все ок формируем массив иначе возвращаем ошибку
        int countI = count(str, lineSeparator);
        int countJ = count(str, elementSeparator);
        if((countI == SizeArray - 1 || (countI == SizeArray && str.endsWith(lineSeparator)))
                &&((int) (countJ / SizeArray) == SizeArray - 1 && countJ % SizeArray == 0)){

            int endIndexStr = str.indexOf(lineSeparator);
            for (int i = 0; i < SizeArray; i++) {
                String strTemp = str.substring(0,endIndexStr) + " ";
                str = str.substring(endIndexStr + 1);
                for (int j = 0; j < SizeArray; j++) {
                    endIndexStr = strTemp.indexOf(elementSeparator);
                    result[i][j] = strTemp.substring(0, endIndexStr);
                    strTemp = strTemp.substring(endIndexStr + 1);
                }
                endIndexStr = str.indexOf(lineSeparator);
                if(endIndexStr == -1 && i != SizeArray - 1){
                   endIndexStr = str.length();
                    str += lineSeparator;
                }
            }
        }
        else{
            throw new MyException("Размер массива не идеален ждем массив " + SizeArray + "Х" + SizeArray);
        }
        return result;
    }

    public static void printArraySqr(String[][] array, String massage){
        System.out.print(massage);
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + "\t | \t");
            }
            System.out.println();
        }
        System.out.println();
    }

}

//    Есть строка вида: "10 3 1 2\n2 3 2 2\n5 6 7 1\n300 3 1 0"; (другими словами матрица 4x4)
//        10 3 1 2
//        2 3 2 2
//        5 6 7 1
//        300 3 1 0
//        Написать метод, на вход которого подаётся такая строка, метод должен преобразовать строку в двумерный массив типа String[][];
//        2. Преобразовать все элементы массива в числа типа int, просуммировать, поделить полученную сумму на 2, и вернуть результат;
//        3. Ваши методы должны бросить исключения в случаях:
//        Если размер матрицы, полученной из строки, не равен 4x4;
//        Если в одной из ячеек полученной матрицы не число; (например символ или слово)
//        4. В методе main необходимо вызвать полученные методы, обработать возможные исключения и вывести результат расчета.
//        5. * Написать собственные классы исключений для каждого из случаев